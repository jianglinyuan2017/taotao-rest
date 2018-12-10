package com.taotao.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NodeItem {

	@JsonProperty("u")
	private String cat_url;
	
	@JsonProperty("n")
	private String cat_name;
	
	@JsonProperty("i")
	private List<?> cat_item;
	
	private List<?> data;

	public String getCat_url() {
		return cat_url;
	}

	public void setCat_url(String cat_url) {
		this.cat_url = cat_url;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}

	public List<?> getCat_item() {
		return cat_item;
	}

	public void setCat_item(List<?> cat_item) {
		this.cat_item = cat_item;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	
	
}
