package com.taotao.contr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.comm.util.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/rest/content")
public class ContentController {

	@Autowired
	ContentService contentService;
	
	@RequestMapping("/list/{contentCategoryId}")
	@ResponseBody
	public List<TbContent> getContentList(@PathVariable long contentCategoryId) {
		List<TbContent> contentList = contentService.getContentList(contentCategoryId);
		return contentList;
	}
}
