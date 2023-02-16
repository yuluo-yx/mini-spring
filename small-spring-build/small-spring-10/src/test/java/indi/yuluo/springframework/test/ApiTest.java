package indi.yuluo.springframework.test;

import indi.yuluo.springframework.context.support.ClassPathXmlApplicationContext;
import indi.yuluo.springframework.test.event.CustomerEvent;
import org.junit.Test;

public class ApiTest {

	@Test
	public void test_event() {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		applicationContext.publishEvent(new CustomerEvent(applicationContext, 1019129009086763L, "成功了！"));

		applicationContext.registerShutdownHook();
	}

}
