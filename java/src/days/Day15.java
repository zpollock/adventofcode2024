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

import src.helpers.Coordinate;
import src.helpers.Utils;

public class Day15 extends Day {
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
        long ret = 0;
        ArrayList<String> gridStrings = new ArrayList<>();
        String moves = "";
        Coordinate rPos = null;

        boolean isGrid = true;
        int row = 0;
        while ((line = reader.readLine()) != null) {
            if(line.isEmpty()) {
                isGrid = false;
                continue;
            }
            if(isGrid) {                
                if(isPart2) {
                    line = line.replace("#", "##");
                    line = line.replace("O", "[]");
                    line = line.replace(".", "..");
                    line = line.replace("@", "@.");
                }
                if(line.contains("@")) {
                    rPos = new Coordinate(line.indexOf("@"), row);
                }
                gridStrings.add(line);
                row++;
            } else {
                moves += line.trim();
            }
        }

        char[][] grid = Utils.convertToCharGrid(gridStrings);
        //Utils.printGrid(grid);
        for(char move : moves.toCharArray()) {
            if(isPart2) makeMove2(grid, move, rPos); 
            else makeMove(grid, move, rPos);
        }

        ret = isPart2 ? gridSum(grid, '[') : gridSum(grid, 'O');
        return String.valueOf(ret);
    }

    private static void makeMove2(char[][] grid, char move, Coordinate rPos) {

        int dx = 0, dy = 0;
        switch (move) {
            case '>': dx = 1; break; 
            case '<': dx = -1; break;
            case '^': dy = -1; break;
            case 'v': dy = 1; break;
            default: return;
        }
    
        int newX = rPos.x + dx;
    
        if (newX < 0 || newX >= grid[0].length) return;        
        if (grid[rPos.y][newX] == '#') return;        
            
        int pushX = newX;
        
        while (pushX >= 0 && pushX < grid[0].length && (grid[rPos.y][pushX] == '[' || grid[rPos.y][pushX] == ']')) {        
            pushX += dx;
        }
    
        if (pushX < 0 || pushX >= grid[0].length || (dx  != 0 && grid[rPos.y][pushX] != '.')) {
            return; 
        }
    
        int boxX = pushX - dx;
        while (boxX != newX - dx) {
            grid[rPos.y][pushX] = grid[rPos.y][pushX-dx];
            pushX -= dx;            
            boxX -= dx;
        }

        if (dy != 0 && grid[rPos.y + dy][rPos.x] != '.') {
            int xAdjust = rPos.x;
            if(grid[rPos.y + dy][rPos.x] == ']') {
                xAdjust--;
            } 
            if(canMoveAllBoxes(xAdjust, rPos.y, dy, grid)) {
                moveAllBoxes(xAdjust, rPos.y + dy, dy, grid);
            }
        }
    
        if(dx != 0 || (dy != 0 && grid[rPos.y + dy][rPos.x] == '.')) {
            grid[rPos.y][rPos.x] = '.';
            grid[rPos.y + dy][newX] = '@';
            rPos.x = newX;
            rPos.y = rPos.y + dy;
        }
    }

    private static boolean canMoveAllBoxes(int x, int y, int dy, char[][] grid) {
        if(grid[y+dy][x] == '#' || grid[y+dy][x+1] == '#') return false;
        if(grid[y+dy][x] == '.' && grid[y+dy][x+1] == '.') return true;
        
        boolean canMove = true;
        if(grid[y+dy][x] == '[') canMove &= canMoveAllBoxes(x, y+dy, dy, grid);            
        if(grid[y+dy][x] == ']') canMove &= canMoveAllBoxes(x-1, y+dy, dy, grid);            
        if(grid[y+dy][x+1] == '[') canMove &= canMoveAllBoxes(x+1, y+dy, dy, grid);                    
        return canMove;
    }

    public static void moveAllBoxes(int x, int y, int dy, char[][] grid) {
        if(grid[y+dy][x] != '.' || grid[y+dy][x+1] != '.') {            
            if(grid[y+dy][x] == ']') moveAllBoxes(x-1, y+dy, dy, grid);            
            if(grid[y+dy][x] == '[') moveAllBoxes(x, y+dy, dy, grid);            
            if(grid[y+dy][x+1] == '[') moveAllBoxes(x+1, y+dy, dy, grid);   
        }

        grid[y+dy][x] = '[';
        grid[y+dy][x+1] = ']';
        grid[y][x] = '.';
        grid[y][x+1] = '.';                    
        
    }

    private static void makeMove(char[][] grid, char move, Coordinate rPos) {
        int dx = 0, dy = 0;
        switch (move) {
            case '>': dx = 1; break; 
            case '<': dx = -1; break;
            case '^': dy = -1; break;
            case 'v': dy = 1; break;
            default: return;
        }
    
        int newX = rPos.x + dx;
        int newY = rPos.y + dy;
    
        if (newX < 0 || newX >= grid[0].length || newY < 0 || newY >= grid.length) return;        
        if (grid[newY][newX] == '#') return;        
            
        int pushX = newX;
        int pushY = newY;
        while (pushX >= 0 && pushX < grid[0].length && pushY >= 0 && pushY < grid.length && grid[pushY][pushX] == 'O') {
            pushX += dx;
            pushY += dy;
        }
    
        if (pushX < 0 || pushX >= grid[0].length || pushY < 0 || pushY >= grid.length || grid[pushY][pushX] != '.') {
            return; 
        }
    
        grid[pushY][pushX] = 'O';
        grid[rPos.y][rPos.x] = '.';
        grid[newY][newX] = '@';
        rPos.x = newX;
        rPos.y = newY;
    
    }
    
    private static long gridSum(char[][] grid, char boxChar) {
        long ret = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == boxChar) {
                    ret += i*100 + j;
                }    
            }            
        }
        return ret;
    }
}
