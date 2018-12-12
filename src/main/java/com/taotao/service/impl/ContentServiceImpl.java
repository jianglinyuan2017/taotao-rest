package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.comm.pojo.EUDataGridResult;
import com.taotao.comm.util.JsonUtils;
import com.taotao.comm.util.TaotaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	TbContentMapper tbContentMapper;
	
	@Override
	public List<TbContent> getContentList(long contentCategoryId) {
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(contentCategoryId);
		List<TbContent> selectByExample = tbContentMapper.selectByExample(example);
		return selectByExample;
	}

}
