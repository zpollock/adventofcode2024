package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import src.helpers.Coordinate;

public class Day4 extends Day {
    @Override
    public String process(String inputFileName, boolean isPart2) {        
        Path filePath = Path.of("resources", inputFileName);
         try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {        
            return isPart2 ? partTwo(reader) : partOne(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private static String partOne(BufferedReader reader) throws IOException {
        String line;

        ArrayList<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);            
        }

        return String.valueOf(countWordDFS(lines, "XMAS"));
    }

    private static int countWordDFS(ArrayList<String> grid, String word) {
        int[][] directions = Coordinate.DIRECTIONS;
        int count = 0;
        
        for (int row = 0; row < grid.size(); row++) {
            for (int col = 0; col < grid.get(row).length(); col++) {
                for (int[] dir : directions) {
                    if (dfsSearch(grid, row, col, word, 0, dir)) {
                        count++;
                    }
                }
            }
        }
        
        return count;
    }
    
    private static boolean dfsSearch(List<String> grid, int row, int col, String word, int index, int[] direction) {
        if (row < 0 || row >= grid.size() || 
            col < 0 || col >= grid.get(row).length()) {
            return false;
        }
        
        if (grid.get(row).charAt(col) != word.charAt(index)) {
            return false;
        }
        
        if (index == word.length() - 1) {
            return true;
        }
        
        return dfsSearch(grid, row + direction[0], col + direction[1], word, index + 1, direction);
    }

    private static String partTwo(BufferedReader reader) throws IOException {
        String line;

        ArrayList<String> lines = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            lines.add(line);            
        }

        return String.valueOf(countXDFS(lines));
    }

    private static int countXDFS(ArrayList<String> grid) {
        int[][] directions = {{1, 1},{1, -1}}; //diagonals only

        int count = 0;
        
        for (int row = 0; row < grid.size(); row++) {
            for (int col = 0; col < grid.get(row).length(); col++) {
                if (dfsSearch(grid, row, col, "MAS", 0, directions[0]) || dfsSearch(grid, row, col, "SAM", 0, directions[0])) {
                    if (dfsSearch(grid, row, col+2, "MAS", 0, directions[1]) || dfsSearch(grid, row, col+2, "SAM", 0, directions[1])) {
                        count++;                                                    
                    }
                }
            }
        }
        
        return count;
    }
}
