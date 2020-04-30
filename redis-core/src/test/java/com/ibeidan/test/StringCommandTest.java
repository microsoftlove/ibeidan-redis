package com.ibeidan.test;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author lee
 * DATE 2020/4/29 15:13
 */
public class StringCommandTest extends AbstractRedisTest {

    @Test
    public void setAndGet() {
        String status = jedis.set("foo","bar");
        assertEquals("OK",status);

        String value = jedis.get("foo");
        assertEquals("bar",value);

        assertNull(jedis.get("bar"));
    }

    /**
     * @author libeibei
     * 2020/4/29 17:45
     * GETSET是一个原子集合，此值返回旧值命令
     **/
    @Test
    public void getSet() {
        // GETSET是一个原子集合，此值返回旧值命令
        String value = jedis.getSet("foo","bars");
        print(value);

        value = jedis.get("foo");
        assertEquals("bars",value);
    }

    @Test
    public void mget(){
        List<String> values = jedis.mget("foo","bar");
        List<String> expected = new ArrayList<>();
        expected.add(null);
        expected.add(null);

        values.stream().forEach(item ->{
            print(item);
        });
        //assertEquals(expected,values);

        jedis.set("foo","bar");
        expected = new ArrayList<>();
        expected.add("bar");
        expected.add(null);
        values = jedis.mget("foo","bar");

        //assertEquals(expected,values);

        expected = new ArrayList<>();
        expected.add("bar");
        expected.add("foo");
        values = jedis.mget("foo","bar");

       // assertEquals(expected,values);

    }

    /**
     * @author libeibei
     * 2020/4/29 17:43
     * setnx key存在，不做操作。 key不存在，再set值。
     **/
    @Test
    public void setnx(){
        //if the key already exists no operation is performed. SETNX actually means "SET if Not eXists".
        long status = jedis.setnx("foo","bar");
        print(status);
        assertEquals("bar",jedis.get("foo"));

        status = jedis.setnx("foo","bar2");
        print(status);
        assertEquals(0,status);
        assertEquals("bar",jedis.get("foo"));
    }

    /**
     * 这个命令相当于set expire 组合
     * 原子性操作
     **/
    @Test
    public void setex(){
        String status = jedis.setex("foo",20,"bar");
        assertEquals("OK",status);
        long ttl = jedis.ttl("foo");
        assertTrue(ttl >0 && ttl<=20);
    }

    /**
     * MSET将用新的值取代旧的值
     * 原子性操作
     **/
    @Test
    public void mset(){
        String status = jedis.mset("foo","bar","bar","foo");
        print(status);
        assertEquals("bar",jedis.get("foo"));
        assertEquals("foo",jedis.get("bar"));
    }

    /**
     *  MSETNX将不会执行任何操作，即使只有一个键已经存在。
     **/
    @Test
    public void msetnx(){
        long status = jedis.msetnx("foo1","bar","bar1","foo");
        assertEquals(0,status);

        status = jedis.msetnx("foo1","bar1","bar1","foo1");
        assertEquals(0,status);
    }

    @Test
    public void incrWrongValue(){
        jedis.set("foo","1");
        jedis.incr("foo");
    }

    @Test
    public void incr(){
        long value = jedis.incr("foo");
        assertEquals(6,value);
    }

    @Test
    public void incrByWrongValue(){
        jedis.set("foo","1");
        //jedis.incrBy("foo",2);
    }


    @Test
    public void decr(){
        long value = jedis.decr("foo");
        assertEquals(-1,value);
    }

    @Test
    public void append(){
        long value = jedis.append("foo","bar");
        //assertEquals(5,value);
    }

    /**
     *  可以使用负偏移量来提供从字符串末尾开始的偏移量。
     *  所以-1表示最后一个字符，-2表示倒数第二个等等。
     **/
    @Test
    public void substr() {
        jedis.set("s","This is a string");
        assertEquals("This",jedis.substr("s",0,3));
        assertEquals("ing",jedis.substr("s",-3,-1));
        assertEquals("This is a string",jedis.substr("s",0,-1));
    }

    @Test
    public void strlen(){
        jedis.set("s","This is a string");
        assertEquals("This is a string".length(),jedis.strlen("s").intValue());
    }

    /**
     *  PSETEX的工作原理与{@link#setex(String，int，String)}完全相同，唯一的区别是
     *  过期时间以毫秒为单位，而不是以秒为单位
     **/
    @Test
    public void psetex() {
        String status = jedis.psetex("foo",20000,"bar");
        assertEquals("OK",status);
    }


}
