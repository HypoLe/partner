package com.boco.eoms.partner.property.service.impl;

import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import utils.PartnerPrivUtils;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.property.dao.IPnrElectricityBillsDao;
import com.boco.eoms.partner.property.model.PnrElectricityBills;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsService;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementConstant;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSFileImport;

/**
 * 类说明：物业合同管理-电费费用记录
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:46
 */
public class PnrElectricityBillsServiceImpl extends CommonGenericServiceImpl<PnrElectricityBills>
		implements IPnrElectricityBillsService {
	private IPnrElectricityBillsDao pnrElectricityBillsDao;
	
	public void setPnrElectricityBillsDao(IPnrElectricityBillsDao pnrElectricityBillsDao) {
		this.setCommonGenericDao(pnrElectricityBillsDao);
		this.pnrElectricityBillsDao = pnrElectricityBillsDao;
	}
	public IPnrElectricityBillsDao getIPnrElectricityBillsDao() {
		return this.pnrElectricityBillsDao;
	}
	public ImportResult importFromFile(FormFile formFile) throws Exception {
		XLSFileImport xlsFileImport=new XLSFileImport(){
			public boolean doSaveRow2Data(HSSFRow row) throws Exception {
				PnrElectricityBills pnrElectricityBills=new PnrElectricityBills();
				pnrElectricityBills=validateEachRow(row);
				save(pnrElectricityBills);
				return true;
			}
			public XLSModel getXLSModel() {
				XLSModel xlsModel=new XLSModel(2,1,20,0,0,0,0,0,0);
				return xlsModel;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile);
		return result;
	}
	
	protected PnrElectricityBills validateEachRow(HSSFRow row) throws Exception {
		PnrElectricityBills pnrElectricityBills=new PnrElectricityBills();
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		//关联租赁合同的合同编号
		pnrElectricityBills.setRelatedAgreementNo(xlsUtil.checkIsNull(row, 1));
		//关联租赁合同的合同名称
		pnrElectricityBills.setRelatedAgreementName(xlsUtil.checkIsNull(row, 2));
		//关联租赁合同的合同甲方
		pnrElectricityBills.setRelatedParta(xlsUtil.nullCell2String(row, 3));
		//关联租赁合同的合同乙方
		pnrElectricityBills.setRelatedPartb(xlsUtil.checkIsNull(row, 4));
		//关联租赁合同的合同类型
		//pnrElectricityBills.setRelatedAgreementType(xlsUtil.name2Id(row, 5, "12501"));
		pnrElectricityBills.setRelatedAgreementType(xlsUtil.checkDictName(row, 5, "12501", false));
		//关联租赁合同的所属行政区域
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
		pnrElectricityBills.setProvince(provinceId);
		pnrElectricityBills.setCity(cityId);
		pnrElectricityBills.setRelatedDistrict(areaId);
		
		
		//支付周期
		//pnrElectricityBills.setPayCircle(xlsUtil.name2Id(row, 7, "12502"));
		pnrElectricityBills.setPayCircle(xlsUtil.checkDictName(row, 7, "12502", false));
		//结算时间段开始时间
		pnrElectricityBills.setSettlementTimeSectStart(xlsUtil.toEomsStanderDate(row, 8));
		//结算时间段终止时间
		pnrElectricityBills.setSettlementTimeSectEnd(xlsUtil.toEomsStanderDate(row, 9));
		//电表上次读数
		pnrElectricityBills.setLastNum(xlsUtil.checkIsNumeric(row, 10));
		//电表本次读数
		pnrElectricityBills.setNowNum(xlsUtil.checkIsNumeric(row, 11));
		//电费单价
		pnrElectricityBills.setUnivalency(xlsUtil.checkIsNumeric(row, 12));
		//应付金额，excel单元格格式为数值
		pnrElectricityBills.setMustPayMoney(xlsUtil.checkIsNumeric(row, 13));
		//已付金额，excel单元格格式为数值
		pnrElectricityBills.setPaidMoney(xlsUtil.checkIsNumeric(row, 14));
		//未付金额，excel单元格格式为数值
		pnrElectricityBills.setUnpaidMoney(xlsUtil.checkIsNumeric(row, 15));
		//支付方式
		//pnrElectricityBills.setPayWay(xlsUtil.name2Id(row, 16, "12503")); 
		pnrElectricityBills.setPayWay(xlsUtil.checkDictName(row, 16, "12503", false)); 
		//结算日期
		pnrElectricityBills.setSettlementDate(xlsUtil.toEomsStanderDate(row, 17));
		//办理费用的经手人
		pnrElectricityBills.setManager(xlsUtil.checkIsNull(row, 18));
		//提醒对象
		pnrElectricityBills.setRemindObject(xlsUtil.nullCell2String(row, 19));
		//记录创建时间
		pnrElectricityBills.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		//备注
		pnrElectricityBills.setRemark(xlsUtil.nullCell2String(row, 20));
		//附件
		pnrElectricityBills.setAttachment("");
		//关联的合同id
		pnrElectricityBills.setRefId("");
		//支付状态为已经支付
		pnrElectricityBills.setPayStatus(PnrPropertyAgreementConstant.HAVEPAID);
		//计划支付日期
		pnrElectricityBills.setPlanPayDate(new Date());
		return pnrElectricityBills;
	}
}
