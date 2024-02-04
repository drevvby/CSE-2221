import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
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
public final class ProgramWithIOAndStaticMethod {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ProgramWithIOAndStaticMethod() {
    }

    /**
     * Generates and outputs the Hailstone series starting with the given
     * {@code NaturalNumber}.
     *
     * @param n
     *            the starting natural number
     * @param out
     *            the output stream
     * @updates out.content
     * @requires n > 0 and out.is_open
     * @ensures out.content = #out.content * [the Hailstone series starting with
     *          n]
     */
    private static void generateSeries(NaturalNumber n, SimpleWriter out) {
        NaturalNumber evenDiv = new NaturalNumber2(2);
        NaturalNumber oddMult = new NaturalNumber2(3);
        NaturalNumber oddAdd = new NaturalNumber2(1);
        NaturalNumber num = new NaturalNumber2(n);

        while (num.toInt() != 1) {

            if (num.toInt() % 2 == 1) {
                num.multiply(oddMult);
                num.add(oddAdd);
            } else {
                num.divide(evenDiv);
            }
            out.print(num.toString() + " ");
        }
        out.println();
        String numString = n.toString();
        out.println(numString);
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

        out.println("Enter a positive integer: ");
        int num = in.nextInteger();
        NaturalNumber n = new NaturalNumber2(num);
        generateSeries(n, out);

        in.close();
        out.close();
    }

}
