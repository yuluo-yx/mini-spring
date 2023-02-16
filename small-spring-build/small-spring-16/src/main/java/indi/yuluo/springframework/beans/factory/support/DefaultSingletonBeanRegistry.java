package indi.yuluo.springframework.beans.factory.support;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import indi.yuluo.springframework.beans.factory.ObjectFactory;
import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.DisposableBean;
import indi.yuluo.springframework.beans.factory.config.SingletonBeanRegistry;

/**
 * @author yuluo
 * @createTime 2023-01-25  22:16
 * 在 DefaultSingletonBeanRegistry 中主要实现 getSingleton 方法，
 * 同时实现了一个 受保护的 addSingleton 方法，这个方法可以被继承此类的其他类调用。
 * 包括：AbstractBeanFactory 以及继承的 DefaultListableBeanFactory 调用。
 */

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	/**
	 * Internal marker for a null singleton object:
	 * used as marker value for concurrent Maps (which don't support null values).
	 */
	protected static final Object NULL_OBJECT = new Object();

	// 一级缓存，普通对象
	/**
	 * Cache of singleton objects: bean name --> bean instance
	 */
	private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	// 二级缓存，提前暴漏对象，没有完全实例化的对象
	/**
	 * Cache of early singleton objects: bean name --> bean instance
	 */
	protected final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>();

	// 三级缓存，存放代理对象
	/**
	 * Cache of singleton factories: bean name --> ObjectFactory
	 */
	private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<String, ObjectFactory<?>>();

	private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();

	@Override
	public Object getSingleton(String beanName) {
		Object singletonObject = singletonObjects.get(beanName);
		if (null == singletonObject) {
			singletonObject = earlySingletonObjects.get(beanName);
			// 判断二级缓存中是否有对象，这个对象就是代理对象，因为只有代理对象才会放到三级缓存中
			if (null == singletonObject) {
				ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
				if (singletonFactory != null) {
					singletonObject = singletonFactory.getObject();
					// 把三级缓存中的代理对象中的真实对象获取出来，放入二级缓存中
					earlySingletonObjects.put(beanName, singletonObject);
					singletonFactories.remove(beanName);
				}
			}
		}
		return singletonObject;
	}

	public void registerSingleton(String beanName, Object singletonObject) {
		singletonObjects.put(beanName, singletonObject);
		earlySingletonObjects.remove(beanName);
		singletonFactories.remove(beanName);
	}

	protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
		if (!this.singletonObjects.containsKey(beanName)) {
			this.singletonFactories.put(beanName, singletonFactory);
			this.earlySingletonObjects.remove(beanName);
		}
	}

	public void registerDisposableBean(String beanName, DisposableBean bean) {
		disposableBeans.put(beanName, bean);
	}

	public void destroySingletons() {
		Set<String> keySet = this.disposableBeans.keySet();
		Object[] disposableBeanNames = keySet.toArray();

		for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
			Object beanName = disposableBeanNames[i];
			DisposableBean disposableBean = disposableBeans.remove(beanName);
			try {
				disposableBean.destroy();
			}
			catch (Exception e) {
				throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
			}
		}
	}

}

