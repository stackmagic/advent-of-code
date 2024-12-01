package year2024.day01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.lineList;

public class Year2024Day01 {

    public static List<List<Integer>> loadAndParse(String fileName) {
        List<String> lines = lineList(fileName);
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();

        for (String line : lines) {
            String[] parts = line.split("\\s+");
            a.add(Integer.parseInt(parts[0]));
            b.add(Integer.parseInt(parts[1]));
        }

        return List.of(a, b);
    }

    public static int solvePart1(String fileName) {
        List<List<Integer>> input = loadAndParse(fileName);
        List<Integer> a = input.get(0);
        List<Integer> b = input.get(1);

        a.sort(Integer::compareTo);
        b.sort(Integer::compareTo);

        int diff = 0;
        for (int i = 0; i < a.size(); i++) {
            diff += Math.abs(a.get(i) - b.get(i));
        }

        return diff;
    }

    public static int solvePart2(String fileName) {
        List<List<Integer>> input = loadAndParse(fileName);
        List<Integer> a = input.get(0);
        List<Integer> b = input.get(1);

        Map<Integer, Integer> bCount = new HashMap<>();
        for (Integer i : b) {
            Integer count = bCount.getOrDefault(i, 0);
            bCount.put(i, count + 1);
        }

        int sim = 0;
        for (Integer i : a) {
            int count = bCount.getOrDefault(i, 0);
            sim += i * count;
        }

        return sim;
    }
}
