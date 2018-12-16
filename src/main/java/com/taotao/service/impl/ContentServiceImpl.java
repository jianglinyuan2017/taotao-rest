package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${PAGE_LIST_ID}")
	private String PAGE_LIST_ID;
	
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

	@Override
	public String getPageContentList() {
		try {
			String hget = jedisClient.hget("常量", PAGE_LIST_ID+"");
			if(StringUtils.isNotEmpty(hget)) {
				List<TbContent> cashTbContents = JsonUtils.jsonToList(hget, TbContent.class);
				return TBlistToString(cashTbContents);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(Long.parseLong(PAGE_LIST_ID));
		List<TbContent> selectByExample = tbContentMapper.selectByExample(example);
		try {
			jedisClient.hset("常量", PAGE_LIST_ID+"", JsonUtils.objectToJson(selectByExample));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TBlistToString(selectByExample);
	}

	public String TBlistToString(List<TbContent> jsonToList) {
		if(null!=jsonToList && !jsonToList.isEmpty()) {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(TbContent j : jsonToList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("srcB", j.getPic());
				map.put("height", 240);
				map.put("alt", "");
				map.put("width", 670);
				map.put("src", j.getPic());
				map.put("widthB", 550);
				map.put("href", j.getPic());
				map.put("heightB", 240);
				list.add(map);
				return JsonUtils.objectToJson(list);
			}
		}
		return null;
	}

}
