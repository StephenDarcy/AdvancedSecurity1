package lab3;

/*
Author: Stephen Darcy
Date: 10/11/2021
Description: Write a Java program (or any other programming language you are happy to use) to perform a
letter frequency attack on any monoalphabetic substitution cipher without human intervention.
Your software should produce possible plaintexts in rough order of likelihood. It would be good
if your user interface allowed the user to specify “give me the top 5 possible plaintexts.”
*/

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //get user plaintext to be encrypted
        System.out.println("Letter frequency attack on any monoalphabetic substitution cipher");
        System.out.println("Enter ciphertext to decrypt: ");
        String ciphertext = scanner.nextLine();

        //get number of possible plaintexts to display
        System.out.println("Enter the number of possible plaintexts to display: ");
        int num = scanner.nextInt();

        //getting results
        letterFrequencyAttack(ciphertext, num);
    }

    /**
     * Function that uses the frequency of the letters in the english alphabet to decrypt a ciphertext
     * Frequency used - ETAOINSHRDLCUMWFGYPBVKJXQZ
     *
     * @param ciphertext the ciphertext entered by the user to be decrypted
     * @param num        the amount of possible plaintexts to be displayed
     */
    private static void letterFrequencyAttack(String ciphertext, int num) {
        //string for frequency of english letters from labsheet
        String englishFrequency = "ETAOINSHRDLCUMWFGYPBVKJXQZ";

        //array to hold all the possible plaintexts
        String[] plaintexts = new String[num];

        //make ciphertext uppercase and remove whitespace with regex
        ciphertext = ciphertext.replaceAll("\\s", "").toUpperCase();

        //getting the frequency of each letter in the ciphertext
        int ciphertextFrequency[] = new int[26];
        for (int i = 0; i < ciphertext.length(); i++) {
            ciphertextFrequency[ciphertext.charAt(i) - 'A']++;
        }

        //clone contents of ciphertextFrequency to new array
        int ciphertextFrequencyReverse[] = new int[26];
        ciphertextFrequencyReverse = ciphertextFrequency.clone();

        //reverse contents of new array
        int arrayLength = ciphertextFrequencyReverse.length;
        for (int i = 0; i < arrayLength / 2; i++) {
            int current = ciphertextFrequencyReverse[i];
            ciphertextFrequencyReverse[i] = ciphertextFrequencyReverse[arrayLength - i - 1];
            ciphertextFrequencyReverse[arrayLength - i - 1] = current;
        }

        //buffer array to see if current letter has been checked
        int buffer[] = new int[26];

        //start of decryption
        for (int i = 0; i < num; i++) {
            //finding the most common letter in ciphertext
            int mostCommon = 0;
            //iterating over array to see if letter is most common
            for (int j = 0; j < arrayLength; j++) {
                if ((ciphertextFrequencyReverse[j] == ciphertextFrequency[i]) && buffer[j] == 0) {
                    //setting buffer to any number but zero so won't be checked again
                    buffer[j] = 99;
                    mostCommon = j;
                    break;
                }
            }

            //getting the ascii for the most likely shift
            int shift = (englishFrequency.charAt(i) - 'A') - mostCommon;

            //current plaintext string
            StringBuilder plaintext = new StringBuilder();

            //getting the number of plaintexts the user requested one letter at a time
            for (int j = 0; j < ciphertext.length(); j++) {
                //shifting each letter using modulus
                int current = ciphertext.charAt(j) - 'A';
                current += shift;
                //making sure current is in range
                if (current < 0) {
                    current += 26;
                }
                if (current > 25) {
                    current -= 26;
                }

                //converting ascii to letter and adding to plaintext
                plaintext.append((char) ('A' + current));
            }
            plaintexts[i] = plaintext.toString();
        }
        //output the plaintexts
        for (int i = 0; i < num; i++) {
            System.out.println(plaintexts[i]);
        }

    }
}
