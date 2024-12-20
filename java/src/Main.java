package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day17
        Assert.assertEquals("4,6,3,5,6,3,5,2,1,0", processDay("d17_1_example.txt", new Day17(), false));
        processDay("d17_1.txt", new Day17(), false);        
        Assert.assertEquals("117440", processDay("d17_2_example.txt", new Day17(), true)); //Part 2
        processDay("d17_1.txt", new Day17(), true); //Part 2  

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
