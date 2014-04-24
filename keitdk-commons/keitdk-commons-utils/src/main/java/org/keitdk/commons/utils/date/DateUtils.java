package org.keitdk.commons.utils.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	public static int Time_Month = Calendar.MONTH;

	public static int Time_HourOfDay = Calendar.HOUR_OF_DAY;

	public static int Time_DayOfYear = Calendar.DAY_OF_YEAR;

	public static int Time_DayOfMonth = Calendar.DAY_OF_MONTH;

	public static int Time_Minute = Calendar.MINUTE;

	public static int Time_Year = Calendar.YEAR;

	public static int TIME_DayofWeeK = Calendar.DAY_OF_WEEK;

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
	public static Date add(Date date, int part, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(part, value);
		return calendar.getTime();
	}

	/**
	 * 格式化为指定格式的日期，缺省以东八区时间格式化
	 *
	 * @param date
	 * @param format
	 *            如 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String format(Date date, String format) {

		return format(date, format, "GMT+8");
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
	public static String format(Date date, String format, String timeZone) {

		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (timeZone != null && !timeZone.trim().equals(""))
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
		return formatter.format(date);
	}

	/**
	 * 时区转换
	 *
	 * @param date
	 * @param locate
	 * @return
	 */
	public static Date get(Date date, String locate) {
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
	public static int get(Date date, int part) {
		return get(date, part, "");

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
	public static int get(Date date, int part, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !timeZone.trim().equals(""))
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		int ret = calendar.get(part);
		if (part == Time_Month)
			ret++;
		return ret;

	}

	/**
	 * 字符转换为日期。
	 *
	 * @param source
	 * @param patterns日期格式串如yyyy-MM-dd
	 *            HH:mm:ss
	 * @return
	 */
	public static Date valueOf(String source, String patterns) {
		return valueOf(source, patterns, "GMT+8");
	}

	/**
	 * 字符串转换为指定时区时间
	 *
	 * @param source
	 * @param patterns
	 *            如yyyy-MM-dd HH:mm:ss
	 * @param timeZone如东八区GMT+8
	 * @return
	 */
	public static Date valueOf(String source, String patterns, String timeZone) {
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

}
