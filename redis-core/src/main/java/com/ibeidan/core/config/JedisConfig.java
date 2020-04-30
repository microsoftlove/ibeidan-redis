package com.ibeidan.core.config;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author lee
 * DATE 2020/4/29 14:17
 */
public class JedisConfig {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功！");
        System.out.println("服务正在运行："+ jedis);
        jedis.set("leekey","ibeidan.com");
        System.out.println("redis 存储的字符串为：" + jedis.get("leekey"));


        //列表
        jedis.lpush("lee-list","1","2","3");

        List<String> list = jedis.lrange("lee-list",0,2);
        list.stream().forEach(item ->{
            System.out.println(""+item);
        });
    }

}
