import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class GlossaryTest {

    /**
     * Definition of separator characters.
     */
    private static final String SEPARATORS = " \t\n\r,-.!?[];:/()\'`*";

    //test of nextWordOrSeparator to make sure that the program can accurately find
    //the next word or separator character. this test ensures that the first word is
    //read as a word and the comma is treated as a separator
    @Test
    public void test_nextWordOrSeparator() {
        SimpleWriter out = new SimpleWriter1L();
        String testStr = "the, first word of this sentence should be the result";
        String expected = "the";
        String expected2 = ", ";

        String testResult = Glossary.nextWordOrSeparator(testStr, 0);
        String testResult2 = Glossary.nextWordOrSeparator(testStr,
                testResult.length());
        assertEquals(testResult, expected);
        assertEquals(testResult2, expected2);
        out.close();
    }

    //another test of nextWordOrSeparator to make sure that the program can accurately
    //find the next word or separator character. this test ensures that weird
    //strings like one starting with multiple spaces and separators containing
    //multiple separator characters are handled correctly
    @Test
    public void test_nextWordOrSeparator2() {
        SimpleWriter out = new SimpleWriter1L();
        String testStr = "       this, h ,da ,sda ";
        String expected = "       ";
        String expected2 = "this";
        String expected3 = ", ";
        String testResult = Glossary.nextWordOrSeparator(testStr, 0);
        String testResult2 = Glossary.nextWordOrSeparator(testStr,
                testResult.length());
        String testResult3 = Glossary.nextWordOrSeparator(testStr,
                testResult.length() + testResult2.length());
        assertEquals(testResult, expected);
        assertEquals(testResult2, expected2);
        assertEquals(testResult3, expected3);
        out.close();
    }

    //simple test of alphabeticalOrder to test handling of regular english words, and also
    //testing two words with the same first letter to see how the method handles
    //those
    @Test
    public void test_abcOrder() {
        Set<String> abcTest = new Set1L<>();

        abcTest.add("Bear");
        abcTest.add("Zebra");
        abcTest.add("Apple");
        abcTest.add("Mouse");
        abcTest.add("Building");
        abcTest.add("Quiet");

        String str = Glossary.alphabeticalOrder(abcTest);
        String str2 = Glossary.alphabeticalOrder(abcTest);
        assertEquals("Apple", str);
        assertEquals("Bear", str2);
    }

    //test to see whether alphabeticalOrder can handle special characters and empty entries
    //empty entries should be first and numbers before letters, with special
    //characters coming last
    @Test
    public void test_abcOrder2() {
        Set<String> abcTest = new Set1L<>();
        SimpleWriter out = new SimpleWriter1L();
        abcTest.add("A");
        abcTest.add("Z");
        abcTest.add("7");
        abcTest.add(" ");
        abcTest.add("[S]");

        String str = Glossary.alphabeticalOrder(abcTest);
        String str2 = Glossary.alphabeticalOrder(abcTest);
        String str3 = Glossary.alphabeticalOrder(abcTest);
        String str4 = Glossary.alphabeticalOrder(abcTest);
        String str5 = Glossary.alphabeticalOrder(abcTest);
        assertEquals(" ", str);
        assertEquals("7", str2);
        assertEquals("A", str3);
        assertEquals("Z", str4);
        assertEquals("[S]", str5);
        out.close();
    }

    //this test is a test of the generated map and set from generateGlossary,
    //the only practical way to test for if they were generated correctly is to
    //print the map and set to the console and check that all keys and values are
    //included
    @Test
    public void test_generateGlossary() {
        SimpleWriter out = new SimpleWriter1L();
        Set<String> setTest = new Set1L<>();
        Map<String, String> mapTest = new Map1L<>();
        String fileName = "testTerms.txt";
        Glossary.generateGlossary(fileName, mapTest, setTest);
        out.println(mapTest);
        out.println(setTest);
        out.close();
    }

    //test to see if generateElements can fill the set with any type of character
    //prints to console to ensure that all characters of the input string are
    //included in the set
    @Test
    public void test_generateElements() {
        SimpleWriter out = new SimpleWriter1L();
        String elements = " ,:;a2c";
        Set<Character> charSet = new Set1L<>();
        Glossary.generateElements(elements, charSet);
        out.println(charSet);
        out.close();
    }

}
