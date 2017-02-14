package com.boco.eoms.mobile.service.serviceimpl;

/**
 * 
 * @author WANGJUN
 * @ClassName: RoomAndCableInspectServiceImpl
 * @Version  
 * @ModifiedBy 
 * @Copyright BOCO
 * @date Sep 29, 2016 9:59:01 AM
 * @description 接入机房和光缆网核查Service实现类
 */
import java.util.List;

import com.boco.eoms.mobile.dao.IRoomAndCableInspectJDBCDao;
import com.boco.eoms.mobile.po.CellItem;
import com.boco.eoms.mobile.po.DianganCabel;
import com.boco.eoms.mobile.po.EquipmentRoomItem;
import com.boco.eoms.mobile.po.GjxCableOpticalCableItem;
import com.boco.eoms.mobile.po.LinkEquipmentItem;
import com.boco.eoms.mobile.po.LinkEquipmentOpticalCableItem;
import com.boco.eoms.mobile.po.LinkEquipmentTerminalItem;
import com.boco.eoms.mobile.po.PortOpticalCableItem;
import com.boco.eoms.mobile.po.WellsOpticalCableItem;
import com.boco.eoms.mobile.service.IRoomAndCableInspectService;

public class RoomAndCableInspectServiceImpl implements
		IRoomAndCableInspectService {

	private IRoomAndCableInspectJDBCDao roomAndCableInspectJDBCDao;

	public IRoomAndCableInspectJDBCDao getRoomAndCableInspectJDBCDao() {
		return roomAndCableInspectJDBCDao;
	}

	public void setRoomAndCableInspectJDBCDao(
			IRoomAndCableInspectJDBCDao roomAndCableInspectJDBCDao) {
		this.roomAndCableInspectJDBCDao = roomAndCableInspectJDBCDao;
	}

	/**
	 * 查询人井对应的光缆、管孔信息
	 * 
	 * @author WANGJUN
	 * @title: getWellsOpticalCableList
	 * @date Sep 29, 2016 10:48:54 AM
	 * @param planResId
	 * @return List<WellsOpticalCableItem>
	 */
	public List<WellsOpticalCableItem> getWellsOpticalCableList(String planResId,int pageIndex,int pageSize) {
		return roomAndCableInspectJDBCDao.getWellsOpticalCableList(planResId,pageIndex,pageSize);
	}
	
	/**
	 * 	 查询人井对应的光缆、管孔信息的总条数
	 	 * @author WANGJUN
	 	 * @title: getWellsOpticalCableCount
	 	 * @date Oct 9, 2016 10:16:00 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getWellsOpticalCableCount(String planResId){
		return roomAndCableInspectJDBCDao.getWellsOpticalCableCount(planResId);
	}
	
	/**
	 *   更新人井对应的光缆信息
	 	 * @author WANGJUN
	 	 * @title: updateWellsOpticalCable
	 	 * @date Sep 29, 2016 3:30:29 PM
	 	 * @param id 光缆id
	 	 * @param cableName 光缆名称
	 	 * @param pipeHoleInfor 管孔信息
	 	 * @param operateType 操作类型
	 */
	public int updateWellsOpticalCable(String id,String cableName,String pipeHoleInfor,String operateType){
		return roomAndCableInspectJDBCDao.updateWellsOpticalCable(id,cableName,pipeHoleInfor,operateType);
	}
	
	

	/**
	 * 查询光交箱端子占用信息
	 * 
	 * @param planResId
	 * @return
	 */
	public List<PortOpticalCableItem> getGjxPortOpticalCableList(String planResId,int pageIndex,int pageSize) {
		return roomAndCableInspectJDBCDao.getGjxPortOpticalCableList(planResId,pageIndex, pageSize);
	}

	/**
	 * 	 查询光交箱端子的总条数
	 	 * 
	 	 * @param planResId
	 	 * @return int
	 */
	public int getGjxPortOpticalCableCount(String planResId){
		return roomAndCableInspectJDBCDao.getGjxPortOpticalCableCount(planResId);
	}
	
	/**
	 * 更新光交箱端子信息
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateGjxPortOpticalCable(String gjxid,String module,String port,String status,String userInfo,String operateType){
		return roomAndCableInspectJDBCDao.updateGjxPortOpticalCable(gjxid, module, port, status, userInfo, operateType);
	}
	
	/**
	 * 查询光交箱经过缆情况信息
	 * 
	 * @param planResId
	 * @return
	 */
	public List<GjxCableOpticalCableItem> getGjxCableOpticalCableList(String planResId,int pageIndex,int pageSize) {
		return roomAndCableInspectJDBCDao.getGjxCableOpticalCableList( planResId, pageIndex, pageSize);
	}

	/**
	 * 	 查询光交箱经过缆的总条数
	 	 * 
	 	 * @param planResId
	 	 * @return int
	 */
	public int getGjxCableOpticalCableCount(String planResId){
		return roomAndCableInspectJDBCDao.getGjxCableOpticalCableCount(planResId);
	}
	
	/**
	 * 更新光交箱经过缆信息
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateGjxCableOpticalCable(String resId ,String id,String ofiber_name,String core_sequence,String operateType,String fportNum,String coreStatus){		
		return roomAndCableInspectJDBCDao.updateGjxCableOpticalCable(resId, id, ofiber_name, core_sequence, operateType,fportNum,coreStatus);
	}
	
	/**
	 *   查询接入机房-机房设备 列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<EquipmentRoomItem> getAccessRoomEquipmentRoomList(String planResId,int pageIndex,int pageSize){
		return roomAndCableInspectJDBCDao.getAccessRoomEquipmentRoomList(planResId,pageIndex,pageSize);
	}
	
	/**
	 *   查询接入机房-机房设备 总条数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getAccessRoomEquipmentRoomCount(String planResId){
		return roomAndCableInspectJDBCDao.getAccessRoomEquipmentRoomCount(planResId);
	}
	
	/**
	 *   接入机房 机房设备 新增、修改、删除
	 	 * @author WANGJUN
	 	 * @title: updateAccessRoomEquipmentRoom
	 	 * @date Oct 14, 2016 9:55:16 AM
	 	 * @param resId
	 	 * @param roomId
	 	 * @param deviceName
	 	 * @param networkType
	 	 * @param deviceVender
	 	 * @param deviceType
	 	 * @param operateType
	 	 * @return int
	 */
	public int updateAccessRoomEquipmentRoom(String resId,String roomId,String deviceName,String networkType,String deviceVender,String deviceType,String operateType){
		return roomAndCableInspectJDBCDao.updateAccessRoomEquipmentRoom(resId,roomId,deviceName,networkType,deviceVender,deviceType,operateType);
	}
	
	/**
	 *   查询接入机房-链接设备 列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<LinkEquipmentItem> getShelfList(String planResId,int pageIndex,int pageSize){
		return roomAndCableInspectJDBCDao.getShelfList(planResId,pageIndex,pageSize);
	}
	
	/**
	 *   查询接入机房-链接设备 总条数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getShelfCount(String planResId){
		return roomAndCableInspectJDBCDao.getShelfCount(planResId);
	}
	
	/**
	 *   查询接入机房-链接设备 端子占用列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<LinkEquipmentTerminalItem> getRoomPortList(String shelf_id,int pageIndex,int pageSize){
		return roomAndCableInspectJDBCDao.getRoomPortList(shelf_id,pageIndex,pageSize);
	}
	
	/**
	 *   查询接入机房-链接设备 端子占用列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getRoomPortCount(String planResId){
		return roomAndCableInspectJDBCDao.getRoomPortCount(planResId);
	}
	
	/**
	 * 更新光交箱链接设备 端子占用 
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateRoomShelfPort(String shelfId ,String port,String status,String userInfo,String operateType,String portId){		
		return roomAndCableInspectJDBCDao.updateRoomShelfPort(shelfId , port, status, userInfo, operateType,portId);
	}
	
	/**
	 *   查询接入机房-链接设备 进出缆列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<LinkEquipmentOpticalCableItem> getRoomCabelList(String shelf_id,int pageIndex,int pageSize){
		return roomAndCableInspectJDBCDao.getRoomCabelList(shelf_id , pageIndex, pageSize);
	}
	
	/**
	 *   查询接入机房-链接设备 进出缆列表总数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getRoomCabelCount(String planResId){
		return roomAndCableInspectJDBCDao.getRoomCabelCount(planResId);
	}
	
	/**
	 * 更新接入机房 链接设备 进出缆
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateRoomShelfCable(String shelfId ,String ofiberId,String ofiberNo,String num,String operateType,String extrenityNo,String extrenityXh,String extrenityStatus){
		return roomAndCableInspectJDBCDao.updateRoomShelfCable(shelfId,ofiberId,ofiberNo,num,operateType,extrenityNo,extrenityXh,extrenityStatus);
	}
	
	/**
	 *   查询光缆网 电杆 进出缆列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<DianganCabel> getDianganCabelList(String shelf_id,int pageIndex,int pageSize){
		return roomAndCableInspectJDBCDao.getDianganCabelList(shelf_id ,pageIndex,  pageSize);
	}
	/**
	 *      查询光缆网 电杆 进出缆列表总数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getDianganCabelCount(String planResId){
		return roomAndCableInspectJDBCDao.getDianganCabelCount(planResId);
	}
	/**
	 * 更新电杆 进出缆
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateDianganCable(String barId ,String planId,String ofiberNo,String num,String ofiberType,String operateType,String id){
		return roomAndCableInspectJDBCDao.updateDianganCable(barId,planId,  ofiberNo,  num,  ofiberType,  operateType,id);
	}
	
	/**
	 * 	 室分小区数
	 	 * @author WANGJUN
	 	 * @title: getCellCount
	 	 * @date Nov 1, 2016 9:15:57 AM
	 	 * @param planResId
	 	 * @param networkType
	 	 * @return int
	 */
	public int getCellCount(String planResId,String networkType){
		return roomAndCableInspectJDBCDao.getCellCount(planResId,networkType);
	}
	
	/**
	 * 	室分小区集合
	 	 * @author WANGJUN
	 	 * @title: getCellList
	 	 * @date Nov 1, 2016 9:16:07 AM
	 	 * @param planResId
	 	 * @param networkType
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<CellItem>
	 */
	public List<CellItem> getCellList(String planResId,String networkType,int pageIndex,int pageSize){
		return roomAndCableInspectJDBCDao.getCellList(planResId,networkType,pageIndex,pageSize);
	}
	
	/**
	 * 	 室分小区 核查 添加
	 	 * @author WANGJUN
	 	 * @title: updateCell
	 	 * @date Nov 1, 2016 2:53:57 PM
	 	 * @param planResId
	 	 * @param siteName
	 	 * @param cellName
	 	 * @param neCode
	 	 * @param networkType
	 	 * @param isCovered
	 	 * @param cellInfor
	 	 * @param operateType
	 	 * @return int
	 */
	public int updateCell(String planResId,String siteName,String cellName,String neCode,String networkType,String isCovered,String cellInfor,String operateType){
		return roomAndCableInspectJDBCDao.updateCell(planResId,siteName,cellName,neCode,networkType,isCovered,cellInfor,operateType);
	}
	
	/**
	 *   查询光交箱端子模块下的端子总数
	 	 * @author WANGJUN
	 	 * @title: getGjxTerminalCount
	 	 * @date Nov 15, 2016 10:41:09 AM
	 	 * @param gjxid
	 	 * @param module
	 	 * @return int
	 */
	public int getGjxTerminalCount(String gjxid,String module){
		return roomAndCableInspectJDBCDao.getGjxTerminalCount(gjxid,module);
	}
	
	/**
	 *   查询光交箱端子模块下的端子明细
	 	 * @author WANGJUN
	 	 * @title: getGjxTerminalList
	 	 * @date Nov 15, 2016 10:43:06 AM
	 	 * @param gjxid
	 	 * @param modulepageIndex
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<PortOpticalCableItem>
	 */
	public List<PortOpticalCableItem> getGjxTerminalList(String gjxid,String module,int pageIndex,int pageSize){
		return roomAndCableInspectJDBCDao.getGjxTerminalList(gjxid,module,pageIndex,pageSize);
	}
	
	/**
	 *   查询设备名称是否存在
	 	 * @author WANGJUN
	 	 * @title: getEquipmentRoomByDeviceNameCount
	 	 * @date Nov 16, 2016 9:45:25 AM
	 	 * @param deviceName
	 	 * @return int
	 */
	public int getEquipmentRoomByDeviceNameCount(String resId,String deviceName){
		return roomAndCableInspectJDBCDao.getEquipmentRoomByDeviceNameCount(resId,deviceName);
	}
	
	/**
	 *   判断机房设备是否已经存在
	 	 * @author WANGJUN
	 	 * @title: isExistRoomEquipment
	 	 * @date Nov 16, 2016 4:40:02 PM
	 	 * @param resId
	 	 * @param deviceName
	 	 * @return boolean
	 */
	public boolean isExistRoomEquipment(String resId,String deviceName){
		int num = roomAndCableInspectJDBCDao.getEquipmentRoomByDeviceNameCount(resId,deviceName);
		if(num != 0){
			return true;
		}
		int num2 = roomAndCableInspectJDBCDao.getToZhzyRoomEquipmentByDeviceNameCount(resId,deviceName);
		if(num2 != 0){
			return true;
		}
		return false;
	}
	
	/**
	 *   判断光交接箱进出缆是否已经存在
	 	 * @author WANGJUN
	 	 * @title: isExistGjxCableOpticalCable
	 	 * @date Nov 17, 2016 10:21:31 AM
	 	 * @param resId
	 	 * @param ofiberName
	 	 * @param coreSequence
	 	 * @return boolean
	 */
	public boolean isExistGjxCableOpticalCable(String resId,String ofiberName,String coreSequence){
		int num = roomAndCableInspectJDBCDao.getGjxCableOpticalCableBykeyCount(resId, ofiberName, coreSequence);
		if(num != 0){
			return true;
		}
		int num2 = roomAndCableInspectJDBCDao.getToZhzyGjxCableBykeyCount(resId, ofiberName, coreSequence);
		if(num2 != 0){
			return true;
		}
		return false;
	}
}

