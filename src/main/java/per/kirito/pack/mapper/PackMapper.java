package per.kirito.pack.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.Pack;

import java.util.List;

/**
 * author: 严晨
 * date: 2020/12/23
 * time: 15:24
 * Pack 的 Mapper 接口
 */
@Repository
public interface PackMapper {

	/**
	 * 根据单号获取到该快递信息
	 * @param id    快递单号
	 * @return per.kirito.pack.pojo.Pack
	 */
	Pack getPackById(String id);

	/**
	 * 根据驿站地址和取件码获取到当前未取件但已有取件码的该快递信息
	 * @param addr  驿站地址
	 * @param code  取件码
	 * @return per.kirito.pack.pojo.Pack
	 */
	Pack getPackByAddrAndCode(@Param("addr") String addr, @Param("code") String code);

	/**
	 * 快递入站
	 * @param pack  快递实体
	 * @return int
	 */
	int addPack(Pack pack);

	/**
	 * 更新快递信息
	 * @param pack  快递实体
	 * @return int
	 */
	int updatePack(Pack pack);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 **/
	
	/**
	 * 根据包裹手机号获取对应用户所有快递数
	 * @param phone 快递收件人手机号
	 * @return int
	 */
	int getUserAllTotalNum(String phone);

	/**
	 * 根据包裹手机号获取对应用户已取快递数
	 * @param phone 快递收件人手机号
	 * @return int
	 */
	int getUserIsTotalNum(String phone);
	
	/**
	 * 根据包裹手机号获取对应用户未取快递数
	 * @param phone 快递收件人手机号
	 * @return int
	 */
	int getUserNoTotalNum(String phone);

	/**
	 * 根据 User 学号查询出该 User 所有快递集合
	 * @param card      学号
	 * @param org       快递公司
	 * @param addr      驿站地址
	 * @param status    快递状态
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getUserPacks(String card, String org, String addr, Integer[] status);

	/**
	 * 根据 User 学号查询出该 User 已取快递集合
	 * @param card  学号
	 * @param org   快递公司
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getUserIsPick(String card, String org);

	/**
	 * 根据 User 学号查询出该 User 未取快递集合
	 * @param card      学号
	 * @param org       快递公司
	 * @param addr      驿站地址
	 * @param status    快递状态
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getUserNoPick(String card, String org, String addr, Integer[] status);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin 相关
	 **/

	/**
	 * 根据驿站地址获取该驿站所有快递数
	 * @param addr  驿站地址
	 * @return int
	 */
	int getAdminAllTotalNum(String addr);

	/**
	 * 根据驿站地址获取该驿站已取快递数
	 * @param addr  驿站地址
	 * @return int
	 */
	int getAdminIsTotalNum(String addr);

	/**
	 * 根据驿站地址获取该驿站未取快递数
	 * @param addr  驿站地址
	 * @return int
	 */
	int getAdminNoTotalNum(String addr);

	/**
	 * 根据 Admin 编号查询出所在驿站的所有快递集合
	 * @param card      Admin 编号
	 * @param org       快递公司
	 * @param status    快递状态
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getAdminPacks(String card, String org, Integer[] status);

	/**
	 * 根据 Admin 编号查询出所在驿站已取快递集合
	 * @param card  Admin 编号
	 * @param org   快递公司
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getAdminIsPick(String card, String org);

	/**
	 * 根据 Admin 编号查询出所在驿站未取快递集合
	 * @param card      Admin 编号
	 * @param org       快递公司
	 * @param status    快递状态
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getAdminNoPick(String card, String org, Integer[] status);

	/**
	 * 根据驿站地址取出当前驿站未有取件码的快递中入站时间最早的快递
	 * @param addr  驿站地址
	 * @return per.kirito.pack.pojo.Pack
	 */
	Pack getPackByStartMin(String addr);

	/**
	 * 根据驿站地址和货架获取当前货架的所有快递
	 * @param card  编号
	 * @param shelf 货架
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getShelfPack(@Param("card") String card, @Param("shelf") String shelf);

	/**
	 * 根据快递单号删除此快递
	 * @param id    快递单号
	 * @return int
	 */
	int deletePackById(String id);

	/**
	 * 根据快递单号查询出此快递状态
	 * @param id    快递单号
	 * @return int
	 */
	int getStatusById(String id);

}
