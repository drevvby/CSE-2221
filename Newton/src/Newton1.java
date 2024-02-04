import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Estimates a square root of a user defined number
 *
 * @author Andrew White
 *
 */
public final class Newton1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton1() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double r = x;
        while (Math.abs(r * r - x) / x > 0.01 * 0.01) {
            /**
             * while the guess is too far from the correct value, take the
             * average of the guess and x divided by the guess
             */
            r = (r + (x / r)) / 2;
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

        //initial response is y to initially ask for a number
        String response = "y";

        while (response.equals("y")) {
            //only prompt user when they enter y for response
            out.print("Enter a number to calculate the square root of: ");
            //set userGuess to the double given by the user
            double userGuess = in.nextDouble();
            //calculate the estimate
            double estimate = sqrt(userGuess);
            out.println("The estimated square root is " + estimate);
            out.print("Would you like to calculate a square root? (y for yes)");
            response = in.nextLine();
            out.println();

        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }
}
