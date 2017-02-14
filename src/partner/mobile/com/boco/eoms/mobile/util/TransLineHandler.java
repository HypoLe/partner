package com.boco.eoms.mobile.util;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boco.eoms.partner.res.model.PnrResConfig;
public class TransLineHandler {
	public static void handRequestLineSeg(HttpServletRequest request,
			HttpServletResponse response) {
			HashMap<String, Object> returnMap = new HashMap<String, Object>();
			List city = (List) request.getAttribute("city");
			String isyd = (String) request.getAttribute("isyd");
			String dept = (String) request.getAttribute("dept");
			String pageSize =  request.getAttribute("pageSize")+"";
			String resultSize = request.getAttribute("resultSize")+"";
			PnrResConfig pnrResConfigForm = (PnrResConfig) request.getAttribute("pnrResConfigForm");
			List<PnrResConfig> lsit = (List<PnrResConfig>)request.getAttribute("list");
			
			returnMap.put("city", city);
			returnMap.put("city1", city);
			returnMap.put("isyd", isyd);
			returnMap.put("dept", dept);
			returnMap.put("pageSize", pageSize);
			returnMap.put("resultSize", resultSize);
			returnMap.put("pnrResConfigForm", pnrResConfigForm);
			returnMap.put("lsit", lsit);
			String returnStr = MobileCommonUtils.replaceToJson(returnMap);
			System.out.println(returnStr);
			MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
	}

}
