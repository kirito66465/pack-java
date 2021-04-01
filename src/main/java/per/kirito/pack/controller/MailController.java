package per.kirito.pack.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.service.inter.MailService;
import per.kirito.pack.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author kirito
 * @date 2021/1/12
 * @time 14:54
 * Mail 的 Controller 层
 */
@Slf4j
@Api(tags = {"发送邮件"}, produces = "application/json", consumes = "application/json")
// @CrossOrigin
@RestController
@RequestMapping(value = "/mail")
public class MailController {

	@Autowired
	private MailService mailService;

	/**
	 * 根据快递单号查询出收件人邮箱并发送取件通知邮件
	 *
	 * @param ids     快递单号
	 * @param token   令牌
	 * @param request http 请求
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	@ApiOperation(value = "邮件通知", notes = "发送邮件请求，返回发送成功与否", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/notice")
	public Map<String, String> sendMail(
			@ApiParam(required = true, name = "ids", value = "快递单号") @RequestParam(value = "ids") String ids,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/mail/notice]；参数[ids={}, token={}]", ids, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return mailService.sendMail(ids, token);
	}

}
