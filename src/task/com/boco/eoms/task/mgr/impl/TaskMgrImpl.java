package com.boco.eoms.task.mgr.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.message.service.impl.MsgServiceImpl;
import com.boco.eoms.task.dao.ITaskDao;
import com.boco.eoms.task.mgr.ITaskAccessoriesMgr;
import com.boco.eoms.task.mgr.ITaskMgr;
import com.boco.eoms.task.model.Task;
import com.boco.eoms.task.util.TaskAppUtil;
import com.boco.eoms.task.util.TaskConstants;
import com.boco.eoms.task.util.TreeGridUtil;

public class TaskMgrImpl implements ITaskMgr {

	ITaskDao taskDao;

	public void setTaskDao(ITaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public String listTasksByDrafter(String drafter) {
		StringBuffer xml = new StringBuffer("<Grid><Body><B>");
		List topTaskList = taskDao.listTopTaskByDrafter(drafter);
		for (Iterator it = topTaskList.iterator(); it.hasNext();) {
			Task topTask = (Task) it.next();
			genDrafterTree(topTask, xml);
		}
		xml.append("</B></Body></Grid>");
		return xml.toString();
	}

	public String searchTasksByDrafter(String whereStr) {
		StringBuffer xml = new StringBuffer("<Grid><Body><B>");
		List list = taskDao.searchTasks(whereStr);
		for (Iterator it = list.iterator(); it.hasNext();) {
			Task task = (Task) it.next();
			task.setIsTopTask(true);
			genDrafterTreeSearch(task, xml, list);
		}
		xml.append("</B></Body></Grid>");
		return xml.toString();
	}

	/**
	 * 递归生成起草人Tree数据（非查询）
	 * 
	 * @param task
	 * @param xml
	 */
	public void genDrafterTree(Task task, StringBuffer xml) {
		xml.append("<I");
		if (StaticMethod.null2String(task.getId()).indexOf("$") > 0) { // 子任务编号
			String id = StaticMethod.null2String(task.getId())
					.substring(
							StaticMethod.null2String(task.getId()).lastIndexOf(
									"$") + 1);
			xml.append(" id='" + StaticMethod.null2String(id) + "'");
		} else {// 父任务编号
			xml.append(" id='" + StaticMethod.null2String(task.getId()) + "'");
		}
		xml.append(" name='" + StaticMethod.null2String(task.getName()) + "'"); // 任务名称
		xml.append(" content='" + StaticMethod.null2String(task.getContent())
				+ "'"); // 任务内容描述
		xml.append(" drafter='" + StaticMethod.null2String(task.getDrafter())
				+ "'"); // 任务负责人
		xml.append(" principal='"
				+ StaticMethod.null2String(task.getPrincipal()) + "'"); // 任务负责人
		xml.append(" taskId='" + StaticMethod.null2String(task.getTaskId())
				+ "'"); // 任务表主键
		xml.append(" remark='" + StaticMethod.null2String(task.getRemark())
				+ "'"); // 完成情况描述
		xml.append(" remind='" + StaticMethod.null2String(task.getRemind())
				+ "'"); // 是否短信提醒
		xml.append(" remindCanEdit='0'"); // 是否短信提醒新增勾选后不可修改
		xml
				.append(" accessories='&amp;lt;img src=&amp;quot;../treegrid/accessories.gif&amp;quot; onclick=onFile(&amp;quot;"
						+ task.getTaskId() + "&amp;quot;)>'"); // 附件

		if (TaskConstants.NOTLEAF.equals(StaticMethod.null2String(task
				.getLeaf()))) { // 非叶子节点任务标签内容有所不同
			xml.append(" Def='Task'");
			xml.append(" CanDrag='0'"); // 设置不可拖拽
			xml.append(">");
			List list = taskDao.listNextLevelTask(task.getTaskId());
			for (Iterator it = list.iterator(); it.hasNext();) {
				Task subTask = (Task) it.next();
				genDrafterTree(subTask, xml);
			}
			xml.append("</I>");
		} else {
			// xml.append(" principalButton='Defaults'"); // 下拉选项
			// xml.append("
			// principalEnum='||管理员&nbsp;&nbsp;&nbsp;(admin)|李秋野&nbsp;&nbsp;&nbsp;(liqiuye)'");
			// // 下拉选项
			xml.append(" progress='"
					+ StaticMethod.null2String(task.getProgress()) + "'"); // 进度百分比
			xml.append(" startTime='"
					+ StaticMethod.null2String(task.getStartTime()) + "'"); // 任务开始时间
			xml.append(" endTime='"
					+ StaticMethod.null2String(task.getEndTime()) + "'"); // 任务结束时间
			xml.append(" nextId='" + StaticMethod.null2String(task.getNextId())
					+ "'"); // 后续任务Id
			xml.append(" CanDrag='0'"); // 设置不可拖拽
			xml.append("/>");
		}
	}

	/**
	 * 递归生成起草人Tree数据（查询）
	 * 
	 * @param task
	 * @param xml
	 */
	public void genDrafterTreeSearch(Task task, StringBuffer xml, List result) {
		if (xml.indexOf(task.getTaskId()) > -1) { // 若Tree中已经包含此子任务，则返回
			return;
		}
		xml.append("<I");
		if (StaticMethod.null2String(task.getId()).indexOf("$") > 0) { // 子任务编号
			if (task.getIsTopTask()) { // 子树顶层任务不拆分Id
				xml.append(" id='" + StaticMethod.null2String(task.getId())
						+ "'");
			} else {
				String id = StaticMethod.null2String(task.getId())
						.substring(
								StaticMethod.null2String(task.getId())
										.lastIndexOf("$") + 1);
				xml.append(" id='" + id + "'");
			}
		} else {// 父任务编号
			xml.append(" id='" + StaticMethod.null2String(task.getId()) + "'");
		}
		xml.append(" name='" + StaticMethod.null2String(task.getName()) + "'"); // 任务名称
		xml.append(" content='" + StaticMethod.null2String(task.getContent())
				+ "'"); // 任务内容描述
		xml.append(" drafter='" + StaticMethod.null2String(task.getDrafter())
				+ "'"); // 任务负责人
		xml.append(" principal='"
				+ StaticMethod.null2String(task.getPrincipal()) + "'"); // 任务负责人
		xml.append(" taskId='" + StaticMethod.null2String(task.getTaskId())
				+ "'"); // 任务表主键
		xml.append(" remark='" + StaticMethod.null2String(task.getRemark())
				+ "'"); // 完成情况描述
		xml.append(" remind='" + StaticMethod.null2String(task.getRemind())
				+ "'"); // 是否短信提醒
		xml.append(" remindCanEdit='0'"); // 是否短信提醒新增勾选后不可修改
		xml
				.append(" accessories='&amp;lt;img src=&amp;quot;../treegrid/accessories.gif&amp;quot; onclick=onFile(&amp;quot;"
						+ task.getTaskId() + "&amp;quot;)>'"); // 附件
		if (result.contains(task)) {
			xml.append(" Selected='1'"); // 被选择状态
		}

		if (TaskConstants.NOTLEAF.equals(StaticMethod.null2String(task
				.getLeaf()))) { // 非叶子节点任务标签内容有所不同
			xml.append(" Def='Task'");
			xml.append(" CanDrag='0'"); // 设置不可拖拽
			xml.append(">");
			List list = taskDao.listNextLevelTask(StaticMethod.null2String(task
					.getTaskId()));
			for (Iterator it = list.iterator(); it.hasNext();) {
				Task subTask = (Task) it.next();
				genDrafterTreeSearch(subTask, xml, result);
			}
			xml.append("</I>");
		} else {
			// xml.append(" principalButton='Defaults'"); // 下拉选项
			// xml.append("
			// principalEnum='||管理员&nbsp;&nbsp;&nbsp;(admin)|李秋野&nbsp;&nbsp;&nbsp;(liqiuye)'");
			// // 下拉选项
			xml.append(" progress='"
					+ StaticMethod.null2String(task.getProgress()) + "'"); // 进度百分比
			xml.append(" startTime='"
					+ StaticMethod.null2String(task.getStartTime()) + "'"); // 任务开始时间
			xml.append(" endTime='"
					+ StaticMethod.null2String(task.getEndTime()) + "'"); // 任务结束时间
			xml.append(" nextId='" + StaticMethod.null2String(task.getNextId())
					+ "'"); // 后续任务Id
			xml.append(" CanDrag='0'"); // 设置不可拖拽
			xml.append("/>");
		}
	}

	public String listTasksByPrincipal(String principal) {
		StringBuffer xml = new StringBuffer("<Grid><Body><B>");
		List topTaskList = taskDao.listTopTaskByPrincipal(principal);
		for (Iterator it = topTaskList.iterator(); it.hasNext();) {
			Task topTask = (Task) it.next();
			// 设置为派发给我的子树顶层任务
			topTask.setIsTopTask(true);
			genPrincipalTree(topTask, xml, principal);
		}
		xml.append("</B></Body></Grid>");
		return xml.toString();
	}

	public String searchTasksByPrincipal(String principal, String whereStr) {
		StringBuffer xml = new StringBuffer("<Grid><Body><B>");
		List list = taskDao.searchTasks(whereStr);
		for (Iterator it = list.iterator(); it.hasNext();) {
			Task task = (Task) it.next();
			task.setIsTopTask(true);
			genPrincipalTreeSearch(task, xml, principal, list);
		}
		xml.append("</B></Body></Grid>");
		return xml.toString();
	}

	/**
	 * 递归生成接收人Tree数据
	 * 
	 * @param task
	 * @param xml
	 * @param currentUserId
	 */
	public void genPrincipalTree(Task task, StringBuffer xml,
			String currentUserId) {
		if (xml.indexOf(StaticMethod.null2String(task.getTaskId())) > -1) { // 若Tree中已经包含此子任务，则返回
			return;
		}
		xml.append("<I");
		if (StaticMethod.null2String(task.getId()).indexOf("$") > 0) { // 子任务编号
			if (task.getIsTopTask()) { // 子树顶层任务不拆分Id
				xml.append(" id='" + task.getId() + "'");
			} else {
				String id = StaticMethod.null2String(task.getId()).substring(
						task.getId().lastIndexOf("$") + 1);
				xml.append(" id='" + id + "'");
			}
		} else {// 父任务编号
			xml.append(" id='" + StaticMethod.null2String(task.getId()) + "'");
		}
		xml.append(" name='" + StaticMethod.null2String(task.getName()) + "'"); // 任务名称
		xml.append(" content='" + StaticMethod.null2String(task.getContent())
				+ "'"); // 任务内容描述
		xml.append(" drafter='"
				+ DictMgrLocator.getId2NameService().id2Name(
						StaticMethod.null2String(task.getDrafter()),
						"tawSystemUserDao") + "'"); // 任务派发人
		xml.append(" principal='"
				+ StaticMethod.null2String(task.getPrincipal()) + "'"); // 任务负责人
		xml.append(" taskId='" + StaticMethod.null2String(task.getTaskId())
				+ "'"); // 任务表主键
		xml.append(" remark='" + StaticMethod.null2String(task.getRemark())
				+ "'"); // 完成情况描述
		xml.append(" remind='" + StaticMethod.null2String(task.getRemind())
				+ "'"); // 是否短信提醒
		xml.append(" remindCanEdit='0'"); // 是否短信提醒新增勾选后不可修改
		xml
				.append(" accessories='&amp;lt;img src=&amp;quot;../treegrid/accessories.gif&amp;quot; onclick=onFile(&amp;quot;"
						+ task.getTaskId() + "&amp;quot;)>'"); // 附件

		// 权限判断，如果派发人不是我，则不具有修改name,content,principal的权限，且不能删除此行
		if (!currentUserId.equals(StaticMethod.null2String(task.getDrafter()))) {
			xml.append(" nameCanEdit='0'");
			xml.append(" contentCanEdit='0'");
			xml.append(" principalCanEdit='0'");
			xml.append(" CanDelete='0'");
			xml.append(" remarkCanEdit='1'");
		}

		if (TaskConstants.NOTLEAF.equals(StaticMethod.null2String(task
				.getLeaf()))) { // 非叶子节点任务标签内容有所不同
			xml.append(" Def='Task'");
			xml.append(" CanDrag='0'"); // 设置不可拖拽
			xml.append(">");
			List list = taskDao.listNextLevelTask(StaticMethod.null2String(task
					.getTaskId()));
			for (Iterator it = list.iterator(); it.hasNext();) {
				Task subTask = (Task) it.next();
				genPrincipalTree(subTask, xml, currentUserId);
			}
			xml.append("</I>");
		} else {
			// 权限判断，如果派发人不是我，则不具有修改startTime,endTime的权限
			if (!currentUserId.equals(StaticMethod.null2String(task
					.getDrafter()))) {
				xml.append(" startTimeCanEdit='0'");
				xml.append(" endTimeCanEdit='0'");
			}
			xml.append(" progress='"
					+ StaticMethod.null2String(task.getProgress()) + "'"); // 进度百分比
			xml.append(" startTime='"
					+ StaticMethod.null2String(task.getStartTime()) + "'"); // 任务开始时间
			xml.append(" endTime='"
					+ StaticMethod.null2String(task.getEndTime()) + "'"); // 任务结束时间
			// xml.append(" nextId='" + task.getNextId() + "'"); // 后续任务Id
			xml.append(" CanDrag='0'"); // 设置不可拖拽
			xml.append("/>");
		}
	}

	/**
	 * 递归生成接收人Tree数据（查询）
	 * 
	 * @param task
	 * @param xml
	 * @param currentUserId
	 */
	public void genPrincipalTreeSearch(Task task, StringBuffer xml,
			String currentUserId, List result) {
		if (xml.indexOf(StaticMethod.null2String(task.getTaskId())) > -1) { // 若Tree中已经包含此子任务，则返回
			return;
		}
		xml.append("<I");
		if (StaticMethod.null2String(task.getId()).indexOf("$") > 0) { // 子任务编号
			if (task.getIsTopTask()) { // 子树顶层任务不拆分Id
				xml.append(" id='" + StaticMethod.null2String(task.getId())
						+ "'");
			} else {
				String id = StaticMethod.null2String(task.getId()).substring(
						task.getId().lastIndexOf("$") + 1);
				xml.append(" id='" + id + "'");
			}
		} else {// 父任务编号
			xml.append(" id='" + StaticMethod.null2String(task.getId()) + "'");
		}
		xml.append(" name='" + StaticMethod.null2String(task.getName()) + "'"); // 任务名称
		xml.append(" content='" + StaticMethod.null2String(task.getContent())
				+ "'"); // 任务内容描述
		xml.append(" drafter='"
				+ DictMgrLocator.getId2NameService().id2Name(
						StaticMethod.null2String(task.getDrafter()),
						"tawSystemUserDao") + "'"); // 任务派发人
		xml.append(" principal='"
				+ StaticMethod.null2String(task.getPrincipal()) + "'"); // 任务负责人
		xml.append(" taskId='" + StaticMethod.null2String(task.getTaskId())
				+ "'"); // 任务表主键
		xml.append(" remark='" + StaticMethod.null2String(task.getRemark())
				+ "'"); // 完成情况描述
		xml.append(" remind='" + StaticMethod.null2String(task.getRemind())
				+ "'"); // 是否短信提醒
		xml.append(" remindCanEdit='0'"); // 是否短信提醒新增勾选后不可修改
		xml
				.append(" accessories='&amp;lt;img src=&amp;quot;../treegrid/accessories.gif&amp;quot; onclick=onFile(&amp;quot;"
						+ task.getTaskId() + "&amp;quot;)>'"); // 附件
		if (result.contains(task)) {
			xml.append(" Selected='1'"); // 被选择状态
		}

		// 权限判断，如果派发人不是我，则不具有修改name,content,principal的权限，且不能删除此行
		if (!currentUserId.equals(StaticMethod.null2String(task.getDrafter()))) {
			xml.append(" nameCanEdit='0'");
			xml.append(" contentCanEdit='0'");
			xml.append(" principalCanEdit='0'");
			xml.append(" CanDelete='0'");
			xml.append(" remarkCanEdit='1'");
		}

		if (TaskConstants.NOTLEAF.equals(StaticMethod.null2String(task
				.getLeaf()))) { // 非叶子节点任务标签内容有所不同
			xml.append(" Def='Task'");
			xml.append(" CanDrag='0'"); // 设置不可拖拽
			xml.append(">");
			List list = taskDao.listNextLevelTask(StaticMethod.null2String(task
					.getTaskId()));
			for (Iterator it = list.iterator(); it.hasNext();) {
				Task subTask = (Task) it.next();
				genPrincipalTreeSearch(subTask, xml, currentUserId, result);
			}
			xml.append("</I>");
		} else {
			// 权限判断，如果派发人不是我，则不具有修改startTime,endTime的权限
			if (!currentUserId.equals(StaticMethod.null2String(task
					.getDrafter()))) {
				xml.append(" startTimeCanEdit='0'");
				xml.append(" endTimeCanEdit='0'");
			}
			xml.append(" progress='"
					+ StaticMethod.null2String(task.getProgress()) + "'"); // 进度百分比
			xml.append(" startTime='"
					+ StaticMethod.null2String(task.getStartTime()) + "'"); // 任务开始时间
			xml.append(" endTime='"
					+ StaticMethod.null2String(task.getEndTime()) + "'"); // 任务结束时间
			// xml.append(" nextId='" + task.getNextId() + "'"); // 后续任务Id
			xml.append(" CanDrag='0'"); // 设置不可拖拽
			xml.append("/>");
		}
	}

	public void saveChangedTasks(String xml, String userId) throws Exception {

		// --- saves data to database ---
		Element[] Ch = TreeGridUtil.getChanges(xml);
		if (Ch == null) {
			return;
		}
		for (int i = 0; i < Ch.length; i++) {
			Element element = Ch[i];
			String taskId = TreeGridUtil.getTaskId(element);
			String parentId = TreeGridUtil.getParent(element);
			String id = TreeGridUtil.getId(element);
			if (TreeGridUtil.isDeleted(element)) { // 删除任务
				Task task = taskDao.getTask(taskId);
				if (null != task) {
					// 删除附件表中关联的附件
					String accessories = StaticMethod.null2String(
							task.getAccessories()).trim();
					if (!"".equals(accessories)) {
						ITaskAccessoriesMgr accessoriesMgr = (ITaskAccessoriesMgr) ApplicationContextHolder
								.getInstance().getBean("accessoriesMgr");
						String[] accessoriesId = accessories.split(",");
						for (int j = 0; j < accessoriesId.length; j++) {
							accessoriesMgr.delFile(accessoriesId[j], taskId);
						}
					}

					// Task parentTask =
					// taskDao.getTask(task.getParentTaskId()); // 取父任务
					taskDao.delTask(taskId); // 删除当前任务
					// if (null != parentTask) {
					// if (taskDao.listNextLevelTask(task.getParentTaskId())
					// .size() <= 0) { // 父任务下面已经没有节点
					// parentTask.setLeaf(TaskConstants.LEAF); // 设置父任务为leaf
					// taskDao.saveTask(parentTask);
					// }
					// }

				}

			} else if (TreeGridUtil.isAdded(element)) { // 新增任务
				Task task = new Task();
				NodeList nodeList = element.getChildNodes();
				if (null != nodeList && nodeList.getLength() > 0) {
					for (int j = 0; j < nodeList.getLength(); j++) {
						// 取传递到后台的属性和值，若在task类中找到对应属性，则反射调用set方法赋值
						Element el = (Element) nodeList.item(j); // I标签下面每个改动的U标签
						String name = el.getAttribute("N"); // 取属性名称
						if (isMethodInFieldArray(
								Task.class.getDeclaredFields(), name)) {
							String methodName = "set"
									+ name.substring(0, 1).toUpperCase()
									+ name.substring(1);
							Method method = task.getClass().getMethod(
									methodName,
									new Class[] { java.lang.String.class });
							method.invoke(task, new String[] { StaticMethod
									.null2String(el.getAttribute("V")) });
						}
					}
				}
				if (null == task.getNextId()) {
					task.setNextId("");
				}
				task.setId(id);
				task.setDeleted(TaskConstants.UNDELETED);
				task.setDrafter(userId);
				task.setDraftTime(StaticMethod.getCurrentDateTime());
				task.setLeaf(TaskConstants.LEAF);
				task.setParentTaskId(parentId);
				taskDao.saveTask(task);

				// 是否需要短信提醒
				if (null != task.getRemind() && !"".equals(task.getRemind())
						&& null != task.getPrincipal()
						&& !"".equals(task.getPrincipal())
						&& "1".equals(task.getRemind())) {
					MsgServiceImpl msgService = new MsgServiceImpl();
					msgService
							.sendMsgImediate("1," + task.getPrincipal(),
									DictMgrLocator.getId2NameService().id2Name(
											StaticMethod.null2String(task
													.getDrafter()),
											"tawSystemUserDao")
											+ "于"
											+ StaticMethod.getCurrentDateTime()
											+ "给您派发了一项任务，任务名称为【"
											+ task.getName() + "】");
				}

				if (!"-1".equals(taskId)) { // 设置父节点的leaf值
					Task parentTask = taskDao.getTask(parentId);
					if (null != parentTask) {
						parentTask.setLeaf(TaskConstants.NOTLEAF);
						taskDao.saveTask(parentTask);
					}
				}
			} else if (TreeGridUtil.isChanged(element)) { // 修改任务
				Task task = taskDao.getTask(taskId);
				if (null != task) {
					NodeList nodeList = element.getChildNodes();
					if (null != nodeList && nodeList.getLength() > 0) {
						for (int j = 0; j < nodeList.getLength(); j++) {
							// 取传递到后台的属性和值，若在task类中找到对应属性，则反射调用set方法赋值
							Element el = (Element) nodeList.item(j); // I标签下面每个改动的U标签
							String name = el.getAttribute("N"); // 取属性名称
							if (null != Task.class.getDeclaredField(name)) {
								String methodName = "set"
										+ name.substring(0, 1).toUpperCase()
										+ name.substring(1);
								Method method = task.getClass().getMethod(
										methodName,
										new Class[] { java.lang.String.class });
								method.invoke(task, new String[] { StaticMethod
										.null2String(el.getAttribute("V")) });
							}
						}
						taskDao.saveTask(task);
					}
				}

			}
		}
	}

	public boolean isMethodInFieldArray(Field[] fields, String methodName) {
		boolean result = false;
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.getName().equals(methodName)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public Task getTask(String taskId) {
		// TODO Auto-generated method stub
		return taskDao.getTask(taskId);
	}

	public Map getTasks(Integer curPage, Integer pageSize, String whereStr) {
		// TODO Auto-generated method stub
		return taskDao.getTasks(curPage, pageSize, whereStr);
	}

	public List getChildTask(String id) {
		// TODO Auto-generated method stub
		return taskDao.getChildTask(id);
	}

	public String listTaskConflict(String principal, String startTime,
			String endTime, String taskId) {
		// TODO Auto-generated method stub
		String str = "";
		List list = taskDao.listTaskConflict(principal, startTime, endTime,
				taskId);
		if (list.size() > 0) {
			str += "<table>";
			str += "<tr><td><B>任务名称</B></td><td><B>开始时间</B></td><td><B>结束时间</B></td></tr>";
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Task task = (Task) it.next();
				// names += task.getName() ;
				str += "<tr>";

				str += "<td><font color='red'>";
				str += task.getName();
				str += "</font></td>";
				str += "<td><font color='red'>";
				str += TaskAppUtil.changeTime(task.getStartTime());
				str += "</font></td>";
				str += "<td><font color='red'>";
				str += TaskAppUtil.changeTime(task.getEndTime());
				str += "</font></td>";

				str += "</tr>";
			}
			str += "</table>";
		}
		return str;
	}

	public void saveTask(Task task) {
		taskDao.saveTask(task);
	}

}
