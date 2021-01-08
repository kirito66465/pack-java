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
 * @description: Pack的Mapper接口
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
	 * User相关
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
	 * @Description: 根据User学号查询出该User所有快递集合
	 * @Param: [card]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getUserPacks(String card);

	/**
	 * @Description: 根据User学号查询出该User已取快递集合
	 * @Param: [card]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getUserIsPick(String card);

	/**
	 * @Description: 根据User学号查询出该User未取快递集合
	 * @Param: [card]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getUserNoPick(String card);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
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
	 * @Description: 根据Admin编号查询出所在驿站的快递集合
	 * @Param: [card]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getAdminPacks(String card);

	/**
	 * @Description: 根据Admin编号查询出所在驿站已取快递集合
	 * @Param: [card]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getAdminIsPick(String card);

	/**
	 * @Description: 根据Admin编号查询出所在驿站未取快递集合
	 * @Param: [card]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getAdminNoPick(String card);

	/**
	 * @Description: 根据驿站地址取出当前驿站未有取件码的快递中入站时间最早的快递
	 * @Param: [addr]
	 * @Return: per.kirito.pack.pojo.Pack
	 **/
	Pack getPackByStartMin(String addr);

}
