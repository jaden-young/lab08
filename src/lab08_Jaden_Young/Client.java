package lab08_Jaden_Young;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Jaden Young
 */
public class Client {
	public static void main(String[] args) {
		//So. Many. Indents.
		Scanner scan = new Scanner(System.in);
		Scanner scanFile;
		boolean validFile = false;
		do {
			try {
				System.out.print("Enter a file name (ex: data.txt) > ");
				scanFile = new Scanner(new File(scan.nextLine()));
				validFile = true;
				int numExpressions = 0;
				while(scanFile.hasNextLine()) {
					String exp = scanFile.nextLine();
					System.out.println("Infix: " + exp);
					try { 
						ArithmeticExpressionBinaryTree tree = 
							new ArithmeticExpressionBinaryTree
							(exp);
						System.out.println("Postfix: " 
								+ tree.postfixExpression());
						System.out.println("----- Expression " + 
								numExpressions++ + " = " + tree.evaluate() 
								+ " -----");
						System.out.println("Preorder");
						tree.preorder();
						System.out.println("Postorder");
						tree.postorder();
						System.out.println("Inorder");
						tree.inorder();
					} catch (IllegalArgumentException iae) {
						System.out.println(iae.getMessage());
					}
				}
			} catch (FileNotFoundException fnfe) {
				System.out.println("Sorry, can't fild that file.");
			}
		} while(!validFile);
	}
}
