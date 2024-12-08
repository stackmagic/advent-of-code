package year2024.day08;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Year2024Day08 {

    public static int solvePart1(char[][] grid) throws IOException {
        Map<Character, Set<Coord>> locations = parseLocations(grid);
        Set<Coord> antinodes = findAntinodes(locations, grid.length, grid[0].length, 1);
        return antinodes.size();
    }

    public static int solvePart2(char[][] grid) throws IOException {
        Map<Character, Set<Coord>> locations = parseLocations(grid);
        Set<Coord> antinodes = findAntinodes(locations, grid.length, grid[0].length, Integer.MAX_VALUE);
        return antinodes.size();
    }

    private static Set<Coord> findAntinodes(Map<Character, Set<Coord>> locations, int ymax, int xmax, int projectionCount) {
        // for each group of per-frequency locations
        return locations.values().stream()
                .flatMap(value -> {
                    // process each coordinate for this frequency
                    return value.stream()
                            .flatMap(a -> {
                                // pair every corrdinate with every other but not itself
                                // and project them off each other to generate two new coordinates for the antinodes
                                return value.stream()
                                        .filter(b -> !a.equals(b))
                                        .flatMap(b -> {
                                            Set<Coord> rv = new HashSet<>();
                                            a.projectMultiple(rv, projectionCount, b, ymax, xmax);
                                            b.projectMultiple(rv, projectionCount, a, ymax, xmax);

                                            // for part 2, the original points also count
                                            if (projectionCount > 1) {
                                                rv.addAll(value);
                                            }
                                            return rv.stream();
                                        });
                            });
                })
                .collect(Collectors.toSet());
    }

    private static Map<Character, Set<Coord>> parseLocations(char[][] grid) {
        final Map<Character, Set<Coord>> rv = new HashMap<>();
        for (int y = 0; y < grid.length; y++) {
            char[] row = grid[y];
            for (int x = 0; x < grid[0].length; x++) {
                char c = row[x];
                if (c != '.') {
                    rv.computeIfAbsent(c, k -> new HashSet<>()).add(new Coord(y, x));
                }
            }
        }
        return rv;
    }

    private record Coord(int y, int x) {

        private Coord project(Coord other) {
            return new Coord(y + (y - other.y), x + (x - other.x));
        }

        private boolean withinMap(int ymax, int xmax) {
            return y >= 0 && y < ymax && x >= 0 && x < xmax;
        }

        public void projectMultiple(Set<Coord> coords, int count, Coord other, int ymax, int xmax) {
            if (count == 0) {
                return;
            }

            Coord next = this.project(other);
            if (!next.withinMap(ymax, xmax)) {
                return;
            }
            coords.add(next);
            next.projectMultiple(coords, count - 1, this, ymax, xmax);
        }
    }
}
