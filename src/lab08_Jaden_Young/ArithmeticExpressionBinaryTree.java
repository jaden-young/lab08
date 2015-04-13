package lab08_Jaden_Young;

import java.util.Scanner;

/**
 *
 * @author Jaden Young
 */
public class ArithmeticExpressionBinaryTree {
    private LinkedBinaryTree<String> tree;

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
		//Whew. Converts the infix expression to postfix, then reverses it
		String postfix = new StringBuilder(
				InfixToPostfix.convert(infixExpression)).reverse().toString();
		//Now that it's reversed, we can read it straight forward
		Scanner scan = new Scanner(postfix);
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
	
    private Position<String> lowestOpenParent(Position<String> currentNode) {
		if(isOperator(currentNode.getElement()) 
				&& (tree.left(currentNode) == null
				|| tree.right(currentNode) == null)) {
			return currentNode;
		}
		return lowestOpenParent(tree.parent(currentNode));
	}
	
	private boolean isOperator(String input) {
		return input.equals("+") 
				|| input.equals("-") 
				|| input.equals("*")
				|| input.equals("/");
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
		Stack<Character> buffer = new LinkedStack<>();
		for (char c : expression.toCharArray()) {
			if (c == '(')
				buffer.push(c);
			else if (c == ')') {
				if(buffer.isEmpty())
					return false;
				buffer.pop();
			}
		}
		return buffer.isEmpty();
	}
}
