package indi.yuluo.springframework.test.bean;

import java.lang.reflect.Method;

import indi.yuluo.springframework.aop.MethodBeforeAdvice;

/**
 * @author yuluo
 * @createTime 2023-02-07  20:25
 * @des null
 */

public class SpouseAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("关怀小两口(切面)：" + method);
	}

}
