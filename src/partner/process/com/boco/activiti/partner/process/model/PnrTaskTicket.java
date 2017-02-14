package com.boco.activiti.partner.process.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务工单 
 *
 */
public class PnrTaskTicket  implements Serializable{
	
	private String id;
	//工单主题
	private String theme;
	//工单子类型
	private String subType;
	//站点
	private String site;
	//站点ID
	private String mainResId;
	//工单内容
	private String content;
	//工单生成时间
	private Date createTime;
	//计划开始时间
	private Date startTime;
	//计划完成时间
	private Date endTime;
	/*//故障站点
	private String failedSite;
	//故障站点ID
	private String faultResId;*/
	//涉及专业
	private String specialty;
	//发起人
	private String initiator;
	//工单流程ID
	private String processInstanceId;
    /**
     * 接收人
     */
	private String candidateUser;
    /**
     * 	接收组
     */
    private String candidateGroup;
    /**
     * 归档时间
     */
    private Date archivingTime;
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
    //工单状态值：1、标记新建；2、标记越期；3、标记审核不通过;4、标记删除;5 归档。
    private Integer state; 
    //工单附件的英文名称字符串
    private String sheetAccessories;
    //附件个数
    private Integer attachmentsNum;
    
//手机端时间显示--start
//    工单生成时间
    private String androidCreateTime ;
//    计划开始时间
    private String androidStartTime;
//    计划结束时间
    private String androidEndTime;
//手机端时间显示--end
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

    public Date getArchivingTime() {
        return archivingTime;
    }

    public void setArchivingTime(Date archivingTime) {
        this.archivingTime = archivingTime;
    }
    public String getCandidateUser() {
        return candidateUser;
    }

    public void setCandidateUser(String candidateUser) {
        this.candidateUser = candidateUser;
    }

    public String getCandidateGroup() {
        return candidateGroup;
    }

    public void setCandidateGroup(String candidateGroup) {
        this.candidateGroup = candidateGroup;
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



	public String getSubType() {
		return subType;
	}



	public void setSubType(String subType) {
		this.subType = subType;
	}



	public String getSite() {
		return site;
	}



	public void setSite(String site) {
		this.site = site;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getStartTime() {
		return startTime;
	}



	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}



	public Date getEndTime() {
		return endTime;
	}



	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getSpecialty() {
		return specialty;
	}



	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}



	public String getInitiator() {
		return initiator;
	}



	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}



	public String getProcessInstanceId() {
		return processInstanceId;
	}



	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getMainResId() {
		return mainResId;
	}

	public void setMainResId(String mainResId) {
		this.mainResId = mainResId;
	}


	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAndroidCreateTime() {
		return androidCreateTime;
	}

	public void setAndroidCreateTime(String androidCreateTime) {
		this.androidCreateTime = androidCreateTime;
	}

	public String getAndroidStartTime() {
		return androidStartTime;
	}

	public void setAndroidStartTime(String androidStartTime) {
		this.androidStartTime = androidStartTime;
	}

	public String getAndroidEndTime() {
		return androidEndTime;
	}

	public void setAndroidEndTime(String androidEndTime) {
		this.androidEndTime = androidEndTime;
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

	public PnrTaskTicket() {
		
	}
	
	
	
}
