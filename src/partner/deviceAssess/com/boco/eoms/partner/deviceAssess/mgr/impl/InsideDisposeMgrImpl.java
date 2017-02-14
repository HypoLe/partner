package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.apache.commons.beanutils.BeanUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.deviceAssess.dao.InsideDisposeDao;
import com.boco.eoms.partner.deviceAssess.dao.jdbc.QueryEomsSheetJDBC;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.InsideDisposeMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.util.excelimport.DataSaveCallback;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ExcelImport;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;

/**
 * <p>
 * Title:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Description:移动内部处理的设备故障事件信息
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2010
 * </p>
 *  
 * @author benweiwei
 * @version 1.0
 * 
 */
public class InsideDisposeMgrImpl implements InsideDisposeMgr {
 
	private InsideDisposeDao  insideDisposeDao;
	
	private QueryEomsSheetJDBC  queryEomsSheetJDBC;
 	
	public QueryEomsSheetJDBC getQueryEomsSheetJDBC() {
		return queryEomsSheetJDBC;
	}

	public void setQueryEomsSheetJDBC(QueryEomsSheetJDBC queryEomsSheetJDBC) {
		this.queryEomsSheetJDBC = queryEomsSheetJDBC;
	}

	public InsideDisposeDao getInsideDisposeDao() {
		return this.insideDisposeDao;
	}
 	
	public void setInsideDisposeDao(InsideDisposeDao insideDisposeDao) {
		this.insideDisposeDao = insideDisposeDao;
	}
 	
    public List getInsideDisposes() {
    	return insideDisposeDao.getInsideDisposes();
    }
    
    public InsideDispose getInsideDispose(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		InsideDispose insideDispose = insideDisposeDao.getInsideDispose(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", insideDispose.getId());
		insideDispose.setDeviceAssessApprove(daa);
    	return insideDispose;
    }
    
    public void saveInsideDispose(InsideDispose insideDispose) {
    	insideDisposeDao.saveInsideDispose(insideDispose);
    }
    
    public void removeInsideDispose(final String id) {
    	insideDisposeDao.removeInsideDispose(id);
    }
    
    public Map getInsideDisposes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = insideDisposeDao.getInsideDisposes(curPage, pageSize, whereStr);
    	List<InsideDispose> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(InsideDispose id : list) {
    		daa = daaMgr.getDeviceAssessApprove("", id.getId());
    		id.setDeviceAssessApprove(daa);
    	}
		return map;
	}

    public void eomsSheetToInsideDispose() {
    	List list = queryEomsSheetJDBC.getFaultSheetList();
    	for (int i = 0 ; list.size()>i;i++) {
    		InsideDispose insideDispose= (InsideDispose)list.get(i);
    		insideDisposeDao.saveInsideDispose(insideDispose);
    	}
    	System.out.println(list.size());
    }

	public void saveDataAndApprove(InsideDispose insideDispose,
			DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveInsideDispose(insideDispose);
		daa.setAssessId(insideDispose.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+insideDispose.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+insideDispose.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", insideDispose.getId());
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

	public ImportResult importFromFile(FormFile formFile,final Map params) throws Exception {
		ExcelImport ei = new ExcelImport(5,1,18);
		ImportResult result = ei.importFromFile(formFile, new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				InsideDispose id = this.fromRow2Object(row);
				id.setTotal(1);
				
				//设置审批信息(事件ID在保存了insideDispose生成主键后设置)
				DeviceAssessApprove daa = new DeviceAssessApprove();
				daa.setAssessType("1122101");//事件类型
				daa.setSheetNum(id.getSheetNum());//工单号
				daa.setName(id.getAffairName());//名称
				daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
				daa.setApproveUser(params.get("approveUser").toString());//审批人
				daa.setClassName(InsideDispose.class.getSimpleName());//实体类名
				daa.setModifyUrl("/partner/deviceAssess/insideDisposes.do?method=edit");//修改URL链接
				daa.setDetailUrl("/partner/deviceAssess/insideDisposes.do?method=goToDetail");//详细查看URL链接
				daa.setState(2);//审批状态（0驳回 1通过 2待审批）
				
				saveDataAndApprove(id,daa);
			}
			//将每行数据构建为考核指标对象
			public InsideDispose fromRow2Object(HSSFRow row) throws Exception {
				SimpleDateFormat sdf = null;
				HSSFCell cell;
				int colNum = 0;
				InsideDispose id = new InsideDispose();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//工单号
				id.setSheetNum(row.getCell(1).getRichStringCellValue().getString());
				//建单时间
				id.setCreateTime(sdf.parse(sdf.format(row.getCell(2).getDateCellValue())));
				//归档时间
				id.setPigeonholeTime(sdf.parse(sdf.format(row.getCell(3).getDateCellValue())));
				//事件名称
				id.setAffairName(row.getCell(4).getRichStringCellValue().getString());
				//级别 (name2Id 1121501)
				String name2Id1 = StaticMethod.null2String(row.getCell(5).getRichStringCellValue().getString());
				if(!"".equals(name2Id1)) {
					name2Id1 = this.name2Id(name2Id1,"1121501");
				}
				id.setAffairLevel(name2Id1);
				//发生省份
				id.setProvince(row.getCell(6).getRichStringCellValue().getString());
				//发生地市
				id.setCity(row.getCell(7).getRichStringCellValue().getString());
				//厂家 (name2Id 1121502)
				String name2Id2 = StaticMethod.null2String(row.getCell(8).getRichStringCellValue().getString());
				if(!"".equals(name2Id2)) {
					name2Id2 = this.name2Id(name2Id2,"1121502");
				}
				id.setFactory(name2Id2);
				//专业 (name2Id 11216)
				String name2Id3 = StaticMethod.null2String(row.getCell(9).getRichStringCellValue().getString());
				if(!"".equals(name2Id3)) {
					name2Id3 = this.name2Id(name2Id3,"11216");
				}
				id.setSpeciality(name2Id3);
				//设备类型
				String name2Id4 = StaticMethod.null2String(row.getCell(10).getRichStringCellValue().getString());
				if(!"".equals(name2Id4)) {
					name2Id4 = this.name2Id(name2Id4,name2Id3);
				}
				id.setEquipmentType(name2Id4);
				//设备名称
				id.setEquipmentName(row.getCell(11).getRichStringCellValue().getString());
				//设备版本
				id.setEquipmentVersion(row.getCell(12).getRichStringCellValue().getString());
				//故障开始时间
				Date date1 = row.getCell(13).getDateCellValue();
				id.setBeginTime(sdf.parse(sdf.format(date1)));
				//业务恢复时间
				Date date2 = row.getCell(14).getDateCellValue();
				id.setResumeTime(sdf.parse(sdf.format(date2)));
				//故障消除时间
				Date date3 = row.getCell(15).getDateCellValue();
				id.setRemoveTime(sdf.parse(sdf.format(date3)));
				//业务恢复时长
				id.setResumeLong(this.dateSubtract(date2, date1));
				//故障处理时长
				id.setDisposalLong(this.dateSubtract(date3, date1));
				//计数
				id.setAmount((int)row.getCell(18).getNumericCellValue());
				
				return id;
			}
	});
		return result;
	}    
        
}