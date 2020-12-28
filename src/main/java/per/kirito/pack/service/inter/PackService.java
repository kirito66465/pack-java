package per.kirito.pack.service.inter;

import org.springframework.web.bind.annotation.RequestParam;
import per.kirito.pack.other.util.PackUtil;
import per.kirito.pack.other.util.TypeConversion;
import per.kirito.pack.pojo.Admin;
import per.kirito.pack.pojo.Pack;
import per.kirito.pack.pojo.Page;
import per.kirito.pack.pojo.User;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:35
 * @description:
 */
public interface PackService {
	Object getPackById(String id);

	String addPack(String id);

	String pickPack(String id, String code);

	List<Pack> getPacksByUser(String card);

	List<Pack> getPacksByAdmin(String card);

	per.kirito.pack.pojo.Page<Pack> getAdminPackByPage(int currentPage, int pageSize);
}
