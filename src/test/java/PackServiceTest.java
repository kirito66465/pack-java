import cn.hutool.core.io.file.FileReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import per.kirito.pack.PackApplication;
import per.kirito.pack.service.inter.PackService;

/**
 * author: 严晨
 * date: 2021/2/23
 * time: 9:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PackApplication.class)
public class PackServiceTest {

	@Autowired
	private PackService packService;

	@Test
	public void test1() {
		System.out.println("===================================");
		FileReader fileReader = new FileReader("D:/IdeaProjects/pack/src/main/resources/file/单号/3000/2101.txt");
		String result = fileReader.readString();
		String[] array = result.split(",");
		for(String a : array) {
			String s = packService.addPack(a, "a");
			System.out.println(s);
		}
		System.out.println("===================================");

		System.out.println("===================================");
		FileReader fileReader2 = new FileReader("D:/IdeaProjects/pack/src/main/resources/file/单号/3000/2102.txt");
		String result2 = fileReader2.readString();
		String[] array2 = result2.split(",");
		for(String a : array2) {
			String s = packService.addPack(a, "b");
			System.out.println(s);
		}
		System.out.println("===================================");

		System.out.println("===================================");
		FileReader fileReader3 = new FileReader("D:/IdeaProjects/pack/src/main/resources/file/单号/3000/2103.txt");
		String result3 = fileReader3.readString();
		String[] array3 = result3.split(",");
		for(String a : array3) {
			String s = packService.addPack(a, "c");
			System.out.println(s);
		}
		System.out.println("===================================");
	}

}
