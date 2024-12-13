package year2024.day13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Year2024Day13 {

    public static long solvePart1(List<String> data) throws IOException {
        return parse(data, false).stream()
                .map(MachineInfo::solve)
                .reduce(0L, Long::sum);
    }

    public static long solvePart2(List<String> data) throws IOException {
        return parse(data, true).stream()
                .map(MachineInfo::solve)
                .reduce(0L, Long::sum);
    }

    private record Prize(long x, long y) {}

    private record Button(long x, long y, int cost) {}

    private record MachineInfo(Button a, Button b, Prize p) {

        private long solve() {
            // ia/ib are the factors of how many times buttons a/b needs to be pressed to reach prize p
            //
            // X = ia * a.x + ib * b.x
            // Y = ia * a.y + ib * b.y
            //
            // solve the first formula for ia and substitute ia in the second, this results
            // in the solution for ib. then use the first formula, insert ib and calculate ia
            long ib = (a.y * p.x - a.x * p.y) / (a.y * b.x - a.x * b.y);
            long ia = (p.y - ib * b.y) / a.y;

            // the x/y coordinates we'd get when using the calculated factors
            long x = ia * a.x + ib * b.x;
            long y = ia * a.y + ib * b.y;

            // if x/y are the same as the prize location, it's a valid solution
            if (x == p.x && y == p.y) {
                return ia * a.cost + ib * b.cost;
            }

            // no solution - no prize :(
            return 0;
        }
    }

    private static List<MachineInfo> parse(List<String> data, boolean insanity) {
        List<MachineInfo> rv = new ArrayList<>();
        int max = Math.ceilDiv(data.size(), 4);
        for (int i = 0; i < max; i++) {
            Button a = parseButton(data.get(4 * i));
            Button b = parseButton(data.get(4 * i + 1));
            Prize p = parsePrize(data.get(4 * i + 2), insanity);
            rv.add(new MachineInfo(a, b, p));
        }
        return rv;
    }

    private static Button parseButton(String s) {
        if (!s.startsWith("Button ")) {
            throw new IllegalArgumentException(s);
        }
        s = s.substring(7);
        char ab = s.charAt(0);
        int cost = switch (ab) {
            case 'A' -> 3;
            case 'B' -> 1;
            default -> throw new IllegalArgumentException(s);
        };
        s = s.replaceAll("[ABXY:,+ ]+", " ").trim();
        String[] split = s.split(" ");
        long x = Long.parseLong(split[0]);
        long y = Long.parseLong(split[1]);
        return new Button(x, y, cost);
    }

    private static Prize parsePrize(String s, boolean insanity) {
        if (!s.startsWith("Prize: X")) {
            throw new IllegalArgumentException(s);
        }
        s = s.replaceAll("[a-zA-Z:,= ]+", " ").trim();
        String[] split = s.split("\\s");
        long x = Long.parseLong(split[0]);
        long y = Long.parseLong(split[1]);
        if (insanity) {
            return new Prize(10000000000000L + x, 10000000000000L + y);
        }
        return new Prize(x, y);
    }
}
