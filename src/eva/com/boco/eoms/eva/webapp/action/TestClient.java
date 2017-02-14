package com.boco.eoms.eva.webapp.action;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

 

public class TestClient {

             

       public static void main(String[] args) {

 

              try {
            	  	 String xmlStr = "";
            	  	 xmlStr += "<root><systemid>partner</systemid><systemname>合作伙伴</systemname><starttime>2010-01-01 00:00:00</starttime><endtime>2010-07-22 00:00:00</endtime>";
            	  	 xmlStr += "<userinfo><userid>chengjianwei</userid><userid>lifan</userid><userid>lijianghong</userid></userinfo></root>";
//                     String endpoint = "http://10.168.87.136:8080/eomsYN/services/PartnerEvaCollectService?wsdl";
                     String endpoint = "http://10.168.87.139:8091/yn_eoms/services/PartnerEvaCollectService?wsdl";
                     //直接引用远程的wsdl文件

                    //以下都是套路 
                     Service service = new Service();

                     Call call = (Call) service.createCall();

                     call.setTargetEndpointAddress(endpoint);
//                     call.setOperationName(new QName("http://10.168.87.136:8080/eomsYN/services/PartnerEvaCollectService","service"));//WSDL里面描述的接口名称
                     call.setOperationName(new QName("http://10.168.87.139:8091/yn_eoms/services/PartnerEvaCollectService","NOPTrade"));//WSDL里面描述的接口名称
//                     public String service(String serviceCode, String userName, String roleID,String authCode, String ipAddress, String eventID,String serviceParas);
                     call.addParameter("serviceCode", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数

                     call.addParameter("userName", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数

                     call.addParameter("roleID", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数

                     call.addParameter("authCode", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数

                     call.addParameter("ipAddress", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数
                     call.addParameter("eventID", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数
                     
                     call.addParameter("serviceParas", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数

                     call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型  


                     String serviceCode = "Partner.Eva.Collect";
                     String userName = "admin";
                     String roleID = "11111111";
                     String authCode = "123";
                     String ipAddress = "127.0.0.1";
                     String eventID ="eventID1111";
                     String serviceParas = xmlStr;

                     String result = (String)call.invoke(new Object[]{serviceCode,userName,roleID,authCode,ipAddress,eventID,serviceParas});

                     //给方法传递参数，并且调用方法

                     System.out.println("result is "+result);

              }

              catch (Exception e) {

                     System.err.println(e.toString());

              }

       }

}
