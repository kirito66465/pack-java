package per.kirito.pack.util;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 16:12
 * @description: 取件码工具类，从 1-1-1 到 20-6-20 一共2400件快递有取件码
 */
public class PickCodeUtil {

	// 每个驿站有20个货架，每个货架有6层，每层能放20个快递，每个驿站最多有2400件快递有取件码，每个货架最多能放120件快递
	private static final int MAX_1 = 20;
	private static final int MAX_2 = 6;
	private static final int MAX_3 = 20;
	private static final int MAX_PACKS = 2400;
	private static final int MAX_SHELF = 120;

	/**
	 * 根据驿站已有包裹数生成取件码
	 * @param count 快递入站前，驿站未取快递总数
	 * @return java.lang.String
	 **/
	public static String generate(int count) {
		// 新入站的快递还未计算在count中
		int all = count + 1;
		if (count >= MAX_PACKS) {       // 如果货架已满，则不生成取件码
			return null;
		}
		// 该摆放的货架快递数量，假设all为1253, 那么取件码应为11-3-13
		int have = all % MAX_SHELF;           // 那么该摆放的这个货架有53件快递
		int code1;
		int code2;
		int code3;
		if (all % MAX_SHELF == 0) {
			code1 = all / MAX_SHELF;
			code2 = MAX_2;
			code3 = MAX_3;
		} else {
			have = all % MAX_SHELF;
			code1 = all / MAX_SHELF + 1;
			if (have % 20 == 0) {
				code2 = have / MAX_3;
				code3 = MAX_3;
			} else {
				code2 = have / MAX_3 + 1;
				code3 = have % MAX_3;
			}
		}
		// 防止生成的第一位取件码大于20的异常
		if (code1 > MAX_3) {
			return null;
		} else {
			if (code3 < 10) {
				return String.valueOf(code1 + "-" + code2 + "-0" + code3);
			} else {
				return String.valueOf(code1 + "-" + code2 + "-" + code3);
			}
		}
	}

	public static void main(String[] args) {
		// String code = generate(19);
		// System.out.println(code);
		for (int i = 0; i < 140; i++) {
			System.out.println(generate(i));
		}
	}

}
