package per.kirito.pack.service.inter;

import per.kirito.pack.pojo.utilPojo.SendRequest;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/4
 * @Time: 15:52
 * @description:
 */
public interface SendService {
	Map<String, Object> sendPack(SendRequest request, String token);
}
