package indi.yuluo.springframework.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.FactoryBean;

/**
 *@author yuluo
 *@createTime 2023-02-01  22:27
 */

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {


	/**
	 * Cache of singleton objects created by FactoryBeans: FactoryBean name --> object
	 * 由 FactoryBean 创建的单例对象的缓存：FactoryBean name --> object
	 */
	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<String, Object>();

	protected Object getCachedObjectForFactoryBean(String beanName) {
		Object object = this.factoryBeanObjectCache.get(beanName);
		return (object != NULL_OBJECT ? object : null);
	}

	protected Object getObjectFromFactoryBean(FactoryBean factory, String beanName) {
		if (factory.isSingleton()) {
			Object object = this.factoryBeanObjectCache.get(beanName);
			if (object == null) {
				object = doGetObjectFromFactoryBean(factory, beanName);
				this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
			}
			return (object != NULL_OBJECT ? object : null);
		} else {
			return doGetObjectFromFactoryBean(factory, beanName);
		}
	}

	private Object doGetObjectFromFactoryBean(final FactoryBean factory, final String beanName){
		try {
			return factory.getObject();
		} catch (Exception e) {
			throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
		}
	}


}
