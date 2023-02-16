package indi.yuluo.springframework.core.convert;

import org.jetbrains.annotations.Nullable;

/**
 * @author yuluo
 * @createTime 2023-02-16  22:17
 * @des null
 */

public interface ConversionService {

	/** Return {@code true} if objects of {@code sourceType} can be converted to the {@code targetType}. */
	boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

	/** Convert the given {@code source} to the specified {@code targetType}. */
	<T> T convert(Object source, Class<T> targetType);

}
