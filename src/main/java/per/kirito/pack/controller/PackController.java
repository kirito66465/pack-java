package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.utilPojo.PackResult;
import per.kirito.pack.pojo.utilPojo.Page;
import per.kirito.pack.service.inter.PackService;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:19
 * @description: Pack的Controller层
 */
@RestController
@RequestMapping(value = "/pack")
public class PackController {

	@Autowired
	private PackService packService;

	// /**
	//  * @Description: 根据快递单号获取快递信息
	//  * @Param: [id]
	//  * @Return: java.lang.Object
	//  **/
	// @CrossOrigin
	// @RequestMapping(value = "/getPackById")
	// public Object getPackById(@RequestParam(value = "id") String id) {
	// 	return packService.getPackById(id);
	// }

	/**
	 * @Description: 驿站管理员添加快递入站
	 * @Param: [id]
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/addPack")
	public String addPack(@RequestParam(value = "id") String id) {
		return packService.addPack(id);
	}

	/**
	 * @Description: User进行取件请求，必须传入驿站地址和取件码
	 * @Param: [id, code]
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/pickPackByUser")
	public String pickPackByUser(@RequestParam(value = "addr") String addr,
	                             @RequestParam(value = "code") String code,
	                             @RequestParam(value = "token") String token) {
		return packService.pickPackByUser(addr, code, token);
	}
	
	/**
	 * @Description: Admin进行取件请求，仅传入快递单号即可
	 * @Param: [id]
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/pickPackByAdmin")
	public String pickPackByAdmin(@RequestParam(value = "id") String id) {
		return packService.pickPackByAdmin(id);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: 分页获取User所有的快递，包括已取出和未取出的快递；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserPackByPage/{currentPage}")
	public Map<String, Object> getUserPackByPage(@PathVariable int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize,
	                                          @RequestParam(value = "token") String token) {
		return packService.getUserPackByPage(currentPage, pageSize, token);
	}

	/**
	 * @Description: 分页获取User的已取出快递；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserIsPick/{currentPage}")
	public Map<String, Object> getUserIsPick(@PathVariable int currentPage,
	                                         @RequestParam(value = "pageSize") int pageSize,
	                                         @RequestParam(value = "token") String token) {
		return packService.getUserIsPick(currentPage, pageSize, token);
	}

	/**
	 * @Description: 分页获取User的未取出快递，无论其有无取件码；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserNoPick/{currentPage}")
	public Map<String, Object> getUserNoPick(@PathVariable int currentPage,
	                                         @RequestParam(value = "pageSize") int pageSize,
	                                         @RequestParam(value = "token") String token) {
		return packService.getUserNoPick(currentPage, pageSize, token);
	}

	@CrossOrigin
	@RequestMapping(value = "/getUserTotalNum")
	public Map<String, Integer> getUserTotalNum(@RequestParam(value = "token") String token) {
		return packService.getUserTotalNum(token);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * @Description: 分页获取Admin所有的快递，包括已取出和未取出的快递；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminPacksByPage/{currentPage}")
	public Map<String, Object> getAdminPackByPage(@PathVariable int currentPage,
	                                              @RequestParam(value = "pageSize") int pageSize,
	                                              @RequestParam(value = "token") String token) {
		return packService.getAdminPackByPage(currentPage, pageSize, token);
	}

	/**
	 * @Description: 分页获取Admin的已取出快递；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminIsPick/{currentPage}")
	public Map<String, Object> getAdminIsPick(@PathVariable int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize,
	                                          @RequestParam(value = "token") String token) {
		return packService.getAdminIsPick(currentPage, pageSize, token);
	}

	/**
	 * @Description: 分页获取Admin的未取出快递，无论其有无取件码；如果没有token令牌，则返回获取信息失败
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminNoPick/{currentPage}")
	public Map<String, Object> getAdminNoPick(@PathVariable int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize,
	                                          @RequestParam(value = "token") String token) {
		return packService.getAdminNoPick(currentPage, pageSize, token);
	}

	@CrossOrigin
	@RequestMapping(value = "/getAdminTotalNum")
	public Map<String, Integer> getAdminTotalNum(@RequestParam(value = "token") String token) {
		return packService.getAdminTotalNum(token);
	}
}
