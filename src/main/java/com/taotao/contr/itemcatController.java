package com.taotao.contr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.facade.service.ItemService;

@Controller
@RequestMapping("/itemcat")
public class itemcatController {
	
	@Autowired
	ItemService ItemService;
	
	@RequestMapping("/list")
	@ResponseBody
	public String getItemCat() {
		StringBuffer s = new StringBuffer();
		s.append("category.getDataService(");
		String json = ItemService.getItemCatList();
		s.append(json);
		s.append(")");
		return s.toString();
	}

}
