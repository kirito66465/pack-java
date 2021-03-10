import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.Test;

/**
 * author: kirito
 * date: 2021/3/8
 * time: 8:46
 */
public class JasyptTest {

	@Test
	public void testEncrypt() throws Exception {
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		EnvironmentPBEConfig config = new EnvironmentPBEConfig();

		config.setAlgorithm("PBEWithMD5AndDES");          // 加密的算法，这个算法是默认的
		config.setPassword("xxx");                     // 加密的密钥
		standardPBEStringEncryptor.setConfig(config);
		String plainText = "xxx";
		String encryptedText = standardPBEStringEncryptor.encrypt(plainText);
		System.out.println(encryptedText);
	}

	@Test
	public void testDecrypt() throws Exception {
		StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
		EnvironmentPBEConfig config = new EnvironmentPBEConfig();

		config.setAlgorithm("PBEWithMD5AndDES");
		config.setPassword("ljk");
		standardPBEStringEncryptor.setConfig(config);
		String encryptedText = "aHsFtlQjatrOP2s8bfLGkUG55z53KLNi";
		String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
		System.out.println(plainText);
	}

}
