package com.ibeidan.test;

import com.ibeidan.utils.pojo.AuthClient;
import com.ibeidan.utils.pojo.AuthUser;
import com.ibeidan.utils.pojo.OAuth2Authentication;
import com.ibeidan.utils.pojo.User;
import org.junit.Before;

/**
 * @author lee
 * DATE 2021/3/29 10:28
 */
public  class AbstractTest {

    public void print(String message){
        System.out.println(message);
    }


    public static OAuth2Authentication auth2Authentication = new OAuth2Authentication();

    public static User user = new User();
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


        user.setGlobalId(13310388L);
        user.setMobile("M20015975963");
        user.setPassword("840d584d6130eadc263366868182a7f929eb232c");
        user.setSalt("d2b38be803f3420b");
        Byte b = new Byte("0");

        user.setStatus(b);
        user.setGender(b);
        user.setCreateClientId("iss_user_app");
        user.setCtime(1541808186732l);
        user.setMtime(1541808186732l);
        user.setCreator(0l);
        user.setModifier(0l);
    }
}
