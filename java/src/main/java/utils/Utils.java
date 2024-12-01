package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Utils {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Utils() {}

    /** load a file from src/main/resources from the same package as the caller */
    public static Stream<String> lineStream(String filename) {
        return getBufferedReader(filename).lines();
    }

    public static List<String> lineList(String filename) {
        return getBufferedReader(filename).lines().collect(Collectors.toList());
    }

    public static BufferedReader getBufferedReader(String filename) {
        InputStream is = getResourceAsStream(filename);
        InputStreamReader ir = new InputStreamReader(is);
        return new BufferedReader(ir);
    }

    public static String file(String filename) throws IOException {
        try (InputStream is = getResourceAsStream(filename)) {
            return new String(is.readAllBytes());
        }
    }

    public static InputStream getResourceAsStream(String filename) {
        StackTraceElement[] trace = new Exception().getStackTrace();
        String path = Arrays.stream(trace)
                .map(StackTraceElement::getClassName)
                .filter(it -> !Utils.class.getName().equals(it))
                .findFirst()
                .get()
                .replace(".", "/")
                .replaceAll("[A-Z].*$", filename);
        ClassLoader classLoader = Utils.class.getClassLoader();
        return Objects.requireNonNull(classLoader.getResourceAsStream(path));
    }
}
