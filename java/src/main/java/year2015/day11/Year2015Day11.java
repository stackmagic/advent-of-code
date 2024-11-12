package year2015.day11;

public class Year2015Day11 {

    private static boolean checkLetters(String pw) {
        byte[] bytes = pw.getBytes();
        for (int i = 0; i < bytes.length - 2; i++) {
            if ((bytes[i] + 1) == bytes[i + 1] && (bytes[i] + 2) == bytes[i + 2]) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkValidChars(String pw) {
        return !pw.contains("i") &&
                !pw.contains("o") &&
                !pw.contains("l");
    }

    private static boolean checkPairs(String pw) {
        byte[] bytes = pw.getBytes();
        int pairs = 0;
        for (int i = 0; i < bytes.length - 1; i++) {
            if (bytes[i] == bytes[i + 1]) {
                pairs++;
                i++;
            }
            if (pairs >= 2) {
                return true;
            }
        }

        return false;
    }

    public static boolean check(String pw) {
        return checkLetters(pw) &&
                checkValidChars(pw) &&
                checkPairs(pw);
    }

    public static String increment(String pw) {
        byte[] bytes = pw.getBytes();

        // increment
        bytes[bytes.length - 1]++;

        // process carryover
        for (int i = bytes.length - 1; i >= 0; i--) {
            if (bytes[i] > 'z') {
                bytes[i] = 'a';
                if (i > 0) {
                    // increment next char
                    bytes[i - 1]++;
                }

                // if we had an overflow at the leftmost position, we need
                // to add another character and after that we're done anyway
                if (i == 0) {
                    byte[] newBytes = new byte[bytes.length + 1];
                    System.arraycopy(bytes, 0, newBytes, 1, bytes.length);
                    newBytes[0] = 'a';

                    bytes = newBytes;
                }
            }
        }

        return new String(bytes);
    }

    public static String getNextPassword(String pw) {
        do {
            pw = increment(pw);
        } while (!check(pw));

        return pw;
    }
}
