package com.boco.eoms.worksheet;

import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.util.InterfaceUtil;

public class Test {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	
	private static String hello="";
	public static void main(String[] args) {
		double i=0.75+2*3/4+(+0.125)+(-12*5/7)+(-4*1/8);
		System.out.println("-----i="+i);
			
			
		
		
	}
	
	//运维商城接口
	
	public static void testOpiMall(){
//		String url="http://localhost:8080/partner/services/OpsMallService?wsdl";
		String url="http://61.156.3.123:8080/partner/services/OpsMallService?wsdl";
		String method="getUrl";
		String msg="<opDetail>" +
				"<recordInfo>" +
				"<fieldInfo>" +
				"<fieldChName>工单类型</fieldChName>" +
				"<fieldEnName>ticketType</fieldEnName>" +
				"<fieldContent>trouble</fieldContent>" +
				"</fieldInfo>" +
				"</recordInfo>" +
				"<recordInfo>" +
				"<fieldInfo>" +
				"<fieldChName>工单号</fieldChName>" +
				"<fieldEnName>processInstanceId</fieldEnName>" +
				"<fieldContent>96241</fieldContent>" +
				"</fieldInfo></recordInfo>" +
				"</opDetail>";
		
	//	String msgString=InterfaceUtil.gkAgencyMethod(url,method,msg);
		//故障	11301
		//任务	11317 
		
		String metod="newShowPageUrl";// isAlive  ---  newShowPageUrl
		
		Service serv =  new Service();
		Call call = null;
			
			try {
				try {
					call = (Call) serv.createCall();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				call.setTargetEndpointAddress(url);
				call.setOperationName(new QName(url,metod));
				String resultString =(String)call.invoke(new Object[]{msg});
			System.out.println(msg);
			System.out.println("返回的结果:"+resultString);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
//		System.out.println(msgString);
	}
	public static void testJsessionid(){
		
		TawSystemSessionForm sessionform = new TawSystemSessionForm();
		sessionform.setUserid("s_admin");
		System.out.println(sessionform);
//		session
	}
	
	public static void testWork(String hello){
		
		

//			String url="http://61.156.3.123:8080/partner/services/Bulletin?wsdl";
//			String url="http://192.168.10.8:8088/partner/services/PartnerProcessSheet?wsdl";
		String url="http://localhost:8087/partner/services/PartnerMaleGuest?wsdl";
			Service serv =  new Service();
			Call call = null;
				
				try {
					try {
						call = (Call) serv.createCall();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					call.setTargetEndpointAddress(url);
					call.setOperationName(new QName(url,"SendNewOrder"));
					String resultString =(String)call.invoke(new Object[]{hello});

				System.out.println("返回的结果:"+resultString);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			
	}
	public static void testWorkOtherSide(){
				
		String msg = "<msg><router><to>sasa</to><msgId>随机编码</msgId>" +
				"<time>时间</time><serviceName>biz_NetWork_tailSend</serviceName>" +
				"<from>wlwh</from></router><data>" +
				"<workOrderNo>G053120140324115212931</workOrderNo>" +
				"<tail>工单处理状态</tail>" +
				"</data></msg>";
		String remsg = "<msg><router><to>sasa</to><msgId>随机编码</msgId>" +
		"<time>时间</time><serviceName>biz_NetWork_GDHD</serviceName>" +
		"<from>wlwh</from></router><data>" +
		"<workOrderNo>G053120140324115212931</workOrderNo>" +
		"<tail>工单处理状态</tail>" +
		"</data></msg>";
		
				String url="http://134.34.112.15:9080/axis/services/ServicesRouter?wsdl";
		Service serv =  new Service();
		Call call = null;
		
		try {
			try {
				call = (Call) serv.createCall();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(url,"startInvoke"));
//					call.setOperationName(new QName(url,"remindersWorkSheet"));
//					String resultString =(String)call.invoke(new Object[]{opDetail2});
			String resultString =(String)call.invoke(new Object[]{remsg});
//					String resultString =(String)call.invoke(new Object[]{"gk_001",reminderMsg});
			System.out.println("返回的结果:"+resultString);
//			System.out.println("参数:"+msg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	
}
