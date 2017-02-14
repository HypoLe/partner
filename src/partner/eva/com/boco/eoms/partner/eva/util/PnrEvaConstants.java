package com.boco.eoms.partner.eva.util;

/**
 * <p>
 * Title:考核常量类
 * </p>
 * <p>
 * Description:考核常量类
 * </p>
 * <p>
 * Date:2008-11-20 上午11:02:42
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public class PnrEvaConstants {

	/**
	 * 日期格式
	 */
	public final static String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 根结点
	 */
	public final static String TREE_ROOT_ID = "1";

	/**
	 * 节点ID按照一定规律子ID跟父ID的length之差为3
	 */
	public static final int NODEID_BETWEEN_LENGTH = 3;

	/**
	 * 如果节点没有数据的情况下设置一个默认值
	 */
	public static final String NODEID_DEFULT_VALUE = "1";

	/**
	 * 如果某个节点ID下没有子节点时需要追加
	 */
	public static final String NODEID_NOSON = "001";

	/**
	 * 判断节点ID是否为设置的最大值
	 */
	public static final String NODEID_IF_MAXID = "999";

	/**
	 * 无名称节点
	 */
	public static final String NODE_NONAME = "未知名称";

	/**
	 * 删除标志
	 */
	public final static String DELETED = "1";

	/**
	 * 未删除标志
	 */
	public final static String UNDELETED = "0";

	/**
	 * 叶节点
	 */
	public final static String NODE_LEAF = "1";

	/**
	 * 非叶节点
	 */
	public final static String NODE_NOTLEAF = "0";

	/**
	 * 节点类型-指标分类
	 */
	public final static String NODETYPE_KPITYPE = "KPITYPE";

	/**
	 * 节点类型-指标
	 */
	public final static String NODETYPE_KPI = "KPI";

	/**
	 * 节点类型-模板类型
	 */
	public final static String NODETYPE_TEMPLATETYPE = "TEMPLATETYPE";

	/**
	 * 节点类型-模板
	 */
	public final static String NODETYPE_TEMPLATE = "TEMPLATE";

	/**
	 * 指标分类
	 */
	public final static String QTIP_KPITYPE = "指标分类";

	/**
	 * 指标
	 */
	public final static String QTIP_KPI = "指标";

	/**
	 * 模板分类
	 */
	public final static String QTIP_TEMPLATETYPE = "模板分类";

	/**
	 * 模板
	 */
	public final static String QTIP_TEMPLATE = "模板";
	
	/**
	 * 默认满分
	 */
	public static final float DEFAULT_FULL_SCORE = 100;
	
	/**
	 * 默认最小权重
	 */
	public static final float DEFAULT_MIN_WT = 0;
	
	/**
	 * 默认最大权重
	 */
	public static final float DEFAULT_MAX_WT = 100;

	/**
	 * 考核周期-年
	 */
	public final static String CYCLE_YEAR = "year";

	/**
	 * 考核周期-季度
	 */
	public final static String CYCLE_QUARTER = "quarter";

	/**
	 * 考核周期-月
	 */
	public final static String CYCLE_MONTH = "month";

	/**
	 * 考核周期-周
	 */
	public final static String CYCLE_WEEK = "week";
	
	/**
	 * 模板已激活
	 */
	public final static String TEMPLATE_ACTIVATED = "1";
	
	/**
	 * 模板未激活
	 */
	public final static String TEMPLATE_NOTACTIVATED = "0";

	/**
	 * 模板状态-草稿
	 */
	public final static String TEMPLATE_DRAFT = "template_draft";

	/**
	 * 模板状态-已下发
	 */
	public final static String TEMPLATE_DISTRIBUTED = "template_distributed";

	/**
	 * 模板状态-待审核
	 */
	public final static String TEMPLATE_AUDIT_WAIT = "template_audit_wait";

	/**
	 * 模板状态-上报（审核通过）
	 */
	public final static String TEMPLATE_REPORTED = "template_reported";

	/**
	 * 模板状态-驳回
	 */
	public final static String TEMPLATE_AUDIT_REJECT = "template_audit_reject";

	/**
	 * 角色类型
	 */
	public static final String ORG_SUBROLE = "subRole";

	/**
	 * 部门类型
	 */
	public static final String ORG_DEPT = "dept";

	/**
	 * 用户类型
	 */
	public static final String ORG_USER = "user";
	

	/**
	 * 地域类型
	 */
	public static final String ORG_AREA = "area";

	/**
	 * The request scope attribute that holds the thread list
	 */
	public static final String AUDITINFO_LIST = "auditInfoList";

	/**
	 * 任务执行状态(活动)
	 */
	public static final String TASK_STSTUS_ACTIVITIES = "activities";

	/**
	 * 任务执行状态(已执行)
	 */
	public static final String TASK_STSTUS_INACTIVE = "inactive";
	
	/**
	 * 是否发布（草稿，未发布）
	 */
	public final static String TASK_PUBLISHED = "1";
	
	/**
	 * 是否发布（已发布）
	 */
	public final static String TASK_NOT_PUBLISHED = "0";
	
	/**
	 * 未锁定
	 */
	public final static String UNLOCK = "0";
	
	/**
	 * 锁定
	 */
	public final static String LOCKED = "1";
	
	/**
	 * 所有子节点
	 */
	public final static String ALL_CHILD_NODE = "all";
	
	/**
	 * 一级子节点
	 */
	public final static String NEXT_CHILD_NODE = "next";
	
	/**
	 * 考核模型管理
	 */
	public final static String OPERATE_TREE_CONFIG = "treeConfig";
	
	/**
	 * 考核模板审核
	 */
	public final static String OPERATE_TEMPLATE_AUDIT = "templateAudit";
	
	/**
	 * 考核记录审核
	 */
	public final static String OPERATE_REPORT_AUDIT = "reportAudit";
	
	/**
	 * 考核表执行
	 */
	public final static String OPERATE_REPORT_EXECUTE = "reportExecute";
	
	/**
	 * 考核表查看
	 */
	public final static String OPERATE_REPORT_SHOW = "reportShow";
	
	/**
	 * 考核层面省公司
	 */
	public final static String EXECUTE_TYPE_PROVINCE = "0";
	
	/**
	 * 考核层面市公司
	 */
	public final static String EXECUTE_TYPE_CITY = "1";
	
	/**
	 * 考核层面县公司
	 */
	public final static String EXECUTE_TYPE_TOWN = "2";
	
	
	/**
	 * 模板未送审
	 */
	public final static String AUDIT_UNDO = "0";
	
	/**
	 * 模板待审核
	 */
	public final static String AUDIT_WAIT = "1";
	
	/**
	 * 模板审核通过
	 */
	public final static String AUDIT_PASS = "2";
	
	/**
	 * 模板审核驳回
	 */
	public final static String AUDIT_DENY = "3";
	
	/**
	 * 模板审核类型为新增
	 */
	public final static String AUDIT_NEW = "0";
	
	/**
	 * 模板审核类型为修改
	 */
	public final static String AUDIT_EDIT = "1";
	
	/**
	 * 审核任务列表
	 */
	public final static String TASK_AUDIT_LIST = "taskAuditList";

	
	/**
	 * 汇总算法-直接得分
	 */
	public final static String SUMTYPE_INPUT = "0";
	
	/**
	 * 汇总算法-短板得分
	 */
	public final static String SUMTYPE_MIN = "1";
	
	/**
	 * 汇总算法-维护比例
	 */
	public final static String SUMTYPE_RATIO = "2";
	
	/**
	 * 反排序
	 */
	public final static String ORDER_DESC = "DESC";
	
	/**
	 * 正排序
	 */
	public final static String ORDER_ASC = "ASC";
	
	/**
	 * 市考核层面的执行人--各地市
	 */
	public final static String EXECUTE_ORG_EC = "0";
	
	/**
	 * 市考核层面的执行人--省网管中心
	 */
	public final static String EXECUTE_ORG_NMC = "1";
	
	/**
	 * 市考核层面的执行人--省网络部
	 */
	public final static String EXECUTE_ORG_ND = "2";
}
