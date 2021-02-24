package per.kirito.pack.myEnum;

/**
 * author: 严晨
 * date: 2020/12/23
 * time: 15:13
 * 快递名字枚举类
 */
public enum Express {

	// 中通：中文名/英文名
	中通("ZTO"),
	// 申通：中文名/英文名
	申通("STO"),
	// 圆通：中文名/英文名
	圆通("YTO"),
	// 京东：中文名/英文名
	京东("JD"),
	// 顺丰：中文名/英文名
	顺丰("SF"),
	// 韵达：中文名/英文名
	韵达("YD"),
	// 天天：中文名/英文名
	天天("TT"),
	// EMS：中文名/英文名
	EMS("EMS");

	private final String expressEngName;

	private Express(String expressEngName) {
		this.expressEngName = expressEngName;
	}

	public String getExpressEngName() {
		return expressEngName;
	}

}
