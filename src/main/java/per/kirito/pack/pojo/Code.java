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
 * @date 2020/12/23
 * @time 17:20
 * 取件码存放表
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_code")
public class Code implements Serializable {

	@Comment(msg = "ID")
	@TableId(value = "ID", type = IdType.AUTO)
	private Integer id;

	@Comment(msg = "取件码")
	@TableField(value = "code")
	private String code;

	@Comment(msg = "所在驿站")
	@TableField(value = "addr")
	private String addr;

	@Comment(msg = "状态，0：未被使用，1：已被使用")
	@TableField(value = "status")
	private Integer status;

	@Comment(msg = "释放时间")
	@TableField(value = "free")
	private String free;

}
