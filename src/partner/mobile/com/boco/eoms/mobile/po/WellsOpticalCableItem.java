package com.boco.eoms.mobile.po;

import java.io.Serializable;

public class WellsOpticalCableItem implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键ID
	private String id;
	// 光缆名称
	private String cableName;
	// 管孔信息
	private String pipeHoleInfor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCableName() {
		return cableName;
	}

	public void setCableName(String cableName) {
		this.cableName = cableName;
	}

	public String getPipeHoleInfor() {
		return pipeHoleInfor;
	}

	public void setPipeHoleInfor(String pipeHoleInfor) {
		this.pipeHoleInfor = pipeHoleInfor;
	}

}
