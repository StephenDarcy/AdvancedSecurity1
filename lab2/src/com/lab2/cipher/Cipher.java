package com.lab2.cipher;

/*
Author: Stephen Darcy
Date: 04/10/2021
Description: Write a program that will implement Caesar Cipher and Vigenère Cipher. You can use
Java or any other programming language.
*/

import java.util.Scanner;

/**
 * Console program that allows the user to encrypt and decrypt two ciphers; Caesar cipher and Vigenère cipher
 */

public class Cipher {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int menuOption = 0;

        //wrap menu in while statement so it loops
        while (menuOption != 5) {
            //getting menu option from user
            menuOption = menu();
            //switch based on users input
            switch (menuOption) {
                case 1 -> encryptCaesar();
                case 2 -> encryptVigenere();
                case 3 -> decryptCaesar();
                case 4 -> decryptVigenere();
                case 5 -> System.out.println("\tExiting...");
                default -> System.out.println("\n\tError invalid input, returning to menu ...");
            }
        }


    }

    private static void decryptVigenere() {
    }

    private static void decryptCaesar() {
    }

    private static void encryptVigenere() {
    }

    /**
     * Asks the user to input their desired shift. User is then prompted to enter plaintext
     * which in turn returns ciphertext
     */
    private static void encryptCaesar() {
        System.out.println("\t----- Caesar Cipher Encryption Selected -----");
        //getting the users desired shift
        System.out.print("\tPlease enter the number you wish to shift your plaintext by (Caesar cipher is traditionally 3):\n\t");
        int shift = getInt();
        scanner.nextLine();

        //getting the users plaintext
        System.out.print("\tPlease enter the plaintext to be encrypted:\n\t");
        String plaintext = scanner.nextLine();
        System.out.println("\tYou entered: " + plaintext);

        //putting plaintext into char array & adding ciphertext stringbuilder
        char[] plaintextArray = plaintext.toCharArray();
        StringBuilder ciphertext = new StringBuilder();

        //encrypting the plaintext
        for (int i = 0; i < plaintext.length(); i++) {
            //making sure plaintextArray[i] is a char; if not setting as a space ' '
            if (Character.isLetter(plaintextArray[i])) {
                //using ASCII values to find new shift position in ASCII
                int offset = ((plaintextArray[i] - 'a') + shift) % 26;

                //converting ASCII value and adding new values to ciphertext
                ciphertext.append((char) (offset + 'a'));
            } else {
                ciphertext.append(" ");
            }
        }

        //printing the ciphertext to the user
        System.out.println("\tCiphertext is: " + ciphertext);

    }

    /**
     * Print a menu and return the users input
     *
     * @return the menu option the user selected
     */
    public static int menu() {
        System.out.println("\n\tLab 2 program Caesar cipher and Vigenère cipher\n");
        System.out.println("\tPlease choose an option below: \n   -----------------------------------------------");
        System.out.println("\t1. Encrypt with Caesar cipher");
        System.out.println("\t2. Encrypt with Vigenère cipher");
        System.out.println("\t3. Decrypt a Caesar cipher");
        System.out.println("\t4. Decrypt a Vigenère cipher");
        System.out.println("\t5. Quit\n");
        System.out.print("\t");

        return getInt();
    }

    /**
     * prompts the user to enter an int, stopping the program from crashing when another datatype
     * is entered.
     *
     * @return an int the user inputted
     */
    public static int getInt() {
        //waiting for user to enter an integer
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("\tPlease enter an integer value\n\t");
        }

        return scanner.nextInt();
    }
}

