package lab08_Jaden_Young;

import java.util.Scanner;

/**
 *
 * @author jaden.young
 */
public class ArithmeticExpressionBinaryTree {
    private LinkedBinaryTree<String> tree;

	/**
	 * Creates a binary tree representing the arithmetic expression.
	 * @param expression Infix expression that will be converted to a binary 
	 *	tree
	 * @throws IllegalArgumentException If the parenthesis () are unbalanced
	 */
    public ArithmeticExpressionBinaryTree(String expression) 
			throws IllegalArgumentException {
		if(!isBalanced(expression))
			throw new IllegalArgumentException("The expression does not have "
					+ "balanced parenthesis.");
		
		tree = new LinkedBinaryTree<>();
		
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
		final String opening = "(";
		final String closing = ")";
		Stack<Character> buffer = new LinkedStack<>();
		for (char c : expression.toCharArray()) {
			if (opening.indexOf(c) != -1)
				buffer.push(c);
			else if (closing.indexOf(c) != -1) {
				if(buffer.isEmpty())
					return false;
				if (closing.indexOf(c) != opening.indexOf(buffer.pop()));
					return false;
			}
		}
		return buffer.isEmpty();
	}
    
	
	
}
