import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program that incorrectly compares two doubles
 *
 * @author Andrew White
 *
 */
public final class TimeForAChange {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TimeForAChange() {
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        double x = 456.0;
        double y = 100.0 * 4.56;
        if (Math.abs(x - y) < 0.001) {
            out.println("equal");
        } else {
            out.println("not equal");
        }
        out.close();
    }

}
