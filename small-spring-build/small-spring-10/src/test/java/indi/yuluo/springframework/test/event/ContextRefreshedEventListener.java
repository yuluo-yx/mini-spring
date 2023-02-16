package indi.yuluo.springframework.test.event;

import indi.yuluo.springframework.context.ApplicationListener;
import indi.yuluo.springframework.context.event.ContextRefreshedEvent;

/**
 * @author yuluo
 * @createTime 2023-02-02  15:01
 */

public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		System.out.println("刷新事件：" + this.getClass().getName());
	}
}
