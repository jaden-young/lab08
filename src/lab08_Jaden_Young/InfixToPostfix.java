package lab08_Jaden_Young;

import java.util.Scanner;

/**
 * Contains 1 static method that converts an infix expression into postfix
 * notation.
 * Only recognizes +, -, *, /, and ()
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
		
		//This. Is. Ugly. Please don't look at it. It works ok?
		while(scan.hasNext()) {
			String token = scan.next();
			switch(token) {
				//always push an opening parenthesis
				case "(":
					opStack.push(token);
					break;
				//move operators from stack to string until the matching 
				//parenthesis, then get rid of the opening parenthesis
				case ")":
					while(!opStack.top().equals("(")) {
						postfix += opStack.pop() + " ";
					}
					opStack.pop();
					break;
				//with ops of same priority, move the current op to the string,
				//then push new op once one of higher priority is reached
				case "+":
				case "-":
					while(!opStack.isEmpty() 
							&& (opStack.top().equals("+") 
								|| opStack.top().equals("-"))) {
						postfix += opStack.pop() + " ";
					}
					opStack.push(token);
					break;
				//with ops of same priority, move the current op to the string,
				//then push new op once one of higher priority is reached
				case "*":
				case "/":
				case "x":
					//putting this many conditions in makes me sick
					while(!opStack.isEmpty() 
							&& (opStack.top().equals("*") 
								|| opStack.top().equals("/")
								|| opStack.top().equals("x"))) {
						postfix += opStack.pop() + " ";
					}
					opStack.push(token);
					break;
				//any number (or anything, since we're not checking for invalid
				//tokens)
				default:
					postfix += token + " ";
			}
		}
		//move remaining operators in stack to string
		while(!opStack.isEmpty()) {
			postfix += opStack.pop() + " ";
		}
		//It's over. It's finally over.
		return postfix;
	}
}
