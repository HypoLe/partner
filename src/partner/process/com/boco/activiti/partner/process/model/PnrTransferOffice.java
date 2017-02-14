package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 传输局工单
 * @author chenbing
 *
 *
 */
/**
  * @author wangyue
  * @ClassName: PnrTransferOffice
  * @Copyright 亿阳信通
  * @date Oct 23, 2014 11:06:25 AM
  * @description 类描述
*/
public class PnrTransferOffice  implements Cloneable,Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    //工单主题
    private String theme;
    //区分接口与传输局  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；overhaul 大修工单；reform 改造工单；
    private String themeInterface;
    //故障发生时间
    private Date createTime;
    //派单时间
    private Date sendTime;
    //派单人
    private String initiator;
    //工单接收人
    private String taskAssignee;
    //故障联系人+手机号
    private String connectPerson;
    //故障处理时限
    private Double processLimit;
    //故障来源
    private String faultSource;
    //故障描述
    private String faultDescription;
    //工单子类型
    private String subType;
    //工单流程ID
    private String processInstanceId;
    //状态 <0：正常>；<1：删除>;<3：待办状态>;<5:归档>;<6:正在会审>;<7：会审结束>;<8:等待运维商城接口调用>；<9:概预算系统已返回金额>；<10:手机端已提交抽查，等待WEB端添加信息>
    private Integer state;
    // 归档时间
    private Date archivingTime;
    //接收人字符串
    private String taskAssigneeJSON;   
    // 最后回复时间    
    private Date lastReplyTime;
    //保存草稿时间
    private Date saveDraftDate;
    //工单附件的英文名称字符串
    private String sheetAccessories;
    //附件个数
    private Integer attachmentsNum;
    //计划完成时间
    private Date endTime;
    //第一派单人
    private String oneInitiator;
    //二次派发时间
    private Date secondSendTime;
    //第二派单人
    private String secondInitiator;
    //第三派发时间
    private Date thirdSendTime;
    //传输局派发 0;外包派发 1;外包质检 2;传输局质检 3;质检 4;回复  5
    private String stepState;
    //手机端时间显示--start
//  派单时间
  private String androidSendTime ;
//  计划完成时间
  private String androidEndTime;
//  故障发生时间
  private String androidCreateTime;
  private String androidApplicationTime;
//手机端时间显示--end
  
  	//接机员
    private String engineer;
    //装机地址
    private String installAddress;
    //接入方式
    private String pattern;
    //地市
    private String city;
    //局站名称
    private String stationName;
   //交接箱名称
    private String spliceBoxName;
    //主干电缆编码
    private String cableNumber;
    //分线盒编码
    private String branchBoxNumber;
    //一级分光器编码
    private String firstOpticalNumber;
    //一级分光器端口
    private String firstOpticalPort;
    //二级分光器编码
    private String secondOpticalNumber;
    //二级分光器端口
    private String secondOpticalPort;
    //光交接箱编码
    private String spliceBoxNumber;
    //光交接箱端口
    private String spliceBoxPort;
    //公客工单编号--仅在公客接口使用
    private String maleGuestNumber;
    /**故障站点*/
    private String faileSite;
    /**是否集团客户*/
    private String isCustomers;
    /**涉及专业*/
    private String specialty;
    /**帐号*/
    private String busiNbr;
    /**局站编码*/
    private String siteCd;
    /**交接箱编码*/
    private String ccpCd;
    /**传输局人员id*/
    private String transferOfficeId;
    /**接线员*/
    private String operator;
    /**障碍业务号码*/
    private String barrierNumber;
    /**公客工单的状态 0 正常工单；1 归集工单；2 归集工单的子工单,3 从归集工单中解锁的**/
    private String maleGuestState;
    /**公客工单的归集工单的工单号 “-“ 正常工单；”-“ 归集工单；”归集工单号“ 归集工单的子工单**/
    private String parentProcessInstanceId;
    
    private List list = new ArrayList();
    //预检预修新增字段
    /**工单类型*/
    private String workOrderType;
    /**紧急程度*/
    private String workOrderDegree;
    /**关键字*/
    private String keyWord;
    /**预检预修提交申请时间*/
    private Date submitApplicationTime;
    /**工单号*/
    private String workOrderNumber;
    /**需求发起人*/
    private String createWork;
    /**县传输局*/
    private String countryCSJ;
    /**市传输局*/
    private String cityCSJ;//2015-2-12停止使用
    /**市公司*/
    private String cityGS;
    /**工单号是否存在标志：true 存在，false  不存在*/
    private boolean workFlag;
    /**工单子类型名称*/
    private String subTypeName;
    /**工单类型名称*/
    private String workOrderTypeName;
    /**调用接口返回信息*/
    private String returnMessage;
    /**重障碍：空 正常派单；1 重修；2 投诉；3 非本传输局责任二次派单*/
    private String repeatState;
    /**废除标志：1  该工单已废除*/
    private String doFlag;
    /**是否保存草稿标志*/
    private String isDraft;
    /**既是投诉又是重修工单*/
    private String secondRepeatState;
    /**众筹流程id*/
    private String netResInspectId;
    
    /**15-01-14 预检预修*/
    private String workType;
    private Double projectAmount;
    /**省线路审批人*/
    private String provinceLine;
    /**省运维审批人*/
    private String provinceManage;
    /**版本标志：0 代表旧预检预修工单；1 代表新预检预修工单*/
    private String  versionFlag;
    /**区县**/
    private String country;
    
    /**15-02-06新流程预检预修*/
    /**区县主任或者市城区班长*/
    private String secondCreateWork;
    /**市线路业务主管*/
    private String cityLineCharge;
    /**市线路主任*/
    private String cityLineDirector;
    /**市运维主管*/
    private String cityManageCharge;
    /**市运维主任*/
    private String cityManageDirector;
    /**省线路主管*/
    private String provinceLineCharge;
    /**省运维主管*/
    private String provinceManageCharge;
    /**承载业务级别*/
    private String bearService;
    /**驳回标志：0或空代表没有驳回；1代表驳回;2代表抓回;*/
    private String rollbackFlag;
    /**预检预修工单类别*/
    private String precheckFlag;
    
    /**工单编号
     * 工单编号：
		  本地网：
		预检预修：B-JN-CQ-JX-20150215-1 
		抢修：B-JN-CQ-QX-20150215-1
		  干线：
		 预检预修：G- JN-CQ-JX-20150215-1
		抢修：G-JN-CQ-QX-20150215-1
		注释：B代表本地网
		      G代表干线
		      JN代表济南
		      CQ代表长清
		      20150215代表时间
		      1：代表第几个工单

     * */
    private String sheetId;
    /**赔补金额-预检预修（本地网、干线） */
    private Double compensate;
//    纬度--供手机端使用
    private String latitude;
//    经度--供手机端使用
    private String longitude;
    //省运维总经理审批通过时间，即派发接口时间
    private Date distributedInterfaceTime;
    //线路名称
    private String lineName;
    //项目起点
    private String projectStartPoint;
    //项目终点
    private String projectEndPoint;
    //具体地点（标石，杆号，人井号）
    private String specificLocation;
    
    /**15-5-27新建派单改造*/
    //发起人部门
    private String initiatorDept;
    //发起人电话
    private String initiatorMobilePhone;
    //主场景ID
    private String mainSceneId;
    //驳回时展现的子场景ID
    private String showChildSceneId;
    //子场景ID串
    private String childSceneIds;
    //部门负责人
    private String deptHead;
    //部门负责人电话
    private String deptHeadMobilePhone;
    //收支比
    private Double calculateIncomeRatio;
    //建设原因及必要性
    private String constructionReasons;
    //网络现状描述
    private String networkStatus;
    //主要建设内容及规模
    private String constructionMainContent;
    //敷设光缆
    private Double layingCable;
    //开挖揽沟
    private Double excavationTrench;
    //整修管道
    private Double repairPipeline;
    //扶正（拆除）电杆
    private Double rightingDemolitionPole;
    //敷设钢绞线
    private Double wireLaying;
    //光缆接头
    private Double fiberOpticCableConnector;
    //其它
    private String mainQuantityOther;
    //创建子场景字符串
    private String createStr;
    //拆除子场景字符串
    private String deleteStr;
    
    //接口之后审核环节的操作人
    private String daiweiAuditPerson;
    //接口之后抽查环节的操作人
    private String orderAuditPerson;
    
    //2015-6-25大修改造新添字段
    //项目编号
    private String projectName;
    //大修改造类别
    private String overhaulType;
    //线路级别
    private String lineType;
    //敷设方式
    private String layingType;
    //中续段
    private String middlePart;
    //光缆型号
    private String cableType;
    //芯数
    private String coreNum;
    //起点经度
    private String createLongitude;
    //起点纬度
    private String createLatitude;
    //终点经度
    private String endLongitude;
    //终点纬度
    private String endLatitude;
    //项目主管签名
    private String chargeName;
    //(主管)电话
    private String chargeTel;
    //补贴合同编号
    private String subsidyNumber;
    //皮长公里造价
    //private double longLeatherMoney;
    //孔公里造价
    //private double holeMoney;
   //皮长公里造价
    private Double longLeatherMoney;
    //孔公里造价
    private Double holeMoney;
    //是否提醒
    private Integer isRemind;
    
    //150916添加
    //工单类型（针对olt-bbu）
    private String jobOrderType;
    
    //批次
    private String batch;
    
    //入库时间
    private Date storageTime;
    
    //乙方费用的总额(新建派发环节)
    private Double sumNeedCostOfPartyB;
    
    //乙方费用的总额(工单处理环节)
    private Double sumWorkerCostOfPartyB;
    
    //工单处理-项目金额估算
    private Double workerProjectAmount; 
	
    //工单处理-收支比
	private Double workerIncomeRatio;	
	
	//工单处理-子场景的IDS
	private String workerChildIds;	
	
	//工单处理-子场景的NAMES
	private String workerChildNames;
	
	//抽查-项目金额估算
	private Double orderauditProjectAmount;	
	
	//抽查-收支比
	private Double orderauditIncomeRatio;	
	
	//抽查-子场景的IDS
	private String orderauditChildIds;	
	
	//抽查-子场景的NAMES
	private String orderauditChildNames;
	
	//抽查-乙方费用总额 
	private Double sumOrderAuditCostOfPartyB;
	
	//工单处理环节的处理标识（主要针对该环节的场景模板）null:手机端未处理，web端未处理；1：手机端已处理，web端未处理；2：手机端已处理，web端已处理
	private String workerSceneHandleFlag;
	
	//区县公司验收环节的处理标识（主要针对该环节的场景模板）null:手机端未处理，web端未处理；1：手机端已处理，web端未处理；2：手机端已处理，web端已处理
	private String workerSceneOrderAuditFlag;
	
	//记录商城是否已派发  1为已派发  空或0为未派发
    private String isDistribute;
    
    /**
     * 工单环节key 及 环节名称
     */
    private String assignee;
    private String taskDefKey;
    private String taskDefKeyName;
    private String taskId; 
    
    //一次核验审批通过时间
    private Date firstVerifyDate;
    //二次抽查审批通过时间
    private Date secondInspectionDate;    
    
    //施工队照片补录标识 1：需要补录照片；0：不需要补录照片
    private String makeupPhotoFlag;
    
    //照片补录的时间
    private Date makeupPhotoDate;
    
    //施工队环节的主场景中文值
    private String transferMainScenesName;
    
    //施工队环节的子场景中文值
    private String transferCopyScenesName;
    
    //最新的主场景中文值
    private String recentMainScenesName;
    
    //最新的子场景中文值
    private String recentCopyScenesName;
    
    //实物赔补
    private Double kindRestitution;
    
    //抽检标识
    private String samplingState;
    
    //抽检评价
    private String samplingJudgments;
    
    //抽检时间
    private Date samplingDate;
    
    //抽检人
    private String samplingUserId;
    
    private String daiweiAuditSceneHandleFlag;//区县抽检标识
    private Double daiweiAuditCostOfPartyB;//区县抽检乙方费用总和
    private Double daiweiAuditProjectAmount;//区县抽检项目金额
    private Double daiweiAuditIncomeRatio;//区县抽检收支比
    private String daiweiAuditChildIds;//区县抽检-子场景的IDS
    private String daiweiAuditChildNames;//区县抽检-子场景的NAMES
    
    private String samplingMarkUserid;//省公司抽检的标记人
    
	public String getMakeupPhotoFlag() {
		return makeupPhotoFlag;
	}

	public void setMakeupPhotoFlag(String makeupPhotoFlag) {
		this.makeupPhotoFlag = makeupPhotoFlag;
	}

	public Date getMakeupPhotoDate() {
		return makeupPhotoDate;
	}

	public void setMakeupPhotoDate(Date makeupPhotoDate) {
		this.makeupPhotoDate = makeupPhotoDate;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getTaskDefKeyName() {
		return taskDefKeyName;
	}

	public void setTaskDefKeyName(String taskDefKeyName) {
		this.taskDefKeyName = taskDefKeyName;
	}

	public Date getSaveDraftDate() {
		return saveDraftDate;
	}

	public void setSaveDraftDate(Date saveDraftDate) {
		this.saveDraftDate = saveDraftDate;
	}

	public String getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(String isDraft) {
		this.isDraft = isDraft;
	}

	public Integer getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(Integer isRemind) {
		this.isRemind = isRemind;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getSecondSendTime() {
		return secondSendTime;
	}

	public void setSecondSendTime(Date secondSendTime) {
		this.secondSendTime = secondSendTime;
	}

	public Date getLastReplyTime() {
    	return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
    }

    public Date getArchivingTime() {
        return archivingTime;
    }

    public void setArchivingTime(Date archivingTime) {
        this.archivingTime = archivingTime;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    
    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getSendTime() {
        return sendTime;
    }
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getInitiator() {
        return initiator;
    }
    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }
    public String getTaskAssignee() {
        return taskAssignee;
    }
    public void setTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }
   
    public String getConnectPerson() {
        return connectPerson;
    }
    public void setConnectPerson(String connectPerson) {
        this.connectPerson = connectPerson;
    }
    public Double getProcessLimit() {
        return processLimit;
    }
    public void setProcessLimit(Double processLimit) {
        this.processLimit = processLimit;
    }
	public String getFaultDescription() {
        return faultDescription;
    }
    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }
    public String getSubType() {
        return subType;
    }
    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getTaskAssigneeJSON() {
		return taskAssigneeJSON;
	}

	public void setTaskAssigneeJSON(String taskAssigneeJSON) {
		this.taskAssigneeJSON = taskAssigneeJSON;
	}
	public String getFaultSource() {
		return faultSource;
	}

	public void setFaultSource(String faultSource) {
		this.faultSource = faultSource;
	}


	public String getSheetAccessories() {
		return sheetAccessories;
	}

	public void setSheetAccessories(String sheetAccessories) {
		this.sheetAccessories = sheetAccessories;
	}

	public Integer getAttachmentsNum() {
		return attachmentsNum;
	}

	public void setAttachmentsNum(Integer attachmentsNum) {
		this.attachmentsNum = attachmentsNum;
	}	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getOneInitiator() {
		return oneInitiator;
	}

	public void setOneInitiator(String oneInitiator) {
		this.oneInitiator = oneInitiator;
	}

	public PnrTransferOffice() {

    }

	public String getAndroidSendTime() {
		return androidSendTime;
	}

	public void setAndroidSendTime(String androidSendTime) {
		this.androidSendTime = androidSendTime;
	}

	public String getAndroidEndTime() {
		return androidEndTime;
	}

	public void setAndroidEndTime(String androidEndTime) {
		this.androidEndTime = androidEndTime;
	}

	public String getAndroidCreateTime() {
		return androidCreateTime;
	}

	public void setAndroidCreateTime(String androidCreateTime) {
		this.androidCreateTime = androidCreateTime;
	}

	public String getSecondInitiator() {
		return secondInitiator;
	}

	public void setSecondInitiator(String secondInitiator) {
		this.secondInitiator = secondInitiator;
	}

	public Date getThirdSendTime() {
		return thirdSendTime;
	}

	public void setThirdSendTime(Date thirdSendTime) {
		this.thirdSendTime = thirdSendTime;
	}

	public String getThemeInterface() {
		return themeInterface;
	}

	public void setThemeInterface(String themeInterface) {
		this.themeInterface = themeInterface;
	}

	public String getEngineer() {
		return engineer;
	}

	public void setEngineer(String engineer) {
		this.engineer = engineer;
	}

	public String getInstallAddress() {
		return installAddress;
	}

	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getSpliceBoxName() {
		return spliceBoxName;
	}

	public void setSpliceBoxName(String spliceBoxName) {
		this.spliceBoxName = spliceBoxName;
	}

	public String getCableNumber() {
		return cableNumber;
	}

	public void setCableNumber(String cableNumber) {
		this.cableNumber = cableNumber;
	}

	public String getBranchBoxNumber() {
		return branchBoxNumber;
	}

	public void setBranchBoxNumber(String branchBoxNumber) {
		this.branchBoxNumber = branchBoxNumber;
	}

	public String getFirstOpticalNumber() {
		return firstOpticalNumber;
	}

	public void setFirstOpticalNumber(String firstOpticalNumber) {
		this.firstOpticalNumber = firstOpticalNumber;
	}

	public String getFirstOpticalPort() {
		return firstOpticalPort;
	}

	public void setFirstOpticalPort(String firstOpticalPort) {
		this.firstOpticalPort = firstOpticalPort;
	}

	public String getSecondOpticalNumber() {
		return secondOpticalNumber;
	}

	public void setSecondOpticalNumber(String secondOpticalNumber) {
		this.secondOpticalNumber = secondOpticalNumber;
	}

	public String getSecondOpticalPort() {
		return secondOpticalPort;
	}

	public void setSecondOpticalPort(String secondOpticalPort) {
		this.secondOpticalPort = secondOpticalPort;
	}

	public String getSpliceBoxNumber() {
		return spliceBoxNumber;
	}

	public void setSpliceBoxNumber(String spliceBoxNumber) {
		this.spliceBoxNumber = spliceBoxNumber;
	}

	public String getSpliceBoxPort() {
		return spliceBoxPort;
	}

	public void setSpliceBoxPort(String spliceBoxPort) {
		this.spliceBoxPort = spliceBoxPort;
	}

	public String getStepState() {
		return stepState;
	}

	public void setStepState(String stepState) {
		this.stepState = stepState;
	}

	public String getMaleGuestNumber() {
		return maleGuestNumber;
	}

	public void setMaleGuestNumber(String maleGuestNumber) {
		this.maleGuestNumber = maleGuestNumber;
	}

	public String getFaileSite() {
		return faileSite;
	}

	public void setFaileSite(String faileSite) {
		this.faileSite = faileSite;
	}

	public String getIsCustomers() {
		return isCustomers;
	}

	public void setIsCustomers(String isCustomers) {
		this.isCustomers = isCustomers;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getBusiNbr() {
		return busiNbr;
	}

	public void setBusiNbr(String busiNbr) {
		this.busiNbr = busiNbr;
	}

	public String getSiteCd() {
		return siteCd;
	}

	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}

	public String getCcpCd() {
		return ccpCd;
	}

	public void setCcpCd(String ccpCd) {
		this.ccpCd = ccpCd;
	}

	public String getTransferOfficeId() {
		return transferOfficeId;
	}

	public void setTransferOfficeId(String transferOfficeId) {
		this.transferOfficeId = transferOfficeId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	

	public String getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}

	public String getWorkOrderDegree() {
		return workOrderDegree;
	}

	public void setWorkOrderDegree(String workOrderDegree) {
		this.workOrderDegree = workOrderDegree;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getBarrierNumber() {
		return barrierNumber;
	}

	public void setBarrierNumber(String barrierNumber) {
		this.barrierNumber = barrierNumber;
	}

	public Date getSubmitApplicationTime() {
		return submitApplicationTime;
	}

	public void setSubmitApplicationTime(Date submitApplicationTime) {
		this.submitApplicationTime = submitApplicationTime;
	}

	public String getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(String workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	public String getCreateWork() {
		return createWork;
	}

	public void setCreateWork(String createWork) {
		this.createWork = createWork;
	}

	public String getCountryCSJ() {
		return countryCSJ;
	}

	public void setCountryCSJ(String countryCSJ) {
		this.countryCSJ = countryCSJ;
	}

	public String getCityCSJ() {
		return cityCSJ;
	}

	public void setCityCSJ(String cityCSJ) {
		this.cityCSJ = cityCSJ;
	}

	public String getCityGS() {
		return cityGS;
	}

	public void setCityGS(String cityGS) {
		this.cityGS = cityGS;
	}

	public boolean isWorkFlag() {
		return workFlag;
	}

	public void setWorkFlag(boolean workFlag) {
		this.workFlag = workFlag;
	}

	public String getAndroidApplicationTime() {
		return androidApplicationTime;
	}

	public void setAndroidApplicationTime(String androidApplicationTime) {
		this.androidApplicationTime = androidApplicationTime;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public String getWorkOrderTypeName() {
		return workOrderTypeName;
	}

	public void setWorkOrderTypeName(String workOrderTypeName) {
		this.workOrderTypeName = workOrderTypeName;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getRepeatState() {
		return repeatState;
	}

	public void setRepeatState(String repeatState) {
		this.repeatState = repeatState;
	}

	public String getDoFlag() {
		return doFlag;
	}

	public void setDoFlag(String doFlag) {
		this.doFlag = doFlag;
	}

	public String getSecondRepeatState() {
		return secondRepeatState;
	}

	public void setSecondRepeatState(String secondRepeatState) {
		this.secondRepeatState = secondRepeatState;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Double getProjectAmount() {
		return projectAmount;
	}

	public void setProjectAmount(Double projectAmount) {
		this.projectAmount = projectAmount;
	}

	public String getProvinceLine() {
		return provinceLine;
	}

	public void setProvinceLine(String provinceLine) {
		this.provinceLine = provinceLine;
	}

	public String getProvinceManage() {
		return provinceManage;
	}

	public void setProvinceManage(String provinceManage) {
		this.provinceManage = provinceManage;
	}

	public String getVersionFlag() {
		return versionFlag;
	}

	public void setVersionFlag(String versionFlag) {
		this.versionFlag = versionFlag;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	public String getSecondCreateWork() {
		return secondCreateWork;
	}

	public void setSecondCreateWork(String secondCreateWork) {
		this.secondCreateWork = secondCreateWork;
	}

	public String getCityLineCharge() {
		return cityLineCharge;
	}

	public void setCityLineCharge(String cityLineCharge) {
		this.cityLineCharge = cityLineCharge;
	}

	public String getCityLineDirector() {
		return cityLineDirector;
	}

	public void setCityLineDirector(String cityLineDirector) {
		this.cityLineDirector = cityLineDirector;
	}

	public String getCityManageCharge() {
		return cityManageCharge;
	}

	public void setCityManageCharge(String cityManageCharge) {
		this.cityManageCharge = cityManageCharge;
	}

	public String getCityManageDirector() {
		return cityManageDirector;
	}

	public void setCityManageDirector(String cityManageDirector) {
		this.cityManageDirector = cityManageDirector;
	}

	public String getProvinceLineCharge() {
		return provinceLineCharge;
	}

	public void setProvinceLineCharge(String provinceLineCharge) {
		this.provinceLineCharge = provinceLineCharge;
	}

	public String getProvinceManageCharge() {
		return provinceManageCharge;
	}

	public void setProvinceManageCharge(String provinceManageCharge) {
		this.provinceManageCharge = provinceManageCharge;
	}

	public String getBearService() {
		return bearService;
	}

	public void setBearService(String bearService) {
		this.bearService = bearService;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public String getRollbackFlag() {
		return rollbackFlag;
	}

	public void setRollbackFlag(String rollbackFlag) {
		this.rollbackFlag = rollbackFlag;
	}

	public String getPrecheckFlag() {
		return precheckFlag;
	}

	public void setPrecheckFlag(String precheckFlag) {
		this.precheckFlag = precheckFlag;
	}

	public Double getCompensate() {
		return compensate;
	}

	public void setCompensate(Double compensate) {
		this.compensate = compensate;
	}

	public Date getDistributedInterfaceTime() {
		return distributedInterfaceTime;
	}

	public void setDistributedInterfaceTime(Date distributedInterfaceTime) {
		this.distributedInterfaceTime = distributedInterfaceTime;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getProjectStartPoint() {
		return projectStartPoint;
	}

	public void setProjectStartPoint(String projectStartPoint) {
		this.projectStartPoint = projectStartPoint;
	}

	public String getProjectEndPoint() {
		return projectEndPoint;
	}

	public void setProjectEndPoint(String projectEndPoint) {
		this.projectEndPoint = projectEndPoint;
	}

	public String getSpecificLocation() {
		return specificLocation;
	}

	public void setSpecificLocation(String specificLocation) {
		this.specificLocation = specificLocation;
	}

	public String getInitiatorDept() {
		return initiatorDept;
	}

	public void setInitiatorDept(String initiatorDept) {
		this.initiatorDept = initiatorDept;
	}

	public String getInitiatorMobilePhone() {
		return initiatorMobilePhone;
	}

	public void setInitiatorMobilePhone(String initiatorMobilePhone) {
		this.initiatorMobilePhone = initiatorMobilePhone;
	}

	public String getMainSceneId() {
		return mainSceneId;
	}

	public void setMainSceneId(String mainSceneId) {
		this.mainSceneId = mainSceneId;
	}

	public String getShowChildSceneId() {
		return showChildSceneId;
	}

	public void setShowChildSceneId(String showChildSceneId) {
		this.showChildSceneId = showChildSceneId;
	}

	public String getChildSceneIds() {
		return childSceneIds;
	}

	public void setChildSceneIds(String childSceneIds) {
		this.childSceneIds = childSceneIds;
	}

	public String getDeptHead() {
		return deptHead;
	}

	public void setDeptHead(String deptHead) {
		this.deptHead = deptHead;
	}

	public String getDeptHeadMobilePhone() {
		return deptHeadMobilePhone;
	}

	public void setDeptHeadMobilePhone(String deptHeadMobilePhone) {
		this.deptHeadMobilePhone = deptHeadMobilePhone;
	}

	public Double getCalculateIncomeRatio() {
		return calculateIncomeRatio;
	}

	public void setCalculateIncomeRatio(Double calculateIncomeRatio) {
		this.calculateIncomeRatio = calculateIncomeRatio;
	}

	public String getConstructionReasons() {
		return constructionReasons;
	}

	public void setConstructionReasons(String constructionReasons) {
		this.constructionReasons = constructionReasons;
	}

	public String getNetworkStatus() {
		return networkStatus;
	}

	public void setNetworkStatus(String networkStatus) {
		this.networkStatus = networkStatus;
	}

	public String getConstructionMainContent() {
		return constructionMainContent;
	}

	public void setConstructionMainContent(String constructionMainContent) {
		this.constructionMainContent = constructionMainContent;
	}

	public Double getLayingCable() {
		return layingCable;
	}

	public void setLayingCable(Double layingCable) {
		this.layingCable = layingCable;
	}

	public Double getExcavationTrench() {
		return excavationTrench;
	}

	public void setExcavationTrench(Double excavationTrench) {
		this.excavationTrench = excavationTrench;
	}

	public Double getRepairPipeline() {
		return repairPipeline;
	}

	public void setRepairPipeline(Double repairPipeline) {
		this.repairPipeline = repairPipeline;
	}

	public Double getRightingDemolitionPole() {
		return rightingDemolitionPole;
	}

	public void setRightingDemolitionPole(Double rightingDemolitionPole) {
		this.rightingDemolitionPole = rightingDemolitionPole;
	}

	public Double getWireLaying() {
		return wireLaying;
	}

	public void setWireLaying(Double wireLaying) {
		this.wireLaying = wireLaying;
	}

	public Double getFiberOpticCableConnector() {
		return fiberOpticCableConnector;
	}

	public void setFiberOpticCableConnector(Double fiberOpticCableConnector) {
		this.fiberOpticCableConnector = fiberOpticCableConnector;
	}

	public String getMainQuantityOther() {
		return mainQuantityOther;
	}

	public void setMainQuantityOther(String mainQuantityOther) {
		this.mainQuantityOther = mainQuantityOther;
	}

	public String getCreateStr() {
		return createStr;
	}

	public void setCreateStr(String createStr) {
		this.createStr = createStr;
	}

	public String getDeleteStr() {
		return deleteStr;
	}

	public void setDeleteStr(String deleteStr) {
		this.deleteStr = deleteStr;
	}

	public String getDaiweiAuditPerson() {
		return daiweiAuditPerson;
	}

	public void setDaiweiAuditPerson(String daiweiAuditPerson) {
		this.daiweiAuditPerson = daiweiAuditPerson;
	}

	public String getOrderAuditPerson() {
		return orderAuditPerson;
	}

	public void setOrderAuditPerson(String orderAuditPerson) {
		this.orderAuditPerson = orderAuditPerson;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOverhaulType() {
		return overhaulType;
	}

	public void setOverhaulType(String overhaulType) {
		this.overhaulType = overhaulType;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public String getLayingType() {
		return layingType;
	}

	public void setLayingType(String layingType) {
		this.layingType = layingType;
	}

	public String getMiddlePart() {
		return middlePart;
	}

	public void setMiddlePart(String middlePart) {
		this.middlePart = middlePart;
	}

	public String getCableType() {
		return cableType;
	}

	public void setCableType(String cableType) {
		this.cableType = cableType;
	}

	public String getCoreNum() {
		return coreNum;
	}

	public void setCoreNum(String coreNum) {
		this.coreNum = coreNum;
	}

	public String getCreateLongitude() {
		return createLongitude;
	}

	public void setCreateLongitude(String createLongitude) {
		this.createLongitude = createLongitude;
	}

	public String getCreateLatitude() {
		return createLatitude;
	}

	public void setCreateLatitude(String createLatitude) {
		this.createLatitude = createLatitude;
	}

	public String getEndLongitude() {
		return endLongitude;
	}

	public void setEndLongitude(String endLongitude) {
		this.endLongitude = endLongitude;
	}

	public String getEndLatitude() {
		return endLatitude;
	}

	public void setEndLatitude(String endLatitude) {
		this.endLatitude = endLatitude;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public String getChargeTel() {
		return chargeTel;
	}

	public void setChargeTel(String chargeTel) {
		this.chargeTel = chargeTel;
	}


	public String getSubsidyNumber() {
		return subsidyNumber;
	}

	public void setSubsidyNumber(String subsidyNumber) {
		this.subsidyNumber = subsidyNumber;
	}

	public Double getLongLeatherMoney() {
		return longLeatherMoney;
	}

	public void setLongLeatherMoney(Double longLeatherMoney) {
		this.longLeatherMoney = longLeatherMoney;
	}

	public Double getHoleMoney() {
		return holeMoney;
	}

	public void setHoleMoney(Double holeMoney) {
		this.holeMoney = holeMoney;
	}

	public String getJobOrderType() {
		return jobOrderType;
	}

	public void setJobOrderType(String jobOrderType) {
		this.jobOrderType = jobOrderType;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Date getStorageTime() {
		return storageTime;
	}

	public void setStorageTime(Date storageTime) {
		this.storageTime = storageTime;
	}

	public Double getSumNeedCostOfPartyB() {
		return sumNeedCostOfPartyB;
	}

	public void setSumNeedCostOfPartyB(Double sumNeedCostOfPartyB) {
		this.sumNeedCostOfPartyB = sumNeedCostOfPartyB;
	}

	public Double getSumWorkerCostOfPartyB() {
		return sumWorkerCostOfPartyB;
	}

	public void setSumWorkerCostOfPartyB(Double sumWorkerCostOfPartyB) {
		this.sumWorkerCostOfPartyB = sumWorkerCostOfPartyB;
	}

	public Double getWorkerProjectAmount() {
		return workerProjectAmount;
	}

	public void setWorkerProjectAmount(Double workerProjectAmount) {
		this.workerProjectAmount = workerProjectAmount;
	}

	public Double getWorkerIncomeRatio() {
		return workerIncomeRatio;
	}

	public void setWorkerIncomeRatio(Double workerIncomeRatio) {
		this.workerIncomeRatio = workerIncomeRatio;
	}

	public String getWorkerChildIds() {
		return workerChildIds;
	}

	public void setWorkerChildIds(String workerChildIds) {
		this.workerChildIds = workerChildIds;
	}

	public String getWorkerChildNames() {
		return workerChildNames;
	}

	public void setWorkerChildNames(String workerChildNames) {
		this.workerChildNames = workerChildNames;
	}

	public Double getOrderauditProjectAmount() {
		return orderauditProjectAmount;
	}

	public void setOrderauditProjectAmount(Double orderauditProjectAmount) {
		this.orderauditProjectAmount = orderauditProjectAmount;
	}

	public Double getOrderauditIncomeRatio() {
		return orderauditIncomeRatio;
	}

	public void setOrderauditIncomeRatio(Double orderauditIncomeRatio) {
		this.orderauditIncomeRatio = orderauditIncomeRatio;
	}

	public String getOrderauditChildIds() {
		return orderauditChildIds;
	}

	public void setOrderauditChildIds(String orderauditChildIds) {
		this.orderauditChildIds = orderauditChildIds;
	}

	public String getOrderauditChildNames() {
		return orderauditChildNames;
	}

	public void setOrderauditChildNames(String orderauditChildNames) {
		this.orderauditChildNames = orderauditChildNames;
	}

	public Double getSumOrderAuditCostOfPartyB() {
		return sumOrderAuditCostOfPartyB;
	}

	public void setSumOrderAuditCostOfPartyB(Double sumOrderAuditCostOfPartyB) {
		this.sumOrderAuditCostOfPartyB = sumOrderAuditCostOfPartyB;
	}

	public String getWorkerSceneHandleFlag() {
		return workerSceneHandleFlag;
	}

	public void setWorkerSceneHandleFlag(String workerSceneHandleFlag) {
		this.workerSceneHandleFlag = workerSceneHandleFlag;
	}
	
	public String getIsDistribute() {
		return isDistribute;
	}

	public void setIsDistribute(String isDistribute) {
		this.isDistribute = isDistribute;
	}

	public String getWorkerSceneOrderAuditFlag() {
		return workerSceneOrderAuditFlag;
	}

	public void setWorkerSceneOrderAuditFlag(String workerSceneOrderAuditFlag) {
		this.workerSceneOrderAuditFlag = workerSceneOrderAuditFlag;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getMaleGuestState() {
		return maleGuestState;
	}

	public void setMaleGuestState(String maleGuestState) {
		this.maleGuestState = maleGuestState;
	}

	public String getParentProcessInstanceId() {
		return parentProcessInstanceId;
	}

	public void setParentProcessInstanceId(String parentProcessInstanceId) {
		this.parentProcessInstanceId = parentProcessInstanceId;
	}
	
	 public PnrTransferOffice clone(){  
		 PnrTransferOffice o = null;  
		         try{  
		             o = (PnrTransferOffice)super.clone();  
		        }catch(CloneNotSupportedException e){  
		            e.printStackTrace();  
		       }  
		         return o;  
		  }  
	public Date getFirstVerifyDate() {
		return firstVerifyDate;
	}

	public void setFirstVerifyDate(Date firstVerifyDate) {
		this.firstVerifyDate = firstVerifyDate;
	}

	public Date getSecondInspectionDate() {
		return secondInspectionDate;
	}

	public void setSecondInspectionDate(Date secondInspectionDate) {
		this.secondInspectionDate = secondInspectionDate;
	}

	public String getTransferMainScenesName() {
		return transferMainScenesName;
	}

	public void setTransferMainScenesName(String transferMainScenesName) {
		this.transferMainScenesName = transferMainScenesName;
	}

	public String getTransferCopyScenesName() {
		return transferCopyScenesName;
	}

	public void setTransferCopyScenesName(String transferCopyScenesName) {
		this.transferCopyScenesName = transferCopyScenesName;
	}

	public String getRecentMainScenesName() {
		return recentMainScenesName;
	}

	public void setRecentMainScenesName(String recentMainScenesName) {
		this.recentMainScenesName = recentMainScenesName;
	}

	public String getRecentCopyScenesName() {
		return recentCopyScenesName;
	}

	public void setRecentCopyScenesName(String recentCopyScenesName) {
		this.recentCopyScenesName = recentCopyScenesName;
	}

	public String getNetResInspectId() {
		return netResInspectId;
	}

	public void setNetResInspectId(String netResInspectId) {
		this.netResInspectId = netResInspectId;
	}

	public Double getKindRestitution() {
		return kindRestitution;
	}

	public void setKindRestitution(Double kindRestitution) {
		this.kindRestitution = kindRestitution;
	}

	public String getSamplingState() {
		return samplingState;
	}

	public void setSamplingState(String samplingState) {
		this.samplingState = samplingState;
	}

	public String getSamplingJudgments() {
		return samplingJudgments;
	}

	public void setSamplingJudgments(String samplingJudgments) {
		this.samplingJudgments = samplingJudgments;
	}

	public Date getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(Date samplingDate) {
		this.samplingDate = samplingDate;
	}

	public String getSamplingUserId() {
		return samplingUserId;
	}

	public void setSamplingUserId(String samplingUserId) {
		this.samplingUserId = samplingUserId;
	}

	public String getDaiweiAuditSceneHandleFlag() {
		return daiweiAuditSceneHandleFlag;
	}

	public void setDaiweiAuditSceneHandleFlag(String daiweiAuditSceneHandleFlag) {
		this.daiweiAuditSceneHandleFlag = daiweiAuditSceneHandleFlag;
	}

	public Double getDaiweiAuditCostOfPartyB() {
		return daiweiAuditCostOfPartyB;
	}

	public void setDaiweiAuditCostOfPartyB(Double daiweiAuditCostOfPartyB) {
		this.daiweiAuditCostOfPartyB = daiweiAuditCostOfPartyB;
	}

	public Double getDaiweiAuditProjectAmount() {
		return daiweiAuditProjectAmount;
	}

	public void setDaiweiAuditProjectAmount(Double daiweiAuditProjectAmount) {
		this.daiweiAuditProjectAmount = daiweiAuditProjectAmount;
	}

	public Double getDaiweiAuditIncomeRatio() {
		return daiweiAuditIncomeRatio;
	}

	public void setDaiweiAuditIncomeRatio(Double daiweiAuditIncomeRatio) {
		this.daiweiAuditIncomeRatio = daiweiAuditIncomeRatio;
	}

	public String getDaiweiAuditChildIds() {
		return daiweiAuditChildIds;
	}

	public void setDaiweiAuditChildIds(String daiweiAuditChildIds) {
		this.daiweiAuditChildIds = daiweiAuditChildIds;
	}

	public String getDaiweiAuditChildNames() {
		return daiweiAuditChildNames;
	}

	public void setDaiweiAuditChildNames(String daiweiAuditChildNames) {
		this.daiweiAuditChildNames = daiweiAuditChildNames;
	}

	public String getSamplingMarkUserid() {
		return samplingMarkUserid;
	}

	public void setSamplingMarkUserid(String samplingMarkUserid) {
		this.samplingMarkUserid = samplingMarkUserid;
	}
}
