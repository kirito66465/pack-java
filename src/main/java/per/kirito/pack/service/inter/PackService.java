package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.Pack;
import per.kirito.pack.pojo.utilPojo.PackResult;
import per.kirito.pack.pojo.utilPojo.Page;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:35
 * @description: Pack的Service层接口
 */
public interface PackService {

	/**
	 * @Description: 驿站管理员添加快递入站
	 * @Param: [id]
	 * @Return: java.lang.String
	 **/
	String addPack(String id, String token);

	/**
	 * @Description: User进行取件，必须传入驿站地址和取件码
	 * @Param: [id, code]
	 * @Return: java.lang.String
	 **/
	String pickPackByUser(String addr, String code, String token);

	/**
	 * @Description: Admin进行取件，仅传入快递单号即可
	 * @Param: [id]
	 * @Return: java.lang.String
	 **/
	String pickPackByAdmin(String id);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: 分页获取User所有的快递，包括已取出和未取出的快递；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getUserPackByPage(int currentPage, int pageSize, String token);

	/**
	 * @Description: 分页获取User已取出的快递；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getUserIsPick(int currentPage, int pageSize, String token);

	/**
	 * @Description: 分页获取User所未取出的快递， 无论有无取件码；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getUserNoPick(int currentPage, int pageSize, String token);

	/**
	 * @Description: 获取User所有快递总数、已取快递数量、未取快递数量
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getUserTotalNum(String token);

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * @Description: 分页获取Admin所有的快递，包括已取出和未取出的快递；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getAdminPackByPage(int currentPage, int pageSize, String token);

	/**
	 * @Description: 分页获取当前驿站的已取出快递；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getAdminIsPick(int currentPage, int pageSize, String token);

	/**
	 * @Description: 分页获取当前驿站的未取出快递，无论有无取件码；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getAdminNoPick(int currentPage, int pageSize, String token);

	/**
	 * @Description: 获取Admin所有快递总数、已取快递数量、未取快递数量
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getAdminTotalNum(String token);

}
