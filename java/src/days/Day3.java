package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 extends Day {
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
        String regex = "mul\\((\\d+),(\\d+)\\)";
        int product_sum = 0;

        while ((line = reader.readLine()) != null) {
            product_sum += partOneHelper(line, regex);  
        }       
        
        return String.valueOf(product_sum);                             
    }

    private static int partOneHelper(String line, String regex) {
        int product_sum = 0;
        Pattern pattern = Pattern.compile(regex);
        String[] input = line.split("\\)");
        for(String s : input) {
            s += ")";
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {   
                String num1 = matcher.group(1);
                String num2 = matcher.group(2);
                product_sum += Integer.parseInt(num1) * Integer.parseInt(num2);
            }
        }
        return product_sum;
    }

    private static String partTwo(BufferedReader reader) throws IOException {
        String regex = "mul\\((\\d+),(\\d+)\\)";
        int product_sum = 0;
        String line = reader.lines().collect(Collectors.joining());   
        
        product_sum += partTwoHelper(line, regex);          
        return String.valueOf(product_sum);
    }

    private static int partTwoHelper(String line, String regex) {
        int dont_index;
        while ((dont_index = line.indexOf("don't()")) > 0) {
            int do_index = line.indexOf("do()", dont_index + "don't()".length());
            if(do_index > 0) {
                line = line.substring(0, dont_index) + line.substring(do_index + "do()".length());
            } else {
                line = line.substring(0, dont_index);
            }            
        }
        return partOneHelper(line, regex);
    }
}
