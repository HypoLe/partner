package com.boco.eoms.commons.system.dept.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * Generated by XDoclet/actionform. This class can be further processed with XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 *
 * @struts.form name="tawSystemDeptForm" 
 */
public class TawSystemDeptForm
    extends    BaseForm
    implements java.io.Serializable
{
	private String tmpdeptid;
    protected String deleted;

    protected String deptemail;

    protected String deptfax;

    protected String deptId;

    protected String deptmanager;

    protected String deptmobile;

    protected String deptName;

    protected String deptphone;

    protected String deptType;

    protected Integer id;

    protected String operremoteip;

    protected String opertime;

    protected String operuserid;

    protected String ordercode;

    protected String parentDeptid;

    protected String regionflag;

    protected String remark;

    protected String tmporarybegintime;

    protected String tmporaryManager;

    protected String tmporarystopTime;

    protected String updatetime;
    
    protected String leaf ;
    
    protected Long postId;
    
    protected String postName;

    protected String newPostId;
    
    protected String areaid;
    
    
    protected String newAreaId;
	
    protected String linkid;
    protected String parentLinkid;
    protected String parentDeptName;
    private String olddeptId;
    private String oldparentDeptid;
    
    //判断是否是代维公司
    private String isPartners;
    
    //以下字段是代维公司特有的字段，TawPartnersDept
	private String qualification;//公司资质（字典）
	private String registerFund;//注册资金
	private String companyType;//公司类型（字典）
	private String setupTime;//成立时间
	private String fixAsset;//固定资产
	private String peopleNumber;//企业人数
	private String companyWeb;//公司网页
	private String contacter;//联系人
	private String businessLicense;//营业执照范围
	private String certificationDept;//认证机关
	private String professionalLevel;//专业资质/等级
	private String isUnicomAssociation;//联通关联（字典，是/否 ）
	private String managePeople;//管理人员
	private String seniorTitle;//高级职称
	private String intermediateTitle;//中级职称
	private String juniorTitle;//初级职称
	private String workers;//职工
	private String ownCars;//自有车辆(台)（主从表）
	private String ownInstrument;//自有仪表（主从表）
	private String lines;//线路(公里)（主从表，代维的维护内容）
	private String baseStations;//基站（个）（主从表）
	private String towers;//铁塔（座）（主从表）
	private String microwaves;//微波（跳）（主从表）
	private String powerAndSet;//电源及配套（套）（主从表）
	private String qualificationValidity;//资质有效期
	private String bankAccount;//银行帐号
	private String thirdServiceContract;//第三方服务合同信息
	private String attachName;//附件名
	
	private FormFile accessoryName; //批量录入文件
    
	public FormFile getAccessoryName() {
		return accessoryName;
	}
	public void setAccessoryName(FormFile accessoryName) {
		this.accessoryName = accessoryName;
	}
	public String getOlddeptId() {
		return olddeptId;
	}
	public void setOlddeptId(String olddeptId) {
		this.olddeptId = olddeptId;
	}
	public String getOldparentDeptid() {
		return oldparentDeptid;
	}
	public void setOldparentDeptid(String oldparentDeptid) {
		this.oldparentDeptid = oldparentDeptid;
	}
	public String getLinkid() {
		return linkid;
	}
	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	/**
	 * @return Returns the newPostId.
	 */
	public String getNewPostId() {
		return newPostId;
	}
	/**
	 * @param newPostId The newPostId to set.
	 */
	public void setNewPostId(String newPostId) {
		this.newPostId = newPostId;
	}
	/**
	 * @return Returns the postName.
	 */
	public String getPostName() {
		return postName;
	}
	/**
	 * @param postName The postName to set.
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}
	/**
	 * @return Returns the postId.
	 */
	public Long getPostId() {
		return postId;
	}
	/**
	 * @param postId The postId to set.
	 */
	public void setPostId(Long postId) {
		this.postId = postId;
	}
    public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	/** Default empty constructor. */
    public TawSystemDeptForm() {}

    public String getDeleted()
    {
        return this.deleted;
    }
   /**
    */

    public void setDeleted( String deleted )
    {
        this.deleted = deleted;
    }

    public String getDeptemail()
    {
        return this.deptemail;
    }
   /**
    */

    public void setDeptemail( String deptemail )
    {
        this.deptemail = deptemail;
    }

    public String getDeptfax()
    {
        return this.deptfax;
    }
   /**
    */

    public void setDeptfax( String deptfax )
    {
        this.deptfax = deptfax;
    }

    public String getDeptId()
    {
        return this.deptId;
    }
   /**
    */

    public void setDeptId( String deptId )
    {
        this.deptId = deptId;
    }

    public String getDeptmanager()
    {
        return this.deptmanager;
    }
   /**
    */

    public void setDeptmanager( String deptmanager )
    {
        this.deptmanager = deptmanager;
    }

    public String getDeptmobile()
    {
        return this.deptmobile;
    }
   /**
    */

    public void setDeptmobile( String deptmobile )
    {
        this.deptmobile = deptmobile;
    }

    public String getDeptName()
    {
        return this.deptName;
    }
   /**
    */

    public void setDeptName( String deptName )
    {
        this.deptName = deptName;
    }

    public String getDeptphone()
    {
        return this.deptphone;
    }
   /**
    */

    public void setDeptphone( String deptphone )
    {
        this.deptphone = deptphone;
    }

    public String getDeptType()
    {
        return this.deptType;
    }
   /**
    */

    public void setDeptType( String deptType )
    {
        this.deptType = deptType;
    }

    public Integer getId()
    {
        return this.id;
    }
   /**
    */

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getOperremoteip()
    {
        return this.operremoteip;
    }
   /**
    */

    public void setOperremoteip( String operremoteip )
    {
        this.operremoteip = operremoteip;
    }

    public String getOpertime()
    {
        return this.opertime;
    }
   /**
    */

    public void setOpertime( String opertime )
    {
        this.opertime = opertime;
    }

    public String getOperuserid()
    {
        return this.operuserid;
    }
   /**
    */

    public void setOperuserid( String operuserid )
    {
        this.operuserid = operuserid;
    }

    public String getOrdercode()
    {
        return this.ordercode;
    }
   /**
    */

    public void setOrdercode( String ordercode )
    {
        this.ordercode = ordercode;
    }

    public String getParentDeptid()
    {
        return this.parentDeptid;
    }
   /**
    */

    public void setParentDeptid( String parentDeptid )
    {
        this.parentDeptid = parentDeptid;
    }

    public String getRegionflag()
    {
        return this.regionflag;
    }
   /**
    */

    public void setRegionflag( String regionflag )
    {
        this.regionflag = regionflag;
    }

    public String getRemark()
    {
        return this.remark;
    }
   /**
    */

    public void setRemark( String remark )
    {
        this.remark = remark;
    }

    public String getTmporarybegintime()
    {
        return this.tmporarybegintime;
    }
   /**
    */

    public void setTmporarybegintime( String tmporarybegintime )
    {
        this.tmporarybegintime = tmporarybegintime;
    }

    public String getTmporaryManager()
    {
        return this.tmporaryManager;
    }
   /**
    */

    public void setTmporaryManager( String tmporaryManager )
    {
        this.tmporaryManager = tmporaryManager;
    }

    public String getTmporarystopTime()
    {
        return this.tmporarystopTime;
    }
   /**
    */

    public void setTmporarystopTime( String tmporarystopTime )
    {
        this.tmporarystopTime = tmporarystopTime;
    }

    public String getUpdatetime()
    {
        return this.updatetime;
    }
   /**
    */

    public void setUpdatetime( String updatetime )
    {
        this.updatetime = updatetime;
    }

        /* To add non XDoclet-generated methods, create a file named
           xdoclet-TawSystemDeptForm.java 
           containing the additional code and place it in your metadata/web directory.
        */
    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *                                                javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // reset any boolean data types to false

    }
	public String getNewAreaId() {
		return newAreaId;
	}
	public void setNewAreaId(String newAreaId) {
		this.newAreaId = newAreaId;
	}
	public String getParentDeptName() {
		return parentDeptName;
	}
	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
	public String getTmpdeptid() {
		return tmpdeptid;
	}
	public void setTmpdeptid(String tmpdeptid) {
		this.tmpdeptid = tmpdeptid;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getRegisterFund() {
		return registerFund;
	}
	public void setRegisterFund(String registerFund) {
		this.registerFund = registerFund;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getSetupTime() {
		return setupTime;
	}
	public void setSetupTime(String setupTime) {
		this.setupTime = setupTime;
	}
	public String getFixAsset() {
		return fixAsset;
	}
	public void setFixAsset(String fixAsset) {
		this.fixAsset = fixAsset;
	}
	public String getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(String peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	public String getCompanyWeb() {
		return companyWeb;
	}
	public void setCompanyWeb(String companyWeb) {
		this.companyWeb = companyWeb;
	}
	public String getContacter() {
		return contacter;
	}
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getCertificationDept() {
		return certificationDept;
	}
	public void setCertificationDept(String certificationDept) {
		this.certificationDept = certificationDept;
	}
	public String getProfessionalLevel() {
		return professionalLevel;
	}
	public void setProfessionalLevel(String professionalLevel) {
		this.professionalLevel = professionalLevel;
	}
	public String getIsUnicomAssociation() {
		return isUnicomAssociation;
	}
	public void setIsUnicomAssociation(String isUnicomAssociation) {
		this.isUnicomAssociation = isUnicomAssociation;
	}
	public String getManagePeople() {
		return managePeople;
	}
	public void setManagePeople(String managePeople) {
		this.managePeople = managePeople;
	}
	public String getSeniorTitle() {
		return seniorTitle;
	}
	public void setSeniorTitle(String seniorTitle) {
		this.seniorTitle = seniorTitle;
	}
	public String getIntermediateTitle() {
		return intermediateTitle;
	}
	public void setIntermediateTitle(String intermediateTitle) {
		this.intermediateTitle = intermediateTitle;
	}
	public String getJuniorTitle() {
		return juniorTitle;
	}
	public void setJuniorTitle(String juniorTitle) {
		this.juniorTitle = juniorTitle;
	}
	public String getWorkers() {
		return workers;
	}
	public void setWorkers(String workers) {
		this.workers = workers;
	}
	public String getOwnCars() {
		return ownCars;
	}
	public void setOwnCars(String ownCars) {
		this.ownCars = ownCars;
	}
	public String getOwnInstrument() {
		return ownInstrument;
	}
	public void setOwnInstrument(String ownInstrument) {
		this.ownInstrument = ownInstrument;
	}
	public String getLines() {
		return lines;
	}
	public void setLines(String lines) {
		this.lines = lines;
	}
	public String getBaseStations() {
		return baseStations;
	}
	public void setBaseStations(String baseStations) {
		this.baseStations = baseStations;
	}
	public String getTowers() {
		return towers;
	}
	public void setTowers(String towers) {
		this.towers = towers;
	}
	public String getMicrowaves() {
		return microwaves;
	}
	public void setMicrowaves(String microwaves) {
		this.microwaves = microwaves;
	}
	public String getPowerAndSet() {
		return powerAndSet;
	}
	public void setPowerAndSet(String powerAndSet) {
		this.powerAndSet = powerAndSet;
	}
	public String getQualificationValidity() {
		return qualificationValidity;
	}
	public void setQualificationValidity(String qualificationValidity) {
		this.qualificationValidity = qualificationValidity;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getThirdServiceContract() {
		return thirdServiceContract;
	}
	public void setThirdServiceContract(String thirdServiceContract) {
		this.thirdServiceContract = thirdServiceContract;
	}
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getIsPartners() {
		return isPartners;
	}
	public void setIsPartners(String isPartners) {
		this.isPartners = isPartners;
	}
	public String getParentLinkid() {
		return parentLinkid;
	}
	public void setParentLinkid(String parentLinkid) {
		this.parentLinkid = parentLinkid;
	}

}
