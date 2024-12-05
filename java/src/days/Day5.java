package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Day5 extends Day {
    @Override
    public String process(String inputFileName, boolean isPart2) {        
        Path filePath = Path.of("resources", inputFileName);
         try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {        
            return partOne(reader, isPart2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private static String partOne(BufferedReader reader, boolean isPart2) throws IOException {
        String line;
        int ret = 0;

        HashMap<Integer, HashSet<Integer>> gtMap = new HashMap<>();
        ArrayList<Integer[]> updates = new ArrayList<>();

        boolean firstSection = true;
        while ((line = reader.readLine()) != null) {
            if(line.isEmpty()) {
                firstSection = false;
                continue;
            }
            if(firstSection) {
                String[] input = line.split("\\|");
                int[] intInput = Arrays.stream(input)
                                .mapToInt(Integer::parseInt)
                                .toArray();
                gtMap.putIfAbsent(intInput[1], new HashSet<>());
                gtMap.get(intInput[1]).add(intInput[0]);
            } else {
                String[] input = line.split(",");
                updates.add(Arrays.stream(input)
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new));
            }
                             
        }

        if(isPart2) return partTwoHelper(updates, gtMap);
        ret += partOneHelper(updates, gtMap);   

        return String.valueOf(ret);
    }

    private static int partOneHelper(ArrayList<Integer[]> updates, HashMap<Integer, HashSet<Integer>> gtMap) {
        int middleSum = 0;

        for(Integer[] updateArray : updates) {        
            if(isValid(updateArray, gtMap, 0) == -1) {
                int middleValue = updateArray[updateArray.length / 2];
                middleSum += middleValue;
            }
        }

        return middleSum;
    }

    private static int isValid(Integer[] updateArray, HashMap<Integer, HashSet<Integer>> gtMap, int startIndex) {
        for(int i = startIndex; i < updateArray.length; i++) {
            HashSet<Integer> gts = gtMap.get(updateArray[i]);
            for(int j = i; gts != null && j < updateArray.length; j++) {
                if(gts.contains(updateArray[j])) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static String partTwoHelper(ArrayList<Integer[]> updates, HashMap<Integer, HashSet<Integer>> gtMap) {
        int middleSum = 0;

        for(Integer[] updateArray : updates) {        
            if (isValid(updateArray, gtMap, 0) == -1) continue;
            int startIndex = 0;
            while((startIndex = isValid(updateArray, gtMap, startIndex)) != -1) {
                makeValid(updateArray, gtMap, startIndex);                
            }
            int middleValue = updateArray[updateArray.length / 2];
            middleSum += middleValue;
        }

        return String.valueOf(middleSum);
    }

    private static void makeValid(Integer[] updateArray, HashMap<Integer, HashSet<Integer>> gtMap, int startIndex) {
        for (int i = startIndex; i < updateArray.length; i++) {
            for (int j = i + 1; j < updateArray.length; j++) {
                HashSet<Integer> gts = gtMap.get(updateArray[i]);
                if (gts != null && gts.contains(updateArray[j])) {
                    int temp = updateArray[i];
                    updateArray[i] = updateArray[j];
                    updateArray[j] = temp;
                    return;
                }
            }
        }
    }    
}
