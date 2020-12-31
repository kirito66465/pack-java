package per.kirito.pack.service.inter;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/28
 * @Time: 15:21
 * @description: 账户管理相关泛型接口
 */
public interface AccountService<E> {

	/**
	 * @Description: 登录
	 * @Param: [card, password]
	 * @Return: java.lang.String
	 **/
	Map<String, String> login(String card, String password);

	/**
	 * @Description: 退出登录
	 * @Param: []
	 * @Return: java.lang.String
	 **/
	String exit(String token);

	/**
	 * @Description: 获取账户信息
	 * @Param: []
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 **/
	Map<String, Object> getInfo(String token);

	/**
	 * @Description: 账户注册
	 * @Param: [entity]
	 * @Return: java.lang.String
	 **/
	Map<String, String> register(E entity);

	/**
	 * @Description: 账户重置密码
	 * @Param: [card, phone, password]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	Map<String, String> forgetPwd(String card, String phone, String password);

}
