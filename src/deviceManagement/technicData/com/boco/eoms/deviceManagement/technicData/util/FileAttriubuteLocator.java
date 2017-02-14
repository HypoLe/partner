package com.boco.eoms.deviceManagement.technicData.util;

import java.io.File;

import com.boco.eoms.base.util.ApplicationContextHolder;

/**
 * 
 * <p>
 * Title:代维技术资料管理的Locator
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2011-09-15 14:08:50
 * </p>
 * 
 * @author 张珂齐
 *
 */
public class FileAttriubuteLocator {

	public static FileAttributes getNetDiskAttributes() {
		FileAttributes attributes = (FileAttributes) ApplicationContextHolder
				.getInstance().getBean("fileAttributes");
		return attributes;
	}

	/**
	 * 剩余空间信息
	 * 
	 * @param userId
	 * @return
	 */
	public static String getFreeSpaceSizeInfo(String userId) {
		return "可用空间：" + (int) getAvailableSize(userId) + "MB  已使用："
				+ (int) getCurrentUserSize(userId) + "MB / "
				+ (int) getMaxUserSize() + "MB ("
				+ (int) getAvailablePercent(userId) + "%)";
	}

	/**
	 * 可用空间
	 * 
	 * @param userId
	 * @return
	 */
	public static float getAvailableSize(String userId) {
		float availableSize = getMaxUserSize() - getCurrentUserSize(userId);
		if (availableSize <= 0) {
			availableSize = 0;
		}
		return availableSize;
	}

	/**
	 * 可用百分比
	 * 
	 * @param userId
	 * @return
	 */
	private static float getAvailablePercent(String userId) {
		float percent = getCurrentUserSize(userId) / getMaxUserSize() * 100;
		if (getAvailableSize(userId) <= 0) {
			percent = 100;
		}
		return percent;
	}

	/**
	 * 当前已用空间
	 * 
	 * @param userId
	 * @return
	 */
	private static float getCurrentUserSize(String userId) {
		FileAttributes attributes = (FileAttributes) ApplicationContextHolder
				.getInstance().getBean("fileAttributes");
		String rootPath = attributes.getNetDiskRootPath();
		String userRootPath = rootPath + File.separator + userId;
		// 如果用户根路径不存在,则创建
		File file = new File(userRootPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		long currentUserSize = computeFileSize(file);
		return (float) currentUserSize / 1024 / 1024;
	}

	/**
	 * 用户可用最大空间
	 * 
	 * @return
	 */
	private static float getMaxUserSize() {
		FileAttributes attributes = (FileAttributes) ApplicationContextHolder
				.getInstance().getBean("fileAttributes");
		return Float.parseFloat(attributes.getMaxUserSpace());
	}

	/**
	 * 计算文件或文件夹大小（递归）
	 * 
	 * @param file
	 *            文件或文件夹
	 * @return
	 * @throws Exception
	 */
	private static long computeFileSize(File file) {
		long size = 0;
		File[] fileList = file.listFiles();
		for (int i = 0; fileList != null && i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + computeFileSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size;
	}
}
