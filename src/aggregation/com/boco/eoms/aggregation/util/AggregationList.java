package com.boco.eoms.aggregation.util;


import java.util.Date;
import java.util.List;
import java.util.Map;


public class AggregationList {

	private AggregationList aggregationList; // aggregation mode

	private List urlList; //

	private List nameList; //

	private List modeList; //

	private String key; // 

	private String keyChild;

	private List MapList;

	private boolean Boolean;

	public List getModeList() {
		return modeList;
	}

	public void setModeList(List modeList) {
		this.modeList = modeList;
	}

	public boolean isBoolean() {
		return Boolean;
	}

	public void setBoolean(boolean b) {
		Boolean = b;
	}

	public AggregationList getAggregationList() {
		return aggregationList;
	}

	public void setAggregationList(AggregationList aggregationList) {
		this.aggregationList = aggregationList;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List getNameList() {
		return nameList;
	}

	public void setNameList(List nameList) {
		this.nameList = nameList;
	}

	public List getUrlList() {
		return urlList;
	}

	public void setUrlList(List urlList) {
		this.urlList = urlList;
	}

	public String getKeyChild() {
		return keyChild;
	}

	public void setKeyChild(String keyChild) {
		this.keyChild = keyChild;
	}

	public List getMapList() {
		return MapList;
	}

	public void setMapList(List mapList) {
		MapList = mapList;
	}

}
