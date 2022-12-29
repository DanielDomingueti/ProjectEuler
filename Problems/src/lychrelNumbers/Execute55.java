package lychrelNumbers;

import java.math.BigInteger;

public class Execute55 {
    public static void main(String[] args) {

        int counter = 0;
        for (int i=0 ; i < 10000 ; i++) {
            if (isLychrel(i)) counter++;
        }

        System.out.println("There're " + counter + " Lychrel numbers within 10000");
    }

    private static boolean isLychrel(int n) {
        BigInteger temp = BigInteger.valueOf(n);
        for (int i = 0; i < 49; i++) {
            temp = temp.add(new BigInteger(reverse(temp.toString())));
            if (isPalindrome(temp.toString())) {
                return false;
            }
        }
        return true;
    }

    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static boolean isPalindrome(String s) {
        return s.equals(reverse(s));
    }
}
