package indi.yuluo.springframework.beans;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:17
 * 定义创建bean异常
 */

public class BeansException extends RuntimeException {

	public BeansException(String msg) {
		super(msg);
	}

	public BeansException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
