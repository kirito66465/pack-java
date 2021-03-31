package per.kirito.pack.myenum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * author: kirito
 * date: 2021/3/30
 * time: 16:42
 * 取件码状态枚举
 */
public enum PackStatusEnum {

	/** 未有取件码 */
	PACK_STATUS__1(-1, "未有取件码"),
	/** 已取出 */
	PACK_STATUS_0(0, "已取出"),
	/** 未取出 */
	PACK_STATUS_1(1, "未取出");

	@EnumValue
	private final Integer code;

	@JsonValue
	private final String desc;

	PackStatusEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return this.desc;
	}

}
