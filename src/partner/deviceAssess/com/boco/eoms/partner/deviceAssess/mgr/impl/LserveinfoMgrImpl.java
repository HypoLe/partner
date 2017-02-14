package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Ftraininfo;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.model.Lserveinfo;
import com.boco.eoms.partner.deviceAssess.model.Repairinfo;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.LserveinfoMgr;
import com.boco.eoms.partner.deviceAssess.util.excelimport.DataSaveCallback;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ExcelImport;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.dao.LserveinfoDao;

/**
 * <p>
 * Title:现场服务事件信息
 * </p>
 * <p>
 * Description:现场服务事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p> 
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class LserveinfoMgrImpl implements LserveinfoMgr {
 
	private LserveinfoDao  lserveinfoDao;
 	
	public LserveinfoDao getLserveinfoDao() {
		return this.lserveinfoDao;
	}
 	
	public void setLserveinfoDao(LserveinfoDao lserveinfoDao) {
		this.lserveinfoDao = lserveinfoDao;
	}
 	
    public List getLserveinfos() {
    	return lserveinfoDao.getLserveinfos();
    }
    
    public Lserveinfo getLserveinfo(final String id) {
    	
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Lserveinfo lserveinfo = lserveinfoDao.getLserveinfo(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", lserveinfo.getId());
		lserveinfo.setDeviceAssessApprove(daa);
    	return lserveinfo;
    	
    }
    
    public void saveLserveinfo(Lserveinfo lserveinfo) {
    	lserveinfoDao.saveLserveinfo(lserveinfo);
    }
    
    public void removeLserveinfo(final String id) {
    	lserveinfoDao.removeLserveinfo(id);
    }
    
    public Map getLserveinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = lserveinfoDao.getLserveinfos(curPage, pageSize, whereStr);
    	List<Lserveinfo> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(Lserveinfo lserveinfo : list) {
    		daa = daaMgr.getDeviceAssessApprove("", lserveinfo.getId());
    		lserveinfo.setDeviceAssessApprove(daa);
    	}
		return map;
	}
    public void saveDataAndApprove(Lserveinfo lserveinfo,
			DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveLserveinfo(lserveinfo);
		daa.setAssessId(lserveinfo.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+lserveinfo.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+lserveinfo.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", lserveinfo.getId());
		if(old != null) {
			try {
				String oldId = old.getId();
				BeanUtils.copyProperties(old, daa);
				old.setId(oldId);
				daaMgr.saveOrUpdateApprove(old);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			daaMgr.saveOrUpdateApprove(daa);
		}
	}
    
    public ImportResult importFromFile(FormFile formFile,final Map params) throws Exception {
		ExcelImport ei = new ExcelImport(5,1,15);
		ImportResult result = ei.importFromFile(formFile, new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				Lserveinfo lserveinfo = this.fromRow2Object(row);
				lserveinfo.setTotal(1);
				lserveinfo.setTakeCountOf(1);
				//设置审批信息(事件ID在保存了insideDispose生成主键后设置)
				DeviceAssessApprove daa = new DeviceAssessApprove();
				daa.setAssessType("1122110");//事件类型
				daa.setSheetNum(lserveinfo.getSheetNum());//工单号
				daa.setName(lserveinfo.getAffairName());//名称
				daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
				daa.setApproveUser(params.get("approveUser").toString());//审批人
				daa.setClassName(Lserveinfo.class.getSimpleName());//实体类名
				daa.setModifyUrl("/partner/deviceAssess/lserveinfos.do?method=edit");//修改URL链接
				daa.setDetailUrl("/partner/deviceAssess/lserveinfos.do?method=goToDetail");//详细查看URL链接
				daa.setState(2);//审批状态（0驳回 1通过 2待审批）
				
				saveDataAndApprove(lserveinfo,daa);
			}
			//将每行数据构建为考核指标对象
			public Lserveinfo fromRow2Object(HSSFRow row) throws Exception {
				SimpleDateFormat sdf = null;
				HSSFCell cell;
				int colNum = 0;
				Lserveinfo lserveinfo = new Lserveinfo();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//工单号
				lserveinfo.setSheetNum(row.getCell(1).getRichStringCellValue().getString());
				//建单时间
				lserveinfo.setCreateTime(sdf.parse(sdf.format(row.getCell(2).getDateCellValue())));
                //转派厂家时间
				lserveinfo.setTurnFactoryTime(sdf.parse(sdf.format(row.getCell(3).getDateCellValue())));
				//归档时间
				lserveinfo.setPigeonholeTime(sdf.parse(sdf.format(row.getCell(4).getDateCellValue())));
				//事件名称
				lserveinfo.setAffairName(row.getCell(5).getRichStringCellValue().getString());
				//级别 (name2Id 1121501)
				String name2Id1 = StaticMethod.null2String(row.getCell(6).getRichStringCellValue().getString());
				if(!"".equals(name2Id1)) {
					name2Id1 = this.name2Id(name2Id1,"1121501");
				}
				lserveinfo.setAffairLevel(name2Id1);
				//发生省份
				lserveinfo.setProvince(row.getCell(7).getRichStringCellValue().getString());
				//发生地市
				lserveinfo.setCity(row.getCell(8).getRichStringCellValue().getString());
				//厂家 (name2Id 1121502)
				String name2Id2 = StaticMethod.null2String(row.getCell(9).getRichStringCellValue().getString());
				if(!"".equals(name2Id2)) {
					name2Id2 = this.name2Id(name2Id2,"1121502");
				}
				lserveinfo.setFactory(name2Id2);
				//专业 (name2Id 11216)
				String name2Id3 = StaticMethod.null2String(row.getCell(10).getRichStringCellValue().getString());
				if(!"".equals(name2Id3)) {
					name2Id3 = this.name2Id(name2Id3,"11216");
				}
				lserveinfo.setSpeciality(name2Id3);
				//设备类型
				String name2Id4 = StaticMethod.null2String(row.getCell(11).getRichStringCellValue().getString());
				if(!"".equals(name2Id4)) {
					name2Id4 = this.name2Id(name2Id4,name2Id3);
				}
				lserveinfo.setDeviceType(name2Id4);
				//服务人天数
				lserveinfo.setServePopu((int)row.getCell(12).getNumericCellValue());
				//满意度
				lserveinfo.setSatisfaction((int)row.getCell(13).getNumericCellValue());
				//满意度打分原因
				lserveinfo.setSatisfactionReason(row.getCell(14).getRichStringCellValue().getString());
                //计数
				lserveinfo.setTakeCountOf((int)row.getCell(15).getNumericCellValue());
				
				return lserveinfo;
			}
	});
		return result;
	}    
}