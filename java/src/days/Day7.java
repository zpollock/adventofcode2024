package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Day7 extends Day {
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
        ArrayList<String[]> lines = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            String[] input = line.split("\\s+");
            lines.add(input);            
        }

        ret = calibrationHelper(lines, isPart2);                    

        return String.valueOf(ret);
    }

    private static long calibrationHelper(ArrayList<String[]> lines, boolean isPart2) {
        long ret = 0;
        for(String[] line : lines) {
            long value = Long.parseLong(line[0].replace(":", ""));
            if(isValidTest(line,1,0, value, isPart2)) ret += value;
        }
        return ret;
    }


    private static boolean isValidTest(String[] line, int pos, long exp_value, long value, boolean isPart2) {
        if(pos == line.length && value == exp_value){
            return true;
        }

        if(pos == line.length) return false;
        long curr_value = Long.parseLong(line[pos]);
        
        if(isValidTest(line, pos + 1, exp_value + curr_value, value, isPart2)) return true;
        if(isValidTest(line, pos + 1, (exp_value == 0) ? curr_value : exp_value * curr_value, value, isPart2)) return true;

        if(isPart2 && pos <= line.length - 1)
            if(isValidTest(line, pos + 1, Long.parseLong(exp_value + "" + curr_value), value, isPart2)) return true; 
        
        
        return false;        

    }
}
