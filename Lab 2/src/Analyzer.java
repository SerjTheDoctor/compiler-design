import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Analyzer {
    private final SymbolTable st;
    private final ProgramInternalForm pif;
    private List<String> operators;
    private List<String> separators;
    private List<String> reservedWords;

    public Analyzer(String tokensFileName) {
        st = new SymbolTable();
        pif = new ProgramInternalForm();

        try {
            loadTokens(tokensFileName);

            System.out.println("\nOperators: " + operators.toString());
            System.out.println("Separators: " + separators.toString());
            System.out.println("Reserved words: " + reservedWords.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTokens(String tokensFileName) throws IOException {
        File file = new File(tokensFileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        operators = Arrays.asList(br.readLine().strip().split(" "));
        separators = Arrays.asList(br.readLine().strip().split(" "));
        reservedWords = Arrays.asList(br.readLine().strip().split(" "));
    }

    public void scan(String inputFileName, String pifFileName, String stFileName) throws IOException {
        File file = new File(inputFileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        int i = 0;
        String line = br.readLine();
        while (line != null) {
//            System.out.println("\nOn line: '" + line + "'");

            List<String> tokens = null;
            try {
                tokens = detect(line, i);
            } catch (LexicalException e) {
                System.out.println(e.getMessage());
                br.close();
                return;
            }
//            System.out.println("Detected: " + tokens);

            for (String token : tokens) {
//                printTokenType(token);
                if (isOperator(token) || isSeparator(token) || isReservedWord(token)) {
                    pif.add(token, new Pair<>(-1, -1));
//                    System.out.println("PIF('" + token + "', (-1, -1))");
                } else if (isIdentifier(token) || isConstant(token)){
                    Pair<Integer, Integer> index = st.insert(token);
                    pif.add(token, index);
//                    System.out.println("PIF('" + token + "', " + index + ")");
                } else {
                    System.out.println("Lexical Error on line " + i + " at token " + token);
                }
            }

            i++;
            line = br.readLine();
        }

        System.out.println("Lexically correct");

        br.close();

        writeToFile(st.toString(), stFileName);
        writeToFile(pif.toString(), pifFileName);
    }

    private void writeToFile(String text, String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
        bw.write(text);
        bw.close();
    }

    private List<String> detect(String line, Integer index) throws LexicalException {
        List<String> tokens = new ArrayList<>();

        int commentIndex = line.indexOf(Language.COMMENT);
        if (commentIndex != -1) {
            line = line.substring(0, commentIndex);
        }

        int start = 0, end = 0;
        while (end < line.length()) {
            String endCharacter = line.substring(end, end + 1);

            while (!line.startsWith(Language.SPACE, end) && !isSeparator(endCharacter) && !isOperator(endCharacter)) {
                end++;
                endCharacter = line.substring(end, end + 1);
            }

            String word = line.substring(start, end);

            if (isToken(word)) {
                // Add token, go to end of token
                tokens.add(word);
                start = end;
            } else if (!word.isBlank()) {
                // Non empty word is not a token => lexical error !
                throw new LexicalException("LexicalError (" + index + ", " + start + "): " + word);
            }
            if (isSeparator(endCharacter)) {
                // Separators are marked as tokens too
                // so we add them
                if (endCharacter.equals('\"')) {

                } else {
                    tokens.add(endCharacter);
                }
            } else if (isOperator(endCharacter)) {
                // If the current operator is followed by an '=', it means it's a composed operator
                // ex: <=, ==, !=, >=
                if (end + 1 < line.length() && line.startsWith(Language.EQUAL, end+1)) {
                    tokens.add(line.substring(end, end + 2));
                    start++;
                    end++;
                } else {
                    tokens.add(endCharacter);
                }
            }

            start++;
            end++;
        }

        return tokens;
    }

    private boolean isToken(String str) {
        return isOperator(str) || isSeparator(str) || isReservedWord(str) ||
                isIdentifier(str) || isConstant(str);
    }

    private boolean isOperator(String str) {
        return operators.contains(str);
    }

    private boolean isSeparator(String str) {
        return separators.contains(str);
    }

    private boolean isReservedWord(String str) {
        return reservedWords.contains(str);
    }

    private boolean isIdentifier(String str) {
        return str.matches(Language.IDENTIFIER_TEST);
    }

    private boolean isConstant(String str) {
        return str.matches(Language.CONSTANT_TEST);
    }

    private void printTokenType(String token) {
        if (isOperator(token)) {
            System.out.println(token + " @ Operator");
        } else if (isSeparator(token)) {
            System.out.println(token + " @ Separator");
        } else if (isReservedWord(token)) {
            System.out.println(token + " @ ReservedWord");
        } else if (isConstant(token)) {
            System.out.println(token + " @ Constant");
        } else if (isIdentifier(token)) {
            System.out.println(token + " @ Identifier");
        } else {
            System.out.println(token + " @ UnknownType");
        }
    }
}
