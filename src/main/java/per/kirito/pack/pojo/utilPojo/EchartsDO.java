package per.kirito.pack.pojo.utilPojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * author: kirito
 * date: 2021/3/28
 * time: 17:32
 * Echarts 饼图数据
 */
@Data
@NonNull
@Builder
@AllArgsConstructor
public class EchartsDO {

	/*
	 * 时间点对应的数量
	 */
	private Integer value;

	/*
	 * 时间点
	 */
	private String name;

}
