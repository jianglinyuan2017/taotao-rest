package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.taotao.comm.util.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.NodeItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemService;

@Service
public class ItemServerImpl implements ItemService{

	@Autowired
	TbItemCatMapper tbItemCatMapper;
	@Override
	public String getItemCatList() {
		List itemCatJson = getItemCatJson(0);
		NodeItem node = new NodeItem();
		node.setCat_data(itemCatJson);
		String objectToJson = JsonUtils.objectToJson(node);
		System.out.println(objectToJson);
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
			}else {
				String a = "/products/"+t.getId()+".html|"+t.getName();
				node_p.setCat_name(a);
			}
			list.add(node_p);
			node_p.setCat_item(getItemCatJson(t.getId()));
		}
		return list;
	}
}





















