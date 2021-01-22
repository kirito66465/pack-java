package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.myEnum.Status;
import per.kirito.pack.util.SendMailUtil;
import per.kirito.pack.service.inter.MailService;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/12
 * @Time: 14:09
 * @description: Mail 的 Service 层，MailService 接口的实现类
 */
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private SendMailUtil sendMailUtil;

	private static final String DO_SUCCESS = Status.DO_SUCCESS.getEnMsg();
	private static final String DO_FAIL = Status.DO_FAIL.getEnMsg();
	private static final String NOT_EXIST = Status.NOT_EXIST.getEnMsg();
	private static final String LOGIN_TO_DO = Status.LOGIN_TO_DO.getEnMsg();

	/**
	 * @Description: 根据快递单号查询出收件人邮箱并发送取件通知邮件
	 * @Param: [id, token]
	 * @Return: java.util.Map<java.lang.String,java.lang.String>
	 **/
	@Override
	public Map<String, String> sendMail(String id, String token) {
		Map<String, String> map = new HashMap<>();
		try {
			if (stringRedisTemplate.hasKey(token)) {
				Map<String, String> result = userMapper.getMailById(id);
				String mail = result.get("mail");
				if (mail != null && !Objects.equals(mail, "")) {
					String code = result.get("code");
					String addr = result.get("addr");
					String org = result.get("org");
					sendMailUtil.sendMail(mail, addr, code, org);
					map.put("result", DO_SUCCESS);
				} else {
					map.put("result", NOT_EXIST);
				}
			} else {
				map.put("result", LOGIN_TO_DO);
			}
			return map;
		} catch (MessagingException | GeneralSecurityException e) {
			e.printStackTrace();
			map.put("result", DO_FAIL);
			return map;
		}
	}

}
