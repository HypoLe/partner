package com.boco.eoms.partner.interfaces.fj.chinamobile.proxy;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.fj.chinamobile.bo.LoadingRequestStub;
import com.res.base.core.ResSystem;
import com.res.base.logs.DebuggingLog;
import com.res.base.logs.MessageLog;
import com.res.soa.message.SOAException;
import com.res.soa.message.SOAFactory;
import javax.xml.namespace.QName;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import com.res.esb.soap.connector.AXISRegistryType;
import com.res.soa.message.command.SOAPModel;
import com.res.soa.proxy.ServiceProxy;

/*
 * Class LoadingRequest.
 */
public class LoadingRequestProxy {

	public static String requesterId = "Web Service Proxy TEST";

	public static void doLoadingRequest(String accessPoint,javax.xml.rpc.holders.StringHolder eventID, java.lang.String systemID, java.util.Calendar sendTime, java.lang.String feedbackUri, int loadingFlag, int workMode, int fileFormat, int charSet, java.lang.String lineSeparator, java.lang.String fieldSeparator, java.lang.String fieldNameList, java.lang.String xmlSchema, java.lang.String dataInfo) throws Exception {

		SOAPModel model = new SOAPModel();
		LoadingRequestStub.timeout = 600000;
		LoadingRequestStub.LoadingRequestRequest req = new LoadingRequestStub.LoadingRequestRequest();	
		if(!accessPoint.equals("")){
			model.setAccessPoint(accessPoint);
		}
		req.setCharSet(charSet);
		req.setDataInfo(dataInfo);
		req.setEventID(eventID.value);
		req.setFeedbackUri(feedbackUri);
		req.setFieldNameList(fieldNameList);
		req.setFieldSeparator(fieldSeparator);
		req.setFileFormat(fileFormat);
		req.setLineSeparator(lineSeparator);
		req.setLoadingFlag(loadingFlag);
		req.setSendTime(sendTime);
		req.setSystemID(systemID);
		req.setWorkMode(workMode);
		req.setXmlSchema(xmlSchema);
		model.setParamTypes(new Class[]{LoadingRequestStub.LoadingRequestRequest.class});
		model.setParamObjs(new Object[]{req});
					
		Object obj = ServiceProxy.getInstance().sendReceive(requesterId,
			"www.fj.chinamobile.com/DataCenterSOAP/LoadingRequestDataRequest", model, null);
		
        
		if (DebuggingLog.willPrintDetailedInfo()) {
			DebuggingLog
					.printDetailedInfo("doLoadingRequest() - completed successfully!");
		}
		
		eventID.value = ((LoadingRequestStub.LoadingRequestRequestResponse)obj).getEventID();
//		return obj;
	}
	
	

	public static void main(String[] args) {
		String configurationFileName = null;
		try {
			configurationFileName = args[0];
		} catch (ArrayIndexOutOfBoundsException ex) {
			configurationFileName = "D:\\TNMS\\TNMS\\tnms-conf\\resesb\\serviceproxy.ini";
		}
		try {
			//-------------------------------------------------------
			// > init the config file for passed INI name or default
			//-------------------------------------------------------
			ServiceProxy.getInstance().initialize(configurationFileName);
			//-------------------------------------------------------
			// > Initialize the configuration of Client
			//-------------------------------------------------------
//			SOAFactory.initClient();
			//-------------------------------------------------------
			// > do the tests
			//-------------------------------------------------------
			LoadingRequestProxy testClient = new LoadingRequestProxy();
			StringHolder eventID =new StringHolder( "IRMS200811141424112241");//�����eventID�Ĳ�������ɵ�У���ļ���(��)
			int workMode = 1;
			int charSet = 0;
			String systemID = "IRMS";
			Calendar sendTime = new GregorianCalendar();
			String feedbackUri = "http://10.46.1.236:8080/irmsrtu/services/LoadingFeedbackSOAPport_http";
			//String feedbackUri = "-1";//����4����װ�ط�!��Ϣ������ṩ�������URI��������Ϊ�ַ�-1��������Ϊ����ṩ������
			int fileFormat = 2;
			int loadingFlag = 2;//1��ȫ��ݣ��������װ��ȫ���ǰ�����Ŀ��� 2������ݣ��������ֱ�ӽ�����ݲ��뵽Ŀ�����
			                    //3����������ݣ�������Ļ������ƥ�䣬���²����ɾ��Ŀ�����ݡ�
			String fieldNameList = ","; //ֻ���ļ���ʽΪ1��Ч���ֶ����б?���ŷָ�
			String xmlSchema = "";
			String fieldSeparator = "0x0d0x0a";//ֻ���ļ���ʽΪ1��Ч���ֶηָ���ڴ˴�Ӧ��Ϊʮ����Ʊ�ʾ����س�����Ӧ��Ϊ0x0d0x0a
			String lineSeparator = "0x0d0x0a";//ֻ���ļ���ʽΪ1��Ч���зָ���ڴ˴�Ӧ��Ϊʮ����Ʊ�ʾ����س�����Ӧ��Ϊ0x0d0x0a
			String dataInfo = "<?xml version=\"1.0\" encoding=\"GB2312\"?><resource-data xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"resource-data.xsd\">" +
			"<notifyId>IRMS_200809271733002</notifyId>" +
			"<class><updateType>2</updateType><resourceKind>OMC</resourceKind><resourceId>OMC-NMS-1810557470777a</resourceId><attributes><attribute name=\"OMC_TYPE\" value=\"2\" /><attribute name=\"LABEL_CN\" value=\"SIE-$OMC_HOST-88aa\" /><attribute name=\"RELATED_VENDOR_CUID\" value=\"DEVICE_VENDOR-13\" /><attribute name=\"MAINT_MODE\" value=\"1\" /><attribute name=\"RELATED_SPACE_CUID\" value=\"DISTRICT-00001-00030-00008\" /><attribute name=\"CUID\" value=\"OMC-NMS-1810557470777a\" /></attributes></class>" +
			"<class><updateType>2</updateType><resourceKind>OMC</resourceKind><resourceId>OMC-NMS-1810557470999a</resourceId><attributes><attribute name=\"OMC_TYPE\" value=\"2\" /><attribute name=\"LABEL_CN\" value=\"SIE-$OMC_HOST-888aaa\" /><attribute name=\"RELATED_VENDOR_CUID\" value=\"DEVICE_VENDOR-13\" /><attribute name=\"MAINT_MODE\" value=\"1\" /><attribute name=\"RELATED_SPACE_CUID\" value=\"DISTRICT-00001-00030-00008\" /><attribute name=\"CUID\" value=\"OMC-NMS-1810557470999a\" /></attributes></class>" +
			"<class><updateType>2</updateType><resourceKind>OMC</resourceKind><resourceId>OMC-NMS-1810557470111a</resourceId><attributes><attribute name=\"OMC_TYPE\" value=\"2\" /><attribute name=\"LABEL_CN\" value=\"SIE-$OMC_HOST-888aaaa\" /><attribute name=\"RELATED_VENDOR_CUID\" value=\"DEVICE_VENDOR-13\" /><attribute name=\"MAINT_MODE\" value=\"1\" /><attribute name=\"RELATED_SPACE_CUID\" value=\"DISTRICT-00001-00030-00001\" /><attribute name=\"CUID\" value=\"OMC-NMS-1810557470111a\" /></attributes></class>" +
			"<class><updateType>2</updateType><resourceKind>OMC</resourceKind><resourceId>OMC-NMS-1810557470222a</resourceId><attributes><attribute name=\"OMC_TYPE\" value=\"2\" /><attribute name=\"LABEL_CN\" value=\"SIE-$OMC_HOST-888aaaaa\" /><attribute name=\"RELATED_VENDOR_CUID\" value=\"DEVICE_VENDOR-13\" /><attribute name=\"MAINT_MODE\" value=\"1\" /><attribute name=\"RELATED_SPACE_CUID\" value=\"DISTRICT-00001-00030-00002\" /><attribute name=\"CUID\" value=\"OMC-NMS-1810557470222a\" /></attributes></class>" +
			"</resource-data>";//ֻ�ڹ���ʽΪ0ʱ��Ч��������Ҫװ�ص���ݼ�¼�������Ϣ�ĸ�ʽͬ������ļ����ļ���ʽҪ��
			String accessPoint ="http://10.46.1.236:8080/irmsrtu_north/services/LoadingRequestSOAPport_http";
			testClient.doLoadingRequest(accessPoint,eventID, systemID, sendTime,
					feedbackUri, loadingFlag, workMode, fileFormat, charSet,
					lineSeparator, fieldSeparator, fieldNameList, xmlSchema,
					dataInfo);
			System.out.println(eventID.value);
			//-------------------------------------------------------
			// > Shutdown the SOA Messaging
			//-------------------------------------------------------
			SOAFactory.shutdownClient();
		} catch (SOAException soex) {
			ResSystem.getLog().note(
					MessageLog.SEV_ERROR,
					"LoadingRequestProxy",
					"LoadingRequestProxy::main() - execution failure: SOAException encountered: "
							+ soex.getMessage());
			System.exit(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			ResSystem.getLog().note(MessageLog.SEV_ERROR, "LoadingRequestProxy",
					"LoadingRequestProxy::main() - - startup or execution failure");
			System.exit(0);
		}
	}
}
