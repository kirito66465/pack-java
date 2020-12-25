package per.kirito.pack.other.util;

import per.kirito.pack.other.myEnum.Express;
import per.kirito.pack.pojo.Pack;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:48
 * @description: 包裹信息完善:自动生成取件码、更新取件状态、添加地址信息
 */
public class PackUtil {
	// 快递已取出
	private static final int HAS_TAKEN = 1;
	// 快递未取出
	private static final int NOT_TAKEN = 0;
	// 快递未取出且无取件码
	private static final int NO_CODE = -1;

	// 快递公司的中文名与英文名
	private static final String ZTO = String.valueOf(Express.中通);
	private static final String STO = String.valueOf(Express.申通);
	private static final String YTO = String.valueOf(Express.圆通);
	private static final String JD = String.valueOf(Express.京东);
	private static final String SF = String.valueOf(Express.顺丰);
	private static final String YD = String.valueOf(Express.韵达);
	private static final String TT = String.valueOf(Express.天天);
	private static final String EMS = String.valueOf(Express.EMS);

	// 所在驿站
	private static final String ADDR_ZY = "中苑";
	private static final String ADDR_XY = "西苑";
	private static final String ADDR_BY = "北苑";

	// 驿站联系人
	private static final String CONT_ZY = "中苑快递员";
	private static final String CONT_XY = "西苑快递员";
	private static final String CONT_BY = "北苑快递员";

	// 驿站联系方式
	private static final String TEL_ZY = "13300000001";
	private static final String TEL_XY = "13300000002";
	private static final String TEL_BY = "13300000003";

	// 驿站已有取件码的最大快递数量
	private static final int MAX_PACKS = 2400;

	// 添加入站信息(入站时只有快递单号这一条信息)
	public static Pack addCode(Pack pack, int count) {
		// TODO: 用户在取件窗口输入驿站地址、取件码进行取件，无取件码无法取件
		// TODO: controller层先判断最大取件码有无被使用，如未被使用，则添加取件码，反之不添加
		// TODO: 此时count未+1，等工具类返回pack之后再+1/-1
		String id = pack.getId().substring(0, 2);
		String org = "";
		String addr = "";
		String cont_per = "";
		String cont_tel = "";
		switch (id) {
			case "75":
				org = ZTO;
				addr = ADDR_ZY;
				cont_per = CONT_ZY;
				cont_tel = TEL_ZY;
				break;
			case "77":
				org = STO;
				addr = ADDR_ZY;
				cont_per = CONT_ZY;
				cont_tel = TEL_ZY;
				break;
			case "YT":
				org = YTO;
				addr = ADDR_ZY;
				cont_per = CONT_ZY;
				cont_tel = TEL_ZY;
				break;
			case "JD":
				org = JD;
				addr = ADDR_XY;
				cont_per = CONT_XY;
				cont_tel = TEL_XY;
				break;
			case "SF":
				org = SF;
				addr = ADDR_XY;
				cont_per = CONT_XY;
				cont_tel = TEL_XY;
				break;
			case "43":
				org = YD;
				addr = ADDR_XY;
				cont_per = CONT_XY;
				cont_tel = TEL_XY;
				break;
			case "88":
				org = TT;
				addr = ADDR_BY;
				cont_per = CONT_BY;
				cont_tel = TEL_BY;
				break;
			case "EA":
				org = EMS;
				addr = ADDR_BY;
				cont_per = CONT_BY;
				cont_tel = TEL_BY;
				break;
			default: break;
		}
		// 添加快递公司信息
		pack.setOrg(org);
		// 添加所在驿站信息
		pack.setAddr(addr);
		// 添加驿站联系人信息
		pack.setCont_name(cont_per);
		// 添加驿站联系方式信息
		pack.setCont_tel(cont_tel);
		// 驿站现存快递数量大于能有取件码的快递数量
		if (count >= MAX_PACKS) {
			pack.setStatus(NO_CODE);
		} else {
			// 此入站快递能有取件码
			pack.setStatus(NOT_TAKEN);
		}
		String time = TypeConversion.getTime();
		pack.setStart(time);
		return pack;
	}

	// 更新快递状态
	public static Pack updateStatus(Pack pack) {
		int status = pack.getStatus();
		if (status == NOT_TAKEN) {
			pack.setStatus(HAS_TAKEN);
			String time = TypeConversion.getTime();     // TODO: 包裹表日期类型的字段，字段类型改为字符串
			pack.setEnd(time);
		}
		return pack;
	}

	public static void main(String[] args) {
		Pack pack = new Pack();
		pack.setId("1234567890");
		pack.setStatus(NOT_TAKEN);
		pack = updateStatus(pack);
		System.out.println(pack.getEnd());
		System.out.println(pack.getStatus());
		System.out.println(pack.getId().substring(0, 2));
	}
}
