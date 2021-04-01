package per.kirito.pack.service.inter;

import java.util.Map;

/**
 * @author kirito
 * @date 2020/12/28
 * @time 15:21
 * 账户管理相关泛型接口
 */
public interface AccountService<E> {

	/**
	 * 登录
	 *
	 * @param card     编号
	 * @param password 密码
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	Map<String, String> login(String card, String password);

	/**
	 * 退出登录
	 *
	 * @param token 令牌
	 * @return java.lang.String
	 */
	String logout(String token);

	/**
	 * 获取账户信息
	 *
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 */
	Map<String, Object> getInfo(String token);

	/**
	 * 账户注册
	 *
	 * @param entity 账户实体信息
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	Map<String, String> register(E entity);

	/**
	 * 账户忘记密码
	 *
	 * @param card     编号
	 * @param phone    手机号
	 * @param password 新密码
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	Map<String, String> forgetPwd(String card, String phone, String password);

	/**
	 * 账户修改密码
	 *
	 * @param card      编号
	 * @param oldPwd    原密码
	 * @param newPwd    新密码
	 * @param checkCode 验证码
	 * @param token     令牌
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	Map<String, String> resetPwd(String card, String oldPwd, String newPwd, String checkCode, String token);

	/**
	 * 更新用户信息
	 *
	 * @param name  姓名
	 * @param addr  地址
	 * @param mail  邮箱
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	Map<String, String> updateInfo(String name, String addr, String mail, String token);

}
