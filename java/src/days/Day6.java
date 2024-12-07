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
import java.util.Map;

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
        int row = 0, col = 0, direction = 0;

        Map<Character, Integer> directionMap = Map.of(
            '^', 0,
            '>', 1,
            'v', 2,
            '<', 3
        );

        int lineCount = 0;
        while ((line = reader.readLine()) != null) {
            gridString.add(line);
            for (Map.Entry<Character, Integer> entry : directionMap.entrySet()) {
                char symbol = entry.getKey();
                if (line.contains(String.valueOf(symbol))) {
                    row = lineCount;
                    col = line.indexOf(symbol);
                    direction = entry.getValue();
                    break;
                }
            }
            lineCount++;
        }
        char[][] grid = Utils.convertToCharGrid(gridString);

        HashSet<String> visited = new HashSet<>();
        HashMap<String, String> visitedPath = new HashMap<>();
        pathVisited(grid, row, col, direction, visited, visitedPath);
        return isPart2 ? String.valueOf(partTwoHelper(grid, row, col, direction, visited, visitedPath)) : String.valueOf(visited.size());
    }

    private static boolean pathVisited(char[][] grid, int row, int col, int direction, HashSet<String> visited, HashMap<String, String> visitedPath) {
        HashSet<String> visitedDirection = new HashSet<>();
        while(!visitedDirection.contains(row + "," + col + "," + direction)) {
            visited.add(row + "," + col);
            visitedDirection.add(row + "," + col + "," + direction);
            if(direction == 0) {//U
                if(!Utils.isInBounds(row -1, col, grid)) return true;

                if(grid[row -1][col] != '#') {
                    String key = (row-1)+","+(col);
                    if(visitedPath != null && !visitedPath.containsKey(key)) {
                        visitedPath.put(key, row + "," + col + "," + direction);
                    }
                    row--;
                } else {
                    direction = 1;
                } 
            } else if (direction == 1) {//R
                if(!Utils.isInBounds(row, col+1, grid)) return true;
                if(grid[row][col+1] != '#') {
                    String key = (row)+","+(col+1);
                    if(visitedPath != null && !visitedPath.containsKey(key)) {
                        visitedPath.put(key, row + "," + col + "," + direction);
                    }
                    col++;
                } else {
                    direction = 2;
                }
            } else if (direction == 2) {//D
                if(!Utils.isInBounds(row +1, col, grid)) return true;
                if(grid[row + 1][col] != '#') {
                    String key = (row+1)+","+(col);
                    if(visitedPath != null && !visitedPath.containsKey(key)) {
                        visitedPath.put(key, row + "," + col + "," + direction);
                    }
                    row++;
                } else {
                    direction = 3;
                }
            } else if (direction == 3) {//L
                if(!Utils.isInBounds(row, col-1, grid)) return true;
                if(grid[row][col-1] != '#') {
                    String key = (row)+","+(col-1);
                    if(visitedPath != null && !visitedPath.containsKey(key)) {
                        visitedPath.put(key, row + "," + col + "," + direction);
                    }
                    col--;
                } else {
                    direction = 0;
                }
            }
        }

        return false;
    }

    private static int partTwoHelper(char[][] grid, int row, int col, int direction, HashSet<String> visited, HashMap<String, String> visitedPath) {
        int loops = 0;
        for (String entry : visitedPath.keySet()) {
            String[] obstruction_coords = entry.split(",");
            int obstruction_i = Integer.parseInt(obstruction_coords[0]);
            int obstruction_j = Integer.parseInt(obstruction_coords[1]);
            grid[obstruction_i][obstruction_j] = '#';
            String[] coords = visitedPath.get(entry).split(",");
            if(!pathVisited(grid, Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]), new HashSet<>(), null)) {
                loops++;
            }            
            grid[obstruction_i][obstruction_j] = '.';
        }
        return loops;
    }
}
