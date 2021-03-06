package per.kirito.pack.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import per.kirito.pack.pojo.Pack;

import java.util.List;

/**
 * @author kirito
 * @date 2020/12/23
 * @time 15:24
 * Pack 的 Mapper 接口
 */
public interface PackMapper extends BaseMapper<Pack> {

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 */

	/**
	 * 根据包裹手机号获取对应用户所有快递数
	 *
	 * @param phone 快递收件人手机号
	 * @return int
	 */
	int getUserAllTotalNum(String phone);

	/**
	 * 根据包裹手机号获取对应用户已取快递数
	 *
	 * @param phone 快递收件人手机号
	 * @return int
	 */
	int getUserIsTotalNum(String phone);

	/**
	 * 根据包裹手机号获取对应用户未取快递数
	 *
	 * @param phone 快递收件人手机号
	 * @return int
	 */
	int getUserNoTotalNum(String phone);

	/**
	 * 根据 User 学号查询出该 User 所有快递集合
	 *
	 * @param page   分页
	 * @param card   学号
	 * @param org    快递公司
	 * @param addr   驿站地址
	 * @param status 快递状态
	 * @param search 搜索
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<per.kirito.pack.pojo.Pack>
	 */
	Page<Pack> getUserPacks(IPage<Pack> page, String card, String org, String addr, Integer[] status, String search);

	/**
	 * 根据 User 学号查询出该 User 已取快递集合
	 *
	 * @param page   分页
	 * @param card   学号
	 * @param org    快递公司
	 * @param search 搜索
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<per.kirito.pack.pojo.Pack>
	 */
	Page<Pack> getUserIsPick(IPage<Pack> page, String card, String org, String search);

	/**
	 * 根据 User 学号查询出该 User 未取快递集合
	 *
	 * @param page   分页
	 * @param card   学号
	 * @param org    快递公司
	 * @param addr   驿站地址
	 * @param status 快递状态
	 * @param search 搜索
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<per.kirito.pack.pojo.Pack>
	 */
	Page<Pack> getUserNoPick(IPage<Pack> page, String card, String org, String addr, Integer[] status, String search);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin 相关
	 */

	/**
	 * 根据驿站地址获取该驿站所有快递数
	 *
	 * @param addr 驿站地址
	 * @return int
	 */
	int getAdminAllTotalNum(String addr);

	/**
	 * 根据驿站地址获取该驿站已取快递数
	 *
	 * @param addr 驿站地址
	 * @return int
	 */
	int getAdminIsTotalNum(String addr);

	/**
	 * 根据驿站地址获取该驿站未取快递数
	 *
	 * @param addr 驿站地址
	 * @return int
	 */
	int getAdminNoTotalNum(String addr);

	/**
	 * 根据 Admin 编号查询出所在驿站的所有快递集合
	 *
	 * @param page   分页
	 * @param card   Admin 编号
	 * @param org    快递公司
	 * @param status 快递状态
	 * @param search 搜索
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<per.kirito.pack.pojo.Pack>
	 */
	Page<Pack> getAdminPacks(IPage<Pack> page, String card, String org, Integer[] status, String search);

	/**
	 * 根据 Admin 编号查询出所在驿站已取快递集合
	 *
	 * @param page   分页
	 * @param card   Admin 编号
	 * @param org    快递公司
	 * @param search 搜索
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<per.kirito.pack.pojo.Pack>
	 */
	Page<Pack> getAdminIsPick(IPage<Pack> page, String card, String org, String search);

	/**
	 * 根据 Admin 编号查询出所在驿站未取快递集合
	 *
	 * @param page   分页
	 * @param card   Admin 编号
	 * @param org    快递公司
	 * @param status 快递状态
	 * @param search 搜索
	 * @return com.baomidou.mybatisplus.extension.plugins.pagination.Page<per.kirito.pack.pojo.Pack>
	 */
	Page<Pack> getAdminNoPick(IPage<Pack> page, String card, String org, Integer[] status, String search);

	/**
	 * 根据驿站地址取出当前驿站未有取件码的快递中入站时间最早的快递
	 *
	 * @param addr 驿站地址
	 * @return per.kirito.pack.pojo.Pack
	 */
	Pack getPackByStartMin(String addr);

	/**
	 * 根据驿站地址和货架获取当前货架的所有快递
	 *
	 * @param card  编号
	 * @param shelf 货架
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getShelfPack(@Param("card") String card, @Param("shelf") String shelf);

	/**
	 * 获取不筛选不分页的驿站所有快递集合
	 *
	 * @param card 编号
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getAllPacksByExcelOfAdmin(@Param("card") String card);

	/**
	 * 获取不筛选不分页的驿站已取快递集合
	 *
	 * @param card 编号
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getIsPacksByExcelOfAdmin(@Param("card") String card);

	/**
	 * 获取不筛选不分页的驿站未取快递集合
	 *
	 * @param card 编号
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getNoPacksByExcelOfAdmin(@Param("card") String card);

}
