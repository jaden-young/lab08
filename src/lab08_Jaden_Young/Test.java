/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab08_Jaden_Young;

import java.util.Scanner;

/**
 *
 * @author jaden
 */
public class Test {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("> ");
		System.out.println(InfixToPostfix.convert(scan.nextLine()));
	}
}
