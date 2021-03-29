package com.ibeidan.test;

import com.ibeidan.client.BdLettuceInit;
import io.lettuce.core.RedisURI;
import org.junit.Test;


import java.util.List;

/**
 * @author lee
 * DATE 2021/3/25 14:05
 */
public class ConfigTest {


    @Test
    public void testConfig(){
      List<RedisURI> nodes = BdLettuceInit.getNodes();
        System.out.println(nodes);
    }


    @Test
    public void setKey(){
        BdLettuceInit.initConfig();
    }


    @Test
    public void poolTest(){

        try {
            BdLettuceInit.poolInit();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
