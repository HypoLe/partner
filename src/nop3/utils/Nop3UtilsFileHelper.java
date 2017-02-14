package utils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.accessories.service.ITawCommonsAccessoriesManager;
import com.boco.eoms.commons.accessories.util.AccessoriesAttributes;

public class Nop3UtilsFileHelper {
	/**
	 * 使用eoms附件组件上传文件的时候，该公共方法可以获得附件在服务器上的真实地址
	 * 
	 * @param accessoriesValue:附件名
	 * @return
	 */
	public static String getPathValue(String accessoriesValue) {

		String circuitAccessories = StaticMethod.nullObject2String(
				accessoriesValue).replace("'", "");
		ITawCommonsAccessoriesManager iTawCommonsAccessoriesManager = (ITawCommonsAccessoriesManager) ApplicationContextHolder
				.getInstance().getBean("ItawCommonsAccessoriesManager");
		TawCommonsAccessories accessories = iTawCommonsAccessoriesManager
				.getTawCommonsAccessoriesByName(circuitAccessories);
		String path = StaticMethod.nullObject2String(accessories
				.getAccessoriesPath());
		AccessoriesAttributes accessoriesAttributes = (AccessoriesAttributes) ApplicationContextHolder
				.getInstance().getBean("accessoriesAttributes");
		String forpath = accessoriesAttributes.getUploadPath();
		String fileUrl = forpath
				+ path
				+ "/"
				+ StaticMethod.nullObject2String(circuitAccessories.replace(
						"'", ""));
		return fileUrl;
	}
}
