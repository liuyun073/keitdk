package org.keitdk.commons.i18n.support;

import org.keitdk.commons.i18n.impl.ResourceBundleI18nService;


public class MyResourceBundleI18nService extends ResourceBundleI18nService {

	public MyResourceBundleI18nService() {
		setBasename("org.keitdk.commons.i18n.messages");
	}

	// ~ Methods
	// ========================================================================================================
	public static I18nServiceAccessor getAccessor() {
		
		return new I18nServiceAccessor(new MyResourceBundleI18nService());
	}
}
