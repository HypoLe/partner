package com.boco.eoms.partner.deviceInspect.switchcfg;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.exolab.castor.xml.Unmarshaller;

import com.boco.eoms.partner.inspect.util.InspectConstants;

/**
 * 
 * 描述：巡检资源与网络资源关联的开关配置加载器
 * 作者：zhangkeqi
 * 时间：Jan 26, 2013-4:25:04 PM
 */
public class SwitchLoader {
	/**不能以斜杆开头*/
	private static String cfgFilePath = "com/boco/eoms/partner/deviceInspect/config/pnr-deviceInspect-switch-config.xml";

	public static PnrDeviceInspectSwitchConfig loadSwitchCfg() throws Exception {
		String filePath = getRealFilePath(cfgFilePath);
		Reader reader = new InputStreamReader(new FileInputStream(filePath));
		PnrDeviceInspectSwitchConfig switchCfg = (PnrDeviceInspectSwitchConfig)Unmarshaller.unmarshal(PnrDeviceInspectSwitchConfig.class, reader);
		InspectConstants.setSheetInspectSwitch(switchCfg.isSheetInspectSwitch());
		InspectConstants.setSwitchConfig(switchCfg);
		return switchCfg;
	}
	
	/**
	 * 获取文件绝对路径
	 * @param filePath 
	 * @return
	 */
	public static String getRealFilePath(String cfgFilePath) {
		String filePath = "";
		filePath = SwitchLoader.class.getResource("/").getPath()+cfgFilePath;
		return filePath;
	}
	
}
