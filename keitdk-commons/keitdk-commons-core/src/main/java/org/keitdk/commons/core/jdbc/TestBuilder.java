package org.keitdk.commons.core.jdbc;

public class TestBuilder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		InsertBuilder builder = new InsertBuilder("aa");
		builder.addField("a1", "a1");
		builder.addField("a2", "a2");
		builder.addField("a3", "a3");
		System.out.println(builder.getsql(false));
		System.out.println(builder.getsql(true));
		UpdateBuilder update = new UpdateBuilder("Test" , "where a="+"i");
		update.addField("a1", "a1");
		update.addField("a2", "a2");
		update.addField("a3", "a3");
		System.out.println(update.getsql(false));
		System.out.println(update.getsql(true));
		StringBuffer sql=new StringBuffer("");
		sql.append(new Integer(1232));
		System.out.println(sql.toString());

	}

}
