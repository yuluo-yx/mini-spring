package indi.yuluo.springframework.context.event;

import indi.yuluo.springframework.context.ApplicationContext;
import indi.yuluo.springframework.context.ApplicationEvent;

/**
 * @author yuluo
 * @createTime 2023-02-02  11:47
 */

public class ApplicationContextEvent extends ApplicationEvent {

	/**
	 * Constructs a prototypical Event.
	 *
	 * @param source the object on which the Event initially occurred
	 * @throws IllegalArgumentException if source is null
	 */
	public ApplicationContextEvent(Object source) {
		super(source);
	}

	/**
	 * Get the <code>ApplicationContext</code> that the event was raised for
	 * 获取<code>引发事件的应用程序上下文<code>
	 * @return
	 */
	public final ApplicationContext getApplicationContext() {
		return (ApplicationContext) getSource();
	}

}
