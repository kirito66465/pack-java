package per.kirito.pack.mapper;

import per.kirito.pack.pojo.Echarts;

/**
 * @author kirito
 * @date 2021/3/28
 * @time 17:12
 * Echarts 的 Mapper 层接口
 */
public interface EchartsMapper {

	/**
	 * 获取数据
	 * @param datee     日期
	 * @param card      编号
	 * @return per.kirito.pack.pojo.Echarts
	 */
	Echarts getData(String datee, String card);

	/**
	 * 更新数据
	 * @param echarts   数据对象
	 */
	void updateData(Echarts echarts);

	/**
	 * 新增初始化数据
	 * @param datee     日期
	 * @param card      编号
	 */
	void initData(String datee, String card);

}
