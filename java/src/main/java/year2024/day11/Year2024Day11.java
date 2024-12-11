package year2024.day11;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Year2024Day11 {

    public static long solvePart1(String data) throws IOException {
        return run(data, 25);
    }

    public static long solvePart2(String data) throws IOException {
        return run(data, 75);
    }

    public static long run(String data, int iterations) {
        // data = "0 0 125 17"
        // counts => [0->2, 125->1, 17->1]
        Map<Long, Long> counts = parse(data).stream()
                .collect(Collectors.toMap(k -> k, k -> 1L, Long::sum));

        for (int i = 0; i < iterations; i++) {
            counts = step(counts);
        }

        return counts.values().stream().reduce(0L, Long::sum);
    }

    private static List<Long> parse(String data) {
        return Arrays.stream(data.split(" "))
                .map(Long::parseLong)
                .toList();
    }

    private static void add(Map<Long, Long> counts, long count, long key) {
        counts.put(key, counts.getOrDefault(key, 0L) + count);
    }

    private static Map<Long, Long> step(Map<Long, Long> counts) {
        Map<Long, Long> rv = new HashMap<>();

        counts.forEach((stone, count) -> {
            // all $count $stones are turned into one or two new numbers. apply the rule
            // and add the $count to the preexisting count (or zero) in the returned map
            if (stone == 0) {
                add(rv, count, 1L);
            } else if (stone.toString().length() % 2 == 0) {
                String s = stone.toString();
                int l = s.length();
                add(rv, count, Long.parseLong(s.substring(0, l / 2)));
                add(rv, count, Long.parseLong(s.substring(l / 2)));
            } else {
                add(rv, count, 2024L * stone);
            }
        });

        return rv;
    }
}
