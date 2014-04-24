/*
 * Created on Aug 12, 2005
 *
 * 
 */
package org.keitdk.commons.core;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 
 *         一页内容
 */
public class ItemPage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 对象数组
	 */
	public List items;
	/**
	 * 对象总数
	 */
	public long total;
	/**
	 * 页码
	 */
	public int pageNo;
	/**
	 * 总页数
	 */
	public long pages;

	private ItemPage() {
	}

	public ItemPage(java.util.List items, long totals, int pageNo, int pageSize) {
		this.items = items;
		this.total = totals;
		this.pageNo = pageNo;
		this.pages = (total - 1) / pageSize + 1;
	}
}
