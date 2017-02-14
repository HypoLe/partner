package base.model;

/**
 * 基础信息Link抽象模型
 * 
 * @author Steve
 * 
 */
public abstract class Nop3GenericLink {
	/*
	 * 这里定义程序Link Model中使用的字段常量，对于某些常量，应该达成命名规范。
	 */
	private String id;// 主键，一般为UUID 32位

	private String status;// 这个状态记录了该流转信息发生时，记录所处的环节，与Nop3GenericMain中的status对应

	private String mainId;// 对应Main表中的Id

	// 记录处理者，之所以不和main表中的taskOwner字段保持一致，是为了加上语意上的区别：
	// 对于流转信息而言，只有处理者和派发受体这2个语意概念，和main表的任务所有者有着语意上的区别
	private String taskHandler;
	private String taskHandlerType;// 记录处理者类型
	private String taskReceiver;// 记录派发受体
	private String taskReceiverType;// 记录派发受体类型

	private String operateTime;// 记录操作时间，一般情况下，该字段可以仅用于显示
	private String operateTimeAtom;// 记录操作时间原始值，保存时间的long值，用于排序和格式化

	private String operateType;// 操作类型

	private String myText;// 流转信息字段，例如审批意见，驳回意见等。当operateType变化时，该字段会变化

	// 流转信息字段描述字段，也就是，如果有合适的标签，我们只需要在标签中引入myText字段，
	// 标签可以依据myTextType配置的类型来处理剩下的一切。而不用为不同的字段类型来设置不同的显示条件了
	private String myTextType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainId() {
		return mainId;
	}

	public void setMainId(String mainId) {
		this.mainId = mainId;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateTimeAtom() {
		return operateTimeAtom;
	}

	public void setOperateTimeAtom(String operateTimeAtom) {
		this.operateTimeAtom = operateTimeAtom;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getMyText() {
		return myText;
	}

	public void setMyText(String myText) {
		this.myText = myText;
	}

	public String getMyTextType() {
		return myTextType;
	}

	public void setMyTextType(String myTextType) {
		this.myTextType = myTextType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskHandler() {
		return taskHandler;
	}

	public void setTaskHandler(String taskHandler) {
		this.taskHandler = taskHandler;
	}

	public String getTaskHandlerType() {
		return taskHandlerType;
	}

	public void setTaskHandlerType(String taskHandlerType) {
		this.taskHandlerType = taskHandlerType;
	}

	public String getTaskReceiver() {
		return taskReceiver;
	}

	public void setTaskReceiver(String taskReceiver) {
		this.taskReceiver = taskReceiver;
	}

	public String getTaskReceiverType() {
		return taskReceiverType;
	}

	public void setTaskReceiverType(String taskReceiverType) {
		this.taskReceiverType = taskReceiverType;
	}
}
