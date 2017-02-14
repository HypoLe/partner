package com.boco.eoms.mobile.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.commons.accessories.exception.AccessoriesException;


public interface IMobileUpFileService {
	public List saveFile(HttpServletRequest request, String appCode,
			String accesspriesFileNames) throws AccessoriesException ;
}
