package indi.yuluo.springframework.context.event;

import indi.yuluo.springframework.context.ApplicationEvent;
import indi.yuluo.springframework.context.ApplicationListener;

/**
 * @author yuluo
 * @createTime 2023-02-02  11:56
 * 事件广播器
 */

public interface ApplicationEventMulticaster {

	/**
	 * Add a listener to be notified of all events.
	 *
	 * 添加侦听器以接收所有事件的通知。
	 * @param listener
	 */
	void addApplicationListener(ApplicationListener<?> listener);

	/**
	 * Remove a listener from the notification list.
	 *
	 * 从通知列表中删除侦听器。
	 * @param listener
	 */
	void removeApplicationListener(ApplicationListener<?> listener);

	/**
	 * Multicast the given application event to appropriate listeners.
	 *
	 * 将给定的应用程序事件多播到适当的侦听器。
	 * @param event
	 */
	void multicastEvent(ApplicationEvent event);

}
