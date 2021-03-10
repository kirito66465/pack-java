package per.kirito.pack.service.inter;

import java.util.Map;

/**
 * author: kirito
 * date: 2021/1/12
 * time: 14:08
 * Mail 的 Service 层接口
 */
public interface MailService {

	/**
	 * 根据快递单号查询出收件人邮箱并发送取件通知邮件
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 **/
	Map<String, String> sendMail(String ids, String token);

}
