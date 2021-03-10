import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileWriter;
import org.junit.Test;
import per.kirito.pack.util.PackIdUtil;

/**
 * author: kirito
 * date: 2021/3/10
 * time: 9:15
 */
public class PackIdUtilTest {

	@Test
	public void generateIdToFile() throws InterruptedException {
		writeToFile("中苑", 1000);
	}

	/**
	 * 传入驿站地址，生成对应的快递单号并写入文件中
	 * @param addr          驿站地址
	 * @param quantity      生成总数量
	 */
	public static void writeToFile(String addr, int quantity) throws InterruptedException {
		System.out.println("==========开始生成 " + addr + " 的快递单号==========");
		System.out.println(DateUtil.now());
		long start = System.currentTimeMillis();
		FileWriter writer = new FileWriter("D://pack/" + addr +".txt");
		int count;
		switch (addr) {
			case "中苑":
				count = quantity / 3;
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("ZTO");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("STO");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("YTO");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				break;
			case "西苑":
				count = quantity / 3;
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("JD");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("SF");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("YD");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				break;
			case "北苑":
				count = quantity / 2;
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("TT");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("EMS");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				break;
			default:
				count = quantity;
				for (int i = 0; i < count; i++) {
					String id = PackIdUtil.generate("ZTO");
					writer.append(id + ",");
					Thread.sleep(100);
				}
				break;
		}
		long end = System.currentTimeMillis();
		System.out.println("耗时: " + (end - start) + "ms");
		System.out.println(DateUtil.now());
		System.out.println("==========生成结束==========");
	}

}
