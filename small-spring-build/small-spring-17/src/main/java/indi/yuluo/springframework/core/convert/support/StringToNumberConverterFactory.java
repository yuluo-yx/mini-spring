package indi.yuluo.springframework.core.convert.support;

import indi.yuluo.springframework.core.convert.converter.Converter;
import indi.yuluo.springframework.core.convert.converter.ConverterFactory;
import indi.yuluo.springframework.util.NumberUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author yuluo
 * @createTime 2023-02-16  22:18
 * @des null
 */

public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

	@Override
	public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToNumber<>(targetType);
	}

	private static final class StringToNumber<T extends Number> implements Converter<String, T> {

		private final Class<T> targetType;

		public StringToNumber(Class<T> targetType) {
			this.targetType = targetType;
		}

		@Override
		@Nullable
		public T convert(String source) {
			if (source.isEmpty()) {
				return null;
			}
			return NumberUtils.parseNumber(source, this.targetType);
		}
	}

}