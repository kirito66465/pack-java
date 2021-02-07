package per.kirito.pack.pojo.utilPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import per.kirito.pack.pojo.Pack;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/29
 * @Time: 10:32
 * @description: 快递信息转换结果：jdbcType(Integer) -> javaType(String)
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
public class PackResult {

	/** 快递单号 */
	private String id;
	/** 快递公司 */
	private String org;
	/** 收件人 */
	private String perName;
	/** 收件手机号 */
	private String perTel;
	/** 收件地址 */
	private String perAddr;
	/** 所在驿站 */
	private String addr;
	/** 取件码 */
	private String code;
	/** 驿站联系人 */
	private String contName;
	/** 驿站联系方式 */
	private String contTel;
	/** 快递状态 */
	private String status;
	/** 入站时间 */
	private String start;
	/** 取件时间 */
	private String end;
	/** 签收人 */
	private String pick;

	public PackResult(Pack pack) {
		this.id = pack.getId();
		this.org = pack.getOrg();
		this.perName = pack.getPer_name();
		this.perTel = pack.getPer_tel();
		this.perAddr = pack.getPer_addr();
		this.addr = pack.getAddr();
		this.code = pack.getCode();
		this.contName = pack.getCont_name();
		this.contTel = pack.getCont_tel();
		this.start = pack.getStart();
		this.end = pack.getEnd();
		this.pick = pack.getPick();
		if (pack.getStatus() == 1) {
			this.status = "未取出";
		} else if (pack.getStatus() == 0) {
			this.status = "已取出";
		} else if (pack.getStatus() == -1) {
			this.status = "未有取件码";
		}
	}

}
