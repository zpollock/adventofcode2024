package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day12       
        // Assert.assertEquals("140", processDay("d12_1_example.txt", new Day12(), false));
        // Assert.assertEquals("1930", processDay("d12_2_example.txt", new Day12(), false));
        // processDay("d12_1.txt", new Day12(), false);        
        // Assert.assertEquals("80", processDay("d12_1_example.txt", new Day12(), true)); //Part 2
        // Assert.assertEquals("236", processDay("d12_3_example.txt", new Day12(), true)); //Part 2
        // processDay("d12_1.txt", new Day12(), true); //Part 2  

        //Day13
        Assert.assertEquals("12", processDay("d14_1_example.txt", new Day14(), false));
        processDay("d14_1.txt", new Day14(), false);        
        //Assert.assertEquals("", processDay("d13_3_example.txt", new Day13(), true)); //Part 2
        processDay("d14_1.txt", new Day14(), true); //Part 2  

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
