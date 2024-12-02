package year2024.day02;

import java.util.ArrayList;
import java.util.List;

import static utils.Utils.intLineStream;

public class Year2024Day02 {

    public static boolean isSafePart1(List<Integer> list) {
        List<Integer> copy = new ArrayList<>(list);
        copy.sort(Integer::compareTo);
        List<Integer> reve = new ArrayList<>(copy).reversed();
        if (!copy.equals(list) && !reve.equals(list)) {
            return false;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            int diff = Math.abs(list.get(i) - list.get(i + 1));
            if (diff > 3 || diff < 1) {
                return false;
            }
        }
        return true;
    }

    public static long solvePart1(String filename) {
        return intLineStream(filename)
                .filter(Year2024Day02::isSafePart1)
                .count();
    }

    public static boolean isSafePart2(List<Integer> list) {
        if (isSafePart1(list)) {
            return true;
        }

        for (int skip = 0; skip < list.size(); skip++) {
            List<Integer> clean = new ArrayList<>(list);
            //noinspection SuspiciousListRemoveInLoop
            clean.remove(skip);
            if (isSafePart1(clean)) {
                return true;
            }
        }

        return false;
    }

    public static long solvePart2(String filename) {
        return intLineStream(filename)
                .filter(Year2024Day02::isSafePart2)
                .count();
    }
}
