package per.kirito.pack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import per.kirito.pack.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kirito
 * @date 2021/4/1
 * @time 10:50:48
 */
@Slf4j
@Api(tags = {"API 文档"})
@Controller
@RequestMapping(value = "/api")
public class ApiController {

	/**
	 * API 文档
	 * @return java.lang.String
	 */
	@ApiOperation(value = "API 文档", notes = "获取 API 文档", httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@RequestMapping(value = "")
	public String api(HttpServletRequest request) {
		log.info("请求 URL[/api]");
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return "/api.html";
	}

}
