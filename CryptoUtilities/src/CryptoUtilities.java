import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Andrew White
 *
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3.
     */
    private static final int THREE = 3;

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (base * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        //if m isn't zero, reduce m to GCD using a temp natural number of
        //n divided by m, so not to change the value of n too early

        if (!m.isZero()) {
            NaturalNumber temp = new NaturalNumber2(n.divide(m));
            reduceToGCD(m, temp);
            //update n with the value of m
            n.transferFrom(m);
        }
        m.clear();

    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {

        //isEven test without using integers

        boolean result = false;

        NaturalNumber temp = new NaturalNumber2(n);
        NaturalNumber two = new NaturalNumber2(2);
        NaturalNumber div2 = new NaturalNumber2(temp.divide(two));
        NaturalNumber potentialN = new NaturalNumber2();
        //since dividing an odd naturalnumber by 2 does some type of rounding,
        //if n is odd, adding n.divide(2) together twice will result in a value
        // not equal to n, if n is even, n/2 added together twice will result in n
        potentialN.add(div2);
        potentialN.add(div2);
        if (potentialN.equals(n)) {
            result = true;
        }
        return result;

    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p,
            NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";

        /*
         * Use the fast-powering algorithm as previously discussed in class,
         * with the additional feature that every multiplication is followed
         * immediately by "reducing the result modulo m"
         */
        /**
         *
         * NaturalNumber one = new NaturalNumber2(1); NaturalNumber two = new
         * NaturalNumber2(2); NaturalNumber temp = new NaturalNumber2(); //if
         * the power is zero, just divide the number by the modulus
         * if(p.isZero()) { n = n.divide(m); } //if p is a positive
         * exponent,start by dividing the power by two in //order to work
         * recursively, then check for even or odd power else if
         * (p.compareTo(one) > 0) { p.divide(two); //if the power is odd, if
         * (!isEven(p)) { n.multiply(n); n.divide(m); powerMod(n, p, m);
         * n.multiply(n); n.divide(m); } else { n.multiply(n); n.divide(m);
         * powerMod(n, p, m); n.divide(m); } } //if the power is zero else if
         * (p.compareTo(one) == 0) { n = new NaturalNumber2(one); n =
         * n.divide(m);
         *
         * }
         **/
        //the previous code was my attempt at using the fast powering method, but
        //one of my tests kept failing so i did a shorter method

        //initializing a new NaturalNumber with value 1 and a counter int
        NaturalNumber one = new NaturalNumber2(1);
        int count = 0;
        //while the counter is less than the value of the NaturalNumber of power, p,
        //the naturalnumber with starting value of 1 will be multiplied by n and divided
        // by m for as large as p is.
        while (count < p.toInt()) {
            one.multiply(n);
            one = one.divide(m);
            count++;
        }
        //transfer the value of the temp naturalnumber back to the original number
        n.copyFrom(one);
    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w,
            NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();

        boolean result = false;
        int nInt = n.toInt();
        int wInt = n.toInt();
        int nInt2 = n.toInt();
        int wInt2 = n.toInt();

        if ((wInt ^ 2 % nInt) == 1 || (wInt ^ (nInt - 1) % nInt) != 1) {
            result = true;
        }

        return result;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * odd n >= 5: simply check whether 2 is a witness that n is
             * composite (which works surprisingly well :-)
             */
            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate several witness candidates --
         * say, 10 to 50 candidates -- guessing that n is prime only if none of
         * these candidates is a witness to n being composite (based on fact #3
         * as described in the project description); use the code for isPrime1
         * as a guide for how to do this, and pay attention to the requires
         * clause of isWitnessToCompositeness
         */

        boolean result = false;
        //creating basic and useful NN's for one, two, and a copy of n
        NaturalNumber two = new NaturalNumber2(2);
        NaturalNumber comp = new NaturalNumber2();
        NaturalNumber nCopy = new NaturalNumber2(n);

        //generates 35 witness candidates to check is number is prime
        for (int i = 0; i < 35; i++) {
            //if the copy of n is greater or equal to 2, a random number will be generated
            if (nCopy.compareTo(two) >= 0) {
                comp = new NaturalNumber2(randomNumber(nCopy));
                //if n is 0,1, or 2, comp is n
            } else {
                comp = new NaturalNumber2(n);
            }
            //if n is even, then n is not prime
            if (isEven(n)) {
                result = false;
                //is n is less than the comp value, then n is prime
            } else if (n.compareTo(comp) <= 0) {
                result = true;
                //if n is odd and greater than comp, then isWitnessToCompositeness is
                //called and the opposite of its return value is set to result
            } else {
                result = !isWitnessToCompositeness(new NaturalNumber2(2), n);
            }
        }
        return result;

    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 1
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use isPrime2 to check numbers, starting at n and increasing through
         * the odd numbers only (why?), until n is likely prime
         */
        //evens can't be prime, besides 2. eliminating evens saves time.

        boolean result = false;

        //while the number is not prime
        while (!result) {
            //get rid of all even numbers by adding 1
            if (isEven(n)) {
                n.increment();
            }
            //check if number is prime

            result = isPrime2(n);
            n.increment();

        }
        //for some reason even though i increment while the number isnt prime, the
        //n isnt incremented.

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println("  expected value = "
                + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
