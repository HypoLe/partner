package com.boco.eoms.task.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.task.mgr.ITaskMgr;
import com.boco.eoms.task.model.Task;

public class TaskAppUtil {
	/**
	 * 递归查找某大任务下的所有子任务（子任务信息存放在li的表中）
	 */
	public List li = new ArrayList();

	public Task taskSon(Task task) {
		// li.add(task) ;//这样写的话li列表包含父任务的全部任务
		Task ta = new Task();
		ITaskMgr taskMgr = (ITaskMgr) ApplicationContextHolder.getInstance()
				.getBean("taskMgr");
		List list = taskMgr.getChildTask(task.getTaskId());// 自己写方法getTasks（id）,用task的id取得子任务
		if (list.size() != 0) {// 有子任务
			Iterator it = list.iterator();
			while (it.hasNext()) {
				ta = (Task) it.next();
				ta.setEndTime(changeTime(ta.getEndTime()));
				ta.setStartTime(changeTime(ta.getStartTime()));
				li.add(ta);
				taskSon(ta);
			}
		} else {// 没有子任务
			ta = task;
		}
		return ta;
	}

	/**
	 * 更改时间显示格式为“yyyy-MM-dd”
	 * 
	 * @param time
	 * @return
	 */
	public static String changeTime(String time) {
		String dateString = "" ;
		if(!"".equals(time) && time != null){
			Date changeTime = new Date(Long.parseLong(time));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			dateString = formatter.format(changeTime);
		}
		return dateString;
	}

	/**
	 * 将日期时间（yyyy-MM-dd）改为GMT（格林尼治标准时间）1970 年，1 月 1 日 00:00:00
	 * 这一刻开始的毫秒数
	 * 
	 * @param time
	 * @return
	 */
	public static String changeTime2Long(String time) {
		String aa = null;
		if (!"".equals(time) && time != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd");
			try {
				Date date = (Date) formatter.parseObject(time);
				aa = Long.toString(date.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return aa;
	}

	/**
	 * 获得本周一零点
	 * 
	 * @return
	 */
	public static String getMonday(String format) {
		Date currentDate = StaticMethod.getLocalTime();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);

		// 取得本周一零点
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获得本周末23:59:59(yyyy-MM-dd hh:mm:ss)
	 * 
	 * @return
	 */
	public static String getSunday(String format) {
		Date currentDate = StaticMethod.getLocalTime();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);

		// 取得下周一零点
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date(calendar.getTime().getTime()
				+ (7 * 24 * 60 * 59 * 1000)));
	}

	/**
	 * 获得本月初的时间
	 * 
	 * @return
	 */
	public static String getFirstDayOfMonth(String format) {
		Date currentDate = StaticMethod.getLocalTime();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);

		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(calendar.getTime());
	}

	/**
	 * 获得本月末
	 * 
	 * @return
	 */
	public static String getLastDayOfMonth(String format) {
		Date currentDate = StaticMethod.getLocalTime();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);

		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(calendar.getTime());
	}
}
