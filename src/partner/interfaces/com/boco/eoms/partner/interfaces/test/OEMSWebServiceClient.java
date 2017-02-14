package com.boco.eoms.partner.interfaces.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.BooleanHolder;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationLocator;
import com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationPortType;
import com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryLocator;
import com.boco.eoms.partner.interfaces.services.LoadingQuery.LoadingQueryPortType;
import com.boco.eoms.partner.interfaces.services.LoadingRequest.LoadingRequestLocator;
import com.boco.eoms.partner.interfaces.services.LoadingRequest.LoadingRequestPortType;
public class OEMSWebServiceClient {
    private static Log log = LogFactory.getLog(OEMSWebServiceClient.class);

    private LoadingPreparationPortType LoadingPreparationport;
    private LoadingRequestPortType LoadingRequestport;
    private LoadingQueryPortType LoadingQueryport;
    private static String mqstr = "<?xml version=\"1.0\" encoding=\"GB2312\"?><resource-data xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"resource-data.xsd\"><notifyId>IRMS_20080927173300</notifyId><class><updateType>1</updateType><resourceKind>OMC</resourceKind><resourceId>OMC-NMS-2055601473</resourceId><attributes><attribute name=\"OMC_TYPE\" value=\"2\" /><attribute name=\"LABEL_CN\" value=\"SIE-$OMC_HOST-999\" /><attribute name=\"RELATED_VENDOR_CUID\" value=\"DEVICE_VENDOR-13\" /><attribute name=\"MAINT_MODE\" value=\"1\" /><attribute name=\"RELATED_SPACE_CUID\" value=\"DISTRICT-00001-00030-00008\" /><attribute name=\"CUID\" value=\"OMC-NMS-2055601473\" /></attributes></class></resource-data>";
    private String ipaddress = "http://10.210.17.49:8080/eomsMain/services";
    
    public void getLoadingPreparationPort() {
        LoadingPreparationLocator server = new LoadingPreparationLocator();
        try {
            LoadingPreparationport = server.getLoadingPreparationSOAPport_http(new URL(ipaddress + "/LoadingPreparationSOAPport_http"));
        } catch (MalformedURLException e) {
            log.error(e.toString());
        } catch (ServiceException e) {
            log.error(e.toString());
        }
    }

    public void getLoadingRequestPort() {
        LoadingRequestLocator server = new LoadingRequestLocator();
        try {
            LoadingRequestport = server.getLoadingRequestSOAPport_http(new URL(ipaddress + "/LoadingRequestSOAPport_http"));
        } catch (MalformedURLException e) {
            log.error(e.toString());
        } catch (ServiceException e) {
            log.error(e.toString());
        }
    }

    public void getLoadingQueryPort() {
        LoadingQueryLocator server = new LoadingQueryLocator();
        try {
            LoadingQueryport = server.getLoadingQuerySOAPport_http(new URL(ipaddress + "/LoadingQuerySOAPport_http"));
        } catch (MalformedURLException e) {
            log.error(e.toString());
        } catch (ServiceException e) {
            log.error(e.toString());
        }
    }

    public void LoadingPreparation() {
        String LoadingPreparationresult = "";
        getLoadingPreparationPort();
        try {
            System.out.println("test breakpoint");
            int suggestFileFormat = 2;
            StringHolder eventID = new StringHolder("IRMS2010010715001000001");
            int suggestWorkMode = 1;
            int suggestCharSet = 0;
            String systemID = "IRMS";
            IntHolder workMode = new IntHolder(1);
            IntHolder charSet = new IntHolder(1);
            StringHolder path = new StringHolder();
            path.value = "";
            StringHolder connectionString = new StringHolder();
            connectionString.value = "";
            BooleanHolder isCompressed = new BooleanHolder(false);
            Calendar sendTime = new GregorianCalendar();
            LoadingPreparationport.loadingPreparationRequest(eventID, systemID, sendTime,
                                                             suggestWorkMode, suggestFileFormat, suggestCharSet,
                                                             workMode, charSet, connectionString, path, isCompressed);
            log.info("专业网管处理装载反馈返回值为：" + workMode.value + " " + eventID.value + " "
                     + charSet.value + " " + connectionString.value + " " + path.value + " "
                     + String.valueOf(isCompressed.value));
            this.LoadingRequest();
        } catch (Exception e) {
            log.debug(e.toString());
            e.printStackTrace();
        }
        System.out.println("print content: " + LoadingPreparationresult);
    }

    public void LoadingRequest() {
        String LoadingRequestresult = "";
        getLoadingRequestPort();
        try {
            System.out.println("test breakpoint");
            StringHolder eventID = new StringHolder("IRMS2010010715001000001");
//            StringHolder eventID = new StringHolder("IRMS20081104142411344");
            String systemID = "IRMS";
            Calendar sendTime = new GregorianCalendar();
            log.info("sendTime is:" + sendTime.getTime().toString());
            /**
             * 0：表示基于消息的批量数据装载
             * 1：表示基于文件的批量数据装载
             */
            int workMode = 1;
//          String feedbackUri = "http://10.210.17.49:8080/partnerRtu/services/LoadingFeedbackSOAPport_http";
            String feedbackUri = "-1";
            int fileFormat = 2;
            int charSet = 0;
            /**
             * 1：全量数据，数据中心装载全量数据前会清空目标表
             * 2：增量数据，数据中心直接将增量数据插入到目标表中
             * 3：更新增量数据，数据中心会根据主键匹配，更新插入或删除目标表数据。
             */
            int loadingFlag = 1;
            String fieldNameList = ",";
            String xmlSchema = "";
            String fieldSeparator = "0x0d0x0a";
            String lineSeparator = "0x0d0x0a";
            SAXReader saxReader = new SAXReader();
//    		File file = new File("D:\\my.xml");
//    		Document document = saxReader.read(file);
    		String dataInfo = "";//document.asXML();
            LoadingRequestport.loadingRequestRequest(eventID, systemID, sendTime,
                                                     feedbackUri, loadingFlag, workMode, fileFormat, charSet,
                                                     lineSeparator, fieldSeparator, fieldNameList, xmlSchema,
                                                     dataInfo);
            log.info("专业网管处理装载反馈返回值为：" + workMode);
            System.out.println("print content: " + dataInfo);
        } catch (Exception e) {
            log.debug(e.toString());
            e.printStackTrace();
        }
    }

    public void LoadingQuery() {
        String LoadingQueryresult = "";
        getLoadingQueryPort();
        try {
            System.out.println("test breakpoint");
            int suggestFileFormat = 2;
            StringHolder eventID = new StringHolder("IRMS2010010617151900001");
            int suggestWorkMode = 1;
            int suggestCharSet = 0;
            String systemID = "IRMS";
            IntHolder workMode = new IntHolder(1);
            IntHolder charSet = new IntHolder(1);
            StringHolder path = new StringHolder();
            path.value = "";
            StringHolder connectionString = new StringHolder();
            connectionString.value = "";
            BooleanHolder isCompressed = new BooleanHolder(false);
            Calendar sendTime = new GregorianCalendar();
            LoadingQueryport.loadingQueryRequest(eventID, systemID, sendTime, workMode, path);
            log.info("专业网管处理装载反馈返回值为：" + workMode.value + " " + eventID.value + " "
                     + charSet.value + " " + connectionString.value + " " + path.value + " "
                     + String.valueOf(isCompressed.value));
        } catch (Exception e) {
            log.debug(e.toString());
            e.printStackTrace();
        }
        System.out.println("print content: " + LoadingQueryresult);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        OEMSWebServiceClient owsc = new OEMSWebServiceClient();
        owsc.LoadingPreparation();
//          owsc.LoadingRequest();
//          owsc.LoadingQuery();
//        owsc.ReplyResourceSheet();
//        owsc.SyncResourceService();
    }
}
