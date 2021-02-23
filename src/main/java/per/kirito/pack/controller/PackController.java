package per.kirito.pack.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.service.inter.PackService;

import java.util.HashMap;
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
	 * User 进行取件请求，仅传入快递单号和 token
	 * @param id        快递单号
	 * @param token     令牌
	 * @return java.lang.String
	 */
	@CrossOrigin
	@RequestMapping(value = "/pickById")
	public String pickById(@RequestParam(value = "id") String id,
	                       @RequestParam(value = "token") String token) {
		return packService.pickById(id, token);
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
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserPackByPage")
	public Map<String, Object> getUserPackByPage(@RequestParam(value = "json") String json) {
		return packService.getUserPackByPage(json);
	}

	/**
	 * 分页获取 User 的已取出快递；如果没有 token 令牌，则返回获取信息失败
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserIsPick")
	public Map<String, Object> getUserIsPick(@RequestParam(value = "json") String json) {
		return packService.getUserIsPick(json);
	}

	/**
	 * 分页获取 User 的未取出快递，无论其有无取件码；如果没有 token 令牌，则返回获取信息失败
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getUserNoPick")
	public Map<String, Object> getUserNoPick(@RequestParam(value = "json") String json) {
		return packService.getUserNoPick(json);
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
	 * 分页获取 Admin 所有的快递，包括已取出和未取出的快递；如果没有 token 令牌，则返回获取信息失败
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminPacksByPage")
	public Map<String, Object> getAdminPackByPage(@RequestParam(value = "json") String json) {
		return packService.getAdminPackByPage(json);
	}

	/**
	 * 分页获取 Admin 的已取出快递；如果没有 token 令牌，则返回获取信息失败
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminIsPick")
	public Map<String, Object> getAdminIsPick(@RequestParam(value = "json") String json) {
		return packService.getAdminIsPick(json);
	}

	/**
	 * 分页获取 Admin 的未取出快递，无论其有无取件码；如果没有 token 令牌，则返回获取信息失败
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getAdminNoPick")
	public Map<String, Object> getAdminNoPick(@RequestParam(value = "json") String json) {
		return packService.getAdminNoPick(json);
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
