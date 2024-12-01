package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.IntStream;

public class Day1 extends Day {
    @Override
    public String process(String inputFileName, boolean isPart2) {        
        Path filePath = Path.of("resources", inputFileName);
         try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {        
            return isPart2 ? partTwo(reader) : partOne(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private static String partOne(BufferedReader reader) throws IOException {
        String line;
        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<Integer> rightList = new ArrayList<>();


        while ((line = reader.readLine()) != null) {
            String[] input = line.split("\\s+");
            leftList.add(Integer.parseInt(input[0]));
            rightList.add(Integer.parseInt(input[1]));                                
        }

        Collections.sort(leftList);
        Collections.sort(rightList);

        int similarity = IntStream.range(0, leftList.size())
            .map(i -> Math.abs(leftList.get(i) - rightList.get(i)))
            .sum();

        return String.valueOf(similarity);
    }

    private static String partTwo(BufferedReader reader) throws IOException {
        String line;

        ArrayList<Integer> leftList = new ArrayList<>();
        HashMap<Integer, Integer> rightMap = new HashMap<>();

        while ((line = reader.readLine()) != null) {
            String[] input = line.split("\\s+");            
            int rightValue = Integer.parseInt(input[1]);
            leftList.add(Integer.parseInt(input[0]));
            rightMap.putIfAbsent(rightValue,0);
            rightMap.put(rightValue,rightMap.get(rightValue) + 1);                                
        }

        int similarity = leftList.stream()
            .mapToInt(value -> value * rightMap.getOrDefault(value, 0))
            .sum();

        return String.valueOf(similarity);
    }
}
