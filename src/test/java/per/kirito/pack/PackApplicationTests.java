package per.kirito.pack;

import org.junit.jupiter.api.Test;
import per.kirito.pack.other.myEnum.Express;

// @SpringBootTest
class PackApplicationTests {

	@Test
	void contextLoads() {
		String express = String.valueOf(Express.中通);
		System.out.println(express);
	}

}
