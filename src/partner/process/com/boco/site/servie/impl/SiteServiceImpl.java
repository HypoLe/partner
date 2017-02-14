package com.boco.site.servie.impl;

import com.boco.site.ftp.FtpUtil;
import com.boco.site.servie.SiteService;

import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.log4j.Logger;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: zhuchengxu Date: 13-12-2 Time: 上午11:21 To
 * change this template use File | Settings | File Templates.
 */
public class SiteServiceImpl implements SiteService {
	private static Logger logger = Logger.getLogger(SiteServiceImpl.class);
	private FtpUtil ftpUtil;
	private JdbcTemplate jdbcTemplate;
	private String tableName;
	private SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMM");
	private SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd");
	private String delimiter = System.getProperty("file.separator");

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setFtpUtil(FtpUtil ftpUtil) {
		this.ftpUtil = ftpUtil;
	}

	@Override
	public void saveSiteFile(String userId, String cname, String imgname,
			String imgpath, String dongitude, String dimension, String position) {
		System.out.println("开始同步抢修数据");
		logger.info("开始同步抢修数据");
		Calendar c = Calendar.getInstance();
		String dir = userId + delimiter + dateFormat1.format(c.getTime())
				+ delimiter + dateFormat2.format(c.getTime());
		try {
			File file = new File(imgpath);
			ftpUtil.upFile(file, dir, file.getName());
			this.insert(cname, file.getName(), dir, dongitude, dimension,
					new Date(), position);
		} catch (Exception e) {
			logger.info("同步出错",e);
			System.out.println("同步出错"+e.getMessage());
		}
		System.out.println("同步抢修数据完成");
		logger.info("同步抢修数据完成");
	}

	public void insert(String cname, String imgname, String imgpath,
			String dongitude, String dimension, Date ctime, String position) {
		logger.info("开始同步MYSQL抢修数据");
		System.out.println("开始同步MYSQL抢修数据");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time =dateFormat.format(ctime);
		String sql = "insert into "
				+ tableName
				+ " (cname,imgname,imgpath,dongitude,dimension,ctime,position) values (?,?,?,?,?,?,?)";
		Object[] args = { cname, imgname, imgpath, dongitude, dimension,time,
				position };
		jdbcTemplate.update(sql, args);
		System.out.println("同步MYSQL抢修数据完成");
		logger.info("同步MYSQL抢修数据完成");
	}
}
