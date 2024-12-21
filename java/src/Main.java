package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day19
        Assert.assertEquals("6", processDay("d19_1_example.txt", new Day19(), false));
        processDay("d19_1.txt", new Day19(), false);        
        Assert.assertEquals("16", processDay("d19_1_example.txt", new Day19(), true)); //Part 2
        processDay("d19_1.txt", new Day19(), true); //Part 2  

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
