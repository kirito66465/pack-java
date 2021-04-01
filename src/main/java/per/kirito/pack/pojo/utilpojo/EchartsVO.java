package per.kirito.pack.pojo.utilpojo;

import lombok.*;
import per.kirito.pack.annotation.Comment;

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
@NoArgsConstructor
public class EchartsVO implements Serializable {

	@Comment(msg = "饼图数据")
	private List<EchartsDO> data;

	@Comment(msg = "折现图数据")
	private List<Integer> count;

}
