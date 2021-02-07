package per.kirito.pack;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import per.kirito.pack.properties.MailProperties;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/4
 * @Time: 20:37
 * @description: Spring Boot 项目启动类
 */
@MapperScan(basePackages = "per.kirito.pack.mapper")
@SpringBootApplication(scanBasePackages = "per.kirito.pack")
public class PackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackApplication.class, args);
	}

	/**
	 * 测试 Properties 配置类
	 **/
	@Component
	public static class OrderPropertiesCommandLineRunner implements CommandLineRunner {

		private final Logger logger = LoggerFactory.getLogger(getClass());

		@Autowired
		private MailProperties mailProperties;

		@Override
		public void run(String... args) {
			logger.info("senderMail: " + mailProperties.getSenderMail());
			logger.info("host: " + mailProperties.getHost());
			logger.info("password: " + mailProperties.getPassword());
		}

	}

}
