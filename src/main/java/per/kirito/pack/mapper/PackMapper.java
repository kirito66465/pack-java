package per.kirito.pack.mapper;

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
	 * @Description: 根据手机号查找出手机号对应User的所有快递，包括已取出和未取出的快递
	 * @Param: [tel]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getPackSByPhone(String tel);

	/**
	 * @Description: 根据驿站地址查找出该驿站的所有快递，包括已取出和未取出的快递
	 * @Param: [addr]
	 * @Return: java.util.List<per.kirito.pack.pojo.Pack>
	 **/
	List<Pack> getPacksByAddr(String addr);

	/**
	 * @Description: 根据驿站地址获取该驿站的快递总数，包括已取出和未取出的快递
	 * @Param: [addr]
	 * @Return: int
	 **/
	int getTotalByAddr(String addr);

	/**
	 * @Description: 根据手机号查找出手机号对应User的快递总数，包括已取出和未取出的快递
	 * @Param: [tel]
	 * @Return: int
	 **/
	int getTotalByTel(String tel);

	// TODO 待完成Mapper.xml

	int getUserHaveCount(String tel, int status);

	List<Pack> getUserHavePacks(String tel, int status);

	int getUserNotCount(String tel, int status);

	List<Pack> getUserNotPacks(String tel, int status);

	int getAdminHaveCount(String tel, int status);

	List<Pack> getAdminHavePacks(String tel, int status);

	int getAdminNotCount(String tel, int status);

	List<Pack> getAdminNotPacks(String tel, int status);

}
