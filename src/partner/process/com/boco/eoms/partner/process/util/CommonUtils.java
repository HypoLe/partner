package com.boco.eoms.partner.process.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boco.activiti.partner.process.model.GkSystemConfigModel;
import com.boco.activiti.partner.process.model.PnrSmsSendEntity;
import com.boco.activiti.partner.process.po.PrecheckRoleModel;
import com.boco.activiti.partner.process.po.TransferMaleGuestFlag;
import com.boco.activiti.partner.process.po.TransferMaleGuestReturn;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.area.dao.hibernate.TawSystemAreaDaoHibernate;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.dao.TawSystemUserDao;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.util.InterfaceUtil;

public class CommonUtils {
	/*
	 * 与公客系统接口配置信息
	 */
	private static GkSystemConfigModel xmlConfig =(GkSystemConfigModel)ApplicationContextHolder.getInstance().getBean("gkSystemConfigModel");
	
	private static PrecheckRoleModel precheckRoleModel = (PrecheckRoleModel) ApplicationContextHolder.getInstance().getBean("precheckRoleModel");
	
	private static ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");
    /**
     * 工单状态
     */
	public final static Integer TASK_COUNTERSIGNING_STATE = 6;

	public static final String taskDetele = "已删除";
	public static final String taskComplete = "已归档";
	public static final String taskRoleId="6872";
	public static final String troubleRoleId="6873";
	public static final String mobile="18686504542";
	public static final String processTroubleDefinitionKey="troubleTicketProcess";
	public static final String troubleInitiator=xmlConfig.getTroubleInitiator();
	public static final String GK_INTERFACE_URL=xmlConfig.getGkInterfaceUrl();
	public static final String GK_STATUS_METHOD=xmlConfig.getStatusMethod();
	public static final String GK_RECEIPT_METHOD=xmlConfig.getReceiptMethod();
	public static final String GK_UNIFY_METHOD=xmlConfig.getUnifyMethod();
	public static final String GK_INTERFACE_MALE_GUEST_URL=xmlConfig.getGkInterfaceMaleGuestUrl();
	public static final String GK_MALE_GUEST_STATUS_METHOD = xmlConfig.getMaleGuestStatusMethod();
	public static final String GK_MALE_GUEST_RECEIPT_METHOD=xmlConfig.getMaleGuestReceiptMethod();
	public static final String GK_MALE_GUEST_UNIFY_METHOD = xmlConfig.getMaleGuestUnifyMethod();
	
	public static final String GK_TRANSFER_INTERFACE_URL = xmlConfig.getTransferInterfaceUrl();
	public static final String GK_TRANSFER_INTERFACE_OVER_METHOD = xmlConfig.getTransferInterfaceOverMethod();
	public static final String GK_TRANSFER_INTERFACE_UNIFY_METHOD = xmlConfig.getTransferInterfaceUnifyMethod();
	/**
	 * 巡检人员自维标识
	 */
	public static final String startDeptMagId = "201";

	/**
	 * 后台分页后每页显示记录条数，修改此属性有模块级影响，请注意
	 */
	public static final int PAGE_SIZE = 20;
	
	
	public static final int PAGE_SIZE10 = 10;
	/**
	 * 获取分页PageIndex
	 * @param request
	 * @param tableId jsp页面display:table的id
	 * @return
	 */
	
//	public static final String IMG_INSPECT_PUBLIC_URL="smb://John:bing004@192.168.10.39/site/";
	//手机工单相关的图片存放的远端windows共享地址
//	public static final String IMG_WORKSHEET_PUBLIC_URL="smb://John:bing004@192.168.10.39/img/";
	
	public static String getPageIndexWithDisplaytag(HttpServletRequest request,String tableId){
		return 	request.getParameter((new org.displaytag.util.ParamEncoder(
				tableId).encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
	}
	/**
	 * 获取审核人
	 * @param roleid //故障 6873;任务6872
	 * @param request 
	 * @return String
	 */
	public static String getAuditorByUserid(String roleid,HttpServletRequest request){
    	
    	TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
    	String userId = sessionForm.getUserid();
        String _deptId = sessionForm.getDeptid();
        String deptId=_deptId;
    	String auditor="";
    	String taskRoleid=roleid; //故障 6873;任务6872
    	ITawSystemUserRefRoleManager userRefRole;
    	userRefRole=(ITawSystemUserRefRoleManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserRefRoleManager");
    	
    	
    	//根据审核角色找到所有的审核人
    	List auditorList = userRefRole.getUserIdBySubRoleids(taskRoleid);
    	//根据回复人找到审核人部门下的班组成员
    	
//    	if(_deptId.length()==9){
    		//现在组找
    		auditor=getInGroupPerson(deptId,userId,auditorList);
//    	}
    	
    	if("".equals(auditor)&&_deptId.length()>2){
    		//组找不到 ，到班找
    		deptId=_deptId.substring(0,_deptId.length()-2);
    		auditor=getInGroupPerson(deptId,userId,auditorList);
    	}
    	
    	
    	return auditor;
    }
	private static String getInGroupPerson(String deptId,String userId,List auditorList){
		String personString ="";
		ITawSystemUserManager systemUser;
		systemUser=(ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		List userList = systemUser.getUserBydeptids(deptId);
    	List<TawSystemUser> tawList = userList;
    	int auditorListSize = auditorList.size();
    	int userListSize = userList.size();
    	
    	if(auditorListSize>0)
    	{
    		for(int i =0;i<auditorListSize;i++)
    		{
    			for(int j =0 ;j<userListSize;j++)
    			{
    				if(tawList.get(j)!=null&&auditorList.get(i)!=null){
	    				if(auditorList.get(i).equals(tawList.get(j).getUserid()))
	    				{
	    					/*if("".equals(personString))
	    					{
	    						personString+=auditorList.get(i);
	    					}else 
	    					{
	    						personString+=","+auditorList.get(i);
							}*/
							personString=auditorList.get(i)+"";
	
	    				}
    				}
    			}
    		}
    	}
		return personString;
	}
	/**
	 * flag :1代表字典类型，2代表地区类型，3代表部门类型,4代表人员类型
	 * @param idStrings
	 * @param flag
	 * @return
	 */
		public static String getId2NameString(String idStrings,int flag,String split)
		{
			String specialtyNameString = "";
				if(null==idStrings||"".equals(idStrings)){
					return specialtyNameString;
				}
			String[] stringArr = idStrings.split(split);
		       if(flag==1)
		       {
		    	   ITawSystemDictTypeManager tawSys = (ITawSystemDictTypeManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");
		    	   
		    	   for(int j=0;j<stringArr.length;j++)
		    	   {
		    		   try {
		    			   if(j==(stringArr.length-1))
		    			   {
		    				   specialtyNameString+=tawSys.id2Name(stringArr[j]);
		    			   }else
		    			   {
		    				   specialtyNameString+=tawSys.id2Name(stringArr[j])+",";
		    			   }
		    		   } catch (DictDAOException e) {
		    			   // TODO Auto-generated catch block
		    			   e.printStackTrace();
		    		   }
		    		   
		    	   }
		       }else if(flag==2)
		       {
		    	   TawSystemAreaDaoHibernate areaDao = (TawSystemAreaDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemAreaDao");
		    	   for(int j=0;j<stringArr.length;j++)
		    	   {
		    		   try {
		    			   if(j==(stringArr.length-1))
		    			   {
		    				   specialtyNameString+= areaDao.id2Name(stringArr[j]);
		    			   }else
		    			   {
		    				   specialtyNameString+=areaDao.id2Name(stringArr[j])+",";
		    			   }
		    		   } catch (DictDAOException e) {
		    			   // TODO Auto-generated catch block
		    			   e.printStackTrace();
		    		   }
		    		   
		    	   }
		    	   
		       }
		       else if(flag==3)
		       {
		    	   ITawSystemDeptManager deptSys = (ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
		    	   for(int j=0;j<stringArr.length;j++)
		    	   {
		    		   try {
		    			   if(j==(stringArr.length-1))
		    			   {
		    				   specialtyNameString+=deptSys.id2Name(stringArr[j]);
		    			   }else
		    			   {
		    				   specialtyNameString+=deptSys.id2Name(stringArr[j])+",";
		    			   }
		    		   } catch (DictDAOException e) {
		    			   // TODO Auto-generated catch block
		    			   e.printStackTrace();
		    		   }
		    		   
		    	   }
		    	   
		       }
		       else if(flag==4)
		       {
		    	   ITawSystemUserManager itawSystemUserManager = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
		    	   for(int j=0;j<stringArr.length;j++)
		    	   {
		    		   try {
		    			   if(j==(stringArr.length-1))
		    			   {
		    				   specialtyNameString+=itawSystemUserManager.id2Name(stringArr[j]);
		    			   }else
		    			   {
		    				   specialtyNameString+=itawSystemUserManager.id2Name(stringArr[j])+",";
		    			   }
		    		   } catch (DictDAOException e) {
		    			   // TODO Auto-generated catch block
		    			   e.printStackTrace();
		    		   }
		    		   
		    	   }
		    	   
		       }
			return specialtyNameString;
		}
		
//关于权限的限制
		public static void getCompetenceLimit(HttpServletRequest request){
			Map<String, String> map = new HashMap<String, String>();
			 TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		        String userId = sessionForm.getUserid();
		        String deptId = sessionForm.getDeptid();
		        String mobilePhone = sessionForm.getContactMobile();
				
//		      初始化地市-站点查询
		 	    ITawSystemDeptManager deptSys = (ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
		 	    String deptIdString="";
		 	    String executeDept = deptId;
		 	    TawSystemDept list = null;
		 	    String areaId ="";
		 	    
		 	    if(deptId.length()>=5)
		 	    {
		 	    	deptIdString=deptId.substring(0,5);
		 	    	list= deptSys.getDeptinfobydeptid(deptIdString,"0");
		 	    }
		 	    if(list !=null)
		 	    {
		 	    	areaId= list.getAreaid();
		 	    }
//		 	    针对自卫人员
		 	    if(deptId.length()>=5&&deptId.substring(0,3).equals("201"))
		 	    {
		 	    	executeDept=deptId.substring(0,5);
		 	    }
		        
		        if(userId.equals("admin")){
		        	areaId="";
		        	executeDept="";
		        }
		        if(deptId.substring(0,3).equals("101")){
		        	executeDept="";
		        }
		        
		        //获取当前系统时间
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		        String initiateTime=df.format(new Date());// new Date()为获取当前系统时间
		        
//		      初始化地市-end
		        request.setAttribute("areaId", areaId);
		        request.setAttribute("country", deptIdString);
		        request.setAttribute("userId", userId);
		        request.setAttribute("deptId", deptId);
		        request.setAttribute("executeDept", executeDept);
		        request.setAttribute("initiateTime", initiateTime);
		        request.setAttribute("mobilePhone", mobilePhone);
		        request.setAttribute("access", 1);//如果为1说明是点击新建工单,显示保存草稿按钮,空说明从别的地方跳入不显示保存按钮
		    
		}
		/**
		 * 
		 */
		public static TawSystemUser getTawSystemUserByUserId(String userId,String bak){
			TawSystemUser pu = null;
			ITawSystemUserManager iTawSystemUserManager = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");

			 pu=iTawSystemUserManager.getTawSystemUserByuserid(userId);
			 
			 return pu;
		}
		/**
		 * 发短信
		 */
		public static void sendMsg(String msg,String userId){
//			发短信
			
			IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) ApplicationContextHolder.getInstance().getBean("pnrTroubleTicketService");

			TawSystemUserDao tawSystemUserDao =(TawSystemUserDao)ApplicationContextHolder.getInstance().getBean("tawSystemUserDao");		
			PnrSmsSendEntity pnrSmsSendEntity = new PnrSmsSendEntity();
			List<String> list =tawSystemUserDao.getAllMobileByuerids("'"+userId+"'");
			String mobile = CommonUtils.mobile;
			if(list !=null && list.size()>0){
				mobile=list.get(0);
			}
			pnrSmsSendEntity.setMobile(mobile);
			pnrSmsSendEntity.setContext(msg);
			pnrTroubleTicketService.saveSendContext(pnrSmsSendEntity);
		}
		/**
		 * 
		 * @param workOrderNo
		 * @param method
		 * @param sendMsg
		 * @return
		 */
		public static String sendToGKInformation(String nowTime,String workOrderNo,String method,String sendMsg){
			String url=GK_INTERFACE_URL;
			String msgStr = "<msg>" +
					"<router>" +
					"<to>sasa</to>" +
					"<msgId>随机编码</msgId>" +
			"<time>"+nowTime+"</time>" +
					"<serviceName>"+method+"</serviceName>" +
			"<from>wlwh</from></router><data>" +
			"<workOrderNo>"+workOrderNo+"</workOrderNo>" +
			"<tail>"+sendMsg+"</tail>" +
			"</data></msg>";
			
			String getMsg=InterfaceUtil.gkAgencyMethod(url, GK_UNIFY_METHOD, msgStr);
			return 	getMsg;
		}
		public static String maleGuestFlagAndReturn(String timeString,String methodName,TransferMaleGuestFlag maleGuestFlag){
			String url = GK_INTERFACE_MALE_GUEST_URL;
			String msgStr = "<msg>" +
			"<router>" +
			"<to>WNO</to>" +
			"<msgId>20140721172250617302</msgId>" +
			"<time>"+timeString+"</time>" +
			"<serviceName>"+methodName+"</serviceName>" +
			"<from>CS</from>" +
			"</router>" +
			"<data>" +
			"<confCRMTicketNo>"+maleGuestFlag.getConfCRMTicketNo()+"</confCRMTicketNo>" +
			"<WorkSheetSymbol>"+maleGuestFlag.getWorkSheetSymbol()+"</WorkSheetSymbol>" +
			"<fieldFlag>"+maleGuestFlag.getFieldFlag()+"</fieldFlag>" +
			"<fieldContent>"+maleGuestFlag.getFieldContent()+"</fieldContent>" +
			"</data>" +
			"</msg>";
			System.out.println(msgStr);
			String getMsg = InterfaceUtil.gkAgencyMethod(url,GK_MALE_GUEST_UNIFY_METHOD , msgStr);
			return getMsg;
		}
		public static String maleGuestFlagAndReturn(String timeString,String methodName,TransferMaleGuestReturn maleGuestReturn){
			String url=GK_INTERFACE_MALE_GUEST_URL;
			String msgStr="<msg><router><to>WNO</to>" +
					"<msgId>20140721172250617302</msgId>" +
			"<time>"+timeString+"</time>" +
			"<serviceName>"+methodName+"</serviceName>" +
			"<from>CS</from></router><data>	" +
			"<confCRMTicketNo>"+maleGuestReturn.getConfCRMTicketNo()+"</confCRMTicketNo>" +
			" <Reason_id>"+maleGuestReturn.getReason_id()+"</Reason_id>" +
			"<Reason_name>"+maleGuestReturn.getReason_name()+"</Reason_name>" +
			"<Back_userid>"+maleGuestReturn.getBack_userid()+"</Back_userid>" +
			"<Back_username>"+maleGuestReturn.getBack_username()+"</Back_username>	" +
			"<Back_dt>"+maleGuestReturn.getBack_dt()+"</Back_dt>" +
			"<flag>"+maleGuestReturn.getFlag()+"</flag>" +
			"<Back_info>"+maleGuestReturn.getBack_info()+"</Back_info>" +
			"</data>" +
			"</msg>";
			 BocoLog.info("发送给公客的报文-接口为："+methodName+"-:"+"公客工单编号"+maleGuestReturn.getConfCRMTicketNo(), 406, msgStr);

			String getMsg = InterfaceUtil.gkAgencyMethod(url,GK_MALE_GUEST_UNIFY_METHOD , msgStr);
			return getMsg;
		}
		public static String transferInterfaceArchive(String timeString,String methodName,String processId,String interfaceNumber){
			String url = GK_TRANSFER_INTERFACE_URL;
			String msgStr="<msg><router><to>WNO</to>" +
			"<msgId>20140721172250617302</msgId>" +
			"<time>"+timeString+"</time>" +
			"<serviceName>"+methodName+"</serviceName>" +
			"<from>CS</from></router><data>	" +
			"<TicketNo>"+processId+"</TicketNo>" +
			" <maleGuestNumber>"+interfaceNumber+"</maleGuestNumber>" +
			"</data>" +
			"</msg>";
			String getMsg = InterfaceUtil.gkAgencyMethod(url,GK_TRANSFER_INTERFACE_UNIFY_METHOD , msgStr);
			return msgStr;
		}
		/**
		 * 获取手机端巡检图片存放的远端windows共享地址
		 * @return
		 */
		public static String getImgInspectPublicUrl() {
		String imgInspectPublicUrl = precheckRoleModel.getImgInspectPublicUrl();
		TawSystemDictType dictypeThree = mgr
				.getDictByDictId(imgInspectPublicUrl);
		return dictypeThree.getDictRemark();
		}
		
		/**
		 * 获取手机工单相关的图片存放的远端windows共享地址
		 * @return
		 */
		public static String getImgWorksheetPublicUrl() {
		 String imgWorksheetPublicUrl = precheckRoleModel.getImgWorksheetPublicUrl();
		TawSystemDictType dictypeThree = mgr
				.getDictByDictId(imgWorksheetPublicUrl);
		return dictypeThree.getDictRemark();
		}
		
		/**
		 * 获取交接箱核查的图片存放的远端windows共享地址
		 * @return
		 */
		public static String getImgJunctionBoxCheckPublicUrl() {
		 String imgWorksheetPublicUrl = precheckRoleModel.getImgJunctionBoxCheckPublicUrl();
		TawSystemDictType dictypeThree = mgr
				.getDictByDictId(imgWorksheetPublicUrl);
		return dictypeThree.getDictRemark();
		} 
		
		/**
		 * 两个Double数相除，并保留scale位小数
		 * 
		 * @param v1	被除数	
		 * @param v2	除数
		 * @param scale 保留的小数位数
		 * @return
		 */
		public static Double div(Double v1,Double v2,int scale){
	        if(scale<0){
	            throw new IllegalArgumentException(
	            "The scale must be a positive integer or zero");
	        }
	        
	        if(v2==0){//判断除数是否为零
	        	return 0.0;
	        }
	        
	        BigDecimal b1 = new BigDecimal(v1.toString());
	        BigDecimal b2 = new BigDecimal(v2.toString());
	        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
	    }
		
		/*
		 *通过人员的id获取人员的地市id 
		 */
		public static String getAreaIdByDeptId(String deptId){
			String areaId="";
			  ITawSystemDeptManager deptSys = (ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
		 	    TawSystemDept tawSystemDept = null;		 	    
		 	   tawSystemDept= deptSys.getDeptinfobydeptid(deptId,"0");
		 	    if(tawSystemDept !=null)
		 	    {
		 	    	areaId= tawSystemDept.getAreaid();
		 	    }
			return areaId;
		}
		
		/**
		 * double	保留小数
		 * 
		 * @param value
		 * @param scale
		 * @return
		 */
		public static double reservedDecimalPlaces(double value,int scale){
			double result=0.0;
			BigDecimal b=new BigDecimal(value);  
			result  = b.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
			return result;
		}
		/**
		 *     //区分接口与传输局  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；overhaul 大修工单；reform 改造工单；
		 * @param processKey
		 * @param varName 该值暂时没有用
		 * @return
		 */
		public static String getTaskThemeinterfaceStr(String processKey,String varName){
				String str="";
				if("transferNewPrechechProcess".equals(processKey)){//本地网
					str="interface";
				}else if("transferArteryPrecheckProcess".equals(processKey)){//干线
					str="arteryPrecheck";

				}else if("overHaulNewProcess".equals(processKey)){//大修改造
					str="overhaul,reform";

				}else if("oltBbuProcess".equals(processKey)){//olt-bbu
					str="oltBbuProcess";

				}else if("myMaleGuestProcess".equals(processKey)){//公客故障工单
					str="maleGuest";

				}else if("myTransferProcess".equals(processKey)){//抢修工单
					str="transferOffice";

				}else if("newTransferPrecheck".equals(processKey)){//预检预修工单-旧
					str="interface";

				}else if("newTwoTransferPrecheck".equals(processKey)){//预检预修工单
					str="interface";

				}
				
				return str;
			}
		/**
		 * 公客测试地市
		 * @param country
		 * @return
		 * 
		 */
		public static boolean isHaveCountry(String country){
			boolean flag = false;
			 String countrystr="282601,282110,";//测试用济宁市中心

			//final String countrystr="281601,281603,282703,282401,282110,282613,281501,281509,281411,281405,281912,281801,282202,281205,282317,281101,281301,282506,281704,282008,";
			if(countrystr.contains(country+",")){
				flag=true;
			}
			
			return flag;
		}
		
		/**
		 * 	 根据字典id获取字典备注
		 	 * @author WANGJUN
		 	 * @title: getDictRemark
		 	 * @date Jul 7, 2016 3:06:59 PM
		 	 * @param dictid
		 	 * @return String
		 */
		public static String getDictRemark(String dictid){
			String dictRemark = "";
			TawSystemDictType dictypeThree = mgr.getDictByDictId(dictid);
			if(dictypeThree != null){
				dictRemark = dictypeThree.getDictRemark();
			}
			return dictRemark;
		}
}
