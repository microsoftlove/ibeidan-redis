package com.ibeidan.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 * DATE 2020/5/19 11:13
 */
public class ScriptingCommandTest extends AbstractRedisTest{

    String script = "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2],ARGV[3]}";

    List<String> keys = new ArrayList<>();




    @Test
    public void  evalMuttiBulk() {
        keys.add("foo");
        keys.add("bar");

        List<String > agrs = new ArrayList<>();
        agrs.add("first");
        agrs.add("second");
        agrs.add("third");

        List<String> response = (List<String>) jedis.eval(script,keys,agrs);
        print(response);
    }

    @Test
    public void evalBulk(){
        String script = "return KEYS[1]";
        keys.add("foo");

        List<String> args = new ArrayList<>();
        args.add("first");

        String response = (String) jedis.eval(script,keys,args);
        print(response);
    }

    @Test
    public void evalInt(){
        String script = "return 2";
        List<String> keys = new ArrayList<>();
        keys.add("key1");

        Long resonse = (Long) jedis.eval(script,keys,new ArrayList<String>());
        print(resonse);
    }

    @Test
    public void evalNestedLists(){
        String script = "return { {KEYS[1]} , {2} }";
        List<?>  results = (List<?>) jedis.eval(script,1,"key1");

        print(results);
    }

    @Test
    public void evalsha(){
        jedis.set("foo","bar");
        jedis.eval("return redis.call('get','foo')");
        String re = (String) jedis.evalsha("6b1bf486c81ceb7edf3c093f4c48582e38c0e791");
        print(re);
    }

    @Test
    public void evalDel(){
        jedis.set("foo","bar");
        Long l = (Long) jedis.eval("return redis.call('del' , KEYS[1])",1,"foo");
        print(l);
    }




}
