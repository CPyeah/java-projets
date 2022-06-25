import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionController {

	private static final AtomicInteger globeTransactionID = new AtomicInteger(100);

	public static Integer getNextTransactionId() {
		return globeTransactionID.getAndAdd(100);
	}

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

	// 开启一个事物， 为了方便，加锁步骤放到了开始事物里
	public static <T> Transaction start(HashMap<Integer, Row<T>> tableData) {
		Integer transactionId = getNextTransactionId();
		Transaction transaction = new Transaction();
		transaction.setTransactionId(transactionId);
		// 如果数据不存在，间隙锁（暂时不管）
		// 。。。。

		return transaction;
	}

	// 事物提交
	public static void commit(Transaction transaction) {

	}

}
