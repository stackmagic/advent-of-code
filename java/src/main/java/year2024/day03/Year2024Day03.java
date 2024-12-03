package year2024.day03;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Year2024Day03 {

    public static int solvePart1(String line) throws IOException {
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(line);

        int sum = 0;
        while (matcher.find()) {
            int a = Integer.parseInt(matcher.group(1));
            int b = Integer.parseInt(matcher.group(2));
            sum += a * b;
        }

        return sum;
    }

    public static int solvePart2(String line) throws IOException {
        Pattern pattern = Pattern.compile("(do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\))");
        Matcher matcher = pattern.matcher(line);

        int sum = 0;
        boolean enabled = true;
        while (matcher.find()) {
            String all = matcher.group(0);
            if (all.equals("do()")) {
                enabled = true;
            } else if (all.equals("don't()")) {
                enabled = false;
            } else if (all.startsWith("mul(") && enabled) {
                int a = Integer.parseInt(matcher.group(2));
                int b = Integer.parseInt(matcher.group(3));
                sum += a * b;
            }
        }

        return sum;
    }
}
