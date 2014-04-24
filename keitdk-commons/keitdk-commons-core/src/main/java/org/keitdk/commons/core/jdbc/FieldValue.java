package org.keitdk.commons.core.jdbc;

import java.io.Serializable;
import java.sql.Types;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

public class FieldValue implements Serializable {

	private static final long serialVersionUID = -87727735314410697L;

	private int type;

	private Object value;

	private String fieldName;
	
	

	public FieldValue(String fieldName, String value) {
		this.fieldName = fieldName;
		this.value = value;
		type = Types.CHAR;
	}

	public FieldValue(String fieldName, int value) {
		this.fieldName = fieldName;
		this.value = Integer.valueOf(value);
		type = Types.INTEGER;
	}

	public FieldValue(String fieldName, float value) {
		this.fieldName = fieldName;
		this.value = new Float(value);
		type = Types.FLOAT;
	}

	public FieldValue(String fieldName, double value) {
		this.fieldName = fieldName;
		this.value = new Double(value);
		type = Types.DOUBLE;
	}

	public FieldValue(String fieldName, byte[] value) {
		this.fieldName = fieldName;
		this.value = ArrayUtils.clone(value);
		type = Types.BINARY;
	}

	public FieldValue(String fieldName, Date value) {
		this.fieldName = fieldName;
		this.value = value;
		type = Types.DATE;
	}

	public FieldValue(String fieldName, Object value, int type) {
		this.type = type;
		this.value = value;
		this.fieldName = fieldName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
