package indi.yuluo.springframework.test.bean;

import indi.yuluo.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author yuluo
 * @createTime 2023-02-07  20:07
 * @des null
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("拦截方法：" + method.getName());
	}

}
