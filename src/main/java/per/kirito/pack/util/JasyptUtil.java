// package per.kirito.pack.util;
//
// import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
// import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
//
// /**
//  * author: 严晨
//  * date: 2021/2/7
//  * time: 14:51
//  * description: jasypt 的配置参数加密与解密工具类
//  */
// public class JasyptUtil {
//
// 	public static void main(String[] args) {
// 		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
// 		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
// 		// 设置密钥
// 		config.setPassword("kirito");
// 		// 设置加密方法
// 		config.setAlgorithm("PBEWithMD5AndDES");
// 		encryptor.setConfig(config);
// 		// 加密
// 		String url = encryptor.encrypt("***");
// 		System.out.println(url);
//
// 		// 解密
// 		System.out.println(encryptor.decrypt(url));
// 	}
//
// }
