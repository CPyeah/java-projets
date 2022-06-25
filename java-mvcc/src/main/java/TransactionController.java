import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionController {

	// 当前活跃的事务列表
	public static List<Integer> m_ids = new LinkedList<>();

	// 全局事务ID
	private static final AtomicInteger globeTransactionID = new AtomicInteger(100);

	// 获取事务ID
	public static Integer getNextTransactionId() {
		return globeTransactionID.getAndAdd(100);
	}

	// 更新数据，加行锁
	public static <T> T update(Row<T> newRow, Transaction transaction,
			HashMap<Integer, Row<T>> tableData) {
		Integer primaryId = newRow.getPrimaryId();
		Row<T> oldRow = tableData.get(primaryId);
		if (oldRow == null) {
			throw new IllegalArgumentException("Data does not exist");
		} else {
			newRow.setTrx_id(transaction.getTransactionId());
			newRow.setRoll_pointer(oldRow); // 更新版本链
			tableData.put(primaryId, newRow);
		}
		return newRow.getOtherData();
	}

	// 开启一个事务
	public static Transaction start() {
		Integer transactionId = getNextTransactionId();

		Transaction transaction = new Transaction();
		transaction.setTransactionId(transactionId);

		// 把当前的事务ID，存放到活跃事务列表中
		m_ids.add(transactionId);
		return transaction;
	}

	// 事务提交
	public static void commit(Transaction transaction) {
		if (transaction.getLock() != null) {
			transaction.getLock().unlock();
		}

		// 在活跃事务列表中，移除事务ID
		m_ids.remove(transaction.getTransactionId());
	}

	// 获取到ReadView
	public <T> Row<T> readView(Row<T> chain, Integer currentTrxId) {
		if (chain == null) {
			return null;
		}
		if (m_ids.isEmpty()) {
			return chain;
		}
		// 该值代表生成readView时m_ids中的最小值
		Integer min_trx_id = m_ids.get(0);
		for (Integer id : m_ids) {
			min_trx_id = Math.min(min_trx_id, id);
		}
		// 该值代表生成readView时系统中应该分配给下一个事务的id值
		Integer max_trx_id = getNextTransactionId();

		Row<T> pointer = chain;

		while (true) {
			if (isThisReadView(pointer, min_trx_id, max_trx_id)) {
				return pointer;
			}
			if (pointer.getRoll_pointer() != null) {
				pointer = pointer.getRoll_pointer();
			} else {
				return null;
			}
		}

	}

	// 判断是否此版本为ReadView
	private <T> boolean isThisReadView(Row<T> pointer,
			Integer min,
			Integer max) {
		// 如果被访问版本的trx_id属性值小于m_ids列表中最小的事务id，
		// 表明生成该版本的事务在生成ReadView前已经提交，
		// 所以该版本可以被当前事务访问。
		if (pointer.getTrx_id() < min) {
			return true;
		}
		// 如果被访问版本的trx_id属性值大于m_ids列表中最大的事务id，
		// 表明生成该版本的事务在生成ReadView后才生成，
		// 所以该版本不可以被当前事务访问。
		if (pointer.getTrx_id() > max) {
			return false;
		}
		// 如果被访问版本的trx_id属性值在m_ids列表中最大的事务id和最小事务id之间，
		// 那就需要判断一下trx_id属性值是不是在m_ids列表中，
		// 如果在，说明创建ReadView时生成该版本的事务还是活跃的，该版本不可以被访问；
		// 如果不在，说明创建ReadView时生成该版本的事务已经被提交，该版本可以被访问。
		if (m_ids.contains(pointer.getTrx_id())) {
			return false;
		} else {
			return true;
		}
	}

}
