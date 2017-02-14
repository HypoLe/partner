package utils;


/**
 * 代维工具类，由Nop3Utils统一提供便利方法
 * 
 * @author Steve
 * 
 */
class Nop3UtilsMonitorCompanyHelper {
	/*// 同一个市，同一个县，同一个代维公司，同一种代维类型的数据在当月只能录入一次，一次一条。
	static boolean permitAddMonitorData(String monitorType, String country,
			String monitorCompany, int monthFlag) {

		// 如果是基站代维
		if (monitorType.equals(Nop3Constants.PROXY_TYPE_BASESTATION)) {
			BaseStationMainService baseStationMainService = Nop3Utils
					.getService(BaseStationMainService.class);
			// country based on id value not on text value, it's unique.
			int result = baseStationMainService.count(new Search()
					.addFilterEqual("country", country).addFilterEqual(
							"monitorCompany", monitorCompany).addFilterEqual(
							"monthFlag", monthFlag));
			if (result < 1) {
				return true;
			}
			return false;
		} else if (monitorType.equals(Nop3Constants.PROXY_TYPE_CIRCUIT)) {
			ICircuitLengthManager iCircuitLengthManager = Nop3Utils
					.getService(ICircuitLengthManager.class);
			// country based on id value not on text value, it's unique.

			List<CircuitLength> list = iCircuitLengthManager
					.getCfListByCondition(" and country='" + country
							+ "' and monitorCompany='" + monitorCompany
							+ "' and monthFlag='" + monthFlag + "'  ");
			if (Nop3Utils.nullToEmpty(list).size() < 1) {
				return true;
			}
			return false;
		}
		return false;
	}*/
}
