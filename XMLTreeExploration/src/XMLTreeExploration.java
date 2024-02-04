import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 *
 *
 * @author Andrew White
 *
 */
public final class XMLTreeExploration {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private XMLTreeExploration() {
    }

    /**
     * Output information about the middle child of the given {@code XMLTree}.
     *
     * @param xt
     *            the {@code XMLTree} whose middle child is to be printed
     * @param out
     *            the output stream
     * @updates out.content
     * @requires <pre>
     * [the label of the root of xt is a tag]  and
     * [xt has at least one child]  and  out.is_open
     * </pre>
     * @ensures <pre>
     * out.content = #out.content * [the label of the middle child
     *  of xt, whether the root of the middle child is a tag or text,
     *  and if it is a tag, the number of children of the middle child]
     * </pre>
     */
    private static void printMiddleNode(XMLTree xt, SimpleWriter out) {
        int numChildren = xt.numberOfChildren();
        int middleIdx = 0;
        if (numChildren % 2 == 0) {
            middleIdx = (numChildren / 2) - 1;
        } else {
            middleIdx = ((numChildren - 1) / 2) - 1;
        }
        String middleLabel = xt.child(middleIdx).label();
        boolean middleIsTag = xt.child(middleIdx).isTag();
        if (middleIsTag) {
            out.println("Number of children of " + middleLabel + ": "
                    + xt.child(middleIdx).numberOfChildren());

        } else {
            out.println("The text of " + middleLabel + " is: "
                    + xt.child(middleIdx).label());
        }

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

        XMLTree xml = new XMLTree1(
                "http://web.cse.ohio-state.edu/software/2221/web-sw1/extras/"
                        + "instructions/xmltree-model/columbus-weather.xml");
        String xmlString = xml.toString();
        //out.println(xmlString);
        xml.display();

        boolean isTag = xml.isTag();
        String xmlRoot = xml.label();
        out.println(xmlRoot);
        if (isTag) {
            out.println(xmlRoot + " is a tag");
        } else {
            out.println(xmlRoot + " is text");
        }

        int numChildren = xml.numberOfChildren();
        XMLTree results = xml.child(0);
        XMLTree channel = results.child(0);
        out.println(channel.numberOfChildren());
        XMLTree title = channel.child(1);
        XMLTree titleText = title.child(0);
        out.println(titleText.label());
        out.println(xml.child(0).child(0).child(1).child(0).label());

        XMLTree astronomy = channel.child(10);
        if (astronomy.hasAttribute("sunset")) {
            out.println("sunset: " + astronomy.attributeValue("sunset"));
        } else {
            out.println("There is no attribute called sunset.");
        }
        if (astronomy.hasAttribute("midday")) {
            out.println("midday: " + astronomy.attributeValue("midday"));
        } else {
            out.println("There is no attribute called midday.");
        }
        printMiddleNode(channel, out);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
