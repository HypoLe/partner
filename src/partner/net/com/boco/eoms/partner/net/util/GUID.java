package com.boco.eoms.partner.net.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 生成唯一标识
 * 
 */
public class GUID 
{
	private Log log = LogFactory.getLog(GUID.class);
	private String valueBeforeMD5;
    private String valueAfterMD5;
    private static Random myRand;
    private static SecureRandom mySecureRand;
    private static String s_id;

    static 
    {
        mySecureRand = new SecureRandom();
        long secureInitializer = mySecureRand.nextLong();
        myRand = new Random(secureInitializer);
        try
        {
            s_id = InetAddress.getLocalHost().toString();
        }
        catch(UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
    
	/**
	 * 无参数的GUID构造函数
	 */
    public GUID()
    {
    	valueBeforeMD5 = "";
        valueAfterMD5 = "";
    }

	/**
	 * 布尔类型参数的GUID构造函数
	 */
    public GUID(boolean secure)
    {
        valueBeforeMD5 = "";
        valueAfterMD5 = "";
        getRandomGUID(secure);
    }

	/**
	 * 获得随机数的GUID数组
	 */
    private synchronized void getRandomGUID(boolean secure)
    {
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer();
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch(NoSuchAlgorithmException e)
        {
            log.error("生成知识ID发生错误!" + e.toString());
        }
        try
        {
            long time = System.currentTimeMillis();
            long rand = 0L;
            if(secure)
                rand = mySecureRand.nextLong();
            else
                rand = myRand.nextLong();
            sbValueBeforeMD5.append(s_id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(time));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(rand));
            valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(valueBeforeMD5.getBytes());
            byte array[] = md5.digest();
            StringBuffer sb = new StringBuffer();
            for(int j = 0; j < array.length; j++)
            {
                int b = array[j] & 0xff;
                if(b < 16)
                    sb.append('0');
                sb.append(Integer.toHexString(b));
            }
            valueAfterMD5 = sb.toString();
        }
        catch(Exception e)
        {
        	log.error("生成知识ID发生错误!" + e.toString());
        }
    }
    
    /**
     * 获取数字型的数字
     */
    private synchronized void getNumberGUID(boolean secure)
    {
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer();
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch(NoSuchAlgorithmException e)
        {
            log.error("生成知识ID发生错误!" + e.toString());
        }
        try
        {
            long time = System.currentTimeMillis();
            long rand = 0L;
            if(secure)
                rand = mySecureRand.nextLong();
            else
                rand = myRand.nextLong();
            sbValueBeforeMD5.append(s_id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(time));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(rand));
            valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(valueBeforeMD5.getBytes());
            byte array[] = md5.digest();
            StringBuffer sb = new StringBuffer();
            for(int j = 0; j < array.length; j++)
            {
                int b = array[j] & 0xff;
                if(b < 16)
                    sb.append('0');
                sb.append(Integer.toOctalString(b));
            }
            valueAfterMD5 = sb.toString();
        }
        catch(Exception e)
        {
        	log.error("生成知识ID发生错误!" + e.toString());
        }
    }

	/**
	 * 产生GUID字符串
	 */
    public String toString()
    {
        getRandomGUID(false);
        String raw = valueAfterMD5.toUpperCase();
        StringBuffer sb = new StringBuffer();
        sb.append(raw.substring(0, 8));
        sb.append("-");
        sb.append(raw.substring(8, 12));
        sb.append("-");
        sb.append(raw.substring(12, 16));
        sb.append("-");
        sb.append(raw.substring(16, 20));
        sb.append("-");
        sb.append(raw.substring(20));
        return sb.toString();
    }
    
    /**
     * 产生数字的字符串
     */
    public String toNumber(){
        getNumberGUID(false);
    	String raw = valueAfterMD5.toUpperCase();
    	String sb = raw.substring(0, 8);
    	return sb;
    }
}
