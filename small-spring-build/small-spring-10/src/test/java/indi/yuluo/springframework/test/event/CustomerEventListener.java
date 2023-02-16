package indi.yuluo.springframework.test.event;

import java.util.Date;

import indi.yuluo.springframework.context.ApplicationListener;

/**
 * @author yuluo
 * @createTime 2023-02-02  14:54
 * 这个是一个用于监听 CustomEvent 事件的监听器，
 * 这里你可以处理自己想要的操作，比如一些用户注册后发送优惠券和短信通知等
 */

public class CustomerEventListener implements ApplicationListener<CustomerEvent> {

	@Override
	public void onApplicationEvent(CustomerEvent event) {
		System.out.println("收到：" + event.getSource() + "消息;时间：" + new Date());

		System.out.println("消息：" + event.getId() + ":" + event.getMessage());
	}

}
