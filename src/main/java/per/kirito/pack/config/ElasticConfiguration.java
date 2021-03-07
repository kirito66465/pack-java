// package per.kirito.pack.config;
//
// import org.apache.http.HttpHost;
// import org.elasticsearch.client.RequestOptions;
// import org.elasticsearch.client.RestClient;
// import org.elasticsearch.client.RestHighLevelClient;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import per.kirito.pack.properties.ElasticProperties;
//
// /**
//  * author: 严晨
//  * date: 2021/2/26
//  * time: 14:52
//  * elasticsearch 配置类
//  */
// @Configuration
// public class ElasticConfiguration {
//
// 	@Autowired
// 	private ElasticProperties properties;
//
// 	public static final RequestOptions COMMON_OPTIONS;
//
// 	static {
// 		RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
// 		COMMON_OPTIONS = builder.build();
// 	}
//
// 	/**
// 	 * 重新注入 RestHighLevelClient
// 	 * @return org.elasticsearch.client.RestHighLevelClient
// 	 */
// 	@Bean
// 	public RestHighLevelClient restHighLevelClient() {
// 		String hostName = properties.getHostName();
// 		int port = properties.getPort();
//
// 		RestHighLevelClient client = new RestHighLevelClient(
//
// 			RestClient.builder(
// 				new HttpHost(hostName, port, "http")
// 			)
//
// 		);
// 		return client;
// 	}
//
// }
