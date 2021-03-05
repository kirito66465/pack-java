package per.kirito.pack;

import cn.hutool.cron.CronUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import per.kirito.pack.properties.ElasticProperties;
import per.kirito.pack.properties.MailProperties;

/**
 * author: 严晨
 * date: 2020/12/4
 * time: 20:37
 * Spring Boot 项目启动类
 */
@Slf4j
@MapperScan(basePackages = "per.kirito.pack.mapper")
@SpringBootApplication(scanBasePackages = "per.kirito.pack")
public class PackApplication {

	public static void main(String[] args) {
		log.info("程序启动");
		SpringApplication.run(PackApplication.class, args);
		CronUtil.start();
	}

	/**
	 * 测试 Properties 配置类
	 **/
	@Component
	public static class OrderPropertiesCommandLineRunner implements CommandLineRunner {

		private final Logger logger = LoggerFactory.getLogger(getClass());

		@Autowired
		private MailProperties mailProperties;

		@Autowired
		private ElasticProperties elasticProperties;

		@Override
		public void run(String... args) {
			log.info("测试 Properties 配置类");
			log.info("---------- MailProperties ----------");
			log.info("senderMail: " + mailProperties.getSenderMail());
			log.info("host: " + mailProperties.getHost());
			log.info("password: " + mailProperties.getPassword());
			log.info("---------- ElasticProperties ----------");
			log.info("hostName: " + elasticProperties.getHostName());
			log.info("port: " + elasticProperties.getPort());
		}

	}

}
