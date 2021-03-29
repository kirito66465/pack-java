package per.kirito.pack.pojo.utilpojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * @author kirito
 * @date 2021/3/28
 * @time 17:32
 * Echarts 数据
 */
@Data
@NonNull
@Builder
@AllArgsConstructor
public class EchartsVO implements Serializable {

	/**
	 * 饼图数据
	 */
	private List<EchartsDO> data;

	/**
	 * 折现图数据
	 */
	private List<Integer> count;

}
