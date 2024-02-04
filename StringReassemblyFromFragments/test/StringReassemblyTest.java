import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    @Test
    public void printWithLineSeparatorsTest1() {
        SimpleWriter out = new SimpleWriter1L();
        String s = "ignore ~ all of~ the l~ine separators";
        StringReassembly.printWithLineSeparators(s, out);
        out.close();
        //this is a good test because it places line separators in all different
        //types of places
    }

    @Test
    public void comboTest1() {
        SimpleWriter out = new SimpleWriter1L();
        String s1 = "this should combine";
        String s2 = "bine these two strings";
        String expected = "this should combine these two strings";
        int overlap = 4;
        String result = StringReassembly.combination(s1, s2, overlap);
        assertEquals(expected, result);
        out.close();
    }

    @Test
    public void add2SetTest() {
        SimpleWriter out = new SimpleWriter1L();
        String s1 = "jumping";
        String s2 = "ping";
        String s3 = "cat";
        Set<String> expected = new Set2<>();
        expected.add(s1);
        expected.add(s3);
        StringReassembly.addToSetAvoidingSubstrings(expected, s2);
        out.println(expected);
        out.close();
        //if the set produced doesn't include ping, test passed
    }

}
