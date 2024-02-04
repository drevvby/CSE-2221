import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
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
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
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
    private static NaturalNumber evaluate(XMLTree exp) {
        NaturalNumber ans = new NaturalNumber2();
        //if the label is number, parse the string tag value into an int and
        //create a new NaturalNumber of that value
        if (exp.label().equals("number")) {
            String s = exp.attributeValue("value");
            NaturalNumber num = new NaturalNumber2(Integer.parseInt(s));
            //add the number to  the cumulative answer
            ans.add(num);
        }
        //if the label is plus, create a new NaturalNumber and perform
        //addition, add to cumulative answer
        if (exp.label().equals("plus")) {
            NaturalNumber num = new NaturalNumber2(evaluate(exp.child(0)));
            num.add(evaluate(exp.child(1)));
            ans.add(num);
        }
        //if the label is minus, take values of the next two children
        if (exp.label().equals("minus")) {
            NaturalNumber num = new NaturalNumber2(evaluate(exp.child(0)));
            NaturalNumber num2 = new NaturalNumber2(evaluate(exp.child(1)));
            //if the second number is larger than the first, print a
            //negative NaturalNumber message and end execution
            if (num.compareTo(num2) < 0) {
                Reporter.fatalErrorToConsole(
                        "ERROR: Subtraction creates negative NaturalNumber");
            }
            //subtract num2 from num and add to the cumulative answer
            num.subtract(num2);
            ans.add(num);
        }
        //if label is times, create two new NaturalNumbers for the next
        //two children and multiply them together, add to cumulative answer
        if (exp.label().equals("times")) {
            NaturalNumber num = new NaturalNumber2(evaluate(exp.child(0)));
            NaturalNumber num2 = new NaturalNumber2(evaluate(exp.child(1)));
            num.multiply(num2);
            ans.add(num);
        }
        //if label is divide, create two new NaturalNumbers for the next two
        //children. if the divisor as an integer is zero, print a divide by
        //zero error message and end execution
        if (exp.label().equals("divide")) {
            NaturalNumber num = new NaturalNumber2(evaluate(exp.child(0)));
            NaturalNumber num2 = new NaturalNumber2(evaluate(exp.child(1)));
            if (num2.toInt() == 0) {
                Reporter.fatalErrorToConsole("ERROR: Cannot divide by zero");
            }
            //divide num by num2 and add to  the cumulative answer
            num.divide(num2);
            ans.add(num);
        }

        return ans;
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