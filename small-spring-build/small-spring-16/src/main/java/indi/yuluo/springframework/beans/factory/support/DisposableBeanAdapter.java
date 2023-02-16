package indi.yuluo.springframework.beans.factory.support;

import java.lang.reflect.Method;

import cn.hutool.core.util.StrUtil;
import indi.yuluo.springframework.beans.BeansException;
import indi.yuluo.springframework.beans.factory.DisposableBean;
import indi.yuluo.springframework.beans.factory.config.BeanDefinition;

/**
 *@author yuluo
 *@createTime 2023-01-30  12:50
 */

public class DisposableBeanAdapter implements DisposableBean {

	private final Object bean;
	private final String beanName;
	private String destroyMethodName;

	public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
		this.bean = bean;
		this.beanName = beanName;
		this.destroyMethodName = beanDefinition.getDestroyMethodName();
	}

	@Override
	public void destroy() throws Exception {

		// 实现接口 DisposableBean
		if (bean instanceof DisposableBean) {
			((DisposableBean) bean) .destroy();
		}

		// 配置信息destory-method {判断是为了二次执行销毁}
		if (StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
			Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
			if (null == destroyMethod) {
				throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
			}
			destroyMethod.invoke(bean);
		}

	}
}
