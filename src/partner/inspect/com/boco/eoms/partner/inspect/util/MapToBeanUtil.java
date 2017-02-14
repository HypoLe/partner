package com.boco.eoms.partner.inspect.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.LogFactory;

/**
 * 
 * @Description:[工具类]
 * @Package:[com.boco.eoms.deviceManagement.busi.protectline.util.PromoAgreementStockUtil.java]
 * @ClassName:[PromoAgreementStockUtil]
 * @author:<a href="mailTo:lifeng2011@boco.com.cn">leefeng</a>
 * @CreateDate:[Jun 12, 2012 4:11:55 PM]
 * @Version:[v1.0]
 * @Copyright: Copyright(c)boco 2012
 */
public class MapToBeanUtil {
	// static String[] fieldType = new
	// String[]{"String","Long","Short","Integer","Boolean","Double","Float","Byte","int","float","short","long","double"};
	static String[] fieldType = new String[] { "String", "Long", "Short",
			"Integer", "Boolean", "Double", "Float", "Byte", "int", "float",
			"short", "long", "double", "char", "boolean","byte" };

	/**
	 * Description: 把Map注入到model中,Model 的参数需要为fieldType数组中的类型
	 * 
	 * @Title: setValueToObj
	 * @param
	 * @param cls
	 * @param
	 * @param map
	 * @param
	 * @return
	 * @return Object
	 * @throws
	 * @info model全部为string 测试通过
	 */
	public static Object setValueToObj(Class cls, Map<String, Object> map) {
		try {
			Object obj = cls.newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();
			Object value;
			String methodName[] = new String[2];
			Object objectValue;
			for (int i = 0; i < fields.length; i++) {
				value = map.get(fields[i].getName());
				if (null != value && !"".equals(value)) {
					try {
						methodName = getMethodName(cls, fields[i].getName());
						value = getObjectValue(methodName[1], value)[0];
						System.out.println(value.getClass().getName());
						invokeMethod(obj, methodName, value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return obj;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static Map getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	/**
	 * Description: 如果args为空,则取出OBJ的值,如果不为空则给OBJ设置值
	 * 
	 * @Title: invokeMethod
	 * @param
	 * @param owner
	 * @param
	 * @param methodName
	 * @param
	 * @param args
	 * @param
	 * @return
	 * @param
	 * @throws Exception
	 * @return Object
	 * @throws
	 */
	public static Object invokeMethod(Object owner, String methodName[],
			Object... args) throws Exception {
		if (null != args) {
			Method method = getMyMethod(owner.getClass(), methodName, args);
			if (null == method) {
				// throws new Exception();
				return null;
			}
			// //////////////方法的参数
			Type[] paramTypeList = method.getGenericParameterTypes();// 方法的参数列表
			for (Type paramType : paramTypeList) {
				if (null != paramType) {
					return method.invoke(owner, args);
				}
				if (paramType instanceof ParameterizedType)/**//* 如果是泛型类型 */{
					Type[] types = ((ParameterizedType) paramType)
							.getActualTypeArguments();// 泛型类型列表
					// System.out.println(" TypeArgument: ");
					for (Type type : types) {
						System.out.println("   " + type);
					}
				}
			}
			return null;
			// return method.invoke(owner, args);
		} else {
			Method method = getMyMethod(owner.getClass(), methodName, null);
			if (null == method)
				return null;
			return method.invoke(owner);
		}
	}

	private static Method getMyMethod(Class cls, String methodName[],
			Object[] args) {

		Method method = null;
		if (null != args) {
			Class[] argsClass = new Class[args.length];
			for (int i = 0, j = args.length; i < j; i++) {
				argsClass[i] = args[i].getClass();
				try {
					boolean b = "int".equals(methodName[1])
							|| "float".equals(methodName[1])
							|| "short".equals(methodName[1])
							|| "byte".equals(methodName[1])
							|| "long".equals(methodName[1])
							|| "double".equals(methodName[1])
							|| "char".equals(methodName[1])
							|| "boolean".equals(methodName[1]);
					if (b) {
						Method[] methods = cls.getDeclaredMethods();
						for (int method_i = 0; method_i < methods.length; method_i++) {
							if (methods[method_i].getName().equals(
									methodName[0])) {
//								System.out.println(methods[method_i].getName());
								return methods[method_i];
							}
						}
						// method = cls.getMethod(methodName[0], intValue);
					} else {
						method = cls.getMethod(methodName[0], argsClass);
					}
				} catch (SecurityException e) {
					e.printStackTrace();
					return method;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					return method;
				}
			}
		}
		return method;
	}

	private static String[] getMethodName(Class cls, String objField) {
		Method[] methods = cls.getDeclaredMethods();
		String methodName;
		String lastIndexPropert;
		String[] returnValue = new String[2];
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				if (method.getName().substring(3).toLowerCase()
						.equals(objField.toLowerCase())) {
//					System.out.println("MethodName:   " + method.getName());// 方法名
//					System.out.println("FieldType:   "
//							+ getFieldType(cls, objField));// 方法名
					returnValue[0] = method.getName();
					returnValue[1] = getFieldType(cls, objField);
					return returnValue;
				}
			}
		}
		return null;
	}

	private static String getFieldType(Class cls, String objField) {
		String returnValue;

		boolean success = false;
		try {
			Field field = cls.getDeclaredField(objField);
			String temp = field.getType().getSimpleName();
			for (int i = 0; i < fieldType.length; i++) {
				if (temp.equals(fieldType[i])) {
					success = true;
					returnValue = temp;
					return returnValue;
				}
			}
			if (!success) {
				LogFactory.getLog(cls).error(cls.getClass().getName()+"  获取  "+objField+" 对象的属性类型出错");
				return null;
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Object[] getObjectValue(String valueType, Object value) {
		Object[] obj = new Object[1];
		boolean success = false;
		if (valueType.equals(fieldType[0])) {
			success = true;
			obj[0] = value.toString();
			return obj;
		}
		if (valueType.equals(fieldType[1]) || valueType.equals(fieldType[11])) {
			success = true;
			obj[0] = Long.parseLong(value.toString());
			return obj;
		}
		if (valueType.equals(fieldType[2]) || valueType.equals(fieldType[10])) {
			success = true;
			obj[0] = Short.parseShort(value.toString());
			return obj;
		}
		if (valueType.equals(fieldType[3]) || valueType.equals(fieldType[8])) {
			success = true;
			obj[0] = Integer.parseInt(value.toString());
			return obj;
		}
		if (valueType.equals(fieldType[4]) || valueType.equals(fieldType[14])) {
			success = true;
			obj[0] = Boolean.parseBoolean(value.toString());
			return obj;
		}
		if (valueType.equals(fieldType[5]) || valueType.equals(fieldType[12])) {
			success = true;
			obj[0] = Double.parseDouble(value.toString());
			return obj;
		}
		if (valueType.equals(fieldType[6]) || valueType.equals(fieldType[9])) {
			success = true;
			obj[0] = Float.parseFloat(value.toString());
			return obj;
		}
		if (valueType.equals(fieldType[7])||valueType.equals(fieldType[15])) {
			success = true;
			obj[0] = new Byte(value.toString());
			return obj;
		}
		if (valueType.equals(fieldType[13])) {
			success = true;
			char c = value.toString().charAt(0);
			obj[0] = c;
			return obj;
		}
		if (!success) {
			LogFactory.getLog(MapToBeanUtil.class).error(
					MapToBeanUtil.class.getName() + "  获取对象的返回值出错"
							+ " 期望返回类型为 " + valueType + "  值 " + value);
			return null;
		}
		return null;
	}
}
