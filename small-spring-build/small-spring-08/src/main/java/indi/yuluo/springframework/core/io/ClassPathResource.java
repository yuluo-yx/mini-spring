package indi.yuluo.springframework.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import cn.hutool.core.lang.Assert;
import indi.yuluo.springframework.util.ClassUtils;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:33
 * 读取classPath下的文件信息
 */

public class ClassPathResource implements Resource {

	private final String path;

	private ClassLoader classLoader;

	public ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		Assert.notNull(path, "Path must not be null");
		this.path = path;
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}

	// 获取 InputStream 流
	@Override
	public InputStream getInputStream() throws IOException {

		InputStream is = classLoader.getResourceAsStream(path);

		if (is == null) {
			throw new FileNotFoundException(this.path + "cannot be opend because it does not exist");
		}

		return is;
	}
}
