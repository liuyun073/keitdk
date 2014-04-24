package org.keitdk.commons.utils;

public class DefaultListItem implements IListItem {
	private String code;

	private String title;

	public DefaultListItem(String code, String title) {
		this.code = code;
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}
}