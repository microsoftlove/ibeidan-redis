package com.ibeidan.test;

import com.ibeidan.client.BdLettuceUtils;
import com.ibeidan.utils.pojo.OAuth2Authentication;
import com.ibeidan.utils.pojo.ProtoStuffUtils;
import org.junit.Test;


import java.io.IOException;


/**
 * @author lee
 * DATE 2021/3/29 10:24
 *
 * 序列化 重写
 */
public class BdLettuceUtilsTest extends  AbstractTest{

    private final String  bdKey = "bdTestKey";
    private final String  bdKeyObj = "bdTestKeyObj";

    private final String  bdValue = "bdValue";

    private final long ttl = 100 * 1000;

    @Test
    public void testSetnx(){
        String v = BdLettuceUtils.setex(bdKey,bdValue,ttl);
        print(v);
    }

    @Test
    public void testSetnxT(){
        String v = BdLettuceUtils.setex(bdKey,8,ttl);
        print(v);
    }

    @Test
    public void testget() throws IOException {
       /* String r = BdLettuceUtils.getKeyString(bdKey);
        print(r);*/
        byte[] bytes = BdLettuceUtils.getKeyByte(bdKey);
        int s = ProtoStuffUtils.deserialize(bytes,Integer.class);
       /* ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(bytes));*/
        System.out.println(bytes.length);
        System.out.println(s);
    }


    @Test
    public void testSetKV(){
        System.out.println(auth2Authentication.getAuthClient().toString());
        String v = BdLettuceUtils.setexSync(bdKeyObj,auth2Authentication,ttl);
        print(v);
    }

    @Test
    public void testgetObj(){
        byte[] bytes = BdLettuceUtils.getKeyByte(bdKeyObj);
        System.out.println(bytes.length);
        OAuth2Authentication auth2Authentications  =  ProtoStuffUtils.deserialize(bytes,OAuth2Authentication.class);
        print(auth2Authentications.getAuthClient().toString());
    }


    @Test
    public void testGetItem(){
        OAuth2Authentication auth  = BdLettuceUtils.getItem(bdKeyObj,OAuth2Authentication.class);
        System.out.println(auth);
        System.out.println(auth.getAuthClient().toString());
    }

    @Test
    public void testGetString(){
        String r = BdLettuceUtils.getString(bdKeyObj);
        System.out.println(r);
    }

    @Test
    public void testDel(){
        Long r = BdLettuceUtils.del(bdKeyObj);
        System.out.println(r);
    }

    @Test
    public void testTTL(){
        Long r = BdLettuceUtils.ttl(bdKeyObj);
        System.out.println(r);
    }

}
