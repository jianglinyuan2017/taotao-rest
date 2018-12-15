package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.taotao.comm.util.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.NodeItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.facade.service.ItemService;
import com.taotao.facade.service.JedisClient;

@Service
public class ItemServerImpl implements ItemService{

	@Autowired
	TbItemCatMapper tbItemCatMapper;
	
	@Autowired
	JedisClient jedisClient;
	
	@Override
	public String getItemCatList() {
		try {
			String hget = jedisClient.hget("内容列表", 0+"");
			if(StringUtils.isNotEmpty(hget)) {
				return hget;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List itemCatJson = getItemCatJson(0);
		NodeItem node = new NodeItem();
		node.setData(itemCatJson);
		String objectToJson = JsonUtils.objectToJson(node);
		try {
			jedisClient.hset("内容列表", 0+"", objectToJson);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectToJson;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public List getItemCatJson(long parentId) {
		NodeItem node = new NodeItem();
		List list = new ArrayList();
		List l = new ArrayList<>();
		TbItemCatExample example = new TbItemCatExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		PageHelper.startPage(1, 14);
		List<TbItemCat> selectByExample = tbItemCatMapper.selectByExample(example);
		for(TbItemCat t : selectByExample) {
			NodeItem node_p = new NodeItem();
			if(t.getIsParent()) {
				if(parentId==0) {
					node_p.setCat_name("<a href='/products/"+t.getId()+".html'>"+t.getName()+"</a>");
				}else {
					node_p.setCat_name(t.getName());
				}
				node_p.setCat_url("/products"+t.getId()+".html");
				node_p.setCat_item(getItemCatJson(t.getId()));
				list.add(node_p);
			}else {
				String a = "/products/"+t.getId()+".html|"+t.getName();
				list.add(a);
			}
		}
		return list;
	}
}





















