package per.kirito.pack.util;

import per.kirito.pack.myenum.Express;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kirito
 * @date 2021/1/5
 * @time 17:15
 * 寄件工具类
 */
public class SendUtil {

	private static final String ZTO = String.valueOf(Express.中通);
	private static final String YD = String.valueOf(Express.韵达);
	private static final String EMS = String.valueOf(Express.EMS);

	private static final String ZTO_TYPE = Express.中通.getExpressEngName();
	private static final String YD_TYPE = Express.韵达.getExpressEngName();
	private static final String EMS_TYPE = Express.EMS.getExpressEngName();

	/**
	 * 生成寄件的快递单号和快递公司
	 *
	 * @param postAddr 驿站地址
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 **/
	public static Map<String, String> getSendIdAndOrg(String postAddr) {
		Map<String, String> map = new HashMap<>();
		String id = "";
		String org = "";
		switch (postAddr) {
			case "中苑":
				id = PackIdUtil.generate(ZTO_TYPE);
				org = ZTO;
				break;
			case "西苑":
				id = PackIdUtil.generate(YD_TYPE);
				org = YD;
				break;
			case "北苑":
				id = PackIdUtil.generate(EMS_TYPE);
				org = EMS;
				break;
			default:
				break;
		}
		map.put("id", id);
		map.put("org", org);
		return map;
	}

	/**
	 * 抽取出来的传入驿站编号，返回寄件快递公司/种类方法
	 *
	 * @param card 驿站编号
	 * @return java.lang.String
	 **/
	public static String getSendOrg(String card) {
		String org = "";
		switch (card) {
			case "2101":
				org = ZTO;
				break;
			case "2102":
				org = YD;
				break;
			case "2103":
				org = EMS;
				break;
			default:
				break;
		}
		return org;
	}

}
