package per.kirito.pack.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import per.kirito.pack.annotation.Comment;

import java.io.Serializable;

/**
 * @author kirito
 * @date 2021/3/28
 * @time 17:07
 * Echarts 数据统计实体类
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "t_echarts")
public class Echarts implements Serializable {

	@Comment(msg = "ID")
	@TableId(value = "ID", type = IdType.AUTO)
	private Integer id;

	@Comment(msg = "日期")
	@TableField(value = "datee")
	private String datee;

	@Comment(msg = "编号")
	@TableField(value = "card")
	private String card;

	@Comment(msg = "9 点数据")
	@TableField(value = "nine")
	private Integer nine;

	@Comment(msg = "10 点数据")
	@TableField(value = "ten")
	private Integer ten;

	@Comment(msg = "11 点数据")
	@TableField(value = "eleven")
	private Integer eleven;

	@Comment(msg = "12 点数据")
	@TableField(value = "twelve")
	private Integer twelve;

	@Comment(msg = "13 点数据")
	@TableField(value = "thirteen")
	private Integer thirteen;

	@Comment(msg = "14 点数据")
	@TableField(value = "fourteen")
	private Integer fourteen;

	@Comment(msg = "15 点数据")
	@TableField(value = "fifteen")
	private Integer fifteen;

	@Comment(msg = "16 点数据")
	@TableField(value = "sixteen")
	private Integer sixteen;

	@Comment(msg = "17 点数据")
	@TableField(value = "seventeen")
	private Integer seventeen;

	@Comment(msg = "18 点数据")
	@TableField(value = "eighteen")
	private Integer eighteen;

	@Comment(msg = "19 点数据")
	@TableField(value = "nineteen")
	private Integer nineteen;

}
