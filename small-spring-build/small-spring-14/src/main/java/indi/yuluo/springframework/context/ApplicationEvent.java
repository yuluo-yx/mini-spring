package indi.yuluo.springframework.context;

import java.util.EventObject;

/**
 *@author yuluo
 *@createTime 2023-02-02  11:46
 * 以继承 java.util.EventObject 定义出具备事件功能的抽象类 ApplicationEvent
 * 后续所有事件的类都需要继承这个类
 */

public abstract class ApplicationEvent extends EventObject {

	/**
	 * Constructs a prototypical Event.
	 *
	 * @param source the object on which the Event initially occurred
	 * @throws IllegalArgumentException if source is null
	 */
	public ApplicationEvent(Object source) {
		super(source);
	}

}
