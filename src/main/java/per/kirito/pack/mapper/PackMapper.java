package per.kirito.pack.mapper;

import org.springframework.stereotype.Repository;
import per.kirito.pack.pojo.Pack;

import java.util.List;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:24
 * @description:
 */
@Repository
public interface PackMapper {
	Pack getPackById(String id);

	int addPack(Pack pack);

	int updatePack(Pack pack);

	List<Pack> getPackSByPhone(String phone);

	List<Pack> getPacksByAddr(String addr);

	List<Pack> getAllPacks();

	int getTotalByAddr(String addr);

	int getTotalByTel(String tel);
}
