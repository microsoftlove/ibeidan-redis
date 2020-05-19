package com.ibeidan.core.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author lee
 * DATE 2020/5/19 14:10
 */
public interface ILock {


    String lock();


    String tryLock();


    String tryLock(long tryLockTime, TimeUnit timeUnit);


    void unLock(String lockId);

}
