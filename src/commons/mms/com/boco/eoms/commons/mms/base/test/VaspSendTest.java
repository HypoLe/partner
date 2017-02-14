package com.boco.eoms.commons.mms.base.test;

import com.boco.eoms.commons.mms.msssubscribe.mgr.impl.SendConfig;
import com.boco.eoms.commons.statistic.base.reference.StaticMethod;
import com.cmcc.mm7.vasp.common.MMConstants;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.service.MM7Sender;


public class VaspSendTest {
	
	public static void main(String[] args) throws Exception 
	{
		String mm7c = StaticMethod.getFilePathForUrl("classpath:config/mms/base/mm7Config.xml");
		String connc = StaticMethod.getFilePathForUrl("classpath:config/mms/base/ConnConfig.xml");
		String sendConfig = StaticMethod.getFilePathForUrl("classpath:config/SendConfig.xml");
		
		MM7Config mm7Config = new MM7Config(mm7c);
		mm7Config. setConnConfigName(connc);
		MM7Sender mm7Sender = new MM7Sender(mm7Config);
		MM7SubmitReq submit = new MM7SubmitReq();
	    
	    //需要按照本地需求修改 mod begin
		submit.addTo("15829550058");//发送彩信的手机号
//	    submit.setTransactionID("1000000");//11111111
//	    submit.setVASID("10658218");
//	    submit.setVASPID("826211");//MyAdd
//	    submit.setServiceCode("000");
	    SendConfig scg = SendConfig.getSendConfig(sendConfig);
	    submit.setTransactionID(scg.transactionID);//设置MM7_submit.REQ/MM7_submit.RES对的标识，必备
	    submit.setVASID(scg.VASID);//设置SP代码，必备
	    submit.setVASPID(scg.VASPID);//设置服务代码，必备
	    submit.setSenderAddress(scg.SenderAddress);//"MM始发方的地址 必备
	    submit.setServiceCode(scg.serviceCode);//设置业务代码，必备
	    submit.setChargedPartyID("1");//设置付费方的手机号码，必备
	    submit.setChargedParty((byte) 1);//VASP所提交MM的付费方  设置VASP所提交MM的付费方，例如，发送方、接收方、发送方和接收方或两方均不付费，可选，0：Sender、1：Recipients、2：Both、3：Neither、4：ThirdParty
		submit.setDeliveryReport(true); //设置是否需要发送报告的请求（boolean值）,可选
	    //mod end
	    
	    submit.setSubject("测试");
		MMContent content = new MMContent();
	    content.setContentType(MMConstants.ContentType. MULTIPART_MIXED);
	    String picpath = StaticMethod.getFilePathForUrl("classpath:config/mms/test/picfilename.gif");
		MMContent sub1 = MMContent.createFromFile(picpath);//"f:\\yellow.gif"
	    sub1.setContentID("1.gif");
	    sub1.setContentType(MMConstants.ContentType.GIF);
	    content.addSubContent(sub1);
		MMContent sub2 = MMContent.createFromString("This is a Test2!");
		sub2.setContentID("2.txt");
		sub2.setContentType(MMConstants.ContentType. TEXT);
		content.addSubContent(sub2);
		submit.setContent(content);
		MM7RSRes res = mm7Sender.send(submit);
		System.out.println("res.statuscode=" + res.getStatusCode() +
	                           ";res.statusText=" + res.getStatusText());
	}
}
