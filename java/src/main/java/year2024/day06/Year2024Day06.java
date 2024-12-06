package year2024.day06;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Year2024Day06 {

    public static int solvePart1(char[][] grid) throws IOException {
        Guard guard = new Guard(grid);
        guard.moveToEnd();
        return guard.coords.size();
    }

    public static int solvePart2(char[][] grid) throws IOException {
        return 2222;
    }

    private record Coord(int y, int x) {}

    private enum Dir {

        NORTH('^', -1, 0),
        EAST('>', 0, 1),
        SOUTH('v', 1, 0),
        WEST('<', 0, -1);

        public final char symbol;
        public final int y;
        public final int x;

        Dir(char symbol, int y, int x) {
            this.symbol = symbol;
            this.y = y;
            this.x = x;
        }

        public static Dir get(char c) {
            for (Dir dir : values()) {
                if (dir.symbol == c) {
                    return dir;
                }
            }
            return null;
        }

        Dir next() {
            return switch (this) {
                case NORTH -> EAST;
                case EAST -> SOUTH;
                case SOUTH -> WEST;
                case WEST -> NORTH;
            };
        }

        char visited(char curr) {
            if (curr == '.') {
                return switch (this) {
                    case NORTH -> '^';
                    case EAST -> '>';
                    case SOUTH -> 'v';
                    case WEST -> '<';
                };
            }
            return '+';
        }
    }

    private static final class Guard {

        private final Set<Coord> coords = new HashSet<>();

        private final char[][] grid;

        private int y;
        private int x;
        private Dir dir;

        private Guard(char[][] grid) {
            this.grid = grid;

            outer:
            for (y = 0; y < grid.length; y++) {
                char[] row = grid[y];
                for (x = 0; x < row.length; x++) {
                    char c = row[x];
                    dir = Dir.get(c);
                    if (dir != null) {
                        break outer;
                    }
                }
            }
        }

        public void moveToEnd() {
            try {
                while (true) {
                    move();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // elvis has left the building
            }

            dumpGrid();

//            int count = 0;
//            for (char[] row : grid) {
//                for (char c : row) {
//                    if (c == '+' || c == '-' || c == '|') {
//                        count++;
//                    }
//                }
//            }
//
//            if (count != coords.size()) {
//                throw new RuntimeException("Different Counts: " + count + " vs. " + coords.size());
//            }
        }


        private void move() {
            // record current location as visited
            coords.add(new Coord(y, x));

            // mark current location on grid as visited with directional info (only for system out)
            grid[y][x] = dir.visited(grid[y][x]);

            // next position
            int yn = y + dir.y;
            int xn = x + dir.x;

            // next location content
            char next = grid[yn][xn];

            // obstacle ahead, rotate to the right
            if (next == '#') {
                dir = dir.next();
                grid[y][x] = '+';
            }

            // no obstacle, move ahead, store discovered location
            else {
                y = yn;
                x = xn;
            }
        }

        private void dumpGrid() {
            for (char[] row : grid) {
                System.out.println(row);
            }
        }
    }
}
