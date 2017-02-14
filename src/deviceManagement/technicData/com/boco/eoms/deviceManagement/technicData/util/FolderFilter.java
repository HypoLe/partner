package com.boco.eoms.deviceManagement.technicData.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 
 * <p>
 * Title:类别过滤器
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
public class FolderFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		return new File(dir + File.separator + name).isDirectory();
	}

}
