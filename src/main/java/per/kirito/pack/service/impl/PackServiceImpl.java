package per.kirito.pack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.kirito.pack.mapper.PackMapper;
import per.kirito.pack.pojo.Pack;
import per.kirito.pack.service.inter.PackService;

/**
 * @version 1.0
 * @Author: kirito
 * @Date: 2020/12/23
 * @Time: 15:36
 * @description:
 */
@Service
public class PackServiceImpl implements PackService {
	@Autowired
	private PackMapper packMapper;

	@Override
	public Pack getPackById(String id) {
		return packMapper.getPackById(id);
	}

	@Override
	public int addPack(Pack pack) {
		return packMapper.addPack(pack);
	}

	@Override
	public int updatePack(Pack pack) {
		return packMapper.updatePack(pack);
	}
}
