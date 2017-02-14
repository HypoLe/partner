package com.boco.eoms.partner.geo.client;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

public class MainClass {
	public static void main(String[] args) {
		
		try {
			String[] mobiles = {
					"闽AAT732",
					"闽AAT725",
					"闽AX6195",
					"闽AAT175",
					"闽AK1013",
					"闽AK1005",
					"闽AK1226",
					"闽AX6111",
					"闽AK1291",
					"闽AX6253",
					"闽AX6237",
					"闽AK1019",
					"闽AK1510",
					"闽X6109",
					"闽AK3218",
					"闽AX6059",
					"闽AX6102",
					"闽AK1018",
					"闽AX6259",
					"闽AK0988",
					"闽AK1210",
					"闽A08939",
					"闽AX6268",
					"闽AK1508",
					"闽AK0991",
					"闽AX6260",
					"闽AX6103",
					"闽DU3752",
					"闽H37605",
					"闽H37730",
					"闽AG2552",
					"闽C1861E",
					"闽H37658",
					"闽C57509",
					"闽C57398",
					"闽AB9385",
					"闽HK0638",
					"闽C43871",
					"闽C57577",
					"闽C0599A",
					"闽AE0302",
					"闽EC8138",
					"闽EC8082",
					"闽A35348",
					"闽A1J159",
					"闽A1J136",
					"闽A1Z566",
					"闽A3E351",
					"闽AE3702",
					"闽A1J130",
					"闽AQ5936",
					"闽AE8933",
					"闽AE1250",
					"闽A1K330",
					"闽AS6912",
					"闽AW0382",
					"闽AD5035",
					"闽AQ6165",
					"闽AD5011",
					"闽AE8983",
					"闽AQ7158",
					"闽AE8878",
					"闽AE1289",
					"闽A3Z652",
					"闽AE1261",
					"闽AQ3051",
					"闽AE8973",
					"闽CU7905",
					"闽AE1269",
					"闽A1J199",
					"闽C1R065",
					"闽ABJ869",
					"闽CG9582",
					"闽DBL179",
					"闽DBA171",
					"闽DL6618",
					"闽CEG195",
					"闽CER678",
					"闽CBY392",
					"闽QX988",
					"闽C4E182",
					"闽C7J898",
					"闽AE1250",
					"闽C9S735",
					"闽F43060",
					"闽D67682",
					"闽C49711",
					"闽C9S771",
					"闽AM9786",
					"闽DG9766",
					"闽DEY258",
					"闽C7J878",
					"闽DX2138",
					"闽AE3685",
					"闽AQ2731",
					"闽A1Z555",
					"闽A1Z322",
					"闽CLF295",
					"闽CPC110",
					"闽C58071",
					"闽C8A376",
					"闽CEA537",
					"闽AP7328",
					"闽CEB175",
					"闽CDA299"

			};
			YddwserPortType service = (YddwserPortType)new YddwserLocator().getyddwserHttpPort();
			for(String mobile :mobiles){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//service.f_dolcs(mobile, 1);
				System.out.println(service.f_qryhislcsdata(mobile, 2, "2010-01-07 00:00:00", "2010-01-07 23:00:00").length );
				//System.out.println(service.f_qrycurlcsdata(mobile, 2));
			}
		//	YddwserPortType service = (YddwserPortType)new YddwserLocator().getyddwserHttpPort();
		//	System.out.println(service.f_qrycurlcsdata("15906074796", 1));
		//	System.out.println(service.f_qryhislcsdata("15906074796", 1, "2010-01-07 10:00:00", "2010-01-07 23:00:00").length);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
