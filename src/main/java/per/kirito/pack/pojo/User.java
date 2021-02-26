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
 * 用户实体类
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User {

	/** 学号 */
	private String card;
	/** 密码 */
	private String password;
	/** 手机号 */
	private String phone;
	/** 姓名 */
	private String name;
	/** 收货地址 */
	private String addr;
	/** 包裹件数 */
	private Integer count;
	/** 电子邮箱 */
	private String mail;

}
