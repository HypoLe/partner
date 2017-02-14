package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrOltBbuOfficeRelationJDBCDao;
import com.boco.activiti.partner.process.po.PnrOltBbuOfficeMainModel;

public class PnrOltBbuOfficeRelationDaoJDBC extends JdbcDaoSupport implements
		IPnrOltBbuOfficeRelationJDBCDao {
	
	/**
	 * 
	 * 查询OLT-BBU机房优化申请流程工单详情 （工单信息+机房信息+优化信息+环节人员等）
	 *  
	 * @param processInstanceId
	 * @return
	 */
	public PnrOltBbuOfficeMainModel findPnrOltBbuOfficeMainByProcessInstanceId(String processInstanceId){
		String sql=	"select m.*," +
			"       l.id                        as relation_id," + 
			"       l.room_id," + 
			"       r.themeinterface            as room_themeinterface," + 
			"       r.serial_number," + 
			"       r.jf_city," + 
			"       r.jf_country," + 
			"       r.jf_address_name," + 
			"       r.olt_number," + 
			"       r.total_user_number," + 
			"       r.voice_user," + 
			"       r.Broadband_User," + 
			"       r.Iptv_User," + 
			"       r.Is_No_Bbu," + 
			"       r.Bbu_Number," + 
			"       r.Line_Total_Investment," + 
			"       l.Is_Need_Rod_Investment," + 
			"       l.Is_Need_Cable_Investment," + 
			"       l.New_Built_Rod_Length," + 
			"       l.New_Built_Rod_Investment," + 
			"       l.Original_Cable_Route," + 
			"       l.New_Cable_Route," + 
			"       l.New_Paragraph," + 
			"       l.New_Line_Routing_Diagram," + 
			"       l.Cable_Cloth_Core_Number," + 
			"       l.Cable_Cloth_Length," + 
			"       l.Cable_Investment," + 
			"       l.Board_Demand," + 
			"       l.Light_Module_Demand," + 
			"       l.Trans_Board_Demand," + 
			"       l.Trans_Light_Module_Demand," + 
			"       l.Equipment_Investment," + 
			"       l.Jf_Remark\n" + 
			"  from pnr_act_transfer_office_main m," + 
			"       pnr_olt_bbu_office_relation  l," + 
			"       pnr_olt_bbu_room             r" + 
			" where m.process_instance_id = l.process_instance_id(+)" + 
			"   and l.room_id = r.id(+)" + 
			"   and m.process_instance_id = '"+processInstanceId+"'";
		
		System.out.println("-----查询OLT-BBU机房优化申请流程工单详情sql="+sql);
		
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		PnrOltBbuOfficeMainModel pnrOltBbuOfficeMainModel = new PnrOltBbuOfficeMainModel();
		if (list != null && list.size() > 0) {
			Map map = list.get(0);
			pnrOltBbuOfficeMainModel.setId(map.get("ID") == null ? null: map.get("ID").toString());
			pnrOltBbuOfficeMainModel.setTheme(map.get("THEME") == null ? null: map.get("THEME").toString());
			try {
				pnrOltBbuOfficeMainModel.setCreateTime(map.get("CREATE_TIME") == null ? null:format.parse(map.get("CREATE_TIME").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			try {
				pnrOltBbuOfficeMainModel.setSendTime(map.get("SEND_TIME") == null ? null:format.parse(map.get("SEND_TIME").toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setInitiator(map.get("INITIATOR") == null ? null: map.get("INITIATOR").toString());
			pnrOltBbuOfficeMainModel.setTaskAssignee(map.get("TASK_ASSIGNEE") == null ? null: map.get("TASK_ASSIGNEE").toString());
			pnrOltBbuOfficeMainModel.setConnectPerson(map.get("connect_person") == null ? null: map.get("connect_person").toString());
			pnrOltBbuOfficeMainModel.setProcessLimit(map.get("process_limit") == null ? null:Double.parseDouble( map.get("process_limit").toString()));
			pnrOltBbuOfficeMainModel.setFaultSource(map.get("fault_source") == null ? null: map.get("fault_source").toString());
			pnrOltBbuOfficeMainModel.setFaultDescription(map.get("fault_description") == null ? null: map.get("fault_description").toString());
			pnrOltBbuOfficeMainModel.setSubType(map.get("sub_type") == null ? null: map.get("sub_type").toString());
			pnrOltBbuOfficeMainModel.setProcessInstanceId(map.get("process_instance_id") == null ? null: map.get("process_instance_id").toString());
			pnrOltBbuOfficeMainModel.setState(map.get("state") == null ? null:Integer.parseInt(map.get("state").toString()));
			try {
				pnrOltBbuOfficeMainModel.setArchivingTime(map.get("archiving_time") == null ? null:format.parse(map.get("archiving_time").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setTaskAssigneeJSON(map.get("task_assignee_json") == null ? null:map.get("task_assignee_json").toString());
			try {
				pnrOltBbuOfficeMainModel.setLastReplyTime(map.get("last_Reply_Time") == null ? null:format.parse(map.get("last_Reply_Time").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pnrOltBbuOfficeMainModel.setSaveDraftDate(map.get("save_draft_date") == null ? null:format.parse(map.get("save_draft_date").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setAttachmentsNum(map.get("attachments_num") == null ? null:Integer.parseInt(map.get("attachments_num").toString()));
			try {
				pnrOltBbuOfficeMainModel.setEndTime(map.get("end_time") == null ? null:format.parse(map.get("end_time").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setOneInitiator(map.get("one_initiator") == null ? null: map.get("one_initiator").toString());
			try {
				pnrOltBbuOfficeMainModel.setSecondSendTime(map.get("second_send_time") == null ? null:format.parse(map.get("second_send_time").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setSecondInitiator(map.get("second_initiator") == null ? null: map.get("second_initiator").toString());
			try {
				pnrOltBbuOfficeMainModel.setThirdSendTime(map.get("third_send_time") == null ? null:format.parse(map.get("third_send_time").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setEngineer(map.get("engineer") == null ? null: map.get("engineer").toString());
			pnrOltBbuOfficeMainModel.setInstallAddress(map.get("install_address") == null ? null: map.get("install_address").toString());
			pnrOltBbuOfficeMainModel.setPattern(map.get("pattern") == null ? null: map.get("pattern").toString());
			pnrOltBbuOfficeMainModel.setCity(map.get("city") == null ? null: map.get("city").toString());
			pnrOltBbuOfficeMainModel.setStationName(map.get("station_name") == null ? null: map.get("station_name").toString());
			pnrOltBbuOfficeMainModel.setSpliceBoxName(map.get("splice_box_name") == null ? null: map.get("splice_box_name").toString());
			pnrOltBbuOfficeMainModel.setCableNumber(map.get("cable_number") == null ? null: map.get("cable_number").toString());
			pnrOltBbuOfficeMainModel.setBranchBoxNumber(map.get("branch_box_number") == null ? null: map.get("branch_box_number").toString());
			pnrOltBbuOfficeMainModel.setFirstOpticalNumber(map.get("first_optical_number") == null ? null: map.get("first_optical_number").toString());
			pnrOltBbuOfficeMainModel.setFirstOpticalPort(map.get("first_optical_port") == null ? null: map.get("first_optical_port").toString());
			pnrOltBbuOfficeMainModel.setSecondOpticalNumber(map.get("second_optical_number") == null ? null: map.get("second_optical_number").toString());
			pnrOltBbuOfficeMainModel.setSecondOpticalPort(map.get("second_optical_port") == null ? null: map.get("second_optical_port").toString());
			pnrOltBbuOfficeMainModel.setSpliceBoxNumber(map.get("splice_box_number") == null ? null: map.get("splice_box_number").toString());
			pnrOltBbuOfficeMainModel.setSpliceBoxPort(map.get("splice_box_port") == null ? null: map.get("splice_box_port").toString());
			pnrOltBbuOfficeMainModel.setMaleGuestNumber(map.get("male_guest_number") == null ? null: map.get("male_guest_number").toString());
			pnrOltBbuOfficeMainModel.setFaileSite(map.get("failed_site") == null ? null: map.get("failed_site").toString());
			pnrOltBbuOfficeMainModel.setIsCustomers(map.get("is_customers") == null ? null: map.get("is_customers").toString());
			pnrOltBbuOfficeMainModel.setSpecialty(map.get("specialty") == null ? null: map.get("specialty").toString());
			pnrOltBbuOfficeMainModel.setBusiNbr(map.get("busi_nbr") == null ? null: map.get("busi_nbr").toString());
			pnrOltBbuOfficeMainModel.setSiteCd(map.get("site_cd") == null ? null: map.get("site_cd").toString());
			pnrOltBbuOfficeMainModel.setCcpCd(map.get("ccp1_cd") == null ? null: map.get("ccp1_cd").toString());
			pnrOltBbuOfficeMainModel.setTransferOfficeId(map.get("transfer_office_id") == null ? null: map.get("transfer_office_id").toString());
			pnrOltBbuOfficeMainModel.setOperator(map.get("operator") == null ? null: map.get("operator").toString());
			pnrOltBbuOfficeMainModel.setBarrierNumber(map.get("barrier_number") == null ? null: map.get("barrier_number").toString());
			pnrOltBbuOfficeMainModel.setWorkOrderType(map.get("work_order_type") == null ? null: map.get("work_order_type").toString());
			pnrOltBbuOfficeMainModel.setWorkOrderDegree(map.get("work_order_degree") == null ? null: map.get("work_order_degree").toString());
			pnrOltBbuOfficeMainModel.setKeyWord(map.get("key_word") == null ? null: map.get("key_word").toString());
			try {
				pnrOltBbuOfficeMainModel.setSubmitApplicationTime(map.get("submit_application_time") == null ? null: format.parse(map.get("submit_application_time").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setWorkOrderNumber(map.get("work_order_number") == null ? null: map.get("work_order_number").toString());
			pnrOltBbuOfficeMainModel.setCreateWork(map.get("create_work") == null ? null: map.get("create_work").toString());
			pnrOltBbuOfficeMainModel.setCountryCSJ(map.get("country_csj") == null ? null: map.get("country_csj").toString());
			pnrOltBbuOfficeMainModel.setCityCSJ(map.get("city_csj") == null ? null: map.get("city_csj").toString());
			pnrOltBbuOfficeMainModel.setCityGS(map.get("city_gs") == null ? null: map.get("city_gs").toString());
			pnrOltBbuOfficeMainModel.setSubTypeName(map.get("sub_type_name") == null ? null: map.get("sub_type_name").toString());
			pnrOltBbuOfficeMainModel.setWorkOrderTypeName(map.get("workorder_type_name") == null ? null: map.get("workorder_type_name").toString());
			pnrOltBbuOfficeMainModel.setRepeatState(map.get("repeat_state") == null ? null: map.get("repeat_state").toString());
			pnrOltBbuOfficeMainModel.setDoFlag(map.get("do_flag") == null ? null: map.get("do_flag").toString());
			pnrOltBbuOfficeMainModel.setSecondRepeatState(map.get("second_repeat_state") == null ? null: map.get("second_repeat_state").toString());
			pnrOltBbuOfficeMainModel.setWorkType(map.get("work_type") == null ? null: map.get("work_type").toString());
			pnrOltBbuOfficeMainModel.setProjectAmount(map.get("project_amount") == null ? null:Double.parseDouble(map.get("project_amount").toString()));
			pnrOltBbuOfficeMainModel.setProvinceLine(map.get("province_line") == null ? null: map.get("province_line").toString());
			pnrOltBbuOfficeMainModel.setProvinceManage(map.get("province_manage") == null ? null: map.get("province_manage").toString());
			pnrOltBbuOfficeMainModel.setVersionFlag(map.get("version_flag") == null ? null: map.get("version_flag").toString());
			pnrOltBbuOfficeMainModel.setCountry(map.get("country") == null ? null: map.get("country").toString());
			pnrOltBbuOfficeMainModel.setSecondCreateWork(map.get("second_creatework") == null ? null: map.get("second_creatework").toString());
			pnrOltBbuOfficeMainModel.setCityLineCharge(map.get("city_line_charge") == null ? null: map.get("city_line_charge").toString());
			pnrOltBbuOfficeMainModel.setCityLineDirector(map.get("city_line_director") == null ? null: map.get("city_line_director").toString());
			pnrOltBbuOfficeMainModel.setCityManageCharge(map.get("city_manage_charge") == null ? null: map.get("city_manage_charge").toString());
			pnrOltBbuOfficeMainModel.setCityManageDirector(map.get("city_manage_director") == null ? null: map.get("city_manage_director").toString());
			pnrOltBbuOfficeMainModel.setProvinceLineCharge(map.get("province_line_charge") == null ? null: map.get("province_line_charge").toString());
			pnrOltBbuOfficeMainModel.setProvinceManageCharge(map.get("province_manage_charge") == null ? null: map.get("province_manage_charge").toString());
			pnrOltBbuOfficeMainModel.setBearService(map.get("bear_service") == null ? null: map.get("bear_service").toString());
			pnrOltBbuOfficeMainModel.setRollbackFlag(map.get("rollback_flag") == null ? null: map.get("rollback_flag").toString());
			pnrOltBbuOfficeMainModel.setPrecheckFlag(map.get("precheck_flag") == null ? null: map.get("precheck_flag").toString());
			pnrOltBbuOfficeMainModel.setSheetId(map.get("sheet_id") == null ? null: map.get("sheet_id").toString());
			pnrOltBbuOfficeMainModel.setCompensate(map.get("compensate") == null ? null:Double.parseDouble(map.get("compensate").toString()));
			try {
				pnrOltBbuOfficeMainModel.setDistributedInterfaceTime(map.get("distributed_interface_time") == null ? null:format.parse(map.get("distributed_interface_time").toString()));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setLineName(map.get("line_name") == null ? null: map.get("line_name").toString());
			pnrOltBbuOfficeMainModel.setProjectStartPoint(map.get("project_start_point") == null ? null: map.get("project_start_point").toString());
			pnrOltBbuOfficeMainModel.setProjectEndPoint(map.get("project_end_point") == null ? null: map.get("project_end_point").toString());
			pnrOltBbuOfficeMainModel.setSpecificLocation(map.get("specific_location") == null ? null: map.get("specific_location").toString());
			pnrOltBbuOfficeMainModel.setInitiatorDept(map.get("initiator_dept") == null ? null: map.get("initiator_dept").toString());
			pnrOltBbuOfficeMainModel.setInitiatorMobilePhone(map.get("initiator_mobile_phone") == null ? null: map.get("initiator_mobile_phone").toString());
			pnrOltBbuOfficeMainModel.setMainSceneId(map.get("main_scene_id") == null ? null: map.get("main_scene_id").toString());
			pnrOltBbuOfficeMainModel.setShowChildSceneId(map.get("show_child_scene_id") == null ? null: map.get("show_child_scene_id").toString());
			pnrOltBbuOfficeMainModel.setChildSceneIds(map.get("child_scene_ids") == null ? null: map.get("child_scene_ids").toString());
			pnrOltBbuOfficeMainModel.setDeptHead(map.get("dept_head") == null ? null: map.get("dept_head").toString());
			pnrOltBbuOfficeMainModel.setDeptHeadMobilePhone(map.get("dept_head_mobile_phone") == null ? null: map.get("dept_head_mobile_phone").toString());
			pnrOltBbuOfficeMainModel.setCalculateIncomeRatio(map.get("calculate_income_ratio") == null ? null:Double.parseDouble(map.get("calculate_income_ratio").toString()));
			pnrOltBbuOfficeMainModel.setConstructionReasons(map.get("construction_reasons") == null ? null: map.get("construction_reasons").toString());
			pnrOltBbuOfficeMainModel.setNetworkStatus(map.get("network_status") == null ? null: map.get("network_status").toString());
			pnrOltBbuOfficeMainModel.setConstructionMainContent(map.get("construction_main_content") == null ? null: map.get("construction_main_content").toString());
			pnrOltBbuOfficeMainModel.setLayingCable(map.get("laying_cable") == null ? null:Double.parseDouble(map.get("laying_cable").toString()));
			pnrOltBbuOfficeMainModel.setExcavationTrench(map.get("excavation_trench") == null ? null:Double.parseDouble(map.get("excavation_trench").toString()));
			pnrOltBbuOfficeMainModel.setRepairPipeline(map.get("repair_pipeline") == null ? null: Double.parseDouble(map.get("repair_pipeline").toString()));
			pnrOltBbuOfficeMainModel.setRightingDemolitionPole(map.get("righting_demolition_pole") == null ? null:Double.parseDouble(map.get("righting_demolition_pole").toString()));
			pnrOltBbuOfficeMainModel.setWireLaying(map.get("wire_laying") == null ? null:Double.parseDouble(map.get("wire_laying").toString()));
			pnrOltBbuOfficeMainModel.setFiberOpticCableConnector(map.get("fiber_optic_cable_connector") == null ? null:Double.parseDouble(map.get("fiber_optic_cable_connector").toString()));
			pnrOltBbuOfficeMainModel.setMainQuantityOther(map.get("main_quantity_other") == null ? null:map.get("main_quantity_other").toString());
			pnrOltBbuOfficeMainModel.setCreateStr(map.get("create_str") == null ? null:map.get("create_str").toString());
			pnrOltBbuOfficeMainModel.setDeleteStr(map.get("delete_str") == null ? null:map.get("delete_str").toString());
			pnrOltBbuOfficeMainModel.setDaiweiAuditPerson(map.get("daiwei_audit_person") == null ? null:map.get("daiwei_audit_person").toString());
			pnrOltBbuOfficeMainModel.setOrderAuditPerson(map.get("order_audit_person") == null ? null:map.get("order_audit_person").toString());
			pnrOltBbuOfficeMainModel.setProjectName(map.get("project_name") == null ? null:map.get("project_name").toString());
			pnrOltBbuOfficeMainModel.setOverhaulType(map.get("overhual_type") == null ? null:map.get("overhual_type").toString());
			pnrOltBbuOfficeMainModel.setLineType(map.get("line_type") == null ? null:map.get("line_type").toString());
			pnrOltBbuOfficeMainModel.setLayingType(map.get("laying_type") == null ? null:map.get("laying_type").toString());
			pnrOltBbuOfficeMainModel.setMiddlePart(map.get("middle_part") == null ? null:map.get("middle_part").toString());
			pnrOltBbuOfficeMainModel.setCableType(map.get("cable_type") == null ? null:map.get("cable_type").toString());
			pnrOltBbuOfficeMainModel.setCoreNum(map.get("core_num") == null ? null:map.get("core_num").toString());
			pnrOltBbuOfficeMainModel.setCreateLongitude(map.get("create_longitude") == null ? null:map.get("create_longitude").toString());
			pnrOltBbuOfficeMainModel.setCreateLatitude(map.get("create_latitude") == null ? null:map.get("create_latitude").toString());
			pnrOltBbuOfficeMainModel.setEndLongitude(map.get("end_longitude") == null ? null:map.get("end_longitude").toString());
			pnrOltBbuOfficeMainModel.setEndLatitude(map.get("end_latitude") == null ? null:map.get("end_latitude").toString());
			pnrOltBbuOfficeMainModel.setChargeName(map.get("charge_name") == null ? null:map.get("charge_name").toString());
			pnrOltBbuOfficeMainModel.setChargeTel(map.get("charge_tel") == null ? null:map.get("charge_tel").toString());
			pnrOltBbuOfficeMainModel.setSubsidyNumber(map.get("subsidy_number") == null ? null:map.get("subsidy_number").toString());
			pnrOltBbuOfficeMainModel.setLongLeatherMoney(map.get("longleather_money") == null ? null:Double.parseDouble(map.get("longleather_money").toString()));
			pnrOltBbuOfficeMainModel.setHoleMoney(map.get("hole_money") == null ? null:Double.parseDouble(map.get("hole_money").toString()));
			pnrOltBbuOfficeMainModel.setIsRemind(map.get("is_remind") == null ? null:Integer.parseInt(map.get("is_remind").toString()));
			pnrOltBbuOfficeMainModel.setJobOrderType(map.get("job_order_type") == null ? null:map.get("job_order_type").toString());
			pnrOltBbuOfficeMainModel.setBatch(map.get("batch") == null ? null:map.get("batch").toString());
			try {
				pnrOltBbuOfficeMainModel.setStorageTime(map.get("storage_time") == null ? null:format.parse(map.get("storage_time").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pnrOltBbuOfficeMainModel.setWorkerSceneHandleFlag(map.get("worker_scene_handle_flag") == null ? null:map.get("worker_scene_handle_flag").toString());
			pnrOltBbuOfficeMainModel.setSumNeedCostOfPartyB(map.get("sum_need_cost_of_party_b") == null ? null:Double.parseDouble(map.get("sum_need_cost_of_party_b").toString()));
			pnrOltBbuOfficeMainModel.setSumWorkerCostOfPartyB(map.get("SUM_WORKER_COST_OF_PARTY_B") == null ? null:Double.parseDouble(map.get("SUM_WORKER_COST_OF_PARTY_B").toString()));
			pnrOltBbuOfficeMainModel.setWorkerProjectAmount(map.get("WORKER_PROJECT_AMOUNT") == null ? null:Double.parseDouble(map.get("WORKER_PROJECT_AMOUNT").toString()));
			pnrOltBbuOfficeMainModel.setWorkerIncomeRatio(map.get("WORKER_INCOME_RATIO") == null ? null:Double.parseDouble(map.get("WORKER_INCOME_RATIO").toString()));
			pnrOltBbuOfficeMainModel.setWorkerChildIds(map.get("WORKER_CHILD_IDS") == null ? null:map.get("WORKER_CHILD_IDS").toString());
			pnrOltBbuOfficeMainModel.setWorkerChildNames(map.get("WORKER_CHILD_NAMES") == null ? null:map.get("WORKER_CHILD_NAMES").toString());
			pnrOltBbuOfficeMainModel.setOrderauditProjectAmount(map.get("ORDERAUDIT_PROJECT_AMOUNT") == null ? null:Double.parseDouble(map.get("ORDERAUDIT_PROJECT_AMOUNT").toString()));
			pnrOltBbuOfficeMainModel.setOrderauditIncomeRatio(map.get("ORDERAUDIT_INCOME_RATIO") == null ? null:Double.parseDouble(map.get("ORDERAUDIT_INCOME_RATIO").toString()));
			pnrOltBbuOfficeMainModel.setOrderauditChildIds(map.get("ORDERAUDIT_CHILD_IDS") == null ? null:map.get("ORDERAUDIT_CHILD_IDS").toString());
			pnrOltBbuOfficeMainModel.setOrderauditChildNames(map.get("ORDERAUDIT_CHILD_NAMES") == null ? null:map.get("ORDERAUDIT_CHILD_NAMES").toString());
			pnrOltBbuOfficeMainModel.setSumOrderAuditCostOfPartyB(map.get("SUM_ORDERAUDIT_COST_OF_PARTY_B") == null ? null:Double.parseDouble(map.get("SUM_ORDERAUDIT_COST_OF_PARTY_B").toString()));
			pnrOltBbuOfficeMainModel.setWorkerSceneOrderAuditFlag(map.get("worker_scene_order_audit_flag") == null ? null:map.get("worker_scene_order_audit_flag").toString());
			//------机房信息------
			pnrOltBbuOfficeMainModel.setRelationId(map.get("relation_id") == null ? null:map.get("relation_id").toString());
			pnrOltBbuOfficeMainModel.setRoomId(map.get("room_id") == null ? null:map.get("room_id").toString());
			pnrOltBbuOfficeMainModel.setRoomThemeInterface(map.get("room_themeinterface") == null ? null:map.get("room_themeinterface").toString());
			pnrOltBbuOfficeMainModel.setSerialNumber(map.get("serial_number") == null ? null:map.get("serial_number").toString());
			pnrOltBbuOfficeMainModel.setJfCity(map.get("jf_city") == null ? null:map.get("jf_city").toString());
			pnrOltBbuOfficeMainModel.setJfCountry(map.get("jf_country") == null ? null:map.get("jf_country").toString());
			pnrOltBbuOfficeMainModel.setJfAddressName(map.get("jf_address_name") == null ? null:map.get("jf_address_name").toString());
			pnrOltBbuOfficeMainModel.setOltNumber(map.get("olt_number") == null ? null:map.get("olt_number").toString());
			pnrOltBbuOfficeMainModel.setTotalUserNumber(map.get("total_user_number") == null ? null:map.get("total_user_number").toString());
			pnrOltBbuOfficeMainModel.setVoiceUser(map.get("voice_user") == null ? null:map.get("voice_user").toString());
			pnrOltBbuOfficeMainModel.setBroadbandUser(map.get("Broadband_User") == null ? null:map.get("Broadband_User").toString());
			pnrOltBbuOfficeMainModel.setIptvUser(map.get("Iptv_User") == null ? null:map.get("Iptv_User").toString());
			pnrOltBbuOfficeMainModel.setIsNoBbu(map.get("Is_No_Bbu") == null ? null:map.get("Is_No_Bbu").toString());
			pnrOltBbuOfficeMainModel.setBbuNumber(map.get("Bbu_Number") == null ? null:map.get("Bbu_Number").toString());
			pnrOltBbuOfficeMainModel.setLineTotalInvestment(map.get("Line_Total_Investment") == null ? null:map.get("Line_Total_Investment").toString());
			pnrOltBbuOfficeMainModel.setIsNeedRodInvestment(map.get("Is_Need_Rod_Investment") == null ? null:map.get("Is_Need_Rod_Investment").toString());
			pnrOltBbuOfficeMainModel.setIsNeedCableInvestment(map.get("Is_Need_Cable_Investment") == null ? null:map.get("Is_Need_Cable_Investment").toString());
			pnrOltBbuOfficeMainModel.setNewBuiltRodLength(map.get("New_Built_Rod_Length") == null ? null:map.get("New_Built_Rod_Length").toString());
			pnrOltBbuOfficeMainModel.setNewBuiltRodInvestment(map.get("New_Built_Rod_Investment") == null ? null:map.get("New_Built_Rod_Investment").toString());
			pnrOltBbuOfficeMainModel.setOriginalCableRoute(map.get("Original_Cable_Route") == null ? null:map.get("Original_Cable_Route").toString());
			pnrOltBbuOfficeMainModel.setNewCableRoute(map.get("New_Cable_Route") == null ? null:map.get("New_Cable_Route").toString());
			pnrOltBbuOfficeMainModel.setNewParagraph(map.get("New_Paragraph") == null ? null:map.get("New_Paragraph").toString());
			pnrOltBbuOfficeMainModel.setNewLineRoutingDiagram(map.get("New_Line_Routing_Diagram") == null ? null:map.get("New_Line_Routing_Diagram").toString());
			pnrOltBbuOfficeMainModel.setCableClothCoreNumber(map.get("Cable_Cloth_Core_Number") == null ? null:map.get("Cable_Cloth_Core_Number").toString());
			pnrOltBbuOfficeMainModel.setCableClothLength(map.get("Cable_Cloth_Length") == null ? null:map.get("Cable_Cloth_Length").toString());
			pnrOltBbuOfficeMainModel.setCableInvestment(map.get("Cable_Investment") == null ? null:map.get("Cable_Investment").toString());
			pnrOltBbuOfficeMainModel.setBoardDemand(map.get("Board_Demand") == null ? null:map.get("Board_Demand").toString());
			pnrOltBbuOfficeMainModel.setLightModuleDemand(map.get("Light_Module_Demand") == null ? null:map.get("Light_Module_Demand").toString());
			pnrOltBbuOfficeMainModel.setTransBoardDemand(map.get("Trans_Board_Demand") == null ? null:map.get("Trans_Board_Demand").toString());
			pnrOltBbuOfficeMainModel.setTransLightModuleDemand(map.get("Trans_Light_Module_Demand") == null ? null:map.get("Trans_Light_Module_Demand").toString());
			pnrOltBbuOfficeMainModel.setEquipmentInvestment(map.get("Equipment_Investment") == null ? null:map.get("Equipment_Investment").toString());
			pnrOltBbuOfficeMainModel.setJfRemark(map.get("Jf_Remark") == null ? null:map.get("Jf_Remark").toString());
		}
		return pnrOltBbuOfficeMainModel;
	}
	
	/**
	 * 
	 * 删除olt-bbu工单和机房关联表数据
	 * 
	 * @param param 删除参数
	 */
	public void deletePnrOltBbuOfficeRelation(Map<String,Object> param){
		String processInstanceId=(String)param.get("processInstanceId");//流程号
		String delSql = "delete from pnr_olt_bbu_office_relation where process_instance_id ='"+processInstanceId+"'";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.addBatch();
				return ps.executeBatch();
			}
		});
	}
	
	/**
	 * 
	 * 根据工单流程id查询出于该工单关联的图片
	 * 
	 * @param processInstanceId	流程号
	 * @param param	其他查询参数
	 * @return
	 */
	public String[] getPhotoByProcessInstanceId(String processInstanceId,Map<String,String> param){
		StringBuilder sql = new StringBuilder("select po.*,pu.name from pnr_act_precheck_photo_ship p join");
		sql.append(" pnr_android_photo po on p.photo_id = po.id");
		sql.append(" left join pnr_user pu  on pu.user_id=po.user_id");
		sql.append(" where p.processinstance_id='").append(processInstanceId).append("'");
		if(param.get("photoFlag")!=null&&!"".equals(param.get("photoFlag"))){
			sql.append(" and p.photo_flag='").append(param.get("photoFlag")).append("'");	
		}
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		String[] str = new String[2];
		String newTr ="";
		String photoIds = "";
		if(list!=null && list.size()>0){
			for(Map map:list){
				String photoId = map.get("ID").toString();
				photoIds+=photoId+",";
				String time = map.get("CREATETIME")==null?"":map.get("CREATETIME").toString();
				String longitude = map.get("LONGITUDE")==null?"无":map.get("LONGITUDE").toString();
				String latitude = map.get("LATITUDE")==null?"无":map.get("LATITUDE").toString();
				String userId = map.get("NAME")==null?"":map.get("NAME").toString();
				newTr += "<tr><td>拍照时间：</td><td>"+time+"</td><td>拍照人：</td><td>"+userId+"</td><td>经度：</td><td>"+longitude+"</td><td>纬度：</td><td>"+latitude+"</td></tr>";
			}
			str[0] = photoIds;
			str[1] = newTr;
		}
		
		return str;
	}
}
