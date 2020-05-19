package com.ibeidan.core.lock.redis;

import com.ibeidan.core.lock.ILock;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author lee
 * DATE 2020/5/19 14:15
 */
public class RedisLock  implements ILock {

    private static final String LOCK_SUCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX ";

    private Jedis jedis;

    private String lockKey;

    public RedisLock(Jedis jedis) {
        this.jedis = jedis;
    }

    // 默认锁过期时间 30s
    private int lockTimeout = 30000;

    // 重试时间 0.5s, 这里的单位是毫秒
    private int retryTime = 500;

    @Override
    public String lock() {
        return null;
    }

    @Override
    public String tryLock() {
        return null;
    }

    @Override
    public String tryLock(long tryLockTime, TimeUnit timeUnit) {
        //判断是否死循环获取锁
        boolean forever = tryLockTime < 0;
        final long startTime = System.currentTimeMillis();
        if (tryLockTime < 0){
            tryLockTime = 0;
        }
        //统一转换为毫秒
        final Long tryTime = (timeUnit !=null ) ? timeUnit.toMillis(tryLockTime) : 0;

        String lockSuccess = null;
        while (lockSuccess == null){
            lockSuccess = this.doLock();
            // 获取成功 退出
            if (lockSuccess != null){
                break;
            }

            //如果不是必须获取到锁，超过了获取锁的最长时间，退出
            if (!forever && System.currentTimeMillis() - startTime -retryTime > tryTime){
                break;
            }

            // 睡眠重试时间，避免太频繁的重试
            LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(retryTime));

        }


        return lockSuccess;
    }

    private String doLock() {
        String lockId = UUID.randomUUID().toString();

        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.px(lockTimeout);
        //尝试加锁，如果成功返回OK
        String result =  jedis.set("ee",lockId,setParams);
        //加锁成功返回value的值，用于解锁
       return LOCK_SUCESS.equals(result) ? lockId : null;
    }

    @Override
    public void unLock(String lockId) {

        //如果get key的值和预期一样 就删除该key
        //保证由加锁的人来解锁
        //其中ARGV[1]表示设置key时指定的随机值-lockId
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                "then return redis.call('del' , KEYS[1]) " +
                " else return 0 end";

        List list = (List) jedis.eval(luaScript, Collections.singletonList(lockKey) ,Collections.singletonList(lockId));


    }


}
