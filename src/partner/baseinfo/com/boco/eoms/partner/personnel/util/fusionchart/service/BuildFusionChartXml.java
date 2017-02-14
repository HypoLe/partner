package com.boco.eoms.partner.personnel.util.fusionchart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import com.boco.eoms.partner.personnel.util.MyUtil;
import com.boco.eoms.partner.personnel.util.fusionchart.FusionChartType;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.Category;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.Chart;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.Dataset;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.SetValue;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.XMLData;

	/**
 * <p>
 * Title:构建页面不同图像的XML
 * </p>
 * <p>
 * Description: 构建XML
 * </p>
 * <p>
 * Jul 22, 2012 9:06:53 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class BuildFusionChartXml {
	
	/**
	 * 柱形 颜色数组
	 */
	private static String[] colors  = {"94B6D2","D6A91F","E79764","D57C7C","7EA900"};
	/**
	 * 图形 标题
	 */
	private static String title;
	/**
	 * 图形 数字后缀
	 */
	private static String numberSuf = "";
	/**
	 * 柱 间距
	 */
	private static int span = 200; 
	/**
	 * 图形 长度
	 */
	private static int width = 600;
	
	private static int showValues=1;
	/**
	 * 返回柱状图(柱可以细分)XML  不可自定义图例字段 图注值由数据库数据生成
	 * @param sql 统计查询语句
	 * @param statisticsType 统计类型（查询结果字段名称 as时取别名） 不能为空
	 * @return
	 */
	public static String getBarXML(String sql,String statisticsType){

		List<Category> categories = new ArrayList<Category>();
		List<Dataset> dataset = new ArrayList<Dataset>();
			List<SetValue> setvalue;
			FusionChartDBData fcdata = new FusionChartDBData();
			List<XMLData> xmldata = fcdata.getDBXMLData(sql, statisticsType);
			if(xmldata==null||xmldata.size()==0)
				return null;
			
			Set<String> categoriesNames = new TreeSet<String>();
			Set<String> datasetNames = new TreeSet<String>();
			for (XMLData data : xmldata) {
				datasetNames.add(data.getDatasetName());//去除重复的 setname
			}
			for (XMLData data : xmldata) {
				categoriesNames.add(data.getCategoryLable());//去除重复的 categoriesName
			}
			
			if(dataset.size()>colors.length)
				throw new RuntimeException("颜色种类少于统计基础类别数量，请添加更多的颜色");
			
			//重新封装XMLData数据，便于构造Chart	
			//Map<dataset List<Map<categories,setvalue>>>
			TreeMap<String,  ArrayList<TreeMap<String,String>>> chatDateMap =
				new TreeMap<String,ArrayList<TreeMap<String,String>>>();
			ArrayList<TreeMap<String, String>> listMap;
			TreeMap<String, String> map;
			for(String setName:datasetNames){
				listMap = new ArrayList<TreeMap<String, String>>();
				for(XMLData data:xmldata){
					map = new TreeMap<String, String>();
					for(String cataName:categoriesNames){
						if(setName.equals(data.getDatasetName())&&cataName.equals(data.getCategoryLable())){
							map.put(cataName, data.getValue());
						}
					}
					listMap.add(map);	
				}
				chatDateMap.put(setName, listMap);
			}
		//构建Chart数据	
			int colorInt = 0;
			String tempSetvalue;
			//添加一个categories;
			for(String cname:categoriesNames){
				categories.add(new Category(cname));
			}
			//添加一个dataset
			for(String datasetName:datasetNames){ 
				setvalue = new ArrayList<SetValue>();
				//设置有值的 categories对应的set
				for(String cname:categoriesNames){
					String hasValueCname = "";  //有值的categories
					ArrayList<TreeMap<String,String>> catAndsetList = chatDateMap.get(datasetName);  //categories与set值的对应关系
					for(TreeMap<String,String> catAndset:catAndsetList){
						tempSetvalue = catAndset.get(cname);
						if(tempSetvalue!=null){
							hasValueCname = cname;
							setvalue.add(new SetValue(tempSetvalue));//设置一个List下 categories 与 setvalue的对应值
						}
					}
					
				//设置没有有值的 categories对应的set   设为""
					if(!hasValueCname.equals(cname))
						setvalue.add(new SetValue(""));
				}
				dataset.add(new Dataset(datasetName,colors[colorInt++],setvalue));
			}
			
			
		Chart chart  = new  Chart(title,numberSuf,showValues,categories,dataset);
		//设置宽度
		width = span*categories.size();
		
		DataProvider dp = new DataProvider();
		
		return dp.getXML(chart,FusionChartType.BAR );
	}

	/**
	 * 返回柱状图XML 自定义图注字段 
	 * @param sql
	 * @param statisticsFields 统计结果字段值需要在图像上显示的别名 按顺序对应
	 * @return
	 */
	public static String getSimpleBarXML(String sql,String[] statisticsFields){
		List<Category> categories = new ArrayList<Category>();
		List<Dataset> dataset = new ArrayList<Dataset>();
		List<SetValue> setvalue;
		
		FusionChartDBData fcdata = new FusionChartDBData();
		List<XMLData> xmldata = fcdata.getSimpleDBXMLData(sql, statisticsFields);
		if(xmldata==null||xmldata.size()==0)
			return null;
		if(statisticsFields.length>colors.length)
			throw new RuntimeException("颜色种类少于统计基础类别数量，请添加更多的颜色");
		//封装数据
		int colorInt = 0;
		//添加一个categories;
		for(XMLData xmld:xmldata){
				categories.add(new Category(xmld.getCategoryLable()));
		}
		//添加一个dataset
		for(int i=0,k=statisticsFields.length;i<k;i++){
			setvalue = new ArrayList<SetValue>();
			for(XMLData xmld:xmldata){
				setvalue.add(new SetValue(xmld.getValues()[i]));
			}
			dataset.add(new Dataset(statisticsFields[i],colors[colorInt++],setvalue));
		}
		Chart chart  = new  Chart(title,numberSuf, showValues,categories,dataset);
		//设置宽度
		width = span*categories.size();
		DataProvider dp = new DataProvider();
		return dp.getXML(chart,FusionChartType.BAR );
	}
	/**
	 * 设置图形颜色
	 * @param colors
	 */
	public static void setColors(String[] colors) {
		BuildFusionChartXml.colors = colors;
	}

	/**
	 * 设置 Y轴 数字后缀
	 * @param numberSuf
	 */
	public static void setNumberSuf(String numberSuf) {
		BuildFusionChartXml.numberSuf = numberSuf;
	}
	/**
	 * 设置 柱状图 间距
	 * @param span
	 */
	public static void setSpan(int span) {
		BuildFusionChartXml.span = span;
	}
	/**
	 * 图像 总长度
	 * @return
	 */
	public static int getWidth() {
		if(width<600)
			width=600;
		return width;
	}
	/**
	 * 设置 标题
	 * @param title
	 */
	public static void setTitle(String title) {
		if(MyUtil.isEmpty(title))
			title = "";
		BuildFusionChartXml.title = title;
	}

	public static void setShowValues(int showValues) {
		BuildFusionChartXml.showValues = showValues;
	}
	
}
