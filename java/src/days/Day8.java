package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import src.helpers.*;

public class Day8 extends Day {
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

        ArrayList<String> gridString = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            gridString.add(line);     
        }

        HashMap<Character, ArrayList<Coordinate>> coordsPerChar = new HashMap<>();
        

        char[][] grid = Utils.convertToCharGrid(gridString);
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] != '.') {
                    coordsPerChar.putIfAbsent(grid[i][j], new ArrayList<>());
                    coordsPerChar.get(grid[i][j]).add(new Coordinate(i, j));
                }
            }
        }

        ret = isPart2 ? partTwoHelper(grid, coordsPerChar): partOneHelper(grid, coordsPerChar);
        return String.valueOf(ret);
    }

    private static int partOneHelper(char[][] grid, HashMap<Character, ArrayList<Coordinate>> coordsPerChar) {
        int antinodes = 0;
        for(Character c : coordsPerChar.keySet()) {
            ArrayList<Coordinate> coords = coordsPerChar.get(c);
            for(int i = 0; i < coords.size() -1; i++) {
                for(int j = i+1; j < coords.size(); j++) {
                    Coordinate a = coords.get(i);
                    Coordinate b = coords.get(j);
                    int distRow = b.x - a.x;
                    int disCol = b.y - a.y;

                    int x = b.x + distRow; 
                    int y = b.y + disCol;
                    if(Utils.isInBounds(x, y, grid) && grid[x][y] != '#') {
                        antinodes++;
                        grid[x][y] = '#';
                    }

                    x = a.x - distRow; 
                    y = a.y - disCol;
                    if(Utils.isInBounds(x, y, grid) && grid[x][y] != '#') {
                        antinodes++;
                        grid[x][y] = '#';
                    }

                }
            }
        }
        return antinodes;
    }

    private static int partTwoHelper(char[][] grid, HashMap<Character, ArrayList<Coordinate>> coordsPerChar) {
        int antinodes = 0;
        for (Character c : coordsPerChar.keySet()) {
            ArrayList<Coordinate> coords = coordsPerChar.get(c);
            for (int i = 0; i < coords.size() - 1; i++) {
                for (int j = i + 1; j < coords.size(); j++) {
                    Coordinate a = coords.get(i);
                    Coordinate b = coords.get(j);
                    int distRow = b.x - a.x;
                    int distCol = b.y - a.y;
    
                    for (int x = b.x, y = b.y; Utils.isInBounds(x, y, grid); x += distRow, y += distCol) {
                        if (Utils.isInBounds(x, y, grid) && grid[x][y] != '#') {
                            grid[x][y] = '#';
                            antinodes++;
                        }
                    }
    
                    for (int x = a.x, y = a.y; Utils.isInBounds(x, y, grid); x -= distRow, y -= distCol) {
                        if (Utils.isInBounds(x, y, grid) && grid[x][y] != '#') {
                            grid[x][y] = '#';
                            antinodes++;
                        }
                    }
                }
            }
        }
    
        return antinodes;
    }

}
