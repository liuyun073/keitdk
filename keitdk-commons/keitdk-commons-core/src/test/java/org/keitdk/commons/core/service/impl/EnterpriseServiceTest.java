package org.keitdk.commons.core.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.keitdk.commons.core.model.BaseNavi;
import org.keitdk.commons.core.service.EnterpriseService;
import org.keitdk.commons.test.spring.SpringTransactionalTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "/spring/applicationContext*.xml" })
public class EnterpriseServiceTest extends SpringTransactionalTestCase{

	@Resource(name="enterpriseService")
	EnterpriseService  enterpriseService;

	BaseNavi bn;

	@Before
	public void before(){
		bn = new BaseNavi();
		bn.setNaviId(1);
		bn.setNaviName("test");
		bn.setAdminId(2);
	}

	@Rollback
	@Test
	public void testQueryString() {

		Session session = enterpriseService.getSessionFactory().openSession();
		session.beginTransaction();

		Serializable id = session.save(bn);

		BaseNavi tmp = (BaseNavi) session.createQuery("from BaseNavi bn where bn.id = " + id ).uniqueResult();

		session.delete(tmp);

		session.getTransaction().commit();
	}


}
