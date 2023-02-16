package indi.yuluo.springframework.core.convert.converter;

/**
 * @author yuluo
 * @createTime 2023-02-16  20:52
 * @des 包装转换处理接口
 */

public interface Converter<S, T> {

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param source
	 * @return
	 */
	T convert(S source);

}
