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

import src.helpers.Utils;

public class Day6 extends Day {
    @Override
    public String process(String inputFileName, boolean isPart2) {        
        Path filePath = Path.of("resources", inputFileName);
         try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {        
            return partOne(reader, isPart2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private static String partOne(BufferedReader reader, boolean isPart2) throws IOException {
        String line;

        ArrayList<String> gridString = new ArrayList<>();
        int lineCount = 0;
        int row = 0;
        int col = 0;
        int direction = 0;
        while ((line = reader.readLine()) != null) {
            gridString.add(line);
            if(line.contains("^")) {
                row = lineCount;
                col = line.indexOf("^"); 
                direction = 0;
            } else if (line.contains(">")) {
                row = lineCount;
                col = line.indexOf(">"); 
                direction = 1;
            } else if (line.contains("v")) {
                row = lineCount;
                col = line.indexOf("v"); 
                direction = 2;
            }  else if (line.contains("<")) {
                row = lineCount;
                col = line.indexOf("<"); 
                direction = 3;
            }
            lineCount++;
        }
        char[][] grid = Utils.convertToCharGrid(gridString);

        HashSet<String> visited = pathVisited(grid, row, col, direction);
        return isPart2 ? String.valueOf(partTwoHelper(grid, row, col, direction, visited)) : String.valueOf(visited.size());
    }

    private static HashSet<String> pathVisited(char[][] grid, int row, int col, int direction) {
        HashSet<String> visited = new HashSet<>();
        HashSet<String> visitedDirection = new HashSet<>();
        while(!visitedDirection.contains(row + "," + col + "," + direction)) {
            visited.add(row + "," + col);
            visitedDirection.add(row + "," + col + "," + direction);
            if(direction == 0) {//U
                if(!Utils.isInBounds(row -1, col, grid)) return visited;

                if(grid[row -1][col] != '#') {
                    row--;
                } else {
                    direction = 1;
                } 
            } else if (direction == 1) {//R
                if(!Utils.isInBounds(row, col+1, grid)) return visited;
                if(grid[row][col+1] != '#') {
                    col++;
                } else {
                    direction = 2;
                }
            } else if (direction == 2) {//D
                if(!Utils.isInBounds(row +1, col, grid)) return visited;
                if(grid[row + 1][col] != '#') {
                    row++;
                } else {
                    direction = 3;
                }
            } else if (direction == 3) {//L
                if(!Utils.isInBounds(row, col-1, grid)) return visited;
                if(grid[row][col-1] != '#') {
                    col--;
                } else {
                    direction = 0;
                }
            }
        }

        return null;
    }

    private static int partTwoHelper(char[][] grid, int row, int col, int direction, HashSet<String> visited) {
        int loops = 0;
        for (String entry : visited) {
            String[] coords = entry.split(",");
            int i = Integer.parseInt(coords[0]);
            int j = Integer.parseInt(coords[1]);
            if(grid[i][j] == '.') {
                grid[i][j] = '#';
                if(pathVisited(grid, row, col, direction) == null) {
                    loops++;
                }
                grid[i][j] = '.';
            }   
        }
        return loops;
    }
}
