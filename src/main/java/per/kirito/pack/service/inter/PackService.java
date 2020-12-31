package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.Pack;
import per.kirito.pack.pojo.utilPojo.PackResult;
import per.kirito.pack.pojo.utilPojo.Page;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:35
 * @description: Pack的Service层接口
 */
public interface PackService {

	/**
	 * @Description: 根据快递单号获取快递信息，如果查询不出则返回String
	 * @Param: [id]
	 * @Return: java.lang.Object
	 **/
	Object getPackById(String id);

	/**
	 * @Description: 驿站管理员添加快递入站
	 * @Param: [id]
	 * @Return: java.lang.String
	 **/
	String addPack(String id);

	/**
	 * @Description: User或Admin进行取件
	 * @Param: [id, code]
	 * @Return: java.lang.String
	 **/
	String pickPack(String id, String code);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: 分页获取User所有的快递，包括已取出和未取出的快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	Page<PackResult> getUserPackByPage(int currentPage, int pageSize, String token);

	/**
	 * @Description: 分页获取User已取出的快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	Page<PackResult> getUserIsPick(int currentPage, int pageSize, String token);

	/**
	 * @Description: 分页获取User所未取出的快递， 无论有无取件码
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	Page<PackResult> getUserNoPick(int currentPage, int pageSize, String token);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * @Description: 分页获取Admin所有的快递，包括已取出和未取出的快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	Page<PackResult> getAdminPackByPage(int currentPage, int pageSize, String token);

	/**
	 * @Description: 分页获取当前驿站的已取出快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	Page<PackResult> getAdminIsPick(int currentPage, int pageSize, String token);

	/**
	 * @Description: 分页获取当前驿站的未取出快递，无论有无取件码
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	Page<PackResult> getAdminNoPick(int currentPage, int pageSize, String token);

}
