package com.boco.eoms.partner.property.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;
import org.joda.time.format.DateTimeFormat;

import utils.PartnerPrivUtils;

import clover.org.apache.velocity.runtime.parser.node.p;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.property.dao.IPnrRentBillsDao;
import com.boco.eoms.partner.property.model.PnrRentBills;
import com.boco.eoms.partner.property.service.IPnrRentBillsService;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementConstant;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSFileImport;

/**
 * 类说明：物业合同管理-租赁费用
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:47
 */
public class PnrRentBillsServiceImpl extends CommonGenericServiceImpl<PnrRentBills>
		implements IPnrRentBillsService {
	private IPnrRentBillsDao pnrRentBillsDao;
	
	public void setPnrRentBillsDao(IPnrRentBillsDao pnrRentBillsDao) {
		this.setCommonGenericDao(pnrRentBillsDao);
	}
	public IPnrRentBillsDao getIPnrRentBillsDao() {
		return this.pnrRentBillsDao;
	}
	public ImportResult importFromFile(FormFile formFile) throws Exception {
		XLSFileImport xlsFileImport=new XLSFileImport(){
			public boolean doSaveRow2Data(HSSFRow row) throws Exception {
				PnrRentBills pnrRentBills=new PnrRentBills();
				pnrRentBills=validateEachRow(row);
				save(pnrRentBills);
				return true;
			}
			public XLSModel getXLSModel() {
				//上传excel模板的数据区域规格
				XLSModel xlsModel=new XLSModel(2,1,17,0,0,0,0,0,0);
				return xlsModel;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile);
		return result;
	}
	protected PnrRentBills validateEachRow(HSSFRow row) throws Exception {
		PnrRentBills pnrRentBills=new PnrRentBills();
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		//关联租赁合同的合同编号
		pnrRentBills.setRelatedAgreementNo(xlsUtil.checkIsNull(row, 1));
		//关联租赁合同的合同名称
		pnrRentBills.setRelatedAgreementName(xlsUtil.checkIsNull(row, 2));
		//关联租赁合同的甲方
		pnrRentBills.setRelatedParta(xlsUtil.nullCell2String(row, 3));
		//关联租赁合同的乙方
		pnrRentBills.setRelatedPartb(xlsUtil.checkIsNull(row, 4));
		//关联租赁合同的合同类型
		//pnrRentBills.setRelatedAgreementType(xlsUtil.name2Id(row, 5, "12501"));
		pnrRentBills.setRelatedAgreementType(xlsUtil.checkDictName(row, 5, "12501", false));
		//关联租赁合同的所属行政区
		//pnrRentBills.setRelatedDistrict(xlsUtil.checkIsNull(row, 6));
		String areaId=xlsUtil.checkAndGetAreaId(row, 6);
		String provinceId="";
		String cityId="";
		if (areaId.length()==PartnerPrivUtils.AreaId_length_County) {
			cityId=areaId.substring(0, PartnerPrivUtils.AreaId_length_City);
			provinceId=areaId.substring(0,PartnerPrivUtils.AreaId_length_Province);
		}else if (areaId.length()==PartnerPrivUtils.AreaId_length_City) {
			cityId=areaId;
			provinceId=areaId.substring(0, PartnerPrivUtils.AreaId_length_Province);
		}else {
			cityId=areaId;
			provinceId=areaId;
		}
		pnrRentBills.setProvince(provinceId);
		pnrRentBills.setCity(cityId);
		pnrRentBills.setRelatedDistrict(areaId);
		//关联租赁合同的支付周期
		//pnrRentBills.setPayCircle(xlsUtil.name2Id(row, 7, "12502"));
		pnrRentBills.setPayCircle(xlsUtil.checkDictName(row, 7, "12502", false));
		//结算时间段
		pnrRentBills.setSettlementTimeSectStart(xlsUtil.toEomsStanderDate(row, 8));
		//结算时间段
		pnrRentBills.setSettlementTimeSectEnd(xlsUtil.toEomsStanderDate(row, 9));
		//应付金额，excel文件单元格格式必须为数值
		pnrRentBills.setMustPayMoney(xlsUtil.checkIsNumeric(row, 10));
		//已付金额，excel文件单元格格式必须为数值
		pnrRentBills.setPaidMoney(xlsUtil.checkIsNumeric(row, 11));
		//未付金额，excel文件单元格格式必须为数值
		pnrRentBills.setUnpaidMoney(xlsUtil.checkIsNumeric(row, 12));
		//支付方式：现金、转账
		//pnrRentBills.setPayWay(xlsUtil.name2Id(row, 13, "12503"));
		pnrRentBills.setPayWay(xlsUtil.checkDictName(row, 13, "12503", false));
		//结算时间
		pnrRentBills.setSettlementDate(xlsUtil.toEomsStanderDate(row, 14));
		//办理相关费用的人员
		pnrRentBills.setManager(xlsUtil.checkIsNull(row, 15));
		//提醒对象，可以为空
		pnrRentBills.setRemindObject(xlsUtil.nullCell2String(row, 16));
		//备注，可以为空
		pnrRentBills.setRemark(xlsUtil.nullCell2String(row, 17));
		//附件名称
		pnrRentBills.setAttachment("");
		//租赁费用记录创建时间
		pnrRentBills.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		//支付状态：无需支付、待支付、已经支付、逾期未支付，设置为已经支付。
		pnrRentBills.setPayStatus(PnrPropertyAgreementConstant.HAVEPAID);
		//关联的合同的id，单独添加的合同无法关联，所以置为空
		pnrRentBills.setRefId("");
		//计划支付日期，单独添加的不在计划内，置为今日
		pnrRentBills.setPlanPayDate(new Date());
		return pnrRentBills;
	}
}
