package indi.yuluo.springframework.test.bean;

import java.lang.reflect.Method;

import indi.yuluo.springframework.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author yuluo
 * @createTime 2023-02-03  22:32
 * @des null
 */

public class UserServiceBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("拦截方法：" + method.getName());
	}
}
