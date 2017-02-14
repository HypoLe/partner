package com.boco.eoms.partner.deviceAssess.util;

import java.util.List;
import java.util.Map;

public class MapObj {
	private String key;//专业，在全专业统计中为  设备类型
	private List list;
	private List listScore;//得分
	private List listRanking;//排名
	private List listPercent;//设备占比
	private int deviceTypeSize;//每种专业下对应的设备类型个数
	private List<String> stringList;
	private List<Integer> integerList;
	private List<Map> mapList;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public int getDeviceTypeSize() {
		return deviceTypeSize;
	}
	public void setDeviceTypeSize(int deviceTypeSize) {
		this.deviceTypeSize = deviceTypeSize;
	}
	public List<Map> getMapList() {
		return mapList;
	}
	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}
	public List<String> getStringList() {
		return stringList;
	}
	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}
	public List<Integer> getIntegerList() {
		return integerList;
	}
	public void setIntegerList(List<Integer> integerList) {
		this.integerList = integerList;
	}
	public List getListPercent() {
		return listPercent;
	}
	public void setListPercent(List listPercent) {
		this.listPercent = listPercent;
	}
	public List getListRanking() {
		return listRanking;
	}
	public void setListRanking(List listRanking) {
		this.listRanking = listRanking;
	}
	public List getListScore() {
		return listScore;
	}
	public void setListScore(List listScore) {
		this.listScore = listScore;
	}
	
}
