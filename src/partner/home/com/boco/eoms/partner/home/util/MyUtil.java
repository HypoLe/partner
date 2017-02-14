package com.boco.eoms.partner.home.util;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.partner.home.model.PublishInfo;
import com.boco.eoms.partner.home.model.PublishLink;
import com.boco.eoms.partner.home.model.PublishTask;
import com.google.common.base.Strings;
/**
 * <p>
 * Title:常用方法
 * </p>
 * <p>
 * Description:常用方法
 * </p>
 * <p>
 * Jul 25, 2012 2:33:41 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class MyUtil {
	
	/**
	 * 字符串编码转换
	 * @param str
	 * @return
	 */
	public static String getString(String str) {
		String newStr = "";
		 if(!isEmpty(str))
			 try {
				 newStr = new String(str.getBytes("ISO8859-1"),"UTF-8");
			} catch (Exception e) {
				newStr = "";
			}
			 return newStr;
	}
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return 空：true
	 */
	public static boolean isEmpty(String str){
		   if("".equals(StaticMethod.null2String(str)))
			   return true;
		   else
			   return false;
	   }
	/**
	 * 字符串2字符串数组
	 * @param str
	 * @param regex 要分割字符
	 * @return
	 */
	public static String[] getStrings(String str,String regex){
		if(!isEmpty(str))
			return str.split(regex);
		else
			throw new RuntimeException("字符串为空");
	}
	
	/**
	 * 字典值 转换为 字典名称  
	 * 即：取得字典对应中文名
	 * @param field 结果集 字段名
	 * @param value
	 * @return
	 */
	 public static String dict2Name(String field,String value){
		if(field.endsWith("typelikedict")){
			ID2NameService service = (ID2NameService) ApplicationContextHolder
			.getInstance().getBean("ID2NameGetServiceCatch");
			value =service.id2Name(value, "tawSystemDictTypeDao");
		}
		else if(field.endsWith("typelikearea")){
				ID2NameService service = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
				value =service.id2Name(value, "tawSystemAreaDao");
			}
		else if(field.endsWith("typelikeuser")){
				ID2NameService service = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
				value =service.id2Name(value, "tawSystemUserDao");
			}
		else if(field.endsWith("typeper")){
			 NumberFormat formatter = new DecimalFormat("0.000");
			 Double per = Double.parseDouble(value)*100;
			 value = formatter.format(per)+"%";
//			 value = formatter.format(per);
			}
		else if(field.endsWith("typefor")){
				ID2NameService service = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
				String aa[]=value.split(",");
				String bb="";
				for(int ii=0;ii<aa.length;ii++){
					bb =bb+service.id2Name(aa[ii], "tawSystemDictTypeDao")+";";
				}
				value=bb;
			}
		return value;
	}

	 public static String getStrTime(Date time){
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String timeStr ;
		 try {
			 timeStr = dateFormat.format(time);
		} catch (Exception e) {
			timeStr = "1900-01-01 00:00:00";
		}
		 return timeStr;
	 }
	 
	 public static List<PublishTask> getTaskFromStr(PublishInfo pInfo,PublishLink publishLink,String taskOwnerIds,String taskOwnerNames){
		 PublishTask publishTask=null;
		 List<PublishTask> list = new ArrayList<PublishTask>();
		 Date time = new Date();
		 String id[]=taskOwnerIds.split(",");
		 String name[]=taskOwnerNames.split(",");
		 
		 ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		 for (int i = 0 ,length = id.length; i < length; i++) {	
			 if(!"".equals((service.id2Name(id[i],"tawSystemUserDao")))){//用户
				 publishTask=new PublishTask();
				 publishTask.setMainId(pInfo.getId());
				 publishTask.setTaskOwnerId(id[i]);
				 publishTask.setTaskOwnerName(service.id2Name(id[i],"tawSystemUserDao"));
				 publishTask.setTaskType(StateType.OPERATE_READ);
				 publishTask.setTaskName("阅知");
				 publishTask.setTaskState(StateType.TASK_RUNNING);
				 publishTask.setOperateTime(time);
				 publishTask.setPrelinkid(publishLink.getId());	
				 list.add(publishTask);
			 }else{//部门
				 ITawSystemUserManager tawUserMgr = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean( "itawSystemUserManager");
				 List listUsers = tawUserMgr.getUserBydeptids(id[i]);
				 if(listUsers!=null&&listUsers.size()>0){
					 for(int ii=0;ii<listUsers.size();ii++){
						 TawSystemUser user = (TawSystemUser)listUsers.get(ii);
						 publishTask=new PublishTask();
						 publishTask.setMainId(pInfo.getId());
						 publishTask.setTaskOwnerId(user.getUserid());
						 publishTask.setTaskOwnerName(user.getUsername());
						 publishTask.setTaskType(StateType.OPERATE_READ);
						 publishTask.setTaskName("阅知");
						 publishTask.setTaskState(StateType.TASK_RUNNING);
						 publishTask.setOperateTime(time);
						 publishTask.setPrelinkid(publishLink.getId());	
						 list.add(publishTask);
					 }
				 }
			 }
		 }
		 return list;
	 }
}
