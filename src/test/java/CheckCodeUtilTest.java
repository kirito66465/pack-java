import org.junit.Test;
import per.kirito.pack.util.CheckCodeUtil;

/**
 * @author kirito
 * @date 2021/4/1
 * @time 9:17:13
 */
public class CheckCodeUtilTest {

	@Test
	public void imageToBase64() throws Exception {
		String base64 = CheckCodeUtil.imageToBase64(120, 40, CheckCodeUtil.getStringRandom(4));
		System.out.println(base64);
	}

	@Test
	public void base64ToImage() throws Exception {
		String base64 = CheckCodeUtil.imageToBase64(120, 40, CheckCodeUtil.getStringRandom(4));
		String file = "D:/code.png";
		CheckCodeUtil.base64ToImage(base64, file);
	}

}
