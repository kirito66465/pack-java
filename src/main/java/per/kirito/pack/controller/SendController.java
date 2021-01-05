package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.utilPojo.SendRequest;
import per.kirito.pack.service.inter.SendService;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/4
 * @Time: 15:51
 * @description:
 */
@RestController
@RequestMapping(value = "/send")
public class SendController {
	@Autowired
	private SendService sendService;

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * User相关
	 **/

	/**
	 * @Description: 接收寄件表单中的信息
	 * @Param: [request, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 *     返回运费和成功与否消息
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getSendInfo")
	public Map<String, Object> getSendInfo(@RequestBody SendRequest request,
	                                       @RequestParam(value = "token") String token) {
		return sendService.sendPack(request, token);
	}


	/**
	 * @Description: TODO: User在接收到返回的运费后，点击确认，请求此方法
	 * @Param: [isSend, id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 *     如果接收到确认，前端出现二维码以扫描，后则delete上一个方法的insert数据
	 **/
	public Map<String, String> isSend(@RequestParam(value = "isSend") boolean isSend,
	                                  @RequestParam(value = "id") String id,
	                                  @RequestParam(value = "token") String token) {
		return null;
	}

	/**
	 * @Description: 分页方式获取User寄件集合
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getSendByUser/{currentPage}")
	public Map<String, Object> getSendByUser(@PathVariable int currentPage,
	                                         @RequestParam(value = "pageSize") int pageSize,
	                                         @RequestParam(value = "token") String token) {
		return sendService.getSendByUser(currentPage, pageSize, token);
	}

	/**
	 * @Description: 获取User寄件数量
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getTotalByUser")
	public Map<String, Object> getTotalByUser(@RequestParam(value = "token") String token) {
		return sendService.getTotalByUser(token);
	}

	/**
	 * -----------------------------------------------------------------------------------------------------------------
	 * Admin相关
	 **/

	/**
	 * @Description: 分页方式获取Admin寄件集合
	 * @Param: [currentPage, pageSize, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getSendByAdmin/{currentPage}")
	public Map<String, Object> getSendByAdmin(@PathVariable int currentPage,
	                                          @RequestParam(value = "pageSize") int pageSize,
	                                          @RequestParam(value = "token") String token) {
		return sendService.getSendByAdmin(currentPage, pageSize, token);
	}

	/**
	 * @Description: 获取Admin寄件数量
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	@CrossOrigin
	@RequestMapping(value = "/getTotalByAdmin")
	public Map<String, Object> getTotalByAdmin(@RequestParam(value = "token") String token) {
		return sendService.getTotalByUser(token);
	}

}
