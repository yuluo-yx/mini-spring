package indi.yuluo.springframework.core.convert.converter;

import java.util.Set;

import cn.hutool.core.lang.Assert;

/**
 * @author yuluo
 * @createTime 2023-02-16  22:21
 * @des null
 */

public interface GenericConverter {

	/**
	 * Return the source and target types that this converter can convert between.
	 */
	Set<ConvertiblePair> getConvertibleTypes();

	/**
	 * Convert the source object to the targetType described by the {@code TypeDescriptor}.
	 * @param source the source object to convert (may be {@code null})
	 * @param sourceType the type descriptor of the field we are converting from
	 * @param targetType the type descriptor of the field we are converting to
	 * @return the converted object
	 */
	Object convert(Object source, Class sourceType, Class targetType);

	/**
	 * Holder for a source-to-target class pair.
	 */
	final class ConvertiblePair {
		private final Class<?> sourceType;

		private final Class<?> targetType;

		public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
			Assert.notNull(sourceType, "Source type must not be null");
			Assert.notNull(targetType, "Target type must not be null");
			this.sourceType = sourceType;
			this.targetType = targetType;
		}

		public Class<?> getSourceType() {
			return this.sourceType;
		}

		public Class<?> getTargetType() {
			return this.targetType;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || obj.getClass() != ConvertiblePair.class) {
				return false;
			}
			ConvertiblePair other = (ConvertiblePair) obj;
			return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);

		}

		@Override
		public int hashCode() {
			return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
		}
	}

}