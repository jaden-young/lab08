package lab08_Jaden_Young;

import java.util.Scanner;

/**
 *
 * @author Jaden Young
 */
public class ArithmeticExpressionBinaryTree {
    private LinkedBinaryTree<String> tree;
	private String postfix;
	
	/**
	 * Creates a binary tree representing the arithmetic expression.
	 * @param infixExpression Infix expression that will be converted to a binary 
	 *	tree
	 * @throws IllegalArgumentException If the parenthesis () are unbalanced
	 */
    public ArithmeticExpressionBinaryTree(String infixExpression) 
			throws IllegalArgumentException {
		if(!isBalanced(infixExpression))
			throw new IllegalArgumentException("The expression does not have "
					+ "balanced parenthesis.");
		
		tree = new LinkedBinaryTree<>();
		postfix = InfixToPostfix.convert(infixExpression);
		String reversed = new StringBuilder(postfix).reverse().toString();
		//Now that it's reversed, we can read it straight forward
		Scanner scan = new Scanner(reversed);
		//make the first operation the root node and current node
		Position<String> current = tree.addRoot(scan.next());
		while(scan.hasNext()) {
			current = lowestOpenParent(current);
			String currentToken = scan.next();
			try {
				current = tree.addRight(current, currentToken);
			} catch(IllegalArgumentException e) {
				current = tree.addLeft(current, currentToken);
			}
		}
    }
	
	public double evaluate() {
		LinkedStack<Double> numStack = new LinkedStack<>();
		Scanner scan = new Scanner(postfix);
		while (scan.hasNext()) {
			String current = scan.next();
			try {
				numStack.push(Double.parseDouble(current));
			} catch (NumberFormatException nfe) {
				double n2 = numStack.pop();
				double n1 = numStack.pop();
				switch(current) {
					case "+":
						numStack.push(n1 + n2);
						break;
					case "-":
						numStack.push(n1 - n2);
						break;
					case "*":
					case "x":
						numStack.push(n1 * n2);
						break;
					case "/":
						numStack.push(n1 / n2);
						break;
				}
			}
		}
		return numStack.pop();
	}
	
    private Position<String> lowestOpenParent(Position<String> currentNode) {
		if(isOperator(currentNode.getElement()) 
				&& (tree.left(currentNode) == null)) {
			return currentNode;
		}
		return lowestOpenParent(tree.parent(currentNode));
	}
	
	private boolean isOperator(String input) {
		return input.equals("+") 
				|| input.equals("-") 
				|| input.equals("*")
				|| input.equals("/")
				|| input.equals("x");
	}
	
	/**
	 * Tests if the given expression has balanced parenthesis.
	 * Tests for ()
	 * Examples:
	 *		Balanced:
	 *			(4 + 2) / 1
	 *			2 * (1 + (3 / 2))
	 *		Unbalanced:
	 *			3 + 1)
	 *			(5 * (3 - 1)
	 * @param expression Expression to check
	 * @return True if parenthesis are balanced, false if not
	 */
	private boolean isBalanced(String expression) {
		LinkedStack<Character> buffer = new LinkedStack<>();
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
			if (c == '(')
				buffer.push(c);
			else if (c == ')') {
				if(buffer.isEmpty()) {
					return false;
				}
				buffer.pop();
			}
		}
		return buffer.isEmpty();
	}
}
