package per.kirito.pack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.kirito.pack.service.inter.CheckCodeService;

import java.util.Map;

/**
 * author: 严晨
 * date: 2021/1/8
 * time: 16:26
 * 验证码的 controller 层
 */
@Api(tags = {"验证码"}, description = "验证码")
@RestController
public class CheckCodeController {

	@Autowired
	private CheckCodeService checkCodeService;

	/**
	 * 获取验证码字符串和图片
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@ApiOperation(value = "获取验证码字符串和图片")
	@CrossOrigin
	@RequestMapping(value = "/getCheckCode")
	public Map<String, String> getCheckCode(@RequestParam(value = "token") String token) {
		return checkCodeService.getCheckCode(token);
	}

}
