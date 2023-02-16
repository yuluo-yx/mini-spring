package indi.yuluo.springframework.core.convert.support;

import indi.yuluo.springframework.core.convert.converter.ConverterRegistry;

/**
 * @author yuluo
 * @createTime 2023-02-16  22:16
 * @des null
 */

public class DefaultConversionService extends GenericConversionService{

	public DefaultConversionService() {
		addDefaultConverters(this);
	}

	public static void addDefaultConverters(ConverterRegistry converterRegistry) {
		// 添加各类类型转换工厂
		converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
	}

}
