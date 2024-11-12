package year2015.day10;

public class Year2015Day10 {

    public static String process(String input) {
        StringBuilder builder = new StringBuilder();
        byte[] bytes = input.getBytes();

        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];

            // look ahead until the character changes
            int j = i;
            while (j + 1 < bytes.length && b == bytes[j + 1]) {
                j++;
            }

            builder.append(j - i + 1);
            builder.append((char) b);
            i = j;
        }

        return builder.toString();
    }
}
