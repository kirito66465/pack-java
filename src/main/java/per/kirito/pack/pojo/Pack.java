package per.kirito.pack.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import per.kirito.pack.annotation.Comment;
import per.kirito.pack.myenum.PackStatusEnum;

import java.io.Serializable;

/**
 * @author kirito
 * @date 2020/12/4
 * @time 20:41
 * 快递实体类
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_pack")
public class Pack implements Serializable {

	@Comment(msg = "快递单号")
	@TableId(value = "id", type = IdType.NONE)
	private String id;

	@Comment(msg = "快递公司")
	@TableField(value = "org")
	private String org;

	@Comment(msg = "收件人")
	@TableField(value = "per_name")
	private String perName;

	@Comment(msg = "收件手机号")
	@TableField(value = "per_tel")
	private String perTel;

	@Comment(msg = "收件地址")
	@TableField(value = "per_addr")
	private String perAddr;

	@Comment(msg = "所在驿站")
	@TableField(value = "addr")
	private String addr;

	@Comment(msg = "取件码")
	@TableField(value = "code")
	private String code;

	@Comment(msg = "驿站联系人")
	@TableField(value = "cont_name")
	private String contName;

	@Comment(msg = "驿站联系方式")
	@TableField(value = "cont_tel")
	private String contTel;

	@Comment(msg = "快递状态")
	@TableField(value = "status")
	private PackStatusEnum status;

	@Comment(msg = "入站时间")
	@TableField(value = "start")
	private String start;

	@Comment(msg = "取件时间")
	@TableField(value = "end")
	private String end;

	@Comment(msg = "取件人")
	@TableField(value = "pick")
	private String pick;

}
