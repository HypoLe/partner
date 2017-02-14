package com.boco.eoms.commons.mms.base.custom.chart;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.boco.eoms.commons.statistic.base.config.graphic.GraphicReport;

public interface ICustomChart {

	/**
	 * 开发人员自定义Jfreechart图片.
	 * @param list ICustomData接口返回值数据模型
	 * @param configig 是配置在excel样式配置文件的"图形报表配置"的映射
	 * @param type 是图形的类型
	 * @return 图片流(可以参考com.boco.eoms.commons.mms.base.util.JFreeCharCreater.java)
	 */
	public ByteArrayOutputStream getCustomChart(List list,GraphicReport configig,String type);
}
