package com.ibeidan.test;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * @author lee
 * DATE 2020/5/18 15:12
 */
public class TransactionCommandsTest extends AbstractRedisTest{

    private final String key = "multiKey:trans";

    Jedis ni ;

    @Before
    public void setUp(){
        ni = new Jedis("localhost",6379);
        ni.connect();
        ni.flushAll();
    }

    /**
     * 标记一个事务块的开始。
     * 事务块内的多条命令会按照先后顺序被放进一个队列当中，最后由 EXEC 命令原子性(atomic)地执行。
     **/
    @Test
    public void multi() {
        Transaction trans = jedis.multi();

        trans.sadd(key,"a");
        trans.sadd(key,"b");
        trans.scard(key);

        List<Object> response = trans.exec();

        print(response);
    }

    /**
     * 监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。
     **/
    @Test
    public void watch() {
        String watch = jedis.watch(key);
        print(watch);
        Transaction transaction= jedis.multi();

        ni.connect();
        ni.set(key,"d");
        ni.disconnect();

        transaction.set(key,"c");//事物将被打断 不会设置为c
        List<Object> list = transaction.exec();
        print(list);
        String ob = jedis.get(key);
        print(ob);
    }


    @Test
    public void unwatch(){
        jedis.watch(key);
        jedis.get(key);
        String val = "foo" ;
        String status = jedis.unwatch();
        print(status);

        Transaction t = jedis.multi();
        ni.connect();
        ni.set(key,"e");
        ni.disconnect();

        t.set(key,val);
        List<Object> l = t.exec();
        print(l);

    }

    @Test
    public void validateWhenInMulti() {
        jedis.multi();
        jedis.ping();
    }

    /**
     * 取消事务，放弃执行事务块内的所有命令。
     * 如果正在使用 WATCH 命令监视某个(或某些) key，那么取消所有监视，等同于执行命令 UNWATCH 。
     **/
    @Test
    public void discard(){
        Transaction t = jedis.multi();
        String status = t.discard();
        print(status);
    }

    @Test
    public void transactionResponse(){
        jedis.set("string","f");
        jedis.lpush("list","foo");
        jedis.hset("hash","a","b");
        jedis.zadd("zset",1,"m");
        jedis.sadd("set","ff");

        Transaction t = jedis.multi();
        Response<String> string = t.get("string");
        Response<String> list = t.lpop("list");
        Response<String> hash = t.hget("hash","a");
        Response<Set<String>> zset = t.zrange("zset",0,1);
        Response<String> set = t.spop("set");
        t.exec();

        print(string.get());
        print(list.get());
        print(hash.get());
        print(zset.get().iterator().next());
        print(set.get());

    }




}
