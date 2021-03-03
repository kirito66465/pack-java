package per.kirito.pack.controller;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.service.inter.CheckCodeService;

import java.util.Map;

/**
 * author: 严晨
 * date: 2021/1/8
 * time: 16:26
 * 验证码的 controller 层
 */
@Api(tags = {"验证码"}, description = "验证码", produces = "application/json", consumes = "application/json")
@RestController
public class CheckCodeController {

	private static Log log = LogFactory.get();

	@Autowired
	private CheckCodeService checkCodeService;

	/**
	 * 获取验证码字符串和图片
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "获取验证码字符串和图片", notes = "获取验证码请求，在登录情况下返回验证码生成成功与否", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping(value = "/getCheckCode")
	public Map<String, String> getCheckCode(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token) {
		log.info("请求 URL[/getCheckCode]；参数[token=" + token + "]");
		return checkCodeService.getCheckCode(token);
	}

}
