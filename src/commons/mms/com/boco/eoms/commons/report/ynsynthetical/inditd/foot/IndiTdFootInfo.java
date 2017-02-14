package com.boco.eoms.commons.report.ynsynthetical.inditd.foot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.commons.mms.base.foot.BaseFootInfo;
import com.boco.eoms.commons.mms.base.foot.IFootInfo;

public class IndiTdFootInfo extends BaseFootInfo implements IFootInfo {

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
			map.put("td_user_amount", nullObject2String(((Map)(list.get(0))).get("td_user_amount")));
			map.put("td_tch_traffic", nullObject2String(((Map)(list.get(0))).get("td_tch_traffic")));
			map.put("td_tv_traffic", nullObject2String(((Map)(list.get(0))).get("td_tv_traffic")));
			map.put("td_group_traffic", nullObject2String(((Map)(list.get(0))).get("td_group_traffic")));
			map.put("td_drop_rate", nullObject2String(((Map)(list.get(0))).get("td_drop_rate")));
			map.put("td_conn_rate", nullObject2String(((Map)(list.get(0))).get("td_conn_rate")));
			map.put("ps_drop_rate", nullObject2String(((Map)(list.get(0))).get("ps_drop_rate")));
			map.put("ps_conn_rate", nullObject2String(((Map)(list.get(0))).get("ps_conn_rate")));
			map.put("td_cs", nullObject2String(((Map)(list.get(0))).get("td_cs")));
			map.put("td_ps", nullObject2String(((Map)(list.get(0))).get("td_ps")));
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
