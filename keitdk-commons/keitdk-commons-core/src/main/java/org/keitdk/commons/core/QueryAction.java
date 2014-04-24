package org.keitdk.commons.core;

public class QueryAction {

	/**
	 * 建议使用EQUAL常量
	 * @deprecated
	 */
	public static final int Extract = 0;// 精确查询 =

	/**
	 * 等于
	 */
	public static final int EQUAL = 0;

	/**
	 * 建议使用LIKE常量
	 * @deprecated
	 */
	public static final int Blurry = 1;

	/**
	 * LIKE（sql）模糊查询
	 */
	public static final int LIKE = 1; // 模糊查询 like

	/**
	 * 建议使用GE(>=)常量
	 * @deprecated
	 */
	public static final int LeftRang = 2; // 范围查询 >=

	/**
	 * 大于等于(>=)
	 */
	public static final int GE = 2;

	/**
	 * 建议使用LE常量(<=)
	 * @deprecated
	 */
	public static final int RightRang = 3; // 范围查询 <=

	/**
	 * 小于等于(<=)
	 */
	public static final int LE = 3;

	/**
	 * 建议使用IN常量
	 * @deprecated
	 */
	public static final int Range = 4; // 范围查询 in

	public static final int IN = 4;

	public static final int NOEQUAL = 5; // <>
    
	/**
	 * 大于(>)
	 */
	public static final int GT = 6; // >

	/**
	 * 小于(<)
	 */
	public static final int LT = 7; // <

	public static final int BETWEEN = 8;//
	// eq 等於
	// allEq 使用Map，使用key/value進行多個等於的比對
	// gt 大於 >
	// ge 大於等於 >=
	// lt 小於 <
	// le 小於等於 <=
	// between 對應sql的BETWEEN子句
	// like 對應sql的LIKE子句
	// in 對應sql的in子句
	// and and關係
	// or or關係
	// sqlRestriction sql限定查詢

}