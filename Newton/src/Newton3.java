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
public final class Newton3 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton3() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%
     *
     * @param x
     *            positive number to compute square root of
     * @param error
     *            percent of answer the guess can be from the answer
     * @return estimate of square root
     */
    private static double sqrt(double x, double error) {
        double r = x;
        while (Math.abs(r * r - x) / x > error * error) {
            /**
             * while the guess is too far from the value defined by a given
             * error
             */
            if (x == 0) {
                r = 0;
                /**
                 * if x is zero, then set the guess to zero as to not divide by
                 * zero
                 */
            } else {
                r = (r + (x / r)) / 2;
                //take the average of the guess and a better guess
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

        //initial response is y to initially ask for a number
        String response = "y";

        while (response.equals("y")) {
            //only prompt user when they enter y for response
            out.print("Enter a number to calculate the square root of: ");
            //set userGuess to the double given by the user
            double userGuess = in.nextDouble();
            out.println();
            out.print("Enter an error threshold: ");
            //ask for and set variable for an error threshold
            double error = in.nextDouble();
            out.println();
            //calculate the estimate
            double estimate = sqrt(userGuess, error);
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
