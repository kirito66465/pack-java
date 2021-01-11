package per.kirito.pack.pojo.utilPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/4
 * @Time: 15:35
 * @description: 前端User进行寄件的请求参数
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
public class SendRequest {

	private String admin;               // 驿站选择
	private String name;                // 收件人姓名
	private String phone;               // 收件人联系方式
	private String addr;                // 收件人地址，省/市/区
	private String info;                // 物品信息
	private int weight;                 // 物品重量
	private double price;               // 运费
	private String token;               // 登录状态令牌

}
