package com.moriartyzzb.acitvemqproject;

import com.moriartyzzb.acitvemqproject.thread.MyThread;
import com.moriartyzzb.acitvemqproject.util.RedisLockUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AcitveMqApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AcitveMqApplication.class, args);
//		SpringApplication application = new SpringApplication(AcitveMqApplication.class);
//		ConfigurableApplicationContext context = application.run(args);
//		RedisLockUtil bean = context.getBean(RedisLockUtil.class);
//		bean.lock("test");
//		for(int i=0;i<100;i++){
//			MyThread myThread = new MyThread();
//			myThread.start();
//		}
	}

}
