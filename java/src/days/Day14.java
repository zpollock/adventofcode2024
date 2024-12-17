package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 extends Day {
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
        String regex = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)";
        Pattern pattern = Pattern.compile(regex);
        ArrayList<Integer[]> robots = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            Integer[] robot = new Integer[4];
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                robot[0] = Integer.parseInt(matcher.group(1));
                robot[1] = Integer.parseInt(matcher.group(2));
                robot[2] = Integer.parseInt(matcher.group(3));
                robot[3] = Integer.parseInt(matcher.group(4));
                robots.add(robot);
            }            
        }

        int rows = robots.size() <= 12 ? 7 : 103;
        int cols = robots.size() <= 12 ? 11 : 101;
        
        HashMap<Integer, Integer> quadMap = new HashMap<>();
        quadMap.put(0, 0);
        quadMap.put(1, 0);
        quadMap.put(2, 0);
        quadMap.put(3, 0);
        quadMap.put(4, 0);


        int iterations = isPart2 ? 101 * 103 : 100;
        for(int seconds = 0; seconds < iterations; seconds++) {
            for(Integer[] robot : robots) {
                makeMove(robot, rows, cols);
                if(seconds == 99) {
                    int quad = determineQuadrant(robot, rows, cols);
                    quadMap.replace(quad, quadMap.get(quad) + 1);
                }                
            }
            if(isPart2) {
                //34 below comes from observation by noticing the pictures seem to be already aligned by column
                if ((seconds + 34)  % 101 == 0 && isChristmasTree(robots, seconds + 1)) {
                    return String.valueOf(seconds + 1);
                }
            }
        }

        ret = quadMap.get(1) * quadMap.get(2) * quadMap.get(3) * quadMap.get(4);
        return String.valueOf(ret);
    }

    private static void makeMove(Integer[] robot, int rows, int cols) {
        robot[0] = (robot[0] + robot[2] + cols) % cols;
        robot[1] = (robot[1] + robot[3] + rows) % rows;
    }

    private static int determineQuadrant(Integer[] robot, int rows, int cols) {
        int midX = cols / 2;
        int midY = rows / 2;
        
        int x = robot[0];
        int y = robot[1];
        
        if (x == midX || y == midY) {
            return 0; //middle
        }
        
        if (x < midX && y < midY) {
            return 1; // bl
        } else if (x > midX && y < midY) {
            return 2; // br
        } else if (x < midX && y > midY) {
            return 3; // tl
        } else {
            return 4; // tr
        }
    }


    private static boolean isChristmasTree(ArrayList<Integer[]> robots, int seconds) {
        char[][] grid = new char[103][101];
        for(Integer[] robot : robots) {
            grid[robot[1]][robot[0]] = '*';
        }
        for(int i = 0; i < grid.length; i++) {
            int countStar = 0;
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == '*') {
                    countStar++;
                    //System.out.print(" * ");
                    //First found the picture by printing it, then changed to a computational arguement. 
                    if(countStar >= 10) return true;  
                } else {
                    countStar = 0;
                    //System.out.print("   ");                    
                }
            }
        }
        
        return false;
    }
}

