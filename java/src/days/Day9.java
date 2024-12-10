package src.days;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.IntStream;

import src.helpers.Coordinate;

public class Day9 extends Day {
    @Override
    public String process(String inputFileName, boolean isPart2) {        
        Path filePath = Path.of("resources", inputFileName);
         try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {        
            return inputParser(reader, isPart2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private static String inputParser(BufferedReader reader, boolean isPart2) throws IOException {
        long ret = 0;
        String line = reader.readLine();        
        ArrayList<Integer> disk = new ArrayList<>();
        ArrayList<Coordinate> fileRange = new ArrayList<>();
        TreeMap<Integer, Integer> spacesRange = new TreeMap<>();
        char[] chars = line.toCharArray();

        int id = 0;
        for(int i = 0; i < chars.length; i+=2) {
            int blocks = (int)chars[i] - (int)'0';
            fileRange.add(new Coordinate(disk.size(),disk.size() + blocks - 1));
            for(int j = 0; j < blocks; j++) {
                disk.add(id);
            }
            id++;
            if(i == chars.length - 1) break;
            int spaces = (int)chars[i+1] - (int)'0';
            if(spaces > 0) {
                spacesRange.put(disk.size(), disk.size() + spaces - 1);
                for(int j = 0; j < spaces; j++) {
                    disk.add(-1);
                }
            }
        }

        ret = (isPart2) ? compressTwo(disk, fileRange, spacesRange) : compressOne(disk);
        return String.valueOf(ret);
    }

    private static long compressOne(ArrayList<Integer> disk) throws IOException {
        long ret = 0;
        int left = 0;
        int right = disk.size() - 1;
        while(left < right) {            
            int l = disk.get(left);
            if(l != -1) {
                ret += left * l;
                left++;
                continue;
            }

            int r = disk.get(right);
            if(r == -1) {
                right--;
                continue;
            }

            disk.set(left, r);
            disk.set(right, -1);
        }

        while(left < disk.size()) {            
            if(disk.get(left) == -1) break;
            ret += left * disk.get(left);
            left++;
        }
        return ret;
    }

    private static long compressTwo(ArrayList<Integer> disk, ArrayList<Coordinate> fileRange, TreeMap<Integer, Integer> spacesRange) throws IOException {        
        
        for(int i = fileRange.size()-1; i > 0; i--) {
            Coordinate coordinate = fileRange.get(i);
            int x = coordinate.x;
            int counter = 1 + coordinate.y - coordinate.x;              
            for(int spaceRangeStart : spacesRange.keySet()) {
                if(spaceRangeStart >= x) break;
                int spaceRangeEnd = spacesRange.get(spaceRangeStart);
                if(1 + spaceRangeEnd - spaceRangeStart >= counter) {
                    for(int j = 0; j < counter; j++) {
                        disk.set(spaceRangeStart+j, disk.get(x + j));
                        disk.set(x + j, -1);
                    }
                    spacesRange.remove(spaceRangeStart);
                    if(1 + spaceRangeEnd - spaceRangeStart > counter) {
                        spacesRange.put(spaceRangeStart + counter, spaceRangeEnd);
                    } 
                    int spacesRight = x + 1;
                    int spacesLeft = x + 1;
                    while(spacesRight < disk.size() && disk.get(spacesRight) == -1) spacesRight++;
                    while(spacesLeft > 0 && disk.get(spacesLeft) == -1) spacesLeft--;
                    spacesRange.put(spacesLeft+1, spacesRight-1);
                    break;
                }
            }                                 
            
        }

        long ret = IntStream.range(0, disk.size())
                    .filter(i -> disk.get(i) != -1)
                    .mapToLong(i -> (long) disk.get(i) * i)
                    .sum();
        return ret;
    }

}


