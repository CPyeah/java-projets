import java.util.HashMap;

public class Customer {

	// 表数据存储
	public static HashMap<Integer, Row<Customer>> tableData = new HashMap<>();

	private Integer id;

	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
