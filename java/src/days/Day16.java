package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import src.helpers.Coordinate;
import src.helpers.Utils;
import src.helpers.Coordinate.Direction;
import src.helpers.Position;

public class Day16 extends Day {
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
        ArrayList<String> inputList = new ArrayList<>();
        String line;
        long ret = 0;
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
        
        ret = dijkstra(grid, start, end, isPart2);
        return String.valueOf(ret);
    }    

    private static long dijkstra(char[][] grid, Position start, Position end, boolean isPart2) {
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
                long moveCost = 0;                
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
                
                if(currentPosition.direction != newPosition.direction) moveCost = 1000;
                long newCost = currentPosition.cost + moveCost + 1;
                newPosition.cost = newCost;
                if (newPosition.x == end.x && newPosition.y == end.y) {
                    if(!isPart2) return newPosition.cost;
                    totalMinCost = Math.min(totalMinCost, newPosition.cost);
                    Position temp = currentPosition;
                    while (temp != null) {
                        path.add(temp.y + "," + temp.x);
                        temp = temp.parent;
                    }                
                    continue;
                }
                
                if(grid[newY][newX] == '.' && (newCost <= minCost[newY][newX] || (isPart2 && newCost -1000 <= minCost[newY][newX]))) {
                    minCost[newY][newX] = newCost;
                    newPosition.parent = currentPosition;
                    pq.offer(newPosition);
                }
            }
        }

        return path.size() + 1;
    }    
}
