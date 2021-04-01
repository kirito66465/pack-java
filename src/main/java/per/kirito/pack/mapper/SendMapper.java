package per.kirito.pack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import per.kirito.pack.pojo.Send;

/**
 * @author kirito
 * @date 2021/1/5
 * @time 11:14
 * Send 的 Mapper 层接口
 */
public interface SendMapper extends BaseMapper<Send> {

	/**
	 * 获取 User 的寄件集合
	 *
	 * @param page   分页
	 * @param card   学号
	 * @param org    快递公司
	 * @param status 寄件状态
	 * @param search 搜索
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<per.kirito.pack.pojo.Send>
	 */
	Page<Send> getSendByUser(IPage<Send> page, String card, String org, String status, String search);

	/**
	 * 获取 Admin 的寄件集合
	 *
	 * @param page   分页
	 * @param org    快递公司
	 * @param status 寄件状态
	 * @param search 搜索
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<per.kirito.pack.pojo.Send>
	 */
	Page<Send> getSendByAdmin(IPage<Send> page, String org, String status, String search);

	/**
	 * 传入状态，获取 User 的该状态寄件数量
	 *
	 * @param card   学号
	 * @param status 状态
	 * @return int
	 */
	int getUserTotal(@Param("card") String card, @Param("status") String status);

	/**
	 * 传入状态，获取 Admin 的该状态寄件数量
	 *
	 * @param org    快递公司
	 * @param status 状态
	 * @return int
	 */
	int getAdminTotal(@Param("org") String org, @Param("status") String status);

}
