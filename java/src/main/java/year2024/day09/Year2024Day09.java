package year2024.day09;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Year2024Day09 {

    public static String toString(List<Integer> blockMap) {
        StringBuilder builder = new StringBuilder();
        for (int value : blockMap) {
            if (value == -1) {
                builder.append(".");
            } else {
                builder.append(value);
            }
        }
        return builder.toString();
    }

    public static long solvePart1(String data) throws IOException {
        List<Integer> blockMap = toBlockMap(data);
        compact(blockMap);
        return checksum(blockMap);
    }

    public static long solvePart2(String data) throws IOException {
        List<Integer> blockMap = toBlockMap(data);
        compact2(blockMap);
        return checksum(blockMap);
    }

    private static long checksum(List<Integer> blockMap) {
        long sum = 0;
        for (int i = 0; i < blockMap.size(); i++) {
            int value = blockMap.get(i);
            if (value != -1) {
                sum += i * Long.parseLong(String.valueOf(value));
            }
        }
        return sum;
    }

    public static void compact2(List<Integer> blockMap) {
        final int len = blockMap.size();
        int l = 0;
        int r = len - 1;
        Set<Integer> processedFiles = new HashSet<>();

        while (r >= 0) {

            // get file
            while (blockMap.get(r) == -1) {r--;}
            int fileId = blockMap.get(r);
            int size = 1;
            while (r - size >= 0 && blockMap.get(r - size) == fileId) {size++;}

            // file already moved? skip
            if (!processedFiles.add(fileId)) {
                r -= size;
                continue;
            }

            // look for empty space
            l = -1;
            int avail = 0;
            while (l + avail < r - size && size > avail) {
                l++;
                while (blockMap.get(l) != -1) {l++;}
                avail = 1;
                while (l + avail < len && blockMap.get(l + avail) == -1) {avail++;}
            }

            // move file if possible
            if (size <= avail && l >= 0 && l < r) {
                for (int i = 0; i < size; i++) {
                    int ll = blockMap.get(l + i);
                    int rr = blockMap.get(r - i);
                    blockMap.set(l + i, rr);
                    blockMap.set(r - i, ll);
                }
            }

            // file processed or no room found for file, start searching for space from beginning again and skip over this file
            r -= size;
        }
    }

    public static void compact(List<Integer> blockMap) {
        int l = 0;
        int r = blockMap.size() - 1;

        while (true) {
            // find rightmost block
            while (blockMap.get(r) == -1) {r--;}
            // find leftmost space
            while (blockMap.get(l) != -1) {l++;}

            // done
            if (l >= r) {
                break;
            }

            // swap
            int ll = blockMap.get(l);
            int rr = blockMap.get(r);
            blockMap.set(l, rr);
            blockMap.set(r, ll);
        }
    }

    public static List<Integer> toBlockMap(String data) {
        char[] inputString = data.toCharArray();
        List<Integer> blockMap = new ArrayList<>();

        int blockId = 0;
        for (int i = 0; i < inputString.length; i++) {

            char blockSpace = inputString[i];
            int blockSpaceInt = Integer.parseInt(String.valueOf(blockSpace));

            // block
            if (i % 2 == 0) {
                for (int j = 0; j < blockSpaceInt; j++) {
                    blockMap.add(blockId);
                }

                blockId++;
            }
            // space
            else {
                for (int j = 0; j < blockSpaceInt; j++) {
                    blockMap.add(-1);
                }
            }
        }

        return blockMap;
    }
}
