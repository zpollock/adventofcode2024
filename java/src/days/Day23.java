package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day23 extends Day {
    @Override
    public String process(String inputFileName, boolean isPart2) {        
        Path filePath = Path.of("resources", inputFileName);
         try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {        
            return parseInput(reader, isPart2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private static String parseInput(BufferedReader reader, boolean isPart2) throws IOException {
        String line;
        long ret = 0;
        HashMap<String, ArrayList<String>> network = new HashMap<>();

        while ((line = reader.readLine()) != null) {
            String[] input = line.split("-");
            network.putIfAbsent(input[0], new ArrayList<>());
            network.putIfAbsent(input[1], new ArrayList<>());
            network.get(input[0]).add(input[1]);
            network.get(input[1]).add(input[0]);
        }

        if (isPart2) return largestFullyConnectedGroup(network);
        ret = partOneHelper(network);
        return String.valueOf(ret);
    }

    private static long partOneHelper(HashMap<String, ArrayList<String>> network) {
        HashSet<String> interConnectedSet = new HashSet<>();
        for(String computer : network.keySet()) {
            if(!computer.startsWith("t")) continue;
            ArrayList<String> connections = network.get(computer);
            for(String connection : getConnectionPair(connections)) {
                String[] nodes = connection.split(",");
                if(network.get(nodes[0]).contains(nodes[1])) {
                    String interConnected = concatenateSortedStrings(computer, nodes[0], nodes[1]);
                    interConnectedSet.add(interConnected);
                }
            }
        }
        return interConnectedSet.size();
    }

    private static ArrayList<String> getConnectionPair(ArrayList<String> strings) {
        ArrayList<String> combinations = new ArrayList<>();

        for (int i = 0; i < strings.size(); i++) {
            for (int j = i + 1; j < strings.size(); j++) {
                combinations.add(strings.get(i) + "," + strings.get(j));
            }
        }

        return combinations;
    }

    private static String concatenateSortedStrings(String s1, String s2, String s3) {
        String[] strings = {s1, s2, s3};
        Arrays.sort(strings);

        StringBuilder result = new StringBuilder();
        for (String str : strings) {
            result.append(str);
        }
        return result.toString();
    }

    public static String largestFullyConnectedGroup(HashMap<String, ArrayList<String>> network) {
        List<String> largestGroup = new ArrayList<>();

        for (String computer : network.keySet()) {
            List<String> currentGroup = findFullyConnectedGroup(computer, network, new HashSet<>());
            if (currentGroup.size() > largestGroup.size()) {
                largestGroup = currentGroup;
            }
        }

        Collections.sort(largestGroup);
        return String.join(",", largestGroup);
    }

    private static List<String> findFullyConnectedGroup(String start, HashMap<String, ArrayList<String>> network, HashSet<String> visited) {
        Queue<String> queue = new LinkedList<>();
        Set<String> currentGroup = new HashSet<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (!visited.contains(current)) {                
                currentGroup.add(current);
                if (!isFullyConnected(currentGroup, network)) {
                    currentGroup.remove(current);
                    continue;
                }
                visited.add(current);

                for (String neighbor : network.get(current)) {                
                    queue.add(neighbor);                    
                }
            }
        }

        return new ArrayList<>(currentGroup);
    }

    private static boolean isFullyConnected(Set<String> group, HashMap<String, ArrayList<String>> network) {
        for (String computer1 : group) {
            for (String computer2 : group) {
                if (!computer1.equals(computer2)) {
                    if (!network.get(computer1).contains(computer2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
