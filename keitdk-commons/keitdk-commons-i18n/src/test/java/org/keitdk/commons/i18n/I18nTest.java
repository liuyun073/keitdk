package org.keitdk.commons.i18n;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.keitdk.commons.i18n.support.I18NManager;

/**
 * 国际化测试
 * @author lingen
 *
 */
@Ignore
public class I18nTest {

	@Test
	public void shouldUsingDefaultLocale(){
		String value = I18NManager.getMessage("name");
        Assert.assertTrue("should using default(zh_CN) locale", value.equals("考拉"));
	}
	
	@Test
	public void shouldUsingENLocale(){
		String value = I18NManager.getMessage("name","en");
		Assert.assertTrue("should using en locale", "Koala".equals(value));
	}
}
