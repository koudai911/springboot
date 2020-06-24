package com.study.cache.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author  jackl
 * @since 1.0
 */
public interface DistributedReentrantLock {
     boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException;

     void unlock();
}
