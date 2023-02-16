package indi.yuluo.springframework.test.event;

import indi.yuluo.springframework.context.ApplicationEvent;

/**
 * @author yuluo
 * @createTime 2023-02-02  14:53
 * 创建一个自定义事件，在事件类的构造函数中可以添加自己的想要的入参信息。
 * 这个事件类最终会被完成的拿到监听里，所以你添加的属性都会被获得到
 */

public class CustomerEvent extends ApplicationEvent {

	private Long id;

	private String message;

	/**
	 * Constructs a prototypical Event.
	 *
	 * @param source the object on which the Event initially occurred
	 * @throws IllegalArgumentException if source is null
	 */
	public CustomerEvent(Object source, Long id, String message) {
		super(source);
		this.id = id;
		this.message = message;
	}

	String getMessage() {
		return message;
	}

	Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	void setMessage(String message) {
		this.message = message;
	}
}
