package com.boco.eoms.partner.interfaces.services.esbclient;

public class NopTradeServiceDelegate {
	public static String authorCode = "";
	public static long lastUse = 0;
	public static String ESB_USER = "BOCO.PARTNER";
	public static String ESB_USER_PASSWORD = "123";

	public String getAuthorCode() {

		if (NopTradeServiceDelegate.lastUse == 0
				|| System.currentTimeMillis() - NopTradeServiceDelegate.lastUse > 259200000) {
			// 生成 新的 authorCode；
			NopTradeServiceDelegate.authorCode = createAuthorCode();

		}
		NopTradeServiceDelegate.lastUse = System.currentTimeMillis();

		return NopTradeServiceDelegate.authorCode;
	}

	public String createAuthorCode() {
		NOPTradeServiceLocator client = new NOPTradeServiceLocator();
		String s = "";
		try {
			s = client.getNOPTradeServiceHttpPort().NOPTrade(
					"BOCO.ESB.REGLOGIN.LOGIN",
					"",
					"",
					"",
					"",
					"",
					"<login><Name>" + NopTradeServiceDelegate.ESB_USER
							+ "</Name><Password>"
							+ NopTradeServiceDelegate.ESB_USER_PASSWORD
							+ "</Password></login>");
			System.out.println("认证返回结果：" + s);
			String token = s.substring(s.indexOf("<resultData>") + 12, s
					.indexOf("</resultData>"));
			return token;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获得esb认证错误");
		}

		return "token error";
	}

	/**
	 * 
	 * 服务方法代理 替换authorCode
	 * 
	 * @param serviceCode
	 * @param userName
	 * @param roleID
	 * @param authCode
	 * @param ipAddress
	 * @param eventID
	 * @param serviceParas
	 * @return
	 */
	public String service(String serviceCode, String userName, String roleID,
			String authCode, String ipAddress, String eventID,
			String serviceParas) throws Exception {
		NOPTradeServiceLocator client = new NOPTradeServiceLocator();

		return client.getNOPTradeServiceHttpPort().NOPTrade(serviceCode,
				NopTradeServiceDelegate.ESB_USER, roleID, this.getAuthorCode(), ipAddress, eventID,
				serviceParas);

	}
}
