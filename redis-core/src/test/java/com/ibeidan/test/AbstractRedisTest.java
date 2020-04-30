package com.ibeidan.test;

import org.junit.After;
import org.junit.Before;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

/**
 * @author lee
 * DATE 2020/4/29 15:02
 */
public abstract class AbstractRedisTest {

    private static final HostAndPort host = new HostAndPort("localhost",6379);

    public Jedis jedis;

    @Before
    public void setHost(){
        jedis = new Jedis(host);
        jedis.connect();

    }

    @After
    public void tearDown(){
        jedis.disconnect();
    }

    protected Jedis createJedis(){
        Jedis j = new Jedis(host);
        j.connect();
        return j;
    }

     protected <T> void print(T msg){
         System.out.println(msg);
     }
}
