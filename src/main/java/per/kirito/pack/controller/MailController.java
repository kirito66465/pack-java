package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.kirito.pack.service.inter.MailService;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/12
 * @Time: 14:54
 * @description: Mail 的 Controller 层
 */
@RestController
@RequestMapping(value = "mail")
public class MailController {
	
	@Autowired
	private MailService mailService;

	/**
	 * 根据快递单号查询出收件人邮箱并发送取件通知邮件
	 * @param id        快递单号
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/notice")
	public Map<String, String> sendMail(@RequestParam(value = "id") String id,
	                                    @RequestParam(value = "token") String token) {
		return mailService.sendMail(id, token);
	}
	
}
