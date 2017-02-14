package com.boco.eoms.sheet.base.util;

import java.util.HashMap;

public class EomsSheetTableColumnLengthCache {
	
	public static HashMap sheetTableColumnLengthMap = new HashMap();

	public static HashMap getSheetTableColumnLengthMap() {
		return sheetTableColumnLengthMap;
	}

	public static void setSheetTableColumnLengthMap(
			HashMap sheetTableColumnLengthMap) {
		EomsSheetTableColumnLengthCache.sheetTableColumnLengthMap = sheetTableColumnLengthMap;
	}
	
	public static void addTable(String tableName,HashMap tableMap){
		
			HashMap map = getSheetTableColumnLengthMap();
			if(map==null){
				sheetTableColumnLengthMap = new HashMap();
			}
			if(!sheetTableColumnLengthMap.containsKey(tableName)){
				map.put(tableName,tableMap);
				setSheetTableColumnLengthMap(map);
			}
			
		
	}
	
	public static void removeTable(String tableName){
		if( getSheetTableColumnLengthMap().containsKey(tableName)){
			HashMap map = getSheetTableColumnLengthMap();
			map.remove(tableName);
			setSheetTableColumnLengthMap(map);
		}
	}
	
	public static void removeAllTable(){
		if(getSheetTableColumnLengthMap()!=null && getSheetTableColumnLengthMap().isEmpty()==false){
			HashMap map = getSheetTableColumnLengthMap();
			map.clear();
			setSheetTableColumnLengthMap(map);
		}
	}
}
