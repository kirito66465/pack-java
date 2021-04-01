package per.kirito.pack.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.service.inter.PackService;
import per.kirito.pack.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author kirito
 * @date 2020/12/23
 * @time 15:19
 * Pack 的 Controller 层
 */
@Slf4j
@Api(tags = {"快递管理"}, produces = "application/json", consumes = "application/json")
// @CrossOrigin
@RestController
@RequestMapping(value = "/pack")
public class PackController {

	@Autowired
	private PackService packService;

	/**
	 * 驿站管理员添加快递入站
	 *
	 * @param id      快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 */
	@ApiOperation(value = "快递入站", notes = "驿站添加快递入站请求，返回入站成功与否", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/add-pack")
	public String addPack(
			@ApiParam(required = true, name = "id", value = "快递单号") @RequestParam(value = "id") String id,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/add-pack]；参数[id={}, token={}]", id, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.addPack(id, token);
	}

	/**
	 * User 进行取件请求，仅传入快递单号和 token
	 *
	 * @param ids     快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 */
	@ApiOperation(value = "学生取件（快递单号 + token）", notes = "学生根据快递单号进行取件请求，返回取件成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PutMapping(value = "/pick-by-id")
	public String pickById(
			@ApiParam(required = true, name = "ids", value = "快递单号") @RequestParam(value = "ids") String ids,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/pick-by-id]；参数[ids={}, token={}]", ids, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.pickById(ids, token);
	}

	/**
	 * User 进行取件请求，必须传入驿站地址和取件码
	 *
	 * @param addr    驿站地址
	 * @param code    取件码
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 */
	@ApiOperation(value = "学生取件（驿站地址 + 取件码）", notes = "学生根据驿站和取件码进行取件请求，返回取件成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PutMapping(value = "/pick-by-code")
	public String pickPackByUser(
			@ApiParam(required = true, name = "addr", value = "驿站地址") @RequestParam(value = "addr") String addr,
			@ApiParam(required = true, name = "code", value = "取件码") @RequestParam(value = "code") String code,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/pick-by-code]；参数[addr={}, code={}, token={}]", addr, code, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.pickPackByUser(addr, code, token);
	}

	/**
	 * Admin 进行取件请求，仅传入快递单号即可
	 *
	 * @param ids     快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 */
	@ApiOperation(value = "管理员取件", notes = "驿站根据快递单号进行取件请求，返回取件成功与否", httpMethod = "PUT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PutMapping(value = "/pick-by-admin")
	public String pickPackByAdmin(
			@ApiParam(required = true, name = "ids", value = "快递单号") @RequestParam(value = "ids") String ids,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/pick-by-admin]；参数[ids=" + ids + ", token=" + token + "]");
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.pickPackByAdmin(ids, token);
	}

	/**
	 * 根据快递单号删除此快递
	 *
	 * @param ids     快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.lang.String
	 */
	@ApiOperation(value = "删除快递", notes = "学生根据快递单号进行删除请求，返回删除成功与否", httpMethod = "DELETE")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@DeleteMapping(value = "/delete-pack")
	public String deletePacks(
			@ApiParam(required = true, name = "ids", value = "快递单号") @RequestParam(value = "ids") String ids,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/delete-pack]；参数[ids={}, token={}]", ids, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.deletePacksById(ids, token);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User 相关
	 */

	/**
	 * 分页获取 User 所有的快递，包括已取出和未取出的快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json    参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址, status:快递状态, search:搜索}
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "学生所有快递集", notes = "获取学生所有快递请求，如果获取成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/user-all-pack")
	public Map<String, Object> getUserPackByPage(
			@ApiParam(required = true, name = "json", value = "{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址, status:快递状态, search:搜索}")
			@RequestParam(value = "json") String json,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/user-all-pack]；参数[json={}]", json);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getUserPackByPage(json);
	}

	/**
	 * 分页获取 User 的已取出快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json    参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, search:搜索}
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "学生已取快递集", notes = "获取学生已取快递请求，如果获取成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/user-is-pack")
	public Map<String, Object> getUserIsPick(
			@ApiParam(required = true, name = "json", value = "{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, search:搜索}")
			@RequestParam(value = "json") String json,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/user-is-pack]；参数[json={}]", json);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getUserIsPick(json);
	}

	/**
	 * 分页获取 User 的未取出快递，无论其有无取件码；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json    参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址, status:快递状态, search:搜索}
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "学生未取快递集", notes = "获取学生未取快递请求，如果获取成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/user-no-pack")
	public Map<String, Object> getUserNoPick(
			@ApiParam(required = true, name = "json", value = "{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, addr:驿站地址, status:快递状态, search:搜索}")
			@RequestParam(value = "json") String json,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/user-no-pack]；参数[json={}]", json);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getUserNoPick(json);
	}

	/**
	 * 获取 User 所有快递总数、已取快递数量、未取快递数量
	 *
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "学生快递数量", notes = "获取学生快递数量请求，返回各个状态的快递数量", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/user-count")
	public Map<String, Object> getUserTotalNum(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/user-count]；参数[token={}]", token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getUserTotalNum(token);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin 相关
	 */

	/**
	 * 分页获取 Admin 所有的快递，包括已取出和未取出的快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json    参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:快递状态, search:搜索}
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "驿站所有快递集", notes = "获取驿站所有快递请求，如果获取成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/admin-all-pack")
	public Map<String, Object> getAdminPackByPage(
			@ApiParam(required = true, name = "json", value = "{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:快递状态, search:搜索}")
			@RequestParam(value = "json") String json,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/admin-all-pack]；参数[json={}]", json);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getAdminPackByPage(json);
	}

	/**
	 * 分页获取 Admin 的已取出快递；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json    参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, search:搜索}
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "驿站已取快递集", notes = "获取驿站已取快递请求，如果获取成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/admin-is-pack")
	public Map<String, Object> getAdminIsPick(
			@ApiParam(required = true, name = "json", value = "{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, search:搜索}")
			@RequestParam(value = "json") String json,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/admin-is-pack]；参数[json={}]", json);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getAdminIsPick(json);
	}

	/**
	 * 分页获取 Admin 的未取出快递，无论其有无取件码；如果没有 token 令牌，则返回获取信息失败
	 *
	 * @param json    参数{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:快递状态, search:搜索}
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "驿站未取快递集", notes = "获取驿站未取快递请求，如果获取成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/admin-no-pack")
	public Map<String, Object> getAdminNoPick(
			@ApiParam(required = true, name = "json", value = "{currentPage:当前页, pageSize:每页大小, token:令牌, org:快递公司, status:快递状态, search:搜索}")
			@RequestParam(value = "json") String json,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/admin-no-pack]；参数[json={}]", json);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getAdminNoPick(json);
	}

	/**
	 * 获取 Admin 所有快递总数、已取快递数量、未取快递数量
	 *
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "驿站快递数量", notes = "获取驿站快递数量请求，返回各个状态的快递数量", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/admin-count")
	public Map<String, Object> getAdminTotalNum(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/admin-count]；参数[token={}]", token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getAdminTotalNum(token);
	}

	/**
	 * 根据驿站地址和货架获取当前货架的所有快递
	 *
	 * @param token   令牌
	 * @param shelf   货架
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "驿站货架快递集", notes = "根据货架获取驿站快递请求，如果获取成功返回分页结果集", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/shelf-pack")
	public Map<String, Object> getShelfPack(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			@ApiParam(required = true, name = "shelf", value = "货架") @RequestParam(value = "shelf") String shelf,
			HttpServletRequest request) {
		log.info("请求 URL[/pack/shelf-pack]；参数[token={}, shelf={}]", token, shelf);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return packService.getShelfPack(token, shelf);
	}

}
