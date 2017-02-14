package com.boco.eoms.partner.interfaces.services;

import com.boco.eoms.commons.system.user.util.TawSystemUser4AInterFace;
import com.boco.eoms.partner.interfaces.services.partnerservice.PartnerService;

public class FriendService {

	public String service(String serviceCode, String userName, String roleID,
			String authCode, String ipAddress, String eventID,
			String serviceParas) {

		try {

			System.out.println("serviceCode===========" + serviceCode);
			System.out.println("userName===========" + userName);
			System.out.println("roleID===q========" + roleID);
			System.out.println("authCode===========" + authCode);
			System.out.println("ipAddress===========" + ipAddress);
			System.out.println("eventID===========" + eventID);
			System.out.println("serviceParas===========" + serviceParas);
			// 增加合作伙伴合同信息
			if ("BOCO.PARTNER.CONTRACT.ADD".equals(serviceCode)) {

				PartnerService ps = new PartnerService();
				return "<response>" + "<head>" + "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>" + "</head>"
						+ "<resultData>" + ps.getAllPartner() + "</resultData>"
						+ "</response>";
			}
			// 更新合作伙伴合同信息
			else if ("BOCO.PARTNER.CONTRACT.UPDATE".equals(serviceCode)) {

				PartnerService ps = new PartnerService();
				return "<response>" + "<head>" + "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>" + "</head>"
						+ "<resultData>" + ps.getAllPartner() + "</resultData>"
						+ "</response>";
			}
			// 删除合作伙伴合同信息
			else if ("BOCO.PARTNER.CONTRACT.DELETE".equals(serviceCode)) {

				PartnerService ps = new PartnerService();
				return "<response>" + "<head>" + "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>" + "</head>"
						+ "<resultData>" + ps.getAllPartner() + "</resultData>"
						+ "</response>";
			}
			// 得到全部从账号数量
			else if ("BOCO.PARTNER.4A.GETSUMSUBACCOUNT".equals(serviceCode)) {

				TawSystemUser4AInterFace tawSystemUser4AInterFace = new TawSystemUser4AInterFace();
				return "<response>"
						+ "<head>"
						+ "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>"
						+ "</head>"
						+ "<resultData>"
						+ tawSystemUser4AInterFace
								.getAllSubAccount(serviceParas)
						+ "</resultData>" + "</response>";
			}

			// 根据从账号id得到从账号信息
			else if ("BOCO.PARTNER.4A.GETSUBACCOUNT".equals(serviceCode)) {

				TawSystemUser4AInterFace tawSystemUser4AInterFace = new TawSystemUser4AInterFace();
				String s = "<response>"
						+ "<head>"
						+ "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>"
						+ "</head>"
						+ "<resultData>"
						+ tawSystemUser4AInterFace
								.getSingleSubAccountInfo(serviceParas)
						+ "</resultData>" + "</response>";
				System.out.println("return :" + s);
				return s;
			}

			// 得到所有从账号信息
			else if ("BOCO.PARTNER.4A.GETALLSUBACCOUNT".equals(serviceCode)) {

				TawSystemUser4AInterFace tawSystemUser4AInterFace = new TawSystemUser4AInterFace();
				return "<response>"
						+ "<head>"
						+ "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>"
						+ "</head>"
						+ "<resultData>"
						+ tawSystemUser4AInterFace
								.getAllSubAccountInfo(serviceParas)
						+ "</resultData>" + "</response>";
			}

			// 得到从账号信息（分页）
			else if ("BOCO.PARTNER.4A.GETALLSUBACCPAGE".equals(serviceCode)) {

				TawSystemUser4AInterFace tawSystemUser4AInterFace = new TawSystemUser4AInterFace();
				return "<response>"
						+ "<head>"
						+ "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>"
						+ "</head>"
						+ "<resultData>"
						+ tawSystemUser4AInterFace
								.getAllSubAccountInfoPages(serviceParas)
						+ "</resultData>" + "</response>";
			}

			// 新增从账号
			else if ("BOCO.PARTNER.4A.ADDSUBACCOUNT".equals(serviceCode)) {

				TawSystemUser4AInterFace tawSystemUser4AInterFace = new TawSystemUser4AInterFace();
				return "<response>" + "<head>" + "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>" + "</head>"
						+ "<resultData>"
						+ tawSystemUser4AInterFace.saveSubAccount(serviceParas)
						+ "</resultData>" + "</response>";
			}

			// 修改从账号信息
			else if ("BOCO.PARTNER.4A.EDITSUBACCOUNT".equals(serviceCode)) {
				try {
					TawSystemUser4AInterFace tawSystemUser4AInterFace = new TawSystemUser4AInterFace();
					return "<response>"
							+ "<head>"
							+ "<Result>0</Result>"
							+ "<ResultInfo>中文</ResultInfo>"
							+ "<DataType>none</DataType>"
							+ "</head>"
							+ "<resultData>"
							+ tawSystemUser4AInterFace
									.updateSubAccount(serviceParas)
							+ "</resultData>" + "</response>";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// 删除从账号
			else if ("BOCO.PARTNER.4A.DELSUBACCOUNT".equals(serviceCode)) {

				TawSystemUser4AInterFace tawSystemUser4AInterFace = new TawSystemUser4AInterFace();
				return "<response>"
						+ "<head>"
						+ "<Result>0</Result>"
						+ "<ResultInfo>中文</ResultInfo>"
						+ "<DataType>none</DataType>"
						+ "</head>"
						+ "<resultData>"
						+ tawSystemUser4AInterFace
								.romoveSubaccount(serviceParas)
						+ "</resultData>" + "</response>";
			}
			return "<response>" + "<head>" + "<Result>-1</Result>"
					+ "<ResultInfo>未找到服务</ResultInfo>"
					+ "<DataType>none</DataType>" + "</head>" + "</response>";

		} catch (Exception e) {
			e.printStackTrace();
			return "<response>" + "<head>" + "<Result>-1</Result>"
					+ "<ResultInfo>" + e.getMessage() + "</ResultInfo>"
					+ "<DataType>none</DataType>" + "</head>" + "</response>";
		}

	}
}
