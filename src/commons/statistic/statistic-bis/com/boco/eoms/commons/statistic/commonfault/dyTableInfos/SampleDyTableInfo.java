package com.boco.eoms.commons.statistic.commonfault.dyTableInfos;

import com.boco.eoms.commons.statistic.base.excelutil.mgr.DyTableInfos;
import com.boco.eoms.commons.statistic.base.excelutil.mgr.impl.DyTableInfo;

/**
 * 复杂的excel样式配置，有表头表尾，表尾有汇总配置，合并拆分单元格
 * @author lizhenyou
 *
 */
public class SampleDyTableInfo implements DyTableInfos {

	public DyTableInfo[] getDyTableInfos() {
		//动态列
		String id1 = "1220";//配置字典的id
    	String[] headNames1 = {"处理完成总数"};//表头文字信息
    	String[] bodyNames1 = {"stat1","stat2","stat3"};//表体配置信息
    	String[] footNames1 = {"stat1","stat2","stat3"};//表尾配置信息
    	DyTableInfo dti1 = new  DyTableInfo(id1,headNames1,bodyNames1,footNames1);
    	
    	String id2 = "1221";
    	String[] headNames2 = {"驳回总数"};
    	String[] bodyNames2 = {"stat4","stat5","stat6"};
    	String[] footNames2 = {"stat4","stat5","stat6"};
    	DyTableInfo dti2 = new  DyTableInfo(id2,headNames2,bodyNames2,footNames2);
    	
    	String id3 = "1222";
    	String[] headNames3 = {"未处理完成总数"};
    	String[] bodyNames3 = {"stat7","stat8","stat9"};
    	String[] footNames3 = {"stat7","stat8","stat9"};
    	DyTableInfo dti3 = new  DyTableInfo(id3,headNames3,bodyNames3,footNames3);
    	
    	DyTableInfo[] dtis = {dti1,dti2,dti3};
    	
    	return dtis;
	}
	
	public DyTableInfo getDyTableInfoById(String id)
	{
		DyTableInfo[] dtis = getDyTableInfos();
		for(int i=0;i<dtis.length;i++)
		{
			if(id.equalsIgnoreCase(dtis[i].getId()))
			{
				return dtis[i];
			}
		}
		
		return null;
	}
}
