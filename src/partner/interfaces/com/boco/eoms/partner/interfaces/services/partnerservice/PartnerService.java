package com.boco.eoms.partner.interfaces.services.partnerservice;

import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.baseinfo.mgr.IAptitudeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.Aptitude;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.interfaces.services.esbclient.NopTradeServiceDelegate;

public class PartnerService {
	private String type[] = { "代维公司", "协维公司" };

	public String getAllPartner() {
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");

		List<?> list = partnerDeptMgr.getPartnerDepts();
		StringBuffer sb = new StringBuffer();
		PartnerDept pd = null;
		sb.append("<partners>");
		for (int i = 0; i < list.size(); i++) {
			pd = (PartnerDept) list.get(i);

			sb.append(this.partnerDeptToXml(pd));

		}
		sb.append("</partners>");

		return sb.toString();
	}

	/**
	 * 得到所添加的合作伙伴信息
	 * 
	 * @param partnerDept
	 * @return
	 */
	public String getAddPartnerDeptInfo(PartnerDept partnerDept) {

		StringBuffer sb = new StringBuffer();
		sb.append("<partners>");
		sb.append(this.partnerDeptToXml(partnerDept));
		sb.append("</partners>");
		return sb.toString();
	}

	/**
	 * 得到所更新的合作伙伴信息
	 * 
	 * @param partnerDept
	 * @return
	 */
	public String getUpdatePartnerDeptInfo(PartnerDept partnerDept) {

		StringBuffer sb = new StringBuffer();
		sb.append("<partners>");
		sb.append(this.partnerDeptToXml(partnerDept));
		sb.append("</partners>");
		return sb.toString();
	}

	/**
	 * 得到删除的合作伙伴信息的主键
	 * 
	 * @param partnerId
	 * @return
	 */
	public String getRemovePartnerInfo(String partnerId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<partners>");
		sb.append("<partnerId>" + partnerId + "</partnerId>");
		sb.append("</partners>");
		return sb.toString();
	}

	/**
	 * 根据dictCode 从字典里面得到相对应的dictName
	 * 
	 * @param dictCode
	 * @return
	 */
	public String createType(String dictCode) {
		ITawSystemDictTypeManager iTawSystemDictTypeManager = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
		String dictName = "";
		TawSystemDictType tawSystemDictType = iTawSystemDictTypeManager
				.getDictByDictType(dictCode);
		dictName = tawSystemDictType.getDictName();

		return dictName;
	}

	public String createBigType(String name) {
		StringBuffer sb = new StringBuffer();
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder
				.getInstance().getBean("partnerDeptMgr");
		List<?> list = partnerDeptMgr.getPartnerDepts();
		PartnerDept pd = null;
		for (int i = 0; i < list.size(); i++) {
			pd = (PartnerDept) list.get(i);
			if (name != null && name.equals(pd.getName())) {
				if ((createType(pd.getBigType()) != null && !""
						.equals(createType(pd.getBigType())))) {
					sb.append(createType(pd.getBigType() + ","));
				} else {
					return "";
				}
			}

		}
		return sb.toString().substring(0, sb.toString().length());
	}

	/**
	 * 根据proName 得到与合作伙伴资质相关的信息
	 * 
	 * @param proName
	 * @return
	 */
	public String createAptitude(String proName) {
		IAptitudeMgr iAptitudeMgr = (IAptitudeMgr) ApplicationContextHolder
				.getInstance().getBean("iAptitudeMgr");
		List list = iAptitudeMgr.getAptitudes();
		Aptitude aptitude = null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			aptitude = (Aptitude) list.get(i);
			if (proName != null && proName.equals(aptitude.getProviderName())) {
				sb.append("服务资质名称：" + aptitude.getAptitudeName() + ",");
				sb.append("服务资质等级：" + createType(aptitude.getAptitudeLevle())
						+ ",");
				sb
						.append("资质生效时间："
								+ createTime(aptitude.getAptitudeStartTime()
										.toString()) + ",");
				sb.append("资质失效时间："
						+ createTime(aptitude.getAptitudeEndTime().toString())
						+ ".");
			} else {
				sb.append("");
			}
		}
		return sb.toString();
	}

	public String createTime(String time) {
		String times = "";
		if ("".equals(time)) {
			times = "";
		} else {
			times = time.substring(0, time.length() - 11);
		}

		return times;
	}

	public String doWithSelected(String value) {
		String result = "";
		if ("1".equals(value)) {
			result = "是";
		} else {
			result = "否";
		}
		return result;
	}

	private String partnerDeptToXml(PartnerDept partnerDept) {

		StringBuffer sb = new StringBuffer("");
		String tmpBigType = createBigType(partnerDept.getName());
		for (int i = 0; i < type.length; i++) {
			if (tmpBigType.contains(type[i])) {
				return "";
			}
		}
		
		sb.append("<partner>");

		sb.append("<partnerId>"
				+ (partnerDept.getId() == null ? "" : partnerDept.getId())
				+ "</partnerId>");
		// 1合作伙伴名称
		sb.append("<name>"
				+ (partnerDept.getName() == null ? "" : partnerDept.getName())
				+ "</name>");
		// 2法人代表
		sb.append("<manager>"
				+ (partnerDept.getManager() == null ? "" : partnerDept
						.getManager()) + "</manager>");

		// 3营业执照号
		sb.append("<licenceNumber>"
				+ (partnerDept.getLicenceNum() == null ? "" : partnerDept
						.getLicenceNum()) + "</licenceNumber>");
		// 4企业代码
		sb.append("<companyRegister>"
				+ (partnerDept.getCompanyRegister() == null ? "" : partnerDept
						.getCompanyRegister()) + "</companyRegister>");
		// 5单位性质
		sb.append("<found>"
				+ (partnerDept.getFund() == null ? "" : partnerDept.getFund())
				+ "万</found>");

		// 6单位资质等级及资质证书编号

		sb.append("<aptitude>"
				+ (createAptitude(partnerDept.getName()) == null ? ""
						: createAptitude(partnerDept.getName()))
				+ "</aptitude>");
		// 7单位性质
		sb.append("<type>"
				+ (createType(partnerDept.getType()) == null ? ""
						: createType(partnerDept.getType())) + "</type>");

		// 8公司主页
		sb.append("<homePage>"
				+ (partnerDept.getHomepage() == null ? "" : partnerDept
						.getHomepage()) + "</homePage>");
		// 9通信地址
		sb.append("<address>"
				+ (partnerDept.getAddress() == null ? "" : partnerDept
						.getAddress()) + "</address>");

		// 10邮政编码
		sb.append("<zip>"
				+ (partnerDept.getZip() == null ? "" : partnerDept.getZip())
				+ "</zip>");

		// 11企业法人
		sb.append("<managerCop>"
				+ (partnerDept.getManagercop() == null ? "" : partnerDept
						.getManagercop()) + "</managerCop>");
		// 12法人授权业务代表名字、手机、传真
		sb
				.append("<contactorphonefax>"
						+ "法人授权业务代表名字："
						+ (partnerDept.getContactor() == null ? ""
								: partnerDept.getContactor())
						+ " "
						+ "手机："
						+ (partnerDept.getPhone() == null ? "" : partnerDept
								.getPhone())
						+ "传真："
						+ (partnerDept.getFax() == null ? "" : partnerDept
								.getFax()));
		sb.append("</contactorphonefax>");
		// 13公司业务邮箱
		sb
				.append("<email>"
						+ (partnerDept.getEmail() == null ? "" : partnerDept
								.getEmail()) + "</email>");
		// 14单位总人数
		sb.append("<personCount>"
				+ (partnerDept.getPersonCou() == null ? "" : partnerDept
						.getPersonCou()) + "</personCount>");
		// 15是否入围
		sb.append("<selected>" + doWithSelected(partnerDept.getSelected())
				+ "</selected>");
		// 16大单位类型
		sb.append("<bigType>"
				+ (createBigType(partnerDept.getName()) == null ? ""
						: createBigType(partnerDept.getName())) + "</bigType>");

		// 17开户银行
		sb.append("<bank>"
				+ (partnerDept.getBank() == null ? "" : partnerDept.getBank())
				+ "</bank>");
		// 18开户名称
		sb.append("<accountName>"
				+ (partnerDept.getAccName() == null ? "" : partnerDept
						.getAccName()) + "</accountName>");
		// 19银行帐号
		sb.append("<account>"
				+ (partnerDept.getAccount() == null ? "" : partnerDept
						.getAccount()) + "</account>");
		// 20税务登记号
		sb.append("<registNO>"
				+ (partnerDept.getRegistNo() == null ? "" : partnerDept
						.getRegistNo()) + "</registNO>");
		// 21合作伙伴资质
		sb.append("<aptitude>"
				+ (createType(partnerDept.getAptitude()) == null ? ""
						: createType(partnerDept.getAptitude()))
				+ "</aptitude>");
		// 22资质有效期
		sb.append("<deadLine>"
				+ (partnerDept.getDeadline() == null ? "" : partnerDept
						.getDeadline()) + "</deadLine>");
		// 23所属地市
		sb.append("<area>"
				+ (partnerDept.getAreaName() == null ? "" : partnerDept
						.getAreaName()) + "</area>");

		sb.append("</partner>");

		return sb.toString();

	}

	public String addPartner(PartnerDept partnerDept) {

		NopTradeServiceDelegate nopTradeServiceDelegate = new NopTradeServiceDelegate();
		try {
			return nopTradeServiceDelegate.service(
					"DATA.PARTNER.EPMS.ADDPARTNER", "", "", "", "", "", this
							.getAddPartnerDeptInfo(partnerDept));
		} catch (Exception e) {
			e.printStackTrace();
			return "errors";
		}

	}

	public String addAllPartner() {

		NopTradeServiceDelegate nopTradeServiceDelegate = new NopTradeServiceDelegate();
		try {
			return nopTradeServiceDelegate.service(
					"DATA.PARTNER.EPMS.ADDPARTNER", "", "", "", "", "", this
							.getAllPartner());
		} catch (Exception e) {
			e.printStackTrace();
			return "errors";
		}

	}

	public String updatePartner(PartnerDept partnerDept) {
		NopTradeServiceDelegate nopTradeServiceDelegate = new NopTradeServiceDelegate();
		try {
			return nopTradeServiceDelegate.service(
					"DATA.PARTNER.EPMS.MODIFYPARTNER", "", "", "", "", "", this
							.getUpdatePartnerDeptInfo(partnerDept));
		} catch (Exception e) {
			e.printStackTrace();
			return "errors";
		}

	}

	public String deletePartner(String deptId) {
		NopTradeServiceDelegate nopTradeServiceDelegate = new NopTradeServiceDelegate();
		try {
			return nopTradeServiceDelegate.service(
					"DATA.PARTNER.EPMS.DELPARTNER", "", "", "", "", "", this
							.getRemovePartnerInfo(deptId));
		} catch (Exception e) {
			e.printStackTrace();
			return "errors";
		}

	}

}
