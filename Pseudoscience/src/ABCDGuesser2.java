import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Estimates a given number using the de Jager formula
 *
 * @author Andrew White
 *
 */
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        out.print("Enter a positive real number: ");
        String userString = in.nextLine();
        while (!FormatChecker.canParseDouble(userString)) {
            out.print("Invalid input! Enter a positive real number: ");
            userString = in.nextLine();
        }
        double userDouble = Double.parseDouble(userString);
        return userDouble;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        out.print("Enter a positive real number besides 1.0: ");
        String userString = in.nextLine();
        double userDouble = 1.0;
        while (!FormatChecker.canParseDouble(userString)
                || Double.parseDouble(userString) == 1.0) {
            out.println(
                    "Error! input must be a positive real number besides 1.0");
            out.print("Enter a positive real number besides 1.0: ");
            userString = in.nextLine();
        }
        userDouble = Double.parseDouble(userString);
        return userDouble;
    }

    private static void printValues(SimpleReader in, SimpleWriter out,
            double bestA, double bestB, double bestC, double bestD,
            double userAppx, double bestEstimate) {
        double relativeError = (Math.abs(userAppx - bestEstimate) / userAppx);
        out.println("Exponent A: " + bestA);
        out.println("Exponent B: " + bestB);
        out.println("Exponent C: " + bestC);
        out.println("Exponent D: " + bestD);
        out.print(relativeError, 2, false);
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

        double[] jagerValues = { -5, -4, -3, -2, -1, -1 / 2, -1 / 3, -1 / 4, 0,
                1 / 4, 1 / 3, 1 / 2, 1, 2, 3, 4, 5 };
        int lengthJager = jagerValues.length;
        double userAppx = getPositiveDouble(in, out);
        double userW = getPositiveDoubleNotOne(in, out);
        double userX = getPositiveDoubleNotOne(in, out);
        double userY = getPositiveDoubleNotOne(in, out);
        double userZ = getPositiveDoubleNotOne(in, out);
        double bestEstimate = 0;
        double bestA = 0;
        double bestB = 0;
        double bestC = 0;
        double bestD = 0;

        for (int countA = 0; countA < lengthJager - 1; ++countA) {
            double a = Math.pow(userW, jagerValues[countA]);

            for (int countB = 0; countB < lengthJager - 1; ++countB) {
                double b = Math.pow(userX, jagerValues[countB]);

                for (int countC = 0; countC < lengthJager - 1; ++countC) {
                    double c = Math.pow(userY, jagerValues[countC]);

                    for (int countD = 0; countD < lengthJager - 1; ++countD) {
                        double d = Math.pow(userZ, jagerValues[countD]);

                        double tempEstimate = a * b * c * d;
                        if (Math.abs(userAppx - tempEstimate) < Math
                                .abs(userAppx - bestEstimate)) {
                            bestEstimate = tempEstimate;
                            bestA = jagerValues[countA];
                            bestB = jagerValues[countB];
                            bestC = jagerValues[countC];
                            bestD = jagerValues[countD];
                        }
                    }
                }
            }
        }
        printValues(in, out, bestA, bestB, bestC, bestD, userAppx,
                bestEstimate);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }
}
