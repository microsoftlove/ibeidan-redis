package com.ibeidan.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.List;

/**
 * @author lee
 * DATE 2020/5/6 16:41
 */
public class ListCommandsTest extends AbstractRedisTest{

    private String key = "foo:list";

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)
     * 返回表的长度
     **/
    @Test
    public void rpush(){
        long size = jedis.rpush(key,"car","bus","dike");
        print(size);
    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作
     **/
    @Test
    public void lpush() {
        long size = jedis.lpush(key,"d","c");
        print(size);
    }

    /**
     * 返回列表key的长度
     **/
    @Test
    public void llen(){
        long l= jedis.llen(key);
        print(l);
    }


    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定
     *
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素，
     * -2 表示列表的倒数第二个元素，以此类推。
     *
     * TODO 时间复杂度: O(S+N)， S 为偏移量 start ， N 为指定区间内元素的数量。
     **/
    @Test
    public void lrange() {
      List<String> excepted =  jedis.lrange(key,0,2);
      print(excepted);
    }

    /**
     * TODO 时间复杂度: O(N)， N 为被移除的元素的数量。
     * 对一个列表进行修剪(trim)，
     * 就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * 命令执行成功时，返回 OK 。
     **/
    @Test
    public void ltrim(){
        String status = jedis.ltrim(key,0,1);
        print(status);
    }

    /**
     * 将列表 key 下标为 index 的元素的值设置为 value
     * TODO 时间复杂度：对头元素或尾元素进行 LSET 操作，复杂度为 O(1)。
     * TODO 其他情况下，为 O(N)， N 为列表的长度。
     **/
    @Test
    public void lset(){
        String status = jedis.lset(key,0,"aa");
        print(status);
    }

    /**
     * 返回列表 key 中，下标为 index 的元素
     * TODO 时间复杂度：O(N)， N 为到达下标 index 过程中经过的元素数量。
     * TODO 因此，对列表的头元素和尾元素执行 LINDEX 命令，复杂度为O(1)。
     **/
    @Test
    public void lindex() {
       String index =  jedis.lindex(key,0);
       print(index);
    }

    /**
     * TODO 时间复杂度： O(N)， N 为列表的长度。
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     * count 的值可以是以下几种：
     *
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     *
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     *
     * count = 0 : 移除表中所有与 value 相等的值。
     *
     * 返回被移除元素的数量。不存在key，返回0
     **/
    @Test
    public void lrem() {
        long count = jedis.lrem(key,-1,"hello");
        print(count);
    }

    /**
     * 移除并返回列表 key 的头元素。
     **/
    @Test
    public void lpop(){
        String element = jedis.lpop(key);
        print(element);
    }

    /**
     * 移除并返回列表 key 的尾元素。
     **/
    @Test
    public void rpop() {
        String element = jedis.rpop(key);
        print(element);
    }

    private String dstKey = "foo:dst";
    /**
     * TODO 时间复杂度： O(1)
     * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
     * 将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
     * 如果 source 和 destination 相同，则列表中的表尾元素被移动到表头，
     * 并返回该元素，可以把这种特殊情况视作列表的旋转(rotation)操作。
     **/
    @Test
    public void rpoplpush(){
        String ele = jedis.rpoplpush(key,dstKey);
        print(ele);
    }

    /**
     * 它是 LPOP key 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，
     * 连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
     * TODO 模式：事件提醒
     * 有时候，为了等待一个新元素到达数据中，需要使用轮询的方式对数据进行探查。
     * 另一种更好的方式是，使用系统提供的阻塞原语，在新元素到达时立即进行处理，
     * 而新元素还没到达时，就一直阻塞住，避免轮询占用资源。
     **/
    @Test
    public void blpop() {
        List<String> result = jedis.blpop(1,dstKey);
        print(result);

    }

    /**
     * 它是 RPOP key 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，
     * 连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
     * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，
     * 弹出第一个非空列表的尾部元素。
     **/
    @Test
    public void brpop() {
        List<String> result = jedis.brpop(5,dstKey);
        print(result);
    }

    /**
     * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
     * 当key不存在时，什么也不做。
     **/
    @Test
    public void lpushx(){
       long st =  jedis.lpushx(key,"ele");
       print(st);
    }

    /**
     * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。
     * 当 key 不存在时， RPUSHX 命令什么也不做。
     **/
    @Test
    public void rpushx() {
        long st = jedis.rpushx(key,"foo");
        print(st);
    }

    /**
     * TODO 时间复杂度: O(N)， N 为寻找 pivot 过程中经过的元素数量。
     * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。
     *
     * 当 pivot 不存在于列表 key 时，不执行任何操作。
     *
     * 当 key 不存在时， key 被视为空列表，不执行任何操作。
     *
     * 如果 key 不是列表类型，返回一个错误。
     **/
    @Test
    public void linsert() {
        long st = jedis.linsert(key, ListPosition.BEFORE,"foo","good");
        print(st);
    }

    /**
     * 当列表 source 为空时， BRPOPLPUSH 命令将阻塞连接，直到等待超时，
     * 或有另一个客户端对 source 执行 LPUSH key value [value …]
     * 或 RPUSH key value [value …] 命令为止
     **/
    @Test
    public void brpoplpush() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Jedis jedis = createJedis();
                 jedis.lpush(key,"a");
            }
        }).start();
        Thread.sleep(1000);
        String ele = jedis.brpoplpush(key,dstKey,0);
        print(ele);
        print(jedis.llen(dstKey));
        print(jedis.lrange(dstKey,0,-1));

    }
}
