package indi.yuluo.springframework.core.io;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:34
 * 资源加载器
 */

public interface ResourceLoader {

	/** Pseudo URL prefix for loading from the class path: "classpath:" */
	String CLASSPATH_URL_PREFIX = "classpath:";

	// 传递location地址即可
	Resource getResource(String location);

}
