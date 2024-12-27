package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day24 extends Day {
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
        
        LinkedHashMap<String, Integer> wiresMap = new LinkedHashMap<>();
        ArrayList<String[]> instructions = new ArrayList<>();

        boolean isWires = true;
        while ((line = reader.readLine()) != null) {
            if(line.isEmpty()) {
                isWires = false;
                continue;
            }
            if(isWires) {
                String[] input = line.split(":");
                wiresMap.put(input[0], Integer.parseInt(input[1].trim()));
            } else {
                String[] input = line.split("\\s+");
                instructions.add(new String[]{input[0], input[1], input[2], input[4]});
                if(input[4].startsWith("z")) wiresMap.put(input[4], 0);
            }
        }

        //if (isPart2) return getMovedItems(instructions);
        ret = partOneHelper(wiresMap, instructions);
        return String.valueOf(ret);
    }

    private static long partOneHelper(LinkedHashMap<String, Integer> wiresMap, ArrayList<String[]> instructions) {
        Deque<String[]> instructionsQueue = new ArrayDeque<>();
        for(String[] instruction : instructions) {
            instructionsQueue.add(instruction);
        }
        while(!instructionsQueue.isEmpty()) {
            String[] instruction = instructionsQueue.poll();
            if(!wiresMap.containsKey(instruction[0])){
                instructionsQueue.offer(instruction);
                continue;
            }
            if(!wiresMap.containsKey(instruction[2])){
                instructionsQueue.offer(instruction);
                continue;
            }
            int input1 = wiresMap.get(instruction[0]);
            int input2 = wiresMap.get(instruction[2]);
            int updatedValue = 0;
            switch (instruction[1]) {
                case "AND":
                    updatedValue = input1 & input2;
                    break;
                case "OR":
                    updatedValue = input1 | input2;
                    break;  
                case "XOR":
                    updatedValue = input1 ^ input2;
                    break;              
                default:
                    break;
            }
            wiresMap.put(instruction[3], updatedValue);
        }

        String binaryString = processWires(wiresMap);
        long intValue = Long.parseLong(binaryString, 2);
        return intValue;

    }

     private static String processWires(LinkedHashMap<String, Integer> wiresMap) {
        List<String> sortedKeys = wiresMap.keySet().stream()
                .filter(key -> key.startsWith("z"))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        StringBuilder result = new StringBuilder();
        for (String key : sortedKeys) {
            Integer value = wiresMap.get(key);
            result.append(value % 2);
        }

        return result.toString();
    }
}
