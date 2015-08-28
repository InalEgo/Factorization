package com.android.inalego.factorization.Maths;

import java.math.BigInteger;

public class Prime {
    private static boolean isPrime(BigInteger n, int precision) {
        if (n.compareTo(new BigInteger("3825123056546413051")) >= 0) {
            return n.isProbablePrime(precision);
        }

        int intN = n.intValue();
        if (intN == 2 || intN == 3 || intN == 5 || intN == 7) {
            return true;
        }

        if (!n.testBit(0)) {
            return false;
        }

        int[] primesToTest = getPrimesToTest(n);
        if (n.equals(new BigInteger("3215031751"))) {
            return false;
        }
        BigInteger d = n.subtract(BigInteger.ONE);
        BigInteger s = BigInteger.ZERO;
        while (d.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            d = d.shiftRight(1);
            s = s.add(BigInteger.ONE);
        }
        for (int a : primesToTest) {
            if (tryComposite(a, d, n, s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrime(BigInteger n) {
        return isPrime(n, 100);
    }

    public static boolean isPrime(int n) {
        return isPrime(BigInteger.valueOf(n), 100);
    }

    public static boolean isPrime(long n) {
        return isPrime(BigInteger.valueOf(n), 100);
    }

    private static int[] getPrimesToTest(BigInteger n) {
        if (n.compareTo(new BigInteger("341550071728321")) >= 0) {
            return new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23};
        }
        if (n.compareTo(new BigInteger("3474749660383")) >= 0) {
            return new int[]{2, 3, 5, 7, 11, 13, 17};
        }
        if (n.compareTo(new BigInteger("2152302898747")) >= 0) {
            return new int[]{2, 3, 5, 7, 11, 13};
        }
        if (n.compareTo(new BigInteger("118670087467")) >= 0) {
            return new int[]{2, 3, 5, 7, 11};
        }
        if (n.compareTo(new BigInteger("25326001")) >= 0) {
            return new int[]{2, 3, 5, 7};
        }
        if (n.compareTo(new BigInteger("1373653")) >= 0) {
            return new int[]{2, 3, 5};
        }
        return new int[]{2, 3};
    }

    private static boolean tryComposite(int a, BigInteger d, BigInteger n, BigInteger s) {
        BigInteger aB = BigInteger.valueOf(a);
        if (aB.modPow(d, n).equals(BigInteger.ONE)) {
            return false;
        }
        for (int i = 0; BigInteger.valueOf(i).compareTo(s) < 0; i++) {
            // if pow(a, 2**i * d, n) == n-1
            if (aB.modPow(BigInteger.valueOf(2).pow(i).multiply(d), n).equals(n.subtract(BigInteger.ONE))) {
                return false;
            }
        }
        return true;
    }
}