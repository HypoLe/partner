/*package utils;

import static utils.Nop3Constants.FAILURE;
import static utils.Nop3Constants.SUCCESS;
import base.interfaces.Supervisable;

import com.boco.eoms.commons.loging.BocoLog;
import com.google.common.base.Strings;
import com.google.gson.Gson;

class Nop3UtilsSupervisionHelper {

	public static void main(String[] args) {
	}

	public static void prepareSupervision(Object object) {

	}

	@SuppressWarnings("unchecked")
	public static <T> T prepareSupervision(Class<T> clazz, String myType,
			Object... args) {
		if (Strings.nullToEmpty(myType).equals("")) {
			return null;
		}
		if (clazz == SupervisionMain.class) {

			Supervisable supervisionMainGenerator = new SupervisionMainGenerator(
					myType, args);
			return (T) supervisionMainGenerator.prepare();

		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static String createSupervision(String supervisionMainJsonStr) {
		// 当对象是String，表示的是基于SupervisionMain的督办实现，参数是来源于接口并且封装成了Json的形式
		String myJsonStr = Strings.nullToEmpty(supervisionMainJsonStr);

		SupervisionMain supervisionMain = new Gson().fromJson(myJsonStr,
				SupervisionMain.class);

		if (supervisionMain == null || supervisionMain.getMyType() == null) {
			return FAILURE;
		}

		String myType = supervisionMain.getMyType();
		Supervisable supervisionMainGenerator = new SupervisionMainGenerator(
				myType, supervisionMain);
		return supervisionMainGenerator.create() == null ? FAILURE : SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public static String finishSupervision(String supervisionMainJsonStr) {
		// 当对象是String，表示的是基于SupervisionMain的督办实现，参数是来源于接口并且封装成了Json的形式
		String myJsonStr = Strings.nullToEmpty(supervisionMainJsonStr);

		SupervisionMain supervisionMain = new Gson().fromJson(myJsonStr,
				SupervisionMain.class);

		if (supervisionMain == null || supervisionMain.getMyType() == null) {
			return FAILURE;
		}

		String myType = supervisionMain.getMyType();
		Supervisable supervisionMainGenerator = new SupervisionMainGenerator(
				myType, supervisionMain);
		return supervisionMainGenerator.finish() == null ? FAILURE : SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public static <T> T createSupervision(Class<T> clazz, String myType,
			Object... args) {
		if (Strings.nullToEmpty(myType).equals("")) {
			return null;
		}
		// 当对象是SupervisionMain，表示的是基于SupervisionMain的督办实现
		if (clazz == SupervisionMain.class) {
			Supervisable supervisionMainGenerator = new SupervisionMainGenerator(
					myType, args);
			return (T) supervisionMainGenerator.create();
		}
		// 在这里书写其它条件
		else {
			BocoLog
					.info(Nop3UtilsSupervisionHelper.class,
							"Do not support Other object but subClasses of SupervisionMain here now ");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T finishSupervision(Class<T> clazz, String myType,
			Object... args) {
		if (Strings.nullToEmpty(myType).equals("")) {
			return null;
		}
		if (clazz == SupervisionMain.class) {
			Supervisable<SupervisionMain> supervisionMainGenerator = new SupervisionMainGenerator(
					myType, args);
			return (T) supervisionMainGenerator.finish();
		}
		// 在这里书写其它条件
		else {
			BocoLog
					.info(Nop3UtilsSupervisionHelper.class,
							"Do not support Other object but subClasses of SupervisionMain here now ");
			return null;
		}
	}
}
*/