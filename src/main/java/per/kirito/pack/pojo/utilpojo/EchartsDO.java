package per.kirito.pack.pojo.utilpojo;

import lombok.*;
import per.kirito.pack.annotation.Comment;

import java.io.Serializable;

/**
 * @author kirito
 * @date 2021/3/28
 * @time 17:32
 * Echarts 饼图数据
 */
@Data
@NonNull
@AllArgsConstructor
public class EchartsDO implements Serializable {

	@Comment(msg = "时间点对应的数量")
	private Integer value;

	@Comment(msg = "时间点")
	private String name;

}
