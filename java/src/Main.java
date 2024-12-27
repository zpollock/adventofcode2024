package src;

import java.time.Duration;
import java.time.Instant;
import org.junit.Assert;
import src.days.*;

public class Main {
    public static void main(String[] args) {
        Instant start = Instant.now();

        //Day20
        Assert.assertEquals("2", processDay("d20_1_example.txt", new Day20(), false));
        processDay("d20_1.txt", new Day20(), false);        
        // Assert.assertEquals("16", processDay("d20_1_example.txt", new Day20(), true)); //Part 2
        // processDay("d20_1.txt", new Day20(), true); //Part 2  

        //Day21
        // Assert.assertEquals("126384", processDay("d21_1_example.txt", new Day21(), false));
        // processDay("d21_1.txt", new Day21(), false);        
        // Assert.assertEquals("16", processDay("d21_1_example.txt", new Day21(), true)); //Part 2
        // processDay("d21_1.txt", new Day21(), true); //Part 2  

        //Day24
        // Assert.assertEquals("4", processDay("d24_1_example.txt", new Day24(), false));
        // Assert.assertEquals("2024", processDay("d24_2_example.txt", new Day24(), false));
        // processDay("d24_1.txt", new Day24(), false);        
        // Assert.assertEquals("z00,z01,z02,z05", processDay("d24_3_example.txt", new Day24(), true)); //Part 2
        // processDay("d24_1.txt", new Day24(), true); //Part 2  

        
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
