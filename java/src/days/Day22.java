package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Day22 extends Day {
    @Override
    public String process(String inputFileName, boolean isPart2) {        
        Path filePath = Path.of("resources", inputFileName);
         try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {        
            return isPart2 ? partTwo(reader) : partOne(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    /**
     * Not yet implemented     
     */
    private static String partOne(BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            //String[] input = line.split("\\s+");
        }

        return null;
    }

    /**
     * Not yet implemented     
     */
    private static String partTwo(BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            //String[] input = line.split("\\s+");
        }

        return null;
    }

}
