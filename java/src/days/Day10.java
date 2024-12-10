package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

import src.helpers.Utils;

public class Day10 extends Day {
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
        int ret = 0;
        String line;        
        ArrayList<String> input = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            input.add(line);                 
        }

        int[][] trailmap = Utils.convertToIntGrid(input);

        for(int i = 0; i < trailmap.length; i++) {
            for(int j = 0; j < trailmap[0].length; j++) {
                if(trailmap[i][j] == 0) {
                    int[] stepsCount = {0};
                    bfs(trailmap, i, j, stepsCount, isPart2 ? null : new HashSet<String>());
                    ret += stepsCount[0];
                }
            }
        }

        return String.valueOf(ret);
    }

    private static void bfs(int[][] trailmap, int row, int col, int[] stepsCount, HashSet<String> visited) 
    {

        if(visited != null) {
            if(visited.contains(row+","+col)) return;
            visited.add(row+","+col);
        }

        if(trailmap[row][col] == 9) {
            stepsCount[0] += 1;
            return;
        }

        if(Utils.isInBounds(row - 1, col, trailmap) && trailmap[row-1][col] == trailmap[row][col] + 1) {
            bfs(trailmap, row -1, col, stepsCount, visited);
        }
        if(Utils.isInBounds(row + 1, col, trailmap) && trailmap[row+1][col] == trailmap[row][col] + 1) {
            bfs(trailmap, row + 1, col, stepsCount, visited);
        }
        if(Utils.isInBounds(row, col-1, trailmap) && trailmap[row][col-1] == trailmap[row][col] + 1) {
            bfs(trailmap, row, col-1, stepsCount, visited);
        }
        if(Utils.isInBounds(row, col+1, trailmap) && trailmap[row][col+1] == trailmap[row][col] + 1) {
            bfs(trailmap, row, col+1, stepsCount, visited);
        }
    }
}
