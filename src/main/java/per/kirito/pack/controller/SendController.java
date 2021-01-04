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
	 * @Description: User在接收到返回的运费后，点击确认，请求此方法
	 * @Param: [isSend, id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 *     如果接收到确认，前端出现二维码以扫描，后则delete上一个方法的insert数据
	 **/
	public Map<String, String> isSend(@RequestParam(value = "isSend") boolean isSend,
	                                  @RequestParam(value = "id") String id,
	                                  @RequestParam(value = "token") String token) {
		return null;
	}

}
