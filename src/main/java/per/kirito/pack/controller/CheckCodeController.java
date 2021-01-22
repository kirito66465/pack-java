package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.kirito.pack.service.inter.CheckCodeService;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/8
 * @Time: 16:26
 * @description: 验证码的 controller 层
 */
@RestController
public class CheckCodeController {

	@Autowired
	private CheckCodeService checkCodeService;

	/**
	 * @Description: 获取验证码字符串和图片
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getCheckCode")
	public Map<String, String> getCheckCode(@RequestParam(value = "token") String token) {
		return checkCodeService.getCheckCode(token);
	}

}
