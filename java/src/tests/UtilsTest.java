package src.tests;

import org.junit.Test;
import src.helpers.Utils;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void testReplaceLetteredNumbers_basicReplacement() {
        String input = "one two three";
        String expected = "1 2 3";
        assertEquals("Basic replacement failed", expected, Utils.replaceLetteredNumbers(input));
    }

    @Test
    public void testReplaceLetteredNumbers_mixedCase() {
        String input = "Four Five six";
        String expected = "Four Five 6"; // Only exact matches should be replaced
        assertEquals("Mixed case input failed", expected, Utils.replaceLetteredNumbers(input));
    }

    @Test
    public void testReplaceLetteredNumbers_numbersInInput() {
        String input = "one 2 three 4";
        String expected = "1 2 3 4";
        assertEquals("Numbers already in input failed", expected, Utils.replaceLetteredNumbers(input));
    }

    @Test
    public void testReplaceLetteredNumbers_emptyInput() {
        String input = "";
        String expected = "";
        assertEquals("Empty input failed", expected, Utils.replaceLetteredNumbers(input));
    }

    @Test
    public void testReplaceLetteredNumbers_noMatch() {
        String input = "ten eleven";
        String expected = "ten eleven";
        assertEquals("No match case failed", expected, Utils.replaceLetteredNumbers(input));
    }
}
