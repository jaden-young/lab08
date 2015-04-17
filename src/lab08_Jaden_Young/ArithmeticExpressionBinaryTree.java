package lab08_Jaden_Young;

import java.util.Iterator;
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
	 * @param infixExpression Infix expression that will be converted to a 
	 * binary tree
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
	
	/**
	 * Returns a double value representing the result of the expression
	 * @return result of the expression
	 */
	public double evaluate() {
		return evaluateHelper(tree.root());
	}
	
	//this essentially comes from the book, minus the switch
	private double evaluateHelper(Position<String> position) {
		if (tree.isExternal(position))
			return Double.parseDouble(position.getElement());
		else {
			double left = evaluateHelper(tree.left(position));
			double right = evaluateHelper(tree.right(position));
			String operator = position.getElement();
			switch (operator) {
				case "+":
					return left + right;
				case "-":
					return left - right;
				case "*":
				case "x":
					return left * right;
				case "/":
					return left / right;
				default:
					throw new IllegalArgumentException("Invalid operator.");
			}
		}
	}
	    
	/**
	 * Prints the pre-order traversal of the tree to the console
	 */
	public void preorder() {
		preorderHelper(tree.root(), 0);
	} 
	
	//I don't want the client to have to call preorder(tree.root(), 0);
	private void preorderHelper(Position<String> position, int depth) {
		System.out.println(spacesForPreorder(position, depth * 2) 
				+ position.getElement());
		for (Position<String> current : tree.children(position)) {
			preorderHelper(current, depth + 2);
		}
	}
	
	//determines how far to indent each line
	private String spacesForPreorder(Position<String> position, int numSpaces) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < numSpaces; i++) {
			sb.append(i % 4 == 0 ? "│" : ' ');
		}
		sb.append(tree.isInternal(position) ? "├── " : "└── ");
		return sb.toString();
	}
	
	/**
	 * Prints an indented postorder traversal of the tree to the console
	 */
	public void postorder() {
		Iterable<Position<String>> postIterable = tree.postorder();
		Iterator<Position<String>> walk = postIterable.iterator();
		while(walk.hasNext()) {
			Position<String> current = walk.next();
			System.out.println(spacesForPostorder(current) 
					+ current.getElement());
		}
	}
	
	private String spacesForPostorder(Position<String> position) {
		StringBuilder sb = new StringBuilder();
		int height = tree.height(tree.root());
		int depth = tree.depth(position);
		for(int i = 0; i < height - depth; i++) {
			sb.append("    ");
		}
		return sb.toString();
	}
	
	public void inorder() {
		Iterable<Position<String>> inIterable = tree.inorder();
		Iterator<Position<String>> walk = inIterable.iterator();
		while(walk.hasNext()) {
			Position<String> current = walk.next();
			System.out.println(spacesForInorder(current) 
					+ current.getElement());
		}
	}
	
	private String spacesForInorder(Position<String> position) {
		StringBuilder sb = new StringBuilder();
		int height = tree.height(tree.root());
		int depth = tree.depth(position);
		for(int i = 0; i < height - depth; i++) {
			sb.append("    ");
		}
		return sb.toString();
	}

	//utility that returns the closest available parent parent position node
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
