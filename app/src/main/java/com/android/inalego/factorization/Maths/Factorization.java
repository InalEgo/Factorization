package com.android.inalego.factorization.Maths;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

public class Factorization {
    private final static BigInteger ZERO = new BigInteger("0");
    private final static BigInteger ONE = new BigInteger("1");
    private final static BigInteger TWO = new BigInteger("2");
    private final static SecureRandom random = new SecureRandom();

    private static BigInteger rho(BigInteger n) {
        BigInteger divisor;
        BigInteger c = new BigInteger(n.bitLength(), random);
        BigInteger x = new BigInteger(n.bitLength(), random);
        BigInteger xx = x;

        if (n.mod(TWO).compareTo(ZERO) == 0) {
            return TWO;
        }

        do {
            x = x.multiply(x).mod(n).add(c).mod(n);
            xx = xx.multiply(xx).mod(n).add(c).mod(n);
            xx = xx.multiply(xx).mod(n).add(c).mod(n);
            divisor = x.subtract(xx).gcd(n);
        } while ((divisor.compareTo(ONE)) == 0);

        return divisor;
    }

    public static void factor(BigInteger n, Map<BigInteger, Integer> result) {
        if (n.compareTo(ZERO) == 0) {
            result.put(ZERO, 1);
            return;
        }
        if (n.compareTo(ONE) == 0) {
            result.put(ONE, 1);
            return;
        }
        if (Prime.isPrime(n)) {
            Integer power = result.get(n);
            result.put(n, power != null ? ++power : 1);
            return;
        }

        BigInteger divisor;
        if (n.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0) {
            divisor = BigInteger.valueOf(trialDivision(n.longValue()));
            Integer power = result.get(divisor);
            result.put(divisor, power != null ? ++power : 1);
        } else {
            divisor = rho(n);
            factor(divisor, result);
        }
        factor(n.divide(divisor), result);
    }

    private static long trialDivision(long n) {
        if (n % 2 == 0) {
            return 2;
        }
        for (long i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return i;
            }
        }
        return n;
    }
}
