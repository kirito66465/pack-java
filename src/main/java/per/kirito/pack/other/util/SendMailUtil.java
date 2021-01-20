package per.kirito.pack.other.util;

import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2021/1/12
 * @Time: 14:17
 * @description: 发送邮件工具类
 */
public class SendMailUtil {
	
	private static final String MAIL_FROM = "1204505357@qq.com";          // 发件人电子邮箱
	private static final String MAIL_HOST = "smtp.qq.com";                // 指定发送邮件的主机为smtp.qq.com

	public static void sendMail(String mail, String addr, String code, String org) throws MessagingException, GeneralSecurityException {
		String to = mail;                                           // 收件人电子邮箱
		// 获取系统属性
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", MAIL_HOST);
		properties.put("mail.smtp.auth", "true");

		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);

		// 获取默认session对象
		Session session = Session.getDefaultInstance(properties, new Authenticator() {

			@Override
			public PasswordAuthentication getPasswordAuthentication()
			{
				//发件人邮件用户名、授权码
				return new PasswordAuthentication("1204505357@qq.com", "knpsvtgocnchigib");
			}

		});
		// 创建默认的 MimeMessage 对象
		MimeMessage message = new MimeMessage(session);
		// Set From: 头部头字段
		message.setFrom(new InternetAddress(MAIL_FROM));
		// Set To: 头部头字段
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		// Set Subject: 头部头字段
		String sub = "[基于Web的校园快递管理系统]-取件通知";
		message.setSubject(sub);
		// 设置消息体
		String text = "您好，您有一个" + org + "快递包裹已到达" + addr + "菜鸟驿站，请凭取件码" + code + "在19:00前尽早取！";
		message.setText(text);
		// 发送消息
		Transport.send(message);
		String dt = TypeConversion.getTime();
		System.out.println(dt + "\tfrom: " + MAIL_FROM + "\tto: " + mail + "\tsub: " + sub + "\ttext: " + text + "\tSend message successfully....");
	}

	public static void main(String[] args) {
		try {
			sendMail("4yanchen6@163.com", "中苑", "1-1-01", "中通");
		} catch (MessagingException | GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

}
