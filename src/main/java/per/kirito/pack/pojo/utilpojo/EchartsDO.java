package per.kirito.pack.pojo.utilpojo;

import lombok.*;

import java.io.Serializable;

/**
 * @author kirito
 * @date 2021/3/28
 * @time 17:32
 * Echarts 饼图数据
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
public class EchartsDO implements Serializable {

	/*
	 * 时间点对应的数量
	 */
	private Integer value;

	/*
	 * 时间点
	 */
	private String name;

}
