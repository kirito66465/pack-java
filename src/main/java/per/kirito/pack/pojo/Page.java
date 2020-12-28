package per.kirito.pack.pojo;

import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/28
 * @Time: 14:48
 * @description:
 */
@Data
public class Page<T> {
	private int currentPage;        // 当前页数
	private int pageSize;           // 每页的记录条数
	private int total;              // 总记录条数
	private List<T> list;           // 当前页的记录
}
