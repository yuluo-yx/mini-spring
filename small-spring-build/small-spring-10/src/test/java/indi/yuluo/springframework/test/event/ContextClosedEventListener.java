package indi.yuluo.springframework.test.event;

import indi.yuluo.springframework.context.ApplicationListener;
import indi.yuluo.springframework.context.event.ContextClosedEvent;

/**
 * @author yuluo
 * @createTime 2023-02-02  15:02
 */

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		System.out.println("关闭事件：" + this.getClass().getName());
	}
}
