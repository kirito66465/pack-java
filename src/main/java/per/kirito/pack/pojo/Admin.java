package per.kirito.pack.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import per.kirito.pack.annotation.Comment;

import java.io.Serializable;

/**
 * @author kirito
 * @date 2020/12/4
 * @time 20:37
 * 管理员实体类
 */
@Data
@NonNull
@TableName(value = "t_admin")
public class Admin implements Serializable {

	@Comment(msg = "编号")
	@TableId(value = "card", type = IdType.NONE)
	private String card;

	@Comment(msg = "密码")
	@TableField(value = "password")
	private String password;

	@Comment(msg = "手机号")
	@TableField(value = "phone")
	private String phone;

	@Comment(msg = "姓名")
	@TableField(value = "name")
	private String name;

	@Comment(msg = "地址")
	@TableField(value = "addr")
	private String addr;

	@Comment(msg = "包裹件数(仅限已入站但未取出的快递)")
	@TableField(value = "count")
	private Integer count;

}
