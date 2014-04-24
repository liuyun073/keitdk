package org.keitdk.commons.core.model;

/**
 * BaseNavi entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class BaseNavi implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer adminId;
	private Integer naviId;
	private String naviName;

	// Constructors

	/** default constructor */
	public BaseNavi() {
	}

	/** full constructor */
	public BaseNavi(Integer adminId, Integer naviId, String naviName) {
		this.adminId = adminId;
		this.naviId = naviId;
		this.naviName = naviName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdminId() {
		return this.adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getNaviId() {
		return this.naviId;
	}

	public void setNaviId(Integer naviId) {
		this.naviId = naviId;
	}

	public String getNaviName() {
		return this.naviName;
	}

	public void setNaviName(String naviName) {
		this.naviName = naviName;
	}

}