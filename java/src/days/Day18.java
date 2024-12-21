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

import src.helpers.Coordinate;
import src.helpers.Utils;
import src.helpers.Coordinate.Direction;

public class Day18 extends Day {
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
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String[] input = line.split(",");
            coordinates.add(new Coordinate(Integer.parseInt(input[0]),Integer.parseInt(input[1])));
        }

        int numBytes = coordinates.size() < 1024 ? 12 : 1024;
        int gridSize = coordinates.size() < 1024 ? 7 : 71;
        char[][] grid = new char[gridSize][gridSize];        
        for (char[] row : grid) {
            Arrays.fill(row, '.'); 
        }
        for(int i = 0; i < numBytes; i++) {
            Coordinate coord = coordinates.get(i);
            grid[coord.y][coord.x] = '#';
        }
        Position startPosition = new Position();
        startPosition.x = 0;
        startPosition.y = 0;
        Position endPosition = new Position();
        endPosition.x = grid[0].length - 1;
        endPosition.y = grid.length - 1;
        if (isPart2) return partTwoHelper(coordinates, numBytes, grid, startPosition, endPosition);
        ret =  partOneHelper(coordinates, numBytes, grid, startPosition, endPosition);
        return String.valueOf(ret);
    }

    private static int partOneHelper(ArrayList<Coordinate> coordinates, int numBytes, char[][] grid, Position startPosition, Position endPosition) {        
        return dijkstra(grid, startPosition, endPosition).size();
    }


    private static String partTwoHelper(ArrayList<Coordinate> coordinates, int numBytes, char[][] grid, Position startPosition, Position endPosition) {   
        HashSet<String> path = dijkstra(grid, startPosition, endPosition);     
        for(int i = numBytes; i < coordinates.size(); i++) {
            Coordinate coord = coordinates.get(i);
            grid[coord.y][coord.x] = '#';
            if(path.contains(coord.y + "," + coord.x)) {
                path = dijkstra(grid, startPosition, endPosition);     
                if(path == null) return coord.x + "," + coord.y;
            }
        }
        return null;
    }

    private static HashSet<String> dijkstra(char[][] grid, Position start, Position end) {
        HashSet<String> path = new HashSet<>();
        long totalMinCost = Long.MAX_VALUE;
        int rows = grid.length;
        int cols = grid[0].length;

        long[][] minCost = new long[rows][cols];
        for (long[] row : minCost) {
            Arrays.fill(row, Long.MAX_VALUE);            
        }

        PriorityQueue<Position> pq = new PriorityQueue<>();
        start.cost = 0;
        start.direction = Direction.RIGHT;
        pq.offer(start);

        while (!pq.isEmpty()) {
            Position currentPosition = pq.poll();         
                        
            if (currentPosition.cost > minCost[currentPosition.y][currentPosition.x] || currentPosition.cost > totalMinCost) continue;

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

                if(currentPosition.direction == Direction.RIGHT && newPosition.direction == Direction.LEFT) continue;
                if(currentPosition.direction == Direction.LEFT && newPosition.direction == Direction.RIGHT) continue;
                if(currentPosition.direction == Direction.UP && newPosition.direction == Direction.DOWN) continue;
                if(currentPosition.direction == Direction.DOWN && newPosition.direction == Direction.UP) continue;
                
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


class Position extends Coordinate implements Comparable<Position>{
    public Position parent = null;
    public Direction direction;
    public long cost;

    @Override
    public int compareTo(Position other) {
        return Long.compare(this.cost, other.cost);
    }
}
