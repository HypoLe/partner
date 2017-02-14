package com.boco.eoms.deviceManagement.technicData.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 
 * <p>
 * Title:文件过滤器
 * </p>
 * <p>
 * Description:用来获取某目录下所有文件，使用方法如下：File[] files = folder.listFiles(new
 * FileFilter()
 * </p>
 * <p>
 * Date:2011-09-15 14:08:50
 * </p>
 * 
 * @author 张珂齐
 *
 */
public class FileFilter implements FilenameFilter {

	public boolean accept(File dir, String name) {
		return new File(dir + File.separator + name).isFile();
	}

}
