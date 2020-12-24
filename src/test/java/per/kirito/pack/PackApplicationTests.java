package per.kirito.pack;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import per.kirito.pack.myEnum.Express;

// @SpringBootTest
class PackApplicationTests {

	@Test
	void contextLoads() {
		String express = String.valueOf(Express.中通);
		System.out.println(express);
	}

}
