package indi.yuluo.springframework.test.converter;

import indi.yuluo.springframework.core.convert.converter.Converter;

/**
 * @author yuluo
 * @createTime 2023-02-16  22:31
 * @des null
 */

public class StringToIntegerConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String source) {
		return Integer.valueOf(source);
	}

}

