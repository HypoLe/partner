package com.boco.eoms.commons.system.priv.cache;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.priv.model.TawSystemPrivOperation;
import com.boco.eoms.commons.system.priv.service.ITawSystemPrivOperationManager;
import com.boco.eoms.partner.netresource.dao.IEomsDao;

public class OperationCache {
	
	public static Map<String,String>[] cache = new HashMap[2];
	
	static {
		loadAllOperation();
	}
	
	public static void loadAllOperation() {
		cache[0] = loadCache1();
		cache[1] = loadCache2();
	}
	
	private static Map<String,String> loadCache1() {
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Map<String,String> cache = new HashMap<String,String>(2000);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select code,parentcode from TAW_SYSTEM_PRIV_OPERATION";
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				cache.put(rs.getString(1), rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return cache;
	}
	private static Map<String,String> loadCache2() {
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Map<String,String> cache = new HashMap<String,String>(2000);
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select code,name from TAW_SYSTEM_PRIV_OPERATION";
		try {
			conn = ed.getCon();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				cache.put(rs.getString(1), rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(rs != null) {
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null) {
					if(!conn.isClosed()) {
						conn.close();
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return cache;
	}
	
	public static List<TawSystemPrivOperation> loadAllFatherModuels(String childCode) {
		List<TawSystemPrivOperation> list = new ArrayList<TawSystemPrivOperation>();
		
		String code = childCode;
		String name = "";
		String parentCode = "";
		while(code != null && !"-1".equals(code)) {
			parentCode = cache[0].get(code);
			name = cache[1].get(code);
			
			TawSystemPrivOperation tpo = new TawSystemPrivOperation();
			tpo.setCode(code);
			tpo.setName(name);
			list.add(tpo);
			
			code = parentCode;
		}
		
		return list;
	}
	
	public static String getWholeNameByCode(List moduleList) {
		String codeName = "";
		StringBuffer buffer = new StringBuffer();
		TawSystemPrivOperation tawSystemPrivOperation = null;
		int length = moduleList.size();
		for(int i=length-1; i>=0; i--) {
			tawSystemPrivOperation = (TawSystemPrivOperation)moduleList.get(i);
			buffer.append("<"+tawSystemPrivOperation.getName()+">");
			if(i!=0) {
				buffer.append("下");
			} else {
				buffer.append("操作。");
			}
		}
		codeName = buffer.toString();
		return codeName;
	}
}
