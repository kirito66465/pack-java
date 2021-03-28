package per.kirito.pack.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Table;

/**
 * author: kirito
 * date: 2021/3/28
 * time: 17:07
 * Echarts 数据统计实体类
 */
@Data
@NonNull
@Builder
@AllArgsConstructor
@Table(name = "t_echarts")
public class Echarts {

	/**
	 * 快递单号
	 */
	private String datee;

	/**
	 * 快递单号
	 */
	private String card;

	/**
	 * 9 点数据
	 */
	private Integer nine;

	/**
	 * 10 点数据
	 */
	private Integer ten;

	/**
	 * 11 点数据
	 */
	private Integer eleven;

	/**
	 * 12 点数据
	 */
	private Integer twelve;

	/**
	 * 13 点数据
	 */
	private Integer thirteen;

	/**
	 * 14 点数据
	 */
	private Integer fourteen;

	/**
	 * 15 点数据
	 */
	private Integer fifteen;

	/**
	 * 16 点数据
	 */
	private Integer sixteen;

	/**
	 * 17 点数据
	 */
	private Integer seventeen;

	/**
	 * 18 点数据
	 */
	private Integer eighteen;

	/**
	 * 19 点数据
	 */
	private Integer nineteen;

}
