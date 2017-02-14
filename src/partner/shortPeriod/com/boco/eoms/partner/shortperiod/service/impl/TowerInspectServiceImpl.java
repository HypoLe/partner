package com.boco.eoms.partner.shortperiod.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.shortperiod.dao.ITowerInspectDao;
import com.boco.eoms.partner.shortperiod.dao.ITowerInspectJDBCDao;
import com.boco.eoms.partner.shortperiod.model.BackTower;
import com.boco.eoms.partner.shortperiod.po.TowerModel;
import com.boco.eoms.partner.shortperiod.po.TowerQueryConditionModel;
import com.boco.eoms.partner.shortperiod.service.ITowerInspectService;

public class TowerInspectServiceImpl extends
		CommonGenericServiceImpl<BackTower> implements ITowerInspectService {
	private ITowerInspectDao towerInspectDao;

	private ITowerInspectJDBCDao towerInspectJDBCDao;

	public ITowerInspectDao getTowerInspectDao() {
		return towerInspectDao;
	}

	public void setTowerInspectDao(ITowerInspectDao towerInspectDao) {
		this.towerInspectDao = towerInspectDao;
		this.setCommonGenericDao(towerInspectDao);
	}

	public ITowerInspectJDBCDao getTowerInspectJDBCDao() {
		return towerInspectJDBCDao;
	}

	public void setTowerInspectJDBCDao(ITowerInspectJDBCDao towerInspectJDBCDao) {
		this.towerInspectJDBCDao = towerInspectJDBCDao;
	}

	public int getTowerCount(String userId,TowerQueryConditionModel towerQueryConditionModel) {
		return towerInspectJDBCDao.getTowerCount(userId,towerQueryConditionModel);
	}

	public List<TowerModel> getTowerList(String userId,TowerQueryConditionModel towerQueryConditionModel,
			int firstResult, int endResult, int pageSize) {
		return towerInspectJDBCDao
				.getTowerList(userId,towerQueryConditionModel,firstResult, endResult, pageSize);
	}

	// 导出详情
	public HSSFWorkbook exportTowerList(String userId,TowerQueryConditionModel towerQueryConditionModel) {
		HSSFWorkbook wb = new HSSFWorkbook();
		// -------------创建sheet页--------------
		String sheetName0 = "铁塔巡检";
		HSSFSheet sheet0 = wb.createSheet(sheetName0);

		// -------------创建表头------------------
		List<String> header0 = Arrays.asList(new String[] { "产品业务确认单编号",
				"站点名称(原始)", "地市", "区县", "站址编码", "需求确认单编号", "产品类型(原始)",
				"机房类型(原始)", "产品单元数1(原始)", "对应实际最高天线挂高1(原始)",
				"BBU是否放在铁塔公司机房1(原始)", "品单元数2(原始)", "实际最高天线挂高2(原始)",
				"是否放在铁塔公司机房2(原始)", "产品单元数3(原始)", "实际最高天线挂高3(原始)",
				"BBU是否放在铁塔公司机房3(原始)", "期末铁塔共享用户数(原始)", "期末机房共享用户数(原始)",
				"配套共享用户数(原始)", "场地费共享用户数(原始)", "电力引入费共享用户数(原始)",
				"维护费共享用户数(原始)", "站点名称(核查)", "产品类型(核查)", "机房类型(核查)",
				"产品单元数1(核查)", "对应实际最高天线挂高1(核查)", "BBU是否放在铁塔公司机房1(核查)",
				"产品单元数2(核查)", "实际最高天线挂高2(核查)", "BBU是否放在铁塔公司机房2(核查)",
				"产品单元数3(核查)", "实际最高天线挂高3(核查)", "BBU是否放在铁塔公司机房3(核查)",
				"期末铁塔共享用户数(核查)", "期末机房共享用户数(核查)", "配套共享用户数(核查)",
				"维护费共享用户数(核查)", "场地费共享用户数(核查)", "电力引入费共享用户数(核查)" });

		HSSFRow row0_0 = sheet0.createRow(0);
		for (int i = 0; i < header0.size(); i++) {
			HSSFCell cell = row0_0.createCell((short) i);
			cell.setCellValue(new HSSFRichTextString(header0.get(i)));
		}

		// -------------向第一个列表sheet0页写入数据------------------
		List<TowerModel> list0 = this.getTowerList(userId,towerQueryConditionModel,0, 1, 65530);
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		int rowNum0 = 1; // sheet0真实数据开始的行数
		if (list0 != null && list0.size() > 0) {
			for (TowerModel model0 : list0) {
				int colNum = 0;
				HSSFRow row = sheet0.createRow(rowNum0);
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getProductNum()));// 产品业务确认单编号
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getResName()));// 站点名称(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getCityName()));// 地市
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getCountryName()));// 区县
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNameId()));// 站址编码
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNameNumber()));// 需求确认单编号

				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA2()));// 产品类型(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA3()));// 机房类型(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA4()));// 产品单元数1(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA5()));// 对应实际最高天线挂高1(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA6()));// BBU是否放在铁塔公司机房1(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA8()));// 产品单元数2(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA9()));// 实际最高天线挂高2(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA10()));// 是否放在铁塔公司机房2(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA12()));// 产品单元数3(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA13()));// 实际最高天线挂高3(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA14()));// BBU是否放在铁塔公司机房3(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA16()));// 期末铁塔共享用户数(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA21()));// 期末机房共享用户数(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA26()));// 配套共享用户数(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA35()));// 场地费共享用户数(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA41()));// 电力引入费共享用户数(原始)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getA48()));// 维护费共享用户数(原始)

				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewResName()));// 站点名称(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(service.id2Name(model0.getNewa2(),"ItawSystemDictTypeDao")));// 产品类型(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(service.id2Name(model0.getNewa3(),"ItawSystemDictTypeDao")));// 机房类型(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(service.id2Name(model0.getNewa4(),"ItawSystemDictTypeDao")));// 产品单元数1(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(service.id2Name(model0.getNewa5(),"ItawSystemDictTypeDao")));// 对应实际最高天线挂高1(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(service.id2Name(model0.getNewa6(),"ItawSystemDictTypeDao")));// BBU是否放在铁塔公司机房1(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa8()));// 产品单元数2(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa9()));// 实际最高天线挂高2(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa10()));// BBU是否放在铁塔公司机房2(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa12()));// 产品单元数3(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa13()));// 实际最高天线挂高3(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa14()));// BBU是否放在铁塔公司机房3(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa16()));// 期末铁塔共享用户数(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa21()));// 期末机房共享用户数(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa26()));// 配套共享用户数(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa31()));// 维护费共享用户数(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa36()));// 场地费共享用户数(核查)
				row.createCell((short) colNum++).setCellValue(
						new HSSFRichTextString(model0.getNewa41()));// 电力引入费共享用户数(核查)
				rowNum0++;
			}
		} else {
			HSSFRow row0_1 = sheet0.createRow(1);
			HSSFCell cell = row0_1.createCell((short) 0);
			cell.setCellValue(new HSSFRichTextString("没有要导出的数据！"));
		}
		return wb;
	}
}
