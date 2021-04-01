package per.kirito.pack;

import cn.hutool.cron.CronUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import per.kirito.pack.properties.MailProperties;

/**
 * @author kirito
 * @date 2020/12/4
 * @time 20:37
 * Spring Boot 项目启动类
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "per.kirito.pack")
public class PackApplication {

	public static void main(String[] args) {
		log.info("程序启动");
		SpringApplication.run(PackApplication.class, args);
		per.kirito.pack.util.cron.CronUtil.printInfo();
		CronUtil.start();
	}

	/**
	 * 测试 Properties 配置类
	 */
	@Component
	public static class OrderPropertiesCommandLineRunner implements CommandLineRunner {

		@Autowired
		private MailProperties mailProperties;

		@Override
		public void run(String... args) {
			log.info("测试 Properties 配置类");
			log.info("---------- MailProperties ----------");
			log.info("senderMail: {}", mailProperties.getSenderMail());
			log.info("host: {}", mailProperties.getHost());
			log.info("password: {}", mailProperties.getPassword());
		}

	}

}
