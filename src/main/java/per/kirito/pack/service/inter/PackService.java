package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.utilPojo.PackResult;
import per.kirito.pack.pojo.utilPojo.Page;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:35
 * @description: Pack的Service层接口
 */
public interface PackService {

	Object getPackById(String id);

	String addPack(String id);

	String pickPack(String id, String code);

	Page<PackResult> getUserPackByPage(int currentPage, int pageSize);

	Page<PackResult> getAdminPackByPage(int currentPage, int pageSize);

}
