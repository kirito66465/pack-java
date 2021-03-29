package per.kirito.pack.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.kirito.pack.pojo.utilpojo.PackResult;
import per.kirito.pack.service.inter.PackService;
import per.kirito.pack.util.IpAddressUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kirito
 * @date 2021/2/26
 * @time 9:38
 * Excel 的 Controller 层
 */
@Slf4j
@Api(tags = {"Excel"})
@RestController
@RequestMapping(value = "/excel")
public class ExcelController {

	@Autowired
	private PackService packService;

	/**
	 * 导出 Excel 到输出流
	 *
	 * @param token    令牌
	 * @param type     快递类型
	 * @param response http 响应
	 * @param request  http 请求
	 **/
	@ApiOperation(value = "下载 Excel", notes = "生成对应 Excel，返回输出流", httpMethod = "POST")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "响应成功"),
			@ApiResponse(code = 201, message = "响应创建"),
			@ApiResponse(code = 401, message = "没有权限"),
			@ApiResponse(code = 403, message = "请求被拒绝"),
			@ApiResponse(code = 404, message = "资源不存在")
	})
	@CrossOrigin
	@PostMapping
	public void download(
			@ApiParam(required = true, name = "token", value = "token 令牌") @RequestParam(value = "token") String token,
			@ApiParam(required = true, name = "type", value = "获取类型") @RequestParam(value = "type") String type,
			HttpServletResponse response,
			HttpServletRequest request) {
		log.info("请求 URL[/excel]；参数[token={}, type={}]", token, type);
		log.info("请求来源: {}", IpAddressUtil.getIpAddress(request));
		// 通过工具类创建 writer
		ExcelWriter writer = ExcelUtil.getWriter(true);
		writer.addHeaderAlias("id", "快递单号");
		writer.addHeaderAlias("org", "快递公司");
		writer.addHeaderAlias("perName", "收件人");
		writer.addHeaderAlias("perTel", "收件手机号");
		writer.addHeaderAlias("perAddr", "收件地址");
		writer.addHeaderAlias("addr", "所在驿站");
		writer.addHeaderAlias("code", "取件码");
		writer.addHeaderAlias("contName", "驿站联系人");
		writer.addHeaderAlias("contTel", "驿站联系方式");
		writer.addHeaderAlias("status", "快递状态");
		writer.addHeaderAlias("start", "入站时间");
		writer.addHeaderAlias("end", "取件时间");
		writer.addHeaderAlias("pick", "签收人");

		ServletOutputStream out = null;
		try {
			String content = "";
			List<PackResult> packs = new ArrayList<>();
			switch (type) {
				case "all":
					content = "全部快递";
					packs = packService.getAllPacksByExcelOfAdmin(token);
					break;
				case "is":
					content = "已取快递";
					packs = packService.getIsPacksByExcelOfAdmin(token);
					break;
				case "no":
					content = "未取快递";
					packs = packService.getNoPacksByExcelOfAdmin(token);
					break;
				default:
					packs = null;
					break;
			}
			// 合并单元格后的标题行，使用默认标题样式
			writer.merge(12, content);
			// 一次性写出内容，使用默认样式，强制输出标题
			writer.write(packs, true);

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			// 弹出下载对话框的文件名，不能为中文，中文需要二次编码
			response.setHeader("Content-Disposition", "attachment;filename=" + type + ".xlsx");

			out = response.getOutputStream();
			writer.flush(out, true);
			log.info("生成 Excel 成功！");
		} catch (Exception e) {
			log.error("error: {}", e.getMessage(), e);
			e.printStackTrace();
		} finally {
			// 关闭 writer，释放内存
			writer.close();
			IoUtil.close(out);
		}
	}

}
