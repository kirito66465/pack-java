package per.kirito.pack.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author kirito
 * @version 1.0
 * @date 2020/12/6 16:04
 * @description:
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_msg")
public class Message {
	private int id;                                 // 序号
	private String sender;                          // 发信人
	private String receiver;                        // 收信人
	private String from_msg;                        // 消息内容
	private String to_reply;                        // 回复内容
	private String send;                            // 发信时间
	private String reply;                           // 回复时间
}
