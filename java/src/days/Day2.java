package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

import src.helpers.Utils;

public class Day2 extends Day {
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
        int num_safe = 0;

        while ((line = reader.readLine()) != null) {
            String[] input = line.split("\\s+");
            int[] report = Arrays.stream(input)
                                .mapToInt(Integer::parseInt)
                                .toArray();
            
            num_safe += isSafe(report);
        }

        return String.valueOf(num_safe);
    }

    private static int isSafe(int[] report) {
        if(report.length < 2) return 1;
                
        if(report[0] < report[1]) {
            return IntStream.range(0, report.length - 1)
                .allMatch(i -> report[i+1] - report[i] > 0 && report[i+1] - report[i] <= 3)
                ? 1 : 0;
        } else if(report[0] > report[1]) {
            return IntStream.range(0, report.length - 1)
                .allMatch(i -> report[i] - report[i+1] > 0 && report[i] - report[i+1] <= 3) 
                ? 1 : 0;
        }
        return 0;
    }

    private static String partTwo(BufferedReader reader) throws IOException {
        String line;
        int num_safe = 0;

        while ((line = reader.readLine()) != null) {
            String[] input = line.split("\\s+");
            int[] report = Arrays.stream(input)
                                .mapToInt(Integer::parseInt)
                                .toArray();
            
            num_safe += isSafeWithDampener(report);
        }

        return String.valueOf(num_safe);
    }

    private static int isSafeWithDampener(int[] report) {
        if(report[0] == report[1]) {
            return isSafe(Utils.removeIndexFromArray(report, 0));
        }

        HashSet<Integer> badLevels = new HashSet<>();

        if(report[0] < report[1]) {
            IntStream.range(0, report.length - 1).forEach(i -> {
                if (report[i+1] - report[i] <= 0 || report[i+1] - report[i] > 3) {
                    badLevels.add(i);
                    badLevels.add(i+1);
                }
            });
        } else if(report[0] > report[1]) {            
            IntStream.range(0, report.length - 1).forEach(i -> {
                if (report[i] - report[i+1] <= 0 || report[i] - report[i+1] > 3) {
                    badLevels.add(i);
                    badLevels.add(i+1);
                }
            });            
        }

        if(badLevels.size() == 0) return 1;
        badLevels.add(0);
        for (int level : badLevels) {
            if (isSafe(Utils.removeIndexFromArray(report, level)) == 1) {
                return 1;
            }
        }

        return 0;
    }
}
