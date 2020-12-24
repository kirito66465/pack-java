package per.kirito.pack.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/11/17 11:02
 * @description:
 */
public class TypeConversion {
	/**
	 * @Description: 获取当前时间
	 * @Param: []
	 * @Return: java.lang.String
	 **/
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * @Description: 采用Base64加密
	 * @Param: [input]
	 * @Return: java.lang.String
	 **/
	public static String base64Encode(byte[] input) {
		String result = null;
		try {
			Class className = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method method = className.getMethod("encode", byte[].class);
			result = (String) method.invoke(null, input);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @Description: 采用Base64解密
	 * @Param: [input]
	 * @Return: byte[]
	 **/
	public static byte[] base64Decode(String input) {
		byte[] result = null;
		try {
			Class className = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method method = className.getMethod("decode", String.class);
			result = (byte[]) method.invoke(null, input);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) throws ParseException {
		String time = TypeConversion.getTime();
		System.out.println("现在时间：" + time);
		System.out.println(base64Encode("123456".getBytes()));
		System.out.println(base64Encode("admin".getBytes()));
	}
}
