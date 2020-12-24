package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.Pack;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:35
 * @description:
 */
public interface PackService {
	Pack getPackById(String id);

	int addPack(Pack pack);

	int updatePack(Pack pack);

	List<Pack> getPackSByPhone(String phone);

	List<Pack> getPacksByAddr(String addr);
}
