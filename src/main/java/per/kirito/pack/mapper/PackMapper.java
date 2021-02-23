package per.kirito.pack.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.Pack;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:24
 * @description: Pack 的 Mapper 接口
 */
@Repository
public interface PackMapper {

	/**
	 * @Description: 根据单号获取到该快递信息
	 * @Param: [id]
	 * @Return: per.kirito.pack.pojo.Pack
	 **/
	Pack getPackById(String id);

	/**
	 * @Description: 根据驿站地址和取件码获取到当前未取件但已有取件码的该快递信息
	 * @Param: [addr, code]
	 * @Return: per.kirito.pack.pojo.Pack
	 **/
	Pack getPackByAddrAndCode(@Param("addr") String addr, @Param("code") String code);

	/**
	 * @Description: 快递入站
	 * @Param: [pack]
	 * @Return: int
	 **/
	int addPack(Pack pack);

	/**
	 * @Description: 更新快递信息
	 * @Param: [pack]
	 * @Return: int
	 **/
	int updatePack(Pack pack);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 **/
	
	/**
	 * @Description: 根据包裹手机号获取对应用户所有快递数
	 * @Param: [phone]
	 * @Return: int
	 **/
	int getUserAllTotalNum(String phone);
	
	/**
	 * @Description: 根据包裹手机号获取对应用户已取快递数
	 * @Param: [phone]
	 * @Return: int
	 **/
	int getUserIsTotalNum(String phone);
	
	/**
	 * @Description: 根据包裹手机号获取对应用户未取快递数
	 * @Param: [phone]
	 * @Return: int
	 **/
	int getUserNoTotalNum(String phone);

	/**
	 * 根据 User 学号查询出该 User 所有快递集合
	 * @param card  学号
	 * @param org   快递公司
	 * @param addr  驿站地址
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getUserPacks(String card, String org, String addr);

	/**
	 * 根据 User 学号查询出该 User 已取快递集合
	 * @param card  学号
	 * @param org   快递公司
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getUserIsPick(String card, String org);

	/**
	 * 根据 User 学号查询出该 User 未取快递集合
	 * @param card  学号
	 * @param org   快递公司
	 * @param addr  驿站地址
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getUserNoPick(String card, String org, String addr);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin 相关
	 **/

	/**
	 * @Description: 根据驿站地址获取该驿站所有快递数
	 * @Param: [addr]
	 * @Return: int
	 **/
	int getAdminAllTotalNum(String addr);

	/**
	 * @Description: 根据驿站地址获取该驿站已取快递数
	 * @Param: [addr]
	 * @Return: int
	 **/
	int getAdminIsTotalNum(String addr);

	/**
	 * @Description: 根据驿站地址获取该驿站未取快递数
	 * @Param: [addr]
	 * @Return: int
	 **/
	int getAdminNoTotalNum(String addr);

	/**
	 * 根据 Admin 编号查询出所在驿站的所有快递集合
	 * @param card  Admin 编号
	 * @param org   快递公司
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getAdminPacks(String card, String org);

	/**
	 * 根据 Admin 编号查询出所在驿站已取快递集合
	 * @param card  Admin 编号
	 * @param org   快递公司
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getAdminIsPick(String card, String org);

	/**
	 * 根据 Admin 编号查询出所在驿站未取快递集合
	 * @param card  Admin 编号
	 * @param org   快递公司
	 * @return java.util.List<per.kirito.pack.pojo.Pack>
	 */
	List<Pack> getAdminNoPick(String card, String org);

	/**
	 * @Description: 根据驿站地址取出当前驿站未有取件码的快递中入站时间最早的快递
	 * @Param: [addr]
	 * @Return: per.kirito.pack.pojo.Pack
	 **/
	Pack getPackByStartMin(String addr);

	/**
	 * @Description: 根据驿站地址和货架取出当前货架的所有快递
	 * @Param: [card, shelf]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getShelfPack(@Param("card") String card, @Param("shelf") String shelf);

	/**
	 * @Description: 根据快递单号删除此快递
	 * @Param: [id]
	 * @Return: int
	 **/
	int deletePackById(String id);

	/**
	 * @Description: 根据快递单号查询出此快递状态
	 * @Param: [id]
	 * @Return: int
	 **/
	int getStatusById(String id);

}
