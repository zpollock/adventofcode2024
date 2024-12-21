package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Day19 extends Day {
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
        String[] patterns = null;
        ArrayList<String> designs = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            if(line.contains(",")) {
                patterns = line.split("\\s*,\\s*");
            } else if (line.isEmpty()) {
                continue;
            } else {
                designs.add(line);
            }
            
        }

        ret = (isPart2) ? partTwoHelper(patterns, designs) : partOneHelper(patterns, designs);
        return String.valueOf(ret);
    }

    private static int partOneHelper(String[] patterns, ArrayList<String> designs) {
        int validDesigns = 0;

        for(String design : designs) {
            validDesigns += isValidDesign(design, patterns, "");
        }
        return validDesigns;
    }
    
    private static int isValidDesign(String design, String[] patterns, String matched) {
        if (matched.equals(design)) return 1;
        int index = matched.length();
    
        for (String pattern : patterns) {
            if (index + pattern.length() <= design.length() && design.substring(index, index + pattern.length()).equals(pattern)) {
                if (isValidDesign(design, patterns, matched + pattern) == 1) return 1;                
            }
        }
    
        return 0;        
    }


    private static long partTwoHelper(String[] patterns, ArrayList<String> designs) {
        long count = 0;

        for(String design : designs) {
            count += numValidDesigns(design, patterns, 0, new HashMap<>());
        }
        return count;
    }

    private static long numValidDesigns(String design, String[] patterns, int index, Map<Integer, Long> memo) {
        if (memo.containsKey(index)) return memo.get(index);
        if (index == design.length()) return 1;
    
        long count = 0;
    
        for (String pattern : patterns) {
            if (index + pattern.length() <= design.length() &&
                design.substring(index, index + pattern.length()).equals(pattern)) {
                count += numValidDesigns(design, patterns, index + pattern.length(), memo);
            }
        }
    
        memo.put(index, count);
        return count;
    }
}
