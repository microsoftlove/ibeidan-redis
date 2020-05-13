package com.ibeidan.test;

import org.junit.Test;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.ZParams;
import redis.clients.jedis.params.ZAddParams;
import redis.clients.jedis.params.ZIncrByParams;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lee
 * DATE 2020/5/8 15:20
 */
public class SortedSetCommandTest extends AbstractRedisTest{

    private String key = "sorted:set";

    private Set<String> sortedSet = new LinkedHashSet<>();

    private long status = 0;

    /**
     *todo 时间复杂度: O(M*log(N))， N 是有序集的基数， M 为成功添加的新成员的数量。
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，
     * 并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     **/
    @Test
    public void zadd() {
        status = jedis.zadd(key,2d,"a");
        print(status);

        status = jedis.zadd(key,8d,"b");
        print(status);

        status = jedis.zadd(key,9d,"c");
        print(status);

    }

    @Test
    public void zaddWithParams(){

        //xx : never add new member , Only set the key if it already exist.
        status = jedis.zadd(key,2d,"g", ZAddParams.zAddParams().xx());
        print(status);

        //nx : never update  current member (score don't change), Only set the key if it does not already exist.
        status = jedis.zadd(key,4d,"f",ZAddParams.zAddParams().nx());
        print(status);

        Map<String,Double> scoreMembers = new HashMap<>();
        scoreMembers.put("g",5d);
        scoreMembers.put("h",6d);

        //ch : return count of members not only added ,but also updated
        //Modify the return value from the number of new elements added to the total number of elements
        //changed
        status = jedis.zadd(key,scoreMembers,ZAddParams.zAddParams().ch());
        print(status);

    }


    /**
     * todo  时间复杂度: O(log(N)+M)， N 为有序集的基数，而 M 为结果集的基数。
     * 返回有序集 key 中，指定区间内的成员。
     *
     **/
    @Test
    public void zrange() {
         sortedSet = jedis.zrange(key,0,1);
         print(sortedSet);

         sortedSet = jedis.zrange(key,0,-1);
         print(sortedSet);
    }

    /**
     * todo 时间复杂度：O(log(N)+M)， 其中 N 为有序集合的元素数量， 而 M 则是命令返回的元素数量。
     * todo 如果 M 是一个常数（比如说，用户总是使用 LIMIT 参数来返回最先的 10 个元素），
     * todo 那么命令的复杂度也可以看作是 O(log(N)) 。
     * 当有序集合的所有成员都具有相同的分值时， 有序集合的元素会根据成员的字典序
     * （lexicographical ordering）来进行排序，
     * 而这个命令则可以返回给定的有序集合键 key 中， 值介于 min 和 max 之间的成员。
     * 如果有序集合里面的成员带有不同的分值， 那么命令返回的结果是未指定的
     *
     * 如何指定范围区间
     * 合法的 min 和 max 参数必须包含 ( 或者 [ ， 其中 ( 表示开区间（指定的值不会被包含在范围之内）， 而 [ 则表示闭区间（指定的值会被包含在范围之内）。
     * 特殊值 + 和 - 在 min 参数以及 max 参数中具有特殊的意义， 其中 + 表示正无限， 而 - 表示负无限。 因此， 向一个所有成员的分值都相同的有序集合发送命令 ZRANGEBYLEX <zset> - + ， 命令将返回有序集合中的所有元素。
     **/
    @Test
    public void zrangByLex(){
        jedis.zadd(key,1,"aaa");
        jedis.zadd(key,1,"ca");
        jedis.zadd(key,1,"bba");
        jedis.zadd(key,1,"da");

        // exclusive aa ~ inclusive c
        sortedSet = jedis.zrangeByLex(key,"(aa","[c");
        print(sortedSet);

        sortedSet = jedis.zrangeByLex(key,"-","+",1,2);
        print(sortedSet);

    }

    /**
     * todo 时间复杂度: O(log(N)+M)， N 为有序集的基数，而 M 为结果集的基数。
     * 其中成员的位置按 score 值递减(从大到小)来排列。
     **/
    @Test
    public void zrevrange() {
        sortedSet = jedis.zrevrange(key,0,200);
        print(sortedSet);
    }

    /**
     * todo 时间复杂度: O(M*log(N))， N 为有序集的基数， M 为被成功移除的成员的数量。
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略
     **/
    @Test
    public void zrem() {
        status = jedis.zrem(key,"bb");
        print(status);
    }

   /**
    * todo 时间复杂度: O(log(N))
    * 为有序集 key 的成员 member 的 score 值加上增量 increment 。
    * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。
    * 返回值
    * member 成员的新 score 值，以字符串形式表示 客户端工具自动转换
    **/
    @Test
    public void zincrby() {
        double score = jedis.zincrby(key,7d,"aa");
        print(score);
    }

    /**
     * 带参数添加
     **/
    @Test
    public void zincrbyWithParams() {
        // xx : never add new member, update score,Only set the key if it already exist.
       Double score = jedis.zincrby(key,8d,"aa", ZIncrByParams.zIncrByParams().xx());
       print(score);

       // nx : never update current member ,Only set the key if it does not already exist.
       score = jedis.zincrby(key,8d,"aaa",ZIncrByParams.zIncrByParams().nx());
       print(score);
    }

    /**
     *todo 时间复杂度: O(log(N))
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
     *
     * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
     **/
    @Test
    public void zrank() {
        status = jedis.zrank(key,"aa");
        print(status);
    }


    /**
     * todo 时间复杂度: O(log(N))
     * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。
     *
     * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
     **/
    @Test
    public void zrevrank() {
        status = jedis.zrevrank(key,"aaa");
        print(status);
    }


    /**
     * todo 时间复杂度: O(log(N)+M)， N 为有序集的基数， M 为被结果集的基数。
     * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * 有序集成员按 score 值递增(从小到大)次序排列。
     **/
    @Test
    public void zrangeWithScores() {
        Set<Tuple> tuples = jedis.zrangeWithScores(key,0,1);
        print(tuples);
    }


    /**
     * todo 时间复杂度: O(log(N)+M)， N 为有序集的基数， M 为结果集的基数。
     * 返回有序集 key 中， score 值介于 max 和 min 之间(默认包括等于 max 或 min )的所有的成员。
     * 有序集成员按 score 值递减(从大到小)的次序排列。
     **/
    @Test
    public void zrevrangeWithScores() {
        Set<Tuple> range = jedis.zrevrangeWithScores(key,0,1);
        print(range);
    }

    /**
     * 返回有序集 key 的基数。
     **/
    @Test
    public void zcard () {
        status = jedis.zcard(key) ;
        print(status);
    }

    /**
     * 返回有序集 key 中，成员 member 的 score 值。
     **/
    @Test
    public void zscore (){
        Double score = jedis.zscore(key,"b");
        print(score);
    }

    /**
     * todo O(log(N)*M)  N 为有序集的基数， M 为结果集的基数。
     * 删除并返回有序集合key中的最多count个具有最高得分的成员。
     **/
    @Test
    public void zpopmax(){
        Tuple tuple = jedis.zpopmax(key);
        print(tuple);
    }

    /**
     * 删除并返回有序集合key中的最多count个具有最高得分的成员。
     * 如未指定，count的默认值为1。指定一个大于有序集合的基数的count不会产生错误。
     * 当返回多个元素时候，得分最高的元素将是第一个元素，然后是分数较低的元素。
     **/
    @Test
    public void zpopmaxWithCount(){
        Set<Tuple> tuples = jedis.zpopmax(key,2);
        print(tuples);
    }

    /**
     * todo O(log(N)*M)  N 为有序集的基数， M 为结果集的基数。
     * 删除并返回有序集合key中的最多count个具有最低得分的成员。
     * 如未指定，count的默认值为1。指定一个大于有序集合的基数的count不会产生错误。
     * 当返回多个元素时候，得分最低的元素将是第一个元素，然后是分数较高的元素。
     **/
    @Test
    public void zpopmin(){
        Set<Tuple> tuples = jedis.zpopmin(key,2);
        print(tuples);
    }

    /**
     * todo 时间复杂度: O(log(N))， N 为有序集的基数。
     * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
     **/
    @Test
    public void zcount(){
       long zcount = jedis.zcount(key,0,2);
        print(zcount);
    }

    /**
     * todo 时间复杂度： O(log(N))，其中 N 为有序集合包含的元素数量。
     * 对于一个所有成员的分值都相同的有序集合键 key 来说， 这个命令会返回该集合中， 成员介于 min 和 max 范围内的元素数量。
     **/
    @Test
    public void zlexcount(){
        long reuslt = jedis.zlexcount(key,"[a","(c");
        print(reuslt);
    }

    /**
     * todo 时间复杂度： O（ log（N）+ M），其中 N 是有序集合中元素的数量，M 是返回元素的数量。
     * 如果 M 是常量（例如，总是要求使用 LIMIT 的前10个元素），则可以将其视为O（ log（N））。
     * 在key中，min与max之间的分数返回排序集合中的所有元素（包括分数等于min或max的元素）。
     * 这些元素被认为是从低到高排序。
     **/
    @Test
    public void zrangebyscore() {
        sortedSet = jedis.zrangeByScore(key,0,5d);
        print(sortedSet);

        sortedSet = jedis.zrangeByScore(key,0,5d,0,1);
        print(sortedSet);
    }

    /**
     * todo 时间复杂度： O（ log（N）+ M），其中 N 是有序集合中元素的数量，M 是返回元素的数量。
     * 如果 M 是常量（例如，总是要求使用 LIMIT 的前10个元素），则可以将其视为 O（ log（N））。
     *
     * 在key中，max和min之间的分数返回排序集合中的所有元素（包括分数等于max或min的元素）。
     * 与排序集的默认排序相反，对于此命令，这些元素被认为是从高分到低排序。
     **/
    @Test
    public void zrevrangebyscore() {
        sortedSet = jedis.zrevrangeByScore(key,5,0);
        print(sortedSet);

        sortedSet = jedis.zrevrangeByScore(key,5,0,0,1);
        print(sortedSet);
    }


    /**
     * todo 时间复杂度: O(log(N)+M)， N 为有序集的基数，而 M 为被移除成员的数量
     * 移除有序集 key 中，指定排名(rank)区间内的所有成员。
     **/
    @Test
    public void zremrangeByRank() {
        status = jedis.zremrangeByRank(key,0,0);
        print(status);
    }

    /**
     * todo 时间复杂度： O(log(N)+M)， N 为有序集的基数，而 M 为被移除成员的数量。
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     **/
    @Test
    public void zremrangeByScore(){
        status = jedis.zremrangeByScore(key,0,2);
        print(status);
    }

    /**
     * todo 时间复杂度：O(log(N)+M)， 其中 N 为有序集合的元素数量，
     * 而 M 则是命令返回的元素数量。 如果 M 是一个常数
     * （比如说，用户总是使用 LIMIT 参数来返回最先的 10 个元素），
     * 那么命令的复杂度也可以看作是 O(log(N)) 。
     * 当有序集合的所有成员都具有相同的分值时，
     * 有序集合的元素会根据成员的字典序（lexicographical ordering）来进行排序，
     * 而这个命令则可以返回给定的有序集合键 key 中， 值介于 min 和 max 之间的成员。
     **/
    @Test
    public void zremrangeByLex() {
        status = jedis.zremrangeByLex(key,"[aa","(c");
        print(status);
    }

    private String dstKey = "sorted:dist";

    /**
     * todo 时间复杂度: O(N)+O(M log(M))， N 为给定有序集基数的总和， M 为结果集的基数。
     * 计算给定的一个或多个有序集的并集，其中给定 key 的数量必须以 numkeys 参数指定，
     * 并将该并集(结果集)储存到 destination 。
     * 默认情况下，结果集中某个成员的 score 值是所有给定集下该成员 score 值之 和 。
     **/
    @Test
    public void zunionstore() {
        status = jedis.zunionstore(dstKey,key);
        print(status);
    }

    /**
     * 使用 AGGREGATE 选项，你可以指定并集的结果集的聚合方式。
     *
     * 默认使用的参数 SUM ，可以将所有集合中某个成员的 score 值之 和 作为结果集中该成员的 score 值；
     * 使用参数 MIN ，可以将所有集合中某个成员的 最小 score 值作为结果集中该成员的 score 值；
     * 而参数 MAX 则是将所有集合中某个成员的 最大 score 值作为结果集中该成员的 score 值。
     **/
    @Test
    public void zunionsstoreParams(){
        ZParams params = new ZParams();
       // params.weights(2,2.5);
        params.aggregate(ZParams.Aggregate.SUM);
        status = jedis.zunionstore(dstKey,params,key);
        print(status);

    }


    /**
     * todo 时间复杂度: O(N*K)+O(M*log(M))，
     * N 为给定 key 中基数最小的有序集， K 为给定有序集的数量， M 为结果集的基数。
     * 计算给定的一个或多个有序集的交集，
     * 其中给定 key 的数量必须以 numkeys 参数指定，并将该交集(结果集)储存到 destination 。
     *
     * 返回值
     * 保存到 destination 的结果集的基数。
     **/
    @Test
    public void zinterstore() {
        status = jedis.zinterstore(dstKey,key);
        print(status);
    }

    @Test
    public void zscan(){
        ScanResult<Tuple> result = jedis.zscan(key,String.valueOf(0));
        print(result.getResult());
    }


}
