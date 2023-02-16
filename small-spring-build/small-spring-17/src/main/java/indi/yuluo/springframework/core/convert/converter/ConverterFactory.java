package indi.yuluo.springframework.core.convert.converter;

/**
 * @author yuluo
 * @createTime 2023-02-16  20:53
 * @des 类型转换工厂
 */

public interface ConverterFactory<S, R> {

	/** * Get the converter to convert from S to target type T, where T is also an ins
	 tance of R.
	 * @param <T> the target type
	 * @param targetType the target type to convert to
	 * @return a converter from S to T
	 */
	<T extends R> Converter<S, T> getConverter(Class<T> targetType);

}
