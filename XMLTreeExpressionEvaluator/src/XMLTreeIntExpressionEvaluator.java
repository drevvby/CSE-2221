import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Andrew White
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        int answer = 0;
        //if the label is number, take the value of the tag and parse to integer
        if (exp.label().equals("number")) {
            String s = exp.attributeValue("value");
            //set value to the answer
            answer = Integer.parseInt(s);
        }
        //if label is plus, add the current answer to the addition of the next
        //two children
        if (exp.label().equals("plus")) {
            answer = answer + evaluate(exp.child(0)) + evaluate(exp.child(1));
        }
        //if label is plus, add the current answer to the subtraction of the
        //next two children
        if (exp.label().equals("minus")) {
            answer = answer + evaluate(exp.child(0)) - evaluate(exp.child(1));
        }
        //if the label is times, add the answer to the multiplication of
        //the next two children
        if (exp.label().equals("times")) {
            answer = answer + evaluate(exp.child(0)) * evaluate(exp.child(1));
        }
        //if the label is divide, add the answer to the division of the next two children
        if (exp.label().equals("divide")) {
            //if the divisor is 0, print a divide by zero error and end execution
            if (evaluate(exp.child(1)) == 0) {
                Reporter.fatalErrorToConsole(
                        "ERROR: Divide by Zero is not allowed");
            }
            answer = answer + evaluate(exp.child(0)) / evaluate(exp.child(1));
        }
        //return the answer each time
        return answer;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}