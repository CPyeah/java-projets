import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TransactionControllerTest {

	@BeforeAll
	static void setup() {

		Row<Customer> row = getCustomerRow(1, "刘备");

		Customer.tableData.put(row.getPrimaryId(), row);
	}

	@Test
	public void test1() {

		// 开启事务A
		Transaction transaction_A = TransactionController.start();

		// 开启事务B
		Transaction transaction_B = TransactionController.start();

		// 更新数据，同时会加上行锁
		Row<Customer> 关羽 = getCustomerRow(1, "关羽");
		TransactionController.update(关羽, transaction_A, Customer.tableData);

		// 更新数据，同时会加上行锁
		Row<Customer> 张飞 = getCustomerRow(1, "张飞");
		TransactionController.update(张飞, transaction_A, Customer.tableData);

		// 事务A提交，并释放锁
		TransactionController.commit(transaction_A);

		// 更新数据，同时会加上行锁
		Row<Customer> 赵云 = getCustomerRow(1, "赵云");
		TransactionController.update(赵云, transaction_B, Customer.tableData);

		// 更新数据，同时会加上行锁
		Row<Customer> 诸葛亮 = getCustomerRow(1, "诸葛亮");
		TransactionController.update(诸葛亮, transaction_B, Customer.tableData);

		//commit
		TransactionController.commit(transaction_B);

		// 打印undo日志链表
		printData(Customer.tableData.get(1));
		// 1-诸葛亮(200) -> 1-赵云(200) -> 1-张飞(100) -> 1-关羽(100) -> 1-刘备(null)

	}

	private void printData(Row<Customer> customerRow) {
		StringBuilder sb = new StringBuilder(toString(customerRow));

		Row<Customer> currentPointer = customerRow;

		while (currentPointer.getRoll_pointer() != null) {
			currentPointer = currentPointer.getRoll_pointer();
			sb.append(" -> ")
					.append(toString(currentPointer));
		}
		System.out.println(sb);

	}

	private String toString(Row<Customer> customerRow) {
		return customerRow.getPrimaryId() + "-" + customerRow.getOtherData().getName()
				+ "("
				+ customerRow.getTrx_id()
				+ ")";
	}


	private static Row<Customer> getCustomerRow(Integer id, String name) {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setName(name);

		Row<Customer> row = new Row<>();
		row.setPrimaryId(customer.getId());
		row.setOtherData(customer);

		return row;
	}

}