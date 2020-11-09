package program;

import java.util.Arrays;
import java.util.List;

public class Language {
    public static final String EQUAL = "=";
    public static final String COMMENT = "..";
    public static final String SPACE = " ";

    public static final String IDENTIFIER_TEST = "^[a-zA-Z_][a-zA-Z_0-9]*$";
    public static final String CONSTANT_TEST = "^(0|[+-]?[1-9][0-9]*|yea|nah|\".*\"|'.*')$";
    public static final String NON_INTEGER_CONSTANT_TEST = "^(yea|nah|\".*\"|'.*')$";

    public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public static final List<String> lowercaseLetters = Arrays.asList(alphabet.split(""));
    public static final List<String> uppercaseLetters = Arrays.asList(alphabet.toUpperCase().split(""));

    public static final List<Integer> digits = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
}
