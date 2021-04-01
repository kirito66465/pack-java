package per.kirito.pack.pojo.utilpojo;

import lombok.*;
import per.kirito.pack.annotation.Comment;

import java.io.Serializable;

/**
 * @author kirito
 * @date 2021/1/4
 * @time 15:35
 * 前台 User 进行寄件的请求参数
 */
@Data
@NonNull
public class SendRequest implements Serializable {

	@Comment(msg = "驿站选择")
	private String admin;
	
	@Comment(msg = "收件人姓名")
	private String name;

	@Comment(msg = "收件人联系方式")
	private String phone;

	@Comment(msg = "收件人地址，省/市/区")
	private String addr;

	@Comment(msg = "物品信息")
	private String info;

	@Comment(msg = "物品重量")
	private Integer weight;

	@Comment(msg = "运费")
	private Double price;

	@Comment(msg = "登录状态令牌")
	private String token;

}
