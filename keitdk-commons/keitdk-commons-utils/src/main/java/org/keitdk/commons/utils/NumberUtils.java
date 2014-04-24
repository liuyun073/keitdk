package org.keitdk.commons.utils;

import java.math.BigDecimal;

public class NumberUtils {

	public static String format(double d, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(d));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();

	}

}
