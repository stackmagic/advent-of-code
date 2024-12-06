package year2024.day04;

import java.io.IOException;

public class Year2024Day04 {

    public static int solvePart1(char[][] grid) throws IOException {
        int count = 0;
        int xlen = grid.length;
        for (int x = 0; x < grid.length; x++) {
            int ylen = grid[x].length;
            for (int y = 0; y < grid[x].length; y++) {
                if (grid[x][y] == 'X') {

                    // todo find a less verbose, more clever but still
                    //  well understandable solution that maybe
                    //  can even solve both parts of the challenge

                    boolean xp = x + 3 < xlen;
                    boolean xm = x - 3 >= 0;
                    boolean yp = y + 3 < ylen;
                    boolean ym = y - 3 >= 0;

                    if (xp &&
                            grid[x + 1][y] == 'M' &&
                            grid[x + 2][y] == 'A' &&
                            grid[x + 3][y] == 'S') {
                        count++;
                    }

                    if (xp && ym &&
                            grid[x + 1][y - 1] == 'M' &&
                            grid[x + 2][y - 2] == 'A' &&
                            grid[x + 3][y - 3] == 'S') {
                        count++;
                    }

                    if (ym &&
                            grid[x][y - 1] == 'M' &&
                            grid[x][y - 2] == 'A' &&
                            grid[x][y - 3] == 'S') {
                        count++;
                    }

                    if (xm && ym &&
                            grid[x - 1][y - 1] == 'M' &&
                            grid[x - 2][y - 2] == 'A' &&
                            grid[x - 3][y - 3] == 'S') {
                        count++;
                    }

                    if (xm &&
                            grid[x - 1][y] == 'M' &&
                            grid[x - 2][y] == 'A' &&
                            grid[x - 3][y] == 'S') {
                        count++;
                    }

                    if (xm && yp &&
                            grid[x - 1][y + 1] == 'M' &&
                            grid[x - 2][y + 2] == 'A' &&
                            grid[x - 3][y + 3] == 'S') {
                        count++;
                    }

                    if (yp &&
                            grid[x][y + 1] == 'M' &&
                            grid[x][y + 2] == 'A' &&
                            grid[x][y + 3] == 'S') {
                        count++;
                    }

                    if (xp && yp &&
                            grid[x + 1][y + 1] == 'M' &&
                            grid[x + 2][y + 2] == 'A' &&
                            grid[x + 3][y + 3] == 'S') {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static int solvePart2(char[][] grid) throws IOException {
        int count = 0;
        for (int x = 1; x < grid.length - 1; x++) {
            for (int y = 1; y < grid[x].length - 1; y++) {
                if (grid[x][y] == 'A') {
                    char tl = grid[x + 1][y - 1];
                    char tr = grid[x + 1][y + 1];
                    char bl = grid[x - 1][y - 1];
                    char br = grid[x - 1][y + 1];

                    if (((tl == 'M' && br == 'S') || (tl == 'S' && br == 'M')) &&
                            ((tr == 'M' && bl == 'S') || (tr == 'S' && bl == 'M'))) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
