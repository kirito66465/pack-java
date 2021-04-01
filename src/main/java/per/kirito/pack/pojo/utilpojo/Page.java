package per.kirito.pack.pojo.utilpojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import per.kirito.pack.annotation.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * @author kirito
 * @date 2020/12/28
 * @time 14:48
 * 自定义分页
 */
@Data
@NoArgsConstructor
public class Page<T> implements Serializable {

	@Comment(msg = "当前页数")
	private Integer currentPage;

	@Comment(msg = "每页的记录条数")
	private Integer pageSize;

	@Comment(msg = "总记录条数")
	private Long total;

	@Comment(msg = "当前页的记录")
	private List<T> list;

}
