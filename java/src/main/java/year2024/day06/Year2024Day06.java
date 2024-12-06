package year2024.day06;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Year2024Day06 {

    public static int solvePart1(char[][] grid) throws IOException {
        Guard guard = new Guard(grid);
        guard.runToEnd();
        return guard.fieldsDiscovered;
    }

    public static int solvePart2(char[][] grid) throws IOException {
        Guard guard = new Guard(grid);
        guard.runToEnd();
        return guard.possibleObstacleLocations.size();
    }

    private static final class Guard {

        private static final char UP = '^';
        private static final char RIGHT = '>';
        private static final char DOWN = 'v';
        private static final char LEFT = '<';

        private final char[][] grid;
        private final char[][] grid2;
        private int x;
        private int y;
        private final int xl;
        private final int yl;

        private int fieldsDiscovered = 1;
        private final Set<String> possibleObstacleLocations = new HashSet<>();

        private Guard(char[][] grid) {
            this.grid = grid;
            yl = grid.length;
            xl = grid[0].length;

            // clone grid - can only call clone() on the innermost, calling on the
            // outer would reference the same inner arrays as the original.
            // clone() is not a deep/recursive clone
            this.grid2 = new char[yl][xl];
            for (int yy = 0; yy < yl; yy++) {
                this.grid2[yy] = grid[yy].clone();
            }

            // find starting position
            for (int yy = 0; yy < grid.length; yy++) {
                for (int xx = 0; xx < grid[yy].length; xx++) {
                    char b = grid[yy][xx];
                    if (b == UP || b == RIGHT || b == DOWN || b == LEFT) {
                        y = yy;
                        x = xx;
                        return;
                    }
                }
            }
        }

        public void runToEnd() {
            try {
                while (true) {
                    move();
                    //dump();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // elvis has left the building
            }

            dump();
        }

        public void dump() {
            System.out.println("");
            System.out.println("");
            System.out.println("---------------------------------------------------------------------");

            for (int yy = 0; yy < yl; yy++) {
                String g1 = new String(grid[yy]);
                String g2 = new String(grid2[yy]);
                System.out.println(g1 + "       " + g2);
            }

            System.out.println("");
            System.out.println("Fields discovered: " + fieldsDiscovered);
            System.out.println("Possible obstacle locations: " + possibleObstacleLocations.size());
        }

        public void move() {

            char curr = grid[y][x];

            // part1: new coords to move to
            int yn = y;
            int xn = x;

            // move
            switch (curr) {
                case UP -> yn--;
                case RIGHT -> xn++;
                case DOWN -> yn++;
                case LEFT -> xn--;
                default -> throw new IllegalArgumentException("'" + curr + "'");
            }
            char next = grid[yn][xn];

            // next is an obstacle, remain where we are but rotate
            if (next == '#') {
                curr = switch (curr) {
                    case UP -> RIGHT;
                    case RIGHT -> DOWN;
                    case DOWN -> LEFT;
                    case LEFT -> UP;
                    default -> throw new IllegalArgumentException("'" + curr + "'");
                };

                grid[y][x] = curr;

                // obstacle means we turn
                grid2[y][x] = '+';

                return;
            }

            // part2: if we were to put an obstacle in front, check if we'd run into an obstacle we've already seen and thus close a loop
            char curr2 = grid2[y][x];
            switch (curr) {
                case UP:
                    // check to the right
                    for (int xx = x; xx < xl - 1; xx++) {
                        if ((grid2[y][xx] == '+' || grid2[y][xx] == '|') && grid2[y][xx + 1] == '#') {
                            possibleObstacleLocations.add(String.format("%d_%d", yn, xn));
                        }
                    }
                    break;
                case RIGHT:
                    // check down
                    for (int yy = y; yy < yl - 1; yy++) {
                        if ((grid2[yy][x] == '+' || grid2[yy][x] == '-') && grid2[yy + 1][x] == '#') {
                            possibleObstacleLocations.add(String.format("%d_%d", yn, xn));
                        }
                    }
                    break;
                case DOWN:
                    // check left
                    for (int xx = x; xx > 0; xx--) {
                        if ((grid2[y][xx] == '+' || grid2[y][xx] == '|') && grid2[y][xx - 1] == '#') {
                            possibleObstacleLocations.add(String.format("%d_%d", yn, xn));
                        }
                    }
                    break;
                case LEFT:
                    // check up
                    for (int yy = y; yy > 0; yy--) {
                        if ((grid2[yy][x] == '+' || grid2[yy][x] == '-') && grid2[yy - 1][x] == '#') {
                            possibleObstacleLocations.add(String.format("%d_%d", yn, xn));
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException(curr2 + "");
            }

            // part2: next is not an obstacle, mark direction on grid2
            if (curr2 != '+') {
                switch (curr) {
                    case UP, DOWN -> {
                        switch (curr2) {
                            case '-' -> grid2[y][x] = '+';
                            default -> grid2[y][x] = '|';
                        }
                    }
                    case RIGHT, LEFT -> {
                        switch (curr2) {
                            case '|' -> grid2[y][x] = '+';
                            default -> grid2[y][x] = '-';
                        }
                    }
                    default -> throw new IllegalArgumentException("'" + curr2 + "'");
                }
            }

            // move to next field, mark current/previous field as visited
            grid[yn][xn] = curr;
            grid[y][x] = 'X';
            y = yn;
            x = xn;

            // true if new field was discovered
            if (next == '.') {
                fieldsDiscovered++;
            }
        }
    }
}
