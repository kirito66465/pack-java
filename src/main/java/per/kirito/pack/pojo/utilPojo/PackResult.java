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

	private String id;                              // 快递单号
	private String org;                             // 快递公司
	private String per_name;                        // 收件人
	private String per_tel;                         // 收件手机号
	private String per_addr;                        // 收件地址
	private String addr;                            // 所在驿站
	private String code;                            // 取件码
	private String cont_name;                       // 驿站联系人
	private String cont_tel;                        // 驿站联系方式
	private String status;                          // 快递状态
	private String start;                           // 入站时间
	private String end;                             // 取件时间
	private String pick;                            // 签收人

	public PackResult(Pack pack) {
		this.id = pack.getId();
		this.org = pack.getOrg();
		this.per_name = pack.getPer_name();
		this.per_tel = pack.getPer_tel();
		this.per_addr = pack.getPer_addr();
		this.addr = pack.getAddr();
		this.code = pack.getCode();
		this.cont_name = pack.getCont_name();
		this.cont_tel = pack.getCont_tel();
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
