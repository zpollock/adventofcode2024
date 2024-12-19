package src.helpers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
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

    public static boolean dfsSearch(char[][] grid, int row, int col, String word, int index, boolean[][] visited, int[][] directions) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || visited[row][col] || grid[row][col] != word.charAt(index)) {
            return false;
        }

        if (index == word.length() - 1) {
            return true;
        }

        visited[row][col] = true;

        for (int[] direction : directions) {
            if (dfsSearch(grid, row + direction[0], col + direction[1], word, index + 1, visited, directions)) {
                return true;
            }
        }

        visited[row][col] = false;
        return false;
    }

    public static boolean bfsSearch(char[][] grid, String word, int[][] directions) {
        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == word.charAt(0)) {
                    queue.offer(new int[]{i, j, 0}); 
                    visited.add(i + "," + j);
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int index = current[2];

            if (index == word.length() - 1) {
                return true;
            }

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (isInBounds(newRow, newCol, grid) && !visited.contains(newRow + "," + newCol) && grid[newRow][newCol] == word.charAt(index + 1)) {
                    queue.offer(new int[]{newRow, newCol, index + 1});
                    visited.add(newRow + "," + newCol);
                }
            }
        }

        return false;
    }

    public static boolean isInBounds(int row, int col, char[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public static boolean isInBounds(int row, int col, int[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    public static char[][] convertToCharGrid(ArrayList<String> grid) {
        char[][] charGrid = new char[grid.size()][];
        for (int i = 0; i < grid.size(); i++) {
            charGrid[i] = grid.get(i).toCharArray();
        }
        return charGrid;
    }

    public static int[][] convertToIntGrid(ArrayList<String> grid) {
        int[][] intGrid = new int[grid.size()][];
        for (int i = 0; i < grid.size(); i++) {
            intGrid[i] = grid.get(i).chars()
                .map(Character::getNumericValue)
                .toArray();
        }
        return intGrid;
    }

    public static int[][] convertToIntGridUsingSpaces(ArrayList<String> grid) {
        int[][] intGrid = new int[grid.size()][];
        for (int i = 0; i < grid.size(); i++) {
            intGrid[i] = Arrays.stream(grid.get(i).split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        }
        return intGrid;
    }

    public static void printGrid(char[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public static void printGrid(int[][] grid) {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

}
