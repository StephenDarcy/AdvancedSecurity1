import java.math.BigInteger;

/**
 * Write a Java program (or any other programming language you are happy to use) to perform the
 * Key Expansion of AES algorithm as shown below. You don’t need to implement the whole AES
 * algorithm.
 * Author: Stephen Darcy
 */

public class Main {

    // used string array as I found it difficult to use hex codes with java
    public static String[] substitutionBox =
            {
                    "63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76",
                    "CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0",
                    "B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15",
                    "04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75",
                    "09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84",
                    "53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF",
                    "D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8",
                    "51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2",
                    "CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73",
                    "60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB",
                    "E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79",
                    "E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08",
                    "BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A",
                    "70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E",
                    "E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF",
                    "8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"
            };

    // rcon values from sheet + 1 extra "00000000" as we loop 11 times not 10
    public static String[] roundConstant = {"00000000", "01000000", "02000000", "04000000", "08000000", "10000000", "20000000", "40000000", "80000000", "1B000000", "36000000"};

    // key from question
    public static String key = "0f1571c947d9e8590cb7add6af7f6798";

    public static void main(String[] args) {
        //getting array ready to hold expanded string
        String[] answer = new String[176];
        int i, j;
        String rotword, subword;

        // The number of 32-bit words comprising the cipher key in this AES cipher.
        int a = 4;

        // The number of rounds in this cipher
        int b = 10;

        // The number of 32-bit words comprising the plaintext and columns comprising the state matrix of an AES cipher.
        int c = 4;

        // for loop to copy key into first 4 words of expanded key
        for (i = 0; i < a; i++) {
            answer[i] = key.substring(8 * i, 8 * i + 2) + key.substring(8 * i + 2, (8 * i + 2) + 2) + key.substring(8 * i + 4, (8 * i + 4) + 2) + key.substring(8 * i + 6, (8 * i + 6) + 2);
            System.out.print("\nw" + i + " = " + formatString(answer[i]));
        }

        int loopAmount = c * (b + 1);
        // for loop that XORs the result of RotWord() and SubWord() with the round constant
        for (j = a; j < loopAmount; j++) {
            String current = answer[j - 1];

            if (j % a == 0) {
                BigInteger currentValue = new BigInteger(roundConstant[j / a], 16);
                rotword = RotWord(current);
                subword = SubWord(rotword);
                current = String.format("%0" + (4 << 1) + "X", new BigInteger(subword, 16).xor(currentValue));
            }
            answer[j] = String.format("%0" + (4 << 1) + "X", new BigInteger(answer[j - a], 16).xor(new BigInteger(current, 16)));
            System.out.print("\nw" + j + " = " + formatString(answer[j]));
        }
    }


    /**
     * This method applies the substitution from the substitution box to each byte
     * String must be first split into an array and then substrings of 4 byte words
     *
     * @param current 8 hex digits to be split
     * @return value after sbox substitution is applied and merged back to 8 hex digit string
     */
    public static String SubWord(String current) {
        String[] tempArray = new String[current.length() / 2];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = current.substring(2 * i, 2 * i + 2);
        }

        for (int i = 0; i < 4; i++) {
            tempArray[i] = substitutionBox[Integer.parseInt(tempArray[i], 16)];
        }

        StringBuilder returnString = new StringBuilder();
        for (String s : tempArray) {
            returnString.append(s);
        }

        return String.valueOf(returnString);
    }

    /**
     * This method applies the cyclic shift required and returns the result
     *
     * @param current 8 hex digits
     * @return 8 hex digits after being shifted
     */
    public static String RotWord(String current) {
        String[] tempArray = new String[current.length() / 2];
        for (int i = 0; i < tempArray.length; i++) {
            tempArray[i] = current.substring(2 * i, 2 * i + 2);
        }

        String temp = tempArray[0];
        System.arraycopy(tempArray, 1, tempArray, 0, 3);
        tempArray[3] = temp;

        StringBuilder returnString = new StringBuilder();
        for (String s : tempArray) {
            returnString.append(s);
        }

        return String.valueOf(returnString);
    }

    /**
     * This method adds spaces every two chars like in the example sheet
     *
     * @param current string to be split
     * @return split string
     */
    public static String formatString(String current) {
        int n = 2;
        StringBuilder returnString = new StringBuilder(current);
        int i = returnString.length() - n;
        while (i > 0) {
            returnString.insert(i, " ");
            i = i - n;
        }
        return returnString.toString();
    }
}