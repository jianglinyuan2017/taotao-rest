package com.taotao.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.comm.util.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.facade.service.ContentService;
import com.taotao.facade.service.JedisClient;

@Service("contentService")
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
