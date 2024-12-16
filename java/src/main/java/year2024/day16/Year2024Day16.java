package year2024.day16;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Year2024Day16 {

    public static int solvePart1(char[][] data) throws IOException {
        Grid grid = new Grid(data);
        Coord start = grid.findFirst('S');
        Coord dest = grid.findFirst('E');
        Map<Coord, Cost> cost = grid.fillCostValues(start, Dir.EAST, dest);
        return cost.get(dest).cost;
    }

    public static int solvePart2(char[][] data) throws IOException {
        return 99;
    }

    private record Cost(int cost, Dir dir) {}

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

        public static Dir get(Coord prev, Coord curr) {
            if (prev.up().equals(curr)) {
                return NORTH;
            }
            if (prev.right().equals(curr)) {
                return EAST;
            }
            if (prev.down().equals(curr)) {
                return SOUTH;
            }
            if (prev.left().equals(curr)) {
                return WEST;
            }
            throw new IllegalArgumentException();
        }
    }

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

        private Stream<Coord> allDirections() {
            return Stream.of(up(), down(), right(), left());
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

    private record Grid(char[][] grid) {

        private Map<Coord, Cost> fillCostValues(Coord start, Dir startDir, Coord dest) {

            Map<Coord, Cost> places = new HashMap<>();
            places.put(start, new Cost(0, startDir));

            int updates;
            do {
                updates = 0;

                dump(places);

                Map<Coord, Cost> newPlaces = new HashMap<>();
                for (Map.Entry<Coord, Cost> entry : places.entrySet()) {

                    Coord place = entry.getKey();
                    Cost currCost = entry.getValue();
                    newPlaces.put(place, currCost);

                    Set<Coord> targets = place.allDirections().filter(this::validTarget).collect(Collectors.toSet());
                    for (Coord target : targets) {

                        int newCostToTarget = currCost.cost + 1;

                        Dir dir = Dir.get(place, target);
                        if (dir != currCost.dir) {
                            newCostToTarget += 1000;
                        }

                        Cost currTargetCost = places.get(target);
                        if (currTargetCost == null || currTargetCost.cost > newCostToTarget) {
                            newPlaces.put(target, new Cost(newCostToTarget, dir));
                            updates++;
                        }
                    }
                }

                places = newPlaces;

            } while (updates > 0);

            return places;
        }

        private boolean validTarget(Coord coord) {
            char c = get(coord);
            return c == '.' || c == 'E';
        }

        private void dump(Map<Coord, Cost> places) {
            char[][] clone = new char[grid.length][grid[0].length];
            for (int i = 0; i < grid.length; i++) {
                clone[i] = grid[i].clone();
            }

            places.forEach((coord, _) -> {
                clone[coord.y][coord.x] = 'X';
            });

            System.out.println("\n\n");
            for (char[] row : clone) {
                System.out.println(row);
            }
        }

        private char get(Coord where) {
            return grid[where.y][where.x];
        }

        private void set(Coord where, char value) {
            grid[where.y][where.x] = value;
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
