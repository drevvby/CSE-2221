import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program that adds two numbers
 *
 * @author Andrew White
 *
 */
public final class Elementary {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Elementary() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        out.println(12345 + 54321);
        out.close();
    }

}
