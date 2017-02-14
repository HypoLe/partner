package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;


import java.io.Serializable;
import java.util.Date;

/**
 * 故障工单 2013-09-10 17:01 pm
 * @author chenbing
 *
 *
 */
public class PnrTroubleTicket  implements Serializable{

    private String id;
    //工单主题
    private String theme;
    //故障发生时间
    private Date createTime;
    //派单时间
    private Date sendTime;
    //派单时间(二次)
    private Date secondSendTime;
    //派单人
    private String initiator;
    //工单接收人
    private String taskAssignee;
    //故障处理人
//    private String doPerson;
    //故障联系人
    private String connectPerson;
    //故障处理时限
    private Double processLimit;
    //故障站点
    private String failedSite;
    //故障站点ID
    private String mainResId;
    //故障来源
    private String faultSource;
    //故障描述
    private String faultDescription;
    //是否影响到集团客户 1:是 ；0：不是
    private Integer isCustomers;
    //工单子类型
    private String subType;
    //涉及专业
    private String specialty;
    //工单流程ID
    private String processInstanceId;
    //状态 0：正常；1：删除;5:归档;6是走二次调度
    private Integer state;

    /**
     * 归档时间
     */
    private Date archivingTime;
    //接收人字符串
    private String taskAssigneeJSON;
    //处理人字符串
//    private String doPersonJSON;
   
   
    /**
     * 最后回复时间
     */
    private Date lastReplyTime;
    /**
     *   所属地市
     */
    private String city;

    /**
     * 所属区县
     */
    private String country;
    //计划完成时间
    private Date endTime;
    //首次派发人（隐患工单）
    private String firstInitiator;
    //首次派发时间（隐患工单）
    private Date firstCreateTime;
    //（二次派发时）第一派发人
    private String oneInitiator;
    //公客系统的工单号
    private String gkSerialNo;
    //公客系统传递的城市名称
    private String gkCityName;
    //公客系统传递的城市名称-id
    private String gkCityId;
    //公客系统传递的区县名称-id
    private String gkCountryId;
    //工单附件的英文名称字符串
    private String sheetAccessories;
    //附件个数
    private Integer attachmentsNum;
    
  //手机端时间显示--start
//  派单时间
  private String androidSendTime ;
//  计划完成时间
  private String androidEndTime;
//  故障发生时间
  private String androidCreateTime;
//手机端时间显示--end
  
    public Integer getCustomers() {
        return isCustomers;
    }

    public void setCustomers(Integer customers) {
        isCustomers = customers;
    }

    public Date getLastReplyTime() {
    	return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
    public String getFailedSite() {
        return failedSite;
    }
    public void setFailedSite(String failedSite) {
        this.failedSite = failedSite;
    }
    
    public String getMainResId() {
		return mainResId;
	}
	public void setMainResId(String mainResId) {
		this.mainResId = mainResId;
	}
	public String getFaultDescription() {
        return faultDescription;
    }
    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }
    public Integer getIsCustomers() {
        return isCustomers;
    }
    public void setIsCustomers(Integer isCustomers) {
        this.isCustomers = isCustomers;
    }
    public String getSubType() {
        return subType;
    }
    public void setSubType(String subType) {
        this.subType = subType;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
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

	public String getFirstInitiator() {
		return firstInitiator;
	}

	public void setFirstInitiator(String firstInitiator) {
		this.firstInitiator = firstInitiator;
	}

	public Date getFirstCreateTime() {
		return firstCreateTime;
	}

	public void setFirstCreateTime(Date firstCreateTime) {
		this.firstCreateTime = firstCreateTime;
	}
	
	public String getOneInitiator() {
		return oneInitiator;
	}

	public void setOneInitiator(String oneInitiator) {
		this.oneInitiator = oneInitiator;
	}

	public Date getSecondSendTime() {
		return secondSendTime;
	}

	public void setSecondSendTime(Date secondSendTime) {
		this.secondSendTime = secondSendTime;
	}
	
	public String getGkSerialNo() {
		return gkSerialNo;
	}

	public void setGkSerialNo(String gkSerialNo) {
		this.gkSerialNo = gkSerialNo;
	}

	public String getGkCityName() {
		return gkCityName;
	}

	public void setGkCityName(String gkCityName) {
		this.gkCityName = gkCityName;
	}

	public String getGkCityId() {
		return gkCityId;
	}

	public void setGkCityId(String gkCityId) {
		this.gkCityId = gkCityId;
	}

	public PnrTroubleTicket() {

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

	public String getGkCountryId() {
		return gkCountryId;
	}

	public void setGkCountryId(String gkCountryId) {
		this.gkCountryId = gkCountryId;
	}



}
