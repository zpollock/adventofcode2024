package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day15
        Assert.assertEquals("2028", processDay("d15_1_example.txt", new Day15(), false));
        Assert.assertEquals("10092", processDay("d15_2_example.txt", new Day15(), false));
        processDay("d15_1.txt", new Day15(), false);        
        Assert.assertEquals("9021", processDay("d15_2_example.txt", new Day15(), true)); //Part 2
        processDay("d15_1.txt", new Day15(), true); //Part 2  

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
