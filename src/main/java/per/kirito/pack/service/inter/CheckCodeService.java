package per.kirito.pack.service.inter;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/8
 * @Time: 16:27
 * @description: 验证码的service层接口
 */
public interface CheckCodeService {

	/**
	 * @Description: 获取验证码字符串和图片
	 * @Param: [token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	Map<String, String> getCheckCode(String token);

}
