package com.ibeidan.core.lock.redis;

import com.ibeidan.core.lock.ILock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>可重复性锁</p>
 * @author lee
 * DATE 2020/5/20 10:44
 */
public class RedisReentrantLock implements ILock {


    // 存储每个线程获取锁的信息
    private final ConcurrentHashMap<Thread, LockData> threadData = new ConcurrentHashMap<>();

    // 普通Redis分布式锁
    private RedisLock redisLock;

    // 创建一个可重入锁,指定名字
    public RedisReentrantLock(String lockName) {
        redisLock = new RedisLock(lockName);
    }
    // 创建一个可重入锁，指定名字和锁过期时间
    public RedisReentrantLock(String lockName,int lockTimeot) {
        redisLock = new RedisLock(lockName,lockTimeot);
    }

    //定义锁信息
    private static class LockData {
        //当前线程
        final Thread currentThread ;
        // 加锁key对应的值
        final String lockId;
        // 加锁的次数
        final AtomicInteger lockCount = new AtomicInteger(1);

        public LockData(Thread currentThread, String lockId) {
            this.currentThread = currentThread;
            this.lockId = lockId;
        }

        public String getLockId() {
            return lockId;
        }
    }

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
        // 获取当前线程
        Thread currentThread = Thread.currentThread();
        // 获取当前线程的锁信息
        LockData lockData = threadData.get(currentThread);
        String lockId = null;
        // 该线程已经拥有锁，加锁次数加1
        if (lockData != null) {
            lockData.lockCount.incrementAndGet();
            //更新锁过期时间
            redisLock.updateKey(threadData.get(currentThread).getLockId());
            return threadData.get(currentThread).getLockId();
        }else {
            //第一次加锁
            lockId = redisLock.tryLock(tryLockTime,timeUnit);
            //加锁成功，记录当前线程
            if (lockId != null) {
                LockData newLockData = new LockData(currentThread,lockId);
                threadData.put(currentThread,newLockData);
                return lockId;
            }
        }

        //执行此处说明加锁失败
        return lockId;
    }

    //释放可重入锁
    @Override
    public void unLock(String lockId) {
        // 获得当前线程
        Thread currentThread = Thread.currentThread();
        // 获取当前加锁信息
        LockData lockData = threadData.get(currentThread);
        //没有找到加锁信息抛出异常
        if (lockData == null) {
            throw new IllegalMonitorStateException("该线程未持有锁，无法释放："+lockId);
        }
        // 加锁次数 -1
        int newLockCount = lockData.lockCount.decrementAndGet();
        // 如果还持有锁，返回
        if (newLockCount > 0){
            return;
        }
        // 释放锁异常
        if (newLockCount < 0){
            throw new IllegalMonitorStateException("释放锁异常：" + lockId);
        }
        try {
            //释放锁
            redisLock.unLock(lockId);
        }finally {
            // 删除该锁信息
            threadData.remove(currentThread);
        }

    }
}
