package com.moriartyzzb.acitvemqproject.controller;

import com.moriartyzzb.acitvemqproject.operator.ActivemqOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zengzhibin
 * @title: MqController
 * @projectName acitve-mq-project
 * @description: TODO
 * @date 2021/2/23 14:31
 */
@RequestMapping("/testMq")
@RestController
public class MqController {
    private final ActivemqOperator activemqOperator;
    //构造方式注入bean
    @Autowired
    public MqController(ActivemqOperator activemqOperator) {
        this.activemqOperator = activemqOperator;
    }

    @GetMapping("/queue")
    public String testMqQueue() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("当前订单【123456789】生成时间： "+df.format(new Date()));// new Date()为获取当前系统时间
        //设置延时时间推送到activeMQ
        activemqOperator.delaySend("testQueue","延时10S",10000L);
        return "OK";
    }

    @JmsListener(destination = "testQueue", containerFactory = "queueJmsListenerContainerFactory")
    public void listenerMq(String message) {
        //业务逻辑,根据单号查询是否已经付款
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("当前订单【123456789】失效时间： "+df.format(new Date()));// new Date()为获取当前系统时间
    }
}
