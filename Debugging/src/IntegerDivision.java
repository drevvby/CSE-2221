import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program that incorrectly calculates seconds per day
 *
 * @author Andrew White
 *
 */
public final class IntegerDivision {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private IntegerDivision() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        final int microsPerDay = 24 * 60 * 60 * 1000;
        final int millisPerDay = 24 * 60 * 60;
        out.println(microsPerDay / millisPerDay);
        out.close();
    }

}
