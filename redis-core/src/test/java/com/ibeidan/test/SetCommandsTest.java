package com.ibeidan.test;

import org.junit.Test;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Set;

/**
 * @author lee
 * DATE 2020/5/7 15:44
 */
public class SetCommandsTest extends AbstractRedisTest{

    private String key = "set:test";

    private String disKey = "set:distest";

    /**
     * TODO 时间复杂度: O(N)， N 是被添加的元素的数量。
     * 将一个或多个 member 元素加入到集合 key 当中，
     * 已经存在于集合的 member 元素将被忽略。
     * 返回值：被添加到集合中的新元素的数量，不包括被忽略的元素。
     **/
    @Test
    public void sadd(){
        long status = jedis.sadd(key,"alarm","bike","car","disk");
        print(status);
    }

    /**
     * TODO 时间复杂度: O(N)， N 为集合的基数。
     * 返回集合 key 中的所有成员。
     **/
    @Test
    public void smembers() {
        Set<String> set = jedis.smembers(key);
        print(set);
    }

    /**
     * TODO 时间复杂度: O(N)， N 为给定 member 元素的数量。
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * 返回值：被成功移除的元素的数量，不包括被忽略的元素。
     **/
    @Test
    public void srem(){
       long status =  jedis.srem(key,"alarm");
       print(status);
    }

    /**
     * 移除并返回集合中的一个随机元素。
     * 返回值：被移除的随机元素。
     **/
    @Test
    public void spop () {
        String member = jedis.spop(key);
        print(member);
    }

    @Test
    public void spopWithCount(){
        Set<String> set = jedis.spop(key,2);
        print(set);
    }

    /**
     * 将 member 元素从 source 集合移动到 destination 集合。
     * SMOVE 是原子性操作。
     *
     * 如果 source 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 0 。
     *
     * 否则， member 元素从 source 集合中被移除，并添加到 destination 集合中去。
     *
     * 当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
     *
     * 当 source 或 destination 不是集合类型时，返回一个错误。
     *
     * 返回值 ： 如果 member 元素被成功移除，返回 1，否则返回 0。
     **/
    @Test
    public void smove() {
        long status = jedis.smove(key,disKey,"car");
        print(status);
    }

    /**
     * 返回集合 key 的基数(集合中元素的数量)。
     * 返回值：集合的基数。 当 key 不存在时，返回 0
     **/
    @Test
    public void scard(){
        long card = jedis.scard(key);
        print(card);
    }

    /**
     * 判断 member 元素是否集合 key 的成员。
     * 返回值：如果 member 元素是集合的成员，返回 1 。
     * 如果 member 元素不是集合的成员，或 key 不存在，返回 0 。
     **/
    @Test
    public void sismember(){
        boolean flag = jedis.sismember(key,"car");
        print(flag);
    }

    /**
     * TODO 时间复杂度: O(N * M)， N 为给定集合当中基数最小的集合， M 为给定集合的个数。
     * 返回一个集合的全部成员，该集合是所有给定集合的【交集】。
     * 返回值：交集成员的列表。
     **/
    @Test
    public void sinter(){
       Set<String> set =  jedis.sinter(key,disKey);
       print(set);
    }


    /**
     * TODO 时间复杂度: O(N * M)， N 为给定集合当中基数最小的集合， M 为给定集合的个数。
     * 这个命令类似于 SINTER key [key …] 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     *
     * TODO 如果 destination 集合已经存在，则将其覆盖。
     *
     * destination 可以是 key 本身。
     *
     * 返回值：结果集中的成员数量。
     **/
    @Test
    public void sinterstore() {
        long status = jedis.sinterstore(disKey,key);
        print(status);
    }

    /**
     * TODO 时间复杂度: O(N)， N 是所有给定集合的成员数量之和。
     * 返回一个集合的全部成员，该集合是所有给定集合的【并集】。
     **/
    @Test
    public void sunion() {
        Set<String> set = jedis.sunion(key,disKey);
        print(set);
    }

    /**
     * TODO 时间复杂度: O(N)， N 是所有给定集合的成员数量之和。
     * 这个命令类似于 SUNION key [key …] 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     *
     * 如果 destination 已经存在，则将其覆盖。
     *
     * destination 可以是 key 本身。
     *
     * 返回值:结果集中的元素数量。
     **/
    @Test
    public void sunionstore() {
        long status = jedis.sunionstore(disKey,key);
        print(status);
    }


    /**
     * TODO 时间复杂度: O(N)， N 是所有给定集合的成员数量之和。
     * 返回一个集合的全部成员，该集合是所有给定集合之间的差集。
     * 返回值:一个包含差集成员的列表。
     **/
    @Test
    public void sdiff () {
        Set<String> set = jedis.sdiff(key,disKey);
        print(set);
    }



    /**
     * todo 时间复杂度: O(N)， N 是所有给定集合的成员数量之和。
     * 这个命令的作用和 SDIFF key [key …] 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。
     * 返回值:结果集中的元素数量。
     **/
    @Test
    public void sdiffstore() {
        long status = jedis.sinterstore(disKey,key);
        print(status);
    }

    /**
     * todo 时间复杂度: 只提供 key 参数时为 O(1) 。如果提供了 count 参数，那么为 O(N) ，N 为返回数组的元素个数。
     * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。
     * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。
     * 如果 count 大于等于集合基数，那么返回整个集合。
     * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
     **/
    @Test
    public void srandmember() {
        String member = jedis.srandmember(key);
        print(member);

        List<String> list = jedis.srandmember(key,2);
        print(list);
    }

    /**
     * @link scan命令
     **/
    @Test
    public void sscan(){
        ScanResult<String> result = jedis.sscan(key,String.valueOf(0));
        print(result.getResult());
    }

}
