package year2024.day17;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Year2024Day17 {

    public static String solvePart1(List<String> data) throws IOException {
        Computer computer = new Computer(data);
        return computer.runToEnd();
    }

    public static String solvePart2(List<String> data) throws IOException {
        return "";
    }

    private static final class Computer {

        private long a;
        private long b;
        private long c;
        private int ip;
        private int[] program;
        private final List<Integer> output = new ArrayList<>();

        private Computer(List<String> data) {
            init(data);
        }

        private void init(List<String> data) {
            a = Long.parseLong(data.get(0).split(" ")[2]);
            b = Long.parseLong(data.get(1).split(" ")[2]);
            c = Long.parseLong(data.get(2).split(" ")[2]);
            ip = 0;
            program = Arrays.stream(data.get(4).split(" ")[1].split(",")).mapToInt(Integer::parseInt).toArray();
        }

        private String runToEnd() {
            try {
                while (true) {
                    step();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // done
            }

            return output.stream()
                    .map(Long::toString)
                    .collect(Collectors.joining(","));
        }

        private void step() {
            int instruction = program[ip];
            int operand = program[ip + 1];

            switch (instruction) {
                case 0 -> a = (long) (a / Math.pow(2, operand));
                case 1 -> b = b ^ operand;
                case 2 -> b = operand % 8;
                case 3 -> {
                    if (a != 0) {
                        ip = operand;
                        return;
                    }
                }
                case 4 -> b = b ^ c;
                case 5 -> output.add(operand % 8);
                case 6 -> b = (long) (a / Math.pow(2, operand));
                case 7 -> c = (long) (a / Math.pow(2, operand));
            }

            ip += 2;
        }
    }
}
