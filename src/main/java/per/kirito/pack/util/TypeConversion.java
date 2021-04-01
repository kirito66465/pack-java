package per.kirito.pack.util;

import per.kirito.pack.pojo.Echarts;
import per.kirito.pack.pojo.utilpojo.EchartsDO;
import per.kirito.pack.pojo.utilpojo.EchartsVO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author kirito
 * @date 2020/11/17
 * @time 11:02
 * 类型转换
 */
public class TypeConversion {

	/**
	 * 获取当前时间（yyyy-MM-dd HH:mm:ss）
	 *
	 * @return java.lang.String
	 */
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * MD5 加密字符串，不可逆
	 *
	 * @param plainText 加密内容
	 * @return java.lang.String
	 */
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
	 *
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
	 *
	 * @param statusStr 字符串
	 * @return java.lang.Integer[]
	 */
	public static Integer[] stringToIntegerArray(String statusStr) {
		if ("".equals(statusStr) || statusStr == null || "null".equals(statusStr)) {
			// 筛选框重置情况下，filter 值为 [] 即传入空字符串
			return new Integer[]{2};
		}
		String[] array = statusStr.split(",");
		Integer[] status = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			status[i] = Integer.valueOf(array[i]);
		}
		return status;
	}

	/**
	 * Echarts 数据库实体 -> 页面所需数据
	 * @param echarts   Echarts 实体
	 * @return per.kirito.pack.pojo.utilpojo.EchartsVO
	 */
	public static EchartsVO getData(Echarts echarts) {
		EchartsVO echartsVO;

		EchartsDO echartsDO;
		List<EchartsDO> data = new ArrayList<>();
		if (echarts.getNine() > 0) {
			echartsDO = new EchartsDO(echarts.getNine(), "9点");
			data.add(echartsDO);
		}
		if (echarts.getTen() > 0) {
			echartsDO = new EchartsDO(echarts.getTen(), "10点");
			data.add(echartsDO);
		}
		if (echarts.getEleven() > 0) {
			echartsDO = new EchartsDO(echarts.getEleven(), "11点");
			data.add(echartsDO);
		}
		if (echarts.getTwelve() > 0) {
			echartsDO = new EchartsDO(echarts.getTwelve(), "12点");
			data.add(echartsDO);
		}
		if (echarts.getThirteen() > 0) {
			echartsDO = new EchartsDO(echarts.getThirteen(), "13点");
			data.add(echartsDO);
		}
		if (echarts.getFourteen() > 0) {
			echartsDO = new EchartsDO(echarts.getFourteen(), "14点");
			data.add(echartsDO);
		}
		if (echarts.getFifteen() > 0) {
			echartsDO = new EchartsDO(echarts.getFifteen(), "15点");
			data.add(echartsDO);
		}
		if (echarts.getSixteen() > 0) {
			echartsDO = new EchartsDO(echarts.getSixteen(), "16点");
			data.add(echartsDO);
		}
		if (echarts.getSeventeen() > 0) {
			echartsDO = new EchartsDO(echarts.getSeventeen(), "17点");
			data.add(echartsDO);
		}
		if (echarts.getEighteen() > 0) {
			echartsDO = new EchartsDO(echarts.getEighteen(), "18点");
			data.add(echartsDO);
		}
		if (echarts.getNineteen() > 0) {
			echartsDO = new EchartsDO(echarts.getNineteen(), "19点");
			data.add(echartsDO);
		}
		if (data != null && data.size() == 0) {
			echartsDO = new EchartsDO(0, "暂无人员取件");
			data.add(echartsDO);
		}

		List<Integer> count = new ArrayList<>();
		count.add(echarts.getNine());
		count.add(echarts.getTen());
		count.add(echarts.getEleven());
		count.add(echarts.getTwelve());
		count.add(echarts.getThirteen());
		count.add(echarts.getFourteen());
		count.add(echarts.getFifteen());
		count.add(echarts.getSixteen());
		count.add(echarts.getSeventeen());
		count.add(echarts.getEighteen());
		count.add(echarts.getNineteen());

		echartsVO = new EchartsVO();
		echartsVO.setData(data);
		echartsVO.setCount(count);
		return echartsVO;
	}

}
