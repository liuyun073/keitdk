package org.keitdk.commons.core.data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * 数据转换类，包括部份日期数据的操作 <br>
 * 2006年11月21日注释
 *
 * @author cybbj
 *
 */
public class DataConvert {

	public static int Time_MONTH = Calendar.MONTH;

	public static int Time_HOUROfDay = Calendar.HOUR_OF_DAY;

	public static int Time_DayOfYear = Calendar.DAY_OF_YEAR;

	public static int Time_DayOfMonth = Calendar.DAY_OF_MONTH;

	public static int Time_Minute = Calendar.MINUTE;

	public static int Time_YEAR = Calendar.YEAR;

	public static int TIME_DAYOFWEEK = Calendar.DAY_OF_WEEK;

	/**
	 * 日期运算
	 *
	 * @param date
	 *            源
	 * @param part
	 *            操作部份
	 * @param value
	 *            改变值
	 * @return 计算后的日期
	 */
	public static Date addDate(Date date, int part, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(part, value);
		return calendar.getTime();
	}

	/**
	 * 将数组转换为列表
	 *
	 * @param items
	 * @return
	 */
	public static List arrayToList(Object[] items) {
		List list = new ArrayList();
		for (int i = 0; i < items.length; i++)
			list.add(items[i]);

		return list;
	}

	/**
	 * 将数组转为特定格式的字符串
	 *
	 * <pre>
	 * Example: values = new String[] { &quot;1&quot;, &quot;2&quot;, &quot;3&quot; };
	 * System.out
	 * 		.println(&quot;delete from User where code in (&quot; + Utils.arrayToSting(values, &quot;,&quot;, true) + &quot;)&quot;);
	 * System.out.println(&quot;delete from User where code in (&quot; + Utils.arrayToSting(values, &quot;,&quot;, false)
	 * 		+ &quot;)&quot;);
	 * </pre>
	 *
	 * @param values
	 *            字符数组
	 * @param split
	 *            分隔符
	 * @param asNumber
	 *            作为数字分隔
	 * @return
	 */
	public static String arrayToSting(String[] values, String split, boolean asNumber) {
		StringBuffer buf = new StringBuffer("");
		String fix = "'";
		if (asNumber)
			fix = "";
		if (values != null)
			for (int i = 0; i < values.length; i++)
				if (i == 0)
					buf.append(fix + values[i] + fix);
				else
					buf.append(split + fix + values[i] + fix);
		return buf.toString();
	}

	/**
	 * 数组转为字符串
	 *
	 * @param items
	 * @param splitChar
	 * @return
	 */
	public static String arrayToString(Object[] items, String splitChar) {
		StringBuffer buf = new StringBuffer("");
		if (items != null)
			for (int i = 0; i < items.length; i++)
				if (i > 0)
					buf.append(splitChar + items[i]);
				else
					buf.append(items[i]);
		return buf.toString();
	}

	/**
	 * 转换日期为yyyy-MM-dd,为空时则返回HTML空(&nbsp;)
	 *
	 * @param source
	 * @return
	 */
	public static String field2HTMLValue(Date source) {
		if (source == null)
			return "&nbsp;";
		else
			return formatDateTime(source, "yyyy-MM-dd");
	}

	/**
	 * 转换空串包括绝对空为"&nbsp;"
	 *
	 * @param source
	 * @return
	 */
	public static String field2HTMLValue(String source) {

		return null2Str(source, "&nbsp;");
	}

	/**
	 * 格式化为指定格式的日期，缺省以东八区时间格式化
	 *
	 * @param date
	 * @param format
	 *            如 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String formatDateTime(Date date, String format) {

		return formatDateTime(date, format, true);
	}

	/**
	 * 格式化为指定格式的日期
	 *
	 * @param date
	 * @param format
	 * @param locate
	 *            当locate为true时使用东八区时间格式化，否则不进行时区转换
	 * @return
	 */
	public static String formatDateTime(Date date, String format, boolean locate) {
		if (date == null)
			return "";
		if (locate)
			return formatDateTime(date, format, "GMT+8");
		else
			return formatDateTime(date, format, null);

	}

	/**
	 * 日期格式化函数
	 *
	 * @param date
	 * @param format
	 * @param timeZone
	 *            时区如东八区GMT+8
	 * @return
	 */
	public static String formatDateTime(Date date, String format, String timeZone) {

		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (timeZone != null && !timeZone.trim().equals(""))
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(date);
	}

	public static String formatFloat(double d, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(d));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();

	}

	/**
	 * 返回格式如yyyy-MM-dd的日期串
	 *
	 * @return
	 */
	public static String getCurrentDate() {
		return getCurrentDate("yyyy-MM-dd");
	}

	/**
	 * 返回指定格式的当前日期
	 *
	 * @return
	 */
	public static String getCurrentDate(String format) {
		return formatDateTime(new Date(), format);
	}

	/**
	 * 返回格式如yyyy-MM-dd HH:mm:ss的日期串
	 *
	 * @return
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回指定格式的当前日期
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrentDateTime(String format) {
		return getCurrentDate(format);
	}

	/**
	 * 转换为指定时区的日期
	 *
	 * @param date
	 * @param locate
	 * @return
	 */
	public static Date getDate(Date date, String locate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setTimeZone(TimeZone.getTimeZone(locate));
		return calendar.getTime();
	}

	/**
	 * 获取指定日期的部份如月,以东8区为准
	 *
	 * @param date
	 * @param part
	 * @return
	 */
	public static int getTimePart(Date date, int part) {
		return getTimePart(date, part, "GMT+8");

	}

	/**
	 * 获取日期的部份数据
	 *
	 * @param date
	 * @param part
	 * @param timeZone
	 *            时区如GMT+8
	 * @return
	 */
	public static int getTimePart(Date date, int part, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !timeZone.trim().equals(""))
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		return calendar.get(part);

	}

	/**
	 * 取出时间中的年份数据
	 *
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {

		return getTimePart(date, Calendar.YEAR);

	}

	/**
	 * 将字符串列表转换为字符串数组
	 *
	 * @param list
	 * @return
	 */
	public static String[] listToStrings(List list) {
		String[] names = new String[list.size()];

		for (int i = 0; i < list.size(); i++)
			names[i] = (String) list.get(i);
		return names;
	}

	/**
	 * 将字符串转为HTML串，当源串为NULL或空时返回&nbsp;
	 *
	 * @param source
	 * @return
	 */
	public static String null2HTML(String source) {
		return null2Str(source, "&nbsp;").trim();

	}

	/**
	 * 当字串超过长度时截短显示，为NULL时显示一个空格
	 *
	 * @param source
	 * @param len
	 * @return
	 */
	public static String null2HTML(String source, int len) {
		return subHTMLString(source, len);
	}

	/**
	 * 如果字符串为NULL则转换为空("")
	 *
	 * @param source
	 * @param nullstr
	 * @return
	 */
	public static String null2Str(String source) {
		return null2Str(source, "");
	}

	/**
	 * 如果字符串为NULL则转换为nullstr
	 *
	 * @param source
	 * @param nullstr
	 * @return
	 */
	public static String null2Str(String source, String nullstr) {
		if (source == null || source.trim().equals(""))
			return nullstr;
		else
			return source;
	}

	/**
	 * 字符转换为日期。
	 *
	 * @param source
	 * @param patterns日期格式串如yyyy-MM-dd
	 *            HH:mm:ss
	 * @return
	 */
	public static Date stringToDate(String source, String patterns) {
		return stringToDate(source, patterns, true);
	}

	/**
	 * 字符转换为日期。
	 *
	 * @param source
	 * @param patterns日期格式串如yyyy-MM-dd
	 *            HH:mm:ss
	 * @param locate
	 *            true--转化为东八区时间
	 * @return
	 */
	public static Date stringToDate(String source, String patterns, boolean locate) {
		if (locate)
			return stringToDate(source, patterns, "GMT+8");
		else
			return stringToDate(source, patterns, "");
	}

	/**
	 * 字符串转换为指定时区时间
	 *
	 * @param source
	 * @param patterns
	 * @param timeZone如东八区GMT+8
	 * @return
	 */
	public static Date stringToDate(String source, String patterns, String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patterns);
		Date date = null;
		if (source == null)
			return date;
		if (timeZone != null && !timeZone.trim().equals(""))
			dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			date = dateFormat.parse(source);
		} catch (java.text.ParseException e) {
			System.out.println("[string to date]" + e.getMessage());
		}

		return date;
	}

	/**
	 * 将特定分隔符分隔的字符串转换为列表
	 *
	 * @param source
	 *            源串
	 * @param splitchar
	 *            分隔符
	 * @return
	 */
	public static List stringToList(String source, String splitchar) {

		if (source == null)
			return new ArrayList();
		else
			return arrayToList(source.split(splitchar));

	}

	/**
	 * 截取指定长度的字符串，并在后面加省略号(...)
	 *
	 * @param source
	 * @param len
	 * @return
	 */
	public static String subHTMLString(String source, int len) {
		return subHTMLString(source, len, "...");

	}

	/**
	 * 截取指定长度的字符串，当字符串为空或null时返回HTML空(&nbsp;)
	 *
	 * @param source
	 * @param len
	 * @param fixStr
	 * @return
	 */
	public static String subHTMLString(String source, int len, String fixStr) {
		if (source == null || source.compareTo("") == 0)
			return "&nbsp;";

		if (len < 1)
			return source.trim();
		source = source.trim();
		int free = len*2;
		for (int i = 0; i < source.length(); i++) {
			if (source.charAt(i) > 255)// charCodeAt(i)
				free = free - 2;
			else
				free--;
			if (free < 1)
				return source.substring(0, i) + fixStr;

		}
		if (source.length() > len)
			source = source.substring(0, len) + fixStr;
		if (source.length() < 1)
			source = fixStr;
		return source;
	}
}
