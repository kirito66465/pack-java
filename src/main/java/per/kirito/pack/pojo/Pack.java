package per.kirito.pack.pojo;

import lombok.*;

import javax.persistence.Table;

/**
 * author: kirito
 * date: 2020/12/4
 * time: 20:41
 * 快递实体类
 */
@Data
@NonNull
@Builder
@AllArgsConstructor
@Table(name = "t_pack")
public class Pack {

	/** 快递单号 */
	private String id;
	/** 快递公司 */
	private String org;
	/** 收件人 */
	private String per_name;
	/** 收件手机号 */
	private String per_tel;
	/** 收件地址 */
	private String per_addr;
	/** 所在驿站 */
	private String addr;
	/** 取件码 */
	private String code;
	/** 驿站联系人 */
	private String cont_name;
	/** 驿站联系方式 */
	private String cont_tel;
	/** 快递状态 */
	private Integer status;
	/** 入站时间 */
	private String start;
	/** 取件时间 */
	private String end;
	/** 取件人 */
	private String pick;

}
