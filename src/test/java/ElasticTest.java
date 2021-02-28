// import com.alibaba.fastjson.JSON;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import org.elasticsearch.action.index.IndexRequest;
// import org.elasticsearch.action.index.IndexResponse;
// import org.elasticsearch.client.RestHighLevelClient;
// import org.elasticsearch.common.xcontent.XContentType;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
// import per.kirito.pack.config.ElasticConfiguration;
//
// import java.io.IOException;
//
// /**
//  * author: 严晨
//  * date: 2021/2/26
//  * time: 15:13
//  */
// @RunWith(SpringRunner.class)
// @SpringBootTest
// public class ElasticTest {
//
// 	@Autowired
// 	private RestHighLevelClient client;
//
// 	@Data
// 	@NoArgsConstructor
// 	@AllArgsConstructor
// 	static class Student {
//
// 		private String name;
// 		private Integer age;
// 		private Boolean sex;
//
// 	}
//
// 	@Test
// 	public void indexData() throws IOException {
// 		IndexRequest indexRequest = new IndexRequest("students");
// 		indexRequest.id("1");
// 		Student student = new Student("kirito", 21, true);
// 		String jsonStr = JSON.toJSONString(student);
// 		indexRequest.source(jsonStr, XContentType.JSON);
//
// 		//执行操作
// 		IndexResponse index = client.index(indexRequest, ElasticConfiguration.COMMON_OPTIONS);
//
// 		//提取有用的操作数据
// 		System.out.println(index);
// 	}
//
// }
