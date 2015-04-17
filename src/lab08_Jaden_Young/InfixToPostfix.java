package lab08_Jaden_Young;

import java.util.Scanner;

/**
 * Contains 1 static method that converts an infix expression into postfix
 * notation.
 * Only recognizes +, -, *, /, x, and ()
 * @author Jaden Young
 */
public class InfixToPostfix {
	/**
	 * Converts an infix expression into postfix. Input expression must be 
	 * delimited by whitespace. Output will be a string also delimited by 
	 * whitespace. 
	 * @param infixExpression Expression to convert
	 * @return String containing converted expression in postfix notation
	 */
	public static String convert(String infixExpression) {
		Scanner scan = new Scanner(infixExpression);
		String postfix = "";
		LinkedStack<String> opStack = new LinkedStack<>();
		
		while(scan.hasNext()) {
			String token = scan.next();
			switch(token) {
				case "(":
					opStack.push(token);
					break;
				case ")":
					while(!opStack.top().equals("("))
						postfix += opStack.pop() + " ";
					opStack.pop();
					break;
				default:
					if(isOperator(token)) 
						opStack.push(token);
					else
						postfix += token + " ";
			}
		}
		while(!opStack.isEmpty())
			postfix += opStack.pop() + " ";
		return postfix;
	}
	
	private static boolean isOperator(String input) {
		return input.equals("+") 
				|| input.equals("-") 
				|| input.equals("*")
				|| input.equals("/")
				|| input.equals("x");
	}
}
