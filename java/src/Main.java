package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day1
        // Assert.assertEquals("11", processDay("d1_1_sample.txt", new Day1(), false));
        // processDay("d1_1.txt", new Day1(), false);        
        // Assert.assertEquals("31", processDay("d1_1_sample.txt", new Day1(), true));//Part 2
        // processDay("d1_1.txt", new Day1(), true);//Part 2         
        //Day3
        
        Assert.assertEquals("18", processDay("d4_1_sample.txt", new Day4(), false));
        processDay("d4_1.txt", new Day4(), false);        
        Assert.assertEquals("9", processDay("d4_1_sample.txt", new Day4(), true)); //Part 2
        processDay("d4_1.txt", new Day4(), true); //Part 2  

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Time taken: " + timeElapsed + " milliseconds");
    }

    private static String processDay(String dayInputFile, Day dayProcessor, boolean isPart2) {
        String result = dayProcessor.process(dayInputFile, isPart2);
        System.out.println("Result: " + result);
        return result;
    }
}

