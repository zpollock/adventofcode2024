package src.helpers;
import java.util.ArrayList;
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

    public static ArrayList<String> getRotatedStrings(ArrayList<String> lines) {
        if (lines.isEmpty()) {
            return new ArrayList<>();
        }
        
        int cols = lines.get(0).length();
        ArrayList<String> rotatedLines = new ArrayList<>();
        
        for (int col = 0; col < cols; col++) {
            StringBuilder rotatedLine = new StringBuilder();
            for (String line : lines) {
                rotatedLine.append(line.charAt(col));
            }
            
            rotatedLines.add(rotatedLine.toString());
        }
        
        return rotatedLines;
    }
    

    public static ArrayList<String> getDiagonalStrings(ArrayList<String> lines) {
        ArrayList<String> diagonals = new ArrayList<>();
        int numRows = lines.size();
        if (numRows == 0) return diagonals;
    
        int numCols = lines.get(0).length();
    
        for (int row = 0; row < numRows; row++) {
            StringBuilder diagonal = new StringBuilder();
            int x = row, y = 0;
            while (x < numRows && y < numCols) {
                diagonal.append(lines.get(x).charAt(y));
                x++;
                y++;
            }
            diagonals.add(diagonal.toString());
        }
    
        for (int col = 1; col < numCols; col++) {
            StringBuilder diagonal = new StringBuilder();
            int x = 0, y = col;
            while (x < numRows && y < numCols) {
                diagonal.append(lines.get(x).charAt(y));
                x++;
                y++;
            }
            diagonals.add(diagonal.toString());
        }
    
        return diagonals;
    }
    

    public static int countSubstrInString(String text, String substring) {
        if (text == null || substring == null || substring.isEmpty()) {
            return 0;
        }
        
        int count = 0;
        int index = 0;
        
        while ((index = text.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length();
        }
        
        return count;
    }
}
