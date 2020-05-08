package com.ibeidan.test;

import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author lee
 * DATE 2020/5/8 15:20
 */
public class SortedSetCommandTest extends AbstractRedisTest{

    private String key = "sorted:set";

    private Set<String> sortedSet = new LinkedHashSet<String>();

    private long status = 0;

    /**
     *todo 时间复杂度: O(M*log(N))， N 是有序集的基数， M 为成功添加的新成员的数量。
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     **/
    @Test
    public void zadd() {
        status = jedis.zadd(key,1d,"a");
        print(status);
    }

}
