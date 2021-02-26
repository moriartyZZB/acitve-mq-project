package com.moriartyzzb.acitvemqproject.thread;

import com.moriartyzzb.acitvemqproject.util.RedisLock;
import com.moriartyzzb.acitvemqproject.util.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zengzhibin
 * @title: MyThread
 * @projectName acitve-mq-project
 * @description: TODO
 * @date 2021/2/25 17:49
 */
@Component
public class MyThread extends Thread {

    private String name;


    public String getName2() {
        return name;
    }


    public void setName2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        RedisLock redisLock = SpringBeanUtil.getBean(RedisLock.class);
        StringRedisTemplate redisTemplate= SpringBeanUtil.getBean(StringRedisTemplate.class);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");//设置日期格式
        Boolean flag=false;
        try {
           // System.out.println(name+"当前加锁时间： " + df.format(new Date()));// new Date()为获取当前系统时间
             flag = redisLock.lock("key_lock",name);
             if (flag){
                 int testV= Integer.valueOf(redisTemplate.opsForValue().get("testV"));
                 System.out.println(name+"【"+testV+"】");
                 if(testV>0){
                     redisTemplate.opsForValue().set("testV",testV-1+"");
                     System.out.println(name+"操作成功");
                 }else{
                     System.out.println(name+"操作失败");
                 }
             }else{
                 System.out.println(name+"获取锁失败");
             }
            //System.out.println(name+"获取锁结果时间:"+ df.format(new Date())+"获取锁结果:"+flag);
            //加锁等待5秒
            //MyThread.sleep(1500);
            //System.out.println(name+"当前释放锁时间： " + df.format(new Date()));// new Date()为获取当前系统时间
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //如果拿到了 才应该释放
            if (flag){
                redisLock.unlock("key_lock");
            }

        }

    }
}
