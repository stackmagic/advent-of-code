package year2015.day04;

import java.security.MessageDigest;

public class Year2015Day04 {

    public static long solve(String hashPrefix) throws Exception {
        String prefix = "iwrupvqb";
        long num = 0L;
        MessageDigest digest = MessageDigest.getInstance("MD5");
        while (true) {
            digest.reset();
            byte[] bytes = digest.digest((prefix + Long.toString(num)).getBytes());
            String hash = toHexString(bytes);

            if (hash.startsWith(hashPrefix)) {
                return num;
            }

            num++;
        }
    }

    // https://stackoverflow.com/questions/332079/in-java-how-do-i-convert-a-byte-array-to-a-string-of-hex-digits-while-keeping-l
    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
