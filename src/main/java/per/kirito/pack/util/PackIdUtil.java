package per.kirito.pack.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 13:39
 * @description: 校园快递，单号规则仅供参考，不完全与实际相符！
 * 中苑：中通、申通、圆通
 * 西苑：京东、顺丰、韵达
 * 北苑：天天、EMS
 */
public class PackIdUtil {

	// 中通单号由14位数字组成，以7542开头，再生成10位数字
	private static final String ZTO = "7542";
	// 申通单号由15位数字组成，以77307开头，再生成10位数字
	private static final String STO = "77307";
	// 圆通单号由2位字母和13位数字组成，以YT开头，再生成13位数字
	private static final String YTO = "YT";

	// 京东单号由2位字母和13位数字组成，以JD002开头，再生成10位数字
	private static final String JD = "JD002";
	// 顺丰单号由2位字母和13位数字组成，以SF1开头，再生成12位数字
	private static final String SF = "SF1";
	// 韵达单号由13位数字组成，以43110开头，再生成8位数字
	private static final String YD = "43110";

	// 天天单号由14位数字组成，以888开头，再生成11位数字
	private static final String TT = "888";
	// EMS单号由4位字母和9位数字组成，以EA开头，以CN结尾，再生成9位数字
	private static final String EMS_BEGIN = "EA";
	private static final String EMS_END = "CN";

	// 状态码：有错误，生成失败
	private static final String STATUS_ERROR = "error";

	/**
	 * @Description: 根据当前日期获取前两位数字
	 * @Param: [date]
	 * @Return: java.lang.String
	 **/
	public static String getTwoId(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * @Description: 根据索引值截取13位时间戳以获取剩余数字
	 * @Param: [index]
	 * @Return: java.lang.String
	 **/
	public static String getLeftId(int index) {
		String timestamp = String.valueOf(System.currentTimeMillis());
		return timestamp.substring(index);
	}

	/**
	 * @Description: 传入快递类型生成取件码
	 * @Param: [type]
	 * @Return: java.lang.String
	 **/
	public static String generate(String type) {
		String UUID = "";
		Date date = new Date();
		String two = getTwoId(date);
		switch (type) {
			case "ZTO":
				UUID += ZTO + two + getLeftId(5);
				break;
			case "STO":
				UUID += STO + two + getLeftId(5);
				break;
			case "YTO":
				UUID += YTO + two + getLeftId(2);
				break;
			case "JD":
				UUID += JD + two + getLeftId(5);
				break;
			case "SF":
				UUID += SF + two + getLeftId(3);
				break;
			case "YD":
				UUID += YD + two + getLeftId(7);
				break;
			case "TT":
				UUID += TT + two + getLeftId(4);
				break;
			case "EMS":
				UUID += EMS_BEGIN + two + getLeftId(6) + EMS_END;
				break;
			default:
				UUID = STATUS_ERROR;
				break;
		}
		return UUID;
	}

	public static void main(String[] args) throws InterruptedException {
		// String zto = generate("ZTO");
		// String sto = generate("STO");
		// String yto = generate("YTO");
		// String jd = generate("JD");
		// String sf = generate("SF");
		// String yd = generate("YD");
		// String tt = generate("TT");
		// String ems = generate("EMS");
		// System.out.println("中通: " + zto);
		// System.out.println("申通: " + sto);
		// System.out.println("圆通: " + yto);
		// System.out.println("京东: " + jd);
		// System.out.println("顺丰: " + sf);
		// System.out.println("韵达: " + yd);
		// System.out.println("天天: " + tt);
		// System.out.println("EMS: " + ems);
		// System.out.println("-------------------------------------------------");
		for (int i = 0; i < 100; i++) {
			System.out.println(generate("EMS"));
			Thread.sleep(100);
		}
	}

}
