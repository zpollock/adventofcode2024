package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day6        
        // Assert.assertEquals("41", processDay("d6_1_example.txt", new Day6(), false));
        // processDay("d6_1.txt", new Day6(), false);        
        // Assert.assertEquals("6", processDay("d6_1_example.txt", new Day6(), true)); //Part 2
        // processDay("d6_1.txt", new Day6(), true); //Part 2  

        //Day7        
        Assert.assertEquals("3749", processDay("d7_1_example.txt", new Day7(), false));
        processDay("d7_1.txt", new Day7(), false);        
        Assert.assertEquals("11387", processDay("d7_1_example.txt", new Day7(), true)); //Part 2
        processDay("d7_1.txt", new Day7(), true); //Part 2  

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
