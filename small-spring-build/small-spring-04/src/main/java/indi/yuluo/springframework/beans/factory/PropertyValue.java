package indi.yuluo.springframework.beans.factory;

/**
 *@author yuluo
 *@createTime 2023-01-26  20:13
 * 属性值
 */

public class PropertyValue {

	private final String name;

	private final Object value;

	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

}
