package com.boco.eoms.partner.baseinfo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;




/**
 * <p>
 * Title:��f��Ϣ
 * </p>
 * <p>
 * Description:��f��Ϣ
 * </p>
 * <p>
 * Tue Feb 10 17:33:14 CST 2009
 * </p>
 * 
 * @author liujinlong
 * @version 3.5
 * 
 */
public class PartnerUserConstants {
	
	/**
	 * list key
	 */
	public final static String PARTNERUSER_LIST = "partnerUserList";
	
	/**
	 * 合作伙伴人力资源管理对应的roleId
	 */
	public final static int PARTNER_USER_ROLEID = 386;
	/**
	 * 技能信息的表名
	 */
	public final static String DWINFO_TABLENAME="partner_dwinfo";
	public final static String CERTIFICATION_TABLENAME="partner_certificate";
	public final static String STUDYEXPERIENCE_TABLENAME="panter_studyexperience";
	public final static String WORKEXPERIENCE_TABLENAME="partner_workexperience";
	public final static String PXEXPERIENCE_TABLENAME="panter_pxexperience";
	public final static String REWARD_TABLENAME="panter_reward";
	/**
	 * 技能信息系统编码的前3位字符
	 */
	public final static String DWINFO_SYSNO_FLAG="JN-";
	public final static String CERTIFICATION_SYSNO_FLAG="ZS-";
	public final static String STUDYEXPERIENCE_SYSNO_FLAG="ED-";
	public final static String WORKEXPERIENCE_SYSNO_FLAG="WE-";
	public final static String PXEXPERIENCE_SYSNO_FLAG="PE-";
	public final static String REWARD_SYSNO_FLAG="JL-";
	/**
	 * 验证邮箱格式
	 * @param line
	 * @return
	 */
	public static boolean emailFormat(String email)
    {
        boolean tag = true;
        final String pattern1 = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";//"^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

}