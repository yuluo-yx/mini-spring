package indi.yuluo.springframework.test.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import indi.yuluo.springframework.beans.factory.FactoryBean;

/**
 *@author yuluo
 *@createTime 2023-02-01  22:47
 * 这是一个实现接口 FactoryBean 的代理类 ProxyBeanFactory 名称，
 * 主要是模拟了 UserDao 的原有功能，类似于 MyBatis 框架中的代理操作
 */

public class ProxyBeanFactory implements FactoryBean<IUserDao> {

	/**
	 * getObject() 中提供的就是一个 InvocationHandler 的代理对象，
	 * 当有方法调用的 时候，则执行代理对象的功能
	 * @return
	 */
	@Override
	public IUserDao getObject() {
		InvocationHandler handler = (proxy, method, args) -> {

			// 添加排除方法
			if ("toString".equals(method.getName())) return this.toString();

			Map<String, String> hashMap = new HashMap<>();
			hashMap.put("10001", "小傅哥");
			hashMap.put("10002", "八杯水");
			hashMap.put("10003", "阿毛");

			return "你被代理了 " + method.getName() + "：" + hashMap.get(args[0].toString());
		};
		return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, handler);
	}

	@Override
	public Class<?> getObjectType() {
		return IUserDao.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
