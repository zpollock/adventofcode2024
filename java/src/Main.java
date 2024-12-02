package src;

import org.junit.Assert;
import src.days.Day;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        //Day1
        // Assert.assertEquals("11", processDay("d1_1_sample.txt", new Day1(), false));
        // processDay("d1_1.txt", new Day1(), false);        
        // Assert.assertEquals("31", processDay("d1_1_sample.txt", new Day1(), true));//Part 2
        // processDay("d1_1.txt", new Day1(), true);//Part 2         
        //Day3
        Assert.assertEquals("161", processDay("d3_1_sample.txt", new Day3(), false));
        processDay("d3_1.txt", new Day3(), false);        
        Assert.assertEquals("48", processDay("d3_2_sample.txt", new Day3(), true)); //Part 2
        processDay("d3_1.txt", new Day3(), true); //Part 2  
    }

    private static String processDay(String dayInputFile, Day dayProcessor, boolean isPart2) {
        String result = dayProcessor.process(dayInputFile, isPart2);
        System.out.println("Result: " + result);
        return result;
    }
}

