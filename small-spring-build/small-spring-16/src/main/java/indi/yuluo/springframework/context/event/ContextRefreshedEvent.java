package indi.yuluo.springframework.context.event;

import indi.yuluo.springframework.context.ApplicationEvent;

/**
 * @author yuluo
 * @createTime 2023-02-02  11:53
 */

public class ContextRefreshedEvent extends ApplicationEvent {

	/**
	 * Constructs a prototypical Event.
	 *
	 * @param source the object on which the Event initially occurred
	 * @throws IllegalArgumentException if source is null
	 */
	public ContextRefreshedEvent(Object source) {
		super(source);
	}

}
