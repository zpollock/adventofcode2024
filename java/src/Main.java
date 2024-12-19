package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day16
        Assert.assertEquals("7036", processDay("d16_1_example.txt", new Day16(), false));
        Assert.assertEquals("11048", processDay("d16_2_example.txt", new Day16(), false));
        processDay("d16_1.txt", new Day16(), false);        
        Assert.assertEquals("45", processDay("d16_1_example.txt", new Day16(), true)); //Part 2
        Assert.assertEquals("64", processDay("d16_2_example.txt", new Day16(), true)); //Part 2
        processDay("d16_1.txt", new Day16(), true); //Part 2  

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
