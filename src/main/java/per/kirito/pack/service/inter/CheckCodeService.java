package per.kirito.pack.service.inter;

import java.util.Map;

/**
 * @author kirito
 * @date 2021/1/8
 * @time 16:27
 * 验证码的 Service 层接口
 */
public interface CheckCodeService {

	/**
	 * 获取验证码字符串和图片
	 *
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	Map<String, String> getCheckCode(String token);

}
