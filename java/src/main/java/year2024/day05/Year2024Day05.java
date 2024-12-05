package year2024.day05;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class Year2024Day05 {

    private static Input parse(Stream<String> data) {
        Map<Integer, List<Integer>> rules = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();

        data.forEach(line -> {
            List<Integer> split = Arrays.stream(line.split("[|,]"))
                    .filter(it -> !it.isEmpty())
                    .map(Integer::parseInt).toList();
            if (split.isEmpty()) {
                return;
            }
            // ordering rules
            if (split.size() == 2) {
                int from = split.get(0);
                int to = split.get(1);
                rules.computeIfAbsent(from, _ -> new ArrayList<>()).add(to);
            }
            // update
            else {
                updates.add(split);
            }
        });

        return new Input(rules, updates);
    }

//    private static boolean isValidOrdering(List<Integer> update, Map<Integer, List<Integer>> rules) {
//        for (int i = 0; i < update.size() - 1; i++) {
//            int num = update.get(i);
//            for (int j = i + 1; j < update.size(); j++) {
//                int next = update.get(j);
//                if (!rules.containsKey(num) || !rules.get(num).contains(next)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    private static Stream<List<Integer>> filterOrder(Input input, boolean onlyCorrect) {
//        return input.updates.stream()
//                .filter(update -> {
//                    boolean valid = isValidOrdering(update, input.rules);
//                    if (onlyCorrect) {
//                        return valid;
//                    } else {
//                        return !valid;
//                    }
//                });
//    }

    public static int solvePart1(Stream<String> data) throws IOException {
        Input input = parse(data);
        Verifyer verifyer = new Verifyer(input.rules);
        return input.updates.stream()
                .filter(verifyer::isValid)
                .mapToInt(Year2024Day05::getListMiddle)
                .reduce(0, Integer::sum);
    }


//    public static int solvePart1(Stream<String> data) throws IOException {
//        Input input = parse(data);
//        return filterOrder(input, true)
//                .mapToInt(it -> it.get(it.size() / 2))
//                .reduce(0, Integer::sum);
//
//    }

    public static int solvePart2(Stream<String> data) throws IOException {
        Input input = parse(data);
        Verifyer verifyer = new Verifyer(input.rules);
        RuleBasedComparator comparator = new RuleBasedComparator(input.rules);
        return input.updates.stream()
                .filter(verifyer::notIsValid)
                .map(ArrayList::new)
                .peek(it -> it.sort(comparator))
                .map(Year2024Day05::getListMiddle)
                .reduce(0, Integer::sum);
    }

    private record Input(Map<Integer, List<Integer>> rules, List<List<Integer>> updates) {}

    private static class Verifyer {
        private final Comparator<Integer> comparator;

        private Verifyer(Map<Integer, List<Integer>> rules) {
            comparator = new RuleBasedComparator(rules);
        }

        public boolean isValid(List<Integer> update) {
            List<Integer> sorted = new ArrayList<>(update);
            sorted.sort(comparator);
            return update.equals(sorted);
        }

        public boolean notIsValid(List<Integer> update) {
            return !isValid(update);
        }
    }

    private static class RuleBasedComparator implements Comparator<Integer> {
        private final Map<Integer, List<Integer>> rules;

        public RuleBasedComparator(Map<Integer, List<Integer>> rules) {
            this.rules = rules;
        }

        @Override
        public int compare(Integer a, Integer b) {
            if (rules.containsKey(a) && rules.get(a).contains(b)) {
                return -1;
            }
            if (rules.containsKey(b) && rules.get(b).contains(a)) {
                return 1;
            }
            return 0;
        }
    }

    public static int getListMiddle(List<Integer> list) {
        return list.get(list.size() / 2);
    }
}
