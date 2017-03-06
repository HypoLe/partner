package com.boco.eoms.partner.shortperiod.po;

public class TowerModel {
	private String productNum; // 产品业务确认单编号

	private String resName;// 站址名称

	private String cityName;// 运营商地市

	private String countryName;// 区县

	private String nameId;// 站址编码

	private String nameNumber;// 需求确认单编号

	private String a2;// 产品类型(原始)
	private String a3; // 机房类型(原始)
	private String a4; // 产品单元数1(原始)
	private String a5; // 对应实际最高天线挂高1(原始)
	private String a6; // BBU是否放在铁塔公司机房1(原始)
	private String a8; // 产品单元数2(原始)
	private String a9; // 实际最高天线挂高2(原始)
	private String a10;// 是否放在铁塔公司机房2(原始)
	private String a12;// 产品单元数3(原始)
	private String a13;// 实际最高天线挂高3(原始)
	private String a14;// BBU是否放在铁塔公司机房3(原始)
	private String a16;// 期末铁塔共享用户数(原始)
	private String a21;// 期末机房共享用户数(原始)
	private String a26;// 配套共享用户数(原始)
	private String a35;// 场地费共享用户数(原始)
	private String a41;// 电力引入费共享用户数(原始)
	private String a48;// 维护费共享用户数(原始)

	private String newResName; // 站点名称(核查)
	private String newa2; // 产品类型(核查)
	private String newa3; // 机房类型(核查)
	private String newa4; // 产品单元数1(核查)
	private String newa5; // 对应实际最高天线挂高1(核查)
	private String newa6; // BBU是否放在铁塔公司机房1(核查)
	private String newa8; // 产品单元数2(核查)
	private String newa9; // 实际最高天线挂高2(核查)
	private String newa10; // BBU是否放在铁塔公司机房2(核查)
	private String newa12; // 产品单元数3(核查)
	private String newa13; // 实际最高天线挂高3(核查)
	private String newa14; // BBU是否放在铁塔公司机房3(核查)
	private String newa16; // 期末铁塔共享用户数(核查)
	private String newa21; // 期末机房共享用户数(核查)
	private String newa26; // 配套共享用户数(核查)
	private String newa31; // 维护费共享用户数(核查)
	private String newa36; // 场地费共享用户数(核查)
	private String newa41; // 电力引入费共享用户数(核查)
	
	private String newa17;//	铁塔共享运营商1的起租日期(核查)
	private String newa18;//	铁塔共享运营商1起租后的共享折扣(核查)
	private String newa19;//	铁塔共享运营商2的起租日期(核查)
	private String newa20;//	铁塔共享运营商2起租后的共享折扣(核查)
	private String newa22;//	机房共享运营商1的起租日期(核查)
	private String newa23;//	机房共享运营商1起租后的共享折扣(核查)
	private String newa24;//	机房共享运营商2的起租日期(核查)
	private String newa25;//	机房共享运营商2起租后的共享折扣(核查)
	private String newa27;//	配套共享运营商1的起租日期(核查)
	private String newa28;//	配套共享运营商1起租后的共享折扣(核查)
	private String newa29;//	配套共享运营商2的起租日期(核查)
	private String newa30;//	配套共享运营商2起租后的共享折扣(核查)
	private String newa32;//	维护费共享运营商1的起租日期(核查)
	private String newa33;//	维护费共享运营商1起租后的共享折扣(核查)
	private String newa34;//	维护费共享运营商2的起租日期(核查)
	private String newa35;//	维护费共享运营商2起租后的共享折扣(核查)
	private String newa48;//	场地费(核查)
	private String newa49;//	电力引入费(核查)
	private String newa37;//	场地费共享运营商1的起租日期(核查)
	private String newa38;//	场地费共享运营商1起租后的共享折扣(核查)
	private String newa39;//	场地费共享运营商2的起租日期(核查)
	private String newa40;//	场地费共享运营商2起租后的共享折扣(核查)
	private String newa42;//	电力引入费共享运营商1的起租日期(核查)
	private String newa43;//	电力引入费共享运营商1起租后的共享折扣(核查)
	private String newa44;//	电力引入费共享运营商2的起租日期(核查)
	private String newa45;//	电力引入费共享运营商2起租后的共享折扣(核查)
	private String towerRemark;//备注
	private String towerDescribe;//描述
	
	//以下针对铁塔核查20170210
	private String cityId;//地市ID
	private String countyId;//区县ID
	private String stationCode;//站址编码(新)
	private String needNo;//需求确认单编号
	private String towerType;//塔型
	private String roomType;//机房类型
	private String hangHigh1;//实际最高天线挂高（米）1
	private String rruRoom1;//RRU拉远时BBU是否放在铁塔公司机房1
	private String towerNum;//铁塔共享用户数
	private String roomNum;//机房共享用户数
	private String supportNum;//配套共享用户数
	private String maitainNum;//维护费共享用户数
	private String fieldNum;//场地费共享用户数
	private String powerNum;//电力引入费共享用户数
	private String rruNum;//RRU数量
	private String antennaNum;//天线数量
	
	private String bTowerType;//塔型(back表)
	private String bRoomType;//机房类型(back表)
    private String bHangHigh1;//实际最高天线挂高（米）1(back表)
    private String bRruRoom1;//RRU拉远时BBU是否放在铁塔公司机房1(back表)
    private String bTowerNum;//铁塔共享用户数(back表)
    private String bRoomNum;//机房共享用户数(back表)
    private String bSupportNum;//配套共享用户数(back表)
    private String bMaitainNum;//维护费共享用户数(back表)
    private String bFieldNum;//场地费共享用户数(back表)
    private String bPowerNum;//电力引入费共享用户数(back表)
    private String bRruNum;//RRU数量
    private String bAntennaNum;//天线数量
	
	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getNameNumber() {
		return nameNumber;
	}

	public void setNameNumber(String nameNumber) {
		this.nameNumber = nameNumber;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA5() {
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getA6() {
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA8() {
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA12() {
		return a12;
	}

	public void setA12(String a12) {
		this.a12 = a12;
	}

	public String getA13() {
		return a13;
	}

	public void setA13(String a13) {
		this.a13 = a13;
	}

	public String getA14() {
		return a14;
	}

	public void setA14(String a14) {
		this.a14 = a14;
	}

	public String getA16() {
		return a16;
	}

	public void setA16(String a16) {
		this.a16 = a16;
	}

	public String getA21() {
		return a21;
	}

	public void setA21(String a21) {
		this.a21 = a21;
	}

	public String getA26() {
		return a26;
	}

	public void setA26(String a26) {
		this.a26 = a26;
	}

	public String getA35() {
		return a35;
	}

	public void setA35(String a35) {
		this.a35 = a35;
	}

	public String getA41() {
		return a41;
	}

	public void setA41(String a41) {
		this.a41 = a41;
	}

	public String getA48() {
		return a48;
	}

	public void setA48(String a48) {
		this.a48 = a48;
	}

	public String getNewResName() {
		return newResName;
	}

	public void setNewResName(String newResName) {
		this.newResName = newResName;
	}

	public String getNewa2() {
		return newa2;
	}

	public void setNewa2(String newa2) {
		this.newa2 = newa2;
	}

	public String getNewa3() {
		return newa3;
	}

	public void setNewa3(String newa3) {
		this.newa3 = newa3;
	}

	public String getNewa4() {
		return newa4;
	}

	public void setNewa4(String newa4) {
		this.newa4 = newa4;
	}

	public String getNewa5() {
		return newa5;
	}

	public void setNewa5(String newa5) {
		this.newa5 = newa5;
	}

	public String getNewa6() {
		return newa6;
	}

	public void setNewa6(String newa6) {
		this.newa6 = newa6;
	}

	public String getNewa8() {
		return newa8;
	}

	public void setNewa8(String newa8) {
		this.newa8 = newa8;
	}

	public String getNewa9() {
		return newa9;
	}

	public void setNewa9(String newa9) {
		this.newa9 = newa9;
	}

	public String getNewa10() {
		return newa10;
	}

	public void setNewa10(String newa10) {
		this.newa10 = newa10;
	}

	public String getNewa12() {
		return newa12;
	}

	public void setNewa12(String newa12) {
		this.newa12 = newa12;
	}

	public String getNewa13() {
		return newa13;
	}

	public void setNewa13(String newa13) {
		this.newa13 = newa13;
	}

	public String getNewa14() {
		return newa14;
	}

	public void setNewa14(String newa14) {
		this.newa14 = newa14;
	}

	public String getNewa16() {
		return newa16;
	}

	public void setNewa16(String newa16) {
		this.newa16 = newa16;
	}

	public String getNewa21() {
		return newa21;
	}

	public void setNewa21(String newa21) {
		this.newa21 = newa21;
	}

	public String getNewa26() {
		return newa26;
	}

	public void setNewa26(String newa26) {
		this.newa26 = newa26;
	}

	public String getNewa31() {
		return newa31;
	}

	public void setNewa31(String newa31) {
		this.newa31 = newa31;
	}

	public String getNewa36() {
		return newa36;
	}

	public void setNewa36(String newa36) {
		this.newa36 = newa36;
	}

	public String getNewa41() {
		return newa41;
	}

	public void setNewa41(String newa41) {
		this.newa41 = newa41;
	}

	public String a2() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNewa24() {
		return newa24;
	}

	public void setNewa24(String newa24) {
		this.newa24 = newa24;
	}

	public String getNewa25() {
		return newa25;
	}

	public void setNewa25(String newa25) {
		this.newa25 = newa25;
	}

	public String getNewa27() {
		return newa27;
	}

	public void setNewa27(String newa27) {
		this.newa27 = newa27;
	}

	public String getNewa28() {
		return newa28;
	}

	public void setNewa28(String newa28) {
		this.newa28 = newa28;
	}

	public String getNewa29() {
		return newa29;
	}

	public void setNewa29(String newa29) {
		this.newa29 = newa29;
	}

	public String getNewa30() {
		return newa30;
	}

	public void setNewa30(String newa30) {
		this.newa30 = newa30;
	}

	public String getNewa32() {
		return newa32;
	}

	public void setNewa32(String newa32) {
		this.newa32 = newa32;
	}

	public String getNewa33() {
		return newa33;
	}

	public void setNewa33(String newa33) {
		this.newa33 = newa33;
	}

	public String getNewa34() {
		return newa34;
	}

	public void setNewa34(String newa34) {
		this.newa34 = newa34;
	}

	public String getNewa35() {
		return newa35;
	}

	public void setNewa35(String newa35) {
		this.newa35 = newa35;
	}

	public String getNewa48() {
		return newa48;
	}

	public void setNewa48(String newa48) {
		this.newa48 = newa48;
	}

	public String getNewa49() {
		return newa49;
	}

	public void setNewa49(String newa49) {
		this.newa49 = newa49;
	}

	public String getNewa37() {
		return newa37;
	}

	public void setNewa37(String newa37) {
		this.newa37 = newa37;
	}

	public String getNewa38() {
		return newa38;
	}

	public void setNewa38(String newa38) {
		this.newa38 = newa38;
	}

	public String getNewa39() {
		return newa39;
	}

	public void setNewa39(String newa39) {
		this.newa39 = newa39;
	}

	public String getNewa40() {
		return newa40;
	}

	public void setNewa40(String newa40) {
		this.newa40 = newa40;
	}

	public String getNewa42() {
		return newa42;
	}

	public void setNewa42(String newa42) {
		this.newa42 = newa42;
	}

	public String getNewa43() {
		return newa43;
	}

	public void setNewa43(String newa43) {
		this.newa43 = newa43;
	}

	public String getNewa44() {
		return newa44;
	}

	public void setNewa44(String newa44) {
		this.newa44 = newa44;
	}

	public String getNewa45() {
		return newa45;
	}

	public void setNewa45(String newa45) {
		this.newa45 = newa45;
	}

	public String getNewa17() {
		return newa17;
	}

	public void setNewa17(String newa17) {
		this.newa17 = newa17;
	}

	public String getNewa18() {
		return newa18;
	}

	public void setNewa18(String newa18) {
		this.newa18 = newa18;
	}

	public String getNewa19() {
		return newa19;
	}

	public void setNewa19(String newa19) {
		this.newa19 = newa19;
	}

	public String getNewa20() {
		return newa20;
	}

	public void setNewa20(String newa20) {
		this.newa20 = newa20;
	}

	public String getNewa22() {
		return newa22;
	}

	public void setNewa22(String newa22) {
		this.newa22 = newa22;
	}

	public String getNewa23() {
		return newa23;
	}

	public void setNewa23(String newa23) {
		this.newa23 = newa23;
	}

	public String getTowerRemark() {
		return towerRemark;
	}

	public void setTowerRemark(String towerRemark) {
		this.towerRemark = towerRemark;
	}

	public String getTowerDescribe() {
		return towerDescribe;
	}

	public void setTowerDescribe(String towerDescribe) {
		this.towerDescribe = towerDescribe;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getNeedNo() {
		return needNo;
	}

	public void setNeedNo(String needNo) {
		this.needNo = needNo;
	}

	public String getTowerType() {
		return towerType;
	}

	public void setTowerType(String towerType) {
		this.towerType = towerType;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getHangHigh1() {
		return hangHigh1;
	}

	public void setHangHigh1(String hangHigh1) {
		this.hangHigh1 = hangHigh1;
	}

	public String getRruRoom1() {
		return rruRoom1;
	}

	public void setRruRoom1(String rruRoom1) {
		this.rruRoom1 = rruRoom1;
	}

	public String getTowerNum() {
		return towerNum;
	}

	public void setTowerNum(String towerNum) {
		this.towerNum = towerNum;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getSupportNum() {
		return supportNum;
	}

	public void setSupportNum(String supportNum) {
		this.supportNum = supportNum;
	}

	public String getMaitainNum() {
		return maitainNum;
	}

	public void setMaitainNum(String maitainNum) {
		this.maitainNum = maitainNum;
	}

	public String getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(String fieldNum) {
		this.fieldNum = fieldNum;
	}

	public String getPowerNum() {
		return powerNum;
	}

	public void setPowerNum(String powerNum) {
		this.powerNum = powerNum;
	}

	public String getBTowerType() {
		return bTowerType;
	}

	public void setBTowerType(String towerType) {
		bTowerType = towerType;
	}

	public String getBRoomType() {
		return bRoomType;
	}

	public void setBRoomType(String roomType) {
		bRoomType = roomType;
	}

	public String getBHangHigh1() {
		return bHangHigh1;
	}

	public void setBHangHigh1(String hangHigh1) {
		bHangHigh1 = hangHigh1;
	}

	public String getBRruRoom1() {
		return bRruRoom1;
	}

	public void setBRruRoom1(String rruRoom1) {
		bRruRoom1 = rruRoom1;
	}

	public String getBTowerNum() {
		return bTowerNum;
	}

	public void setBTowerNum(String towerNum) {
		bTowerNum = towerNum;
	}

	public String getBRoomNum() {
		return bRoomNum;
	}

	public void setBRoomNum(String roomNum) {
		bRoomNum = roomNum;
	}

	public String getBSupportNum() {
		return bSupportNum;
	}

	public void setBSupportNum(String supportNum) {
		bSupportNum = supportNum;
	}

	public String getBMaitainNum() {
		return bMaitainNum;
	}

	public void setBMaitainNum(String maitainNum) {
		bMaitainNum = maitainNum;
	}

	public String getBFieldNum() {
		return bFieldNum;
	}

	public void setBFieldNum(String fieldNum) {
		bFieldNum = fieldNum;
	}

	public String getBPowerNum() {
		return bPowerNum;
	}

	public void setBPowerNum(String powerNum) {
		bPowerNum = powerNum;
	}

	public String getRruNum() {
		return rruNum;
	}

	public void setRruNum(String rruNum) {
		this.rruNum = rruNum;
	}

	public String getAntennaNum() {
		return antennaNum;
	}

	public void setAntennaNum(String antennaNum) {
		this.antennaNum = antennaNum;
	}

	public String getBRruNum() {
		return bRruNum;
	}

	public void setBRruNum(String rruNum) {
		bRruNum = rruNum;
	}

	public String getBAntennaNum() {
		return bAntennaNum;
	}

	public void setBAntennaNum(String antennaNum) {
		bAntennaNum = antennaNum;
	}
}
