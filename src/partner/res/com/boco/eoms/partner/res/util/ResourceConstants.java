package com.boco.eoms.partner.res.util;

import java.util.HashMap;
import java.util.Map;

/** 
 * Description: 常量
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */
public class ResourceConstants {

	public final static Integer PROPERTYTYPE_CHOOSE=0;                //请选择
	public final static Integer PROPERTYTYPE_SHARE=1;                //共享
	public final static Integer PROPERTYTYPE_CONSTRUCTION=2;         //自建
	public final static Integer PROPERTYTYPE_HIRE=3;                 //租用
	
	public final static Integer EXECUTETYPE_DEPARTMENT = 2;          //部门
	
	public final static String TlInspectFlag_YES = "1";          //线路巡检标识
	public final static String TlInspectFlag_NO = "0";          //非路巡检标识
	/**敷设点来源 正常情况*/
	public final static String TLP_SOURCE_0 = "0";          
	/**敷设点来源 光缆段起点新增*/
	public final static String TLP_SOURCE_1 = "1";          
	/**敷设点来源 光缆段终点新增*/
	public final static String TLP_SOURCE_2 = "2";          
	
	public static Map<Integer, String> propertyTypeType = new HashMap<Integer, String>();
	
	static{
		propertyTypeType.put(PROPERTYTYPE_CHOOSE, "--请选择--");
		propertyTypeType.put(PROPERTYTYPE_SHARE, "共享");
		propertyTypeType.put(PROPERTYTYPE_CONSTRUCTION, "自建");
		propertyTypeType.put(PROPERTYTYPE_HIRE, "租用");
	}
	
}
