package per.kirito.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Table;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/4 20:37
 * @description:
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_user")
public class User {
	private String card;        // 学号
	private String password;    // 密码
	private String phone;       // 手机号
	private String name;        // 姓名
	private String addr;        // 收货地址
	private int count;          // 包裹件数    TODO: 包裹件数数据类型变为整型
}
