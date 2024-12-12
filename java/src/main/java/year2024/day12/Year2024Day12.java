package year2024.day12;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Year2024Day12 {

    public static long solvePart1(char[][] data) throws IOException {
        Grid grid = new Grid(data);
        return grid.discoverRegions().values().stream()
                .flatMap(Collection::stream)
                .map(Region::price)
                .reduce(0L, Long::sum);
    }

    public static long solvePart2(char[][] data) throws IOException {
        Grid grid = new Grid(data);
        return grid.discoverRegions().values().stream()
                .flatMap(Collection::stream)
                .map(Region::bulkPrice)
                .reduce(0L, Long::sum);
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

        private Set<Coord> allDirections() {
            return Set.of(up(), down(), right(), left());
        }
    }

    private record BoundaryCoords(Coord in, Coord out) {

        private BoundaryCoords up() {
            return new BoundaryCoords(in.up(), out.up());
        }

        private BoundaryCoords down() {
            return new BoundaryCoords(in.down(), out.down());
        }

        private BoundaryCoords right() {
            return new BoundaryCoords(in.right(), out.right());
        }

        private BoundaryCoords left() {
            return new BoundaryCoords(in.left(), out.left());
        }

        /** is this part of a vertical side? true if the x (left/right) coordinate differs */
        private boolean vertical() {
            return Math.abs(in.x - out.x) == 1;
        }

        /** is this part of a horizontal side? true if the y (up/down) coordinate differs */
        private boolean horizontal() {
            return Math.abs(in.y - out.y) == 1;
        }

        private Set<BoundaryCoords> growAlongAxis() {
            if (vertical()) {
                return Set.of(this, up(), down());
            }
            if (horizontal()) {
                return Set.of(this, right(), left());
            }
            throw new IllegalStateException("neither horizontal nor vertical " + this);
        }
    }

    private record Region(Character plantType, Set<Coord> coordinates, Set<Coord> outside) {

        public long price() {
            return area() * perimeter();
        }

        public long bulkPrice() {
            return area() * sides();
        }

        public long area() {
            return coordinates.size();
        }

        /**
         * creates pairs of in+out coordinates describing the border. this is needed, because some
         * coordinates count twice in inside or outside corners, regardless of if we work with only
         * inside or outside coordinates - so pairs it is, as this describes a unique border segment.
         */
        public Stream<BoundaryCoords> boundaryPairs() {
            return outside.stream()
                    .flatMap(out -> out.allDirections().stream()
                            .filter(coordinates::contains)
                            .map(in -> new BoundaryCoords(in, out)));
        }

        public long perimeter() {
            return boundaryPairs()
                    .filter(bc -> coordinates.contains(bc.in))
                    .collect(Collectors.toSet())
                    .size();
        }

        public long sides() {
            Set<BoundaryCoords> all = boundaryPairs().collect(Collectors.toSet());
            List<BoundaryCoords> remaining = new ArrayList<>(all);
            Set<Set<BoundaryCoords>> allSides = new HashSet<>();

            /*
             * take any BoundaryCoords (in-out coord pair) and try to expand in its axis
             * until we find no more points - that's a full side found
             *
             * remove all the found points from the list of remaining points and start again
             */
            while (!remaining.isEmpty()) {
                Set<BoundaryCoords> currSide = new HashSet<>();
                currSide.add(remaining.getFirst());
                int size;

                do {
                    size = currSide.size();
                    currSide = currSide.stream()
                            .flatMap(bp -> bp.growAlongAxis().stream())
                            .filter(all::contains)
                            .collect(Collectors.toSet());
                } while (currSide.size() != size);

                remaining.removeAll(currSide);
                allSides.add(currSide);
            }

            return allSides.size();
        }
    }

    private record Grid(char[][] grid) {

        private Map<Character, List<Region>> discoverRegions() {

            Set<Coord> coordsDiscovered = new HashSet<>();
            Map<Character, List<Region>> regions = new HashMap<>();

            for (int y = 0; y < grid.length; y++) {
                char[] row = grid[y];
                for (int x = 0; x < row.length; x++) {
                    Coord coord = new Coord(y, x);
                    if (coordsDiscovered.contains(coord)) {
                        continue;
                    }

                    char plantType = row[x];
                    Region region = new Region(plantType, new HashSet<>(), new HashSet<>());
                    regions.computeIfAbsent(plantType, _ -> new ArrayList<>()).add(region);
                    discoverRegion(region, coord, coordsDiscovered);
                }
            }

            return regions;
        }

        private void discoverRegion(Region region, Coord coord, Set<Coord> coordsDiscovered) {

            // different region - store boundary coord
            if (!withinBounds(coord) || get(coord) != region.plantType) {
                region.outside.add(coord);
                return;
            }

            // same region, add to global list of coords so the outer for loop in discoverRegions()
            // won't try to discover the same region from n different starting points
            if (!coordsDiscovered.add(coord)) {
                return;
            }

            // new coord, add to region
            region.coordinates.add(coord);

            // move in all 4 directions and see what's there
            coord.allDirections().forEach(next -> discoverRegion(region, next, coordsDiscovered));
        }

        private boolean withinBounds(Coord coord) {
            return coord.y >= 0 && coord.y < grid.length && coord.x >= 0 && coord.x < grid[0].length;
        }

        private char get(Coord where) {
            return grid[where.y][where.x];
        }
    }
}
