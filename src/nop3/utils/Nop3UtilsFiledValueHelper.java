package utils;

import static utils.Nop3Constants.*;

import com.google.common.base.Preconditions;

public class Nop3UtilsFiledValueHelper {

	public static String dealWithField(String fieldValue, String fieldType) {

		Preconditions.checkNotNull(fieldValue);
		Preconditions.checkNotNull(fieldType);

		if (fieldType.equals(OPERATETYPE_LINK)) {
			return getOperateType().get(fieldValue);
		}

		return fieldValue;
	}
}
