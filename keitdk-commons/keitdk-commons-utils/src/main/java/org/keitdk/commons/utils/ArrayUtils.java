package org.keitdk.commons.utils;

import java.util.ArrayList;
import java.util.List;


public class ArrayUtils {


	/**
	 * 将数组转换为列表
	 * @param <T>
	 *
	 * @param items
	 * @return
	 */
	public static <T> List<T> arrayToList(T[] items) {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < items.length; i++)
			list.add(items[i]);

		return list;
	}

	/**
	 * 将数组转为特定格式的字符串
	 *
	 * Example: values = new String[] { '1', '2', '3' };<br>
	 * System.out.println('delete from User where code in (' +
	 * Utils.arrayToSting(values, ',', true) + ')');<br>
	 * System.out.println('delete from User where code in (' +
	 * Utils.arrayToSting(values, ',', false) + ')');
	 *
	 * @param values
	 *            字符数组
	 * @param split
	 *            分隔符
	 * @param asNumber
	 *            作为数字分隔
	 * @return
	 */
	public static String arrayToSting(String[] values, String split,
			boolean asNumber) {
		StringBuffer buf = new StringBuffer("");
		String fix = "'";
		if (asNumber)
			fix = "";
		if (values != null)
			for (int i = 0; i < values.length; i++)
				if (i == 0)
					buf.append(fix + values[i].trim() + fix);
				else
					buf.append(split + fix + values[i].trim() + fix);
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
	 * 将字符串列表转换为字符串数组
	 *
	 * @param list
	 * @return
	 */
	public static String[] listToStrings(List<String> list) {
		String[] names = new String[list.size()];

		for (int i = 0; i < list.size(); i++)
			names[i] = (String) list.get(i);
		return names;
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
	public static List<String> stringToList(String source, String splitchar) {

		if (source == null)
			return new ArrayList<String>();
		else
			return arrayToList(source.split(splitchar));

	}

	/**
	 * 转换普通值--名称列表为IListItem列表，用来名称转换或列表输出
	 *
	 * @param items
	 *            以值--名称编码的数据项,以半角逗号分隔
	 * @return IListItem列表
	 */
	public static List<DefaultListItem> convertList(String[] items, String split) {
		List<DefaultListItem> list = new ArrayList<DefaultListItem>();

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				String[] arr = items[i].split(split);
				if (arr.length > 1)
					list.add(new DefaultListItem(arr[0], arr[1]));
				else
					list.add(new DefaultListItem(items[i], items[i]));
			}
		}
		return list;

	}


}
