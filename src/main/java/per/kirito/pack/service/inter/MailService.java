package per.kirito.pack.service.inter;

import java.util.Map;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/12
 * @Time: 14:08
 * @description: Mail 的 Service 层接口
 */
public interface MailService {

	/**
	 * 根据快递单号查询出收件人邮箱并发送取件通知邮件
	 * @param id    快递单号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	Map<String, String> sendMail(String id, String token);

}
