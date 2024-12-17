package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends Day {
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
        long ret = 0;
        String line;
        String regex = "(\\d+)\\D+(\\d+)";
        Pattern pattern = Pattern.compile(regex);

        while ((line = reader.readLine()) != null) {
            if(line.isEmpty()) continue;
            long[] machineValues = new long[6];
            for (int i = 0; i < 3; i++) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    machineValues[i * 2] = Integer.parseInt(matcher.group(1));
                    machineValues[i * 2 + 1] = Integer.parseInt(matcher.group(2));
                }
                line = reader.readLine();
            }            
            long[] solution = !isPart2 ? 
                solveEquations(machineValues[0],machineValues[1],machineValues[2], machineValues[3], machineValues[4], machineValues[5]) :
                solveEquations(machineValues[0],machineValues[1],machineValues[2], machineValues[3], 10000000000000l + machineValues[4], 10000000000000l + machineValues[5]);
            ret += solution[0] * 3 + solution[1];
        }

        return String.valueOf(ret);
    }

    private static long[] solveEquations(long a1, long a2, long b1, long b2, long c1, long c2) {
        long mainDet = a1 * b2 - a2 * b1;
        if (mainDet == 0) {
            return new long[]{0, 0};
        }

        long aDet = (c1 * b2 - c2 * b1);
        long bDet = (a1 * c2 - a2 * c1);

        if (aDet % mainDet != 0 || bDet % mainDet != 0) {
            return new long[] { 0, 0 };
        }

        long x = aDet / mainDet;
        long y =  bDet / mainDet;
    
        return new long[]{x, y};
    }
}
