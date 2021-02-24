package per.kirito.pack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import per.kirito.pack.service.inter.MailService;

import java.util.Map;

/**
 * author: 严晨
 * date: 2021/1/12
 * time: 14:54
 * Mail 的 Controller 层
 */
@Api(tags = {"发送邮件"}, description = "发送邮件")
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
	@ApiOperation(value = "邮件通知")
	@CrossOrigin
	@RequestMapping(value = "/notice")
	public Map<String, String> sendMail(@RequestParam(value = "id") String id,
	                                    @RequestParam(value = "token") String token) {
		return mailService.sendMail(id, token);
	}
	
}
