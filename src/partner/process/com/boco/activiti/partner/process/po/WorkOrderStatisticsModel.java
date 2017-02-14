package com.boco.activiti.partner.process.po;

/**
 * User: zhuchengxu
 * Date: 13-9-17
 * Time: 下午2:45
 */
public class WorkOrderStatisticsModel {
    private String city;
    private int citylength;
    private String cityName;
    private int sumNum;
    private int overtimeNum;
    private String overtimeRate;
    private int archiveNumber;
    private int unfiledNumber;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSumNum() {
        return sumNum;
    }

    public void setSumNum(int sumNum) {
        this.sumNum = sumNum;
    }

    public int getOvertimeNum() {
        return overtimeNum;
    }

    public void setOvertimeNum(int overtimeNum) {
        this.overtimeNum = overtimeNum;
    }

    public String getOvertimeRate() {
        return overtimeRate;
    }

    public void setOvertimeRate(String overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    public int getArchiveNumber() {
        return archiveNumber;
    }

    public void setArchiveNumber(int archiveNumber) {
        this.archiveNumber = archiveNumber;
    }

    public int getUnfiledNumber() {
        return unfiledNumber;
    }

    public void setUnfiledNumber(int unfiledNumber) {
        this.unfiledNumber = unfiledNumber;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

	public int getCitylength() {
		return citylength;
	}

	public void setCitylength(int citylength) {
		this.citylength = citylength;
	}

}
