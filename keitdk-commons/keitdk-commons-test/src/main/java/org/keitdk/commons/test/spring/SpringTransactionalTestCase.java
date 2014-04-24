/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.keitdk.commons.test.spring;

import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring的支持数据库访问, 事务控制和依赖注入的JUnit4 集成测试基类.
 * 相比Spring原基类名字更短并保存了dataSource变量.
 *
 * 子类需要定义applicationContext文件的位置, 如:
 *
 * @ContextConfiguration(locations = { "/applicationContext.xml" })
 *
 * @author calvin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.UNIT_TEST)
public abstract class SpringTransactionalTestCase  {

	protected DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		//super.setDataSource(dataSource);
		this.dataSource = dataSource;
	}
}
