import java.util.concurrent.locks.ReentrantLock;
import lombok.Data;

@Data
public class Row<T> {

	// 当前操作的事务id
	private Integer trx_id;

	// 历史记录的地址
	private Row<T> roll_pointer;

	// 主键ID
	private Integer primaryId;

	// 其他数据
	private T otherData;

	// 行锁
	private ReentrantLock rowLock;

}
