package per.kirito.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Table;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/6 16:08
 * @description: 寄件实体类
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_send")
public class Send {

	private String from_name;       // 寄件人姓名
	private String from_tel;        // 寄件人手机号
	private String from_addr;       // 寄件人地址
	private String to_name;         // 收件人姓名
	private String to_tel;          // 收件人手机号
	private String to_addr;         // 收件人地址
	private double price;           // 运费
	private String id;              // 快递单号
	private String org;             // 快递公司
	private String status;          // 快递状态
	private String dt;              // 时间戳

}
