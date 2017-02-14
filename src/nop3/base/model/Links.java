package base.model;

/**
 * 定义所有继承自Nop3GenericLink类的通用字段常量，多用于Search条件的拼接
 * 
 * @author Steve
 * @since October,2011
 * 
 */
public class Links {

	/**
	 * 主键，一般为UUID 32位
	 */
	public static final String id = "id";

	/**
	 * 这个状态记录了该流转信息发生时，记录所处的环节，与Nop3GenericMain中的status对应
	 */
	public static final String status = "status";
	/**
	 * 对应Main表中的Id
	 */
	public static final String mainId = "mainId";
	/**
	 * 记录处理者，之所以不和main表中的taskOwner字段保持一致，是为了加上语意上的区别：
	 * 对于流转信息而言，只有处理者和派发受体这2个语意概念，和main表的任务所有者有着语意上的区别
	 */
	public static final String taskHandler = "taskHandler";
	/**
	 * 记录处理者类型
	 */
	public static final String taskHandlerType = "taskHandlerType";
	/**
	 * 记录派发受体
	 */
	public static final String taskReceiver = "taskReceiver";
	/**
	 * 记录派发受体类型
	 */
	public static final String taskReceiverType = "taskReceiverType";
	/**
	 * 记录操作时间，一般情况下，该字段可以仅用于显示
	 */
	public static final String operateTime = "operateTime";
	/**
	 * 记录操作时间原始值，保存时间的long值，用于排序和格式化
	 */
	public static final String operateTimeAtom = "operateTimeAtom";
	/**
	 * 操作类型
	 */
	public static final String operateType = "operateType";
	/**
	 * 流转信息字段，例如审批意见，驳回意见等。当operateType变化时，该字段会变化
	 */
	public static final String myText = "myText";
	/**
	 * 流转信息字段描述字段，也就是，如果有合适的标签，我们只需要在标签中引入myText字段，
	 * 标签可以依据myTextType配置的类型来处理剩下的一切。而不用为不同的字段类型来设置不同的显示条件了
	 */
	public static final String myTextType = "myTextType";
}
