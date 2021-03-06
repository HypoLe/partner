package com.boco.eoms.sheet.base.qo.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.util.xml.XMLProperties;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.sheet.base.qo.DataBaseTypeAdapterQO;
import com.boco.eoms.sheet.base.qo.IWorkSheetQO;
import com.boco.eoms.sheet.base.util.QuerySqlInit;

/**
 * 工单查询对象实现
 * @author wangjianhua
 */
public class WorkSheetQOImpl implements IWorkSheetQO {

	private String poMain;
	
	private String poLink;
	
	private String poTask;
	
	protected String aliasMain;
	
	protected String aliasLink;
	
	protected String aliasTask;
	
	private  String fromName=" ";

	protected String clauseSql =" ";
	
	private String sheetModelConfig;
	
	/**
	 * 没有权限的公共查询
	 * @param properties
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getClauseSql(Map properties,Map condition) {
		//页面属性
		Iterator names = properties.keySet().iterator();
		//添加和排序
		StringBuffer where = new StringBuffer();
		StringBuffer orderWhere = new StringBuffer();
		DataBaseTypeAdapterQO dataBaseTypeAdapterQO = new DataBaseTypeAdapterQO();
		while (names.hasNext()) {
			String name = (String) names.next();
			if (name == null || name.equals("")) {
				continue;
			}
			//如果不是以Expression结尾的，说明是字段属性
			if (name.indexOf("Expression") == -1) {
				//判断name是不是以urgentFaultMain.sheetId形式开头。
				if (name.indexOf(".") > -1) {
					//得到属性值
					Object nameValue = properties.get(name);
					if (nameValue != null && nameValue.getClass().isArray()) {
						nameValue = ((Object[]) nameValue)[0];
					}
					String nameArray[] = name.split("\\.");
					String columnName = nameArray[1];
					if (nameArray[0].indexOf("main") > -1) {
						name = this.getAliasMain() + "." + columnName;
					} else if (nameArray[0].indexOf("task") > -1) {
						name = this.getAliasTask() + "." + columnName;
					} else {
						name = this.getAliasLink() + "." + columnName;
					}
					if (!StaticMethod.nullObject2String(properties.get(columnName + "StringExpression")).equals("")) { //匹配字符串型
						if (nameValue == null || nameValue.equals("")) continue;
						this.stringExpression(properties, columnName, where, name, (String)nameValue);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "StartDateExpression")).equals("")) { //匹配日期型
						this.dateExpression(properties, columnName, where, name, dataBaseTypeAdapterQO);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "StartNumberExpression")).equals("")) { //匹配数字型
						this.numberExpression(properties, columnName, where, name);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "OrderExpression")).equals("")) { //匹配排序条件
						this.orderExpression(properties, columnName, orderWhere, name);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "ChoiceExpression")).equals("")) { //匹配排序条件
						this.choiceExpression(properties, columnName, where, name);
					}
				} else {
					continue;
				}
			}			
		}
		
		String fromSql = "";
		String baseWhereSql = "";
		
		//条件表示有link表的字段，但没有task表的字段
		if(!where.toString().equals("") && where.toString().indexOf(getAliasLink()) > -1 &&  where.toString().indexOf(getAliasTask()) == -1){
			fromSql = "select distinct " + this.getAliasMain() + " from "
			            + this.getPoMain() + " as " + this.getAliasMain()+"," 
			            + this.getPoLink() + " as " + this.getAliasLink();
			baseWhereSql = " where " + this.getAliasMain() + ".id = "
			            + this.getAliasLink() + ".mainId  and " 
			            + this.getAliasMain() + ".templateFlag <> 1 and " 
			            + this.getAliasLink() + ".templateFlag <> 1 and "
			            + this.getAliasMain() + ".title is not null and "
						 //增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
						 + this.getAliasMain() + ".deleted<>'1' ";
			
    	//条件表示有task表的字段但没有link表的字段
		} else if(!where.toString().equals("") && where.toString().indexOf(getAliasLink()) == -1 &&  where.toString().indexOf(getAliasTask()) > -1){
			fromSql = "select distinct " + this.getAliasMain() + " from "
		            + this.getPoMain() + " as " + this.getAliasMain() + "," 
		            + this.getPoTask() + " as " + this.getAliasTask();
			baseWhereSql = " where " + this.getAliasMain() + ".id = "
		            + this.getAliasTask() + ".sheetKey  and " 
		            + this.getAliasMain() + ".templateFlag <> 1 and " 
		            + this.getAliasMain() + ".title is not null and "
		            //增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
		            + this.getAliasMain() + ".deleted<>'1' ";
			
    	//条件表示即有task表的字段又有link表的字段
		} else if(!where.toString().equals("") && where.toString().indexOf(getAliasLink()) > -1 &&  where.toString().indexOf(getAliasTask()) > -1){
			fromSql = "select distinct " + this.getAliasMain() + " from "
					 + this.getPoMain() + " as " + this.getAliasMain() + ","
					 + this.getPoLink() + " as " + this.getAliasLink() + ","
					 + this.getPoTask() + " as " + this.getAliasTask();
			baseWhereSql = " where " + this.getAliasMain() + ".id = " + this.getAliasLink() + ".mainId"
					 + " and " + this.getAliasMain() + ".id = " + this.getAliasTask() + ".sheetKey "
					 + " and " + this.getAliasMain() + ".templateFlag <> 1 " 
		             + " and " + this.getAliasMain() + ".title is not null and "
		    		 //增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
					 + this.getAliasMain() + ".deleted<>'1' ";
			
    	} else {//else表示的是即没有task表也没有link表的字段只有main表的字段
    		fromSql = "select distinct " + this.getAliasMain() + " from "
            		+ this.getPoMain() + " as " + this.getAliasMain();
    		baseWhereSql = " where " + this.getAliasMain() + ".templateFlag <> 1 and "
    				+ this.getAliasMain() + ".title is not null and "
		 			//增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
    				+ this.getAliasMain() + ".deleted<>'1' ";
    	}
		
		String sheetQueryRights = StaticMethod.nullObject2String(condition.get("sheetQueryRights")) ;
		if("true".equals(sheetQueryRights)){
			String rights = StaticMethod.nullObject2String(condition.get("rights"));
			if("true".equals(rights)){//需要进行权限控制
				String userDeptId = StaticMethod.nullObject2String(condition.get("userDeptId")) ;
				if(PartnerPrivUtils.isPartnerDept(userDeptId)){//如果是代维公司
					fromSql += " , PnrSheetQuery as query ";
					baseWhereSql += " and " + this.getAliasMain() + "=query.mainId ";
					
					int level = PartnerPrivUtils.getPartnerDeptLevel(userDeptId);
					if(PartnerPrivUtils.Level_provinceDept == level){
						baseWhereSql += " and query.provincePnrDept='"+userDeptId+"'";
					}else if(PartnerPrivUtils.Level_city == level){
						baseWhereSql += " and query.cityPnrDept='"+userDeptId+"'";
					}else if(PartnerPrivUtils.Level_County== level){
						baseWhereSql += " and query.countryPnrDept='"+userDeptId+"'";
					}else if(PartnerPrivUtils.Level_group == level){
						baseWhereSql += " and query.groupPnrDept='"+userDeptId+"'";
					}
				}else{ //如果是移动公司
					String userAreaId = StaticMethod.nullObject2String(condition.get("userAreaId")) ;
					String userId = StaticMethod.nullObject2String(condition.get("userId")) ;
					PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) ApplicationContextHolder.getInstance().getBean("pnrBaseAreaIdList");
					String rootAreaId = pnrBaseAreaIdList.getRootAreaId();
					
					//如果不是admin并且所在的地域不是根地域
					if(!"admin".equals(userId) && !rootAreaId.equals(userAreaId)){
						if(userAreaId.length() == (rootAreaId.length()+2)){ //如果是地市
							baseWhereSql += " and "+this.getAliasMain()+".mainCity='"+userAreaId+"' ";
						}else if(userAreaId.length() == (rootAreaId.length()+4)){
							baseWhereSql += " and "+this.getAliasMain()+".mainCountry='"+userAreaId+"' ";
						}
					}
				}
			}
		}
		
		fromName = fromSql + baseWhereSql;
		StringBuffer sql = new StringBuffer();
		sql.append(fromName).append(where.toString());
		String orderCondition = (String)condition.get("orderCondition");
		String pageSize = StaticMethod.nullObject2String(condition.get("pageSize"));	
		if(!pageSize.equals("-1")){
		if(orderCondition != null && ! orderCondition.equals("")){
			sql.append(" order by " +this.getAliasMain()+"."+ orderCondition);
		}else{
			if (!orderWhere.toString().equals("")) {
				sql.append( " order by " + orderWhere.toString());
			} else {
				sql.append(" order by " + this.getAliasMain() + ".sendTime desc");
			}			
		}
		}
		return sql.toString();
	}
	/**
	 * 适用于陕西用户要求的新查询，查询结果包括工单详情、处理详情和归档情况
	 * @param properties
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getClauseSqlNew(Map properties,Map condition) {
		//页面属性
		Iterator names = properties.keySet().iterator();
		//添加和排序
		StringBuffer where = new StringBuffer();
		StringBuffer orderWhere = new StringBuffer();
		DataBaseTypeAdapterQO dataBaseTypeAdapterQO = new DataBaseTypeAdapterQO();
		while (names.hasNext()) {
			String name = (String) names.next();
			if (name == null || name.equals("")) {
				continue;
			}
			//如果不是以Expression结尾的，说明是字段属性
			if (name.indexOf("Expression") == -1) {
				//判断name是不是以urgentFaultMain.sheetId形式开头。
				if (name.indexOf(".") > -1) {
					//得到属性值
					Object nameValue = properties.get(name);
					if (nameValue != null && nameValue.getClass().isArray()) {
						nameValue = ((Object[]) nameValue)[0];
					}
					String nameArray[] = name.split("\\.");
					String columnName = nameArray[1];
					if (nameArray[0].indexOf("main") > -1) {
						name = this.getAliasMain() + "." + columnName;
					} else if (nameArray[0].indexOf("task") > -1) {
						name = this.getAliasTask() + "." + columnName;
					} else {
						name = this.getAliasLink() + "." + columnName;
					}
					if (!StaticMethod.nullObject2String(properties.get(columnName + "StringExpression")).equals("")) { //匹配字符串型
						if (nameValue == null || nameValue.equals("")) continue;
						this.stringExpression(properties, columnName, where, name, (String)nameValue);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "StartDateExpression")).equals("")) { //匹配日期型
						this.dateExpression(properties, columnName, where, name, dataBaseTypeAdapterQO);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "StartNumberExpression")).equals("")) { //匹配数字型
						this.numberExpression(properties, columnName, where, name);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "OrderExpression")).equals("")) { //匹配排序条件
						this.orderExpression(properties, columnName, orderWhere, name);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "ChoiceExpression")).equals("")) { //匹配排序条件
						this.choiceExpression(properties, columnName, where, name);
					}
				} else {
					continue;
				}
			}			
		}
		
		String fromSql = "";
		String baseWhereSql = "";
		

		String sheetModelConfig = this.getSheetModelConfig();
		
		Map map = getQueryDict(sheetModelConfig);
    	String mainDict = map.get("mainDict").toString();
    	String mappingDict = map.get("mappingDict").toString();
    	String linkDict = map.get("linkDict").toString();		
    	String queryDict = mainDict + "," + mappingDict + "," + linkDict;   //拼接最终查询字段装在一个map里面作为结果返回
		String selectSql = " select " + getSqlForDict(this.getAliasMain(),mainDict) + 
		                    "," + getSqlForDict("mapping",mappingDict) + " from ";
		//条件表示有link表的字段，但没有task表的字段
		if(!where.toString().equals("") && where.toString().indexOf(getAliasLink()) > -1 &&  where.toString().indexOf(getAliasTask()) == -1){
//			fromSql = "select distinct " + this.getAliasMain() + ".* from "
			fromSql = selectSql
			            + getTableName(this.getPoMain()) + " as " + this.getAliasMain()+"," 
			            + getTableName(this.getPoLink()) + " as " + this.getAliasLink();
			baseWhereSql = " where " + this.getAliasMain() + ".id = "
			            + this.getAliasLink() + ".mainId  and " 
			            + this.getAliasMain() + ".templateFlag <> 1 and " 
			            + this.getAliasLink() + ".templateFlag <> 1 and "
			            + this.getAliasMain() + ".title is not null and "
						 //增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
						 + this.getAliasMain() + ".deleted<>'1' ";
			
    	//条件表示有task表的字段但没有link表的字段
		} else if(!where.toString().equals("") && where.toString().indexOf(getAliasLink()) == -1 &&  where.toString().indexOf(getAliasTask()) > -1){
//			fromSql = "select distinct " + this.getAliasMain() + ".* from "
			fromSql = selectSql
		            + getTableName(this.getPoMain()) + " as " + this.getAliasMain() + "," 
		            + getTableName(this.getPoTask()) + " as " + this.getAliasTask();
			baseWhereSql = " where " + this.getAliasMain() + ".id = "
		            + this.getAliasTask() + ".sheetKey  and " 
		            + this.getAliasMain() + ".templateFlag <> 1 and " 
		            + this.getAliasMain() + ".title is not null and "
		            //增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
		            + this.getAliasMain() + ".deleted<>'1' ";
			
    	//条件表示即有task表的字段又有link表的字段
		} else if(!where.toString().equals("") && where.toString().indexOf(getAliasLink()) > -1 &&  where.toString().indexOf(getAliasTask()) > -1){
//			fromSql = "select distinct " + this.getAliasMain() + ".* from "
			fromSql = selectSql
					 + getTableName(this.getPoMain()) + " as " + this.getAliasMain() + ","
					 + getTableName(this.getPoLink()) + " as " + this.getAliasLink() + ","
					 + getTableName(this.getPoTask()) + " as " + this.getAliasTask();
			baseWhereSql = " where " + this.getAliasMain() + ".id = " + this.getAliasLink() + ".mainId"
					 + " and " + this.getAliasMain() + ".id = " + this.getAliasTask() + ".sheetKey "
					 + " and " + this.getAliasMain() + ".templateFlag <> 1 " 
		             + " and " + this.getAliasMain() + ".title is not null and "
		    		 //增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
					 + this.getAliasMain() + ".deleted<>'1' ";
			
    	} else {//else表示的是即没有task表也没有link表的字段只有main表的字段
//    		fromSql = "select distinct " + this.getAliasMain() + ".* from "
			fromSql = selectSql
            		+ getTableName(this.getPoMain()) + " as " + this.getAliasMain();
    		baseWhereSql = " where " + this.getAliasMain() + ".templateFlag <> 1 and "
    				+ this.getAliasMain() + ".title is not null and "
		 			//增加条件main.deleted<>'1' ，过滤已经被删除或隐藏的工单 add by jialei
    				+ this.getAliasMain() + ".deleted<>'1' ";
    	}
		
		/* 增加关联映射表查询上一步操作人 add by munanyang  begin*/
		fromSql += " ,pnr_sheet_mapping as mapping " ;
		baseWhereSql += " and " + this.getAliasMain() + ".id = mapping.mainid ";
		/* 增加关联映射表查询上一步操作人，关联link表查询工单处理情况 add by munanyang  end*/		
		
		String sheetQueryRights = StaticMethod.nullObject2String(condition.get("sheetQueryRights")) ;
		if("true".equals(sheetQueryRights)){
			String rights = StaticMethod.nullObject2String(condition.get("rights"));
			if("true".equals(rights)){//需要进行权限控制
				String userDeptId = StaticMethod.nullObject2String(condition.get("userDeptId")) ;
				if(PartnerPrivUtils.isPartnerDept(userDeptId)){//如果是代维公司
					fromSql += " , Pnr_Sheet_Query as query ";
					baseWhereSql += " and " + this.getAliasMain() + ".id=query.mainId ";
					
					int level = PartnerPrivUtils.getPartnerDeptLevel(userDeptId);
					if(PartnerPrivUtils.Level_provinceDept == level){
						baseWhereSql += " and query.provincePnrDept='"+userDeptId+"'";
					}else if(PartnerPrivUtils.Level_city == level){
						baseWhereSql += " and query.cityPnrDept='"+userDeptId+"'";
					}else if(PartnerPrivUtils.Level_County== level){
						baseWhereSql += " and query.countryPnrDept='"+userDeptId+"'";
					}else if(PartnerPrivUtils.Level_group == level){
						baseWhereSql += " and query.groupPnrDept='"+userDeptId+"'";
					}
				}else{ //如果是移动公司
					String userAreaId = StaticMethod.nullObject2String(condition.get("userAreaId")) ;
					String userId = StaticMethod.nullObject2String(condition.get("userId")) ;
					PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) ApplicationContextHolder.getInstance().getBean("pnrBaseAreaIdList");
					String rootAreaId = pnrBaseAreaIdList.getRootAreaId();
					
					//如果不是admin并且所在的地域不是根地域
					if(!"admin".equals(userId) && !rootAreaId.equals(userAreaId)){
						if(userAreaId.length() == (rootAreaId.length()+2)){ //如果是地市
							baseWhereSql += " and "+this.getAliasMain()+".mainCity='"+userAreaId+"' ";
						}else if(userAreaId.length() == (rootAreaId.length()+4)){
							baseWhereSql += " and "+this.getAliasMain()+".mainCountry='"+userAreaId+"' ";
						}
					}
				}
			}
		}
		
		fromName = fromSql + baseWhereSql;
		StringBuffer sql = new StringBuffer();
		sql.append(fromName).append(where.toString());
//		return sql.toString();
//	    Map sqlMap = getQuerySql(sql.toString());
	    String sqlNew = getQuerySql(sql.toString(),mainDict,mappingDict,linkDict);
	    

		String orderCondition = (String)condition.get("orderCondition");
		String pageSize = StaticMethod.nullObject2String(condition.get("pageSize"));	
		if(!pageSize.equals("-1")){
		if(orderCondition != null && ! orderCondition.equals("")){
			sqlNew +=" order by a."+ orderCondition;
		}else{
			if (!orderWhere.toString().equals("")) {
				sqlNew += " order by " + orderWhere.toString();
			} else {
				sqlNew += " order by a.sheetId desc";
			}			
		}
		}	    
		
		//最后查询的结果集需要根据配置文件中的列名整合成一个包含map的list，为了避免多次读取配置文件，在这里将列名返回
		Map sqlMap = new HashMap();
		sqlMap.put("sqlMap", sqlNew);
		sqlMap.put("queryDict",queryDict);  
        	
 		return sqlMap;
	}
	
	/**
	 * 带有权限的SQL查询，运用待处理列表、已处理列表、草稿列表、建立工单列表、已归档工单列表、已作废工单列表的查询
	 * @param properties
	 * @param userId
	 * @param displayTabelObjName 列表里需要显示的对象列表如NetdataMain or NetdataLink or NetdataTask
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAclClauseSql(Map properties, Map condition) {
		String flowName = (String) condition.get("flowName");
		//页面属性
		Iterator names = properties.keySet().iterator();
		//添加和排序
		StringBuffer where = new StringBuffer();
		StringBuffer orderWhere = new StringBuffer();
		DataBaseTypeAdapterQO dataBaseTypeAdapterQO = new DataBaseTypeAdapterQO();
		
		while (names.hasNext()) {
			String name = (String) names.next();
			if (name == null || name.equals("")) {
				continue;
			}
			//如果不是以Expression结尾的，说明是字段属性
			if (name.indexOf("Expression") == -1) {
				//判断name是不是以urgentFaultMain.sheetId形式开头。
				if (name.indexOf(".") > -1) {
					//得到属性值
					Object nameValue = properties.get(name);
					if (nameValue != null && nameValue.getClass().isArray()) {
						nameValue = ((Object[]) nameValue)[0];
					}
					String nameArray[] = name.split("\\.");
					String columnName = nameArray[1];
//					if (nameArray[0].indexOf("main") > -1) {
//						name = this.getAliasMain() + "." + columnName;
//					} else if (nameArray[0].indexOf("task") > -1) {
//						name = this.getAliasTask() + "." + columnName;
//					} else {
//						name = this.getAliasLink() + "." + columnName;
//					}
					if (!StaticMethod.nullObject2String(properties.get(columnName + "StringExpression")).equals("")) { //匹配字符串型
						if (nameValue == null || nameValue.equals("")) continue;
						this.stringExpression(properties, columnName, where, name, (String)nameValue);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "StartDateExpression")).equals("")) { //匹配日期型
						this.dateExpression(properties, columnName, where, name, dataBaseTypeAdapterQO);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "StartNumberExpression")).equals("")) { //匹配数字型
						this.numberExpression(properties, columnName, where, name);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "OrderExpression")).equals("")) { //匹配排序条件
						this.orderExpression(properties, columnName, orderWhere, name);
					} else if (!StaticMethod.nullObject2String(properties.get(columnName + "ChoiceExpression")).equals("")) { //匹配排序条件
						this.choiceExpression(properties, columnName, where, name);
					}
				} else {
					continue;
				}
			}			
		}
		
		//从页面获取是 待处理/已处理 的查询页面列表
		//得到属性值
		Object done = properties.get("doneType");
		if (done != null && done.getClass().isArray()) {
			done = ((Object[]) done)[0];
		}
		if (done != null ) {
			//已处理列表
			if (done.equals("senddone")) {
				fromName = QuerySqlInit.getDoneTaskSql(condition);
			//未处理列表
			} else if (done.equals("sendundo")) {
				fromName = QuerySqlInit.getUndoTaskSql(condition, flowName);
					
			}	
		//从草稿，管理者工单列表，建立工单等列表查询	
		} 

		StringBuffer sql = new StringBuffer();
		sql.append(fromName).append(where.toString());
		String orderCondition = StaticMethod.nullObject2String(condition.get("orderCondition"));
		String pageSize = StaticMethod.nullObject2String(condition.get("pageSize"));	
		if(!pageSize.equals("-1")){		
		if(orderCondition != null && ! orderCondition.equals("")){
			sql.append(" order by " + orderCondition);
		}else{
			if (!orderWhere.toString().equals("")) {
				sql.append( " order by " + orderWhere.toString());
			} else {
				if (done.equals("sendundo")) {
					sql.append(" order by task.createTime desc");
				} else {
					sql.append(" order by main.sendTime desc");
				}
			}	
		}
		}

		System.out.println("sql========"+sql);
		return sql.toString();
	}
	
	@SuppressWarnings("unchecked")
	public void stringExpression(Map properties, String columnName, StringBuffer where, String name, String nameValue ) {
		Object expressionValue = properties.get(columnName + "StringExpression");
		if (expressionValue != null && expressionValue.getClass().isArray()) {
			expressionValue = ((Object[]) expressionValue)[0];
		}
		if (expressionValue != null && expressionValue.equals("=")) {
			where.append(WorkSheetQOhelper.addEqualClause(name, nameValue));
		} if (expressionValue != null && expressionValue.equals("in")) { 
			where.append(WorkSheetQOhelper.addInClause(name, nameValue));
		} else {
			where.append(WorkSheetQOhelper.addLikeClause(name, nameValue));
		}	
	}
	
	@SuppressWarnings("unchecked")
	public void dateExpression(Map properties, String columnName, StringBuffer where, String name,DataBaseTypeAdapterQO dataBaseTypeAdapterQO) {
		StringBuffer dateWhere = new StringBuffer();
		
		Object startDateExpressionValue = properties.get(columnName + "StartDateExpression");
		if (startDateExpressionValue != null && startDateExpressionValue.getClass().isArray()) {
			startDateExpressionValue = ((Object[]) startDateExpressionValue)[0];
		}
		
		Object startDateValue = properties.get(columnName + "StartDate");
		if (startDateValue != null && startDateValue.getClass().isArray()) {
			startDateValue = ((Object[]) startDateValue)[0];
		}
		
		Object logicExpressionValue = properties.get(columnName + "LogicExpression");
		if (logicExpressionValue != null && logicExpressionValue.getClass().isArray()) {
			logicExpressionValue = ((Object[]) logicExpressionValue)[0];
		}
		
		Object endDateExpressionValue = properties.get(columnName + "EndDateExpression");
		if (endDateExpressionValue != null && endDateExpressionValue.getClass().isArray()) {
			endDateExpressionValue = ((Object[]) endDateExpressionValue)[0];
		}
		
		Object endDateValue = properties.get(columnName + "EndDate");
		if (endDateValue != null && endDateValue.getClass().isArray()) {
			endDateValue = ((Object[]) endDateValue)[0];
		}
		
		if (startDateValue != null && !startDateValue.equals("")) {
			dateWhere.append(" and (").append(WorkSheetQOhelper.addGreatOrEqualDateClause(name, (String)startDateExpressionValue, (String)startDateValue, dataBaseTypeAdapterQO));
		}
		if (endDateValue != null && !endDateValue.equals("")) {
			if (logicExpressionValue == null || logicExpressionValue.equals("")) logicExpressionValue = "and";
			dateWhere.append(" " + logicExpressionValue + " ");
			dateWhere.append(WorkSheetQOhelper.addGreatOrEqualDateClause(name, (String)endDateExpressionValue , (String)endDateValue, dataBaseTypeAdapterQO));
			dateWhere.append(")");
		} else if ((endDateValue == null || endDateValue.equals("")) && (startDateValue!= null && !startDateValue.equals(""))) {
			dateWhere.append(")");
		}
		where.append(dateWhere.toString());
		dateWhere = null;
	}
	
	@SuppressWarnings("unchecked")
	public void numberExpression(Map properties, String columnName, StringBuffer where, String name) {
		StringBuffer numberWhere = new StringBuffer();
		Object startNumberExpressionValue = properties.get(columnName + "StartNumberExpression");
		if (startNumberExpressionValue != null && startNumberExpressionValue.getClass().isArray()) {
			startNumberExpressionValue = ((Object[]) startNumberExpressionValue)[0];
		}
		
		Object startNumberValue = properties.get(columnName + "StartNumber");
		if (startNumberValue != null && startNumberValue.getClass().isArray()) {
			startNumberValue = ((Object[]) startNumberValue)[0];
		}
		
		Object logicExpressionValue = properties.get(columnName + "LogicExpression");
		if (logicExpressionValue != null && logicExpressionValue.getClass().isArray()) {
			logicExpressionValue = ((Object[]) logicExpressionValue)[0];
		}
		
		Object EndNumberExpressionValue = properties.get(columnName + "EndNumberExpression");
		if (EndNumberExpressionValue != null && EndNumberExpressionValue.getClass().isArray()) {
			EndNumberExpressionValue = ((Object[]) EndNumberExpressionValue)[0];
		}
		
		Object EndNumberValue = properties.get(columnName + "EndNumber");
		if (EndNumberValue != null && EndNumberValue.getClass().isArray()) {
			EndNumberValue = ((Object[]) EndNumberValue)[0];
		}
		
		if (startNumberValue != null && !String.valueOf(startNumberValue).equals("")) {
			numberWhere.append(" and (").append(WorkSheetQOhelper.addGreatOrEqualNumberClause(name, (String)startNumberExpressionValue, String.valueOf(startNumberValue)));
		}
		if (EndNumberValue != null && !String.valueOf(EndNumberValue).equals("")) {
			if (logicExpressionValue.equals("")) logicExpressionValue = "and";
			numberWhere.append(" " + logicExpressionValue + " ");
			numberWhere.append(WorkSheetQOhelper.addGreatOrEqualNumberClause(name, (String)EndNumberExpressionValue , String.valueOf(EndNumberValue)));
			numberWhere.append(")");
		} else if ((EndNumberValue == null || EndNumberValue.equals("")) && (startNumberValue != null && !startNumberValue.equals(""))) {
			numberWhere.append(")");
		}
		where.append(numberWhere.toString());
		numberWhere = null;
	}
	
	@SuppressWarnings("unchecked")
	public void choiceExpression(Map properties, String columnName, StringBuffer where, String name) { 
		Object choiceExpressionValue = properties.get(columnName + "ChoiceExpression");
		if (choiceExpressionValue != null && choiceExpressionValue.getClass().isArray()) {
			choiceExpressionValue = ((Object[]) choiceExpressionValue)[0];
			where.append(WorkSheetQOhelper.addEqualClause(name, (String)choiceExpressionValue));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void orderExpression(Map properties, String columnName, StringBuffer orderWhere, String name) {
		Object orderExpressionValue = properties.get(columnName + "OrderExpression");
		if (orderExpressionValue != null && orderExpressionValue.getClass().isArray()) {
			orderExpressionValue = ((Object[]) orderExpressionValue)[0];
		}
		if (orderWhere.toString().equals("")) {
			orderWhere.append(WorkSheetQOhelper.addOrderClause(name, (String)orderExpressionValue));
		} else {
			orderWhere.append("," + WorkSheetQOhelper.addOrderClause(name, (String)orderExpressionValue));
		}
		
	}
	
	public void setPoMain(String PoName) {
		this.poMain = PoName;
	}
	public String getPoMain() {
		return poMain;
	}
	public void setPoLink(String PoName) {
		this.poLink = PoName;
	}
	public String getPoLink() {
		return poLink;
	}

	public String getAliasLink() {
		return aliasLink;
	}

	public void setAliasLink(String aliasLink) {
		this.aliasLink = aliasLink;
	}

	public String getAliasMain() {
		return aliasMain;
	}

	public void setAliasMain(String aliasMain) {
		this.aliasMain = aliasMain;
	}

	public String getAliasTask() {
		return aliasTask;
	}

	public void setAliasTask(String aliasTask) {
		this.aliasTask = aliasTask;
	}

	public String getPoTask() {
		return poTask;
	}

	public void setPoTask(String poTask) {
		this.poTask = poTask;
	}
	public String getTableName(String entryName){
		String name = entryName.substring(3,entryName.length()-4);
		String tableName = "pnr_";
		if(entryName.indexOf("Main")>1){
			tableName +=  name + "_main";
		}else if(entryName.indexOf("Link")>1){
			tableName +=  name + "_link";
		}else if(entryName.indexOf("Task")>1){
			tableName +=  name + "_task";
		}
		return tableName;
	}
	/**
	 *  根据传入的参数拼接sql 
	 */
	public String getQuerySql(String sql,String mainDict,String mappingDict,String linkDict){
		   	
    	
		String sqlNew = " select " + getSqlForDict("a",mainDict) + "," 
		              + getSqlForDict("a",mappingDict) + ","
		              + getSqlForDict("link",linkDict) 
		              + " from (" + sql 
		              + ") a left join " + getTableName(this.getPoLink()) + " link " 
		              + " on a.replylinkid = link.id ";
		


		return sqlNew;

	}
	/**
	 *  根据工单类型从配置文件中读取需要查询的列
	 */
    public Map getQueryDict(String sheetModelConfig){
    	String mainDict = "";
    	String linkDict = "";
    	String mappingDict = "";
//    	String taskDict = "";
    	Map map = new HashMap();
    	try{
    		XMLProperties queryProperties = XmlManage.getFile(sheetModelConfig);
    		mainDict = queryProperties.getProperty("main");  
    		linkDict = queryProperties.getProperty("link");  
    		mappingDict = queryProperties.getProperty("mapping");  
//    		taskDict = queryProperties.getProperty("task");  
    		map.put("mainDict", mainDict);
    		map.put("linkDict", linkDict);
    		map.put("mappingDict", mappingDict);
//    		map.put("taskDict", taskDict);
    	}catch(Exception e){
            e.printStackTrace();
		 }
    	return map;
    }    
    public String getSqlForDict(String tableName,String Dict){
    	String [] dictList = Dict.split(",");
    	String querySql = "";
    	for(int i = 0 ; i < dictList.length; i++){
    		if(i < dictList.length -1){
        		querySql += tableName + "." + dictList[i] + ",";
    		}else {
        		querySql += tableName + "." + dictList[i];
    		}
    	}
    	return querySql;
    }
	public String getSheetModelConfig() {
		return sheetModelConfig;
	}
	public void setSheetModelConfig(String sheetModelConfig) {
		this.sheetModelConfig = sheetModelConfig;
	}
    
}
