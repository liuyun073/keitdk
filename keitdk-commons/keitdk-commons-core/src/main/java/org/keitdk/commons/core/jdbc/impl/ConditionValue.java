package org.keitdk.commons.core.jdbc.impl;

import org.keitdk.commons.core.jdbc.IConditionValue;


public class ConditionValue implements IConditionValue {

	private Object value;

	private boolean isMuilt = false;

	private int type;

	/**
	 *
	 * @param value
	 * @param isMuilt
	 * @param type
	 */
	public ConditionValue(Object value, boolean isMuilt, int type) {
		this.value = value;
		this.isMuilt = isMuilt;
		this.type = type;
	}

	public ConditionValue(Object value, int type) {
		this.value = value;
		this.type = type;
	}

	public boolean getMuilt() {
		return isMuilt;
	}

	public int getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

}
