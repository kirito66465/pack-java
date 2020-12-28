package per.kirito.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.*;
import per.kirito.pack.service.inter.PackService;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:19
 * @description: 包裹相关controller
 */
@RestController(value = "/pack")
public class PackController {
	@Autowired
	private PackService packService;

	// 根据快递单号获取快递信息
	@CrossOrigin
	@RequestMapping(value = "/getPackById")
	public Object getPackById(@RequestParam(value = "id") String id) {
		return packService.getPackById(id);
	}

	// 管理员添加快递入站
	@CrossOrigin
	@RequestMapping(value = "/addPack")
	public String addPack(@RequestParam(value = "id") String id) {           // TODO: 改成添加参数，其余自动生成
		return packService.addPack(id);
	}

	// 取件
	@CrossOrigin
	@RequestMapping(value = "/pickPack")
	public String pickPack(@RequestParam(value = "id") String id,
	                       @RequestParam(value = "code") String code) {
		return packService.pickPack(id, code);
	}

	// 用户获取自己的快递
	@CrossOrigin
	@RequestMapping(value = "/getPacksByUser")
	public List<Pack> getPacksByUser(@RequestParam(value = "card") String card) {
		return packService.getPacksByUser(card);
	}

	// 管理员获取当前驿站所有的快递
	@CrossOrigin
	@RequestMapping(value = "/getPacksByAdmin")
	public List<Pack> getPacksByAdmin(@RequestParam(value = "card") String card) {
		return packService.getPacksByAdmin(card);
	}

	@CrossOrigin
	@RequestMapping(value = "/getAdminPacksByPage")
	public Page<Pack> getAdminPackByPage(@RequestParam(defaultValue = "1") int currentPage,
	                                     @RequestParam(value = "pageSize") int pageSize) {
		return packService.getAdminPackByPage(currentPage, pageSize);
	}
}
