package com.boco.eoms.partner.baseinfo.util;

import java.util.List;
import java.util.Random;

import com.boco.eoms.partner.baseinfo.model.FusionChart;
import com.boco.eoms.partner.baseinfo.model.FusionChartData;


public class FusionChartMethod {
	/**
	 * @see 根据传来的list和数据类型得到对应生成图表的xml
	 * @param para list 数据集合
	 * @return String 标准的xml的标准格式的串
	 */
	public static String getString(List dataList,FusionChart fusionChart) {
		StringBuffer strXML = new StringBuffer();
		strXML.append("<chart bgColor='c9defb' baseFontSize='12' numdivlines='4' palette='2' formatNumberScale='0' ");
		if(fusionChart.getCaption()!=null){
			strXML.append("caption='"+fusionChart.getCaption()+"' ");
		}
		if(fusionChart.getSlantLabels()!=null){
			strXML.append("slantLabels='"+fusionChart.getSlantLabels()+"' ");
		}
		if(fusionChart.getLabelDisplay()!=null){
			strXML.append("labelDisplay='"+fusionChart.getLabelDisplay()+"' ");
		}
		if(fusionChart.getSubCaption()!=null){
			strXML.append("subCaption='"+fusionChart.getSubCaption()+"' ");
		}
		if(fusionChart.getXAxisName()!=null){
			strXML.append("xAxisName='"+fusionChart.getXAxisName()+"' ");
		}
		if(fusionChart.getYAxisName()!=null){
			strXML.append("yAxisName='"+fusionChart.getYAxisName()+"' ");
		}
		if(fusionChart.getShowNames()!=null){
			strXML.append("showNames='"+fusionChart.getShowNames()+"' ");
		}
		if(fusionChart.getRotateNames()!=null){
			strXML.append("rotateNames='"+fusionChart.getRotateNames()+"' ");
		}
		if(fusionChart.getShowValues()!=null){
			strXML.append("showValues='"+fusionChart.getShowValues()+"' ");
		}
		if(fusionChart.getYAxisMinValue()!=null){
			strXML.append("yAxisMinValue='"+fusionChart.getYAxisMinValue()+"' ");
		}
		if(fusionChart.getYAxisMaxValue()!=null){
			strXML.append("yAxisMaxValue='"+fusionChart.getYAxisMaxValue()+"' ");
		}
		if(fusionChart.getShowLimits()!=null){
			strXML.append("showLimits='"+fusionChart.getShowLimits()+"' ");
		}
		strXML.append(">");
		FusionChartData fusionChartData= null;
			for(int i=0;i<dataList.size();i++){
				fusionChartData = (FusionChartData)dataList.get(i);
				if(fusionChartData.getNumForInt()!=null){
					strXML.append("<set label='" + fusionChartData.getTitle() + "' value='" +fusionChartData.getNumForInt()+ "' color ='"+changeColor()+"'");
				}else{
					strXML.append("<set label='" + fusionChartData.getTitle() + "' value='" +fusionChartData.getNumForFloat()+ "' color ='"+changeColor()+"'");
				}
				if(fusionChartData.getUrl()!=null){
					strXML.append("link='"+fusionChartData.getUrl()+"' ");
				}
				strXML.append("/>");
			}
		strXML.append("</chart>");
		return strXML.toString();
	}
	/**
	 * 获得随机的颜色
	 * 
	 * @param value
	 * @return
	 */
	public static String changeColor() {
		Random r = new Random();
        int i = 0;
        String str="";
        String s = "";
        while(i<6)
        {
            switch(r.nextInt(15))
            {
                case(0):s="0";break;
                case(1):s="1";break;
                case(2):s="2";break;
                case(3):s="3";break;
                case(4):s="4";break;
                case(5):s="5";break;
                case(6):s="6";break;
                case(7):s="7";break;
                case(8):s="8";break;
                case(9):s="9";break;
                case(10):s="A";break;
                case(11):s="B";break;
                case(12):s="C";break;
                case(13):s="D";break;
                case(14):s="E";break;
                case(15):s="F";break;
            }
            i++;
            str=s+str;
        }
        return str;
	}
}
