package per.kirito.pack.pojo;

import lombok.*;

import javax.persistence.Table;

/**
 * author: kirito
 * date: 2020/12/6
 * time: 16:08
 * 寄件实体类
 */
@Data
@NonNull
@Builder
@AllArgsConstructor
@Table(name = "t_send")
public class Send {

	/**
	 * 寄件人姓名
	 */
	private String from_name;

	/**
	 * 寄件人手机号
	 */
	private String from_tel;

	/**
	 * 寄件人地址
	 */
	private String from_addr;

	/**
	 * 收件人姓名
	 */
	private String to_name;

	/**
	 * 收件人手机号
	 */
	private String to_tel;

	/**
	 * 收件人地址
	 */
	private String to_addr;

	/**
	 * 运费
	 */
	private Double price;

	/**
	 * 快递单号
	 */
	private String id;

	/**
	 * 快递公司
	 */
	private String org;

	/**
	 * 快递状态
	 */
	private String status;

	/**
	 * 时间戳
	 */
	private String dt;

}
