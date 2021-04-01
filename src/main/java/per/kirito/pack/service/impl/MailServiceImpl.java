package per.kirito.pack.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import per.kirito.pack.mapper.UserMapper;
import per.kirito.pack.myenum.Status;
import per.kirito.pack.util.SendMailUtil;
import per.kirito.pack.service.inter.MailService;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author kirito
 * @date 2021/1/12
 * @time 14:09
 * Mail 的 Service 层，MailService 接口的实现类
 */
@Slf4j
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
	 * 根据快递单号查询出收件人邮箱并发送取件通知邮件
	 *
	 * @param ids   快递单号
	 * @param token 令牌
	 * @return java.util.Map<java.lang.String, java.lang.String>
	 */
	@Override
	public Map<String, String> sendMail(String ids, String token) {
		Map<String, String> map = new HashMap<>();
		try {
			if (stringRedisTemplate.hasKey(token)) {
				String[] idArr = ids.split(",");
				int noExistCount = 0;
				for (String id : idArr) {
					Map<String, String> result = userMapper.getMailById(id);
					String mail = result.get("mail");
					if (mail != null && !Objects.equals(mail, "")) {
						String code = result.get("code");
						String addr = result.get("addr");
						String org = result.get("org");
						sendMailUtil.sendMail(mail, addr, code, org);
					} else {
						noExistCount++;
					}
				}
				if (noExistCount > 0) {
					String result = "有 " + noExistCount + " 件快递通知取件失败，因为快递所属学生账号没有绑定邮箱！";
					log.info("token: {} {}", token, result);
					map.put("result", result);
				} else {
					map.put("result", DO_SUCCESS);
				}
			} else {
				log.info("token: {} 发送通知邮件失败，因为登录状态失效！", token);
				map.put("result", LOGIN_TO_DO);
			}
			return map;
		} catch (MessagingException | GeneralSecurityException e) {
			log.error("error: {}", e.getMessage(), e);
			log.info("token: {} 发送通知邮件失败，因为发生了异常！", token);
			map.put("result", DO_FAIL);
			return map;
		}
	}

}
