package com.boco.eoms.workbench.contact.webapp.action;

public class EnumObject {
	
	String strElem = "";
	
	public EnumObject(){
		
	}
	
	public void Add(EnumObject eobject){
		strElem += eobject.toString() + ",";
	}
	
	public void Add(String estring){
		strElem += "\"" + estring + "\",";
	}
	
	public String toString()
	{
		String tempStr = "";
		if(strElem.length() > 0){
			tempStr = strElem.substring(0, strElem.length() - 1);
			tempStr = "[" + tempStr + "]";
		}else{
			tempStr = strElem;
			tempStr = "[" + tempStr + "]";
		}
		return tempStr;
	}
}
