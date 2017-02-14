package com.boco.eoms.partner.interfaces.services.esbclient;

public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// NOPTradeServiceLocator client = new NOPTradeServiceLocator();
		//
		// String s;
		// try {
		// s = client
		// .getNOPTradeServiceHttpPort()
		// .NOPTrade("BOCO.ESB.REGLOGIN.LOGIN", "", "", "", "", "",
		// "<login><Name>BOCO.PARTNER</Name><Password>123</Password></login>");
		// String token = s.substring(s.indexOf("<resultData>") + 12, s
		// .indexOf("</resultData>"));
		//
		// System.out.println(token);
		// } catch (RemoteException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (ServiceException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		NopTradeServiceDelegate s = new NopTradeServiceDelegate();
		System.out.println("begin:");
		System.out
				.println("resposne:"
						+ s
								.service("BOCO.ESB.REGLOGIN.LOGIN", "", "", "",
										"", "",
										"<login><Name>BOCO.PARTNER</Name><Password>123</Password></login>"));

	}

}
