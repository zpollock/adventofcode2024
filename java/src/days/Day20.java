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
import java.util.PriorityQueue;
import java.util.Set;

import src.helpers.Coordinate;
import src.helpers.Coordinate.Direction;
import src.helpers.Position;
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

        ret = (isPart2) ? partTwoHelper(null) : partOneHelper(grid, start, end);
        return String.valueOf(ret);
    }

    private static int partOneHelper(char[][] grid, Position start, Position end) {
        int shortestPath = dijkstra(grid, start, end).size();  
        int timeToBeat = shortestPath > 100 ? shortestPath : 84;      
        ArrayList<Integer> cheatPaths = dijkstraWithCheats(grid, start, end, timeToBeat);
        return cheatPaths.size();
    }


    private static int partTwoHelper(int[] line) {
        return 0;
    }

    private static ArrayList<Integer> dijkstraWithCheats(char[][] grid, Position start, Position end, int timeToBeat) {
        ArrayList<Integer> cheatPaths = new ArrayList<>();
        int rows = grid.length;
        int cols = grid[0].length;
    
        //Set<String> visited = new HashSet<>();
        Set<String> uniquePaths = new HashSet<>();
        
        long[][] minCost = new long[rows][cols];
        for (long[] row : minCost) {
            Arrays.fill(row, Long.MAX_VALUE);            
        }
        minCost[start.y][start.x] = 0;
    
        PriorityQueue<Position> pq = new PriorityQueue<>();
        start.cost = 0;
        start.direction = Direction.RIGHT;
        start.extra = 2; 
        pq.offer(start);
    
        while (!pq.isEmpty()) {
            Position current = pq.poll();
            
            if (current.cost > timeToBeat) {
                continue;
            }
            
            if (current.x == end.x && current.y == end.y) {
                StringBuilder pathKey = new StringBuilder();
                Position temp = current;
                int pathLength = 0;
                
                while (temp != null) {
                    pathKey.insert(0, String.format(",%d,%d", temp.x, temp.y));
                    pathLength++;
                    temp = temp.parent;
                }
                
                if (uniquePaths.add(pathKey.toString())) {
                    cheatPaths.add(pathLength - 1);
                }
                continue;
            }
    
            for (int[] d : Coordinate.DIRECTIONS_WO_DIAGONALS) {         
                int newX = current.x + d[0];
                int newY = current.y + d[1];
                
                if (!Utils.isInBounds(newY, newX, grid)) {
                    continue;
                }
    
                Position next = new Position(newX, newY);
                next.parent = current;
                next.cost = current.cost + 1;
                next.extra = current.extra;
    
                if (d[0] == 1 && d[1] == 0) next.direction = Direction.RIGHT;
                if (d[0] == -1 && d[1] == 0) next.direction = Direction.LEFT;
                if (d[0] == 0 && d[1] == 1) next.direction = Direction.UP;
                if (d[0] == 0 && d[1] == -1) next.direction = Direction.DOWN;
    
                if (isOppositeDirection(current.direction, next.direction)) {
                    continue;
                }
    
                boolean isValidMove = false;
                if (grid[newY][newX] != '#') {
                    isValidMove = true;
                    if(next.extra == 1) next.extra = 0;
                } else if (next.extra > 0) {
                    isValidMove = true;
                    next.extra--; 
                }
    
                if (isValidMove) {
                    Position temp = next.parent;
                    while (temp != null) {
                        if (temp.x == next.x && temp.y == next.y) {
                            isValidMove = false;
                            break;
                        }
                        temp = temp.parent;
                    }
                }
    
                if (isValidMove && next.cost <= minCost[newY][newX]) {
                    minCost[newY][newX] = next.cost;
                    pq.offer(next);
                }
            }
        }
    
        return cheatPaths;
    }

    private static boolean isOppositeDirection(Direction dir1, Direction dir2) {
        return (dir1 == Direction.RIGHT && dir2 == Direction.LEFT) ||
            (dir1 == Direction.LEFT && dir2 == Direction.RIGHT) ||
            (dir1 == Direction.UP && dir2 == Direction.DOWN) ||
            (dir1 == Direction.DOWN && dir2 == Direction.UP);
    }

    private static HashSet<String> dijkstra(char[][] grid, Position start, Position end) {
        HashSet<String> path = new HashSet<>();
        int rows = grid.length;
        int cols = grid[0].length;

        long[][] minCost = new long[rows][cols];
        for (long[] row : minCost) {
            Arrays.fill(row, Long.MAX_VALUE);            
        }
        minCost[start.y][start.x] = 0;

        PriorityQueue<Position> pq = new PriorityQueue<>();
        start.cost = 0;
        start.direction = Direction.RIGHT;
        pq.offer(start);

        while (!pq.isEmpty()) {
            Position currentPosition = pq.poll();         
                        
            if (currentPosition.cost > minCost[currentPosition.y][currentPosition.x]) continue;

            for(int[] d : Coordinate.DIRECTIONS_WO_DIAGONALS) {         
                int newX = currentPosition.x + d[0];
                int newY = currentPosition.y + d[1];
                Position newPosition = new Position();
                newPosition.x = newX;
                newPosition.y = newY;

                if(d[0] == 1 && d[1] == 0) newPosition.direction = Direction.RIGHT;
                if(d[0] == -1 && d[1] == 0) newPosition.direction = Direction.LEFT;
                if(d[0] == 0 && d[1] == 1) newPosition.direction = Direction.UP;
                if(d[0] == 0 && d[1] == -1) newPosition.direction = Direction.DOWN;

                if (isOppositeDirection(currentPosition.direction, newPosition.direction)) {
                    continue;
                }
                
                long newCost = currentPosition.cost + 1;
                newPosition.cost = newCost;
                if (newPosition.x == end.x && newPosition.y == end.y) {
                    Position temp = currentPosition;
                    while (temp != null) {
                        path.add(temp.y + "," + temp.x);
                        temp = temp.parent;
                    }                
                    return path;
                }
                
                if(Utils.isInBounds(newY, newX, grid) && grid[newY][newX] == '.' && newCost < minCost[newY][newX]) {
                    minCost[newY][newX] = newCost;
                    newPosition.parent = currentPosition;
                    pq.offer(newPosition);
                }
            }
        }

        return null;
    }    
}
