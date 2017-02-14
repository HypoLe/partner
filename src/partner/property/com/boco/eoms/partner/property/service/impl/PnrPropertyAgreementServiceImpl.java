package com.boco.eoms.partner.property.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.util.StatisticMethod;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.property.dao.IPnrPropertyAgreementDao;
import com.boco.eoms.partner.property.model.PnrElectricityBills;
import com.boco.eoms.partner.property.model.PnrPropertyAgreement;
import com.boco.eoms.partner.property.model.PnrRentBills;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsService;
import com.boco.eoms.partner.property.service.IPnrPropertyAgreementService;
import com.boco.eoms.partner.property.service.IPnrRentBillsService;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementConstant;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementHandler;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSFileImport;

/**
 * 类说明：电费费用记录物业合同
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:47
 */
public class PnrPropertyAgreementServiceImpl extends CommonGenericServiceImpl<PnrPropertyAgreement>
		implements IPnrPropertyAgreementService {
	private IPnrPropertyAgreementDao pnrPropertyAgreementDao;
	
	public void setPnrPropertyAgreementDao(IPnrPropertyAgreementDao pnrPropertyAgreementDao) {
		this.setCommonGenericDao(pnrPropertyAgreementDao);
	}
	public IPnrPropertyAgreementDao getIPnrPropertyAgreementDao() {
		return this.pnrPropertyAgreementDao;
	}
	/**
	 * 管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile) throws Exception {
		XLSFileImport xlsFileImport=new XLSFileImport(){
			public XLSModel getXLSModel() {
				//获取上传excel文件的数据区域
				XLSModel xlsModel=new XLSModel(2,0,17,0,0,0,0,0,0);
				return xlsModel;
			}
			public boolean doSaveRow2Data(HSSFRow row) throws Exception {
				PnrPropertyAgreement pnrPropertyAgreement=new PnrPropertyAgreement();
				//验证当前行的每一列
				pnrPropertyAgreement=validateEachRow(row);
				save(pnrPropertyAgreement);
				IPnrElectricityBillsService pnrElectricityBillsService=(IPnrElectricityBillsService)ApplicationContextHolder.getInstance().getBean("pnrElectricityBillsService");
				IPnrRentBillsService pnrRentBillsService=(IPnrRentBillsService)ApplicationContextHolder.getInstance().getBean("pnrRentBillsService");
				PnrPropertyAgreementHandler agreementHandler=new PnrPropertyAgreementHandler();
				//生成电费/租赁费用支付计划
				List list=agreementHandler.producePayPlan(pnrPropertyAgreement);
				for (Object object : list) {
					if (object.getClass().getName().equals("com.boco.eoms.partner.property.model.PnrElectricityBills")) {
						pnrElectricityBillsService.save((PnrElectricityBills)object);
					}
					if (object.getClass().getName().equals("com.boco.eoms.partner.property.model.PnrRentBills")) {
						pnrRentBillsService.save((PnrRentBills)object);
					}
				}
				return true;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile);
		return result;
	}
	public PnrPropertyAgreement validateEachRow(HSSFRow row) throws Exception {
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		PnrPropertyAgreement pnrPropertyAgreement=new PnrPropertyAgreement();
		//合同编号
		pnrPropertyAgreement.setAgreementNo(xlsUtil.checkIsNull(row, 0));
		//合同所属站点
		pnrPropertyAgreement.setSite(xlsUtil.checkIsNull(row, 1));
		//合同名称
		pnrPropertyAgreement.setAgreementName(xlsUtil.checkIsNull(row, 2));
		//合同甲方，可以为空
		pnrPropertyAgreement.setParta(xlsUtil.nullCell2String(row, 3));
		//合同乙方
		pnrPropertyAgreement.setPartb(xlsUtil.checkIsNull(row, 4));
		//合同类型：租赁合同、电费合同
		//pnrPropertyAgreement.setAgreementType(xlsUtil.name2Id(row, 5, "12501"));
		pnrPropertyAgreement.setAgreementType(xlsUtil.checkDictName(row, 5, "12501",false));
		//验证：合同签订日期必须小于合同的截止日期
		xlsUtil.compareDate(row, 6, 7, "lessThan");
		//合同签订日期
		pnrPropertyAgreement.setSignDate(xlsUtil.toEomsStanderDate(row, 6));
		//合同截止日期
		pnrPropertyAgreement.setEndDate(xlsUtil.toEomsStanderDate(row, 7));
		//支付周期
		//pnrPropertyAgreement.setPayCycle(xlsUtil.name2Id(row, 8, "12502"));
		pnrPropertyAgreement.setPayCycle(xlsUtil.checkDictName(row, 8, "12502",false));
		//所属行政区域
		pnrPropertyAgreement.setDistirct(xlsUtil.checkAndGetAreaId(row, 9));//需要将区域name转换为id
		//甲方签订人，可以为空
		pnrPropertyAgreement.setPartaSigningPerson(xlsUtil.nullCell2String(row, 10));
		//甲方签订人电话，可以为空
		pnrPropertyAgreement.setPartaSigningPersonTel(xlsUtil.nullCell2String(row, 11));
		//乙方签订人，可以为空
		pnrPropertyAgreement.setPartbSigningPerson(xlsUtil.nullCell2String(row, 12));
		//乙方签订人电话，可以为空
		pnrPropertyAgreement.setPartbSigningPersonTel(xlsUtil.nullCell2String(row, 13));
		//合同金额，单元格格式必须为数值型
		String contactM=xlsUtil.checkNumber(row, 14, true);
		if ("".equals(contactM)) {
			contactM="0";
		}
		pnrPropertyAgreement.setAgreementAmount(Float.parseFloat(contactM));
		//提醒对象
		pnrPropertyAgreement.setRemindObject(xlsUtil.checkAndGetRemindObjectIdsAndPhones(row,15));
		//备注，可以为空
		pnrPropertyAgreement.setRemark(xlsUtil.nullCell2String(row, 16));
		//合同记录创建时间：当前时间
		pnrPropertyAgreement.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		//合同附件名称
		pnrPropertyAgreement.setAttachment("");
		//删除标志位"0"：不删除（添加字段）
		pnrPropertyAgreement.setDeletedFlag("0");
		//合同状态为：有效（添加字段）
		pnrPropertyAgreement.setAgreementStatus(PnrPropertyAgreementConstant.AGREEMENTEFFECTED);
		//合同到期提醒为：未到期提醒（添加字段）
		pnrPropertyAgreement.setExpireRemind(PnrPropertyAgreementConstant.AGREEMENTNOREMIND);
		//为城市和省份赋值
		String districtId=StaticMethod.null2String(pnrPropertyAgreement.getDistirct());
		String provinceId="";
		String cityId="";
		if (districtId.length()==6) {
			cityId=districtId.substring(0, 4);
			provinceId=districtId.substring(0,2);
		}else if (districtId.length()==4) {
			cityId=districtId;
			provinceId=districtId.substring(0, 2);
		}else {
			cityId=districtId;
			provinceId=districtId;
		}
		pnrPropertyAgreement.setCity(cityId);
		pnrPropertyAgreement.setProvince(provinceId);
		return pnrPropertyAgreement;
	}
}
