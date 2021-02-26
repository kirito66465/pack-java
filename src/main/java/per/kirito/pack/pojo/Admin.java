package per.kirito.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Table;

/**
 * author: 严晨
 * date: 2020/12/4
 * time: 20:37
 * 管理员实体类
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_admin")
public class Admin {

	/** 编号 */
	private String card;
	/** 密码 */
	private String password;
	/** 手机号 */
	private String phone;
	/** 姓名 */
	private String name;
	/** 地址 */
	private String addr;
	/** 包裹件数(仅限已入站但未取出的快递) */
	private Integer count;

}
