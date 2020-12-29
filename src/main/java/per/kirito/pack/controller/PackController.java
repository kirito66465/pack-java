package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.utilPojo.PackResult;
import per.kirito.pack.pojo.utilPojo.Page;
import per.kirito.pack.service.inter.PackService;

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

	/**
	 * @Description: 根据快递单号获取快递信息       TODO: 可能弃用
	 * @Param: [id]
	 * @Return: java.lang.Object
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getPackById")
	public Object getPackById(@RequestParam(value = "id") String id) {
		return packService.getPackById(id);
	}

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
	 * @Description: User或Admin进行取件请求
	 * @Param: [id, code]
	 * @Return: java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/pickPack")
	public String pickPack(@RequestParam(value = "id") String id,
	                       @RequestParam(value = "code") String code) {
		return packService.pickPack(id, code);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: 分页获取User所有的快递，包括已取出和未取出的快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserPackByPage")
	public Page<PackResult> getUserPackByPage(@RequestParam(defaultValue = "1") int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize) {
		return packService.getUserPackByPage(currentPage, pageSize);
	}

	// 分页获取User的已取出快递

	// 分页获取User的未取出快递，无论其有无取件码

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * @Description: 分页获取Admin所有的快递，包括已取出和未取出的快递
	 * @Param: [currentPage, pageSize]
	 * @Return: per.kirito.pack.pojo.utilPojo.Page<per.kirito.pack.pojo.utilPojo.PackResult>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminPacksByPage")
	public Page<PackResult> getAdminPackByPage(@RequestParam(defaultValue = "1") int currentPage,
	                                              @RequestParam(value = "pageSize") int pageSize) {
		return packService.getAdminPackByPage(currentPage, pageSize);
	}

	// 分页获取Admin的已取出快递

	// 分页获取Admin的未取出快递，无论其有无取件码

}
