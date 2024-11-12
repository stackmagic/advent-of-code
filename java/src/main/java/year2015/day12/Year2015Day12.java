package year2015.day12;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import utils.Utils;

import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;

public class Year2015Day12 {

    public static int sum(JsonElement element, boolean filterRed) {
        if (element.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
            if (filterRed) {
                boolean containsRed = entries.stream()
                        .map(Map.Entry::getValue)
                        .filter(JsonElement::isJsonPrimitive)
                        .map(JsonElement::getAsString)
                        .anyMatch("red"::equals);
                if (containsRed) {
                    return 0;
                }
            }
            return entries.stream()
                    .map(Map.Entry::getValue)
                    .map(it -> sum(it, filterRed))
                    .reduce(0, Integer::sum);
        }
        if (element.isJsonArray()) {
            return StreamSupport.stream(element.getAsJsonArray().spliterator(), true)
                    .map(it -> sum(it, filterRed))
                    .reduce(0, Integer::sum);
        }
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isNumber()) {
                return primitive.getAsInt();
            }
        }
        return 0;
    }

    public static int process(String json) {
        JsonElement element = Utils.GSON.fromJson(json, JsonElement.class);
        return sum(element, false);
    }

    public static int processNoRed(String json) {
        JsonElement element = Utils.GSON.fromJson(json, JsonElement.class);
        return sum(element, true);
    }
}
