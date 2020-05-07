package com.ibeidan.test;

import org.junit.Test;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lee
 * DATE 2020/4/30 14:10
 */
public class HashSetCommandTest extends AbstractRedisTest {

    private String key = "fooHashSet";

    private String SCAN_POINTER_START = String.valueOf(0);
    /**
     * 如果字段已经存在，并且HSET刚刚生成了值的更新，则返回0，
     * 如果创建了新字段，则返回1。
     **/
    @Test
    public void hset(){
        long status = jedis.hset(key,"bar","car");
        print(status);
        status = jedis.hset(key,"bar","foo");
        print(status);
    }

    /**
     * 如果键保存散列，则检索与指定字段关联的值。
     **/
    @Test
    public void hget(){
       String get =  jedis.hget(key,"bars");
       print(get);
    }

    /**
     *  如果字段不存在，则将指定的哈希字段设置为指定的值
     *  如果字段已经存在，则返回0，
     *  如果创建了新字段，则返回1。
     **/
    @Test
    public void hsetnx(){
        long status = jedis.hsetnx(key,"nx","1");
        print(status);
    }


    /**
     * 将各自的字段设置为各自的值。 用新值代替旧值。
     * TODO 时间复杂度：O(N)(以N为字段数)
     **/
    @Test
    public void hmset(){
        Map<String,String> hash = new HashMap<>();
        hash.put("bar","bar");
        hash.put("car","car");
        String status = jedis.hmset(key,hash);
        print(status);
    }

    /**
     * 按请求的相同顺序,专门列出与指定所有字段相关的的值
     * TODO 时间复杂度：O(N)(以N为字段数)
     **/
    @Test
    public void hmget(){
        List<String> list = jedis.hmget(key,"bar","car","foo");
        print(list);
    }

    @Test
    public void hsetVariadic(){
        Map<String,String> hash = new HashMap<>();
        hash.put("d","d");
        hash.put("bar","bar");
        long status = jedis.hset(key,hash);
        print(status);
    }


    /**
     * 按值递增哈希中字段存储的数字。
     * 如果key不存在，创建key
     * 如果字段不存在或保存字符串，则该值设置为0
     * 返回，增量过的值
     **/
    @Test
    public void hincrBy() {
        long value = jedis.hincrBy(key,"nx",1);
        print(value);
    }


    @Test
    public void hincrByFloat(){
        Double value = jedis.hincrByFloat(key,"nx",1.1);
        print(value);
    }

    /**
     * 测试哈希中指定字段的存在。
     **/
    @Test
    public void hexists(){
      Boolean f = jedis.hexists(key,"d");
      print(f);
    }

    /**
     * 从key存储的哈希中删除指定字段。
     * 成功1
     **/
    @Test
    public void hdel(){
        Long r = jedis.hdel(key,"d");
        print(r);
    }

    /**
     * 返回hash的元素个数
     **/
    @Test
    public void hlen(){
        Long l = jedis.hlen(key).longValue();
        print(l);
    }

    /**
     * 返回hash的所有字段
     * TODO 时间复杂度：O(N)(以N为字段数)
     **/
    @Test
    public void hkeys() {
       Set<String> keys = jedis.hkeys(key);
       print(keys);
    }

    /**
     * 返回hash的所有字段
     * TODO 时间复杂度：O(N)(以N为字段数)
     **/
    @Test
    public void hvals() {
       List<String>  vals = jedis.hvals(key);
       print(vals);
    }


    /**
     * 返回hash所有字段和对应的值
     * TODO 时间复杂度：O(N)(以N为字段数)
     **/
    @Test
    public void  hgetAll(){
        Map<String,String> hash = jedis.hgetAll(key);
        print(hash);
    }

    /**
     * 返回哈希表 key 中，所有的域和值。
     **/
    @Test
    public void hgetAllPipeline(){
        Pipeline pipeline = jedis.pipelined();
        Response<Map<String,String>> response = pipeline.hgetAll(key);
        pipeline.sync();
        print(response.get());
    }

    /**
     * 增量式迭代命令每次执行的复杂度为 O(1)
     * SCAN 命令用于迭代当前数据库中的数据库键。
     *
     * SSCAN 命令用于迭代集合键中的元素。
     *
     * HSCAN 命令用于迭代哈希键中的键值对。
     *
     * ZSCAN 命令用于迭代有序集合中的元素（包括元素成员和元素分值）
     *
     * 以上列出的四个命令都支持增量式迭代，
     * 它们每次执行都只会返回少量元素， 所以这些命令可以用于生产环境
     **/
    @Test
    public void hscan(){
        jedis.hset(key,"a","a");
        jedis.hset(key,"b","b");

        ScanResult<Map.Entry<String,String>> result = jedis.hscan(key,SCAN_POINTER_START);
        print(result.getResult());//返回hash filed-value

    }

    /**
     * 对元素的模式匹配工作是在命令从数据集中取出元素之后，
     * 向客户端返回元素之前的这段时间内进行的，
     * 所以如果被迭代的数据集中只有少量元素和模式相匹配，
     * 那么迭代命令或许会在多次执行中都不返回任何元素。
     **/
    @Test
    public void hsacnMatch(){

        ScanParams params =new ScanParams();
        params.match("a*");

        ScanResult<Map.Entry<String,String>> result = jedis.hscan(key,SCAN_POINTER_START,params);
        print(result.getResult());

    }

    /**
     *返回哈希表 key 中，与给定field相关联的值的字符串长度（string length）
     **/
    @Test
    public void testHstrLen_EmptyHash() {
        Long respon = jedis.hstrlen(key,"a");
        print(respon);
    }


}
