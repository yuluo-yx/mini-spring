package indi.yuluo.springframework.context.support;

import java.util.Set;

import indi.yuluo.springframework.beans.factory.FactoryBean;
import indi.yuluo.springframework.beans.factory.InitializingBean;
import indi.yuluo.springframework.core.convert.ConversionService;
import indi.yuluo.springframework.core.convert.converter.Converter;
import indi.yuluo.springframework.core.convert.converter.ConverterFactory;
import indi.yuluo.springframework.core.convert.converter.ConverterRegistry;
import indi.yuluo.springframework.core.convert.converter.GenericConverter;
import indi.yuluo.springframework.core.convert.support.DefaultConversionService;
import indi.yuluo.springframework.core.convert.support.GenericConversionService;
import org.jetbrains.annotations.Nullable;

/**
 * @author yuluo
 * @createTime 2023-02-16  22:25
 * @des null
 */

public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

	@Nullable
	private Set<?> converters;

	@Nullable
	private GenericConversionService conversionService;

	@Override
	public ConversionService getObject() throws Exception {
		return conversionService;
	}

	@Override
	public Class<?> getObjectType() {
		return conversionService.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.conversionService = new DefaultConversionService();
		registerConverters(converters, conversionService);
	}

	private void registerConverters(Set<?> converters, ConverterRegistry registry) {
		if (converters != null) {
			for (Object converter : converters) {
				if (converter instanceof GenericConverter) {
					registry.addConverter((GenericConverter) converter);
				} else if (converter instanceof Converter<?, ?>) {
					registry.addConverter((Converter<?, ?>) converter);
				} else if (converter instanceof ConverterFactory<?, ?>) {
					registry.addConverterFactory((ConverterFactory<?, ?>) converter);
				} else {
					throw new IllegalArgumentException("Each converter object must implement one of the " +
							"Converter, ConverterFactory, or GenericConverter interfaces");
				}
			}
		}
	}

	public void setConverters(Set<?> converters) {
		this.converters = converters;
	}

}

