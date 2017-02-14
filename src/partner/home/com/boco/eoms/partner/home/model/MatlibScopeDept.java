package com.boco.eoms.partner.home.model;

/**
 * MatlibScopeDept entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MatlibScopeDept implements java.io.Serializable {

	// Fields

	private String id;
	private String matlibid;
	private String scopedeptid;

	// Constructors

	/** default constructor */
	public MatlibScopeDept() {
	}

	/** full constructor */
	public MatlibScopeDept(String matlibid, String scopedeptid) {
		this.matlibid = matlibid;
		this.scopedeptid = scopedeptid;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatlibid() {
		return this.matlibid;
	}

	public void setMatlibid(String matlibid) {
		this.matlibid = matlibid;
	}

	public String getScopedeptid() {
		return this.scopedeptid;
	}

	public void setScopedeptid(String scopedeptid) {
		this.scopedeptid = scopedeptid;
	}

}