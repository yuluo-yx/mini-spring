package indi.yuluo.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import cn.hutool.core.lang.Assert;

/**
 *@author yuluo
 *@createTime 2023-01-27  15:34
 */

public class UrlResource implements Resource {

	private final URL url;

	public UrlResource(URL url) {
		Assert.notNull(url, "URL must to be null");
		this.url = url;
	}

	@Override
	public InputStream getInputStream() throws IOException {

		URLConnection urlConnection = this.url.openConnection();

		try {
			return urlConnection.getInputStream();
		}
		catch (IOException e) {
			if (urlConnection instanceof HttpURLConnection) {
				((HttpURLConnection) urlConnection).disconnect();
			}
			throw e;
		}
	}
}
