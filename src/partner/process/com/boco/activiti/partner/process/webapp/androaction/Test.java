package com.boco.activiti.partner.process.webapp.androaction;

import java.util.HashMap;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		String[][] type = {
				{ "请选择", "基站", "接入网", "直放站室分", "WLAN", "铁塔及天馈", "重点客户机房" },
				{ "", "112250103", "112250502", "112250302", "112250601",
						"112250404", "112250901" } };

		String[][][] level = {
				{ { "请选择 " }, { "" } },
				{
						{ "VIP基站", "A类", "B类", "C类" },
						{ "11225010301", "11225010302", "11225010303",
								"11225010304" } },
				{ { "A类", "B类", "C类" },
						{ "11225050201", "11225050202", "11225050203" } },
				{
						{ "标准", "VIP", "A类", "B类" },
						{ "11225030201", "11225030202", "11225030203",
								"11225030204" } },
				{ { "A类", "B类", "C类" },
						{ "11225060101", "11225060102", "11225060103" } },
				{ { "月标准", "季度" }, { "11225040401", "11225040402" } },
				{
						{ "VIP", "A类", "B类", "C类" },
						{ "11225090101", "11225090102", "11225090103",
								"11225090104" } } };
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("type", type);
		map.put("level", level);
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		System.out.println("返回数组---------" + jsonObject.toString());
		
//		ITawSystemAreaManager mgr = (ITawSystemAreaManager) ApplicationContextHolder
//		.getInstance().getBean("ItawSystemAreaManager");
//
//
//		JSONArray jsonRoot =new JSONArray();
//		jsonRoot.put(value)u
//		
//		JSONObject jitem2 = new JSONObject();
//		
//		JSONObject jitem3 = new JSONObject();
		
		
		
		
	}
}
