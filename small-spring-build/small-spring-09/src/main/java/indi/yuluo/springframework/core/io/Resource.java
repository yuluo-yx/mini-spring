package indi.yuluo.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:34
 */

public interface Resource {

	// 提供获取 InputStream 流的方法
	InputStream getInputStream() throws IOException;

}
