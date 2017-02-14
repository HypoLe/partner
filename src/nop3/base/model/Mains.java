package base.model;

/**
 * 定义所有继承自Nop3GenericMain类的通用字段常量，多用于Search条件的拼接
 * 
 * @author Steve
 * @since October,2011
 * 
 */
public class Mains {
	/**
	 * 主键，一般为UUID 32位
	 */
	public static final String id = "";
	/**
	 * 流程状态，该状态可以理解为：“横切面”状态
	 */
	public static final String status = "";
	/**
	 * 该状态可以理解为：“纵切面”状态
	 */
	public static final String verticalStatus = "";
	/**
	 * 任务所有者类型，角色，部门，人等任意类型
	 */
	public static final String taskOwnerType = "";
	/**
	 * 任务所有者
	 */
	public static final String taskOwner = "";
	/**
	 * 记录创建者Id
	 */
	public static final String createUserId = "";
	/**
	 * 记录创建时间
	 */
	public static final String createDate = "";
	/**
	 * 年标记
	 */
	public static final String yearFlag = "";
	/**
	 * 月标记
	 */
	public static final String monthFlag = "";
	/**
	 * 天标记
	 */
	public static final String dayFlag = "";
}
