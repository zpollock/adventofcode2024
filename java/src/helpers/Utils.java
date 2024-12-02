package src.helpers;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static String replaceLetteredNumbers(String line) {
        String[] searchList = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] replacementList = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        return StringUtils.replaceEach(line, searchList, replacementList);
    } 

    public static int[] removeIndexFromArray(int[] array, int indexToRemove) {
        return IntStream.range(0, array.length)
                        .filter(i -> i != indexToRemove)
                        .map(i -> array[i])
                        .toArray();
    }
}
