package org.keitdk.commons.core.jdbc;

import org.keitdk.commons.core.RequestParams;

/**
 * 转换数据记录的行为POJO对象
 *
 * @author Administrator
 *
 */
public interface IORTransform<T> {

	/**
	 * 转换
	 * @param <T>
	 *
	 * @param params
	 * @return Example:
	 *
	 * select * from users where ...
	 *
	 * User user = new User();<br>
	 * user.username= params.getString("name");<br>
	 * ....<br>
	 * return user;
	 *
	 *
	 *
	 */
	public T transform(RequestParams params);

}
