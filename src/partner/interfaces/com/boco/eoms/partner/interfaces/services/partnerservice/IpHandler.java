package com.boco.eoms.partner.interfaces.services.partnerservice;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;

public class IpHandler extends BasicHandler {

	private static final long serialVersionUID = 1L;
	public String ipAllow = "";

	public void init() {
		ipAllow = (String) this.getOption("ipAllow");
	}

	public void invoke(MessageContext context) throws AxisFault {

		String requestIp = (String) context.getProperty("remoteaddr");
		System.out.println("requestIp=" + requestIp);

		System.out.println(" context.getSOAPActionURI()"
				+ context.getSOAPActionURI());

		if (ipAllow.indexOf(requestIp) > -1) {
			System.out.println("运行访问");

		} else {
			System.out.println("禁止访问IP" + requestIp);
			throw new AxisFault("客户端IP：" + requestIp + "禁止访问");
		}
	}
	
}
