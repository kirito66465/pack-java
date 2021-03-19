package per.kirito.pack.pojo;

import lombok.*;

import javax.persistence.Table;

/**
 * author: kirito
 * date: 2020/12/4
 * time: 20:37
 * 管理员实体类
 */
@Data
@NonNull
@Builder
@AllArgsConstructor
@Table(name = "t_admin")
public class Admin {

	/**
	 * 编号
	 */
	private String card;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 地址
	 */
	private String addr;

	/**
	 * 包裹件数(仅限已入站但未取出的快递)
	 */
	private Integer count;

}
