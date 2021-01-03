package per.kirito.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Table;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/4 20:41
 * @description:
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_pack")
public class Pack {

	private String id;                              // 快递单号
	private String org;                             // 快递公司
	private String per_name;                        // 收件人
	private String per_tel;                         // 收件手机号
	private String per_addr;                        // 收件地址
	private String addr;                            // 所在驿站
	private String code;                            // 取件码
	private String cont_name;                       // 驿站联系人
	private String cont_tel;                        // 驿站联系方式
	private int status;                             // 快递状态
	private String start;                           // 入站时间
	private String end;                             // 取件时间
	private String pick;                            // 取件人

}
