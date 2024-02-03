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
public final class CoinChange1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CoinChange1() {
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

        out.print("Enter a number of cents: ");
        int centsLeft = in.nextInteger();

        int dollars = centsLeft / 100;
        centsLeft = centsLeft % 100;

        int halfs = centsLeft / 50;
        centsLeft = centsLeft % 50;

        int quarters = centsLeft / 25;
        centsLeft = centsLeft % 25;

        int dimes = centsLeft / 10;
        centsLeft = centsLeft % 10;

        int nickels = centsLeft / 5;
        centsLeft = centsLeft % 5;

        int pennies = centsLeft;

        out.println("Number of dollar coins: " + dollars);
        out.println("Number of half dollar coins: " + halfs);
        out.println("Number of quarters: " + quarters);
        out.println("Number of dimes: " + dimes);
        out.println("Number of nickels: " + nickels);
        out.println("Number of pennies: " + pennies);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
