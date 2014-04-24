package org.keitdk.commons.core.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.keitdk.commons.core.model.BaseNavi;
import org.keitdk.commons.core.service.CommonService;
import org.keitdk.commons.test.spring.SpringTransactionalTestCase;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "/spring/applicationContext*.xml" })
public class CommonServiceImplTest extends SpringTransactionalTestCase {

	@Resource(name="commonService")
	CommonService  commonService;

	BaseNavi bn;

	@Before
	public void before(){
		bn = new BaseNavi();
		bn.setNaviId(1);
		bn.setNaviName("test");
		bn.setAdminId(2);
	}

	@Test
	public void testGetById() {

		Serializable id = commonService.save(bn);

		BaseNavi tmp = commonService.getById(BaseNavi.class, id);

		Assert.assertNotNull(tmp);

		commonService.delete(tmp);
	}

	@Test
	public void testSave() {

		Serializable id = commonService.save(bn);

		BaseNavi tmp = commonService.getById(BaseNavi.class, id);

		Assert.assertEquals(tmp.getNaviName(), bn.getNaviName());

		commonService.delete(tmp);
	}

	@Test
	public void testDelete() {
		Serializable id = commonService.save(bn);
		commonService.delete(BaseNavi.class, id);
	}

	@Test
	public void testDeleteClassOfTSerializable() {
		Serializable id = commonService.save(bn);
		commonService.delete(BaseNavi.class, id);
	}

}
