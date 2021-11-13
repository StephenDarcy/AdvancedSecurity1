package lab3;

/*
Author: Stephen Darcy
Date: 10/11/2021
Description: Write a Java program (or any other programming language you are happy to use) to encrypt
plaintext using a 2 * 2 Hill cipher.
*/

import java.util.Locale;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //get user plaintext to be encrypted
        System.out.println("Hill Cipher 2*2 Matrix Encryption");
        System.out.println("Enter plaintext to encrypt: ");
        String plaintext = scanner.nextLine();

        //get key to encrypt plaintext with
        System.out.println("Enter a 4 letter key:");
        String key = scanner.nextLine();

        //make all uppercase and remove whitespace with regex
        plaintext = plaintext.replaceAll("\\s", "").toUpperCase();
        key = key.replaceAll("\\s", "").toUpperCase();

        //making plaintext length an even number (adding z to end if odd)
        if (plaintext.length() % 2 != 0) {
            plaintext += "Z";
        }

        //creating 2 2D arrays to hold the plaintext matrix and key matrix
        int[][] plaintextMatrix = new int[2][plaintext.length()];
        int[][] keyMatrix = new int[2][2];

        //using a for loop convert the plaintext to numbers and insert into matrix
        //using 65 as this is the ascii code for 'A' and plaintext is all uppercase
        int row1Index = 0, row2Index = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            char current = plaintext.charAt(i);
            if (i % 2 == 0) {
                plaintextMatrix[0][row1Index] = ((int) current) - 65;
                row1Index++;
            } else {
                plaintextMatrix[1][row2Index] = ((int) current) - 65;
                row2Index++;
            }
        }

        //same as above but with the key into a 2*2 matrix
        int rowIndex = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                char current = key.charAt(rowIndex);
                keyMatrix[i][j] = ((int) current) - 65;
                rowIndex++;
            }
        }

        //finding the product of the key and plaintext pair mod 26 by doing matrix multiplication
        //on the 2D arrays
        StringBuilder ciphertext = new StringBuilder();
        int currentRow1, currentRow2;
        for (int i = 0; i < plaintext.length() / 2; i++) {
            currentRow1 = ((plaintextMatrix[0][i] * keyMatrix[0][0]) + (plaintextMatrix[1][i] * keyMatrix[0][1])) % 26;
            currentRow2 = ((plaintextMatrix[0][i] * keyMatrix[1][0]) + (plaintextMatrix[1][i] * keyMatrix[1][1])) % 26;

            //adding to stringbuilder
            ciphertext.append((char) (currentRow1 + 65));
            ciphertext.append((char) (currentRow2 + 65));
        }

        //printing the ciphertext
        System.out.println(ciphertext);
    }
}

