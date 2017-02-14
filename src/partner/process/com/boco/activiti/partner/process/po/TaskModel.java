package com.boco.activiti.partner.process.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhuchengxu
 * Date: 13-9-12
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class TaskModel implements Serializable {
    /**
     * 任务ID
     */
   private String taskId;
    //工单主题
    private String theme;
    //派单时间
    private Date sendTime;
    //到期时间
    private Date endTime;
    //派单人
    private String initiator;
    //二次派发时第一派单人
    private String oneInitiator;
    //传输局人员
    private String secondInitiator;
    //执行人
    private String executor;
    
    private Date applicationTime;
    /**
     *     工单流程ID
     */
    private String processInstanceId;
    /**
     * 当前状态
     */
    private String statusName;
    /**
     * 所属部门
     */
    private String deptId;
    /**
     * 时间差
     */
    private String TimeDifference;
    /**
     * TASK_DEF_KEY_:流程中task任务的定义id
     */
    private String taskDefKey;
    /**
     * 属于二次还是一次派发 state：0：一次派发；6：二次派发。
     */
    private Integer state;
    
	/**
	 * 
	 *second initiator
	 */
    private String twoInitiator;
    /**
     * 登录人在该工单所处职务
     */
    private String personStatus;
    /**
     * 传输局派发 0;外包派发 1;外包质检 2;传输局质检 3;质检 4;回复  5
     */
    private String stepState;
    /**阻碍地址*/
    private String installAddress;
    /**阻碍业务号码*/
    private String barrierNumber;
    /**需求发起人*/
    private String createPerson;
    /**方案制作人*/
    private String countryPerson;
    /**市传输分局*/
    private String cityCSJ;
    /**市公司*/
    private String citySGS;
    /**故障发生时间*/
    private Date createTime;
    /**工单历时*/
    private String workTask;
    /**联系人*/
    private String connectPerson;
    /**所属区域*/
    private String city;
    /**工单时限*/
    private String processLimit;
    /**是否显示颜色标志*/
    private boolean changeColor;
    
    /**区县*/
    private String country;
    /**线路属性*/
    private String workType;
    /**工单类型*/
    private String workorderTypeName;
    /**工单子类型*/
    private String subTypeName;
    /**关键字*/
    private String keyWord;
    /**紧急程度*/
    private String workOrderDegree;
    /**项目金额*/
    private double projectAmount;
    /**市线路人员*/
    private String cityLineExamine;
    /**市运维人员*/
    private String cityManageExamine;
    /**代维公司人员*/
    private String daiwei;
    /**后备人员*/
    private String tempTask;
    /**省线路人员*/
    private String countryLineExamine;
    /**工单编码*/
    private String sheetId;
    /**驳回工单标志：0 正常工单；1 驳回工单*/
    private String rollbackFlag;
    /**预检预修工单类别*/
    private String precheckFlag;
    /**赔补金额*/
    private double compensate;
    
    //区分接口与传输局  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；overhaul 大修工单；reform 改造工单；
    private String themeInterface;
    
    //themeInterface对应的中文值，现阶段主要针对大修和改造工单的区分
    private String themeInterfaceName;
    
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
    private String calculateIncomeRatio;
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
    //新建子场景字符串
    private String createStr;
    //拆除子场景字符串
    private String deleteStr;
    
    //待回复列表显示的工费
    private String totalFee;
    
    //待回复列表显示的材料费
    private String totalMaterialCost;
    
      //大修改造线路级别
    private String lineType;
    
    //省运维总经理审批时间，即派发接口的时间
    private Date distributedInterfaceTime;
    
    //工单处理-项目金额估算 
    private Double workerProjectAmount;
    
    //抽查-项目金额估算 
    private Double orderauditProjectAmount;
        //审批标识
    private String approveFlag;
    
    //工单子类型
    private String subType;
    
    //故障处理回复时间
    private Date lastReplyTime;
    
    //局站名称
    private String stationName;
    
    
    private String siteCd;
    //是否归集
    private String maleGuestState;  
    
    //是否归集中文值
    private String maleGuestStateName;
    
    //故障原因
    private String faultSource;
    
    //审批标识中文值
    private String approveFlagName;
    
  //单条工单处理还是没处理标识 N：未处理；Y：已处理
    private String handleFlag;
    
    //流程类型
    private String processType;
    
    //路径第一部分
    private String pathOne;
    
    //路径第二部分
    private String pathTwo;
    
    //最新主场景
    private String recentMainScenesName;
    
    //最新子场景
    private String recentCopyScenesName;
    
    //流程类型中文值
    private String processTypeName;
    
    private String tcountry;//工单区县-抢修及公客二次抽查
    private String tdate;//审批时间-抢修及公客二次抽查
    
    /**重要程度*/
    private String importance;
    /**巡检众筹流程*/
    private String reportedDate;//上报日期
    private String questionType;//问题类型
    private String resourceType;//资源类型
    private String specialty;//专业
    private String taskDefKeyName;//环节的名称
    
    private Double kindRestitution;//实物金额
    
    //抽检标识
    private String samplingState;
    
    //抽检评价
    private String samplingJudgments;
    
     public String getTcountry() {
		return tcountry;
	}

	public void setTcountry(String tcountry) {
		this.tcountry = tcountry;
	}

	public String getTdate() {
		return tdate;
	}

	public void setTdate(String tdate) {
		this.tdate = tdate;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getMaleGuestState() {
		return maleGuestState;
	}

	public void setMaleGuestState(String maleGuestState) {
		this.maleGuestState = maleGuestState;
	}

	public String getSiteCd() {
		return siteCd;
	}

	public void setSiteCd(String siteCd) {
		this.siteCd = siteCd;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
    
    public double getCompensate() {
		return compensate;
	}

	public void setCompensate(double compensate) {
		this.compensate = compensate;
	}

	public String getTwoInitiator() {
		return twoInitiator;
	}

	public void setTwoInitiator(String twoInitiator) {
		this.twoInitiator = twoInitiator;
	}

	public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getTimeDifference() {
		return TimeDifference;
	}

	public void setTimeDifference(String timeDifference) {
		TimeDifference = timeDifference;
	}
	
	public String getTaskDefKey() {
		return taskDefKey;
	}

	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}

	public String getOneInitiator() {
		return oneInitiator;
	}

	public void setOneInitiator(String oneInitiator) {
		this.oneInitiator = oneInitiator;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	public String getPersonStatus() {
		return personStatus;
	}

	public void setPersonStatus(String personStatus) {
		this.personStatus = personStatus;
	}
	
	
	   
	public String getStepState() {
		return stepState;
	}

	public void setStepState(String stepState) {
		this.stepState = stepState;
	}

	public TaskModel() {
	}

	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	public String getInstallAddress() {
		return installAddress;
	}

	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}

	public String getBarrierNumber() {
		return barrierNumber;
	}

	public void setBarrierNumber(String barrierNumber) {
		this.barrierNumber = barrierNumber;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public String getCountryPerson() {
		return countryPerson;
	}

	public void setCountryPerson(String countryPerson) {
		this.countryPerson = countryPerson;
	}

	public String getCityCSJ() {
		return cityCSJ;
	}

	public void setCityCSJ(String cityCSJ) {
		this.cityCSJ = cityCSJ;
	}

	public String getCitySGS() {
		return citySGS;
	}

	public void setCitySGS(String citySGS) {
		this.citySGS = citySGS;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getConnectPerson() {
		return connectPerson;
	}

	public void setConnectPerson(String connectPerson) {
		this.connectPerson = connectPerson;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProcessLimit() {
		return processLimit;
	}

	public void setProcessLimit(String processLimit) {
		this.processLimit = processLimit;
	}

	public String getWorkTask() {
		return workTask;
	}

	public void setWorkTask(String workTask) {
		this.workTask = workTask;
	}

	public boolean isChangeColor() {
		return changeColor;
	}

	public void setChangeColor(boolean changeColor) {
		this.changeColor = changeColor;
	}

	public String getSecondInitiator() {
		return secondInitiator;
	}

	public void setSecondInitiator(String secondInitiator) {
		this.secondInitiator = secondInitiator;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkorderTypeName() {
		return workorderTypeName;
	}

	public void setWorkorderTypeName(String workorderTypeName) {
		this.workorderTypeName = workorderTypeName;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getWorkOrderDegree() {
		return workOrderDegree;
	}

	public void setWorkOrderDegree(String workOrderDegree) {
		this.workOrderDegree = workOrderDegree;
	}

	public double getProjectAmount() {
		return projectAmount;
	}

	public void setProjectAmount(double projectAmount) {
		this.projectAmount = projectAmount;
	}

	public String getCityLineExamine() {
		return cityLineExamine;
	}

	public void setCityLineExamine(String cityLineExamine) {
		this.cityLineExamine = cityLineExamine;
	}

	public String getCityManageExamine() {
		return cityManageExamine;
	}

	public void setCityManageExamine(String cityManageExamine) {
		this.cityManageExamine = cityManageExamine;
	}

	public String getDaiwei() {
		return daiwei;
	}

	public void setDaiwei(String daiwei) {
		this.daiwei = daiwei;
	}

	public String getTempTask() {
		return tempTask;
	}

	public void setTempTask(String tempTask) {
		this.tempTask = tempTask;
	}

	public String getCountryLineExamine() {
		return countryLineExamine;
	}

	public void setCountryLineExamine(String countryLineExamine) {
		this.countryLineExamine = countryLineExamine;
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

	public String getThemeInterface() {
		return themeInterface;
	}

	public void setThemeInterface(String themeInterface) {
		this.themeInterface = themeInterface;
	}

	public String getThemeInterfaceName() {
		return themeInterfaceName;
	}

	public void setThemeInterfaceName(String themeInterfaceName) {
		this.themeInterfaceName = themeInterfaceName;
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

	public String getCalculateIncomeRatio() {
		return calculateIncomeRatio;
	}

	public void setCalculateIncomeRatio(String calculateIncomeRatio) {
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

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTotalMaterialCost() {
		return totalMaterialCost;
	}

	public void setTotalMaterialCost(String totalMaterialCost) {
		this.totalMaterialCost = totalMaterialCost;
	}

	public Date getDistributedInterfaceTime() {
		return distributedInterfaceTime;
	}

	public void setDistributedInterfaceTime(Date distributedInterfaceTime) {
		this.distributedInterfaceTime = distributedInterfaceTime;
	}

	public Double getWorkerProjectAmount() {
		return workerProjectAmount;
	}

	public void setWorkerProjectAmount(Double workerProjectAmount) {
		this.workerProjectAmount = workerProjectAmount;
	}

	public Double getOrderauditProjectAmount() {
		return orderauditProjectAmount;
	}

	public void setOrderauditProjectAmount(Double orderauditProjectAmount) {
		this.orderauditProjectAmount = orderauditProjectAmount;
	}

	public String getApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public Date getLastReplyTime() {
		return lastReplyTime;
	}

	public void setLastReplyTime(Date lastReplyTime) {
		this.lastReplyTime = lastReplyTime;
	}

	public String getFaultSource() {
		return faultSource;
	}

	public void setFaultSource(String faultSource) {
		this.faultSource = faultSource;
	}

	public String getMaleGuestStateName() {
		return maleGuestStateName;
	}

	public void setMaleGuestStateName(String maleGuestStateName) {
		this.maleGuestStateName = maleGuestStateName;
	}

	public String getApproveFlagName() {
		return approveFlagName;
	}

	public void setApproveFlagName(String approveFlagName) {
		this.approveFlagName = approveFlagName;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getPathOne() {
		return pathOne;
	}

	public void setPathOne(String pathOne) {
		this.pathOne = pathOne;
	}

	public String getPathTwo() {
		return pathTwo;
	}

	public void setPathTwo(String pathTwo) {
		this.pathTwo = pathTwo;
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

	public String getHandleFlag() {
		return handleFlag;
	}

	public void setHandleFlag(String handleFlag) {
		this.handleFlag = handleFlag;
	}

	public String getProcessTypeName() {
		return processTypeName;
	}

	public void setProcessTypeName(String processTypeName) {
		this.processTypeName = processTypeName;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getReportedDate() {
		return reportedDate;
	}

	public void setReportedDate(String reportedDate) {
		this.reportedDate = reportedDate;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getTaskDefKeyName() {
		return taskDefKeyName;
	}

	public void setTaskDefKeyName(String taskDefKeyName) {
		this.taskDefKeyName = taskDefKeyName;
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
}
