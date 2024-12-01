package src.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Day {
    public abstract String process(String input, boolean isPart2);

    protected String[] parseInputByLines(String input) {
        return input.split("\\r?\\n");
    }

    protected String readInput(String filename) throws IOException {
        Path path = Path.of("resources", filename);
        return Files.readString(path);
    }

    // try {
    //     String input = readInput(inputFileName);
    //     String[] lines = parseInputByLines(input);
    // } catch (IOException e) {
    //     System.err.println("Error reading input file: " + e.getMessage());
    // }
}

