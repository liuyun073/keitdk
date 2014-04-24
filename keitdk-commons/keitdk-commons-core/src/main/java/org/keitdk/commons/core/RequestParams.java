package org.keitdk.commons.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


public class RequestParams extends HashMap<String, Object> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5080497883487812687L;

	public RequestParams() {
	}

	public HttpServletRequest request;

	/**
	 */
	public void putA(String name, Object value) {
		putA(name.toLowerCase().trim(), value, false);
	}

	public void putA(String name, Object value, boolean replace) {
		name = name.toLowerCase().trim();
		if (exist(name)) {
			if (replace)
				remove(name);
			else
				return;
		}
		put(name, value);
	}

	public Object getA(String name) {
		try {
			return get(name.toLowerCase());
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 */
	public String getString(String name, String defaultValue) {
		try {
			String keyValue = (String) get(name.toLowerCase());
			if (keyValue == null)
				keyValue = defaultValue;
			else
				keyValue = keyValue.trim();

			return keyValue.trim();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public String getString(String name) {
		return getString(name, "");
	}

	public Date getDate(String name, String format) {
		String value = getString(name, "");
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date d = null;
		try {
			if (value.compareTo("") != 0)
				d = f.parse(value);
		} catch (Exception e) {

		}
		return d;

	}

	public Date getDate(String name) {
		return getDate(name, "yyyy-MM-dd");// "yyyy-MM-dd hh:mm"
	}

	/**
	 */
	public void removeA(String name) {
		remove(name.toLowerCase());
		// remove(name.toUpperCase());
	}

	public int getInt(String name, int defaultValue) {
		try {
			return Integer.parseInt(get(name.toLowerCase()).toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public boolean getBoolean(String name, boolean defaultvalue) {
		String value = getString(name);
		if (value.compareTo("") == 0)
			return defaultvalue;
		else
			return value.compareToIgnoreCase("1") == 0;
	}

	public boolean getBoolean(String name) {
		return getString(name).compareToIgnoreCase("1") == 0;
	}

	/**
	 */
	public float getFloat(String name) {
		return getFloat(name, 0);
	}

	public float getFloat(String name, float defaultValue) {
		try {
			return Float.parseFloat(get(name.toLowerCase()).toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public Float asFloat(String name) {
		try {

			return Float.valueOf(getString(name.toLowerCase()));
		} catch (Exception e) {
			return null;
		}
	}

	public Double asDouble(String name)
	{
		try {

			return Double.valueOf(getString(name.toLowerCase()));
		} catch (Exception e) {
			return null;
		}
	}

	public Integer asInteger(String name)
	{
		try {

			return Integer.valueOf(getString(name.toLowerCase()));
		} catch (Exception e) {
			return null;
		}
	}

	public boolean exist(String name) {
		try {
			return containsKey(name.toLowerCase())
					|| containsKey(name.toUpperCase());

		} catch (Exception e) {
			return false;
		}
	}
}
