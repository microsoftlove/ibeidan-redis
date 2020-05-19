package com.ibeidan.test;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author lee
 * DATE 2020/5/19 11:41
 */
public class LockTest extends AbstractRedisTest {


    private String key = "order:id";


    @Test
    public void lock(){
        if (jedis.exists(key)){

        }else {
            jedis.setex(key,20000,"foo");

        }
    }

    public boolean lock2(){
        jedis.setex(key,30,"1");
        return true;
    }


    @Test
    public void unLockTest(){
        unLock("foo");
    }

    public void unLock(String lockId) {

        //如果get key的值和预期一样 就删除该key
        //保证由加锁的人来解锁
        //其中ARGV[1]表示设置key时指定的随机值-lockId
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                "then return redis.call('del' , KEYS[1]) " +
                " else return 0 end";

        Long list = (Long) jedis.eval(luaScript, Collections.singletonList(key
        ) ,Collections.singletonList(lockId));

        print(list);

    }



}
