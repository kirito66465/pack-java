package per.kirito.pack.pojo.utilPojo;

import lombok.*;

/**
 * author: 严晨
 * date: 2021/1/4
 * time: 15:35
 * 前端 User 进行寄件的请求参数
 */
@Data
@NonNull
@Builder
@AllArgsConstructor
public class SendRequest {

	/** 驿站选择 */
	private String admin;
	/** 收件人姓名 */
	private String name;
	/** 收件人联系方式 */
	private String phone;
	/** 收件人地址，省/市/区 */
	private String addr;
	/** 物品信息 */
	private String info;
	/** 物品重量 */
	private Integer weight;
	/** 运费 */
	private Double price;
	/** 登录状态令牌 */
	private String token;

}
