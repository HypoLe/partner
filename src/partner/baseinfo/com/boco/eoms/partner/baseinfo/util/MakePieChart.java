package com.boco.eoms.partner.baseinfo.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.boco.eoms.common.util.StaticMethod;

public class MakePieChart {
		
	public static String getPieChart(String title,List datalist,String tempPath,String jpgName) throws IOException{	
		DefaultPieDataset data = new DefaultPieDataset();
		String[] dataStrs = new String[2];
		String configPath =  MakePieChart.class.getResource("").toString();
		System.out.println("sPath==="+configPath);
		configPath = configPath.substring(5,configPath.indexOf("WEB-INF"))+tempPath;
		System.out.println("sPath==="+configPath);
		File uploadDir = new File(configPath); // 文件缓冲区
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		for(int i=0;i<datalist.size();i++){
			dataStrs = (String[])datalist.get(i);
			data.setValue(dataStrs[0], StaticMethod.nullObject2Long(dataStrs[1], 0));
		}
		JFreeChart chart = ChartFactory.createPieChart(title,  // 图表标题
		data, 
		true, // 是否显示图例
		false,
		false
		);
//		Util.setCategoryPlot(chart.getCategoryPlot()); //饼图没有网格线
		PiePlot pieplot = (PiePlot)chart.getPlot();
        pieplot.setLabelFont(new Font("宋体", 0, 12));
        pieplot.setNoDataMessage("无数据");
        pieplot.setCircular(true);
        pieplot.setLabelGap(0.02D);
        pieplot.setBackgroundPaint(new Color(199,237,204));

        pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} {1} {2}",
                NumberFormat.getNumberInstance(),
                new DecimalFormat("(0.00%)")));
                
		//写图表对象到文件，参照柱状图生成源码
		FileOutputStream fos_jpg = null;
		
		try {
			fos_jpg = new FileOutputStream(configPath+jpgName);
			ChartUtilities.writeChartAsJPEG(fos_jpg,1.0f,chart,600,450,null);
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {}
		}
		return jpgName;
	}
	/**
	 * 获取一个演示用的简单数据集对象
	 * @return
	 */
	private static DefaultPieDataset getDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("苹果",100);
		dataset.setValue("梨子",200);
		dataset.setValue("葡萄",300);
		dataset.setValue("香蕉",400);
		dataset.setValue("荔枝",500);
		return dataset;
	}
}

