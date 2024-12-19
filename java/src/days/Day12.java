package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
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
        long total2 = 0;
        HashSet<String> visisted = new HashSet<>();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                char crop = grid[i][j];
                if(visisted.contains(i+","+j)) continue;
                int[][] path = new int[grid.length+2][grid[0].length+2];
                long[] val = bfs(grid, crop, i , j, visisted, path);                
                total += val[0] * val[1];
                if(isPart2) {
                    int corners = countCorners(path);
                    total2 += corners * val[0];
                }
            }
        }
        return isPart2 ? total2 : total;
    }

    private static long[] bfs(char[][] grid, char crop, int i, int j, HashSet<String> visited, int[][] path) {        
        int area = 0;
        long perimeter = 0;
        Deque<String> queue = new ArrayDeque<>();
        
        queue.add(i + "," + j); 
        visited.add(i + "," + j);
        path[i+1][j+1] = 1;
        
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
                    path[newRow+1][newCol+1] = 1;
                }
            }
        }
        
        return new long[]{area, perimeter};
    }

    public static int countCorners(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int corners = 0;
    
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {                
                int temp = corners;
                if (grid[i][j] == 1) {
                    if (grid[i-1][j] == 0 && grid[i][j-1] == 0) corners++; //top-left
                    if (grid[i-1][j] == 0 && grid[i][j+1] == 0) corners++; //top-right
                    if (grid[i+1][j] == 0 && grid[i][j-1] == 0) corners++; //bottom-left
                    if (grid[i+1][j] == 0 && grid[i][j+1] == 0) corners++; //bottom-right
                    if(temp != corners) {
                        System.out.println("hi");
                    }
                }
            }
        }

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (grid[i][j] == 0) {
                    if (grid[i-1][j] == 1 && grid[i][j-1] == 1 && grid[i-1][j-1] == 1) corners++; //top-left
                    if (grid[i-1][j] == 1 && grid[i][j+1] == 1 && grid[i-1][j+1] == 1) corners++; //top-right
                    if (grid[i+1][j] == 1 && grid[i][j-1] == 1 && grid[i+1][j-1] == 1) corners++; //bottom-left
                    if (grid[i+1][j] == 1 && grid[i][j+1] == 1 && grid[i+1][j+1] == 1) corners++; //bottom-right
                }
            }
        }
        return corners;
    }         
}
