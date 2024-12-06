package year2024.day06;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Year2024Day06 {

    public static int solvePart1(char[][] grid) throws IOException {
        Guard guard = new Guard(grid);
        guard.moveToEnd(true);
        return guard.points.size();
    }

    public static int solvePart2(char[][] grid) throws IOException {
        Guard guard = new Guard(grid);
        guard.moveToEnd(true);
        guard.loopCheck();
        return guard.obstacles.size();
    }

    private record Point(int y, int x) {}

    private record PointDir(int y, int x, Dir dir) {}

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

        /** starting point of the guard - can't place obstacle in front of him */
        private final PointDir start;

        /** set of distinct visited points for part 1 */
        private final Set<Point> points = new HashSet<>();

        /** all visited points including their direction for loopchecking in part 2 */
        private final Set<PointDir> pointsWithDir = new HashSet<>();

        /** all valid obstacle locations */
        private final Set<Point> obstacles = new HashSet<>();

        private final char[][] grid;

        private int y;
        private int x;
        private Dir dir;

        private final int ymax;
        private final int xmax;

        private Guard(char[][] grid) {
            this.grid = grid;
            this.ymax = grid.length;
            this.xmax = grid[0].length;

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

            start = new PointDir(y, x, dir);
        }

        /** @return true if a loop was found - which in part1 is guaranteed to NOT be the case */
        public boolean moveToEnd(boolean recordPath) {
            Set<PointDir> loopCheck = new HashSet<>();
            y = start.y;
            x = start.x;
            dir = start.dir;

            while (true) {

                // next position
                int yn = y + dir.y;
                int xn = x + dir.x;

                // leaving the grid - failed move
                if (yn < 0 || yn >= ymax || xn < 0 || xn >= xmax) {
                    return false;
                }

                // next location content
                char next = grid[yn][xn];

                // obstacle ahead, rotate to the right
                if (next == '#') {
                    dir = dir.next();
                }

                // no obstacle, move ahead, store discovered location
                else {
                    y = yn;
                    x = xn;

                    if (!loopCheck.add(new PointDir(y, x, dir))) {
                        // loop found
                        return true;
                    } else if (recordPath) {
                        // record current location as visited
                        points.add(new Point(y, x));
                        pointsWithDir.add(new PointDir(y, x, dir));

                        // mark current location on grid as visited
                        grid[y][x] = 'X'; // dir.visited(grid[y][x]);
                    }
                }
            }
        }

        private void loopCheck() {
            pointsWithDir.forEach(pwd -> {

                // get original field
                char original = grid[pwd.y][pwd.x];

                // add obstacle
                grid[pwd.y][pwd.x] = '#';

                if (moveToEnd(false)) {
                    obstacles.add(new Point(pwd.y, pwd.x));
                }

                // restore original grid
                grid[pwd.y][pwd.x] = original;
            });

            // it would be a valid location if only the guard wasn't there
            Point whereTheGuardSitds = new Point(start.y, start.x);
            obstacles.remove(whereTheGuardSitds);

            // dump result
            obstacles.forEach(pwd -> grid[pwd.y][pwd.x] = 'O');
            for (char[] row : grid) {
                System.out.println(row);
            }
        }
    }
}
