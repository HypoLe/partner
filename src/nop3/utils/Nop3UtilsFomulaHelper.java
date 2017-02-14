package utils;

import java.util.*;

/**
 * @author DaiZhiGang
 * @author Steve
 * @since 2011,10
 * 
 * 公式计算通用类，仅支持四则表达式
 */
@SuppressWarnings("unchecked")
class Nop3UtilsFomulaHelper {
	private ArrayList expression = new ArrayList();// 存储中序表达式

	private ArrayList right = new ArrayList();// 存储右序表达式

	private String result;// 结果

	private Map<String, String> argumentValueMap;// 储存变量名和变量值的Map

	// 依据输入信息创建对象，将数值与操作符放入ArrayList中
	@SuppressWarnings("unchecked")
	public Nop3UtilsFomulaHelper(String input, Map<String, String> argumentsMap) {
		StringTokenizer st = new StringTokenizer(input, "+-*/()", true);
		while (st.hasMoreElements()) {
			String s = st.nextToken();
			expression.add(s);
		}
		argumentValueMap = argumentsMap;
	}

	// 将中序表达式转换为右序表达式

	private void toRight() {
		Stacks aStack = new Stacks();
		String operator;
		int position = 0;
		while (true) {
			if (Calculate.isOperator((String) expression.get(position))) {
				if (aStack.top == -1
						|| ((String) expression.get(position)).equals("(")) {
					aStack.push(expression.get(position));
				} else {
					if (((String) expression.get(position)).equals(")")) {
						while (true) {

							if (aStack.top != -1
									&& !((String) aStack.top()).equals("(")) {
								operator = (String) aStack.pop();
								right.add(operator);
							} else {
								if (aStack.top != -1)
									aStack.pop();
								break;
							}
						}
					} else {
						while (true) {
							if (aStack.top != -1
									&& Calculate.priority((String) expression
											.get(position)) <= Calculate
											.priority((String) aStack.top())) {
								operator = (String) aStack.pop();
								if (!operator.equals("("))
									right.add(operator);
							} else {
								break;
							}

						}
						aStack.push(expression.get(position));
					}
				}
			} else
				right.add(expression.get(position));
			position++;
			if (position >= expression.size())
				break;
		}
		while (aStack.top != -1) {
			operator = (String) aStack.pop();
			if (!operator.equals("("))
				right.add(operator);
		}
	}

	// 对右序表达式进行求值
	public String getResult() throws IllegalArgumentException {
		this.toRight();
		Stacks aStack = new Stacks();
		String op1, op2, is = null;
		Iterator it = right.iterator();

		// int myIndex = 0;
		// int myMaxLength = myArgument.length;

		while (it.hasNext()) {
			is = (String) it.next();
			if (Calculate.isOperator(is)) {
				op1 = (String) aStack.pop();
				op2 = (String) aStack.pop();
				aStack.push(Calculate.twoResult(is, op1, op2));
			} else if (argumentValueMap.containsKey(is)) {
				// The exception will be thrown here.
				aStack.push(argumentValueMap.get(is));
			} else
				aStack.push(is);
		}
		result = (String) aStack.pop();
		it = expression.iterator();
		return String.format("%1$.2f", Double.parseDouble(result));
	}

	public static void main(String avg[]) {
		System.out.println(Nop3Utils.isNum("12.123"));
		System.out.println(Nop3Utils.isNum("0.123"));
		System.out.println(Nop3Utils.isNum("56"));
		System.out.println(Nop3Utils.isNum("ss"));
	}

}

class Stacks {
	private LinkedList list = new LinkedList();

	int top = -1;

	@SuppressWarnings("unchecked")
	public void push(Object value) {
		top++;
		list.addFirst(value);
	}

	public Object pop() {
		Object temp = list.getFirst();
		top--;
		list.removeFirst();
		return temp;

	}

	public Object top() {
		return list.getFirst();
	}
}

class Calculate {
	// 判断是否为操作符号
	public static boolean isOperator(String operator) {
		if (operator.equals("+") || operator.equals("-")
				|| operator.equals("*") || operator.equals("/")
				|| operator.equals("(") || operator.equals(")"))
			return true;
		else
			return false;
	}

	// 设置操作符号的优先级别
	public static int priority(String operator) {
		if (operator.equals("+") || operator.equals("-"))
			return 1;
		else if (operator.equals("*") || operator.equals("/"))
			return 2;
		else
			return 0;
	}

	// 做2值之间的计算
	public static String twoResult(String operator, String a, String b) {
		try {
			String op = operator;
			String rs = new String();
			double x = Double.parseDouble(b);
			double y = Double.parseDouble(a);
			double z = 0;
			if (op.equals("+"))
				z = x + y;
			else if (op.equals("-"))
				z = x - y;
			else if (op.equals("*"))
				z = x * y;
			else if (op.equals("/"))
				z = x / y;
			else
				z = 0;
			return rs + z;
		} catch (NumberFormatException e) {
			return "Error";
		}
	}
}