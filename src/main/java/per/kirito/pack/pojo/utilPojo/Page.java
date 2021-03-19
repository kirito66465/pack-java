package per.kirito.pack.pojo.utilPojo;

import lombok.Data;

import java.util.List;

/**
 * author: kirito
 * date: 2020/12/28
 * time: 14:48
 * 分页实体类
 */
@Data
public class Page<T> {

	/**
	 * 当前页数
	 */
	private Integer currentPage;

	/**
	 * 每页的记录条数
	 */
	private Integer pageSize;

	/**
	 * 总记录条数
	 */
	private Integer total;

	/**
	 * 当前页的记录
	 */
	private List<T> list;

}
