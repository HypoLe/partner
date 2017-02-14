package com.boco.eoms.partner.baseinfo.mgr.impl;

import java.util.Iterator;
import java.util.List;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.baseinfo.dao.AreaDeptTreeDao;
import com.boco.eoms.partner.baseinfo.dao.PartnerUserDao;
import com.boco.eoms.partner.baseinfo.dao.TawApparatusDao;
import com.boco.eoms.partner.baseinfo.dao.TawPartnerCarDao;
import com.boco.eoms.partner.baseinfo.dao.TawPartnerOilDao;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.util.AreaDeptTreeConstants;

/**
 * <p>
 * Title:地域部门树
 * </p>
 * <p>
 * Description:地域部门树
 * </p>
 * <p>
 * Fri Feb 06 11:46:50 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
public class AreaDeptTreeMgrImpl implements AreaDeptTreeMgr {

	private AreaDeptTreeDao areaDeptTreeDao;

	private PartnerUserDao partnerUserDao;

	private TawPartnerCarDao tawPartnerCarDao;

	private TawPartnerOilDao tawPartnerOilDao;

	private TawApparatusDao tawApparatusDao;

	public AreaDeptTreeDao getAreaDeptTreeDao() {
		return this.areaDeptTreeDao;
	}

	public void setAreaDeptTreeDao(AreaDeptTreeDao areaDeptTreeDao) {
		this.areaDeptTreeDao = areaDeptTreeDao;
	}

	public PartnerUserDao getPartnerUserDao() {
		return partnerUserDao;
	}

	public void setPartnerUserDao(PartnerUserDao partnerUserDao) {
		this.partnerUserDao = partnerUserDao;
	}

	public TawPartnerOilDao getTawPartnerOilDao() {
		return tawPartnerOilDao;
	}

	public void setTawPartnerOilDao(TawPartnerOilDao tawPartnerOilDao) {
		this.tawPartnerOilDao = tawPartnerOilDao;
	}

	public TawApparatusDao getTawApparatusDao() {
		return tawApparatusDao;
	}

	public void setTawApparatusDao(TawApparatusDao tawApparatusDao) {
		this.tawApparatusDao = tawApparatusDao;
	}

	public AreaDeptTree getAreaDeptTree(final String id) {
		return areaDeptTreeDao.getAreaDeptTree(id);
	}

	public void saveAreaDeptTree(AreaDeptTree areaDeptTree) {
		boolean isNew = (null == areaDeptTree.getId() || "".equals(areaDeptTree.getId()));
		if (isNew) {
			// 生成新节点Id
			String nodeId = getUsableNodeId(areaDeptTree.getParentNodeId());
			areaDeptTree.setNodeId(nodeId);
			areaDeptTree.setLeaf(AreaDeptTreeConstants.NODE_LEAF);
			areaDeptTree.setCreateTime(StaticMethod.getLocalTime());// 增加新增时间
			// 保存新节点
			areaDeptTreeDao.saveAreaDeptTree(areaDeptTree);

			// 如果父节点不是根结点则设置父节点为非叶子节点
			if (!AreaDeptTreeConstants.TREE_ROOT_ID.equals(areaDeptTree.getParentNodeId())) {
				updateNodeLeaf(areaDeptTree.getParentNodeId(), AreaDeptTreeConstants.NODE_NOTLEAF);
			}
		} else {
			areaDeptTreeDao.saveAreaDeptTree(areaDeptTree);
		}
	}

	public AreaDeptTree getAreaDeptTreeByNodeId(final String nodeId) {
		return areaDeptTreeDao.getAreaDeptTreeByNodeId(nodeId);
	}

	public void removeAreaDeptTreeByNodeId(final String nodeId) {
		AreaDeptTree areaDeptTree = areaDeptTreeDao.getAreaDeptTreeByNodeId(nodeId);
		// 获得父节点Id
		String parentNodeId = areaDeptTree.getParentNodeId();
		areaDeptTreeDao.removeAreaDeptTreeByNodeId(nodeId);

		// 删除节点后若父节点下已经没有其他子节点则将父节点设置为叶子节点
		if (!AreaDeptTreeConstants.TREE_ROOT_ID.equals(parentNodeId)) {
			if (!isHasNextLevel(parentNodeId)) {
				updateNodeLeaf(parentNodeId, AreaDeptTreeConstants.NODE_LEAF);
			}
		}
	}

	public List getNextLevelAreaDeptTrees(String parentNodeId) {
		return areaDeptTreeDao.getNextLevelAreaDeptTrees(parentNodeId);
	}

	public String getUsableNodeId(String parentNodeId) {
		return areaDeptTreeDao.getUsableNodeId(parentNodeId, parentNodeId.length()
				+ AreaDeptTreeConstants.NODEID_BETWEEN_LENGTH);
	}

	public boolean isHasNextLevel(String parentNodeId) {
		boolean flag = false;
		List list = areaDeptTreeDao.getNextLevelAreaDeptTrees(parentNodeId);
		if (list.iterator().hasNext()) {
			flag = true;
		}
		return flag;
	}

	public void updateNodeLeaf(String nodeId, String leaf) {
		AreaDeptTree areaDeptTree = areaDeptTreeDao.getAreaDeptTreeByNodeId(nodeId);
		areaDeptTree.setLeaf(leaf);
		areaDeptTreeDao.saveAreaDeptTree(areaDeptTree);
	}

	public List getChildNodes(String parentNodeId) {
		return areaDeptTreeDao.getChildNodes(parentNodeId);
	}

	// 根据父节点parentNodeId和节点类型nodeType得到某个省、某个地市、某个厂商下的节点（人力信息、仪器仪表、车辆管理、油机管理）
	public List getChildLeafNodes(final String parentNodeId, String nodeType) {
		return areaDeptTreeDao.getChildLeafNodes(parentNodeId, nodeType);
	}

	// 根据叶子节点（人力信息、仪器仪表、车辆管理、油机管理）对象集，获得nodeId，并把nodeId组装成字符串
	public String getStringNodeIdByLeaf(final String parentNodeId, String nodeType) {
		List list = this.getChildLeafNodes(parentNodeId, nodeType);
		String nodes = "'a'";// 当父节点下无任何人力信息时返回
		if (list.size() > 0) {
			nodes = "";
			String[] nodeIds = new String[list.size()];
			Iterator it = list.iterator();
			for (int i = 0; i < list.size(); i++) {
				AreaDeptTree areaDeptTree = (AreaDeptTree) it.next();
				nodeIds[i] = areaDeptTree.getNodeId();
				if ((i + 1) < list.size())
					nodes += nodeIds[i] + ",";
				else if ((i + 1) == list.size())
					nodes += nodeIds[i];
			}
		}
		return nodes;
	}

	public String id2Name(final String id) {
		return areaDeptTreeDao.id2Name(id);
	}

	public List getAreaDeptTreesByType(final String nodeType) {
		return areaDeptTreeDao.getAreaDeptTreesByType(nodeType);
	}

	// 由节点名得到节点ID 用于根据地市名查询地市nodeId，用于批量导入
	public String getNodeIdByNodeName(String name) {
		return areaDeptTreeDao.getNodeIdByNodeName(name);
	}

	// 由父节点ID和节点名得到节点Id，用于根据地市nodeId和厂商名查询厂商nodeId，用于批量导入
	public String getNodeIdByParentAndName(String parentNodeId, String name) {
		return areaDeptTreeDao.getNodeIdByParentAndName(parentNodeId, name);
	}

	// 由地市名、厂商名得到叶子节点的nodeId（人力信息、车辆管理、仪器仪表、油机管理）
	public String getLeafNodeIdByNames(String area, String factory, String type) {
		return areaDeptTreeDao.getLeafNodeIdByNames(area, factory, type);
	}

	// 由节点id，得到叶子节点
	public List getLeavesByNodeId(final String nodeId, String nodeType) {
		return areaDeptTreeDao.getLeavesByNodeId(nodeId, nodeType);
	}

	// 由省名得到省下全部地市
	public List getAreaByProvince(String provinceName) {
		return areaDeptTreeDao.getAreaByProvince(provinceName);
	}

	// 返回查询条件得到的结果
	public List getInfoByCondition(String sql) {
		return areaDeptTreeDao.getInfoByCondition(sql);
	}

	// 由合作伙伴名称得到全部合作伙伴节点
	public List getDeptByDeptName(String provinceName) {
		return areaDeptTreeDao.getDeptByDeptName(provinceName);
	}

	// 得到省的列表
	public List getProvinceNodes() {
		return areaDeptTreeDao.getProvinceNodes();
	}

	// 得到一列地市名下的树节点集合
	public List getNodesByAreas(String areaNames) {
		return areaDeptTreeDao.getNodesByAreas(areaNames);
	}

	// 09.03.12 加方法 不同用户的人可以看到不同的节点,当登录用户是地市用户时执行这个方法
	public List getNextLevelAreaDeptTrees(final String parentNodeId, String area) {
		return areaDeptTreeDao.getNextLevelAreaDeptTrees(parentNodeId, area);
	}

	// updateType 更新类型(1新增2删除3修改)
	// name 合作伙伴名称 areaId 地域id（对应pnr_dept里areaId字段） interfaceHeadId
	// 存放二维码系统中的总公司的ID
	public AreaDeptTree addDeptTree(String updateType, String name, String areaId, String interfaceHeadId) {
		AreaDeptTree areaDeptTree3 = null;
		// 新增时的操作
		if ("1".equals(updateType) && !"".equals(areaId)) {
			String sql = " from AreaDeptTree areaDeptTree where areaDeptTree.nodeType='area' and areaDeptTree.areaId = '"
					+ areaId + "'";
			List list = this.getInfoByCondition(sql);
			AreaDeptTree areaDeptTree = new AreaDeptTree();
			AreaDeptTree factory = new AreaDeptTree();
			if (list.size() != 0) {
				areaDeptTree = (AreaDeptTree) list.get(0);
				String nodeId = StaticMethod.null2String(areaDeptTree.getNodeId());

				// 给地市节点的factoryLists字段赋值，为了在合作伙伴统计报表中使用。
				// AreaDeptTree area = this.getAreaDeptTreeByNodeId(nodeId);
				// String factoryLists = area.getFactoryLists();
				// if(factoryLists==null||factoryLists.equals("")){
				// factoryLists = ""+name;
				// }
				// else {
				// factoryLists += ","+ name;
				// }
				// area.setFactoryLists(factoryLists);
				// this.saveAreaDeptTree(area);

				// 在树表里存厂商节点 保存合作伙伴单表的同时保存树表里合作伙伴节点

				factory.setParentNodeId(nodeId);
				factory.setNodeName(name);
				factory.setNodeType(AreaDeptTreeConstants.NODE_TYPE_FACTORY);
				factory.setAreaId(areaId);
				factory.setInterfaceHeadId(interfaceHeadId);

				this.saveAreaDeptTree(factory);

				if (!nodeId.equals("")) {
					AreaDeptTree factory1 = this.getAreaDeptTree(factory.getId());
					// 在树表里存人力信息节点
					AreaDeptTree user = new AreaDeptTree();
					user.setParentNodeId(factory1.getNodeId());
					user.setNodeName("人力信息");
					user.setNodeType(AreaDeptTreeConstants.NODE_TYPE_USER);
					user.setAreaId(areaId);
					user.setInterfaceHeadId(interfaceHeadId);
					this.saveAreaDeptTree(user);

					// 在树表里存仪器仪表节点
					AreaDeptTree instrument = new AreaDeptTree();
					instrument.setParentNodeId(factory1.getNodeId());
					instrument.setNodeName("仪器仪表");
					instrument.setNodeType(AreaDeptTreeConstants.NODE_TYPE_INSTRUMENT);
					instrument.setAreaId(areaId);
					instrument.setInterfaceHeadId(interfaceHeadId);
					this.saveAreaDeptTree(instrument);

					// 在树表里存车辆管理节点
					AreaDeptTree car = new AreaDeptTree();
					car.setParentNodeId(factory1.getNodeId());
					car.setNodeName("车辆管理");
					car.setNodeType(AreaDeptTreeConstants.NODE_TYPE_CAR);
					car.setAreaId(areaId);
					car.setInterfaceHeadId(interfaceHeadId);
					this.saveAreaDeptTree(car);

					// 在树表里存油机管理节点
					AreaDeptTree oil = new AreaDeptTree();
					oil.setParentNodeId(factory1.getNodeId());
					oil.setNodeName("油机管理");
					oil.setNodeType(AreaDeptTreeConstants.NODE_TYPE_OIL);
					oil.setAreaId(areaId);
					oil.setInterfaceHeadId(interfaceHeadId);
					this.saveAreaDeptTree(oil);
				}
			}

			return factory;
		}
		// 删除时的操作
		if ("2".equals(updateType)) {
			// sql 查对应市区的 节点 改变factoryList的值
			String sql = " from AreaDeptTree areaDeptTree where areaDeptTree.nodeName = '" + areaId + "'";
			// sql1是对合作伙伴节点的查询
			String sql1 = " from AreaDeptTree areaDeptTree where areaDeptTree.nodeName = '" + name
					+ "' and nodeType = 'factory' and areaId='" + areaId + "'";
			List list = this.getInfoByCondition(sql);
			List list1 = this.getInfoByCondition(sql1);
			AreaDeptTree areaDeptTree = new AreaDeptTree();
			AreaDeptTree areaDeptTree1 = new AreaDeptTree();
			if (list.size() != 0) {
				areaDeptTree = (AreaDeptTree) list.get(0);
			}
			if (list1.size() != 0) {
				areaDeptTree1 = (AreaDeptTree) list1.get(0);
				String id = StaticMethod.null2String(areaDeptTree1.getId());
				String treeNodeId = StaticMethod.null2String(areaDeptTree1.getNodeId());
				// String factoryLists = areaDeptTree.getFactoryLists();
				// if(!id.equals("")){
				// if(factoryLists!=null&&!factoryLists.equals("")){
				// String[] factories = factoryLists.split(",");
				// String factoryLists1 = "";
				// for(int j=0;j<factories.length;j++){
				// if(factories[j].equals(name)){
				// factories[j] = "";
				// break;
				// }
				// }
				// for(int k=0;k<factories.length;k++){
				// if(!factories[k].equals("")&&k<=factories.length-1){
				// factoryLists1 += (factories[k]+",");
				// }
				// }
				// if(factoryLists1.length()>0&&factoryLists1.lastIndexOf(",")==factoryLists1.length()-1){
				// factoryLists1 = factoryLists1.substring(0,
				// factoryLists1.length()-1);
				// }
				// areaDeptTree.setFactoryLists(factoryLists1);
				// this.saveAreaDeptTree(areaDeptTree);
				// }
				// }
				/*
				 * id:EOMS-liujinlong-20090921162034
				 * 开发人邮箱：liujinlong@boco.com.cn 时间：2009-09-21
				 * 修改目的：删除合作伙伴时，单表一并删除（设置deleted字段为1）
				 */
				List list2 = this.getLeavesByNodeId(treeNodeId, "");
				Iterator it = list2.iterator();
				// PartnerUserMgr partnerUserMgr = (PartnerUserMgr)
				// ApplicationContextHolder
				// .getInstance().getBean("partnerUserMgr");
				// TawApparatusMgr tawApparatusMgr = (TawApparatusMgr)
				// ApplicationContextHolder
				// .getInstance().getBean("tawApparatusMgr");
				// TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr)
				// ApplicationContextHolder
				// .getInstance().getBean("tawPartnerCarMgr");
				// TawPartnerOilMgr tawPartnerOilMgr = (TawPartnerOilMgr)
				// ApplicationContextHolder
				// .getInstance().getBean("tawPartnerOilMgr");
				AreaDeptTree tree = null;
				String deptNodeId = null;// 合作伙伴节点id，用于仪器仪表、车辆管理、油机管理
				while (it.hasNext()) {
					tree = (AreaDeptTree) it.next();
					if (tree.getNodeType().equals(AreaDeptTreeConstants.NODE_TYPE_USER)) {
						partnerUserDao.removePartnerUserByTreeNodeId(tree.getNodeId());
					} else if (tree.getNodeType().equals(AreaDeptTreeConstants.NODE_TYPE_INSTRUMENT)) {
						deptNodeId = tree.getParentNodeId();
						tawApparatusDao.removeTawApparatusByDeptID(deptNodeId);
					} else if (tree.getNodeType().equals(AreaDeptTreeConstants.NODE_TYPE_CAR)) {
						deptNodeId = tree.getParentNodeId();
						tawPartnerCarDao.removeTawPartnerCarByDeptID(deptNodeId);
					} else if (tree.getNodeType().equals(AreaDeptTreeConstants.NODE_TYPE_OIL)) {
						deptNodeId = tree.getParentNodeId();
						tawPartnerOilDao.removeTawPartnerOilByDeptID(deptNodeId);
					}
				}
				/*
				 * 修改结束：EOMS-liujinlong-20090921162034
				 */
				this.removeAreaDeptTreeByNodeId(treeNodeId);

				return areaDeptTree1;
			}
		}

		// 修改时的操作
		// if("3".equals(updateType)){
		// }
		return areaDeptTree3;
	}

	public TawPartnerCarDao getTawPartnerCarDao() {
		return tawPartnerCarDao;
	}

	public void setTawPartnerCarDao(TawPartnerCarDao tawPartnerCarDao) {
		this.tawPartnerCarDao = tawPartnerCarDao;
	}

}