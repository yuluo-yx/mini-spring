package indi.yuluo.springframework.test.bean;

import java.lang.reflect.Proxy;

import indi.yuluo.springframework.beans.factory.FactoryBean;

/**
 * @author yuluo
 * @createTime 2023-02-07  20:25
 * @des 代理类
 */

public class HusbandMother implements FactoryBean<IMother> {

	@Override
	public IMother getObject() throws Exception {
		return (IMother) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IMother.class}, (proxy, method, args) -> "婚后媳妇妈妈的职责被婆婆代理了！" + method.getName());
	}

	@Override
	public Class<?> getObjectType() {
		return IMother.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
