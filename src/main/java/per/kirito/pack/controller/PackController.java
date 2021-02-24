package per.kirito.pack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.service.inter.PackService;

import java.util.Map;

/**
 * author: 严晨
 * date: 2020/12/23
 * time: 15:19
 * Pack 的 Controller 层
 */
@Api(tags = {"快递管理"}, description = "快递管理")
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
	@ApiOperation(value = "快递入站")
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
	@ApiOperation(value = "学生取件（快递单号 + token）")
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
	@ApiOperation(value = "学生取件（驿站地址 + 取件码）")
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
	@ApiOperation(value = "管理员取件")
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
	@ApiOperation(value = "删除快递")
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
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址, status:快递状态}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ApiOperation(value = "学生所有快递集")
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
	@ApiOperation(value = "学生已取快递集")
	@CrossOrigin
	@RequestMapping(value = "/getUserIsPick")
	public Map<String, Object> getUserIsPick(@RequestParam(value = "json") String json) {
		return packService.getUserIsPick(json);
	}

	/**
	 * 分页获取 User 的未取出快递，无论其有无取件码；如果没有 token 令牌，则返回获取信息失败
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址, status:快递状态}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ApiOperation(value = "学生未取快递集")
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
	@ApiOperation(value = "学生快递数量")
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
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:快递状态}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ApiOperation(value = "驿站所有快递集")
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
	@ApiOperation(value = "驿站已取快递集")
	@CrossOrigin
	@RequestMapping(value = "/getAdminIsPick")
	public Map<String, Object> getAdminIsPick(@RequestParam(value = "json") String json) {
		return packService.getAdminIsPick(json);
	}

	/**
	 * 分页获取 Admin 的未取出快递，无论其有无取件码；如果没有 token 令牌，则返回获取信息失败
	 * @param json  参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:快递状态}
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ApiOperation(value = "驿站未取快递集")
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
	@ApiOperation(value = "驿站快递数量")
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
	@ApiOperation(value = "驿站货架快递集")
	@CrossOrigin
	@RequestMapping(value = "/getShelfPack")
	public Map<String, Object> getShelfPack(@RequestParam(value = "token") String token,
	                                        @RequestParam(value = "shelf") String shelf) {
		return packService.getShelfPack(token, shelf);
	}

}
