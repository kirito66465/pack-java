package per.kirito.pack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.service.inter.AccountService;
import per.kirito.pack.util.PackIdUtil;

import java.util.Map;

/**
 * author: 严晨
 * date: 2020/12/11
 * time: 20:11
 * Admin 的 Controller 层
 */
@Api(tags = {"管理员管理"}, description = "管理员管理")
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

	@Qualifier("adminServiceImpl")
	@Autowired
	private AccountService<Admin> accountService;

	/**
	 * Admin 登录
	 * @param card      编号
	 * @param password  密码
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "登录")
	@CrossOrigin
	@RequestMapping(value = "/login")
	public Map<String, String> adminLogin(@RequestParam(value = "card") String card,
	                        @RequestParam(value = "password") String password) {
		return accountService.login(card, password);
	}

	/**
	 * Admin 退出登录
	 * @param token 令牌
	 * @return java.lang.String
	 **/
	@ApiOperation(value = "退出登录")
	@CrossOrigin
	@RequestMapping(value = "/logout")
	public String adminLogout(@RequestParam(value = "token") String token) {
		return accountService.logout(token);
	}

	/**
	 * 获取 Admin 信息
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@ApiOperation(value = "获取账号信息")
	@CrossOrigin
	@RequestMapping(value = "/getInfo")
	public Map<String, Object> getAdminInfo(@RequestParam(value = "token") String token) {
		return accountService.getInfo(token);
	}

	/**
	 * 修改密码
	 * @param card      编号
	 * @param oldPwd    原密码
	 * @param newPwd    新密码
	 * @param checkCode 验证码
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "修改密码")
	@CrossOrigin
	@RequestMapping(value = "/resetPwd")
	public Map<String, String> resetPwd(@RequestParam(value = "card") String card,
	                                    @RequestParam(value = "oldPwd") String oldPwd,
	                                    @RequestParam(value = "newPwd") String newPwd,
	                                    @RequestParam(value = "checkCode") String checkCode,
	                                    @RequestParam(value = "token") String token) {
		return accountService.resetPwd(card, oldPwd, newPwd, checkCode, token);
	}

	/**
	 * 更新 Admin 信息
	 * @param name      姓名
	 * @param phone     手机号
	 * @param mail      邮箱
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "更新账号信息")
	@CrossOrigin
	@RequestMapping(value = "/updateInfo")
	public Map<String, String> updateInfo(@RequestParam(value = "name") String name,
	                                      @RequestParam(value = "phone") String phone,
	                                      @RequestParam(value = "mail") String mail,
	                                      @RequestParam(value = "token") String token) {
		return accountService.updateInfo(name, phone, mail, token);
	}

	/**
	 * 生成快递单号
	 * @param type  快递所属公司
	 * @return java.lang.String
	 */
	@ApiOperation(value = "生成快递单号")
	@CrossOrigin
	@RequestMapping(value = "/getPackId")
	public String getPackId(@RequestParam(value = "type") String type) {
		return PackIdUtil.generate(type);
	}

}
