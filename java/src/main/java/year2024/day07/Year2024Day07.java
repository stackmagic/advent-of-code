package year2024.day07;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Year2024Day07 {

    public static long solvePart1(Stream<String> input) throws IOException {
        return solve(input, List.of(ADD, MUL));
    }

    public static long solvePart2(Stream<String> input) throws IOException {
        return solve(input, List.of(ADD, MUL, CON));
    }

    private static long solve(Stream<String> input, List<Op> ops) {
        return parse(input).stream()
                .map((Data data) -> solveEquation(data, ops))
                .reduce(0L, Long::sum);
    }

    private static long solveEquation(Data data, List<Op> ops) {
        TreeNode node = (TreeNode) buildNodes(data.expected, data.numbers(), ops);
        if (node.eval(null)) {
            return data.expected;
        }

        // no solutions
        return 0;
    }

    /**
     * build a "tree" of sorts where the nodes represent a number and the
     * connections correspond to the operations. except, it's more a list
     * because all the nodes on a given level are the same and the only
     * difference is the operation connecting them.
     */
    private static Node buildNodes(Long expected, List<Long> numbers, List<Op> ops) {
        if (numbers.size() == 1) {
            return new LeafNode(ops, numbers.getFirst(), expected);
        }

        Long x = numbers.getFirst();
        List<Long> xs = numbers.subList(1, numbers.size());
        Node remainder = buildNodes(expected, xs, ops);

        return new TreeNode(ops, x, remainder);
    }

    interface Op extends BiFunction<Long, Long, Long> {}

    private static final Op ADD = (Long sum, Long value) -> (sum == null ? 0 : sum) + value;
    private static final Op MUL = (Long sum, Long value) -> (sum == null ? 1 : sum) * value;
    private static final Op CON = (Long sum, Long value) -> Long.parseLong((sum == null ? "" : sum.toString()) + value);

    interface Node {
        boolean eval(Long sum);
    }

    record TreeNode(List<Op> ops, long value, Node remainder) implements Node {
        public boolean eval(Long sum) {
            return ops.stream()
                    .anyMatch(op -> remainder.eval(op.apply(sum, value)));
        }
    }

    record LeafNode(List<Op> ops, long value, long expected) implements Node {
        public boolean eval(Long sum) {
            return ops.stream()
                    .map(op -> op.apply(sum, value))
                    .anyMatch(it -> it.equals(expected));
        }
    }

    record Data(Long expected, List<Long> numbers) {}

    private static List<Data> parse(Stream<String> input) {
        return input.map(line -> {
            String[] pri = line.split(":");
            long expected = Long.parseLong(pri[0]);
            List<Long> numbers = Arrays.stream(pri[1].trim().split("\\s+")).map(Long::parseLong).collect(Collectors.toList());
            return new Data(expected, numbers);
        }).toList();
    }
}
