package indi.yuluo.springframework.context;

import java.util.EventListener;

/**
 * @author yuluo
 * @createTime 2023-02-02  12:01
 */

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

	/**
	 * Handle an application event.
	 * @param event the event to respond to
	 */
	void onApplicationEvent(E event);

}
