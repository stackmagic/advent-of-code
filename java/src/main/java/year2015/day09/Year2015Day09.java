package year2015.day09;

import java.io.IOException;
import java.util.*;

import static utils.Utils.lines;

public class Year2015Day09 {

    private static Map<String, TreeSet<Edge>> load(String file, Comparator<Integer> comparator) throws IOException {
        Map<String, TreeSet<Edge>> map = new HashMap<>();
        lines(file).forEach(line -> {
            String[] parts = line.split(" ");
            String a = parts[0];
            String b = parts[2];
            int distance = Integer.parseInt(parts[4]);

            map.computeIfAbsent(a, k -> new TreeSet<>((e1, e2) -> comparator.compare(e1.distance, e2.distance))).add(new Edge(a, b, distance));
            map.computeIfAbsent(b, k -> new TreeSet<>((e1, e2) -> comparator.compare(e1.distance, e2.distance))).add(new Edge(b, a, distance));
        });

        return map;
    }

    public static int solvePart1(String file) throws IOException {
        return solve(file, (a, b) -> a - b);
    }

    public static int solvePart2(String file) throws IOException {
        return solve(file, (a, b) -> b - a);
    }

    /**
     * each node is connected to every other node
     * we try each node as starting node once and just use the min/max distance of the remaining
     * connections to determine where to go next. repeat until all nodes are visited. from each
     * attempt use the min/max and that's the solution.
     */
    private static int solve(String file, Comparator<Integer> comparator) throws IOException {
        Map<String, TreeSet<Edge>> edges = load(file, comparator);
        Set<String> startNodes = new HashSet<>(edges.keySet());

        return startNodes.stream()
                .map(start -> {

                    Set<String> nodesToVisit = new HashSet<>(edges.keySet());
                    nodesToVisit.remove(start);
                    int distance = 0;

                    while (!nodesToVisit.isEmpty()) {
                        Edge next = edges.get(start).stream()
                                .filter(it -> nodesToVisit.contains(it.destination))
                                .findFirst()
                                .get();

                        distance += next.distance;
                        nodesToVisit.remove(next.destination);
                        start = next.destination;
                    }


                    return distance;
                })
                .sorted(comparator)
                .findFirst()
                .get();
    }

    private record Edge(String start, String destination, int distance) {}
}
