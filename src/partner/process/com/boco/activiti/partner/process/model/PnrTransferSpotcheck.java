package com.boco.activiti.partner.process.model;

import java.util.Date;

/**
 * 抽查记录，
 * 线路维护中心-预检预修流程
 * @author John
 *
 */
public class PnrTransferSpotcheck {

		private String id;
//		回复表id
		private String handleId;
//		taskId
		private String taskId;
//		工单号
		private String processInstanceId;
//		经度
		private String longitude;
//	     纬度
		private String latitude;
//		抽查人
		private String userId;
//		是否在500内:0--否，1--在
		private String state;
//		数据插入时间
		private Date insertDate;
		
		
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getHandleId() {
			return handleId;
		}
		public void setHandleId(String handleId) {
			this.handleId = handleId;
		}
		public String getProcessInstanceId() {
			return processInstanceId;
		}
		public void setProcessInstanceId(String processInstanceId) {
			this.processInstanceId = processInstanceId;
		}
		public String getLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public Date getInsertDate() {
			return insertDate;
		}
		public void setInsertDate(Date insertDate) {
			this.insertDate = insertDate;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
	
		
}
