package com.boco.eoms.commons.report.ynsynthetical.clientscale;

public class CalculateUtil {

	/**
	 * p1 - p2 = ?
	 * @param p1 75.23%
	 * @param p2 73.21%
	 * @return 2.02%
	 */
	public static String calculatePercent(String p1,String p2)
	{
		int ip1 = p1.indexOf("%");
		int ip2 = p2.indexOf("%");
		if(ip1 != -1)
		{
			p1 = p1.replace("%", "");
		}
		if(ip2 != -1)
		{
			p2 = p2.replace("%", "");
		}
		
		double p3 =  Double.parseDouble(p1) - Double.parseDouble(p2);
		//保留小数点2位
		p3 = (double) (Math.round(p3*100)/100.0);
		
		String r = "";
		if(ip1 != -1 || ip2 != -1)
		{
			r = String.valueOf(p3) + "%";
		}
		else
		{
			r = String.valueOf(p3);
		}
		return r;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String x = calculatePercent("78.59%","78.23%");
		String y = calculatePercent("78.59","78.23");
		System.out.println(x);
		System.out.println(y);
	}

}
