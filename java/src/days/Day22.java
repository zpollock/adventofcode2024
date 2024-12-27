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
import java.util.HashSet;

public class Day22 extends Day {
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
        ArrayList<Long> secretNumbers = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            secretNumbers.add(Long.parseLong(line));     
        }

        ret = (isPart2) ? partTwoHelper(secretNumbers) : partOneHelper(secretNumbers);
        return String.valueOf(ret);
    }

    private static long partOneHelper(ArrayList<Long> secretNumbers) {
        long ret = 0;
        for(long secretNumber : secretNumbers) {
            for(int i = 0; i < 2000; i++) {
                secretNumber = calculateSecretNumber(secretNumber);
            }
            ret += secretNumber;
        }
        return ret;
    }

    private static long calculateSecretNumber(long secretNumber) {
        long result = secretNumber * 64;
        secretNumber = mix(secretNumber, result);
        secretNumber = prune(secretNumber);
        result = secretNumber / 32;
        secretNumber = mix(secretNumber, result);
        secretNumber = prune(secretNumber);
        result = secretNumber * 2048;
        secretNumber = mix(secretNumber, result);
        secretNumber = prune(secretNumber);
        return secretNumber;
    }

    private static long mix(long secretNumber, long result) {
        return secretNumber ^ result;
    }

    private static long prune(long secretNumber) {
        return secretNumber % 16777216;
    }


    private static long partTwoHelper(ArrayList<Long> secretNumbers) {
        long ret = 0;
        HashSet<String> sequences = new HashSet<>();
        HashMap<String, Long> sequenceValues = new HashMap<>();              
        for(long secretNumber : secretNumbers) {
            long originalSecretNumber = secretNumber;
            String prices = "";                  
            secretNumber = calculateSecretNumber(secretNumber);
            long lastPrice = secretNumber % 10;
            for(int i = 1; i < 2000; i++) {
                secretNumber = calculateSecretNumber(secretNumber);
                long price = secretNumber % 10;
                long diff = lastPrice - price;
                prices += diff >= 0 ? "+" + diff : diff;
                lastPrice = price;
                if(prices.length() == 8) {
                    sequences.add(prices);
                    sequenceValues.put(originalSecretNumber + "," + prices, price);
                    prices = prices.substring(2, prices.length());
                }
            }
        }

        for(String sequence : sequences) {
            long bananas = 0;
            for(long secretNumber : secretNumbers) {
                bananas += sequenceValues.getOrDefault(secretNumber + "," + sequence, 0l);
            }
            ret = Math.max(ret, bananas);
        }
        return ret;
    }

}
