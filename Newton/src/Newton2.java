import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Andrew White
 *
 */
public final class Newton2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        /**
         * replica of the sqrt function from Newton3.java, with the exception of
         * the parameter error
         */
        double r = x;
        while (Math.abs(r * r - x) / x > 0.01 * 0.01) {
            if (x == 0) {
                r = 0;
            } else {
                r = (r + (x / r)) / 2;
            }
        }
        return r;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        //initialize simple reader and writer
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter a number to calculate the square root of: ");
        double userGuess = in.nextDouble();
        while (userGuess >= 0) {
            /**
             * ask user for a number to estimate the square root of while the
             * number is non negative
             */
            double estimate = sqrt(userGuess);
            out.println();
            out.println("The estimated square root is " + estimate);
            out.print("Enter a number to calculate the square root of: ");
            userGuess = in.nextDouble();
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }
}
