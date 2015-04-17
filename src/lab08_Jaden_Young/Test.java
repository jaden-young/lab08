/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab08_Jaden_Young;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author jaden
 */
public class Test {
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
					System.out.println(exp);
					try { 
						ArithmeticExpressionBinaryTree tree = 
							new ArithmeticExpressionBinaryTree
							(exp);
						System.out.println("----- Expression " + numExpressions
								+ " = " + tree.evaluate() + " -----");
						numExpressions++;
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
