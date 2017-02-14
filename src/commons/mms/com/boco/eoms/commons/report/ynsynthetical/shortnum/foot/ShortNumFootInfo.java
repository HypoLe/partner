package com.boco.eoms.commons.report.ynsynthetical.shortnum.foot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.commons.mms.base.foot.BaseFootInfo;
import com.boco.eoms.commons.mms.base.foot.IFootInfo;

public class ShortNumFootInfo extends BaseFootInfo implements IFootInfo {

	/**
	 * 算法得出来的list，在MMSchedulerV35 中设置list初始化值
	 */
	private List list = null;
	
	/**
	 * 所有输入的条件
	 */
	private Map info = null;
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map getInfo() {
		Map map = new HashMap();
		map.putAll(info);
//		map.put("todaytime", "2009-7-6");
		map.put("beginTime", info.get("beginTime"));
		map.put("endTime", info.get("endTime"));
		
		//TD网KPI指标数据
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date data = Calendar.getInstance().getTime();
		if(list.size() < 1)
		{
			map.put("day", String.valueOf(data.getDate()));
			map.put("month", String.valueOf(data.getMonth()+1));
			return map;
		}
		else
		{
			String compress_date = String.valueOf(((Map)(list.get(0))).get("compress_date"));
			try {
				data = sdf.parse(compress_date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			map.put("day", String.valueOf(data.getDate()));
			map.put("month", String.valueOf(data.getMonth()+1));
			
			map.put("compress_date", nullObject2String(((Map)(list.get(0))).get("compress_date")));
			map.put("short_num_used", nullObject2String(((Map)(list.get(0))).get("short_num_used")));
			map.put("short_num_used1", nullObject2String(((Map)(list.get(0))).get("short_num_used1")));
			map.put("short_num_used2", nullObject2String(((Map)(list.get(0))).get("short_num_used2")));
			map.put("short_num_used3", nullObject2String(((Map)(list.get(0))).get("short_num_used3")));
			map.put("short_num_used4", nullObject2String(((Map)(list.get(0))).get("short_num_used4")));
			map.put("short_num_used5", nullObject2String(((Map)(list.get(0))).get("short_num_used5")));
			map.put("short_num_used6", nullObject2String(((Map)(list.get(0))).get("short_num_used6")));
			map.put("jt_num", nullObject2String(((Map)(list.get(0))).get("jt_num")));
			map.put("jt_num1", nullObject2String(((Map)(list.get(0))).get("jt_num1")));
			map.put("jt_num2", nullObject2String(((Map)(list.get(0))).get("jt_num2")));
			map.put("jt_num3", nullObject2String(((Map)(list.get(0))).get("jt_num3")));
			map.put("jt_num4", nullObject2String(((Map)(list.get(0))).get("jt_num4")));
			map.put("jt_num5", nullObject2String(((Map)(list.get(0))).get("jt_num5")));
			map.put("jt_num6", nullObject2String(((Map)(list.get(0))).get("jt_num6")));
		}
		
		return map;
	}

	public void setInfot(Map info) {
		this.info = info;
	}
	
	public static void main(String[] args)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String compress_date = "2009-8-19 14:19:28";
		Date data = Calendar.getInstance().getTime();
		try {
			data = sdf.parse(compress_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map map = new HashMap();
		map.put("day", String.valueOf(data.getDate()));
		map.put("month", String.valueOf(data.getMonth()+1));
		
		return  ;
	}
}
