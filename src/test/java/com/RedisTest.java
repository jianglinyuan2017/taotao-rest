package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {

	public static void main(String[] args) {
		ApplicationContext application = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-*.xml");
		JedisPool bean = (JedisPool)application.getBean("redisClient");
		Jedis resource = bean.getResource();
		String a = resource.get("key1");
		System.out.println(a);
	}
}
