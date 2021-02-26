package com.moriartyzzb.acitvemqproject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Zengzhibin
 * @title: RedisLockUtil
 * @projectName acitve-mq-project
 * @description: TODO
 * @date 2021/2/25 17:08
 */
@Component
public class RedisLockUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final ThreadLocal<String> tlExpireTime = new ThreadLocal<>();

    public RedisLockUtil() {

    }

    public RedisLockUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 在一定的限定时间内 获取某一类资源或操作的锁
     *
     * @param key 键名（可以随便指定；一般为某一类资源或操作命名）
     * @return true or false
     */
    public Boolean lock(String key) throws InterruptedException {
        //设置最长过期时间
        Boolean isExists = redisTemplate.boundValueOps(key).setIfAbsent(key, 100, TimeUnit.SECONDS);
        //获取锁成功，直接返回
        if (isExists) {
            //记录本线程设置的过期时间，释放锁的时候可以根据其进行判断是否该释放锁
            long expireTime = System.currentTimeMillis() + 5000;
            return true;
        } else {
            return false;
        }
    }

    /**
     * 释放某一类资源或操作的锁
     *
     * @param key key 键名（与加锁时候的key要一致）
     */
    public void unlock(String key) {
        Long expireTimeMillis = redisTemplate.boundValueOps(key).getExpire();
        if (expireTimeMillis < 10) {
            redisTemplate.delete(key);
        }
    }
}
