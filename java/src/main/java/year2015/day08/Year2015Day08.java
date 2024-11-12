package year2015.day08;

import java.io.IOException;

import static utils.Utils.lines;

public class Year2015Day08 {

    public static int solvePart1(String file) throws IOException {
        return lines(file)
                .map(line -> {

                    int s = 0;
                    byte[] bytes = line.getBytes();
                    for (int b = 0; b < bytes.length; b++) {

                        // starting "
                        if (b == 0 && bytes[b] == '"') {
                            continue;
                        }

                        // escape sequences
                        else if (bytes[b] == '\\') {

                            // 1 extra char available
                            if (b + 1 < bytes.length) {

                                // quote
                                if (bytes[b + 1] == '"') {
                                    s++;
                                    b++;
                                    continue;
                                }
                                // backslash
                                else if (bytes[b + 1] == '\\') {
                                    s++;
                                    b++;
                                    continue;
                                }

                                // hex escape sequence
                                else if (b + 3 < bytes.length && bytes[b + 1] == 'x') {
                                    s++;
                                    b += 3;
                                    continue;
                                }
                            }
                        }

                        // ending "
                        else if (b + 1 == bytes.length) {
                            continue;
                        }

                        // normal character
                        else {
                            s++;
                        }
                    }

                    return line.length() - s;
                })
                .reduce(0, Integer::sum);
    }

    public static int solvePart2(String file) throws IOException {
        return lines(file)
                .map(line -> {

                    int s = 2; // starting and ending "
                    byte[] bytes = line.getBytes();
                    for (byte b : bytes) {
                        switch (b) {
                            case '"':
                            case '\\':
                                s += 2;
                                break;
                            default:
                                s++;
                        }
                    }

                    return s - line.length();
                })
                .reduce(0, Integer::sum);
    }
}
