package per.kirito.pack;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/4
 * @Time: 20:37
 * @description: Spring Boot项目启动类
 */
@MapperScan(basePackages = "per.kirito.pack.mapper")
@SpringBootApplication(scanBasePackages = "per.kirito.pack")
public class PackApplication {

	public static void main(String[] args) {
		SpringApplication.run(PackApplication.class, args);
	}

}
