package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.service.inter.PackService;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:19
 * @description: Pack 的 Controller 层
 */
@RestController
@RequestMapping(value = "/pack")
public class PackController {

	@Autowired
	private PackService packService;

	/**
	 * 驿站管理员添加快递入站
	 * @param id        快递单号
	 * @param token     令牌
	 * @return java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/addPack")
	public String addPack(@RequestParam(value = "id") String id,
	                      @RequestParam(value = "token") String token) {
		return packService.addPack(id, token);
	}

	/**
	 * User 进行取件请求，必须传入驿站地址和取件码
	 * @param addr      驿站地址
	 * @param code      取件码
	 * @param token     令牌
	 * @return java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/pickPackByUser")
	public String pickPackByUser(@RequestParam(value = "addr") String addr,
	                             @RequestParam(value = "code") String code,
	                             @RequestParam(value = "token") String token) {
		return packService.pickPackByUser(addr, code, token);
	}

	/**
	 * Admin 进行取件请求，仅传入快递单号即可
	 * @param id        快递单号
	 * @param token     令牌
	 * @return java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/pickPackByAdmin")
	public String pickPackByAdmin(@RequestParam(value = "id") String id,
	                              @RequestParam(value = "token") String token) {
		return packService.pickPackByAdmin(id, token);
	}

	/**
	 * 根据快递单号删除此快递
	 * @param id        快递单号
	 * @param token     令牌
	 * @return java.lang.String
	 **/
	@CrossOrigin
	@RequestMapping(value = "/deletePack")
	public String deletePack(@RequestParam(value = "id") String id,
	                         @RequestParam(value = "token") String token) {
		return packService.deletePackById(id, token);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 **/

	/**
	 * 分页获取 User 所有的快递，包括已取出和未取出的快递；如果没有 token 令牌，则返回获取信息失败
	 * @param currentPage   当前页
	 * @param pageSize      每页大小
	 * @param token         令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserPackByPage/{currentPage}")
	public Map<String, Object> getUserPackByPage(@PathVariable int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize,
	                                          @RequestParam(value = "token") String token) {
		return packService.getUserPackByPage(currentPage, pageSize, token);
	}

	/**
	 * 分页获取 User 的已取出快递；如果没有 token 令牌，则返回获取信息失败
	 * @param currentPage   当前页
	 * @param pageSize      每页大小
	 * @param token         令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserIsPick/{currentPage}")
	public Map<String, Object> getUserIsPick(@PathVariable int currentPage,
	                                         @RequestParam(value = "pageSize") int pageSize,
	                                         @RequestParam(value = "token") String token) {
		return packService.getUserIsPick(currentPage, pageSize, token);
	}

	/**
	 * 分页获取 User 的未取出快递，无论其有无取件码；如果没有 token 令牌，则返回获取信息失败
	 * @param currentPage   当前页
	 * @param pageSize      每页大小
	 * @param token         令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserNoPick/{currentPage}")
	public Map<String, Object> getUserNoPick(@PathVariable int currentPage,
	                                         @RequestParam(value = "pageSize") int pageSize,
	                                         @RequestParam(value = "token") String token) {
		return packService.getUserNoPick(currentPage, pageSize, token);
	}

	/**
	 * 获取 User 所有快递总数、已取快递数量、未取快递数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserTotalNum")
	public Map<String, Object> getUserTotalNum(@RequestParam(value = "token") String token) {
		return packService.getUserTotalNum(token);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin 相关
	 **/

	/**
	 * 分页获取 Admin 所有的快递，包括已取出和未取出的快递；如果没有 token令 牌，则返回获取信息失败
	 * @param currentPage   当前页
	 * @param pageSize      每页大小
	 * @param token         令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminPacksByPage/{currentPage}")
	public Map<String, Object> getAdminPackByPage(@PathVariable int currentPage,
	                                              @RequestParam(value = "pageSize") int pageSize,
	                                              @RequestParam(value = "token") String token) {
		return packService.getAdminPackByPage(currentPage, pageSize, token);
	}

	/**
	 * 分页获取 Admin 的已取出快递；如果没有 token 令牌，则返回获取信息失败
	 * @param currentPage   当前页
	 * @param pageSize      每页大小
	 * @param token         令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminIsPick/{currentPage}")
	public Map<String, Object> getAdminIsPick(@PathVariable int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize,
	                                          @RequestParam(value = "token") String token) {
		return packService.getAdminIsPick(currentPage, pageSize, token);
	}

	/**
	 * 分页获取 Admin 的未取出快递，无论其有无取件码；如果没有 token 令牌，则返回获取信息失败
	 * @param currentPage   当前页
	 * @param pageSize      每页大小
	 * @param token         令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminNoPick/{currentPage}")
	public Map<String, Object> getAdminNoPick(@PathVariable int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize,
	                                          @RequestParam(value = "token") String token) {
		return packService.getAdminNoPick(currentPage, pageSize, token);
	}

	/**
	 * 获取 Admin 所有快递总数、已取快递数量、未取快递数量
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminTotalNum")
	public Map<String, Object> getAdminTotalNum(@RequestParam(value = "token") String token) {
		return packService.getAdminTotalNum(token);
	}

	/**
	 * 根据驿站地址和货架取出当前货架的所有快递
	 * @param token 令牌
	 * @param shelf 货架
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getShelfPack")
	public Map<String, Object> getShelfPack(@RequestParam(value = "token") String token,
	                                        @RequestParam(value = "shelf") String shelf) {
		return packService.getShelfPack(token, shelf);
	}

}
