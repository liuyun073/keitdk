package org.keitdk.commons.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * @author Administrator
 *
 * 查询条件
 */
public class Condition implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String AND = "and";

	public final static String OR = "or";

	/**
	 * 逻辑操作符
	 */
	public String operator;

	/**
	 * 公式(必须为类型Formula或者List<Condition>)
	 */
	public Object formula;

	/**
	 * 逻辑操作为AND的实例化函数
	 *
	 * @param formula
	 *            条件公式
	 */
	public static Condition newInstance(Object formula) throws RuntimeException {
		checkType(formula);

		return new Condition(formula);
	}

	/**
	 * 实例化函数
	 *
	 * @param operator
	 *            操作符
	 * @param formula
	 *            条件公式
	 * @return
	 * @throws RuntimeException
	 */
	public static Condition newInstance(String operator, Object formula)
			throws RuntimeException {
		checkType(formula);

		return new Condition(operator, formula);
	}

	private Condition() {

	}

	/**
	 * 逻辑操作为AND的构造函数
	 *
	 * @param formula
	 *            条件公式
	 */
	private Condition(Object formula) {
		this.operator = AND;
		this.formula = formula;
	}

	private Condition(String operator, Object formula) {
		this.operator = operator;
		this.formula = formula;
	}

	/**
	 * 检查formula变量的类型是否合法
	 *
	 * @param formula
	 * @throws RuntimeException
	 */
	private static void checkType(Object formula) throws RuntimeException {
		if (formula == null)
			throw new RuntimeException("条件公式不能为空");

		if (!(formula instanceof Formula || formula instanceof List))
			throw new RuntimeException("公式类型不能为："
					+ formula.getClass().getName());
	}

	/**
	 * 生成查询条件
	 *
	 * @param conditions
	 *            条件集合
	 * @param mainCondition
	 *            是否最外层条件
	 * @return
	 */
	public synchronized static String toString(Collection<Condition> conditions,
			boolean mainCondition) {
		StringBuffer buffer = new StringBuffer();
		boolean firstCondition = true;

		if (conditions != null) {
			Iterator<Condition> iter = conditions.iterator();
			//for (int i = 0; i < conditions.size(); i++) {
			while(iter.hasNext()){
				Condition condition = iter.next();
				if (condition.formula instanceof Formula) {
					if (((Formula) condition.formula).isValid()) {
						buffer.append((firstCondition ? "" : " "
								+ condition.operator)
								+ " " + condition.formula);
						firstCondition = false;
					}
				} else if (condition.formula instanceof List) {
					String sub = toString((List) condition.formula, false);
					if (sub.length() > 0) {
						buffer.append((firstCondition ? "" : " "
								+ condition.operator)
								+ " ("
								+ sub
								+ ")");
						firstCondition = false;
					}
				} else
					throw new RuntimeException(
							"Condition类的formula只能为类型Formula或List<Condition>");

			}

			if (mainCondition && buffer.length() > 0)
				buffer.insert(0, " where");
		}

		return buffer.toString();
	}
}
