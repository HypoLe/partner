package com.boco.eoms.partner.baseinfo.util;


import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.mgr.TawPartnerCarMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.model.TawPartnerCar;

public class ReadExcelToObject {

	public void getPartnerUser(String fileName) {
		ITawSystemAreaManager iTawSystemAreaManager = (ITawSystemAreaManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemAreaManager");
		ITawSystemDeptManager iTawSystemDeptManager = (ITawSystemDeptManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDeptManager");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) ApplicationContextHolder
				.getInstance().getBean("areaDeptTreeMgr");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
				.getInstance().getBean("partnerUserMgr");
		try {
			InputStream is = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(is);
			Sheet dataSheet = wb.getSheet(0);
			String newDeptName = "";
			TawSystemDept tawSystemDept;
			PartnerUser partnerUser;
			AreaDeptTree areaDeptTree;
			Map areaIdtrueMap = getAreaIdtrue();
			Map areaIdMap = getAreaId();
			String duty = "";
			for (int i = 1; i < dataSheet.getRows(); i++) {
				tawSystemDept = new TawSystemDept();
				partnerUser = new PartnerUser();
				areaDeptTree = new AreaDeptTree();
				String areaName = dataSheet.getCell(2, i).getContents();
				String deptParentName = dataSheet.getCell(3, i).getContents()
						.trim();
				if ((areaName != null && !"".equals(areaName))
						&& (deptParentName != null && !""
								.equals(deptParentName))) {
					newDeptName = deptParentName + areaName + "分公司";
					tawSystemDept = iTawSystemDeptManager
							.getDeptinfoBydeptname(newDeptName, "0");
					String deptId = areaDeptTreeMgr
							.getNodeIdByNodeName(newDeptName);

					if (dataSheet.getCell(1, i).getContents() != null
							&& !"".equals(dataSheet.getCell(1, i).getContents()
									.trim())) {
						partnerUser.setUserId(dataSheet.getCell(1, i)
								.getContents());
					}
					if (dataSheet.getCell(4, i).getContents() != null
							&& !"".equals(dataSheet.getCell(4, i).getContents()
									.trim())) {
						partnerUser.setName(dataSheet.getCell(4, i)
								.getContents());
					}
					if (dataSheet.getCell(5, i).getContents() != null
							&& !"".equals(dataSheet.getCell(5, i).getContents()
									.trim())) {
						if (dataSheet.getCell(5, i).getContents()
								.equals("男")) {
							partnerUser.setSex("112020201");
						} else {
							partnerUser.setSex("112020202");
						}
					}
					if (dataSheet.getCell(6, i).getContents() != null
							&& !"".equals(dataSheet.getCell(6, i).getContents()
									.trim())) {
						partnerUser.setStation(dataSheet.getCell(6, i)
								.getContents());
					} else {
						partnerUser.setStation(" ");
					}
					if (dataSheet.getCell(7, i).getContents() != null
							&& !"".equals(dataSheet.getCell(7, i).getContents()
									.trim())) {
						partnerUser.setPersonCardNo(dataSheet.getCell(7, i)
								.getContents());
					}else {
						partnerUser.setPersonCardNo("511111111111111111");
					}
					if (dataSheet.getCell(8, i).getContents() != null
							&& !"".equals(dataSheet.getCell(8, i).getContents()
									.trim())) {
						partnerUser.setRemark(dataSheet.getCell(8, i)
								.getContents());

					} else {
						partnerUser.setRemark(" ");
					}
					if (dataSheet.getCell(9, i).getContents() != null
							&& !"".equals(dataSheet.getCell(9, i).getContents()
									.trim())) {
						if (dataSheet.getCell(9, i).getContents()
								.equals("初级")) {
							partnerUser.setWorkLicenseLevel("112130101");
						} else if (dataSheet.getCell(9, i).getContents()
								.equals("中级")) {
							partnerUser.setWorkLicenseLevel("112130102");
						}
					}
					if (dataSheet.getCell(10, i).getContents() != null
							&& !"".equals(dataSheet.getCell(10, i)
									.getContents())) {
						partnerUser.setLicenseNo(dataSheet.getCell(10, i)
								.getContents());
					} else {
						partnerUser.setLicenseNo(" ");
					}
					if (dataSheet.getCell(11, i).getContents() != null
							&& !"".equals(dataSheet.getCell(11, i)
									.getContents())) {
						partnerUser.setPhoto(dataSheet.getCell(11, i)
								.getContents());
						partnerUser.setAccessory(dataSheet.getCell(11, i)
								.getContents());
					} else {
						partnerUser.setPhoto("");
						partnerUser.setAccessory("");
					}
					if (dataSheet.getCell(12, i).getContents() != null
							&& !"".equals(dataSheet.getCell(12, i)
									.getContents())) {

						duty = dataSheet.getCell(12, i).getContents();
						if (duty.contains(" ")) {
							int prfix = dataSheet.getCell(12, i).getContents()
									.trim().indexOf(" ");
							String temp1 = duty.substring(0, prfix);
							String temp2 = duty.substring(prfix, duty.length());
							duty = temp1 + temp2;

						}
						partnerUser.setResponsibility(duty);
					}else {
						partnerUser.setResponsibility(" ");
					}
					if (dataSheet.getCell(13, i).getContents() != null
							&& !"".equals(dataSheet.getCell(13, i)
									.getContents())) {
						partnerUser.setMaintainLevel(dataSheet.getCell(13, i)
								.getContents());
					} else {
						partnerUser.setMaintainLevel("112130301");
					}
					if (dataSheet.getCell(14, i).getContents() != null
							&& !"".equals(dataSheet.getCell(14, i)
									.getContents())) {
						partnerUser.setMobilePhone(dataSheet.getCell(14, i)
								.getContents());
						partnerUser.setPhone(dataSheet.getCell(14, i)
								.getContents());
					}else if (dataSheet.getCell(14, i).getContents().equals("")||dataSheet.getCell(14, i).getContents().equals("鏆傛棤")) {
						partnerUser.setMobilePhone("11911911911");
						partnerUser.setPhone("11911911911");
					}
					else {
						partnerUser.setMobilePhone("11911911911");
						partnerUser.setPhone("11911911911");
					}
					if (dataSheet.getCell(15, i).getContents() != null
							&& !"".equals(dataSheet.getCell(15, i)
									.getContents())) {
						if (dataSheet.getCell(15, i).getContents()
								.equals("1")) {
							partnerUser.setServiceProfessional("112120102");
						} else if (dataSheet.getCell(15, i).getContents()
								.trim().equals("2")) {
							partnerUser.setServiceProfessional("112120106");
						}

					}
					partnerUser.setAreaidtrue((String) areaIdtrueMap
							.get(areaName));
					partnerUser.setAreaName(areaName);
					partnerUser.setAreaNames(areaName);
					partnerUser.setDeptId(deptId);
					partnerUser.setDeptName(newDeptName);
					partnerUser.setAreaId((String) areaIdMap.get(areaName));
					partnerUser.setPartnerid((String) getDeptParentId().get(
							newDeptName));
					partnerUser.setBigpartnerid((String) getDeptParentId().get(
							deptParentName));
					// partnerUser.setLicenseNo("123");
					partnerUser.setLicenseType("112020101");
					partnerUser.setDiploma("112020302");// 
					partnerUser.setBirthdey("1900-12-31");// 
					//partnerUser.setWorkTime(new Date());// 
					partnerUser.setDeptWorkTime(new Date());// 
					partnerUser.setPost("112020501");// 
					partnerUser.setPostState("112020401");// 
					partnerUser.setEmail("email@email.com");//
					partnerUser.setDeleted("0");
					// partnerUser.setWorkLicenseLevel("112130101");// 
					partnerUser.setWorkLicenseMajor("112130201");// 
					partnerUser.setProjectName("项目名称");// 
					partnerUser.setProjectProperty("112130401");// 
					partnerUser.setMaintainLevel("112130301");// 
					// partnerUser.setServiceProfessional("112120101");// 
					// partnerUser.setResponsibility("112130501");// 
					partnerUser.setIsbackbone("1");// 
					partnerUser.setTreeNodeId(deptId + "01");
				//	partnerUser.setAccessory(" ");
					// partnerUser.setPhoto(" ");
					partnerUserMgr.savePartnerUser(partnerUser);
					System.out.println(partnerUser.getName()+"操作第"+i+"人"+newDeptName);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getPartnerCar(String fileName) {
		try {
			AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) ApplicationContextHolder
					.getInstance().getBean("areaDeptTreeMgr");
			TawPartnerCarMgr tawPartnerCarMgr = (TawPartnerCarMgr) ApplicationContextHolder
					.getInstance().getBean("tawPartnerCarMgr");
			InputStream is = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(is);
			Sheet dataSheet = wb.getSheet(0);
			String newDeptName = "";
			TawPartnerCar tawPartnerCar;
			Map areaIdMap = getAreaId();
			Map areaIdtrueMap = getAreaIdtrue();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String partnerId = "";
			String bigPartnerId = "";
			for (int i = 1; i < dataSheet.getRows(); i++) {
				tawPartnerCar = new TawPartnerCar();
				String areaName = dataSheet.getCell(2, i).getContents();
				String deptParentName = dataSheet.getCell(3, i).getContents();
				if ((areaName != null && !"".equals(areaName))
						&& (deptParentName != null && !""
								.equals(deptParentName))) {
					newDeptName = deptParentName + areaName + "分公司";

					String deptId = areaDeptTreeMgr
							.getNodeIdByNodeName(newDeptName);

					tawPartnerCar.setDimensionalCode("erweima");
					// tawPartnerCar.setBrand("姹借溅鍝佺墝");
					tawPartnerCar.setModel("model");
					tawPartnerCar.setDrivingLicense("xczh-01");
					tawPartnerCar.setDrivingLicenseThumbnail(" ");

					tawPartnerCar.setFactory("生产厂家");

					tawPartnerCar.setEngineNo("engine-1");//
					tawPartnerCar.setAirDisplacement("11L");//
					tawPartnerCar.setAnnualCheckData(new Date());//

					tawPartnerCar.setServiceProfessional("112120101");//
					tawPartnerCar.setStartUseMilemeter("0");//

					tawPartnerCar.setUse("112130601");//
					tawPartnerCar.setPiece("100");
					tawPartnerCar.setDeleted("0");
					tawPartnerCar.setDept_id(deptId);
					tawPartnerCar.setArea_id((String) areaIdMap.get(areaName));
					tawPartnerCar.setAreaidtrue((String) areaIdtrueMap
							.get(areaName));
					partnerId = (String) getDeptParentId().get(newDeptName);
					bigPartnerId = (String) getDeptParentId().get(
							deptParentName);
					tawPartnerCar.setPartnerid(partnerId);
					tawPartnerCar.setBigpartnerid(bigPartnerId);
					AreaDeptTree areaDeptTree = areaDeptTreeMgr
							.getAreaDeptTreeByNodeId(deptId + "03");
					areaDeptTree.setIsShow("0");
					areaDeptTreeMgr.saveAreaDeptTree(areaDeptTree);
					if (dataSheet.getCell(6, i).getContents() != null
							&& !"".equals(dataSheet.getCell(6, i).getContents()
									.trim())) {
						tawPartnerCar.setCar_number(dataSheet.getCell(6, i)
								.getContents());
					}
					if (dataSheet.getCell(2, i).getContents() != null
							&& !"".equals(dataSheet.getCell(2, i).getContents()
									.trim())) {
						tawPartnerCar.setCity(dataSheet.getCell(2, i)
								.getContents());
					}
					if (dataSheet.getCell(4, i).getContents() != null
							&& !"".equals(dataSheet.getCell(4, i).getContents()
									.trim())) {
						tawPartnerCar.setBrand(dataSheet.getCell(4, i)
								.getContents());
					}
					if (dataSheet.getCell(5, i).getContents() != null
							&& !"".equals(dataSheet.getCell(5, i).getContents()
									.trim())) {
						if (dataSheet.getCell(5, i).getContents()
								.equals("自购")) {
							tawPartnerCar.setOwnershipType("112130701");
						} else {
							tawPartnerCar.setOwnershipType("112130702");
						}

					}else {
						tawPartnerCar.setOwnershipType("112130702");
					}
					if (dataSheet.getCell(7, i).getContents() != null
							&& !"".equals(dataSheet.getCell(7, i).getContents()
									.trim())) {
						tawPartnerCar.setStation(dataSheet.getCell(7, i)
								.getContents());
					}
					if (dataSheet.getCell(8, i).getContents() != null
							&& !"".equals(dataSheet.getCell(8, i).getContents()
									.trim())) {
						tawPartnerCar.setPurchaseTime(dataSheet.getCell(8, i).getContents());
					}else {
						tawPartnerCar.setPurchaseTime("1900-12-31");
					}
					if (dataSheet.getCell(9, i).getContents() != null
							&& !"".equals(dataSheet.getCell(9, i).getContents()
									.trim())) {
						if (dataSheet.getCell(9, i).getContents()
								.equals("两驱")) {
							tawPartnerCar.setDriveType("1121501");
						} else if (dataSheet.getCell(9, i).getContents()
								.equals("四驱")) {
							tawPartnerCar.setDriveType("1121502");
						}else if (dataSheet.getCell(9, i).getContents()
								.equals("微型车")) {
							tawPartnerCar.setDriveType("1121503");
						}else if (dataSheet.getCell(9, i).getContents()
								.equals("厢式货车")) {
							tawPartnerCar.setDriveType("1121504");
						}else if (dataSheet.getCell(9, i).getContents()
								.equals("越野")) {
							tawPartnerCar.setDriveType("1121505");
						}

					}else {
						tawPartnerCar.setDriveType("1121502");
					}
					if (dataSheet.getCell(10, i).getContents() != null
							&& !"".equals(dataSheet.getCell(10, i)
									.getContents())) {
						tawPartnerCar.setStart_time(sdf.parse("20"+(dataSheet
								.getCell(10, i).getContents())));
					} else {
						tawPartnerCar.setStart_time(sdf.parse("1900-12-31"));
					}
					if (dataSheet.getCell(11, i).getContents() != null
							&& !"".equals(dataSheet.getCell(11, i)
									.getContents())) {
						if (dataSheet.getCell(11, i).getContents()
								.equals("至今")) {
							tawPartnerCar.setEndTime(sdf.parse("2099-12-31"));
						} else {
							tawPartnerCar.setEndTime(sdf
									.parse("20"+(dataSheet.getCell(11, i)
											.getContents())));
						}

					}else {
						tawPartnerCar.setEndTime(sdf.parse("2099-12-31"));
					}

					if (dataSheet.getCell(12, i).getContents() != null
							&& !"".equals(dataSheet.getCell(12, i)
									.getContents())) {
						tawPartnerCar.setThumbnail(dataSheet.getCell(12, i)
								.getContents());
					} else {
						tawPartnerCar.setThumbnail(" ");
					}
					if (dataSheet.getCell(13, i).getContents() != null
							&& !"".equals(dataSheet.getCell(13, i)
									.getContents())) {
						if (dataSheet.getCell(13, i).getContents()
								.equals("1")) {
							tawPartnerCar.setCategory("112131101");
						} else if (dataSheet.getCell(13, i).getContents()
								.trim().equals("2")) {
							tawPartnerCar.setCategory("112131102");
						}

					}
					
					tawPartnerCarMgr.saveTawPartnerCar(tawPartnerCar);
					System.out.println(tawPartnerCar.getCar_number()+"操作第"+i+"车辆"+newDeptName);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map getAreaIdtrue() {
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");
		List allPartnerDepts = partnerDeptMgr.getPartnerDepts();
		Map areaIdMap = new Hashtable();
		PartnerDept partnerDept;
		for (int i = 0; i < allPartnerDepts.size(); i++) {
			partnerDept = (PartnerDept) allPartnerDepts.get(i);
			if (areaIdMap.get(partnerDept.getAreaName()) == null&&(partnerDept
					.getAreaId()!=null&&!"".equals(partnerDept.getAreaId()))) {
				areaIdMap.put(partnerDept.getAreaName(), partnerDept
						.getAreaId());
			}

		}
		return areaIdMap;
	}

	public Map getAreaId() {
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) ApplicationContextHolder
				.getInstance().getBean("areaDeptTreeMgr");
		List allAreaDept = areaDeptTreeMgr.getChildNodes("101");
		Map areaIdMap = new Hashtable();
		AreaDeptTree areaDeptTree;
		for (int i = 0; i < allAreaDept.size(); i++) {
			areaDeptTree = (AreaDeptTree) allAreaDept.get(i);
			if (areaIdMap.get(areaDeptTree.getNodeName()) == null) {
				areaIdMap.put(areaDeptTree.getNodeName(), areaDeptTree
						.getNodeId());
			}

		}
		return areaIdMap;
	}

	public Map getDeptParentId() {
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");
		List partnerDeptList = partnerDeptMgr.getPartnerDepts();
		Map parentIdMap = new Hashtable();
		PartnerDept partnerDept;
		for (int i = 0; i < partnerDeptList.size(); i++) {
			partnerDept = (PartnerDept) partnerDeptList.get(i);
			if (parentIdMap.get(partnerDept.getName()) == null) {
				parentIdMap.put(partnerDept.getName(), partnerDept.getId());
			}
		}

		return parentIdMap;
	}

	public List dutyList(String fileName) {
		List arrList = new ArrayList();
		try {
			InputStream is = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(is);
			Sheet dataSheet = wb.getSheet(0);
			for (int i = 0; i < dataSheet.getRows(); i++) {
				if (dataSheet.getCell(12, i).getContents() != null
						&& !"".equals(dataSheet.getCell(12, i).getContents()
								.trim())) {
					String newDuty = "";
					String duty = dataSheet.getCell(12, i).getContents();
					if (dataSheet.getCell(12, i).getContents().contains(
							" ")) {
						int prfix = dataSheet.getCell(12, i).getContents()
								.trim().indexOf(" ");
						String temp1 = duty.substring(0, prfix);
						String temp2 = duty.substring(prfix, duty.length());
						duty = newDuty = temp1 + temp2;
					} else {
						arrList.add(duty);
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return arrList;
	}

	public List removeDuplicateDuty(List list) {
		Set someSet = new HashSet(list);
		Iterator iterator = someSet.iterator();
		List tempList = new ArrayList();
		int i = 0;
		while (iterator.hasNext()) {
			tempList.add(iterator.next().toString());
			i++;
		}
		return tempList;
	}

	public Map addDutyDict() {
		ITawSystemDictTypeManager iTawSystemDictTypeManager = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("iTawSystemDictTypeManager");
		List allDutys = removeDuplicateDuty(dutyList("D:\\下载专用\\document\\person.xls"));
		TawSystemDictType tawSystemDictType;
		Map dutyType = new Hashtable();
		String dictCode = "";
		for (int i = 0; i < allDutys.size(); i++) {
			tawSystemDictType = new TawSystemDictType();
			tawSystemDictType.setDictName((String) allDutys.get(i));
			if (i < 10) {
				dictCode = "11213050" + i;
			} else {
				dictCode = "1121305" + i;
			}
			tawSystemDictType.setDictCode(dictCode);
			if (dutyType.get(allDutys.get(i)) == null)
				dutyType.put(allDutys.get(i), dictCode);
			iTawSystemDictTypeManager.saveTawSystemDictType(tawSystemDictType);

		}
		return dutyType;
	}

	public static void main(String[] args) {
		ReadExcelToObject readExcelToObject = new ReadExcelToObject();
		//readExcelToObject.getPartnerUser("D:\\下载专用\\document\\person2.xls");
		readExcelToObject.getPartnerCar("D:\\下载专用\\document\\car2.xls");
		// System.out.println(readExcelToObject.getAreaId().get("鐜夋邯"));
		Map map = readExcelToObject.getDeptParentId();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			System.out.println("Key: " + e.getKey() + "; Value: "
					+ e.getValue());
		}

	}
}
