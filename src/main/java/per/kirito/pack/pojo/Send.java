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
 * @date 2020/12/6
 * @time 16:08
 * 寄件实体类
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_send")
public class Send implements Serializable {

	@Comment(msg = "寄件人姓名")
	@TableField(value = "from_name")
	private String fromName;

	@Comment(msg = "寄件人手机号")
	@TableField(value = "from_tel")
	private String fromTel;

	@Comment(msg = "寄件人地址")
	@TableField(value = "from_addr")
	private String fromAddr;

	@Comment(msg = "收件人姓名")
	@TableField(value = "to_name")
	private String toName;

	@Comment(msg = "收件人手机号")
	@TableField(value = "to_tel")
	private String toTel;

	@Comment(msg = "收件人地址")
	@TableField(value = "to_addr")
	private String toAddr;

	@Comment(msg = "运费")
	@TableField(value = "price")
	private Double price;

	@Comment(msg = "快递单号")
	@TableId(value = "id", type = IdType.NONE)
	private String id;

	@Comment(msg = "快递公司")
	@TableField(value = "org")
	private String org;

	@Comment(msg = "快递状态")
	@TableField(value = "status")
	private String status;

	@Comment(msg = "时间戳")
	@TableField(value = "dt")
	private String dt;

}
