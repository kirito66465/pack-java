package per.kirito.pack;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/4
 * @Time: 20:37
 * @description:
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PackApplication.class);
	}

}
