package per.kirito.pack.pojo.utilPojo;

import lombok.Data;

import java.util.List;

/**
 * author: 严晨
 * date: 2020/12/28
 * time: 14:48
 * 分页实体类
 */
@Data
public class Page<T> {

	/** 当前页数 */
	private int currentPage;
	/** 每页的记录条数 */
	private int pageSize;
	/** 总记录条数 */
	private int total;
	/** 当前页的记录 */
	private List<T> list;

}
