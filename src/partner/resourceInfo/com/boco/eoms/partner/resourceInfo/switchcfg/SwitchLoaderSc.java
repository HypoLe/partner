package com.boco.eoms.partner.resourceInfo.switchcfg;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.exolab.castor.xml.Unmarshaller;

import com.boco.eoms.partner.inspect.util.InspectConstants;

/**
 * 
 * 描述：四川版本的开关
 * 作者：munanyang
 * 时间：Jan 26, 2013-4:25:04 PM
 */
public class SwitchLoaderSc {
	/**不能以斜杆开头*/
	private static String cfgFilePath = "com/boco/eoms/partner/resourceInfo/config/pnr-sc-switch-config.xml";

	public static PnrScSwitchConfig loadSwitchCfg() throws Exception {
		String filePath = getRealFilePath(cfgFilePath);
		Reader reader = new InputStreamReader(new FileInputStream(filePath));
		PnrScSwitchConfig switchCfg = (PnrScSwitchConfig)Unmarshaller.unmarshal(PnrScSwitchConfig.class, reader);
//		InspectConstants.setSheetInspectSwitch(switchCfg.isSheetInspectSwitch());
		
		return switchCfg;
	}
	
	/**
	 * 获取文件绝对路径
	 * @param filePath 
	 * @return
	 */
	public static String getRealFilePath(String cfgFilePath) {
		String filePath = "";
		filePath = SwitchLoaderSc.class.getResource("/").getPath()+cfgFilePath;
		return filePath;
	}
	
}
