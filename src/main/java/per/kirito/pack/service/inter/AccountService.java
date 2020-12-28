package per.kirito.pack.service.inter;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/28
 * @Time: 15:21
 * @description:
 */
public interface AccountService<E> {
	String login(String card, String password);

	String exit();

	Map<String, Object> getInfo();

	String register(E entity);

	Map<String, String> forgetPwd(String card, String phone, String password);
}
