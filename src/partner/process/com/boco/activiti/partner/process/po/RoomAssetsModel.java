package com.boco.activiti.partner.process.po;

import java.io.Serializable;

/**
 * 机房资产MODEL类
 * @author WANGJUN
 *
 */
public class RoomAssetsModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//主键
	private String id;
	//资产编号
	private String assetNumbers;
	//资产名称
	private String assetName;
	//资产标签号
	private String assetTagNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetNumbers() {
		return assetNumbers;
	}

	public void setAssetNumbers(String assetNumbers) {
		this.assetNumbers = assetNumbers;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAssetTagNumber() {
		return assetTagNumber;
	}

	public void setAssetTagNumber(String assetTagNumber) {
		this.assetTagNumber = assetTagNumber;
	}

}
