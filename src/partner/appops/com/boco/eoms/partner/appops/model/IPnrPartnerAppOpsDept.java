package com.boco.eoms.partner.appops.model;

import java.util.Date;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:����
 * </p>
 * <p>
 * Description:����
 * </p>
 * <p>
 * Mon Feb 09 10:57:12 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
public class IPnrPartnerAppOpsDept extends BaseObject {
	
	public IPnrPartnerAppOpsDept(String id,String name,String interfaceHeadId){
		this.id = id;
		this.name = name;
		this.interfaceHeadId = interfaceHeadId;
	}

	/**
	 * 主键���
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 *
	 * 合作伙伴名称������
	 *
	 */
	private java.lang.String name;
   
	public void setName(java.lang.String name){
		this.name= name;       
	}
   
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *
	 * 合作伙伴企业性质��������
	 *
	 */
	private java.lang.String type;
   
	public void setType(java.lang.String type){
		this.type= type;       
	}
   
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *
	 * 合作伙伴资质����
	 *
	 */
	private java.lang.String aptitude;
   
	public void setAptitude(java.lang.String aptitude){
		this.aptitude= aptitude;       
	}
   
	public java.lang.String getAptitude(){
		return this.aptitude;
	}

	/**
	 *
	 * 资质有效期
	 *
	 */
	private java.lang.String deadline;
   
	public void setDeadline(java.lang.String deadline){
		this.deadline= deadline;       
	}
   
	public java.lang.String getDeadline(){
		return this.deadline;
	}

	/**
	 *
	 * 合作伙伴法人������Ա
	 *
	 */
	private java.lang.String manager;
   
	public void setManager(java.lang.String manager){
		this.manager= manager;       
	}
   
	public java.lang.String getManager(){
		return this.manager;
	}

	/**
	 *
	 * 区域ID
	 *
	 */
	private java.lang.String areaId;
   
	public void setAreaId(java.lang.String areaId){
		this.areaId= areaId;       
	}
   
	public java.lang.String getAreaId(){
		return this.areaId;
	}

	/**
	 *
	 * 公司地址������
	 *
	 */
	private java.lang.String areaName;
   
	public void setAreaName(java.lang.String areaName){
		this.areaName= areaName;       
	}
   
	public java.lang.String getAreaName(){
		return this.areaName;
	}

	/**
	 *
	 * 注册资金（万）���
	 *
	 */
	private java.lang.String fund;
   
	public void setFund(java.lang.String fund){
		this.fund= fund;       
	}
   
	public java.lang.String getFund(){
		return this.fund;
	}

	/**
	 *
	 * �联系电话
	 *
	 */
	private java.lang.String phone;
   
	public void setPhone(java.lang.String phone){
		this.phone= phone;       
	}
   
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *
	 * 公司地址
	 *
	 */
	private java.lang.String address;
   
	public void setAddress(java.lang.String address){
		this.address= address;       
	}
   
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *
	 * 传真号码����
	 *
	 */
	private java.lang.String fax;
   
	public void setFax(java.lang.String fax){
		this.fax= fax;       
	}
   
	public java.lang.String getFax(){
		return this.fax;
	}
	/**
	 *
	 * 开户银行�ʻ�
	 *
	 */
	private String bank;

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 *
	 * 银行帐号�ʻ�
	 *
	 */
	private java.lang.String account;
   
	public void setAccount(java.lang.String account){
		this.account= account;       
	}
   
	public java.lang.String getAccount(){
		return this.account;
	}

	/**
	 *
	 * 第三方服务合同信息
	 *
	 */
	private java.lang.String third;
   
	public void setThird(java.lang.String third){
		this.third= third;       
	}
   
	public java.lang.String getThird(){
		return this.third;
	}

	/**
	 *
	 * ���
	 *
	 */
	private java.lang.String accessory;
   
	public void setAccessory(java.lang.String accessory){
		this.accessory= accessory;       
	}
   
	public java.lang.String getAccessory(){
		return this.accessory;
	}

	/**
	 *
	 * ��ע
	 *
	 */
	private java.lang.String remark;
   
	public void setRemark(java.lang.String remark){
		this.remark= remark;       
	}
   
	public java.lang.String getRemark(){
		return this.remark;
	}
	
	/**
	 * 对应树节点id
	 */
	private String treeId;

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}
	/**
	 * 对应树节点nodeId
	 */
	private String treeNodeId;
	
	public String getTreeNodeId() {
		return treeNodeId;
	}

	public void setTreeNodeId(String treeNodeId) {
		this.treeNodeId = treeNodeId;
	}
	
	
	/**
	 * 删除标志位
	 * 2009-9-24
	 * liujinlong
	 */
	private String deleted;

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public boolean equals(Object o) {
		if( o instanceof IPnrPartnerAppOpsDept ) {
			IPnrPartnerAppOpsDept partnerDept=(IPnrPartnerAppOpsDept)o;
			if (this.id != null || this.id.equals(partnerDept.getId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 联系人
	 * 2009-9-24
	 * bww
	 */
	private String contactor;

	/**
	 * 邮政编码
	 * 2009-9-24
	 * bww
	 */
	private String zip;	
	/**
	 * 邮箱地址
	 * 2009-9-24
	 * bww
	 */
	private String email;	
	/**
	 * 企业注册号
	 * 2009-9-24
	 * bww
	 */
	private String companyRegister;	
	/**
	 * 廉政协议号
	 * 2009-9-24
	 * bww
	 */
	private String honestAgreement;	
	
	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyRegister() {
		return companyRegister;
	}

	public void setCompanyRegister(String companyRegister) {
		this.companyRegister = companyRegister;
	}

	public String getHonestAgreement() {
		return honestAgreement;
	}

	public void setHonestAgreement(String honestAgreement) {
		this.honestAgreement = honestAgreement;
	}
	
	/**
	 * 存放二维码系统中的总公司的ID
	 * 便于统计
	 * add by wangjunfeng
	 * 2010-01-21
	 */
	private String interfaceHeadId ;

	public String getInterfaceHeadId() {
		return interfaceHeadId;
	}

	public void setInterfaceHeadId(String interfaceHeadId) {
		this.interfaceHeadId = interfaceHeadId;
	}
	
	/**
	 * 新增时间
	 * add by daizhigang
	 * 2010-01-25
	 */
	
	private Date createTime ;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 营业执照号
	 * add by benweiwei
	 * 2010-05-05
	 */
	
	private String licenceNum;

	public String getLicenceNum() {
		return licenceNum;
	}

	public void setLicenceNum(String licenceNum) {
		this.licenceNum = licenceNum;
	}
	
	/**
	 * 公司主页
	 * add by benweiwei
	 * 2010-05-05
	 */
	
	private String homepage;

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * 单位总人数 
	 * add by benweiwei
	 * 2010-05-05
	 */
	
	private String personCou;

	public String getPersonCou() {
		return personCou;
	}

	public void setPersonCou(String personCou) {
		this.personCou = personCou;
	}

	/**
	 * 是否入围
	 * add by benweiwei
	 * 2010-05-05
	 */
	
	private String selected;

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	/**
	 * 开户银行名称
	 * add by benweiwei
	 * 2010-05-05
	 */
	
	private String accName;

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	/**
	 * 税务登记号
	 * add by benweiwei
	 * 2010-05-05
	 */
	
	private String registNo;

	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	
	/**
	 * 企业法人
	 * add by benweiwei
	 * 2010-05-05
	 */
	
	private String managercop;

	public String getManagercop() {
		return managercop;
	}

	public void setManagercop(String managercop) {
		this.managercop = managercop;
	}
	
	/**
	 * 部门管理id（对应）
	 * add by benweiwei
	 * 2010-05-011
	 */
	
	private String deptMagId;

	public String getDeptMagId() {
		return deptMagId;
	}

	public void setDeptMagId(String deptMagId) {
		this.deptMagId = deptMagId;
	}
	
	/**
	 * 大单位类型 (字典多选,用逗号分隔)
	 * add by benweiwei
	 * 2010-05-18
	 */
	
	private String bigType;

	public String getBigType() {
		return bigType;
	}

	public void setBigType(String bigType) {
		this.bigType = bigType;
	}

	/**
	 * 专业
	 * add by benweiwei
	 * 2010-11-08
	 */
	
	private String specialty;

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public IPnrPartnerAppOpsDept() {
		super();
	}
	/**
	 * 公司所属级别:1为省下属,2为市级,3为县级
	 */
	private String deptLevel;

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}
	private String organizationNo;//组织编码
	private String ifCompany;//是否为公司
	private String kindCurrencies;//货币种类
	private String companySynopses;//公司简介
	private String registerTime;//注册日期
	private String selectedLevel;//入围级别
	/*为了便于统计添加的字段 begin add by fengguangping begin*/
	private String provinceId;//省份id
	private String cityId;//区县id;
	private String countyId;//县id
	private int pro1;//基站设备及配套
	private int pro2;//传输线路
	private int pro3;//直放站室分
	private int pro6;//ＷＬＡＮ
	private int pro4;//铁塔及天愧；
	private int pro5;//集客家客
	private int pro7;//扩展用;
	private int pro8;//扩展用;
	private int pro9;//扩展用;
	private int fund1;//100w以下;
	private int fund2;//100-500w以下;
	private int fund3;//500-1000w以下;
	private int fund4;//1000w以上;
	private int sellevel1;//入围级别为集团
	private int sellevel2;//入围级别为省市
	private int sellevel3;//入围级别为地市
	private int companyType1;//组织企业性质为国有企业;
	private int companyType2;//组织企业性质为集体企业;
	private int companyType3;//组织企业性质为联营企业;
	private int companyType4;//组织企业性质为股份合作制企业;
	private int companyType5;//组织企业性质为国有企业私营企业;
	private int companyType6;//组织企业性质为外商独资企业;
	private int companyType7;//组织企业性质为有限责任公司;
	private int companyType8;//组织企业性质为股份有限公司;
	/*为了便于统计添加的字段 begin add by fengguangping end*/
	private int isPartner;//是否是代维人员;2013-06-25 朱成旭添加
	public int getPro1() {
		return pro1;
	}

	public void setPro1(int pro1) {
		this.pro1 = pro1;
	}

	public int getPro2() {
		return pro2;
	}

	public void setPro2(int pro2) {
		this.pro2 = pro2;
	}

	public int getPro3() {
		return pro3;
	}

	public void setPro3(int pro3) {
		this.pro3 = pro3;
	}

	public int getPro4() {
		return pro4;
	}

	public void setPro4(int pro4) {
		this.pro4 = pro4;
	}

	public int getPro5() {
		return pro5;
	}

	public void setPro5(int pro5) {
		this.pro5 = pro5;
	}

	public int getPro7() {
		return pro7;
	}

	public void setPro7(int pro7) {
		this.pro7 = pro7;
	}

	public int getPro8() {
		return pro8;
	}

	public void setPro8(int pro8) {
		this.pro8 = pro8;
	}

	public int getPro9() {
		return pro9;
	}

	public void setPro9(int pro9) {
		this.pro9 = pro9;
	}

	public int getFund1() {
		return fund1;
	}

	public void setFund1(int fund1) {
		this.fund1 = fund1;
	}

	public int getFund2() {
		return fund2;
	}

	public void setFund2(int fund2) {
		this.fund2 = fund2;
	}

	public int getFund3() {
		return fund3;
	}

	public void setFund3(int fund3) {
		this.fund3 = fund3;
	}

	public int getFund4() {
		return fund4;
	}

	public void setFund4(int fund4) {
		this.fund4 = fund4;
	}

	public int getSellevel1() {
		return sellevel1;
	}

	public void setSellevel1(int sellevel1) {
		this.sellevel1 = sellevel1;
	}

	public int getSellevel2() {
		return sellevel2;
	}

	public void setSellevel2(int sellevel2) {
		this.sellevel2 = sellevel2;
	}

	public int getSellevel3() {
		return sellevel3;
	}

	public void setSellevel3(int sellevel3) {
		this.sellevel3 = sellevel3;
	}

	public int getCompanyType1() {
		return companyType1;
	}

	public void setCompanyType1(int companyType1) {
		this.companyType1 = companyType1;
	}

	public int getCompanyType2() {
		return companyType2;
	}

	public void setCompanyType2(int companyType2) {
		this.companyType2 = companyType2;
	}

	public int getCompanyType3() {
		return companyType3;
	}

	public void setCompanyType3(int companyType3) {
		this.companyType3 = companyType3;
	}

	public int getCompanyType4() {
		return companyType4;
	}

	public void setCompanyType4(int companyType4) {
		this.companyType4 = companyType4;
	}

	public int getCompanyType5() {
		return companyType5;
	}

	public void setCompanyType5(int companyType5) {
		this.companyType5 = companyType5;
	}

	public int getCompanyType6() {
		return companyType6;
	}

	public void setCompanyType6(int companyType6) {
		this.companyType6 = companyType6;
	}

	public int getCompanyType7() {
		return companyType7;
	}

	public void setCompanyType7(int companyType7) {
		this.companyType7 = companyType7;
	}

	public int getCompanyType8() {
		return companyType8;
	}

	public void setCompanyType8(int companyType8) {
		this.companyType8 = companyType8;
	}
	
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
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

	public String getSelectedLevel() {
		return selectedLevel;
	}

	public void setSelectedLevel(String selectedLevel) {
		this.selectedLevel = selectedLevel;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getOrganizationNo() {
		return organizationNo;
	}

	public void setOrganizationNo(String organizationNo) {
		this.organizationNo = organizationNo;
	}

	public String getIfCompany() {
		return ifCompany;
	}

	public void setIfCompany(String ifCompany) {
		this.ifCompany = ifCompany;
	}

	public String getKindCurrencies() {
		return kindCurrencies;
	}

	public void setKindCurrencies(String kindCurrencies) {
		this.kindCurrencies = kindCurrencies;
	}

	public String getCompanySynopses() {
		return companySynopses;
	}

	public void setCompanySynopses(String companySynopses) {
		this.companySynopses = companySynopses;
	}

	public int getPro6() {
		return pro6;
	}

	public void setPro6(int pro6) {
		this.pro6 = pro6;
	}

	public int getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(int isPartner) {
		this.isPartner = isPartner;
	}
	
}