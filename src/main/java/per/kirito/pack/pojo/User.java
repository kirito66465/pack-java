package per.kirito.pack.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import per.kirito.pack.annotation.Comment;

import java.io.Serializable;

/**
 * @author kirito
 * @date 2020/12/4
 * @time 20:37
 * 用户实体类
 */
@Data
@NonNull
@ApiModel(value = "User", description = "学生对象")
@TableName(value = "t_user")
public class User implements Serializable {

	@Comment(msg = "学号")
	@ApiModelProperty(value = "学号", name = "card", dataType = "String", required = true)
	@TableId(value = "card", type = IdType.NONE)
	private String card;

	@Comment(msg = "密码")
	@ApiModelProperty(value = "密码", name = "password", dataType = "String", required = true)
	@TableField(value = "password")
	private String password;

	@Comment(msg = "手机号")
	@ApiModelProperty(value = "手机号", name = "phone", dataType = "String", required = true)
	@TableField(value = "phone")
	private String phone;

	@Comment(msg = "姓名")
	@ApiModelProperty(value = "姓名", name = "name", dataType = "String", required = false)
	@TableField(value = "name")
	private String name;

	@Comment(msg = "收货地址")
	@ApiModelProperty(value = "手机号", name = "phone", dataType = "String", required = true)
	@TableField(value = "addr")
	private String addr;

	@Comment(msg = "包裹件数(仅限已入站但未取出的快递)")
	@ApiModelProperty(value = "包裹件数(仅限已入站但未取出的快递)", name = "count", dataType = "Integer", required = true)
	@TableField(value = "count")
	private Integer count;

	@Comment(msg = "电子邮箱")
	@ApiModelProperty(value = "电子邮箱", name = "mail", dataType = "String", required = true)
	@TableField(value = "mail")
	private String mail;

}
