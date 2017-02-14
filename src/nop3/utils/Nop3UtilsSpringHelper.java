package utils;

import com.boco.eoms.base.util.ApplicationContextHolder;

public class Nop3UtilsSpringHelper {
	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> t, String... beanName) {
		if (beanName != null && beanName.length == 1) {
			return (T) ApplicationContextHolder.getInstance().getBean(
					(beanName[0]));
		} else {
			String source = t.getSimpleName();
			return (T) ApplicationContextHolder.getInstance().getBean(
					(source.substring(0, 1).toLowerCase().concat(source
							.substring(1))));
		}
	}
}
