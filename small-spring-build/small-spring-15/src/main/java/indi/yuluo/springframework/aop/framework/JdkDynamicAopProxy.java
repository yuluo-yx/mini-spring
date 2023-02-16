package indi.yuluo.springframework.aop.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import indi.yuluo.springframework.aop.AdvisedSupport;
import org.aopalliance.intercept.MethodInterceptor;

/**
 *@author yuluo
 *@createTime 2023-02-03  21:51
 */

public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

	private final AdvisedSupport advised;

	public JdkDynamicAopProxy(AdvisedSupport advised) {
		this.advised = advised;
	}

	@Override
	public Object getProxy() {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				advised.getTargetSource().getTargetClass(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
			MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
			return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
		}

		return method.invoke(advised.getTargetSource().getTarget(), args);
	}

}
