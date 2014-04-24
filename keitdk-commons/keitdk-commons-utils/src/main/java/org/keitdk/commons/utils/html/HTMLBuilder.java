package org.keitdk.commons.utils.html;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.keitdk.commons.utils.data.DataConvert;


public class HTMLBuilder {

	/**
	 * 由IListItem列表生成Radio列表
	 *
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值
	 * @param buttonName
	 *            按钮名称
	 * @param ext
	 *            扩展字符串，如onclick='' or class="button"
	 * @param radio
	 *            true=单选 false复选
	 * @return HTML代码
	 */
//	public static String getButtonsByList(List list, String defaultValue, String buttonName,
//			String ext, boolean radio) {
//		if (list == null)
//			return "";
//		if (defaultValue == null)
//			defaultValue = "";
//		StringBuffer buf = new StringBuffer("");
//		String tmp;
//		currentValue = defaultValue;
//		for (int i = 0; i < list.size(); i++) {
//
//			IListItem item = (IListItem) list.get(i);
//			tmp = " value='" + item.getCode() + "'";
//			if (i == 0)
//				currentValue = item.getCode();
//			if (defaultValue.equals(item.getCode())) {
//				tmp = tmp + " checked ";
//				currentValue = item.getCode();
//			}
//
//			if (radio)
//				buf.append("<input type=radio name='" + buttonName + "'" + tmp + ext + ">"
//						+ item.getTitle() + "\n");
//			else
//				buf.append("<input type=checkbox name='" + buttonName + "'" + tmp + ext + ">"
//						+ item.getTitle() + "\n");
//
//		}
//		return buf.toString();
//	}

	/**
	 * 返回下拉列表选项。
	 *
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @return HTML代码
	 */
//	public static String getOptionsByList(List list, String defaultValue) {
//		if (list == null)
//			return "";
//		StringBuffer buf = new StringBuffer("");
//		if (defaultValue == null)
//			defaultValue = "";
//		String tmp;
//		currentValue = defaultValue;
//		for (int i = 0; i < list.size(); i++) {
//			IListItem item = (IListItem) list.get(i);
//			tmp = " value='" + item.getCode() + "'";
//			if (i == 0)
//				currentValue = item.getCode();
//			if (defaultValue.equals(item.getCode())) {
//				tmp = tmp + " selected";
//				currentValue = item.getCode();
//			}
//
//			buf.append("<option" + tmp + ">" + item.getTitle() + "</option>\n");
//		}
//		return buf.toString();
//	}

	/**
	 * 通过编码返回名称或标题
	 *
	 * @param list
	 *            IListItem列表
	 * @param code
	 *            编码
	 * @return 名称或标题
	 */
//	public static String getTitleByList(List list, String code) {
//		if (list == null)
//			return code;
//		if (code == null) {
//			code = "";
//			return code;
//		}
//		if (list != null)
//			for (int i = 0; i < list.size(); i++) {
//				IListItem item = (IListItem) list.get(i);
//				if (code.equals(item.getCode()))
//					return item.getTitle();
//			}
//		return code;
//	}

	/**
	 * 返回下拉列表选项。
	 *
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @return HTML代码
	 */
//	public static String getOptionsAsTree(List list, String defaultValue) {
//		return getOptionsAsTree(list, defaultValue, true);
//	}

	/**
	 * 返回下拉列表选项。
	 *
	 * @param list
	 *            IListItem列表
	 * @param defaultValue
	 *            当前选中的值，可以为空。
	 * @param allowSelectTop
	 *            是充许选择顶级项
	 * @return HTML代码
	 */
//	public static String getOptionsAsTree(List list, String defaultValue, boolean allowSelectTop) {
//
//		return (new TreeUtils(list, defaultValue, allowSelectTop)).getTree();
//	}

	/**
	 * 通过编码返回名称或标题
	 *
	 * @param list
	 *            IListItem列表
	 * @param code
	 *            编码
	 * @return 名称或标题
	 */
//	public static String getNameByList(List list, String code) {
//		return getTitleByList(list, code);
//	}

	/**
	 * 当前缺省值，在查询条件输出之后需要立即取出，否则在下次合成查询界面时改变
	 * 有时填充下列表时，没有查询条件，默认第一项为查询的值。此时可能需要取到当前列表的缺省值是什么。
	 */
	private static String currentValue = "";

	/**
	 * 当前缺省值，在查询条件输出之后需要立即取出，否则在下次合成查询界面时改变
	 * 有时填充下列表时，没有查询条件，默认第一项为查询的值。此时可能需要取到当前列表的缺省值是什么。
	 *
	 * @return
	 */
	public static String getCurrentValue() {
		return currentValue;
	}

	/**
	 * 通过数组输出单选按钮
	 *
	 * @return
	 * @param buttonName
	 *            按钮名称
	 * @param splitStr
	 *            值分隔符
	 * @param defaultValue
	 *            缺省值：用来设置按钮状态
	 * @param list
	 *            源数据
	 */
	public static String getRadioButton(List list, String defaultValue, String splitStr,
			String buttonName) {
		return getRadioButton(list, defaultValue, splitStr, buttonName, "");
	}

	/**
	 * 返回单选按钮列表
	 *
	 * @param list
	 *            值与编码列表
	 * @param defaultValue
	 *            缺省值（编码）
	 * @param splitStr
	 *            分隔符
	 * @param buttonName
	 *            按钮名称
	 * @param ext
	 *            扩展字符
	 * @return
	 */
	public static String getRadioButton(List list, String defaultValue, String splitStr,
			String buttonName, String ext) {
		StringBuffer page = new StringBuffer("");
		boolean withCode = !splitStr.equals("");
		String code;
		String value;
		if (defaultValue == null)
			defaultValue = "";
		currentValue = defaultValue;
		if (list != null)
			for (int i = 0; i < list.size(); i++) {

				code = "";
				value = ((String) list.get(i)).trim();
				if (withCode) {
					String[] items = value.split(splitStr);
					if (items.length == 2) {
						code = " value='" + items[0] + "' ";
						if (items[0].equalsIgnoreCase(defaultValue))
							code = code + " checked";
						value = items[1];
					} else
						code = " value='" + value + "' ";

				}

				if (value.equalsIgnoreCase(defaultValue))
					code = code + " checked ";

				page.append("<input type=radio name='" + buttonName + "'" + code + ext + ">"
						+ value + "\n");

			}
		return page.toString();
	}

	/**
	 * 输出下拉列表选项
	 *
	 * @return
	 * @param splitStr
	 *            值分隔符
	 * @param defaultValue
	 *            缺省值
	 * @param list
	 */
	public static String getComboboxItem(List list, String defaultValue, String splitStr) {
		StringBuffer page = new StringBuffer("");
		boolean withCode = splitStr.compareTo("") != 0;
		boolean needSetDefault;
		String code;
		String value;
		if (defaultValue == null)
			defaultValue = "";
		currentValue = defaultValue;
		if (list != null)
			for (int i = 0; i < list.size(); i++) {

				code = "";
				needSetDefault = true;

				value = ((String) list.get(i)).trim();
				if (i == 0 && defaultValue.compareTo("") == 0)
					currentValue = value;
				if (withCode) {
					String[] items = value.split(splitStr);
					if (i == 0 && defaultValue.compareTo("") == 0)
						currentValue = items[0];
					if (items.length == 2) {
						code = " value='" + items[0] + "' ";
						if (items[0].equalsIgnoreCase(defaultValue))
							code = code + " selected ";
						value = items[1];
						needSetDefault = false;
					}

				}

				if (needSetDefault && value.compareToIgnoreCase(defaultValue) == 0)
					code = code + " selected ";

				page.append("<option" + code + ">" + value + "</option>\n");

			}
		return page.toString();
	}

	/**
	 * 输出下拉列表选项
	 *
	 * @param defaultValue
	 * @param list
	 */
	public static String getComboboxItem(String list, String defaultValue) {
		return getComboboxItem(list, defaultValue, ";", ",");
	}

	/**
	 * 输出下拉列表选项
	 *
	 * @return
	 * @param codeSplitStr
	 *            值分隔符（如名称与编码的分隔）
	 * @param itemSplitStr
	 *            项分隔符（如多行数据的分隔）
	 * @param defaultValue
	 *            缺省值
	 * @param list
	 *            格式化的字符串
	 */
	public static String getComboboxItem(String list, String defaultValue, String itemSplitStr,
			String codeSplitStr) {
		if (list == null)
			return "";
		String[] items = list.split(itemSplitStr);
		StringBuffer page = new StringBuffer("");
		String code;
		String value;
		boolean withCode = codeSplitStr.compareTo("") != 0;
		boolean needSetDefault;
		if (defaultValue == null)
			defaultValue = "";
		currentValue = defaultValue;

		for (int i = 0; i < items.length; i++) {
			// String item = items[i].trim();
			code = "";
			value = items[i];
			needSetDefault = true;
			if (i == 0 && defaultValue.compareTo("") == 0)
				currentValue = value;
			if (withCode) {
				String[] item = value.split(codeSplitStr);
				if (i == 0 && defaultValue.compareTo("") == 0)
					currentValue = item[0];
				if (item.length == 2) {
					code = " value='" + item[0] + "' ";
					if (item[0].equalsIgnoreCase(defaultValue))
						code = code + " selected ";
					value = item[1];
					needSetDefault = false;
				}
			}

			if (needSetDefault && value.equalsIgnoreCase(defaultValue))
				code = code + " selected ";

			page.append("<option" + code + ">" + value + "</option>\n");
		}
		return page.toString();
	}

	/**
	 * 输出单选按钮组
	 *
	 * @return
	 * @param buttonName
	 *            按钮名称
	 * @param codeSplitStr
	 *            值分隔符（如名称与编码的分隔）
	 * @param itemSplitStr
	 *            项分隔符（如多行数据的分隔）
	 * @param defaultValue
	 *            缺省值
	 * @param list
	 *            格式化的字符串
	 */
	public static String getRadioButton(String list, String defaultValue, String itemSplitStr,
			String codeSplitStr, String buttonName) {
		if (list == null)
			return "";
		String[] items = list.split(itemSplitStr);
		StringBuffer page = new StringBuffer("");
		String code;
		String value;
		boolean needSetDefault;
		boolean withCode = codeSplitStr.compareTo("") != 0;
		if (defaultValue == null)
			defaultValue = "";
		currentValue = defaultValue;
		for (int i = 0; i < items.length; i++) {
			value = items[i];
			needSetDefault = true;
			code = "";
			if (i == 0 && defaultValue.compareTo("") == 0)
				currentValue = value;
			if (withCode) {
				String[] item = value.split(codeSplitStr);
				if (i == 0 && defaultValue.compareTo("") == 0)
					currentValue = item[0];
				if (item.length == 2) {
					code = " value='" + item[0] + "' ";
					if (item[0].compareTo(defaultValue) == 0)
						code = code + " checked ";
					value = item[1];
					needSetDefault = false;
				} else
					code = " value='" + value + "' ";
			}
			if (needSetDefault && value.equalsIgnoreCase(defaultValue))
				code = code + " selected ";

			page.append("<input type=radio name=" + buttonName + code + ">" + value + "\n");
		}
		return page.toString();
	}

	/**
	 * 由编码取名称
	 *
	 * @return
	 * @param code
	 *            编码
	 * @param list
	 */
	public static String getNameByList(String list, String code) {
		return getNameByList(list, code, ";", ",");
	}

	/**
	 * 由编码取名称
	 *
	 * @return
	 * @param codeSplitStr
	 *            值分隔符（如名称与编码的分隔）
	 * @param itemSplitStr
	 *            项分隔符（如多行数据的分隔）
	 * @param code
	 *            值
	 * @param list
	 *            格式化的字符串
	 */
	public static String getNameByList(String list, String code, String itemSplit, String valueSplit) {
		if (list == null)
			return "";
		if(code==null)
			return "";
		String[] items = list.split(itemSplit);
		for (int i = 0; i < items.length; i++) {
			String[] value = items[i].split(valueSplit);
			if (value.length == 2)
				if (value[0].equalsIgnoreCase(code))
					return value[1];
		}
		return code;
	}

	/**
	 * 解码字符串为哈希表
	 *
	 * @return
	 * @param valueSplit
	 *            值分隔符
	 * @param itemSplit
	 *            项分隔符
	 * @param list
	 *            源串
	 */
	public static HashMap getListByString(String list, String itemSplit, String valueSplit) {
		HashMap hm = new HashMap();
		if (list == null)
			return hm;
		String[] items = list.split(itemSplit);
		for (int i = 0; i < items.length; i++) {
			String[] value = items[i].split(valueSplit);
			if (value.length == 2)
				hm.put(value[0], value[1]);
		}
		return hm;
	}

	/**
	 * 解码字符串为超链列表
	 *
	 * @return
	 * @param HREFFix
	 *            超级链接相对路径或绝对路径如"../file/"
	 * @param valueSplit
	 *            值分隔符
	 * @param itemSplit
	 *            项分隔
	 * @param list
	 */
	public static String getHREFList(String list, String itemSplit, String valueSplit,
			String HREFFix) {
		if (list == null)
			return "";
		StringBuffer sb = new StringBuffer("");
		String[] items = list.split(itemSplit);
		for (int i = 0; i < items.length; i++) {
			String[] value = items[i].split(valueSplit);
			if (value.length == 2)
				sb.append("<li><a href='" + HREFFix + value[0] + "'>" + value[1] + "</a>");
		}
		return sb.toString();
	}

	/**
	 * 将指定URI转换为HTML anchor()，当url为空时返回&nbsp;
	 *
	 * @param url
	 *            achor
	 * @param title
	 *            提示
	 * @return
	 */
	public static String getHREF(String url, String title) {

		if (url == null || url.equals(""))
			return "&nbsp;";
		else {
			if (title == null || title.equals(""))
				title = url;
			return "<a href='" + url + "'>" + title + "</a>";
		}
	}

	/**
	 * 生成从from到to的日期下拉选项
	 *
	 * @param from
	 *            开始时间
	 * @param to
	 *            结束时间
	 * @param format
	 *            日期格式字符串
	 * @param titleformat
	 *            显示内容格式串
	 * @return 下拉列表选项
	 */
	public static String getMonthOption(Date from, Date to, String format, String titleformat,
			String defaultvalue) {
		StringBuffer buf = new StringBuffer("");
		from = (from == null) ? new Date() : from;
		to = (to == null) ? new Date() : to;
		boolean asc = to.after(from);
		Date d = from;
		String cur;
		if (asc) {

			while (d.before(to)) {
				cur = DataConvert.formatDateTime(d, format, "GMT+8");
				if (cur.equals(defaultvalue))
					buf.append("<option value='" + cur + "' selected>"
							+ DataConvert.formatDateTime(d, titleformat, "GMT+8") + "</option>\n");
				else
					buf.append("<option value='" + cur + "'>"
							+ DataConvert.formatDateTime(d, titleformat, "GMT+8") + "</option>\n");

				d = DataConvert.addDate(d, DataConvert.Time_MONTH, 1);
			}

		} else {
			d = from;
			while (d.after(to)) {
				cur = DataConvert.formatDateTime(d, format, "GMT+8");
				if (cur.equals(defaultvalue))
					buf.append("<option value='" + cur + "' selected>"
							+ DataConvert.formatDateTime(d, titleformat, "GMT+8") + "</option>\n");
				else
					buf.append("<option value='" + cur + "'>"
							+ DataConvert.formatDateTime(d, titleformat, "GMT+8") + "</option>\n");
				d = DataConvert.addDate(d, DataConvert.Time_MONTH, -1);
			}
		}
		return buf.toString();

	}

	/**
	 *
	 * @param from
	 *            开始日期
	 * @param to
	 *            截止日期
	 * @param format
	 *            Option-value中的年格式
	 * @param defaultvalue
	 *            当前缺省值
	 * @return 季度下拉列表
	 */
	public static String getSeasonOption(Date from, Date to, String format, String defaultvalue) {
		StringBuffer buf = new StringBuffer("");
		from = (from == null) ? new Date() : from;
		from = DataConvert.getDate(from, "GMT+8");
		to = (to == null) ? new Date() : to;
		to = DataConvert.getDate(to, "GMT+8");
		boolean asc = to.after(from);
		Date d = from;
		String cur, tmp;

		int month;
		if (asc) {
			while (d.before(to)) {
				month = Integer.valueOf(DataConvert.formatDateTime(d, "M",false));

				if (month >= 1 && month <= 3)
					tmp = "1";
				else if (month >= 4 && month <= 6)
					tmp = "2";
				else if (month >= 7 && month <= 9)
					tmp = "3";
				else
					tmp = "4";
				cur = DataConvert.formatDateTime(d, format) + tmp;
				if (cur.equals(defaultvalue))
					buf.append("<option value='" + cur + "' selected>"
							+ DataConvert.formatDateTime(d, "yy年") + tmp + "季度</option>\n");
				else
					buf.append("<option value='" + cur + "'>"
							+ DataConvert.formatDateTime(d, "yy年") + tmp + "季度</option>\n");

				d = DataConvert.addDate(d, DataConvert.Time_MONTH, 3);
			}

		} else {
			d = from;
			while (d.after(to)) {
				month = DataConvert.getTimePart(d, DataConvert.Time_MONTH);

				if (month >= 1 && month <= 3)
					tmp = "1";
				else if (month >= 4 && month <= 6)
					tmp = "2";
				else if (month >= 7 && month <= 9)
					tmp = "3";
				else
					tmp = "4";
				cur = DataConvert.formatDateTime(d, format) + tmp;
				if (cur.equals(defaultvalue))
					buf.append("<option value='" + cur + "' selected>"
							+ DataConvert.formatDateTime(d, "yy年") + tmp + "季度</option>\n");
				else
					buf.append("<option value='" + cur + "'>"
							+ DataConvert.formatDateTime(d, "yy年") + tmp + "季度</option>\n");
				d = DataConvert.addDate(d, DataConvert.Time_MONTH, -3);
			}
		}
		return buf.toString();

	}
}