package year2024.day15;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static utils.Utils.toCharGrid;

public class Year2024Day15 {

    public static long solvePart1(List<String> data) throws IOException {
        return solve(parsePuzzle(data, false));
    }

    public static long solvePart2(List<String> data) throws IOException {
        return solve(parsePuzzle(data, true));
    }

    private static long solve(Puzzle puzzle) {
        Grid grid = puzzle.grid;
        Deque<Character> moves = puzzle.moves;

        Coord guard = grid.findFirst('@');
        while (!moves.isEmpty()) {
            char move = moves.removeFirst();
            guard = grid.push(guard, move);
        }

        grid.dump();
        if (grid.wide) {
            return grid.boxGps('[');
        } else {
            return grid.boxGps('O');
        }
    }

    private static Puzzle parsePuzzle(List<String> data, boolean wide) throws IOException {
        if (wide) {
            data = data.stream()
                    .map(line -> {
                        if (line.startsWith("#")) {
                            StringBuilder sb = new StringBuilder();
                            for (char c : line.toCharArray()) {
                                switch (c) {
                                    case '#' -> sb.append("##");
                                    case 'O' -> sb.append("[]");
                                    case '.' -> sb.append("..");
                                    case '@' -> sb.append("@.");
                                }
                            }
                            return sb.toString();
                        } else {
                            return line;
                        }
                    }).collect(Collectors.toList());
        }

        String[] map = data.stream()
                .filter(it -> it.startsWith("#"))
                .toArray(String[]::new);
        char[][] grid = toCharGrid(map);
        char[] moveChars = data.stream()
                .filter(it -> !it.isEmpty() && !it.startsWith("#"))
                .collect(Collectors.joining())
                .toCharArray();
        Deque<Character> moves = new LinkedList<>();
        for (char moveChar : moveChars) {
            moves.addLast(moveChar);
        }
        return new Puzzle(new Grid(grid, wide), moves);
    }

    private record Puzzle(Grid grid, Deque<Character> moves) {}

    private record Coord(int y, int x) {
        private Coord up() {
            return new Coord(y - 1, x);
        }

        private Coord down() {
            return new Coord(y + 1, x);
        }

        private Coord right() {
            return new Coord(y, x + 1);
        }

        private Coord left() {
            return new Coord(y, x - 1);
        }

        private Coord go(char c) {
            return switch (c) {
                case '^' -> up();
                case '>' -> right();
                case 'v' -> down();
                case '<' -> left();
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }
    }

    private record Grid(char[][] grid, boolean wide) {

        private void dump() {
            for (char[] chars : grid) {
                System.out.println(chars);
            }
        }

        private char get(Coord where) {
            return grid[where.y][where.x];
        }

        private void set(Coord where, char value) {
            grid[where.y][where.x] = value;
        }

        private Coord push(Coord from, char direction) {
            if (wide) {
                return pushWide(from, direction);
            } else {
                if (pushWideHorizontal(from, direction)) {
                    // guard moved
                    return from.go(direction);
                } else {
                    // not moving
                    return from;
                }
            }
        }

        private void swap(Coord a, Coord b) {
            char aa = get(a);
            char bb = get(b);
            set(a, bb);
            set(b, aa);
        }

        private Coord pushWide(Coord from, char direction) {
            boolean vertical = direction == '^' || direction == 'v';
            boolean success = false;

            if (vertical) {
                if (canPushVertical(from.go(direction), direction)) {
                    pushWideVertical(from, direction);
                    success = true;
                }
            } else {
                success = pushWideHorizontal(from, direction);
            }

            if (success) {
                // guard moved
                return from.go(direction);
            } else {
                // not moving
                return from;
            }
        }

        /** check depth-first if pushing is possible */
        private boolean canPushVertical(Coord from, char direction) {
            char value = get(from);
            if (value == '#') {
                return false;
            }
            if (value == '.') {
                return true;
            }

            Coord next = from.go(direction);
            if (value == '@') {
                return canPushVertical(next, direction);
            }

            Coord other = null;
            if (value == '[') {
                other = from.right().go(direction);
            }
            if (value == ']') {
                other = from.left().go(direction);
            }
            return canPushVertical(next, direction) && canPushVertical(other, direction);
        }

        /**
         * execute vertical push depth-first, basically the same as pushWideHorizontal
         * except we push wide boxes and so if we encounter a box, we also need to
         * check if the other half of the box can be pushed.
         */
        private void pushWideVertical(Coord from, char direction) {
            char value = get(from);
            if (value == '#') {
                throw new IllegalStateException("Found wall but shouldn't be possible since canPush returned true??? where: " + from);
            }
            if (value == '.') {
                return;
            }

            // move guard
            Coord next = from.go(direction);
            if (value == '@') {
                pushWideVertical(next, direction);
                swap(next, from);
                return;
            }

            // move some boxes
            Coord other = value == '[' ? from.right() : value == ']' ? from.left() : null;
            if (other != null) {
                Coord otherNext = other.go(direction);

                pushWideVertical(next, direction);
                pushWideVertical(otherNext, direction);

                swap(from, next);
                swap(other, otherNext);
            }
        }

        /**
         * check depth-first if there's a free spot and if so, swap the 2 adjacent spots
         * every time we return 1 level out of the recursion
         */
        private boolean pushWideHorizontal(Coord from, char direction) {
            char value = get(from);
            if (value == '#') {
                return false;
            }
            if (value == '.') {
                return true;
            }

            Coord next = from.go(direction);
            if (pushWideHorizontal(next, direction)) {
                swap(next, from);
                return true;
            }

            return false;
        }

        /**
         * the description for calculating box gps in part 2 is weird. could have just
         * said it's always measured from top-left to the left half '[' of the box. so,
         * same as part 1, we just use '[' instead of 'O'. simple.
         */
        private long boxGps(char boxChar) {
            long rv = 0;

            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    if (grid[y][x] == boxChar) {
                        rv += 100L * y + x;
                    }
                }
            }

            return rv;
        }

        private Coord findFirst(char c) {
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    if (grid[y][x] == c) {
                        return new Coord(y, x);
                    }
                }
            }
            throw new IllegalStateException("not found: " + c);
        }
    }
}
