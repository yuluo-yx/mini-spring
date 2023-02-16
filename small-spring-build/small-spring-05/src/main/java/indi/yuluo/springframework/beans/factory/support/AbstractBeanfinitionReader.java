package indi.yuluo.springframework.beans.factory.support;

import indi.yuluo.springframework.beans.factory.config.BeanDefinition;
import indi.yuluo.springframework.core.io.DefaultResourceLoader;
import indi.yuluo.springframework.core.io.ResourceLoader;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:22
 */

public abstract class AbstractBeanfinitionReader implements BeanDefinitionReader {

	private final BeanDefinitionRegistry registry;

	private ResourceLoader resourceLoader;

	protected AbstractBeanfinitionReader(BeanDefinitionRegistry registry) {
		this(registry, new DefaultResourceLoader());
	}

	public AbstractBeanfinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
		this.registry = registry;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public BeanDefinitionRegistry getRegistry() {
		return registry;
	}

	@Override
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
}
