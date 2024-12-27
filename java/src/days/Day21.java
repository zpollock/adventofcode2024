package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import src.helpers.Coordinate.Direction;

public class Day21 extends Day {
    private static final Map<Character, int[]> numericKeypad = new HashMap<>();
    private static final Map<Character, int[]> directionalKeypad = new HashMap<>();
    private static final Map<String, String> memo = new HashMap<>();

    static {        
        char[][] numericGrid = {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {' ', '0', 'A'}
        };

        char[][] directionalGrid = {
            {' ', '^', 'A'},
            {'<', 'v', '>'},
        };

        for (int r = 0; r < numericGrid.length; r++) {
            for (int c = 0; c < numericGrid[r].length; c++) {
                numericKeypad.put(numericGrid[r][c], new int[]{r, c});
            }
        }

        for (int r = 0; r < directionalGrid.length; r++) {
            for (int c = 0; c < directionalGrid[r].length; c++) {
                directionalKeypad.put(directionalGrid[r][c], new int[]{r, c});
            }
        }
    }

    public static String getDirections(char from, char to, Map<Character, int[]> positions) {
        if (memo.containsKey(from + "," + to)) {
            return memo.get(from + "," + to);
        }

        StringBuilder directions = new StringBuilder();
        int[] start = positions.get(from);
        int[] end = positions.get(to);
        int[] forbidden = positions.get(' ');
        int currentRow = start[0];
        int currentCol = start[1];

        int rowDiff = end[0] - start[0];
        while (rowDiff != 0) {            
            if (rowDiff > 0) {
                directions.append('v');
                rowDiff--;
                currentRow++;
            } else {
                directions.append('^');
                rowDiff++;
                currentRow--;
            }
        }

        if(forbidden[0] == currentRow && forbidden[1] == currentCol) {
            String columnFirstDirections = getDirectionsColumnFirst(from, to, positions);
            memo.put(from + "," + to, columnFirstDirections); 
            return columnFirstDirections;
        } 

        int colDiff = end[1] - start[1];
        while (colDiff != 0) {
            if (colDiff > 0) {
                directions.append('>');
                colDiff--;
            } else {
                directions.append('<');
                colDiff++;
            }
        }
        
        String result = directions.toString();
        memo.put(from + "," + to, result);
        return result;
    }

    public static String getDirectionsColumnFirst(char from, char to, Map<Character, int[]> positions) {
        if (memo.containsKey(from + "," + to)) {
            return memo.get(from + "," + to);
        }
        StringBuilder directions = new StringBuilder();
        int[] start = positions.get(from);
        int[] end = positions.get(to);
        
        int[] forbidden = positions.get(' ');
        int currentRow = start[0];
        int currentCol = start[1];

        int colDiff = end[1] - start[1];
        while (colDiff != 0) {
            if (colDiff > 0) {
                directions.append('>');
                colDiff--;
                currentCol++;
            } else {
                directions.append('<');
                colDiff++;
                currentCol--;
            }
        }

        if(forbidden[0] == currentRow && forbidden[1] == currentCol) {
            return getDirections(from, to, positions);
        } 

        int rowDiff = end[0] - start[0];
        while (rowDiff != 0) {            
            if (rowDiff > 0) {
                directions.append('v');
                rowDiff--;                    
            } else {
                directions.append('^');
                rowDiff++;
            }
        }

        String result = directions.toString();
        memo.put(from + "," + to, result);
        return result;
    }

    @Override
    public String process(String inputFileName, boolean isPart2) {        
        Path filePath = Path.of("resources", inputFileName);
         try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {        
            return parseInput(reader, isPart2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private static String parseInput(BufferedReader reader, boolean isPart2) throws IOException {
        ArrayList<String> input = new ArrayList<>();
        String line;
        long ret = 0;

        while ((line = reader.readLine()) != null) {
            input.add(line);           
        }

        ret = (isPart2) ? partTwoHelper(input) : partOneHelper(input);
        return String.valueOf(ret);
    }

    private static long partOneHelper(ArrayList<String> codes) {
        long ret = 0;
        for(String code : codes) {
            int intVal = Integer.parseInt(code.substring(0, code.length() - 1));
            int minLength = Integer.MAX_VALUE;
            HashSet<String> directionsSet = getDirectionsPerm("A" + code, numericKeypad, false);
            for(String directionsEntry : directionsSet) {
                String directions = getDirections("A" + directionsEntry, directionalKeypad, false,0,new StringBuilder());
                directions = getDirections("A" + directions, directionalKeypad, false, 0, new StringBuilder());
                minLength = Math.min(minLength, directions.length());
            }
            ret += intVal * minLength;
        }
        return ret;
    }

    private static HashSet<String> getDirectionsPerm(String code, Map<Character, int[]> keypad, boolean isColumnsFirst) {
        HashSet<String> directionsSet = new HashSet<>();

        for(int i = 0; i < code.length() - 1; i++) {
            String directionColumnFirst = getDirectionsColumnFirst(code.charAt(i), code.charAt(i+1), keypad) + "A";
            String direction = getDirections(code.charAt(i), code.charAt(i+1), keypad) + "A";
            if(directionsSet.isEmpty()) {
                directionsSet.add(direction);
                directionsSet.add(directionColumnFirst);
                continue;
            }

            List<String> directionsList = new ArrayList<>(directionsSet);
            for(String directionsEntry :  directionsList) {
                directionsSet.add(directionsEntry + direction);
                directionsSet.add(directionsEntry + directionColumnFirst);
                directionsSet.remove(directionsEntry);
            }
            
        }
        return directionsSet;
    }

    private static String getDirections(String code, Map<Character, int[]> keypad, boolean isColumnsFirst, int i, StringBuilder directions) {
        if(i == code.length() - 1) return "";                
        
        if(isColumnsFirst) {
            directions.insert(0,getDirectionsColumnFirst(code.charAt(i), code.charAt(i+1), keypad) + "A");
        } else {
            String memoRes = "";
            for(int j = i+1; j < code.length(); j++) {
                if(memo.containsKey(code.substring(i,j))) {
                    memoRes = memo.get(code.substring(i,j));
                    return memoRes + getDirections(code, keypad, isColumnsFirst, j - 1, directions);
                } else {
                    break;
                }
            }
            directions.insert(0,getDirections(code.charAt(i), code.charAt(i+1), keypad) + "A");
            memo.put(code.substring(i), directions.toString());            
        }
        getDirections(code, keypad, isColumnsFirst, i + 1, directions);
        return directions.toString();
    }

    private static long partTwoHelper(ArrayList<String> codes) {
        long ret = 0;
        for(String code : codes) {
            int intVal = Integer.parseInt(code.substring(0, code.length() - 1));
            long minLength = Long.MAX_VALUE;
            HashSet<String> directionsSet = getDirectionsPerm("A" + code, numericKeypad, false);
            for(String directionsEntry : directionsSet) {
                String directions = getDirections("A" + directionsEntry, directionalKeypad, false, 0,  new StringBuilder());
                for(int i = 0; i < 24; i++) {
                    System.out.println(i);
                    directions = getDirections("A" + directions, directionalKeypad, false, 0, new StringBuilder());
                }
                minLength = Math.min(minLength, directions.length());
            }
            ret += intVal * minLength;
        }
        return ret;
    }
}
