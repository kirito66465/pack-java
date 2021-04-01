package per.kirito.pack.pojo.utilpojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "SendRequest", description = "寄件表单对象")
public class SendRequest implements Serializable {

	@Comment(msg = "驿站选择")
	@ApiModelProperty(value = "驿站", name = "admin", dataType = "String", required = true)
	private String admin;
	
	@Comment(msg = "收件人姓名")
	@ApiModelProperty(value = "收件人姓名", name = "name", dataType = "String", required = true)
	private String name;

	@Comment(msg = "收件人联系方式")
	@ApiModelProperty(value = "收件人联系方式", name = "phone", dataType = "String", required = true)
	private String phone;

	@Comment(msg = "收件人地址，省/市/区")
	@ApiModelProperty(value = "收件人地址", name = "addr", dataType = "String", required = true, example = "江苏省南京市栖霞区")
	private String addr;

	@Comment(msg = "物品信息")
	@ApiModelProperty(value = "物品信息", name = "info", dataType = "String", required = true)
	private String info;

	@Comment(msg = "物品重量")
	@ApiModelProperty(value = "物品重量", name = "weight", dataType = "Integer", required = true)
	private Integer weight;

	@Comment(msg = "运费")
	@ApiModelProperty(value = "运费", name = "price", dataType = "Double", required = true)
	private Double price;

	@Comment(msg = "登录状态令牌")
	@ApiModelProperty(value = "登录状态令牌", name = "token", dataType = "String", required = true)
	private String token;

}
