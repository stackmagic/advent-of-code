package year2024.day14;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Year2024Day14 {

    public static int solvePart1(Stream<String> data, int xMax, int yMax) throws IOException {
        List<Robot> robots = parse(data);
        for (int i = 0; i < 100; i++) {
            robots = tick(robots, xMax, yMax);
        }

        return calcSafetyScore(robots, xMax, yMax);
    }

    /**
     * save all generations, sort by safety score and the one with then lowest score is the solution,
     * because the tree clumps a lot of robots in one space, possibly all in the same quadrant, and
     * the theory is, score = many * few * few * few, which would result in a low score. someone on
     * reddit said it was the 11th lowest score. with my input, it's the lowest score ¯\_(ツ)_/¯
     */
    public static int solvePart2(Stream<String> data, int xMax, int yMax) throws IOException {
        List<Generation> generations = new ArrayList<>();
        List<Robot> robots = parse(data);
        for (int i = 0; i < xMax * yMax; i++) {
            robots = tick(robots, xMax, yMax);
            int score = calcSafetyScore(robots, xMax, yMax);
            generations.add(new Generation(i + 1, score, robots));
        }

        generations.sort(Comparator.comparingInt(a -> a.score));
        Generation first = generations.getFirst();
        dump(first.robots, xMax, yMax);
        System.out.println("Generation: " + first.gen);
        return first.gen;
    }

    private static void dump(List<Robot> robots, int xMax, int yMax) {
        char[][] grid = new char[yMax][xMax];
        for (int y = 0; y < yMax; y++) {
            grid[y] = new char[xMax];
            for (int x = 0; x < xMax; x++) {
                grid[y][x] = '.';
            }
        }

        robots.forEach(r -> {
            char c = grid[r.pos.y][r.pos.x];
            grid[r.pos.y][r.pos.x] = switch (c) {
                case '.' -> '1';
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
        });

        for (char[] row : grid) {
            System.out.println(row);
        }
    }

    private enum Quadrant {NE, SE, SW, NW, AXIS}

    private record Generation(int gen, int score, List<Robot> robots) {}

    private record Coord(int x, int y) {}

    private record Robot(Coord pos, Coord vel) {
        public Robot tick(int xMax, int yMax) {
            int x = moveOnAxis(pos.x, vel.x, xMax);
            int y = moveOnAxis(pos.y, vel.y, yMax);
            return new Robot(new Coord(x, y), vel);
        }

        private int moveOnAxis(int pos, int vel, int max) {
            int next = pos + vel;
            if (next < 0) {
                return max + next;
            }
            if (next >= max) {
                return next % max;
            }
            return next;
        }

        public Quadrant getQuadrant(int xAxis, int yAxis) {
            if (pos.x > xAxis) {
                if (pos.y > yAxis) {
                    return Quadrant.SE;
                }
                if (pos.y < yAxis) {
                    return Quadrant.NE;
                }
            }
            if (pos.x < xAxis) {
                if (pos.y > yAxis) {
                    return Quadrant.SW;
                }
                if (pos.y < yAxis) {
                    return Quadrant.NW;
                }
            }
            return Quadrant.AXIS;
        }
    }

    private static List<Robot> tick(List<Robot> robots, int xMax, int yMax) {
        return robots.stream().map(it -> it.tick(xMax, yMax)).toList();
    }

    private static Map<Quadrant, List<Robot>> splitByQuadrant(List<Robot> robots, int xMax, int yMax) {
        int xAxis = (int) Math.floor(xMax / 2.0);
        int yAxis = (int) Math.floor(yMax / 2.0);

        Map<Quadrant, List<Robot>> quadrants = new HashMap<>();
        robots.forEach(robot -> {
            Quadrant quadrant = robot.getQuadrant(xAxis, yAxis);
            quadrants.computeIfAbsent(quadrant, _ -> new ArrayList<>()).add(robot);
        });

        return quadrants;
    }

    private static int calcSafetyScore(List<Robot> robots, int xMax, int yMax) {
        Map<Quadrant, List<Robot>> quadrants = splitByQuadrant(robots, xMax, yMax);
        quadrants.remove(Quadrant.AXIS);
        return quadrants.values().stream()
                .map(List::size)
                .reduce(1, (a, b) -> a * b);
    }

    private static List<Robot> parse(final Stream<String> data) {
        return data.map(line -> {
            String[] parts = line.substring(2).split("[pv=, ]+");
            return new Robot(
                    new Coord(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])),
                    new Coord(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]))
            );
        }).toList();
    }
}
