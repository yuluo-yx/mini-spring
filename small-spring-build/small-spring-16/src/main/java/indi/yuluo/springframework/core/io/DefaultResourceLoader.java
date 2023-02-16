package indi.yuluo.springframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

import cn.hutool.core.lang.Assert;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:33
 * 默认的资源加载器实现
 * 是设计模式约定的具体结 果，像是这里不会让外部调用放知道过多的细节，而是仅关心具体调用结果即可
 */

public class DefaultResourceLoader implements ResourceLoader {

	@Override
	public Resource getResource(String location) {
		Assert.notNull(location, "Location must not be null");

		if (location.startsWith(CLASSPATH_URL_PREFIX)) {
			return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
		} else {
			try {
				URL url = new URL(location);
				return new UrlResource(url);
			} catch (MalformedURLException e) {
				return new FileSystemResource(location);
			}
		}
	}

}
