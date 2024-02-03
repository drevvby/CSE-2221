import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public final class Glossary {

    /**
     * Definition of separator characters.
     */
    private static final String SEPARATORS = " \t\n\r,-.!?[];:/()\'`*";

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Glossary() {
        // no code needed here
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        //initialize counter and character that will be replaced
        int count = 0;

        //while count is less than the input string str's length, c will be set to the
        //current character of str and added to the charSet, creating a character set
        //full of every unique character in str
        while (count < str.length()) {
            char c = str.charAt(count);
            if (!charSet.contains(c)) {

                charSet.add(c);
            }
            count++;
        }

    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code SEPARATORS}) or "separator string" (maximal length string of
     * characters in {@code SEPARATORS}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection entries(SEPARATORS) = {}
     * then
     *   entries(nextWordOrSeparator) intersection entries(SEPARATORS) = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection entries(SEPARATORS) /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of entries(SEPARATORS)  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of entries(SEPARATORS))
     * </pre>
     */
    static String nextWordOrSeparator(String text, int position) {
        assert text != null : "Violation of: text is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int i = position;
        while (i < text.length()
                && SEPARATORS.indexOf(text.charAt(position)) == SEPARATORS
                        .indexOf(text.charAt(i))) {
            i++;
        }
        return text.substring(position, i);
    }

    /**
     * take a set of terms and produce the lowest string alphabetically, also
     * removing it from the set
     *
     * @param terms
     *            the set of terms from the glossary
     * @return the lowest remaining string alphabetically
     */
    public static String alphabeticalOrder(Set<String> terms) {
        //new temporary set and empty string, which will be used to test for
        //alphabetization
        Set<String> temp = new Set1L<>();
        String abcOrder = "";

        //loop runs through every single entry in the set terms, takes a random
        //string and compares it against every string in the set, incrementing the
        //counter if the string is of lesser order
        while (terms.size() > 0 && abcOrder.equals("")) {
            int count = 0;
            String abcTest = terms.removeAny();
            for (String comp : terms) {
                if (comp.compareTo(abcTest) < 0) {
                    count++;
                }
            }
            //if the counter is never incremented, the String being tested is lesser
            //in alphabetical order, otherwise, add the tested String to the set
            if (count == 0) {
                abcOrder = abcTest;
            } else {
                temp.add(abcTest);
            }
        }
        //replace the terms set with the new alphabetized set
        terms.add(temp);
        return abcOrder;
    }

    /**
     *
     * @param out
     *            SimpleWriter to print to file
     * @param glossary
     *            map of words and definitions
     * @param word
     *            the current word a page is being made for
     * @param outputFile
     *            the name of the file being written to
     * @param abcTerms
     *            the terms of the glossary in alphabetical order in an array
     */
    public static void printEntry(SimpleWriter out,
            Map<String, String> glossary, String word, String outputFile,
            String[] abcTerms, Set<String> terms) {
        assert out != null : "Violation of: out is not null";
        assert glossary.size() > 0 : "Violation of: glossary is not empty";
        assert !outputFile.equals("") : "Violation of: outputFile exists";

        //string for the file the html code is being output to
        String link = outputFile + "/" + word + ".html";
        //SimpleWriter to write to the file
        SimpleWriter outFile = new SimpleWriter1L(link);

        //store the definition of the current word
        String definition = glossary.value(word);

        //print out basic header html tags
        outFile.println("<html>");
        outFile.println("<head>");
        outFile.println("<title>" + word + "</title>");
        outFile.println("</head>");
        outFile.println("<body style=\"background-color:ivory;\">");
        outFile.println("<h2><b><i><font color = \"red\">" + word
                + "</font></i></b></h2>");

        //create a new set of separators, and run it through generateElements with a comma
        Set<Character> separators = new Set1L<>();
        String finalDef = "";
        //add a comma and a space to the separators set
        generateElements(",", separators);
        generateElements(" ", separators);

        /**
         * loop through every character in the definition, and checks every
         * single word and if the word is equal to any of the terms in the
         * glossary, a link is created for that word
         */
        int position = 0;
        while (position < definition.length()) {

            String wosResult = nextWordOrSeparator(definition, position);
            out.println(wosResult);
            //if the separator set contains even the first character of the result
            //of nextWordOrSeparator, it is a separator, so it is added to the
            //final definition with no changes
            if (separators.contains(wosResult.charAt(0))) {
                finalDef = finalDef + wosResult;
                //if the string contains nothing from the separators set, then it
                //has to be checked for if it exists in the array of terms, if it
                //does, a link is created in place of the word with
                //the properly formatted and clickable html link
            } else {
                boolean linked = false;
                for (int j = 0; j < abcTerms.length; j++) {
                    String arrElement = abcTerms[j];
                    if (wosResult.equals(arrElement)) {
                        finalDef = finalDef + "<a href=\"" + wosResult
                                + ".html\">" + wosResult + "</a>";
                        linked = true;
                    }

                }
                //if none of the words in the definition were a part of the terms
                //array, then that word is added to the final definition with no link
                if (!linked) {
                    finalDef = finalDef + wosResult;
                }
            }
            //move the position to after the word that was just checked
            //(in other words skip to the next word to be checked
            position += wosResult.length();
        }
        //printing the final definition and closing tags to the file
        outFile.println("<blockquote>" + finalDef + "<blockquote>");
        outFile.println("<hr />");
        outFile.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
        outFile.println("</body>");
        outFile.println("</html>");
        outFile.close();
    }

    public static void printMainMenu(SimpleWriter out,
            Map<String, String> glossary, Set<String> terms,
            Queue<String> termsInAlpha, String outputFile, String[] abcTerms) {

        //creating a new mainpage file name for the html code to be output to
        String indexName = outputFile + "/index.html";
        SimpleWriter outFile = new SimpleWriter1L(indexName);
        //printing opening html tags
        outFile.println("<html>");
        outFile.println("<head>");
        outFile.println("<title>Glossary</title>");
        outFile.println("</head>");
        outFile.println("<body style=\"background-color:ivory;\">");
        outFile.println("<h2>Glossary</h2>");
        outFile.println("<hr />");
        outFile.println("<h3>Index</h3>");
        outFile.println("<ul>");
        //for every single alphabetized term in the queue, create a link to that
        //page entry
        while (termsInAlpha.length() > 0) {
            String word = termsInAlpha.dequeue();
            printEntry(out, glossary, word, outputFile, abcTerms, terms);
            outFile.println(
                    "<li><a href= \"" + word + ".html\">" + word + "</a></li>");
        }
        //closing html tags
        outFile.println("</ul>");
        outFile.println("</body>");
        outFile.println("</html>");
        outFile.close();
    }

    static void generateGlossary(String fileName, Map<String, String> glossary,
            Set<String> terms) {
        assert fileName != null : "Violation of: fileName is not null";
        assert glossary != null : "Violation of: glossary is not null";

        //create a simplereader to look through the file
        SimpleReader in = new SimpleReader1L(fileName);

        //while there's still content in the file, look through each grouping of lines
        //the first line in the group is the definition, and every line after it
        //containing content is the corresponding definition
        while (!in.atEOS()) {
            String currentTerm = in.nextLine();
            String definition = "";
            String partialDefinition = in.nextLine();
            while (!partialDefinition.equals("")) {
                definition += partialDefinition;
                partialDefinition = in.nextLine();
            }
            //add the term and definition to the map
            glossary.add(currentTerm, definition);
            terms.add(currentTerm);

        }
        //close the file reader
        in.close();

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        //initialize SimpleReader and SimpleWriter objects
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        //prompts for input and output files
        out.println("Enter the name of an input file: ");
        String inputFile = in.nextLine();
        out.println("Enter the name of an output file: ");
        String outputFile = in.nextLine();

        //all input needed has been obtained, so close input stream
        in.close();

        //create empty map and set for terms and their definitions
        Map<String, String> glossary = new Map1L<>();
        Set<String> terms = new Set1L<>();

        //fill map with matching terms and definitions and fill the set with
        //just the terms along with a copy
        generateGlossary(inputFile, glossary, terms);

        //create new empty queue where alphabetized terms will be entered into
        String[] abcTerms = new String[terms.size()];
        Queue<String> termsQueue = new Queue1L<>();

        //looping through every term and calling alphabetical order to produce
        //a queue of terms in abc order

        int i = 0;
        while (terms.size() > 0) {
            String termAlpha = alphabeticalOrder(terms);
            termsQueue.enqueue(termAlpha);
            abcTerms[i] = termAlpha;
            i++;
        }
        //print the html code to the file
        printMainMenu(out, glossary, terms, termsQueue, outputFile, abcTerms);
        out.close();
    }

}
