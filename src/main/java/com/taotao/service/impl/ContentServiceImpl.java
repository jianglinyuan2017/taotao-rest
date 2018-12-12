package com.taotao.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.taotao.service.JedisClient;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	TbContentMapper tbContentMapper;
	
	@Autowired
	JedisClient jedisClient;
	
	@Override
	public List<TbContent> getContentList(long contentCategoryId) {

		try {
			String hget = jedisClient.hget("常量", contentCategoryId+"");
			if(StringUtils.isNotEmpty(hget)) {
				List<TbContent> cashTbContents = JsonUtils.jsonToList(hget, TbContent.class);
				return cashTbContents;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(contentCategoryId);
		List<TbContent> selectByExample = tbContentMapper.selectByExample(example);
		try {
			jedisClient.hset("常量", contentCategoryId+"", JsonUtils.objectToJson(selectByExample));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectByExample;
	}

}
