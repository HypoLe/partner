package com.boco.eoms.deviceManagement.technicData.util;

/**
 * 
 * <p>
 * Title:代维技术资料管理属性
 * </p>
 * <p>
 * Description: 代维技术资料管理
 * </p>
 * <p>
 * Date:2011-09-15 14:08:50
 * </p>
 * 
 * @author 张珂齐
 *
 */
public class FileAttributes {

	/**
	 * 代维技术资料管理根路径
	 */
	private String netDiskRootPath;
	
	/**
	 * 最大单个上传文件限制（单位MB）
	 */
	private String maxFileSize;
	
	/**
	 * 用户个人空间（单位MB）
	 */
	private String maxUserSpace;
	
	public String getMaxFileSize() {
		return maxFileSize;
	}
	public void setMaxFileSize(String maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	public String getMaxUserSpace() {
		return maxUserSpace;
	}
	public void setMaxUserSpace(String maxUserSpace) {
		this.maxUserSpace = maxUserSpace;
	}
	public String getNetDiskRootPath() {
		return netDiskRootPath;
	}
	public void setNetDiskRootPath(String netDiskRootPath) {
		this.netDiskRootPath = netDiskRootPath;
	}
	
}
