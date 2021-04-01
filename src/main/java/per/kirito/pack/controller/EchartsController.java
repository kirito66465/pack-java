package per.kirito.pack.controller;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.service.inter.EchartsService;
import per.kirito.pack.util.IpAddressUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author kirito
 * @date 2021/3/28
 * @time 17:27
 * Echarts 的 Controller 层
 */
@Slf4j
@Api(tags = {"Echarts"})
// @CrossOrigin
@RestController
@RequestMapping(value = "/echarts")
public class EchartsController {

	@Autowired
	private EchartsService echartsService;

	/**
	 * 获取 Echarts 数据
	 *
	 * @param datee 日期
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	@ApiOperation(value = "获取 Echarts 数据", notes = "获取 Echarts 数据，包括饼图和平滑折线图所需数据", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@PostMapping(value = "/get-data")
	public Map<String, Object> getData(
			@ApiParam(required = false, name = "datee", value = "日期") String datee,
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			HttpServletRequest request) {
		log.info("请求 URL[/echarts/get-data]；参数[datee={}, token={}]", datee, token);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		return echartsService.getData(datee, token);
	}

}
