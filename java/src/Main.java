package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day9        
        Assert.assertEquals("1928", processDay("d9_1_example.txt", new Day9(), false));
        processDay("d9_1.txt", new Day9(), false);        
        Assert.assertEquals("2858", processDay("d9_1_example.txt", new Day9(), true)); //Part 2
        processDay("d9_1.txt", new Day9(), true); //Part 2  

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
