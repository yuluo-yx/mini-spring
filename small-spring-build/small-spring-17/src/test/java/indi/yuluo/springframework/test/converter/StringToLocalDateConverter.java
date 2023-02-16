package indi.yuluo.springframework.test.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import indi.yuluo.springframework.core.convert.converter.Converter;

/**
 * @author yuluo
 * @createTime 2023-02-16  22:31
 * @des null
 */

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

	private final DateTimeFormatter DATE_TIME_FORMATTER;

	public StringToLocalDateConverter(String pattern) {
		DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(pattern);
	}

	@Override
	public LocalDate convert(String source) {
		return LocalDate.parse(source, DATE_TIME_FORMATTER);
	}

}
