package base.model;

import com.boco.eoms.base.webapp.form.BaseForm;

public class GenericBusinessModelForm extends BaseForm implements
		java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 这里定义程序Main Model中使用的字段常量，对于某些常量，应该达成命名规范。
	 */
	/*
	 * 这里定义程序Main Model中使用的字段常量，对于某些常量，应该达成命名规范。
	 */
	private String id;// 主键，一般为UUID 32位
	private String status;// 流程状态，该状态可以理解为：“横切面”状态
	private String verticalStatus;// 该状态可以理解为：“纵切面”状态
	private String taskOwnerType;// 任务所有者类型，角色，部门，人等任意类型
	private String taskOwner;// 任务所有者
	private String createUserId;// 记录创建者Id
	private String createDate;// 记录创建时间
	private String createDateAtom;// 记录创建时间Long,用于排序
	private String yearFlag;// 年标记
	private String monthFlag;// 月标记
	private String dayFlag;// 天标记
	private String monitorCompany;// 代维公司;
	private String city;// 地市
	private String country;// 县区

	private String myText1;
	private String myTextType1;

	private String myText2;
	private String myTextType2;

	private String myText3;
	private String myTextType3;

	private String myText4;
	private String myTextType4;

	private String myText5;
	private String myTextType5;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVerticalStatus() {
		return verticalStatus;
	}

	public void setVerticalStatus(String verticalStatus) {
		this.verticalStatus = verticalStatus;
	}

	public String getTaskOwnerType() {
		return taskOwnerType;
	}

	public void setTaskOwnerType(String taskOwnerType) {
		this.taskOwnerType = taskOwnerType;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDateAtom() {
		return createDateAtom;
	}

	public void setCreateDateAtom(String createDateAtom) {
		this.createDateAtom = createDateAtom;
	}

	public String getYearFlag() {
		return yearFlag;
	}

	public void setYearFlag(String yearFlag) {
		this.yearFlag = yearFlag;
	}

	public String getMonthFlag() {
		return monthFlag;
	}

	public void setMonthFlag(String monthFlag) {
		this.monthFlag = monthFlag;
	}

	public String getDayFlag() {
		return dayFlag;
	}

	public void setDayFlag(String dayFlag) {
		this.dayFlag = dayFlag;
	}

	public String getMonitorCompany() {
		return monitorCompany;
	}

	public void setMonitorCompany(String monitorCompany) {
		this.monitorCompany = monitorCompany;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMyText1() {
		return myText1;
	}

	public void setMyText1(String myText1) {
		this.myText1 = myText1;
	}

	public String getMyTextType1() {
		return myTextType1;
	}

	public void setMyTextType1(String myTextType1) {
		this.myTextType1 = myTextType1;
	}

	public String getMyText2() {
		return myText2;
	}

	public void setMyText2(String myText2) {
		this.myText2 = myText2;
	}

	public String getMyTextType2() {
		return myTextType2;
	}

	public void setMyTextType2(String myTextType2) {
		this.myTextType2 = myTextType2;
	}

	public String getMyText3() {
		return myText3;
	}

	public void setMyText3(String myText3) {
		this.myText3 = myText3;
	}

	public String getMyTextType3() {
		return myTextType3;
	}

	public void setMyTextType3(String myTextType3) {
		this.myTextType3 = myTextType3;
	}

	public String getMyText4() {
		return myText4;
	}

	public void setMyText4(String myText4) {
		this.myText4 = myText4;
	}

	public String getMyTextType4() {
		return myTextType4;
	}

	public void setMyTextType4(String myTextType4) {
		this.myTextType4 = myTextType4;
	}

	public String getMyText5() {
		return myText5;
	}

	public void setMyText5(String myText5) {
		this.myText5 = myText5;
	}

	public String getMyTextType5() {
		return myTextType5;
	}

	public void setMyTextType5(String myTextType5) {
		this.myTextType5 = myTextType5;
	}

}
