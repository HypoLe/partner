package utils;

import static utils.Nop3Constants.*;
import static utils.Nop3UtilsDateHelper.getQuarterOfYear;
import static utils.Nop3UtilsDateHelper.getStartOfQuarter;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;

import base.model.Nop3GenericLink;
import base.model.Nop3GenericMain;
import base.pojo.ScheduledTask;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.genericdao.search.Search;

/**
 * 系统统一接口实用类，应该在本类中提供便利方法供调用。具体实现应该在其他类中。
 * 
 * @author Steve
 * 
 */
public class Nop3Utils {
	/**
	 * 拼接查询条件的泛型方法
	 * 
	 * An immutable java.util.Map containing parameter names as keys and
	 * parameter values as map values. The keys in the parameter map are of type
	 * String. The values in the parameter map are of type String array.
	 * 
	 * @author Steve
	 * @since August,2011
	 * 
	 */
	public static Search getSqlFromRequestMap(Map<String, String[]> requestMap,
			Search search) {
		String clauseValue;
		for (String clause : requestMap.keySet()) {
			clauseValue = requestMap.get(clause)[0];
			if (clauseValue.equals("")) {
				continue;
			} else if (clause.indexOf("StringEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringEqual"));
				search.addFilterEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("StringLike") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLike"));
				search.addFilterLike(clause, "%" + clauseValue.trim() + "%");
			} else if (clause.indexOf("DateGreaterThan") != -1) {
				clause = clause.substring(0, clause.indexOf("DateGreaterThan"));
				search.addFilterGreaterThan(clause, clauseValue.trim());
			} else if (clause.indexOf("DateLessThan") != -1) {
				clause = clause.substring(0, clause.indexOf("DateLessThan"));
				search.addFilterLessThan(clause, clauseValue.trim());
			} else {

			}
		}
		return search;
	}

	/**
	 * 设置流转信息的公共属性
	 * 
	 * @author Steve
	 * @since October,2011
	 * @param requestMap:
	 *            也就是参数Map
	 * @param nop3GenericLink
	 * @return
	 */
	public static <T extends Nop3GenericLink> T setLinkFromRequestMap(
			Map<String, String[]> requestMap, T nop3GenericLink) {

		// Set time related properties
		Date currentDate = new Date();
		nop3GenericLink.setOperateTime(toEomsStandardDate(currentDate));
		nop3GenericLink.setOperateTimeAtom(String
				.valueOf(currentDate.getTime()));

		String[] myTextArray = requestMap.get("myText");
		if (myTextArray != null && myTextArray.length > 0) {
			nop3GenericLink.setMyText(requestMap.get("myText")[0].toString());
		}
		String[] myTextTypeArray = requestMap.get("myTextType");
		if (myTextTypeArray != null && myTextTypeArray.length > 0) {
			nop3GenericLink.setMyTextType(requestMap.get("myTextType")[0]
					.toString());
		} else {
			nop3GenericLink.setMyTextType("plainText");
		}

		nop3GenericLink.setOperateType(requestMap.get("operateType")[0]
				.toString());
		return nop3GenericLink;
	}

	/**
	 * 设置Main表的公共属性
	 * 
	 * @author Steve
	 * @since October,2011
	 * @param requestMap:
	 *            也就是参数Map
	 * @param nop3GenericLink
	 * @return
	 */
	public static <T extends Nop3GenericMain> T setMainFromRequestMap(
			Map<String, String[]> requestMap, T nop3GenericMain) {

		// Set time related properties, you need joda time's help.
		Date currentDate = new Date();
		nop3GenericMain.setCreateDate(new Date());
		DateTime myDateTime = new DateTime(currentDate);

		nop3GenericMain.setYearFlag(myDateTime.getYear());
		nop3GenericMain.setDayFlag(myDateTime.getDayOfMonth());
		nop3GenericMain.setMonthFlag(myDateTime.getMonthOfYear());
		nop3GenericMain
				.setVerticalStatus(Nop3Constants.VERTICAL_STATUS_DEFAULT);

		if (requestMap == null || requestMap.size() < 1) {
			return nop3GenericMain;
		}

		// Set taskOwner and taskOwnerType, they can not be null, BEGIN
		String[] taskOwnerArray = requestMap.get("taskOwner");

		// Quick return
		if (taskOwnerArray == null || taskOwnerArray.length < 1) {
			return nop3GenericMain;
		}

		Preconditions.checkArgument(taskOwnerArray != null
				&& taskOwnerArray.length > 0);
		nop3GenericMain.setTaskOwner(taskOwnerArray[0]);

		String[] taskOwnerTypeArray = requestMap.get("taskOwnerType");
		Preconditions.checkArgument(taskOwnerTypeArray != null
				&& taskOwnerTypeArray.length > 0);
		nop3GenericMain.setTaskOwnerType(taskOwnerTypeArray[0]);
		// Set taskOwner and taskOwnerType, they can not be null, END

		return nop3GenericMain;
	}

	/**
	 * 为Nop3工作任务制定周期性的工作任务
	 * 
	 * 为Nop3考核指标制定周期性的考核指标
	 * 
	 * @author Steve
	 * @since August,2011
	 * @see utils.Nop3Utils.getScheduledTask(Date startDateTime, Date
	 *      endDatetime, String period)
	 * @param startDateTime:计划开始时间
	 * @param endDatetime:计划结束时间
	 * @param period:周期,根据业务需求,限定为:周,月,季度,半年,年;根据开发规范,限定为Nop3Constants里面的常量
	 * @return
	 */
	public static List<ScheduledTask> getScheduledTask(String startDateTime,
			String endDatetime, String period) {
		return getScheduledTask(toEomsStandardDate(startDateTime),
				toEomsStandardDate(endDatetime), period);
	}

	/**
	 * 
	 * 为Nop3工作任务制定周期性的工作任务
	 * 
	 * 为Nop3考核指标制定周期性的考核指标
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param startDateTime:计划开始时间
	 * @param endDatetime:计划结束时间
	 * @param period:周期,根据业务需求,限定为:天,周,月,季度,半年,年;根据开发规范,限定为Nop3Constants里面的常量
	 * 
	 */

	public static List<ScheduledTask> getScheduledTask(Date startDateTime,
			Date endDatetime, String period) {
		// Defence program
		Preconditions.checkArgument(getPeriodSet().contains(period),
				"See Nop3Constants's period constants please. ");
		List<ScheduledTask> scheduledList = Lists.newArrayList();
		ScheduledTask myScheduled = new ScheduledTask();
		DateTime myStartDateTime = new DateTime(startDateTime);
		DateTime myEndDateTime = new DateTime(endDatetime);
		int periodCount = 0;
		if (period.equals(PERIOD_OF_DAY)) {

			/*
			 * Calculate days between two data.
			 */
			int daysCount = Days.daysBetween(myStartDateTime, myEndDateTime)
					.getDays() + 1;
			for (int i = 0; i < daysCount; i++) {
				myScheduled.setStartDateTime(myStartDateTime.withMillisOfDay(0)
						.plusDays(i).toDate());
				myScheduled.setEndDateTime(myStartDateTime.withMillisOfDay(0)
						.plusDays(i + 1).minusSeconds(1).toDate());
				scheduledList.add(myScheduled);
				myScheduled = new ScheduledTask();
			}
		} else if (period.equals(PERIOD_OF_WEEK)) {

			/*
			 * Calculate weeks between two data， one year contains 52 weeks.
			 */
			int weeksCount = (myEndDateTime.getYear() - myStartDateTime
					.getYear())
					* 52
					+ myEndDateTime.getWeekOfWeekyear()
					- myStartDateTime.getWeekOfWeekyear() + 1;

			for (int i = 0; i < weeksCount; i++) {
				myScheduled.setStartDateTime(myStartDateTime.withDayOfWeek(1)
						.withMillisOfDay(0).plusWeeks(i).toDate());
				myScheduled.setEndDateTime(myStartDateTime.withDayOfWeek(7)
						.withHourOfDay(23).withMinuteOfHour(59)
						.withSecondOfMinute(59).plusWeeks(i).toDate());
				scheduledList.add(myScheduled);
				myScheduled = new ScheduledTask();
			}
		} else if (period.equals(PERIOD_OF_MONTH)) {
			periodCount = (myEndDateTime.getYear() - myStartDateTime.getYear())
					* 12
					+ (myEndDateTime.getMonthOfYear() - myStartDateTime
							.getMonthOfYear()) + 1;
			for (int i = 0; i < periodCount; i++) {
				myScheduled.setStartDateTime(myStartDateTime.withDayOfMonth(1)
						.withMillisOfDay(0).plusMonths(i).toDate());
				myScheduled.setEndDateTime(myStartDateTime.withDayOfMonth(1)
						.withMillisOfDay(0).plusMonths(i + 1).minusSeconds(1)
						.toDate());
				scheduledList.add(myScheduled);
				myScheduled = new ScheduledTask();
			}
		} else if (period.equals(PERIOD_OF_QUARTER)) {

			periodCount = (myEndDateTime.getYear() - myStartDateTime.getYear())
					* 4
					+ (getQuarterOfYear(myEndDateTime) - getQuarterOfYear(myStartDateTime))
					+ 1;
			// Change myStartDateTime reference
			myStartDateTime = getStartOfQuarter(myStartDateTime);
			int myMonthCount = 0;

			for (int i = 0; i < periodCount; i++) {
				myScheduled.setStartDateTime(myStartDateTime.plusMonths(
						myMonthCount).toDate());
				myScheduled.setEndDateTime(myStartDateTime.plusMonths(
						myMonthCount + 3).minusSeconds(1).toDate());
				myMonthCount += 3;
				scheduledList.add(myScheduled);
				myScheduled = new ScheduledTask();
			}
		} else if (period.equals(PERIOD_OF_HALF_YEAR)) {
			periodCount = ((myEndDateTime.getYear() - myStartDateTime.getYear()) * 12 + (myEndDateTime
					.getMonthOfYear() - myStartDateTime.getMonthOfYear())) / 6;
			int myMonthCount = 0;
			for (int i = 0; i < periodCount; i++) {
				myScheduled.setStartDateTime(myStartDateTime.withMillisOfDay(0)
						.plusMonths(myMonthCount).toDate());
				myScheduled.setEndDateTime(myStartDateTime.withMillisOfDay(0)
						.plusMonths(myMonthCount + 6).minusSeconds(1).toDate());
				myMonthCount += 6;
				scheduledList.add(myScheduled);
				myScheduled = new ScheduledTask();
			}
		}

		else if (period.equals(PERIOD_OF_YEAR)) {

			periodCount = myEndDateTime.getYear() - myStartDateTime.getYear()
					+ 1;
			for (int i = 0; i < periodCount; i++) {
				myScheduled.setStartDateTime(myStartDateTime.withDayOfYear(1)
						.withMillisOfDay(0).plusYears(i).toDate());
				myScheduled.setEndDateTime(myStartDateTime.withDayOfYear(1)
						.withMillisOfDay(0).plusYears(i + 1).minusSeconds(1)
						.toDate());
				scheduledList.add(myScheduled);
				myScheduled = new ScheduledTask();
			}
		} else {
			periodCount = 0;
		}
		System.out.println("Todoo we get period loop of " + periodCount);
		return scheduledList;
	}

	/**
	 * 
	 * 转化Data类型的值为eoms标准时间，例如2011-01-01 12:12:12
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param date
	 * @return
	 */
	public static String toEomsStandardDate(Date date) {
		return new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * 转化String类型的时间值为eoms标准时间Date型,即java.util.Date
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param date
	 * @return
	 */
	public static Date toEomsStandardDate(String date) {
		return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
				date).toDate();
	}

	/**
	 * 读入HttpServletResponse,输出成功的Json字符串到前台,全浏览器兼容
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param response
	 * @throws IOException
	 */
	public static void printJsonSuccessMsg(HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding(Charsets.UTF_8.toString());
		response.setContentType("text/plain");
		response.getWriter().write(
				new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true").put("msg", "ok").build()));
	}

	/**
	 * 读入HttpServletResponse,输出失败的Json字符串到前台,全浏览器兼容
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param response
	 * @throws IOException
	 */
	public static void printJsonFailureMsg(HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding(Charsets.UTF_8.toString());
		response.setContentType("text/plain");
		response
				.getWriter()
				.write(
						new Gson()
								.toJson(new ImmutableMap.Builder<String, String>()
										.put("success", "false").put("msg",
												"failure").build()));
	}

	/**
	 * 
	 * 读入HttpServletResponse,将Json格式写出对象到HttpServletResponse
	 * 
	 * 只传一个参数将直接把对象写出到HttpServletResponse，传二个参数则会把对象以键值对的方式写出到HttpServletResponse，
	 * 
	 * @author Steve
	 * @since August,2011
	 * 
	 * @param response
	 * @param object,可变参数，限制于2个参数之内
	 * @throws IOException
	 */
	public static void printJsonObject(HttpServletResponse response,
			Object... object) throws IOException {
		boolean myObjectLength = object.length >= 1 && object.length <= 2;
		Preconditions.checkArgument(myObjectLength,
				"Object Length should be between 1 and 2");
		response.setCharacterEncoding(Charsets.UTF_8.toString());
		response.setContentType("text/plain");
		Map<String, Object> myResultMap = Maps.newHashMap();
		myResultMap.put("success", "true");
		myResultMap.put("msg", "ok");
		if (object.length == 2) {
			myResultMap.put(object[0].toString(), object[1]);
			response.getWriter().write(new Gson().toJson(myResultMap));
		} else {
			response.getWriter().write(new Gson().toJson(object[0]));
		}
	}

	/**
	 * 
	 * 读入HttpServletResponse,将Json格式写出对象到HttpServletResponse 此方法能够序列化null字段
	 * 只传一个参数将直接把对象写出到HttpServletResponse
	 * ，传二个参数则会把对象以键值对的方式写出到HttpServletResponse，
	 * 
	 * @author Liuchang
	 * @since Dec,2011
	 * 
	 * @param response
	 * @param object
	 *            ,可变参数，限制于2个参数之内
	 * @throws IOException
	 */
	public static void printJsonObjectSerializeNulls(
			HttpServletResponse response, Object... object) throws IOException {
		boolean myObjectLength = object.length >= 1 && object.length <= 2;
		Preconditions.checkArgument(myObjectLength,
				"Object Length should be between 1 and 2");
		response.setCharacterEncoding(Charsets.UTF_8.toString());
		Map<String, Object> myResultMap = Maps.newHashMap();
		response.setContentType("text/plain");
		myResultMap.put("success", "true");
		myResultMap.put("msg", "ok");
		// serializeNulls 序列化null字段
		Gson gson = new GsonBuilder().serializeNulls().create();
		if (object.length == 2) {
			myResultMap.put(object[0].toString(), object[1]);
			response.getWriter().write(gson.toJson(myResultMap));
		} else {
			response.getWriter().write(gson.toJson(object[0]));
		}
	}

	/**
	 * 通过泛型来获取Spring Bean,可以不需要精确转型即可使用Bean
	 * 
	 * 传入参数首字母小写后应该等于注入的Bean的名称，例如：getService(BaseStationMainService.class);
	 * 
	 * beanName: 一般说来，Spring注入的Bean名称为类名首字母小写的规则；
	 * 加入beanName的目的是为了处理例如sessionForm这种特殊命名的Spring Bean
	 */
	public static <T> T getService(Class<T> t, String... beanName) {
		return Nop3UtilsSpringHelper.getService(t, beanName);
	}

	/**
	 * 接受Action类，截取字符串来动态控制前台的listName
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getListName(Class clazz) {
		// Defence coding.
		Preconditions
				.checkArgument(clazz.getSimpleName().indexOf("Action") != -1);
		String source = clazz.getSimpleName().substring(0,
				clazz.getSimpleName().length() - 6)
				+ "List";
		return source.substring(0, 1).toLowerCase().concat(source.substring(1));
	}

	public static void setPublicRequestAttribute(
			HttpServletRequest httpServletRequest) {
		String listType = Strings.nullToEmpty(httpServletRequest
				.getParameter("listType"));
		httpServletRequest.setAttribute("listType", listType);
	}

	/**
	 * 使用eoms附件组件上传文件的时候，该公共方法可以获得附件在服务器上的真实地址
	 * 
	 * @param accessoriesValue:附件名
	 * @return
	 */
	public static String getPathValue(String accessoriesValue) {
		return Nop3UtilsFileHelper.getPathValue(accessoriesValue);
	}

	/**
	 * 如果列表不为空，那么返回列表本身；如果列表为空，则返回空列表
	 */
	public static <T> List<T> nullToEmpty(List<T> list) {
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return Lists.newArrayList();
		}
	}

	/**
	 * @author DaiZhiGang
	 * @author Steve
	 * @param myFormula:传入的公式，四则表达式（即加减乘除），结果保存小数点后2位
	 * @param argumentMap:
	 *            键(key)：变量名;值(value)：变量值
	 * 
	 * @throws IllegalArgumentException:公式所含量变量与可变参数所含变量不匹配
	 */
	public static String getFormulaValue(String myFormula,
			Map<String, String> argumentMap) {
		try {
			return new Nop3UtilsFomulaHelper(myFormula, argumentMap)
					.getResult();
		} catch (NumberFormatException e) {
			System.out.println("BreakPoint-yunNan-公式中的变量名和传入Map参数中的变量名不匹配");
			return "0";
		}
	}

	public static String formatDouble(String result) {
		return String.format("%1$.2f", Double.parseDouble(result));
	}

	public static String formatDouble(double result) {
		return String.format("%1$.2f", result);
	}

	public static boolean isNum(String code) {
		if (code == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
		Matcher isNum = pattern.matcher(code);
		return isNum.matches();
	}

	public static boolean isCharacter(String code) {
		if (code == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[A-Za-z]*");
		Matcher isNum = pattern.matcher(code);
		return isNum.matches();
	}

	/**
	 * Builder模式，用来设置HttpServletRequest
	 * 
	 * @param request
	 * @return 设置好RequestURI的request，用于Jsp页面layout
	 */
	public static HttpServletRequest setRequestURI(HttpServletRequest request) {
		request.setAttribute(METHOD_HISTORY + "URI", request.getRequestURI()
				+ "?method=" + METHOD_HISTORY);
		request.setAttribute(METHOD_LIST + "URI", request.getRequestURI()
				+ "?method=" + METHOD_LIST);
		request.setAttribute(METHOD_DELETE + "URI", request.getRequestURI()
				+ "?method=" + METHOD_DELETE);
		request.setAttribute(METHOD_IMPORTEXCEL + "URI", request
				.getRequestURI()
				+ "?method=" + METHOD_IMPORTEXCEL);
		request.setAttribute(METHOD_ADD + "URI", request.getRequestURI()
				+ "?method=" + METHOD_ADD);
		request.setAttribute(METHOD_GO_TO_ADD + "URI", request.getRequestURI()
				+ "?method=" + METHOD_GO_TO_ADD);
		request.setAttribute(METHOD_GO_TO_IMPORTEXCEL + "URI", request
				.getRequestURI()
				+ "?method=" + METHOD_GO_TO_IMPORTEXCEL);
		request.setAttribute("URI", request.getRequestURI());
		return request;
	}

	/**
	 * Builder模式，用来设置HttpServletRequest
	 * 
	 * @param request
	 * @return
	 */
	public static <iActionClass, iModel extends Nop3GenericLink> HttpServletRequest setRequestListAttribute(
			HttpServletRequest request, Class<iActionClass> clazz,
			List<iModel> list) {
		System.out.println("Todoo" + clazz.getSimpleName());

		String listName = getListName(clazz);
		request.setAttribute("listName", listName);
		request.setAttribute(listName, list);

		for (iModel model : nullToEmpty(list)) {
			model.setOperateType((Nop3UtilsFiledValueHelper.dealWithField(model
					.getOperateType(), Nop3Constants.OPERATETYPE_LINK)));

			model.setMyText(Nop3UtilsFiledValueHelper.dealWithField(model
					.getMyText(), model.getMyTextType()));
		}

		return request;
	}

	/**
	 * 根据传入的起始时间，结束时间和周期计算出每个周期的时间段
	 * 
	 * @param period
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List<Date> getTimeWithPeriod(String period, Date startTime,
			Date endTime) {
		return Nop3UtilsDateHelper
				.getTimeWithPeriod(period, startTime, endTime);
	}

	/*public static boolean permitAddMonitorData(String monitorType,
			String country, String monitorCompany, int monthFlag) {
		return Nop3UtilsMonitorCompanyHelper.permitAddMonitorData(monitorType,
				country, monitorCompany, monthFlag);
	}
*/
	public static int getLevelOfTreeNode(String treeType) {
		// AreaId为26返回1，2601时返回2
		return treeType.length() / 2;
	}

	/*public static <T> T prepareSupervision(Class<T> clazz, String myType,
			Object... args) {
		return Nop3UtilsSupervisionHelper.prepareSupervision(clazz, myType,
				args);
	}
*/
	/**
	 * 仅适用于手机或者webService接口的创建督办项。交互格式为Json，封装对象必须为
	 * 
	 * com.boco.partner2.supervision.model.SupervisionMain
	 * 
	 * @param supervisionMainJsonStr
	 * @return
	 */
/*	public static String createSupervision(String supervisionMainJsonStr) {
		return Nop3UtilsSupervisionHelper
				.createSupervision(supervisionMainJsonStr);
	}

	public static String finishSupervision(String supervisionMainJsonStr) {
		return Nop3UtilsSupervisionHelper
				.finishSupervision(supervisionMainJsonStr);
	}

	public static <T> T createSupervision(Class<T> clazz, String myType,
			Object... args) {
		return Nop3UtilsSupervisionHelper
				.createSupervision(clazz, myType, args);
	}*/

/*	public static <T> T finishSupervision(Class<T> clazz, String myType,
			Object... args) {
		return Nop3UtilsSupervisionHelper
				.createSupervision(clazz, myType, args);
	}*/
}
