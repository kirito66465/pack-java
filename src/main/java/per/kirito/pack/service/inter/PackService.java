package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.Pack;

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
}
