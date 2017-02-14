package com.boco.eoms.commons.mms.scheduler;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.mms.base.config.Foot;
import com.boco.eoms.commons.mms.base.config.Reports;
import com.boco.eoms.commons.mms.base.custom.chart.ICustomChart;
import com.boco.eoms.commons.mms.base.custom.data.ICustomData;
import com.boco.eoms.commons.mms.base.foot.IFootInfo;
import com.boco.eoms.commons.mms.base.util.Display;
import com.boco.eoms.commons.mms.base.util.ExcelToPic;
import com.boco.eoms.commons.mms.base.util.JFreeCharCreater;
import com.boco.eoms.commons.mms.base.util.MMSConstants;
import com.boco.eoms.commons.mms.base.util.ParseXML2Obj;
import com.boco.eoms.commons.mms.mmsreport.mgr.MmsreportMgr;
import com.boco.eoms.commons.mms.mmsreport.model.Mmsreport;
import com.boco.eoms.commons.mms.mmsreporttemplate.mgr.MmsreportTemplateMgr;
import com.boco.eoms.commons.mms.mmsreporttemplate.model.MmsreportTemplate;
import com.boco.eoms.commons.mms.msssubscribe.mgr.MsssubscribeMgr;
import com.boco.eoms.commons.mms.msssubscribe.model.Msssubscribe;
import com.boco.eoms.commons.mms.statreport.mgr.StatreportMgr;
import com.boco.eoms.commons.mms.statreport.model.Statreport;
import com.boco.eoms.commons.statistic.base.config.excel.Excel;
import com.boco.eoms.commons.statistic.base.config.excel.Sheet;
import com.boco.eoms.commons.statistic.base.config.graphic.GraphicConfig;
import com.boco.eoms.commons.statistic.base.config.graphic.GraphicReport;
import com.boco.eoms.commons.statistic.base.config.model.KpiConfig;
import com.boco.eoms.commons.statistic.base.config.model.KpiDefine;
import com.boco.eoms.commons.statistic.base.dao.impl.StatJdbcDAOImpl;
import com.boco.eoms.commons.statistic.base.mgr.impl.ExcelConverter;
import com.boco.eoms.commons.statistic.base.mgr.impl.ExportExcel;
import com.boco.eoms.commons.statistic.base.reference.ApplicationContextHolder;
import com.boco.eoms.commons.statistic.base.reference.ParseXmlService;
import com.boco.eoms.commons.statistic.base.util.ExcelConverterUtil;
import com.boco.eoms.commons.statistic.base.util.FileUtil;
import com.boco.eoms.commons.statistic.base.util.GraphicsReportUtil;
import com.boco.eoms.commons.statistic.base.util.StatUtil;
import com.boco.eoms.commons.statistic.customstat.util.CustomStatUtil;

public class MMSchedulerV35 implements Job{
	
	public Logger logger = Logger.getLogger(this.getClass());

	public void execute(JobExecutionContext context)
						throws org.quartz.JobExecutionException
	{
		try {
			//如果没有系统没有com.boco.eoms.base.util.ApplicationContextHolder的情况需要把InitStaticBaseApplicationContextServlet配置到web.xml中
			if (ApplicationContextHolder.getInstance().getCtx() == null) 
			{
				ApplicationContextHolder.getInstance().setCtx(com.boco.eoms.base.util.ApplicationContextHolder.getInstance().getCtx());
			}
			String JobName = "";
			if (context != null) {
				JobName = context.getJobDetail().getName();
			}
			logger.info("\n执行定制统计的JobName是 ：" + JobName);
			Calendar currtenCanlendar = Calendar.getInstance();// 开始执行统计时间
			
			//定制彩信报模板
			System.out.println("定制彩信报模板");
			MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr)ApplicationContextHolder.getInstance().getBean("mmsreportTemplateMgr");
			MmsreportTemplate mmsreportTemplate = mmsreportTemplateMgr.getMmsreportTemplateForSubId(JobName); 
			if(mmsreportTemplate == null)
			{
				return;
			}
			String executeCycle = mmsreportTemplate.getExecuteCycle();
			String[] statReportIds = mmsreportTemplate.getStatReportId().split(",");
			
			//彩信报实例
			System.out.println("彩信报实例");
			Mmsreport mmsreport = new Mmsreport();
			mmsreport.setMmsreport_template_id(mmsreportTemplate.getId());
			mmsreport.setMmsReportCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currtenCanlendar.getTime()));
			mmsreport.setMmsReportName(mmsreportTemplate.getMmsName() + "-" + CustomStatUtil.typeToName(executeCycle));
			mmsreport.setStatreport_id(mmsreportTemplate.getStatReportId());
			mmsreport.setMmsreportType(executeCycle);
			mmsreport.setUserid(mmsreportTemplate.getUserid());
			MmsreportMgr mmsreportMgr = (MmsreportMgr)ApplicationContextHolder.getInstance().getBean("mmsreportMgr");
			//保存彩信报信息
			mmsreportMgr.saveMmsreport(mmsreport);
			
			//按照定制的彩信报生成彩信报需要的所有报表
			System.out.println("按照定制的彩信报生成彩信报需要的所有报表");
			for(int i=0; i<statReportIds.length;i++)
			{
				//根据彩信报报表配置的id得到excel与算法配置路径
				String sheetId = statReportIds[i];
				Reports reports = (Reports) ParseXmlService.create().xml2object(Reports.class, StaticMethod.getFilePathForUrl(MMSConstants.REPORT_CONFIG));
				com.boco.eoms.commons.mms.base.config.Sheet sh = reports.getSheetById(sheetId);
				String excelPath = sh.getExcelPath();//"classpath:config/mms/report-config/commonfault-config/oracle/statistic-config-excel-commonfault_T_resolve_KPI4_oracle.xls";
				String quaryPath = sh.getQueryPath();//"classpath:config/mms/report-config/commonfault-config/oracle/statistic-config-query-commonfault_T_resolve_KPI4_oracle.xml";			
				String sheetIndex = sh.getIndex();//"0";
				int imageWidth = sh.getImageWidth();//图片宽度
				
				//解析算法配置文件查询数据库得到List
				ExcelConverter ec = new ExcelConverter();
				Excel excel = ec.parseExcelToConfig(StaticMethod.getFilePathForUrl(excelPath));
				Sheet sheet = excel.getSheetByIndex(sheetIndex);
				String kpiDefineName = sheet.getQueryName();
				
				//应该是从日报，月报，周报，的情况中取得时间点
				Map param = new HashMap();
				//param.put("endTime", "2008-12-03 15:16:16");
				//param.put("beginTime", "2006-12-03 15:16:16");
				Calendar beginCalendar = CustomStatUtil.getCalendar(executeCycle,
						(Calendar)StatUtil.CloneObject(currtenCanlendar));
				param.put("beginTime", new SimpleDateFormat("yyyy-MM-dd")
						.format(beginCalendar.getTime()) + " " + "00:00:00");// 统计开始时间
				param.put("endTime", new SimpleDateFormat("yyyy-MM-dd")
						.format(currtenCanlendar.getTime()) + " " + "00:00:00");// 统计结束时间
				
				List list = null;
				KpiDefine kpiDefine = null;
				String customDataClass = sh.getCustomDataClass();
				if(customDataClass == null || customDataClass.equalsIgnoreCase(""))
				{
					//没有自定义数据(使用统计工具查询数据库)
					KpiConfig kpiConfig = (KpiConfig)ParseXML2Obj.ParseXML2Obj(KpiConfig.class,StaticMethod.getFilePathForUrl(quaryPath));
					kpiDefine = kpiConfig.getConfigByKpiDefineName(kpiDefineName);
					String[] condSql = {""};
					StatJdbcDAOImpl sjdi = new StatJdbcDAOImpl();
					list = sjdi.getListKpiResult(kpiDefine, param, condSql, "123");
//					list = initList_guizhou();
				}
				else
				{
					//调用自己定义的数据类
					ICustomData iCustomData = (ICustomData)Class.forName(customDataClass).newInstance();
					list = iCustomData.getCustomData(param);
				}
				
//				UUIDHexGenerator uu = UUIDHexGenerator.getInstance();
//		        String uuid = uu.getID();
		        StatreportMgr statreportMgr = (StatreportMgr)ApplicationContextHolder.getInstance().getBean("statreportMgr");
		        Statreport statreport = new Statreport();
		        statreportMgr.saveStatreport(statreport);
		        String uuid = statreport.getId();
		        
		        String excelName = "";
		        String picfilename = "";
		        
				//绘制报表图片gif, stat(默认) 2维表格， column 柱图 ，line 线图 ,pie 饼图，customchart 自定义图形
				String gType = sh.getType();
				
				if(MMSConstants.TXT.equalsIgnoreCase(gType))
				{
					//文字
				}
				else
				{
					//根据模板excel与结果集List生成Excel
			        String realPath = FileUtil.getWEBURL(MMSConstants.REPORT_CONFIG) + MMSConstants.KEEP_REPORT_FILE_PATH;//"D:/";
			        //realPath = realPath.substring(1);//去掉绝对路径前的"/"
			        ExportExcel ee = new ExportExcel();
//					OutputStream ops = ee.ResultExportExcel(StaticMethod.getFilePathForUrl(excelPath), 
//							sheet.getSheetName(), list, param, kpiDefine);
					OutputStream ops = ee.ResultExportExcel(StaticMethod.getFilePathForUrl(excelPath), 
							sheet.getSheetName(), list, param);
					excelName = uuid + ".xls";//"llllll402881841f81e969011f82dcca07000820090217141202.xls";//"2008-12-05-11-57-42402881ef1e0a6dfe011e0a71932e000d.xls";//"test.xls";//"test.xls";//"402881861f7e0ec9011f7e1bb5e3000720090216160239.xls";
					String outexcelPath = realPath + excelName;//"D:/kao.xls";
					FileUtil.writeFile((ByteArrayOutputStream)ops,outexcelPath);
					
					String pictureFormat = mmsreportTemplate.getPictureFormat();
					
					picfilename = uuid + "." + "gif";//"gif";
					if(MMSConstants.STAT.equalsIgnoreCase(gType))
					{
						//报表 2维表格
						ExcelToPic etp = new ExcelToPic();
						int headNum = sheet.getTables()[0].getHead_end();
						int iw = imageWidth;
						etp.createRowsImage(realPath, picfilename, excelName,headNum,iw,Color.YELLOW);
						//由于excel文件比较大彩信报使用时间久了会浪费大量空间，所以在绘制完图片后将其删除
						FileUtil.deleteFile(outexcelPath);
						//System.out.println("删除excel文件");
					}
					else
					{
						GraphicConfig gc = (GraphicConfig)GraphicsReportUtil.xml2object(GraphicConfig.class, sheet.getGraphicConfig());
						GraphicReport gr= gc.getGraphicReports()[0];
						
						Display display = new Display();
						display.title = gr.getTitle();
						display.targetString = gr.getTargetString();
						display.valueString = gr.getValueString();
						display.numberFormat = gr.getNumberFormat();
						display.labelPositions = gr.getLabelPositions();
						display.itemLabelsVisible = gr.isItemLabelsVisible();
						display.width = gr.getWidth();
						display.height = gr.getHeight();
						display.fontSize = gr.getFontSize();
						display.quality = gr.getQuality();
						
						JFreeCharCreater jcc = new JFreeCharCreater();
	//						list = jcc.initList();
						ByteArrayOutputStream bos = null;
						if(MMSConstants.COLUMN.equalsIgnoreCase(gType))
						{
							//多指标-多住
	//						CategoryDataset[] dataset = new CategoryDataset[1];
	//						dataset[0] = jcc.CoverterList2CategoryDataset(list,gr);
	////						JFreeCharCreater.printDataSet(dataset);
	////						dataset[0] = jcc.getDataSet2();
							
							//单指标-多柱（时间作为X轴）
							CategoryDataset[] dataset = new CategoryDataset[1];
							if(gr.getFieldDefines().length == 1 && gr.getSummaryDefines().length >= 2)
							{
								dataset[0] = jcc.CoverterList2CategoryDatasetForSingleIndex(list,gr);
							}
							//多指标-多柱
							else
							{
								dataset[0] = jcc.CoverterList2CategoryDataset(list,gr);
							}
							bos = jcc.CreateChart(1,dataset,display);
						}
						else if(MMSConstants.LINE.equalsIgnoreCase(gType))
						{
							//单指标-多柱（时间作为X轴）
							CategoryDataset[] dataset = new CategoryDataset[1];
							if(gr.getFieldDefines().length == 1 && gr.getSummaryDefines().length >= 2)
							{
								dataset[0] = jcc.CoverterList2CategoryDatasetForSingleIndex(list,gr);
							}
							//多指标-多柱
							else
							{
								dataset[0] = jcc.CoverterList2CategoryDataset(list,gr);
							}
							bos = jcc.CreateChart(2,dataset,display);
						}
						else if(MMSConstants.PIE.equalsIgnoreCase(gType))
						{
							PieDataset[] dataset = new PieDataset[1];
							dataset[0] = jcc.CoverterList2PieDataset(list,gr);
							bos = jcc.CreateChart(3,dataset,display);
						}
						else if(MMSConstants.COLUMNLINE.equalsIgnoreCase(gType))
						{
							CategoryDataset datasetColumn = jcc.ConverterList2CategoryDatasetBarAndLine(list,gr,MMSConstants.COLUMN);
							CategoryDataset datasetLine = jcc.ConverterList2CategoryDatasetBarAndLine(list,gr,MMSConstants.LINE);
							CategoryDataset[] dataset = {datasetColumn,datasetLine};
							bos = jcc.CreateChart(4,dataset,display);
						}
						else if(MMSConstants.CUSTOMCHART.equalsIgnoreCase(gType))
						{
							//自定义柱图
							String customChartClass = sh.getCustomChartClass();
							ICustomChart iCustomChart = (ICustomChart)Class.forName(customChartClass).newInstance();
							bos = iCustomChart.getCustomChart(list, gr, MMSConstants.CUSTOMCHART);
							return;
						}
						else
						{
							return;
						}
						
						FileUtil.writeFile(bos, realPath+picfilename);
					}
				}
				
				//修改报表注释模板，根据注释模板替换相应位置的字符串$info$f1$info$
				Foot foot = sh.getFoot();
				String footString = "";
				if(foot != null && !"".equalsIgnoreCase(foot.getCls()))
				{
					footString = foot.getInfo();
					IFootInfo ob = (IFootInfo)Class.forName(foot.getCls()).newInstance();
					ob.setList(list);
					ob.setInfot(param);
					footString = ExcelConverterUtil.rep(ob.getInfo(),footString);
				}
				
				//保存到报表实例中
				//picUrl,excelUrl
				statreport.setMmsreport_template_id(mmsreportTemplate.getId());
				statreport.setUserid(mmsreportTemplate.getUserid());
				statreport.setMmsreport_id(mmsreport.getId());
				statreport.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currtenCanlendar.getTime()));
				statreport.setReportName(sh.getName());//报表名称
				statreport.setReportType(executeCycle);//报表类型 月，周，日
				statreport.setExcelID(excelName);//Excel报表url
				statreport.setPicID(picfilename);//图片url
				statreport.setFootInfo(footString);//报表脚注说明信息
//				statreport.setId(uuid);
				//StatreportMgr statreportMgr = (StatreportMgr)ApplicationContextHolder.getInstance().getBean("statreportMgr");
				
//				logger.info("保存 报表" + uuid);
				statreportMgr.saveStatreport(statreport);
			}
			
			//发送通知，通知该条彩信已经生成完毕
			//System.out.println("发送通知，通知该条彩信已经生成完毕");
			String sendStatus = notifyMessage(mmsreport);
			mmsreport.setSendStatus(sendStatus);
			mmsreportMgr.saveMmsreport(mmsreport);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("彩信报生成完毕");
	}
	
	private String notifyMessage(Mmsreport mmsreport) throws Exception
    {
    	//通知订阅模块，如果该报表被订阅则发送信息（彩信方式）
		MsssubscribeMgr mm = (MsssubscribeMgr)ApplicationContextHolder.getInstance().getBean("msssubscribeMgr");
		
		List list_msssubscribe = mm.getMmsreport(mmsreport);
		
		//彩信报发送状态
		String status = "未发送";
		if(list_msssubscribe != null)
		{
			for(int i=0;i<list_msssubscribe.size();i++)
			{
				Msssubscribe msssubscribe  = (Msssubscribe)list_msssubscribe.get(i);
				//发送彩信报
				logger.info("开始发送彩信报");
				msssubscribe.setMmreportName(mmsreport.getMmsReportName());
				status = mm.sendMmsreport(msssubscribe,mmsreport);
				mmsreport.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
				logger.info("发送彩信报：" + status);
			}
		}
		return status;
    }
	 
	/**
	 * @param args
	 * @throws JobExecutionException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws JobExecutionException, FileNotFoundException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//		new MMSchedulerV35().execute(null);
//		String realPath = "/D:/ddd.gif";
//		realPath = realPath.substring(1);
//		
//		FootInfo ob = (FootInfo)Class.forName("com.boco.eoms.commons.mms.commonfault.foot.CommonfaultT4FootInfo").newInstance();
//		System.out.println(ob.getInfo());
//		System.out.println(MMSConstants.MMSREPORT_FILE_RELATIVEPATH);
//		String cycle = "0 0 1 ? * SUN";
		String cycle = "0 0 2 "+ 15 +" * ?";
		//String cycle = "0 0 4 * * ?";
//		String x = cycle.substring(9);
		String x = cycle.substring(6,7);
		if(!" ".equalsIgnoreCase(cycle.substring(7,8)))
		{
			x = cycle.substring(6,8);
		}
		
		System.out.println(x);
		
		
	}
	
	public List initList_guizhou()
	{
		List list = new ArrayList();
		Map map1 = new HashMap();
		map1.put("ne_name", "贵州-黔西南");
		map1.put("tch_traffic", "11882.482");
		
		Map map2 = new HashMap();
		map2.put("ne_name", "贵州-安顺地区");
		map2.put("tch_traffic", "8591.089166666667");
		
		Map map3 = new HashMap();
		map3.put("ne_name", "贵州-贵阳市");
		map3.put("tch_traffic", "40642.15596392769");
		
		Map map4 = new HashMap();
		map4.put("ne_name", "贵州-毕节地区");
		map4.put("tch_traffic", "16699.903100000007");
		
		Map map5 = new HashMap();
		map5.put("ne_name", "贵州-六盘水市");
		map5.put("tch_traffic", "11327.775277777779");
		
		Map map6 = new HashMap();
		map6.put("ne_name", "贵州-遵义市");
		map6.put("tch_traffic", "29004.18222222222");
		
		Map map7 = new HashMap();
		map7.put("ne_name", "贵州-黔东南");
		map7.put("tch_traffic", "17440.983");
		
		Map map8 = new HashMap();
		map8.put("ne_name", "贵州-铜仁地区");
		map8.put("tch_traffic", "12472.894999999999");
		
		Map map9 = new HashMap();
		map9.put("ne_name", "贵州-黔南");
		map9.put("tch_traffic", "14572.659211111113");
		
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
		list.add(map7);
		list.add(map8);
		list.add(map9);
		
		return list;
	}

}
