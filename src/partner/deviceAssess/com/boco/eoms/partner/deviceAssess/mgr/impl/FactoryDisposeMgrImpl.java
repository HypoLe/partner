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
import com.boco.eoms.partner.deviceAssess.dao.FactoryDisposeDao;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.FactoryDisposeMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.FactoryDispose;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.util.excelimport.DataSaveCallback;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ExcelImport;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.webapp.form.FactoryDisposeForm;

/**
 * <p>
 * Title:厂家处理设备故障事件信息
 * </p>
 * <p>
 * Description:厂家处理设备故障事件信息
 * </p>
 * <p>
 * Sun Sep 26 15:01:06 CST 2010
 * </p>
 *  
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FactoryDisposeMgrImpl implements FactoryDisposeMgr {
 
	private FactoryDisposeDao  factoryDisposeDao;
 	
	public FactoryDisposeDao getFactoryDisposeDao() {
		return this.factoryDisposeDao;
	}
 	
	public void setFactoryDisposeDao(FactoryDisposeDao factoryDisposeDao) {
		this.factoryDisposeDao = factoryDisposeDao;
	}
 	
    public List getFactoryDisposes() {
    	return factoryDisposeDao.getFactoryDisposes();
    }
    
    public FactoryDispose getFactoryDispose(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		FactoryDispose factoryDispose = factoryDisposeDao.getFactoryDispose(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", factoryDispose.getId());
		factoryDispose.setDeviceAssessApprove(daa);
    	return factoryDispose;
    }
    
    public void saveFactoryDispose(FactoryDispose factoryDispose) {
    	factoryDisposeDao.saveFactoryDispose(factoryDispose);
    }
    
    public void removeFactoryDispose(final String id) {
    	factoryDisposeDao.removeFactoryDispose(id);
    }
    
    public Map getFactoryDisposes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = factoryDisposeDao.getFactoryDisposes(curPage, pageSize, whereStr);
    	List<FactoryDispose> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(FactoryDispose factoryDispose : list) {
    		daa = daaMgr.getDeviceAssessApprove("", factoryDispose.getId());
    		factoryDispose.setDeviceAssessApprove(daa);
    	}
		return map;
	}

    
    public void saveDataAndApprove(FactoryDispose factoryDispose,
    		DeviceAssessApprove daa) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveFactoryDispose(factoryDispose);
		
		daa.setAssessId(factoryDispose.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+factoryDispose.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+factoryDispose.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", factoryDispose.getId());
		if(old != null) {
			try {
				String oldId = old.getId();
				BeanUtils.copyProperties(old, daa);
				old.setId(oldId);
				daaMgr.saveOrUpdateApprove(old);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			daaMgr.saveOrUpdateApprove(daa);
		}
    }
	public ImportResult importFromFile(FormFile formFile, final Map params)
			throws Exception {
		ExcelImport ei = new ExcelImport(5,1,18);
		ImportResult result = ei.importFromFile(formFile, new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				FactoryDispose fd = this.fromRow2Object(row);
				fd.setTotal(1);
				
				//设置审批信息(事件ID在保存了insideDispose生成主键后设置)
				DeviceAssessApprove daa = new DeviceAssessApprove();
				daa.setAssessType("1122106");//事件类型
				daa.setSheetNum(fd.getSheetNum());//工单号
				daa.setName(fd.getAffairName());//名称
				daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
				daa.setApproveUser(params.get("approveUser").toString());//审批人
				daa.setClassName(InsideDispose.class.getSimpleName());//实体类名
				daa.setModifyUrl("/partner/deviceAssess/factoryDisposes.do?method=edit");//修改URL链接
				daa.setDetailUrl("/partner/deviceAssess/factoryDisposes.do?method=goToDetail");//详细查看URL链接
				daa.setState(2);//审批状态（0驳回 1通过 2待审批）
				
				saveDataAndApprove(fd,daa);
			}
			//将每行数据构建为考核指标对象
			public FactoryDispose fromRow2Object(HSSFRow row) throws Exception {
				SimpleDateFormat sdf = null;
				HSSFCell cell;
				int colNum = 0;
				FactoryDispose fd = new FactoryDispose();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//工单号
				fd.setSheetNum(row.getCell(1).getRichStringCellValue().getString());
				//建单时间
				fd.setCreateTime(sdf.parse(sdf.format(row.getCell(2).getDateCellValue())));
				//转派厂家时间
				fd.setChangeTime(sdf.parse(sdf.format(row.getCell(3).getDateCellValue())));
				//归档时间
				fd.setPigeonholeTime(sdf.parse(sdf.format(row.getCell(4).getDateCellValue())));
				//事件名称
				fd.setAffairName(row.getCell(5).getRichStringCellValue().getString());
				//级别 (name2Id 1121501)
				String name2Id1 = StaticMethod.null2String(row.getCell(6).getRichStringCellValue().getString());
				if(!"".equals(name2Id1)) {
					name2Id1 = this.name2Id(name2Id1,"1121501");
				}
				fd.setAffairLevel(name2Id1);
				//发生省份
				fd.setProvince(row.getCell(7).getRichStringCellValue().getString());
				//发生地市
				fd.setCity(row.getCell(8).getRichStringCellValue().getString());
				//厂家 (name2Id 1121502)
				String name2Id2 = StaticMethod.null2String(row.getCell(9).getRichStringCellValue().getString());
				if(!"".equals(name2Id2)) {
					name2Id2 = this.name2Id(name2Id2,"1121502");
				}
				fd.setFactory(name2Id2);
				//专业 (name2Id 11216)
				String name2Id3 = StaticMethod.null2String(row.getCell(10).getRichStringCellValue().getString());
				if(!"".equals(name2Id3)) {
					name2Id3 = this.name2Id(name2Id3,"11216");
				}
				fd.setSpeciality(name2Id3);
				//设备类型
				String name2Id4 = StaticMethod.null2String(row.getCell(11).getRichStringCellValue().getString());
				if(!"".equals(name2Id4)) {
					name2Id4 = this.name2Id(name2Id4,name2Id3);
				}
				fd.setEquipmentType(name2Id4);
				//设备名称
				fd.setEquipmentName(row.getCell(12).getRichStringCellValue().getString());
				//设备版本
				fd.setEquipmentVersion(row.getCell(13).getRichStringCellValue().getString());
				//故障开始时间
				Date date1 = row.getCell(14).getDateCellValue();
				fd.setBeginTime(sdf.parse(sdf.format(date1)));
				//业务恢复时间
				Date date2 = row.getCell(15).getDateCellValue();
				fd.setResumeTime(sdf.parse(sdf.format(date2)));
				//故障消除时间
				Date date3 = row.getCell(16).getDateCellValue();
				fd.setRemoveTime(sdf.parse(sdf.format(date3)));
				//满意度
				fd.setSatisfaction((int)row.getCell(17).getNumericCellValue());
				//业务恢复时长
				fd.setResumeLong(this.dateSubtract(date2, date1));
				//故障处理时长
				fd.setFaultLong(this.dateSubtract(date3, date1));
				//计数
				fd.setAmount((int)row.getCell(20).getNumericCellValue());
				
				return fd;
			}
	});
		return result;
	}
	
}