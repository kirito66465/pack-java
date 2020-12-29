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
 * @date 2020/12/6 16:08
 * @description:
 */
@Data
@NonNull
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_send")
public class Send {

	private String from_name;
	private String from_tel;
	private String from_addr;
	private String to_name;
	private String to_tel;
	private String to_addr;
	private String id;
	private String org;
	private String status;
	private String dt;

}
