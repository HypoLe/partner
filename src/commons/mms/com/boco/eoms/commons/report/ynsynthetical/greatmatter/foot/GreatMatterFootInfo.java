package com.boco.eoms.commons.report.ynsynthetical.greatmatter.foot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.commons.mms.base.foot.BaseFootInfo;
import com.boco.eoms.commons.mms.base.foot.IFootInfo;

public class GreatMatterFootInfo extends BaseFootInfo implements IFootInfo {

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
		//重大事件数据
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
			map.put("content", nullObject2String(((Map)(list.get(0))).get("content")));
		}
		
		return map;
	}
	
	public void setInfot(Map info) {
		this.info = info;
	}
}
