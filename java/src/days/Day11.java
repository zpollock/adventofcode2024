package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class Day11 extends Day {
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
        LinkedList<Long> linkedInput = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            String[] input = line.split("\\s+");
            Arrays.stream(input).forEach(x -> linkedInput.add(Long.parseLong(x)));
        }

        ListIterator<Long> iterator = linkedInput.listIterator();
        while (iterator.hasNext()) {
            ret += dfs(new HashMap<>(), 0, iterator.next(), isPart2 ? 75 : 25);
        }

        return String.valueOf(ret);
    }



    private static long dfs(HashMap<String, Long> memo, int blinks, long stoneVal, int maxBlinks) {
        if (blinks == maxBlinks) {
            return 1; 
        }
    
        String key = stoneVal + "," + blinks;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
    
        long stones = 0;
        if (stoneVal == 0) {
            stones += dfs(memo, blinks + 1, 1, maxBlinks);
        } else if (String.valueOf(stoneVal).length() % 2 != 0) {
            stones += dfs(memo, blinks + 1, stoneVal * 2024, maxBlinks);
        } else {
            String val = String.valueOf(stoneVal);
            String valL = val.substring(0, val.length() / 2);
            String valR = val.substring(val.length() / 2);
    
            stones += dfs(memo, blinks + 1, Long.parseLong(valL), maxBlinks);
            stones += dfs(memo, blinks + 1, Long.parseLong(valR), maxBlinks);
        }
    
        memo.put(key, stones);
        return stones;
    }
    
}
