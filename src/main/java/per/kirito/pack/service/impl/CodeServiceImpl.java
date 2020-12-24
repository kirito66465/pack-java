package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.kirito.pack.mapper.CodeMapper;
import per.kirito.pack.pojo.Code;
import per.kirito.pack.service.inter.CodeService;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 17:23
 * @description:
 */
@Service
public class CodeServiceImpl implements CodeService {
	@Autowired
	private CodeMapper codeMapper;

	@Override
	public int findCode(Code code) {
		return codeMapper.findCode(code);
	}

	@Override
	public int updateCode(Code code) {
		return codeMapper.updateCode(code);
	}
}
