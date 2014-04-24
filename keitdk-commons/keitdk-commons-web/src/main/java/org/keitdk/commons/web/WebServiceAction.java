package org.keitdk.commons.web;

public class WebServiceAction {

	public final static String ListPageAttributeName = "itemPage";

	public final static String ListAllAttributeName = "commonWebService.List";

	public final static String InstanceAttributeName = "instance";

	/*
	 * to保存记录,返回itemPage；toSave
	 */
	public final static String TO_SAVE = "TOSAVE";

	public final static String TO_SAVEANDLISTPAGE = "TOSAVE";

	public final static int SAVE_ACTION = 1;

	/*
	 * to详细记录,返回object； toDetail
	 */
	public final static String TO_DETAIL = "TODETAIL";

	public final static int VIEW_ACTION = 2;

	/*
	 * to修改记录,返回itemPage； toModify
	 */
	public final static String TO_MODIFY = "TOMODIFY";

	public final static String TO_MODIFYANDLISTPAGE = "TOMODIFY";

	public final static int UPDATE_ACTION = 3;

	/*
	 * to删除记录,返回itemPage；
	 */
	public final static String TO_DELETE = "TODELETE";

	public final static String TO_DELETENADLISTALL = "DELETE&LISTALL";

	public final static String TO_DELETENADLISTPAGE = "TODELETE";

	public final static int DELETE_ACTION = 4;

	/*
	 * to分页； toPage
	 */
	public final static String TO_PAGE = "TOPAGE";

	public final static String TO_LISTALL = "TOLISTALL";

	public final static int QUERY_ACTION = 5;

	/*
	 * to保存记录，返回list；toSaveList
	 */
	public final static String TO_SAVELIST = "TOSAVELIST";

	public final static String TO_SAVEANDLISTALL = "TOSAVELIST";

	public final static int SAVE_LISTALL_ACTION = 6;

	/*
	 * to修改记录，返回list；toModifyList
	 */
	public final static String TO_MODIFYLIST = "TOMODIFYLIST";

	public final static String TO_MODIFYANDLISTALL = "TOMODIFYLIST";

	public final static int UPDATE_LISTALL_ACTION = 7;

	public final static String TO_SAVEVIEW = "TOSAVEVIEW";

	public final static String TO_SAVEANDHINT = "TOSAVEVIEW";

	public final static String TO_UPDATEVIEW = "TOUPDATEVIEW";

	public final static String TO_UPDATEANDHINT = "TOUPDATEVIEW";

	public final static String getEditPageAction(String key) {

		return getEditPageAction(key == null || key.trim().equals(""));
	}

	public final static String getEditPageAction(boolean isNew) {
		if (isNew)
			return TO_SAVEVIEW;
		else
			return TO_UPDATEVIEW;
	}

	public final static String getEditPageAction(String key, boolean popModel) {
		if (popModel) {
			if (key == null || key.trim().equals(""))
				return TO_SAVE;
			else
				return TO_MODIFY;
		} else
			return getEditPageAction(key);
	}

	/**
	 * 消息提醒页
	 */
	public final static String RET_MESSAGE = "message";

	/**
	 * 错误提醒页
	 */
	public final static String RET_ERROR = "error";

	/**
	 * 对话模式消息返回页
	 */
	public final static String RET_DIALOGRESULT = "dialogResult";

	/**
	 * 登陆页
	 */
	public final static String RET_LOGIN = "login";

	/**
	 * 门户页
	 */
	public final static String RET_PORTAL = "portal";

	/**
	 * 后台管理首页
	 *
	 */
	public final static String RET_ADMIN = "admin";

	/**
	 * 缺省页
	 */
	public final static String RET_DEFAULT = "default";

	public final static String RET_SUCCESS = "success";

}