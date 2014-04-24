package org.keitdk.commons.utils;

public class StringUtils {
	public static String subfix = "...";

	public static String valueOf(String source, int maxlength) {
		return valueOf(source, "", maxlength);
	}

	public static String valueOf(String source) {
		return valueOf(source, "");
	}

	public static String valueOf(String source, String defaultValue) {
		return valueOf(source, defaultValue, 0);
	}

	public static String null2Str(String source, int maxlength) {
		return valueOf(source, "", maxlength);
	}

	public static String valueOf(String source, String defaultValue, int maxlength) {
		if (source == null || source.trim().equals(""))
			return defaultValue;

		if (maxlength < 1)
			return source.trim();
		source = source.trim();

		int free = maxlength * 2;
		for (int i = 0; i < source.length(); i++) {
			if (source.charAt(i) > 255)// charCodeAt(i)
				free = free - 2;
			else
				free--;
			if (free < 1)
				return source.substring(0, i) + subfix;

		}

		if (source.length() > maxlength)
			source = source.substring(0, maxlength) + subfix;

		return source;
	}

	/**
	 * 将字符串转为HTML串，当源串为NULL或空时返回&nbsp;
	 *
	 * @param source
	 * @return
	 */
	public static String null2HTML(String source) {
		return null2HTML(source, 0);

	}

	/**
	 * 当字串超过长度时截短显示，为NULL时显示一个空格
	 *
	 * @param source
	 * @param len
	 * @return
	 */
	public static String null2HTML(String source, int len) {
		return valueOf(source, "&nbsp;", len).trim();
	}

	/**
	 * 返回以0作前缀的指定长度的序列号
	 */
	public static String fixLength(String value, int fixLen) {
		return fixLength(value, fixLen, '0');
	}

	/**
	 * 返回指定名称和长度以及指定前缀的序列号，通常使用0作前缀
	 */
	public static String fixLength(String value, int fixLen, char fixChar) {
		String stmp = "";
		for (int i = 0; i < fixLen - value.length(); i++) {
			stmp = stmp + fixChar;
		}

		return stmp + value;
	}

}
