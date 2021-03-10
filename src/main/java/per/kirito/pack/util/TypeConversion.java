package per.kirito.pack.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/11/17 11:02
 * @description: 类型转换
 */
public class TypeConversion {

	/**
	 * 获取当前时间
	 * @return java.lang.String
	 **/
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * MD5 加密字符串，不可逆
	 * @param plainText 加密内容
	 * @return java.lang.String
	 **/
	public static String stringToMD5(String plainText) {
		byte[] secretBytes = null;
		int length = 32;
		String meta = "0";
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有这个md5算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < length - md5code.length(); i++) {
			md5code = meta + md5code;
		}
		return md5code;
	}

	/**
	 * ["中通","申通","圆通"] 转换为 中通,申通,圆通
	 * @param array json 数组
	 * @return java.lang.String
	 */
	public static String arrayToString(String array) {
		// 正则表达式
		String regEx = "[\n`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？\"]";
		String org = array.replaceAll(regEx, "");
		return org;
	}

	/**
	 * "-1,0,1" 转换为 [-1,0,1]
	 * 即字符串转为整型数组
	 * @param statusStr 字符串
	 * @return java.lang.Integer[]
	 */
	public static Integer[] stringToIntegerArray(String statusStr) {
		if ("".equals(statusStr) || statusStr == null || "null".equals(statusStr)) {
			// 筛选框重置情况下，filter 值为 [] 即传入空字符串
			return new Integer[] {2};
		}
		String[] array = statusStr.split(",");
		Integer[] status = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			status[i] = Integer.valueOf(array[i]);
		}
		return status;
	}

	public static void main(String[] args) throws ParseException {
		String time = TypeConversion.getTime();
		System.out.println("现在时间：" + time);
		System.out.println(stringToMD5("123456"));
		System.out.println(stringToMD5("admin"));
	}

}
