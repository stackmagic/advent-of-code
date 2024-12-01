package year2015.day07;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.Utils.lineStream;

public class Year2015Day07 {

    /** Map<Output, Operation> */
    private static final Map<String, Operation> ops = new HashMap<>();

    /** Map<Output, Value> */
    private static final Map<String, Character> vals = new HashMap<>();

    public static void setup() throws IOException {
        lineStream("data.txt").forEach(line -> {
            String[] split = line.split("->");
            String output = split[1].trim();
            Operation operation = createOperation(split[0]);
            ops.put(output, operation);
        });
    }

    public static char evaluate()  {
        return ops.get("a").evaluate();
    }

    public static void overrideSignalB(char signal) {
        Operation bOverride = createOperation(Integer.toString(signal));
        ops.put("b", bOverride);
        vals.clear();
    }

    private static Operation createOperation(String input) {

        String[] parts = input.split(" ");

        if (parts.length == 1) {
            try {
                int value = Integer.parseInt(parts[0]);
                return new SetValueOperation((char) value);
            } catch (NumberFormatException e) {
                return new PassTroughOperation(parts[0]);
            }
        }

        if (parts.length == 2) {
            return new NotOperation(parts[1]);
        }

        if (parts.length == 3) {
            String[] inputs = new String[]{parts[0], parts[2]};
            switch (parts[1]) {
                case "AND":
                    return new AndOperation(inputs);
                case "OR":
                    return new OrOperation(inputs);
                case "RSHIFT":
                    return new RightShiftOperation(parts[0], Integer.parseInt(parts[2]));
                case "LSHIFT":
                    return new LeftShiftOperation(parts[0], Integer.parseInt(parts[2]));
            }
        }

        System.out.println(input);
        throw new IllegalArgumentException(input);
    }

    private static String[] assertLength(String[] arr, int length) {
        if (arr.length != length) {
            throw new AssertionError("expected " + length + " elements but got " + arr.length);
        }
        return arr;
    }

    private interface Operation {
        char evaluate();
    }

    private static abstract class InputsOperation implements Operation {

        protected final String[] inputs;

        public InputsOperation(String[] inputs) {
            this.inputs = inputs;
        }

        protected char[] getValues() {
            char[] rv = new char[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                String input = inputs[i];
                Character val = vals.get(input);
                if (null == val) {
                    try {
                        rv[i] = (char) Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        Operation op = ops.get(input);
                        char result = op.evaluate();
                        rv[i] = result;
                    }
                    vals.put(input, rv[i]);
                } else {
                    rv[i] = val;
                }
            }
            return rv;
        }
    }

    private static class AndOperation extends InputsOperation {

        public AndOperation(String[] inputs) {
            super(assertLength(inputs, 2));
        }

        @Override
        public char evaluate() {
            char[] values = getValues();
            return (char) (values[0] & values[1]);
        }

        @Override
        public String toString() {
            return inputs[0] + " AND " + inputs[1];
        }
    }

    private static class OrOperation extends InputsOperation {

        public OrOperation(String[] inputs) {
            super(assertLength(inputs, 2));
        }

        @Override
        public char evaluate() {
            char[] values = getValues();
            return (char) (values[0] | values[1]);
        }

        @Override
        public String toString() {
            return inputs[0] + " OR " + inputs[1];
        }
    }

    private static class NotOperation extends InputsOperation {

        public NotOperation(String input) {
            super(new String[]{input});
        }

        @Override
        public char evaluate() {
            return (char) ~getValues()[0];
        }

        @Override
        public String toString() {
            return "NOT " + inputs[0];
        }
    }

    private static abstract class ShiftOperation extends InputsOperation {

        protected final int amount;

        public ShiftOperation(String input, int amount) {
            super(new String[]{input});
            this.amount = amount;
        }

        public char getValue() {
            return getValues()[0];
        }
    }

    private static class RightShiftOperation extends ShiftOperation {

        public RightShiftOperation(String input, int amount) {
            super(input, amount);
        }

        @Override
        public char evaluate() {
            return (char) (getValue() >>> amount);
        }

        @Override
        public String toString() {
            return inputs[0] + " RSHIFT " + amount;
        }
    }

    private static class LeftShiftOperation extends ShiftOperation {

        public LeftShiftOperation(String input, int amount) {
            super(input, amount);
        }

        @Override
        public char evaluate() {
            return (char) (getValue() << amount);
        }

        @Override
        public String toString() {
            return inputs[0] + " LSHIFT " + amount;
        }
    }

    private static class PassTroughOperation extends InputsOperation {

        public PassTroughOperation(String input) {
            super(new String[]{input});
        }

        @Override
        public char evaluate() {
            return getValues()[0];
        }

        @Override
        public String toString() {
            return inputs[0];
        }
    }

    private static class SetValueOperation implements Operation {

        private final char value;

        public SetValueOperation(char value) {
            this.value = value;
        }

        @Override
        public char evaluate() {
            return value;
        }

        @Override
        public String toString() {
            return Integer.toString((int) value);
        }
    }
}