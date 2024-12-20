package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day17 extends Day {
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
        long regA = 0;
        long regB = 0;
        long regC = 0;
        String programString = null;
        int[] program;

        while ((line = reader.readLine()) != null) {
            if(line.startsWith("Register A")) regA = Integer.parseInt(line.split(":")[1].trim());
            if(line.startsWith("Register B")) regB = Integer.parseInt(line.split(":")[1].trim());
            if(line.startsWith("Register C")) regC = Integer.parseInt(line.split(":")[1].trim());
            if(line.startsWith("Program")) programString = line.split(":")[1].trim();            
        }

        program = Arrays.stream(programString.split(","))
                       .mapToInt(Integer::parseInt)
                       .toArray();
        if(!isPart2) return runProgram(regA, regB, regC, program, null, false);
        long ret = findA(program, programString.replace(",", ""));
        return String.valueOf(ret);
    }

    private static long findA(long a, int[] program, String searchString, int matchedIndex, long maxA, String lastOutput) {
        if (matchedIndex == searchString.length()) {
                return a;
        }
    
        long step = (long) Math.pow(8, searchString.length() - matchedIndex - 1);
        for (long nextA = a; nextA < maxA; nextA += step) {
            String programOutput = runProgram(nextA, 0, 0, program, searchString, true);            
            if (programOutput.substring(searchString.length() - matchedIndex - 1).startsWith(searchString.substring(searchString.length() - matchedIndex - 1))) {
                long result = findA(nextA, program, searchString, matchedIndex + 1, maxA, programOutput);
                if (result != -1) {
                    return result; 
                }
            }
        }
        return -1;
    }
    
    public static long findA(int[] program, String searchString) {
        long maxA = (long)Math.pow(8, program.length);
        long minA = (long) Math.pow(8, program.length-1);
        return findA(minA, program, searchString, 0, maxA, "");
    }

    private static String runProgram(long regA, long regB, long regC, int[] program, String searchString, boolean isPart2) {
        String output = "";
        for(int i = 0; i < program.length; i+=2) {
            int opcode = program[i];
            int operandLiteral = program[i+1];
            long operandCombo = operandLiteral;
            if(operandLiteral == 4) operandCombo = regA;
            if(operandLiteral == 5) operandCombo = regB;
            if(operandLiteral == 6) operandCombo = regC;
            if(opcode == 0) { //adv
                regA = (long)(regA / (Math.pow(2, operandCombo)));
            } else if(opcode == 1) { //bxl
                regB ^= operandLiteral;
            } else if(opcode == 2) { //bst
                regB = operandCombo % 8;
            } else if(opcode == 3 && regA != 0) { //jnz
                i = operandLiteral - 2;
                continue;
            } else if(opcode == 4) { //bxc
                regB ^= regC;
            } else if(opcode == 5) { //out
                if(isPart2) {
                    output += operandCombo % 8;
                } else {
                    output += output.isBlank() ? operandCombo % 8 : "," + operandCombo % 8;
                }
            } else if(opcode == 6) { //bdv
                regB = (long)(regA / (Math.pow(2, operandCombo)));
            } else if(opcode == 7) { //cdv
                regC = (long)(regA / (Math.pow(2, operandCombo)));
            }
        }
        return output;
    }

}
