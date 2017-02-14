package com.boco.eoms.commons.mms.base.config;

import java.util.ArrayList;
import java.util.List;

public class Reports {
	
	private String serviceId = "";
	
	private Report reports[] = null;
	
	public String getStatReportTypeBySheetId(String sheetid)
	{
		for (int i = 0; i < reports.length; i++) 
		{
			Report r = reports[i];
			Sheet[] s = r.getSheet();
			for (int j = 0; j < s.length; j++) 
			{
				if(s[j].getId().equalsIgnoreCase(sheetid))
				{
					return r.getType();
				}
			}
		}
		
		return null;
	}
	
	public Report getReportBySheetId(String sheetid)
	{
		for (int i = 0; i < reports.length; i++) 
		{
			Report r = reports[i];
			Sheet[] s = r.getSheet();
			for (int j = 0; j < s.length; j++) 
			{
				if(s[j].getId().equalsIgnoreCase(sheetid))
				{
					return r;
				}
			}
		}
		return null;
	}

	public Report[] getReports() {
		return reports;
	}
	
	public Report getReportById(String id)
	{
		for (int i = 0; i < reports.length; i++) 
		{
			if(reports[i].getId().equalsIgnoreCase(id))
			{
				return reports[i];
			}
		}
		
		return null;
	}

	public void setReports(Report[] reports) {
		this.reports = reports;
	}
	
	public Sheet getSheetById(String id)
	{
		Sheet sheet = null;
		for(int i=0;i<reports.length;i++)
		{
			Report report = reports[i];
			Sheet[] sheets = report.getSheet();
			for(int j=0;j<sheets.length;j++)
			{
				if(id.equalsIgnoreCase(sheets[j].getId()))
				{
					sheet = sheets[j];
					break;
				}
			}
		}
		
		return sheet;
	}
	
	public List getAllSheet()
	{
		List sheetList = new ArrayList();
		for(int i=0;i<reports.length;i++)
		{
			Report report = reports[i];
			Sheet[] sheets = report.getSheet();
			for(int j=0;j<sheets.length;j++)
			{
				sheetList.add(sheets[j]);
			}
		}
		
		return sheetList;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}
