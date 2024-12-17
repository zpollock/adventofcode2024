package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import src.helpers.Coordinate;
import src.helpers.Utils;

public class Day12 extends Day {
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
        long ret = 0;
        String line;
        ArrayList<String> lines = new ArrayList<>();

        while ((line = reader.readLine()) != null) {            
            lines.add(line);            
        }

        char[][] grid = Utils.convertToCharGrid(lines);


        ret = helper(grid, isPart2);
        return String.valueOf(ret);
    }

    private static long helper(char[][] grid, boolean isPart2) {
        long total = 0;
        HashSet<String> visisted = new HashSet<>();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                char crop = grid[i][j];
                if(visisted.contains(i+","+j)) continue;
                total += isPart2 ? bfs2(grid, crop, i , j, visisted) : bfs(grid, crop, i , j, visisted);                
            }
        }
        return total;
    }

    private static long bfs(char[][] grid, char crop, int i, int j, HashSet<String> visited) {
        int area = 0;
        long perimeter = 0;
        Deque<String> queue = new ArrayDeque<>();
        
        queue.add(i + "," + j); 
        visited.add(i + "," + j);
        
        int[][] directions = Coordinate.DIRECTIONS_WO_DIAGONALS;
        
        while (!queue.isEmpty()) {
            String value = queue.pollFirst();
            String[] values = value.split(",");
            int row = Integer.parseInt(values[0]);
            int col = Integer.parseInt(values[1]);
            area++;
            
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (!Utils.isInBounds(newCol, newRow, grid) || grid[newRow][newCol] != crop) {
                    perimeter++;
                    continue;
                }
                
                if (!visited.contains(newRow + "," + newCol)) {
                    queue.addLast(newRow + "," + newCol);
                    visited.add(newRow + "," + newCol);
                }
            }
        }
        
        return area * perimeter;
    }

    private static int bfs2(char[][] grid, char crop, int i, int j, HashSet<String> visited) {
       return 0;
    }
}
