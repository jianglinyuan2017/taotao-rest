package com.taotao.service.impl;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://www.baidu.com");
		HttpResponse execute = httpClient.execute(get);
		HttpEntity entity = execute.getEntity();
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
	}

}
