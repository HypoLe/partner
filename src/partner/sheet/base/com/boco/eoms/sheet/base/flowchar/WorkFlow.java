package com.boco.eoms.sheet.base.flowchar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.service.ITaskService;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefine;
import com.boco.eoms.sheet.base.util.flowdefine.xml.PhaseId;
import com.boco.eoms.sheet.base.util.flowdefine.xml.ToPhaseId;

public class WorkFlow {
	
	public WorkFlow() {
		super();
	}
	
	public Map createWorkFlow(Map parameterMap) throws Exception {
		//得到所需的参数
		String sheetKey = StaticMethod.nullObject2String(parameterMap.get("sheetKey"), "");
		String description = StaticMethod.nullObject2String(parameterMap.get("description"), "");
		String dictSheetName = StaticMethod.nullObject2String(parameterMap.get("dictSheetName"), "");
		String linkServiceName = StaticMethod.nullObject2String(parameterMap.get("linkServiceName"), "");
		String path = StaticMethod.nullObject2String(parameterMap.get("path"), "");
		FlowDefine flowDefine = (FlowDefine)parameterMap.get("flowDefine");
		Map linkObjectMap = (Map)parameterMap.get("linkObjectMap");
		ITaskService taskService = (ITaskService)parameterMap.get("taskService");
			
		// action
		String action = "." + path
				+ ".do?method=showWorkFlowInstance&linkServiceName="
				+ linkServiceName + "&dictSheetName=" + dictSheetName
				+ "&description=" + description;
		// 历史序列号
		List historyList = new ArrayList();
		// 当前序列号
		List currentList = new ArrayList();
		// 步骤列表
		Map stepMap = new HashMap();
		List stepList = new ArrayList();
		// 连接线列表
		List joinLineList = new ArrayList();
		// 阶段名称
		String phaseName = "";
		int lineNumber = 0;
		// 帮助Map
		Map helperMap = new HashMap();
		PhaseId phaseIds[] = flowDefine.getPhaseId();
		String step = "";
		
		StringBuffer taskName = new StringBuffer();
		// 产生步骤列表 见页面的javascript里的step方法
		for (int i = 0; i < phaseIds.length; i++) {
			PhaseId phaseId = phaseIds[i];
			if (phaseId.getType().equals("receive")) {
				phaseName = "新建工单";
				step = "{id:" + (i + 1) + ",name:\"" + phaseName + "\"";
			} else {
				phaseName = phaseId.getName();
				step = "{id:" + (i + 1) + ",name:\"" + phaseName + "\",url:\""
						+ action + "&mainId=" + sheetKey + "&taskName="
						+ phaseId.getId() + "\"";
			}
			stepMap.put(phaseId.getId(),step);
			
			helperMap.put(phaseId.getId(), new Integer(i + 1));
			taskName.append("'").append(phaseId.getId()).append("'");
			if (i != (phaseIds.length - 1)) {
				taskName.append(",");
			}
			
		}

		Map historyMap = new HashMap();
		Map currentMap = new HashMap();
		Map historyAndCureentMap = new HashMap();
		Map historyTaskMap = new HashMap();
		Map currentTaskMap = new HashMap();
		
		// 加入新建工单步骤
		historyList.add(new Integer((helperMap.get("receive") == null ? 1
				: ((Integer) helperMap.get("receive")).intValue())));
		
		
		//产生当前步骤和历史步骤
		//一派多的情况
		Map setpTasks = new HashMap();
		List tasks = taskService.getTasksBySheetKey(sheetKey,taskName.toString());
		for (Iterator it = tasks.iterator(); it.hasNext();) {
			ITask task = (ITask) it.next();
			if (historyMap.get(String.valueOf(task.getTaskName())) == null) {
				String taskNamePhaseId = task.getTaskName();
				Integer number = (Integer) helperMap.get(taskNamePhaseId);
				//不包含当前步骤的历史步骤
				if (!task.getTaskStatus().equals("5")) {
					currentMap.put(task.getTaskName(), number);
					currentTaskMap.put(task.getTaskName(), task);
					if (historyTaskMap.get(task.getTaskName()) != null) {
						historyTaskMap.remove(task.getTaskName());
					}
				} else {
					if (currentTaskMap.get(task.getTaskName()) == null) {
						historyMap.put(task.getTaskName(), number);
						historyTaskMap.put(task.getTaskName(), task);
					}
				}
				historyAndCureentMap.put(task.getTaskName(), number);
				historyList.add(number);
			} else {
				//判断该任务是否已完成
				if (!task.getTaskStatus().equals("5")) {
					historyTaskMap.remove(task.getTaskName());
					if (currentTaskMap.get(task.getTaskName()) == null) {
						currentTaskMap.put(task.getTaskName(), task);
					}
				} 
			}
			//如果有一派多的情况
			if (setpTasks.get(task.getTaskName()) == null) {
				List sameTasks = new ArrayList();
				sameTasks.add(task);
				setpTasks.put(task.getTaskName(), sameTasks);
			} else {
				List sameTasks = (List)setpTasks.get(task.getTaskName());
				sameTasks.add(task);
				setpTasks.put(task.getTaskName(), sameTasks);
			}
			if (!task.getTaskStatus().equals("5")) {
				currentList.add(historyAndCureentMap.get(task.getTaskName()));
			}
		}
		
		//在步骤列表里加入显示步骤内容
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		ITawSystemUserManager serviceUser = (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		String stepString = "";
		for (Iterator it = stepMap.keySet().iterator(); it.hasNext();) {
			String stepTaskName = (String)it.next();
			stepString  = (String)stepMap.get(stepTaskName);
			
			//在当前步骤里查找task对象，如果有的话就加入tips
			if (currentTaskMap.get(stepTaskName) != null) {
				List taskList = (List)setpTasks.get(stepTaskName);
				if (taskList.size() > 0) {
					stepString  += ",tips:\"";
				}
				for (int j = 0; j < taskList.size(); j ++ ) {
					ITask task = (ITask)taskList.get(j);
					//操作人角色或用户名的ID
					String owner = task.getTaskOwner();
					String userName = service.id2Name(owner,"tawSystemUserDao");
					String opereateRoleName=task.getOperateRoleId();
					String roleName = service.id2Name(opereateRoleName, "tawSystemSubRoleDao");
					String status = task.getTaskStatus();
					//等于2的情况
					if (status != null && status.equals(Constants.TASK_STATUS_READY)) {
						List users = serviceUser.getUsersBySubRoleid(owner);
						if (roleName != null && !roleName.equals("")) {
							for(int i=0;i<users.size();i++) {
								stepString += "预处理人：" + ((TawSystemUser)users.get(i)).getUsername(); 
								stepString += "<br/>";
								stepString += "联系方式：" + ((TawSystemUser)users.get(i)).getMobile();
								stepString += "<br/>";
							}
							stepString += "任务状态：未接单";
							stepString += "<br/>";
							stepString += "操作角色： " + roleName;
							stepString += "<br/><br/>";
						} 
						//其它的是等于8的情况		
				} else {
						//接单未处理
						String usercontact = serviceUser.getMobilesByUserId(owner);
						if (status != null && status.equals(Constants.TASK_STATUS_CLAIMED)) {
							if (userName != null && !userName.equals("")) {
								stepString += "操作人： " + userName;
								stepString += "<br/>";
								if (usercontact != null && !usercontact.equals("")) {
									//已操作人联系方式									
									stepString += "联系方式：" + usercontact;
									stepString += "<br/>"; 
								}
								stepString += "任务状态：已接单-未处理";
								stepString += "<br/>";
								if (roleName != null && !roleName.equals("")) {
									stepString += "操作角色： " + roleName;
									stepString += "<br/>";
								} 
								if (linkObjectMap.get(task.getTaskName() + "1") != null) {
									//操作时间
									BaseLink link = (BaseLink)linkObjectMap.get(task.getTaskName() + "1");
									stepString += "受理时间：" + StaticMethod.date2String(link.getOperateTime());
									stepString += "<br/><br/>";
								}
							}
						}
						//接单已处理
					   if (status != null && status.equals(Constants.TASK_STATUS_FINISHED)) {
							if (userName != null && !userName.equals("")) {
								stepString += "操作人：" + userName;
								stepString += "<br/>";
								if (usercontact != null && !usercontact.equals("")) {
									//已操作人联系方式
									stepString += "联系方式：" + usercontact;
									stepString += "<br/>";
								}
								stepString += "状态：已处理";
								stepString += "<br/>";		
								if (roleName != null && !roleName.equals("")) {
									stepString += "操作角色： " + roleName;
									stepString += "<br/>";
								} 
								//操作时间
								if (linkObjectMap.get(task.getTaskName() + "1") != null) {
									BaseLink link = (BaseLink)linkObjectMap.get(task.getTaskName() + "1");
									stepString += "处理时间：" + StaticMethod.date2String(link.getOperateTime());
									stepString += "<br/><br/>";
								}
							} 
					   }
					}
				}
				stepString += "\"}";	
			//历史列表	
			} else if (historyTaskMap.get(stepTaskName) != null) {
				
				List taskList = (List)setpTasks.get(stepTaskName);
				if (taskList.size() > 0) {
					stepString  += ",tips:\"";
				}
				for (int j = 0; j < taskList.size(); j ++ ) {
					ITask task = (ITask)taskList.get(j);
					//操作人角色或用户名的ID
					String owner = task.getTaskOwner();
					String userName = service.id2Name(owner,"tawSystemUserDao");
					String opereateRoleName=task.getOperateRoleId();
					String roleName = service.id2Name(opereateRoleName, "tawSystemSubRoleDao");
					String status = task.getTaskStatus();
					//等于2的情况
					if (status != null && status.equals(Constants.TASK_STATUS_READY)) {
						List users = serviceUser.getUsersBySubRoleid(owner);
						if (roleName != null && !roleName.equals("")) {
							for(int i=0;i<users.size();i++) {
								stepString += "预处理人：" + ((TawSystemUser)users.get(i)).getUsername(); 
								stepString += "<br/>";
								stepString += "联系方式：" + ((TawSystemUser)users.get(i)).getMobile();
								stepString += "<br/>";
							}
							stepString += "任务状态：未接单";
							stepString += "<br/>";
							stepString += "操作角色： " + roleName;
							stepString += "<br/><br/>";
						} 
						//其它的是等于8的情况		
				} else {
						//接单未处理
						String usercontact = serviceUser.getMobilesByUserId(owner);
						if (status != null && status.equals(Constants.TASK_STATUS_CLAIMED)) {
							if (userName != null && !userName.equals("")) {
								stepString += "操作人： " + userName;
								stepString += "<br/>";
								if (usercontact != null && !usercontact.equals("")) {
									//已操作人联系方式
									stepString += "<br/>"; 
									stepString += "联系方式：" + usercontact;
								}
								stepString += "任务状态：已接单-未处理";
								stepString += "<br/>";
								if (roleName != null && !roleName.equals("")) {
									stepString += "操作角色： " + roleName;
									stepString += "<br/>";
								} 							
								if (linkObjectMap.get(task.getTaskName() + "1") != null) {
									//操作时间
									BaseLink link = (BaseLink)linkObjectMap.get(task.getTaskName() + "1");
									stepString += "受理时间：" + StaticMethod.date2String(link.getOperateTime());
									stepString += "<br/><br/>";
								}
							}
						}
						//接单已处理
					   if (status != null && status.equals(Constants.TASK_STATUS_FINISHED)) {
							if (userName != null && !userName.equals("")) {
								stepString += "操作人：" + userName;
								stepString += "<br/>";
								if (usercontact != null && !usercontact.equals("")) {
									//已操作人联系方式
									stepString += "联系方式：" + usercontact;
									stepString += "<br/>";
								}
								stepString += "状态：已处理";
								stepString += "<br/>";		
								if (roleName != null && !roleName.equals("")) {
									stepString += "操作角色： " + roleName;
									stepString += "<br/>";
								} 
								//操作时间
								if (linkObjectMap.get(task.getTaskName() + "1") != null) {
									BaseLink link = (BaseLink)linkObjectMap.get(task.getTaskName() + "1");
									stepString += "处理时间：" + StaticMethod.date2String(link.getOperateTime());
									stepString += "<br/><br/>";
								}
							} 
					   }
					}
				}
				stepString += "\"}";
			} else {
				//表示该步骤即不是当前步骤也不是历史步骤
				stepString += "}";
			}
			stepList.add(stepString);
		}
				
				
				
				//当前task
//				ITask task = (ITask) currentTaskMap.get(stepTaskName);
//				//操作人角色或用户名的ID
//				String owner = task.getTaskOwner();
//				String userName = service.id2Name(owner,"tawSystemUserDao");
//				String opereateRoleName=task.getOperateRoleId();
//				String roleName = service.id2Name(opereateRoleName, "tawSystemSubRoleDao");
//				String status = task.getTaskStatus();
//				//等于2的情况
//				if (status != null && status.equals(Constants.TASK_STATUS_READY)) {
//					List users = serviceUser.getUsersBySubRoleid(owner);
//					if (roleName != null && !roleName.equals("")) {
//						stepString += ", tips:\"任务状态：未接单";
//						stepString += "<br/>";
//						stepString += "操作角色： " + roleName;
//						stepString += "<br/>";
//						for(int i=0;i<users.size();i++) {
//							stepString += "预处理人：" + ((TawSystemUser)users.get(i)).getUsername(); 
//							stepString += "<br/>";
//							stepString += "预处理人联系方式：" + ((TawSystemUser)users.get(i)).getMobile();
//							stepString += "<br/>";
//						}
//						stepString += "\"}";
//					} else {
//						stepString += "}";
//					}
//					
//				//其它的是等于8的情况	
//				} else {
//					String usercontact = serviceUser.getMobilesByUserId(owner);
//					if (userName != null && !userName.equals("")) {
//						stepString += ", tips:\"状态：已接单-未处理";
//						stepString += "<br/>";
//						stepString += "操作人： " + userName;
//						stepString += "<br/>";
//						if (roleName != null && !roleName.equals("")) {
//							stepString += "操作角色： " + roleName;
//							stepString += "<br/>";
//						} 
//						if (usercontact != null && !usercontact.equals("")) {
//							//已操作人联系方式
//							stepString += "<br/>"; 
//							stepString += "操作人联系方式：" + usercontact;
//						}
//						
//						if (linkObjectMap.get(task.getTaskName() + "1") != null) {
//							//操作时间
//							BaseLink link = (BaseLink)linkObjectMap.get(task.getTaskName() + "1");
//							stepString += "受理时间：" + StaticMethod.date2String(link.getOperateTime());
//							stepString += "<br/>";
//						}
//						stepString += "\"}";
//					} else {
//						stepString += "}";
//					}
//				}		
//			} else if (historyTaskMap.get(stepTaskName) != null) {
//				//在历史步骤里查找task对象，如果有的话就加入tips
//				ITask task = (ITask) historyTaskMap.get(stepTaskName);
//				//操作人角色或用户名的ID
//				String owner = task.getTaskOwner();
//				String opereateRoleName = task.getOperateRoleId();
//				String userName = service.id2Name(owner,"tawSystemUserDao");
//				if (userName != null && !userName.equals("")) {
//					stepString += ", tips:\"状态：已处理";
//					stepString += "<br/>";
//					stepString += "操作人：" + userName;
//					stepString += "<br/>";
//					String usercontact = serviceUser.getMobilesByUserId(owner);				
//					 String roleName = service.id2Name(opereateRoleName, "tawSystemSubRoleDao");
//
//					if (roleName != null && !roleName.equals("")) {
//						stepString += "操作角色： " + roleName;
//						stepString += "<br/>";
//					} 
//					if (usercontact != null && !usercontact.equals("")) {
//						//已操作人联系方式
//						stepString += "操作人联系方式：" + usercontact;
//						stepString += "<br/>";
//					}
//					//操作时间
//					if (linkObjectMap.get(task.getTaskName() + "1") != null) {
//						BaseLink link = (BaseLink)linkObjectMap.get(task.getTaskName() + "1");
//						stepString += "处理时间：" + StaticMethod.date2String(link.getOperateTime());
//						stepString += "<br/>";
//					}
//					stepString += "\"}";
//				} else {
//					stepString += "}";
//				}	
		//	} 
//		else {
//				//表示该步骤即不是当前步骤也不是历史步骤
//				stepString += "}";
//			}
//
//			stepList.add(stepString);
//		}

		// 历史列表里加入新建工单步骤
		historyMap.put("receive", new Integer(1));
		
		// 产生连接线
		Map allLine = new HashMap();
		for (int i = 0; i < phaseIds.length; i++) {
			PhaseId phaseId = phaseIds[i];
			String currentStepNumber = (helperMap.get(phaseId.getId()) == null ? ""
					: String.valueOf(((Integer) helperMap.get(phaseId.getId()))
							.intValue()));
			ToPhaseId toPhaseIds[] = phaseId.getToPhaseId();
			if (toPhaseIds == null)
				continue;
			for (int j = 0; j < toPhaseIds.length; j++) {
				ToPhaseId toPhaseId = toPhaseIds[j];
				String toStepNumber = (helperMap.get(toPhaseId.getId()) == null ? ""
						: String.valueOf(((Integer) helperMap.get(toPhaseId
								.getId())).intValue()));
				String stepId = "9" + (lineNumber++);
				String lineJoin = "{id:" + stepId + ",view:\""
						+ toPhaseId.getName() + "\",from:" + currentStepNumber
						+ ",to:" + toStepNumber;
				String allLineId = currentStepNumber + "-" + toStepNumber;
				allLine.put(allLineId, lineJoin);
			}
		}
		
		
		//产生历史线
		Map historyLine = new HashMap();
		if (tasks.size() > 0) {
			for (int i = 0; i < tasks.size(); i++) {
				Object startObj = tasks.get(i);
				if (startObj != null) {
					ITask startTask = (ITask) startObj;
					String startNumber = String.valueOf(historyAndCureentMap.get(startTask.getTaskName()));
					String endNumber = "";
					int nextObject = i + 1;
					if (nextObject < tasks.size()) {
						Object endObj = tasks.get(nextObject);
						if (endObj != null) {
							ITask endTask = (ITask) endObj;
							endNumber = String.valueOf(historyAndCureentMap.get(endTask.getTaskName()));
						}
						String index = startNumber + "-" + endNumber;
						historyLine.put(index, "");
					}
				}
			}
			//增加新建到第一步的历史线
			String startObj = "1";
			Object endObj = tasks.get(0);
			if (endObj != null) {
				ITask endTask = (ITask) endObj;
				String endNumber = String.valueOf(historyAndCureentMap.get(endTask.getTaskName()));
				String index = startObj + "-" + endNumber;
				historyLine.put(index, "");
			}
		}
		for (Iterator it = allLine.keySet().iterator(); it.hasNext();) {
			String index = (String)it.next();
			Object historyIndex = historyLine.get(index);
			if (historyIndex != null) {
				Object line = allLine.get(index);
				if (line != null) {
					String finalLine = (String)line + ",complete:true}";
					joinLineList.add(finalLine);
				}
					
			} else {
				Object line = allLine.get(index);
				String finalLine = (String)line + "}";
				joinLineList.add(finalLine);
			}
		}
		
		Map resultMap = new HashMap();
		resultMap.put("currentList", currentList);
		resultMap.put("historyList", historyList);
		resultMap.put("joinLineList", joinLineList);
		resultMap.put("stepList", stepList);
		
		// 释放内存
		stepMap.clear();
		helperMap.clear();
		historyMap.clear();
		currentMap.clear();
		historyLine.clear();
		allLine.clear();
		currentTaskMap.clear();
		historyTaskMap.clear();
		tasks.clear();
		linkObjectMap.clear();
		historyAndCureentMap.clear();
		return resultMap;
	}
}
