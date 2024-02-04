import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Basic implementation of a password checker using multiple boolean values for
 * criteria and for loops
 *
 * @author Andrew White
 *
 */
public final class CheckPassword {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CheckPassword() {
    }

    /**
     * Checks whether the given String satisfies the OSU criteria for a valid
     * password. Prints an appropriate message to the given output stream.
     *
     * @param passwordCandidate
     *            the String to check
     * @param out
     *            the output stream
     */
    private static void checkPassword(String password, SimpleWriter out) {
        boolean len = false;
        boolean cap = false;
        boolean low = false;
        boolean dig = false;
        int numPass = 0;
        if (password.length() >= 8) {
            len = true;
            ++numPass;
        }
        int numCap = 0;
        for (int i = 0; i < password.length() - 1; ++i) {
            if (Character.isUpperCase(password.charAt(i))) {
                ++numCap;
            }
        }
        if (numCap > 0) {
            cap = true;
            ++numPass;
        }
        int numLow = 0;
        for (int j = 0; j < password.length() - 1; ++j) {
            if (Character.isUpperCase(password.charAt(j))) {
                ++numLow;
            }
        }
        if (numLow > 0) {
            low = true;
            ++numPass;
        }
        int numDig = 0;
        for (int k = 0; k < password.length() - 1; ++k) {
            if (Character.isDigit(password.charAt(k))) {
                ++numDig;
            }
        }
        if (numDig > 0) {
            dig = true;
            ++numPass;
        }

        if (dig == false) {
            out.println("Password needs at least one numerical digit");
        }
        if (len == false) {
            out.println("Password needs at least 8 characters");
        }
        if (cap == false) {
            out.println("Password needs at least one capital");
        }
        if (low == false) {
            out.println("Password needs at least one lowercase");
        }

        if (numPass >= 2) {
            out.println("Password is valid.");
        }

        out.close();
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

        out.print("Enter a password: ");

        String password = in.nextLine();
        out.println();

        checkPassword(password, out);
        /*
         * Close input and output streams
         */
        in.close();

    }

}
