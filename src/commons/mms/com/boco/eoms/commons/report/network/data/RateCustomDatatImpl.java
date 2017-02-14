package com.boco.eoms.commons.report.network.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.commons.mms.base.custom.data.ICustomData;

public class RateCustomDatatImpl implements ICustomData {

	public List getCustomData(Map param) {
		 List list=new ArrayList();
		 
//		 Connection conn = null;
//		 Statement stmt = null;
//		 ResultSet rs = null;
//		    try {            
//		      Class.forName("com.informix.jdbc.IfxDriver").newInstance();
//		      String url =
//		          "jdbc:informix-sqli://10.101.16.16:8002/npmdb:informixserver=wnmsserver1;user=eoms;password=eoms2005";  // 数据源
//		      conn = DriverManager.getConnection(url);
//		      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//		                                            ResultSet.CONCUR_UPDATABLE);
//		      String sql = "select extend(compress_date, month to day)||'' as compress_date,hour(first_result) hour,region,suc_answ_sys,drop_call_tch,round(drop_rate ,3 ) drop_rate,first_result  from local_radio_rate where (hour(first_result) in (8,9,10,18,19,20)) and ne_type =10000 and compress_date >=extend(current-interval(6) day to day) and compress_date <=current  order by FIRST_RESULT,hour";
//		      rs = stmt.executeQuery(sql);
//		      while (rs.next())
//		      {
//		        String f1 = nullObject2String(rs.getString(1)).trim();
//		        String f2 = nullObject2String(rs.getString(2)).trim();
//		        String f = nullObject2String(rs.getString(3)).trim();
//		        String f3 =new String(f.getBytes("ISO-8859-1"),"gb2312");
//		        String f4 = nullObject2String(rs.getString(4)).trim();
//		        String f5 = nullObject2String(rs.getString(5)).trim();
//		        String f6 = nullObject2String(rs.getString(6)).trim();//掉话率 百分比
//		        f6 = String.valueOf(Double.parseDouble(f6)*100);
//		        //  把结果封装成map；
//		        //最后是要返回list封装成的map;
//		        Map map=new HashMap();
//		        map.put("f1",f1);
//		        map.put("f2",f2);
//		        map.put("f3",f3);
//		        map.put("f4",f4);
//		        map.put("f5",f5);
//		        map.put("f6",f6);
//		        list.add(map);
//		      }
//		    }
//		    catch (Exception e) {
//		      e.printStackTrace();
//		    }
//		    finally
//		    {
//		    	if(rs != null)
//		    	{
//		    		try {
//						rs.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					rs = null;
//		    	}
//		    	if(stmt != null)
//		    	{
//		    		 try {
//						stmt.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					stmt = null;
//		    	}
//		    	if(conn != null)
//		    	{
//		    		try {
//						conn.close();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					conn = null;
//		    	}
//		    }
		 list = initList();
		   return list;
	}
	
	private String nullObject2String(Object o) {
		String str = "";
		try {
			str = o.toString();
		} catch (Exception e) {
			return "";
		}
		return str;
	}
	
	private List initList()
	{
		List list=new ArrayList();
		Map map1= new HashMap();
		map1.put("f1","2009-7-3");
        map1.put("f2","8");
        map1.put("f3","四川省");
        map1.put("f4","75655");
        map1.put("f5","44776");
        map1.put("f6","59.2");
        
        Map map11= new HashMap();
        map11.put("f1","2009-7-3");
        map11.put("f2","9");
        map11.put("f3","四川省");
        map11.put("f4","75655");
        map11.put("f5","44776");
        map11.put("f6","52.2");
        
        Map map111= new HashMap();
        map111.put("f1","2009-7-3");
        map111.put("f2","10");
        map111.put("f3","四川省");
        map111.put("f4","75655");
        map111.put("f5","44776");
        map111.put("f6","57.2");
        
        Map map2= new HashMap();
        map2.put("f1","2009-7-4");
        map2.put("f2","8");
        map2.put("f3","四川省");
        map2.put("f4","75685");
        map2.put("f5","44776");
        map2.put("f6","69.2");
        
        Map map22= new HashMap();
        map22.put("f1","2009-7-4");
        map22.put("f2","9");
        map22.put("f3","四川省");
        map22.put("f4","75655");
        map22.put("f5","44776");
        map22.put("f6","52.2");
        
        Map map222= new HashMap();
        map222.put("f1","2009-7-4");
        map222.put("f2","10");
        map222.put("f3","四川省");
        map222.put("f4","75655");
        map222.put("f5","44776");
        map222.put("f6","68.2");
        
        //
        Map map3= new HashMap();
        map3.put("f1","2009-7-5");
        map3.put("f2","8");
        map3.put("f3","四川省");
        map3.put("f4","55685");
        map3.put("f5","42776");
        map3.put("f6","29.2");
        
        Map map33= new HashMap();
        map33.put("f1","2009-7-5");
        map33.put("f2","9");
        map33.put("f3","四川省");
        map33.put("f4","75655");
        map33.put("f5","44776");
        map33.put("f6","52.2");
        
        Map map333= new HashMap();
        map333.put("f1","2009-7-5");
        map333.put("f2","10");
        map333.put("f3","四川省");
        map333.put("f4","75655");
        map333.put("f5","44776");
        map333.put("f6","68.2");
        
        //
        Map map4= new HashMap();
        map4.put("f1","2009-7-6");
        map4.put("f2","8");
        map4.put("f3","四川省");
        map4.put("f4","55685");
        map4.put("f5","42776");
        map4.put("f6","35.2");
        
        Map map44= new HashMap();
        map44.put("f1","2009-7-6");
        map44.put("f2","9");
        map44.put("f3","四川省");
        map44.put("f4","75655");
        map44.put("f5","44776");
        map44.put("f6","52.2");
        
        Map map444= new HashMap();
        map444.put("f1","2009-7-6");
        map444.put("f2","10");
        map444.put("f3","四川省");
        map444.put("f4","75655");
        map444.put("f5","44776");
        map444.put("f6","68.2");
        
        //
        Map map5= new HashMap();
        map5.put("f1","2009-7-7");
        map5.put("f2","8");
        map5.put("f3","四川省");
        map5.put("f4","55685");
        map5.put("f5","42776");
        map5.put("f6","15.2");
        
        Map map55= new HashMap();
        map55.put("f1","2009-7-7");
        map55.put("f2","9");
        map55.put("f3","四川省");
        map55.put("f4","75655");
        map55.put("f5","44776");
        map55.put("f6","52.2");
        
        Map map555= new HashMap();
        map555.put("f1","2009-7-7");
        map555.put("f2","10");
        map555.put("f3","四川省");
        map555.put("f4","75655");
        map555.put("f5","44776");
        map555.put("f6","28.2");
        
        //
        Map map6= new HashMap();
        map6.put("f1","2009-7-8");
        map6.put("f2","8");
        map6.put("f3","四川省");
        map6.put("f4","55685");
        map6.put("f5","42776");
        map6.put("f6","75.2");
        
        Map map66= new HashMap();
        map66.put("f1","2009-7-8");
        map66.put("f2","9");
        map66.put("f3","四川省");
        map66.put("f4","75655");
        map66.put("f5","44776");
        map66.put("f6","52.2");
        
        Map map666= new HashMap();
        map666.put("f1","2009-7-8");
        map666.put("f2","10");
        map666.put("f3","四川省");
        map666.put("f4","75655");
        map666.put("f5","44776");
        map666.put("f6","28.2");
        
        list.add(map1);
        list.add(map11);
        list.add(map111);
        list.add(map2);
        list.add(map22);
        list.add(map222);
        list.add(map3);
        list.add(map33);
        list.add(map333);
        list.add(map4);
        list.add(map44);
        list.add(map444);
        list.add(map5);
        list.add(map55);
        list.add(map555);
        list.add(map6);
        list.add(map66);
        list.add(map666);
        
        return list;
	}
	
	public static void main(String[] args)
	{
//		String f6 = "0.625";
//		f6 = String.valueOf(Double.parseDouble(f6)*100);
//		System.out.println(f6);
		
		
		 Connection conn = null;
		 Statement stmt = null;
		 ResultSet rs = null;
		    try {            
		      Class.forName("com.informix.jdbc.IfxDriver").newInstance();
//		      sqli://10.194.2.224:8002/npmdb:INFORMIXSERVER=wnmsserver;NEWCODESET=gbk,8859-1,819
		      String url =
		          "jdbc:informix-sqli://10.194.2.224:8002/npmdb:informixserver=wnmsserver;user=npmuser;password=npmuser;NEWCODESET=gbk,8859-1,819";  // 数据源
		      conn = DriverManager.getConnection(url);
		      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		      
		      String sql = "select soa_get_nename(ne_id,ne_type)  ne_name,tch_traffic from tpa_radio_sum where first_result=extend(today-1,year to second)+10 units hour and ne_type=10003 and sum_level=0;";
		      rs = stmt.executeQuery(sql);
		      
		      while (rs.next())
		      {
			        String f1 = rs.getString(1).trim();
			        String f2 = rs.getString(2).trim();
			        System.out.println(f1 + "," + f2);
		      }
		      
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		    finally
		    {
		    	if(rs != null)
		    	{
		    		try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					rs = null;
		    	}
		    	if(stmt != null)
		    	{
		    		 try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					stmt = null;
		    	}
		    	if(conn != null)
		    	{
		    		try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					conn = null;
		    	}
		    }
	}
}
