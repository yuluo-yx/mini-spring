package indi.yuluo.springframework.beans.factory.config;

import indi.yuluo.springframework.beans.factory.HierarchicalBeanFactory;
import indi.yuluo.springframework.core.convert.ConversionService;
import indi.yuluo.springframework.util.StringValueResolver;
import org.jetbrains.annotations.Nullable;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:21
 * 可获取 BeanPostProcessor、BeanClassLoader 等的一个 配置化接口
 */

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

	String SCOPE_SINGLETON = "singleton";

	String SCOPE_PROTOTYPE = "prototype";

	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

	/**
	 * 销毁单例对象
	 */
	void destroySingletons();

	/**
	 * Add a String resolver for embedded values such as annotation attributes.
	 * @param valueResolver the String resolver to apply to embedded values
	 */
	void addEmbeddedValueResolver(StringValueResolver valueResolver);

	/**
	 * Resolve the given embedded value, e.g. an annotation attribute.
	 * @param value the value to resolve
	 * @return the resolved value (may be the original value as-is)
	 */
	String resolveEmbeddedValue(String value);

	/**
	 * Specify a Spring 3.0 ConversionService to use for converting
	 * property values, as an alternative to JavaBeans PropertyEditors.
	 * @since 3.0
	 */
	void setConversionService(ConversionService conversionService);

	/**
	 * Return the associated ConversionService, if any.
	 * @since 3.0
	 */
	@Nullable
	ConversionService getConversionService();

}
