package year2024.day10;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Year2024Day10 {

    public static int solvePart1(char[][] map) throws IOException {
        List<Result> scores = checkMap(map);
        return scores.stream()
                .map(Result::nines)
                .map(Collection::size)
                .reduce(0, Integer::sum);
    }

    public static int solvePart2(char[][] map) throws IOException {
        List<Result> scores = checkMap(map);
        return scores.stream()
                .map(Result::paths)
                .map(Collection::size)
                .reduce(0, Integer::sum);
    }

    private static List<Result> checkMap(char[][] map) {
        Grid grid = new Grid(map);
        List<Coord> trailheads = grid.findAll('0');
        return trailheads.stream()
                .map(th -> {
                    Set<Coord> nines = new HashSet<>();
                    Set<String> paths = evaluateTrail("start", nines, grid, th);
                    return new Result(nines, paths);
                })
                .toList();
    }

    /**
     * recursively evaluate a trail.
     *
     * per starting point, we...
     * - for part 1: every '9' we find is added to the Set of coords - finding the same '9' multiple
     *   times trough different paths still only counts once
     * - for part 2: we build a string representing the path taken to find the '9'. this is always 1 input
     *   and 0..3 outputs, so with every level we potentially multiply the number of paths by 3 (1 direction in,
     *   3 directions out).
     */
    private static Set<String> evaluateTrail(String path, Set<Coord> nines, Grid grid, Coord coord) {
        char curr = grid.get(coord);
        String newPath = path + "#>" + coord;
        if (curr == '9') {
            nines.add(coord);
            return Set.of(newPath);
        } else {
            char expectedNext = nextHeight(curr);
            return Arrays.stream(Dir.values())
                    .map(dir -> grid.moveAndGet(coord, dir))
                    .filter(Objects::nonNull)
                    .filter(mr -> mr.value == expectedNext)
                    .flatMap(mr -> evaluateTrail(newPath, nines, grid, mr.coord).stream())
                    .collect(Collectors.toSet());
        }
    }

    private static char nextHeight(char curr) {
        return switch (curr) {
            case '0' -> '1';
            case '1' -> '2';
            case '2' -> '3';
            case '3' -> '4';
            case '4' -> '5';
            case '5' -> '6';
            case '6' -> '7';
            case '7' -> '8';
            case '8' -> '9';
            default -> 'X';
        };
    }

    private enum Dir {

        NORTH(-1, 0),
        EAST(0, 1),
        SOUTH(1, 0),
        WEST(0, -1);

        public final int y;
        public final int x;

        Dir(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    private record Result(Set<Coord> nines, Set<String> paths) {}

    private record Coord(int y, int x) {}

    private record MoveResult(Coord coord, char value) {}


    // ---------------------------------
    // todo "generic" character grid (what about int?)
    //  direction enum with cardinal() and intercardinal() methods additionally to values()
    //  make them work together and cover all the usecases encountered so far


    private record Grid(char[][] grid) {

        private boolean withinBounds(Coord coord) {
            return coord.y >= 0 && coord.y < grid.length && coord.x >= 0 && coord.x < grid[0].length;
        }

        private char get(Coord where) {
            return grid[where.y][where.x];
        }

        private List<Coord> findAll(char needle) {
            List<Coord> rv = new ArrayList<>();
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[0].length; x++) {
                    if (grid[y][x] == needle) {
                        rv.add(new Coord(y, x));
                    }
                }
            }
            return rv;
        }

        private MoveResult moveAndGet(Coord from, Dir dir) {
            Coord next = switch (dir) {
                case NORTH -> up(from);
                case EAST -> right(from);
                case SOUTH -> down(from);
                case WEST -> left(from);
            };
            if (withinBounds(next)) {
                return new MoveResult(next, get(next));
            }
            return null;
        }

        private Coord up(Coord from) {
            return new Coord(from.y - 1, from.x);
        }

        private Coord down(Coord from) {
            return new Coord(from.y + 1, from.x);
        }

        private Coord right(Coord from) {
            return new Coord(from.y, from.x + 1);
        }

        private Coord left(Coord from) {
            return new Coord(from.y, from.x - 1);
        }
    }
}
