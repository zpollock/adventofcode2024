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
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;

import src.helpers.Coordinate;
import src.helpers.Coordinate.Direction;
import src.helpers.Position;
import src.helpers.Paths;
import src.helpers.Utils;

public class Day20 extends Day {
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
        String line;
        int ret = 0;
        ArrayList<String> inputList = new ArrayList<>();
        Position start = new Position();
        Position end = new Position();

        int row = 0;
        while ((line = reader.readLine()) != null) {
            inputList.add(line);       
            if(line.contains("S")) {
                start.y = row;
                start.x = line.indexOf("S");
            }
            if(line.contains("E")) {
                end.y = row;
                end.x = line.indexOf("E");
            }
            row++;
        }

        char[][] grid = Utils.convertToCharGrid(inputList);
        int[][] moveCheatSquares = isPart2 ? generateMoveSquares(6) : generateMoveSquares(2);
        int timeToBeat = isPart2 ? 
            (row > 15 ? 100 : 52) : 
            (row > 15 ? 100 : 6);
        ret = pathWithCheats(grid, start, end, moveCheatSquares, timeToBeat);
        return String.valueOf(ret);
    }     
    
    
    private static int pathWithCheats(char[][] grid, Position start, Position end, int[][] moveCheatSquares, int timeToBeat) {
        HashMap<String, Long> path = Paths.dijkstra(grid, start, end, Coordinate.DIRECTIONS_WO_DIAGONALS);
        HashSet<String> validCheats = new HashSet<>();
    
        for (Map.Entry<String, Long> entry : path.entrySet()) {
            String[] coords = entry.getKey().split(",");
            int y = Integer.parseInt(coords[0]);
            int x = Integer.parseInt(coords[1]);
    
            if ((x == start.x && y == start.y) || (x == end.x && y == end.y)) {
                continue;
            }
    
            long currentDistance = entry.getValue();
    
            for (int[] move : moveCheatSquares) {
                int newY = y + move[0];
                int newX = x + move[1];
                
                if (!Utils.isInBounds(newY, newX, grid)) {
                    continue;
                }
                
                String newPos = newY + "," + newX;
                if (!path.containsKey(newPos)) {
                    continue;
                }
    
                long targetDistance = path.get(newPos);
                long manhattanDistance = Math.abs(newY - y) + Math.abs(newX - x);
                long cheatDistance = currentDistance + manhattanDistance;
    
                if (targetDistance - cheatDistance >= timeToBeat) {
                    validCheats.add(y + "," + x + "-" + newPos);
                }
            }
        }
    
        return validCheats.size();
    }
    

    private static int[][] generateMoveSquares(int numMoves) {
        List<int[]> moves = new ArrayList<>();
        for (int dy = -numMoves; dy <= numMoves; dy++) {
            for (int dx = -numMoves; dx <= numMoves; dx++) {
                if (Math.abs(dy) + Math.abs(dx) == numMoves) {
                    moves.add(new int[]{dy, dx});
                }
            }
        }
        
        return moves.toArray(new int[0][]);
    }       
}
