package src.helpers;
import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static String replaceLetteredNumbers(String line) {
        String[] searchList = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] replacementList = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        return StringUtils.replaceEach(line, searchList, replacementList);
    } 
}
