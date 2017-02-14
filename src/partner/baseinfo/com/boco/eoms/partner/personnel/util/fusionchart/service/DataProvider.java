package com.boco.eoms.partner.personnel.util.fusionchart.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.boco.eoms.partner.personnel.util.fusionchart.FusionChartType;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.Category;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.Chart;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.Dataset;
import com.boco.eoms.partner.personnel.util.fusionchart.bean.SetValue;
import com.thoughtworks.xstream.XStream;
/**
 * <p>
 * Title:
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
public class DataProvider {
	
	public String getXML(Chart chart,String fusionType){
		return convertXML(chart2XML(chart,fusionType));
	}
	
	
	/**
	 * chart对象 转换成 xml
	 * @param chart
	 * @param type 柱状图,饼图 类型
	 * @return
	 */	
	private String chart2XML(Chart chart,String type) {
		XStream xstream = new XStream();
		xstream.alias("chart", Chart.class);
		xstream.addImplicitCollection(Chart.class, "dataset");
		xstream.useAttributeFor(Chart.class, "numberSuffix");
		xstream.useAttributeFor(Chart.class, "palette");
		xstream.useAttributeFor(Chart.class, "lineThickness");
		xstream.useAttributeFor(Chart.class, "showValues");
		xstream.useAttributeFor(Chart.class, "labelDisplay");
		xstream.useAttributeFor(Chart.class, "slantLabels");
		xstream.useAttributeFor(Chart.class, "areaOverColumns");
		xstream.useAttributeFor(Chart.class, "useRoundEdges");
		xstream.useAttributeFor(Chart.class, "caption");
		xstream.useAttributeFor(Chart.class, "legendPosition");
		xstream.useAttributeFor(Chart.class, "baseFontSize");
	
	if(type.equals(FusionChartType.BAR)){	
		
		xstream.alias("category", Category.class);
		xstream.useAttributeFor(Category.class, "label");
		
		xstream.alias("dataset", Dataset.class);
		xstream.addImplicitCollection(Dataset.class, "set");
		xstream.useAttributeFor(Dataset.class, "seriesName");
		xstream.useAttributeFor(Dataset.class, "color");
		xstream.useAttributeFor(Dataset.class, "showValue");
		xstream.useAttributeFor(Dataset.class, "lineThickness");
		xstream.useAttributeFor(Dataset.class, "yaxismaxvalue");
		xstream.useAttributeFor(Dataset.class, "parentYAxis");
		xstream.useAttributeFor(Dataset.class, "anchorSides");
		xstream.useAttributeFor(Dataset.class, "anchorRadius");
		xstream.useAttributeFor(Dataset.class, "anchorAlpha");
		
	}	
		xstream.alias("set", SetValue.class);
	if(type.equals(FusionChartType.PIE)){
		xstream.useAttributeFor(SetValue.class, "name");
	}
		xstream.useAttributeFor(SetValue.class, "value");
		
		return xstream.toXML(chart);
	}
	/**
	 * 格式化 xml
	 * @param src
	 * @return
	 */
	private String convertXML(String src) {
		//" 替换为 '
		Pattern p = Pattern.compile("\"");
		Matcher m = p.matcher(src);
		String tmp = m.replaceAll("'");
		//移除 >和 < 之间的空格
		p = Pattern.compile(">\\s+<");
		m = p.matcher(tmp);
		tmp = m.replaceAll("><");
		// ; 替换为 十六进制的换行标识 &#xD;
		p = Pattern.compile(";");
		m = p.matcher(tmp);
		tmp = m.replaceAll("&#xD;");
		return tmp;
	}
}