package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Day25 extends Day {
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
        ArrayList<Integer[]> locks = new ArrayList<>();
        ArrayList<Integer[]> keys = new ArrayList<>();

        Integer[] newEntry = null;
        while ((line = reader.readLine()) != null) {
            if(line.isEmpty()) {
                newEntry = null;
                continue;
            }

            if(newEntry == null) {
                newEntry = new Integer[]{0,0,0,0,0};
                if(line.startsWith("#")) {
                    locks.add(newEntry);
                } else {
                    keys.add(newEntry);
                }
            } else {
                for(int i = 0; i < line.length(); i++) {
                    if(line.charAt(i) == '#') newEntry[i]++;
                }
            }
        }

        ret = partOneHelper(locks, keys);
        return String.valueOf(ret);
    }

    private static long partOneHelper(ArrayList<Integer[]> locks, ArrayList<Integer[]> keys) {
        return locks.stream()
            .flatMap(lock -> keys.stream()
                .filter(key -> IntStream.range(0, lock.length)
                    .allMatch(i -> lock[i] + key[i] <= 6)))
            .count();
    }
}
