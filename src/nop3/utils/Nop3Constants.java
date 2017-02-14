package utils;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Nop3Constants {

	/**
	 * 后台分页后每页显示记录条数，修改此属性有模块级影响，请注意
	 */
	public static final int PAGE_SIZE = 15;

	/*
	 * 用于制定工作计划和考核指标的周期常量
	 * 
	 * 时间常量为：天，一周，一月，一季度，半年，年
	 * 
	 * 开始
	 */
	public static final String PERIOD_OF_DAY = "day";

	public static final String PERIOD_OF_WEEK = "week";

	public static final String PERIOD_OF_MONTH = "month";

	public static final String PERIOD_OF_QUARTER = "quarter";

	public static final String PERIOD_OF_HALF_YEAR = "halfOfYear";

	public static final String PERIOD_OF_YEAR = "year";

	public static final Set<String> getPeriodSet() {
		Set<String> myPeriodSet = Sets.newHashSet();
		myPeriodSet.add(PERIOD_OF_WEEK);
		myPeriodSet.add(PERIOD_OF_MONTH);
		myPeriodSet.add(PERIOD_OF_QUARTER);
		myPeriodSet.add(PERIOD_OF_HALF_YEAR);
		myPeriodSet.add(PERIOD_OF_YEAR);
		myPeriodSet.add(PERIOD_OF_DAY);
		return myPeriodSet;
	}

	/*
	 * 用于制定工作计划和考核指标的周期常量
	 * 
	 * 结束
	 */

	public static String submitToListType2Enable = "0";

	public static String submitToListType2Disable = "1";

	/*
	 * 用于表示横切面状态常量，开始
	 */

	// 草稿状态
	public static final String STATUS_DRAFT = "0";

	// 待审核状态
	public static final String STATUS_AUDIT = "1";
	
	// 驳回
	public static final String STATUS_REJECT = "2";

	// 提交成功
	public static final String STATUS_SUCCESS = "3";
	
	// 等待确认
	public static final String STATUS_VERIFY = "4";

	/*
	 * 用于表示横切面状态常量，结束
	 */

	/*
	 * 用于表示纵切面状态常量，开始
	 */
	/**
	 * 默认状态，可以提交给下个环节
	 */
	public static final String VERTICAL_STATUS_DEFAULT = "0";
	/*
	 * 用于表示纵切面状态常量，结束
	 */

	public static final String TASK_OWNER_TYPE_USER = "user";

	/**
	 * 代维类型:线路代维，用于服务协议新增和合同新增
	 */
	public static final String PROXY_TYPE_CIRCUIT = "circuitProxy";

	/**
	 * 代维类型:基站代维，用于服务协议新增和合同新增
	 */
	public static final String PROXY_TYPE_BASESTATION = "baseStationProxy";

	// 记录显示的方法名，开始
	public static final String METHOD_LIST = "list";

	public static final String METHOD_HISTORY = "history";

	public static final String METHOD_ADD = "add";

	public static final String METHOD_DELETE = "delete";

	public static final String METHOD_IMPORTEXCEL = "importExcel";

	public static final String METHOD_GO_TO_ADD = "goToAdd";

	public static final String METHOD_GO_TO_IMPORTEXCEL = "goToImportExcel";

	// 记录显示的方法名，结束

	public static final String OPERATETYPE_LINK = "general";

	// 保存草稿
	public static final String OPERATETYPE_LINK_SAVE_DRAFT = "0";

	// 提交审核
	public static final String OPERATETYPE_LINK_SEND_AUDIT = "1";

	// 驳回
	public static final String OPERATETYPE_LINK_REJECT_BACK = "2";

	// 提交成功
	public static final String OPERATETYPE_LINK_SUBMIT_SUCCESS = "3";
	
	// 等待确认
	public static final String OPERATETYPE_LINK_WAIT_VERIFY = "4";

	public static final Map<String, String> getOperateType() {
		Map<String, String> operateMap = Maps.newHashMap();
		operateMap.put(OPERATETYPE_LINK_SAVE_DRAFT, "保存草稿");
		operateMap.put(OPERATETYPE_LINK_SEND_AUDIT, "提交审核");
		operateMap.put(OPERATETYPE_LINK_REJECT_BACK, "驳回");
		operateMap.put(OPERATETYPE_LINK_SUBMIT_SUCCESS, "提交成功");
		return operateMap;
	}

	// 用于标识用户身份：省公司，市县公司，代维公司，省公司领导等等，开始
	// WARNING: 这里常量的驼峰风格不能更改，否则会广泛的影响权限判断代码，Comment By Steve, 2011-11
	public static final String PARTY_A = "partyA"; // 甲方，泛指中国移动云南公司

	public static final String PARTY_A_PROVINCE_LEADER = "partyAProvinceLeader"; // 甲方，指中国移动云南公司省公司领导

	public static final String PARTY_A_PROVINCE = "partyAProvince"; // 甲方，指中国移动云南公司省公司

	public static final String PARTY_A_CITY = "partyACity"; // 甲方，指中国移动云南公司市公司

	public static final String PARTY_A_COUNTRY = "partyACountry"; // 甲方，指中国移动云南公司县公司

	public static final String PARTNER = "partyB"; // 乙方，合作伙伴，多指代代维公司

	// 用于标识用户身份：省公司，市县公司，代维公司，省公司领导等等，结束

	// 地图树视图常量，开始
	public static final String PERSPECTIVE_AREA = "area";

	public static final String PERSPECTIVE_PARTNER = "partner";

	// 地图树视图常量，结束

	public static final String SUCCESS = "success";

	public static final String FAILURE = "failure";

	public static final int DEFAULT_THREAD_SLEEP_SECONDS = 1;

}
