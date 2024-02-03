import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class CoinChange2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CoinChange2() {
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
         * Put your main program code here
         */
        String[] coinNames = { "dollars", "half dollars", "quarters", "dimes",
                "nickles", "pennies" };
        int[] coinVals = { 100, 50, 25, 10, 5, 1 };

        out.print("Enter a number of cents: ");
        int centsLeft = in.nextInteger();

        for (int counter = 0; counter < coinVals.length; ++counter) {
            int numOfCoins = centsLeft / coinVals[counter];
            centsLeft = centsLeft % coinVals[counter];
            out.println("Number of " + coinNames[counter] + ": " + numOfCoins);

        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
