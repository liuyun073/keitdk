package org.keitdk.commons.test.spring;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(locations = { "/spring/applicationContext*.xml" })
public class SpringContextTestCaseTest extends SpringContextTestCase {

	@Test
	public void test() {

	}

}
