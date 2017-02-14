package utils;

import java.util.Iterator;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

public class Nop3UtilsHibernateHelper {

	private static Configuration hibernateConf = new Configuration();

	private static PersistentClass getPersistentClass(Class clazz) {

		synchronized (Nop3UtilsHibernateHelper.class) {

			PersistentClass pc = hibernateConf.getClassMapping(clazz.getName());

			if (pc == null) {

				hibernateConf = hibernateConf.addClass(clazz);

				pc = hibernateConf.getClassMapping(clazz.getName());

			}

			return pc;

		}

	}
	/**
	 * 根据class获取该class对应的表名
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class clazz) {

		return getPersistentClass(clazz).getTable().getName();

	}

	/**
	 * 根据class获取该class对应的表的主键字段名
	 * @param clazz
	 * @return
	 */
	public static String getPkColumnName(Class clazz) {

		return getPersistentClass(clazz).getTable().getPrimaryKey()

		.getColumn(0).getName();

	}
	
	/**
	 * 根据class获取该class对应的表里的字段名字
	 * @param clazz
	 * @return
	 */
	public static String getColumnNames(Class clazz){
		String columnNames="";
		Iterator  columnIterator=getPersistentClass(clazz).getTable().getColumnIterator();
		while(columnIterator.hasNext()){
			Column column=(Column)columnIterator.next();
			columnNames=columnNames+column.getName()+",";
		}
		if(columnNames.indexOf(",")>0) columnNames=columnNames.substring(0,columnNames.lastIndexOf(","));
	    return columnNames;
	}
	/**
	 * 根据class获取该class类里的属性
	 * @param clazz
	 * @return 用,号隔开的String
	 */
	public static String getPropNamesWithoutPK(Class clazz){
		String propNames="";
		Iterator  columnIterator=getPersistentClass(clazz).getPropertyIterator();
		while(columnIterator.hasNext()){
			Property column=(Property)columnIterator.next();
			propNames=propNames+column.getName()+",";
		}
		if(propNames.indexOf(",")>0) propNames=propNames.substring(0,propNames.lastIndexOf(","));
	    return propNames;
	}
	
	/**
	 * 根据class获取该class类里的属性
	 * @param clazz
	 * @return
	 */
	public static java.util.List getPropNamesWithoutPK(Class clazz,String a){
		Iterator  columnIterator=getPersistentClass(clazz).getPropertyIterator();
		java.util.List<String> proNameList = new java.util.ArrayList<String>();
		while(columnIterator.hasNext()){
			Property column=(Property)columnIterator.next();
			proNameList.add(column.getName());
		}
	    return proNameList;
	}
	/**
	 * 根据class和表里的字段名，获取该class对应的类里的属性名
	 * @param clazz
	 * @param columnName
	 * @return
	 */
	public static String getPropNameByColumnName(Class clazz,String columnName) {
		String propName="";
		String pk=getPkColumnName(clazz);
		if(!columnName.equals(pk)){
		 Property property=getPersistentClass(clazz).getProperty(columnName);
		 if(property!=null){
			propName=property.getName();
		  }
		}
		else {
			propName=pk;	
		}
		return propName;		
	}
}
