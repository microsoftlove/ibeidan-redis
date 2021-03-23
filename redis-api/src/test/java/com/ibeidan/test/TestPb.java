package com.ibeidan.test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.ibeidan.tutorial.AddressBookProtos;
import com.ibeidan.tutorial.OAuth2AuthenticationProto;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;

/**
 * @author lee
 * DATE 2021/3/23 14:08
 */
public class TestPb {
    private static final HostAndPort host = new HostAndPort("localhost",6379);

    public Jedis jedis;

    @Before
    public void setHost(){
        jedis = new Jedis(host);
        jedis.connect();

    }

    @Test
    public void test(){
        AddressBookProtos.Person.Builder person= AddressBookProtos.Person.newBuilder().setEmail("sun_beibei@126.com")
                .setId(12);

        AddressBookProtos.AddressBook.Builder address = AddressBookProtos.AddressBook.newBuilder();
       address.addPeople(person);

        AddressBookProtos.AddressBook addressBook = address.build();
        System.out.println(addressBook.toByteArray().length);
    }

    @Test
    public void testAuth() throws InvalidProtocolBufferException {


        OAuth2AuthenticationProto.AuthClient.Builder authClient = OAuth2AuthenticationProto.AuthClient.newBuilder();
        authClient.setClientId("shop_app");
        authClient.setBizId(3L);
        authClient.setName("商户版-app");
        authClient.setResourceIds("shop_web,shop_h5,shop_app,ug_buser_web");
        authClient.setScope("");
        authClient.setAuthorities("ROLE_CLIENT,ROLE_RESOURCE");

        OAuth2AuthenticationProto.AuthUser.Builder authUser = OAuth2AuthenticationProto.AuthUser.newBuilder();
        authUser.setBizId(3L);
        authUser.setBizUserId(5961474781931897338L);
        authUser.setGlobalId(7000000000000007649L);
        authUser.setUsername("");
        authUser.setEmail("qiyuwei@ishansong.com");
        authUser.setMobile("M20000871827");
        authUser.setDeviceId("86bb06d2-f876-48b3-9afb-28f7176f725e");

        OAuth2AuthenticationProto.OAuth2Authentication.Builder auth2Authentication = OAuth2AuthenticationProto.OAuth2Authentication.newBuilder();
        auth2Authentication.setAuthClient(authClient);
        auth2Authentication.setAuthUser(authUser);
        auth2Authentication.setScope("");
        auth2Authentication.setGrantType("regist_sms");

        OAuth2AuthenticationProto.OAuth2Authentication oAuth2AuthenticationData = auth2Authentication.build();

        for(byte b : oAuth2AuthenticationData.toByteArray()){
           // System.out.println(b);
        }
        System.out.println("\n" + "bytes长度" + oAuth2AuthenticationData.toByteString().size());

      //反序列化

        OAuth2AuthenticationProto.OAuth2Authentication desObj =
                OAuth2AuthenticationProto.OAuth2Authentication
                        .parseFrom(oAuth2AuthenticationData.toByteArray());
        System.out.println(desObj.getAuthClient().getName());
        System.out.println(desObj.toString());

        jedis.setex("lee".getBytes(),1000*60,oAuth2AuthenticationData.toByteArray());
    }


}
