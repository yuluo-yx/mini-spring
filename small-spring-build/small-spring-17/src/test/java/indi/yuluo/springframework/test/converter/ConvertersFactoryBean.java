package indi.yuluo.springframework.test.converter;

import java.util.HashSet;
import java.util.Set;

import indi.yuluo.springframework.beans.factory.FactoryBean;

/**
 * @author yuluo
 * @createTime 2023-02-16  22:31
 * @des null
 */

public class ConvertersFactoryBean implements FactoryBean<Set<?>> {

	@Override
	public Set<?> getObject() throws Exception {
		HashSet<Object> converters = new HashSet<>();
		StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
		converters.add(stringToLocalDateConverter);
		return converters;
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
