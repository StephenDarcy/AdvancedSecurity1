/*
Author: Stephen Darcy C18490924
Date: 20/11/2021
Description: Write a Java program (or any other programming language you are happy to use) which will test
if the given number is a prime number or not
*/

import java.math.BigInteger;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Greet user and get number to be tested
        System.out.println("Miller-Rabin Primality Test");
        System.out.println("Please enter a number to test whether it is prime or not: ");
        long n = scanner.nextLong();

        // Start of test
        // Step 1: Find integers k,q,k>0,q odd, so that (n–1) = 2^kq
        long[] ans = stepOne(n);
        long q = ans[0];
        long k = ans[1];

        // Step 2: Select a random integer a, 1 < a < n – 1
        long a = 1 + (long) (Math.random() * (n - 1));
        a = 105532;

        // Step 3: if a^q mod n = 1 then return (“inconclusive");
        // Getting the result of a^q mod n
        BigInteger a2jq = stepThree(a, q, n);
        BigInteger var = new BigInteger("1");
        // checking if a^q mod n = 1
        if (a2jq.equals(var)) {
            System.out.println("Inconclusive");
            return;
        }


        // Step 4: for j = 0 to k – 1 do
        for (long j = 0; j < (k - 1); j++) {
            // Step 5: if (a^2^jq mod n = n-1) then return(“inconclusive")
            // using ajq from earlier and passing n
            BigInteger result = stepFive(a2jq, n);

            // checking if a^2^jq mod n = n-1
            if (result.longValue() == n - 1) {
                System.out.println("Inconclusive");
                return;
            }
        }

        // Step 6: return("Composite")
        System.out.println("Composite");
    }


    /**
     * Method that uses the modPow function for BigIntegers to preform the equation: a^2^jq mod n
     *
     * @param a2jq calculated in step 3
     * @param n    modulo
     * @return result of above equation
     */
    private static BigInteger stepFive(BigInteger a2jq, long n) {
        // Converting n to BigIntegers to use modPow()
        BigInteger bigN = BigInteger.valueOf(n);

        return a2jq.modPow(BigInteger.valueOf(2), bigN);
    }

    private static BigInteger stepThree(long a, long q, long n) {
        // Converting values to BigIntegers to use modPow()
        BigInteger bigA = BigInteger.valueOf(a);
        BigInteger bigQ = BigInteger.valueOf(q);
        BigInteger bigN = BigInteger.valueOf(n);

        // getting result of a^q mod n
        return bigA.modPow(bigQ, bigN);
    }

    private static long[] stepOne(long n) {
        long k = 1, q = 0;
        long[] returnValues = new long[2];

        while (q % 2 == 0) {
            k += 1;
            q = (long) ((n - 1) / Math.pow(2, k));
        }
        returnValues[0] = q;
        returnValues[1] = k;

        return returnValues;
    }

}
