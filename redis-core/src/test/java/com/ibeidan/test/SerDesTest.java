package com.ibeidan.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibeidan.core.pojo.AuthClient;
import com.ibeidan.core.pojo.AuthUser;
import com.ibeidan.core.pojo.OAuth2Authentication;
import com.ibeidan.core.protostuff.ProtoStuffUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author lee
 * DATE 2021/3/19 10:51
 *
 * 1、pb原生开发
 * a.定义.proto文件
 * b.使用编译工具进行编译
 * c.使用api进行读写
 *
 * 2、pbstuff开发
 *
 * 比对
 * pb
 * pbstuff
 * jackjson 存储大小
 *
 */
public class SerDesTest  extends AbstractRedisTest{

    OAuth2Authentication auth2Authentication = new OAuth2Authentication();
    ObjectMapper objectMapper = new ObjectMapper();
    @Before
    public void initObj(){
        AuthUser authUser = new AuthUser();
        authUser.setBizId(3L);
        authUser.setBizUserId(5961474781931897338L);
        authUser.setGlobalId(7000000000000007649L);
        authUser.setUsername(null);
        authUser.setEmail("qiyuwei@ishansong.com");
        authUser.setMobile("M20000871827");
        authUser.setDeviceId("86bb06d2-f876-48b3-9afb-28f7176f725e");


        AuthClient authClient = new AuthClient();
        authClient.setClientId("shop_app");
        authClient.setBizId(3L);
        authClient.setName("商户版-app");
        authClient.setResourceIds("shop_web,shop_h5,shop_app,ug_buser_web");
        authClient.setScope("");
        authClient.setAuthorities("ROLE_CLIENT,ROLE_RESOURCE");


        auth2Authentication.setAuthClient(authClient);
        auth2Authentication.setAuthUser(authUser);
        auth2Authentication.setScope("");
        auth2Authentication.setGrantType("regist_sms");
    }

    @Test
    public void testSer(){
        byte[] serializer = ProtoStuffUtils.serialize(auth2Authentication);
        for (int i = 0; i < serializer.length; i++) {
            System.out.println(i + " : " + serializer[i]);
        }
        OAuth2Authentication deserializer = ProtoStuffUtils.deserialize(serializer, OAuth2Authentication.class);
        System.out.println("反序列化的结果： " + deserializer.getAuthClient().toString());


        try {
            byte[] serializer2 =  objectMapper.writeValueAsBytes(auth2Authentication);
            for (int i = 0; i < serializer2.length; i++) {
                System.out.println(i + " : " + serializer2[i]);
            }

            OAuth2Authentication authUser1=  objectMapper.readValue(serializer2, OAuth2Authentication.class);
            System.out.println("反序列化的结果： " + authUser1.getAuthClient().toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void keyGet() throws JsonProcessingException {
        String pbKey = "oauth2:accessToken:2D7EB90CD3BCFA4C9BCCE7266FCFCE4E:authentication";
        String jsonKey = "jsonKey";
        Integer s = 19;
        byte[] b= ProtoStuffUtils.serialize(s);

        for (int i = 0; i < b.length; i++) {
            System.out.println(i + " : " + b[i]);
        }
        System.out.println("4444444");

        byte[] serializer2 =  objectMapper.writeValueAsBytes(s);
        for (int i = 0; i < serializer2.length; i++) {
            System.out.println(i + " : " + serializer2[i]);
        }
    }
    String pbKey = "pboauth2:accessToken:2D7EB90CD3BCFA4C9BCCE7266FCFCE4E:authentication";
    String jsonKey = "jsonoauth2:accessToken:2D7EB90CD3BCFA4C9BCCE7266FCFCE4E:authentication";
    @Test
    public void setPbKey() throws IOException {

        byte[] serializer = ProtoStuffUtils.serialize(auth2Authentication);
        byte[] serializer2 =  objectMapper.writeValueAsBytes(auth2Authentication);


        jedis.setex(pbKey.getBytes(),1000*60,serializer);
        jedis.setex(jsonKey.getBytes(),1000*60,serializer2);

        byte[] pb = jedis.get(pbKey.getBytes());
        OAuth2Authentication deserializer = ProtoStuffUtils.deserialize(pb, OAuth2Authentication.class);
        System.out.println("反序列化的结果： " + deserializer.getAuthClient().toString());


        byte[] json = jedis.get(jsonKey.getBytes());
        OAuth2Authentication authUser1=  objectMapper.readValue(json, OAuth2Authentication.class);
        System.out.println("反序列化的结果： " + authUser1.getAuthClient().toString());


    }


    @Test
    public void desTest() throws IOException {
        byte[] pb = jedis.get(pbKey.getBytes());
        OAuth2Authentication deserializer = ProtoStuffUtils.deserialize(pb, OAuth2Authentication.class);
        System.out.println("反序列化的结果： " + deserializer.getAuthUser().toString());


        byte[] json = jedis.get(jsonKey.getBytes());
        OAuth2Authentication authUser1=  objectMapper.readValue(json, OAuth2Authentication.class);
        System.out.println("反序列化的结果： " + authUser1.getAuthUser().toString());
    }
}
