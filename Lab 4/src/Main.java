import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NonDeterministicException {
        FiniteAutomata fa = new FiniteAutomata();
        fa.readFile("FA.in");

        printMenu();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("0")) {
            switch (input.strip()) {
                case "0":
                    return;
                case "1":
                    String states = ListToSetString(fa.allStates.toString());
                    System.out.println("Q = " + states);
                    break;
                case "2":
                    String alphabet = ListToSetString(fa.alphabet.toString());
                    System.out.println("Σ = " + alphabet);
                    break;
                case "3":
                    String finalStates = ListToSetString(fa.finalStates.toString());
                    System.out.println("F = " + finalStates);
                    break;
                case "4":
                    fa.transitions.forEach((transition, values) ->
                        values.forEach(value -> {
                            System.out.println("δ(" + transition.getFirst() + "," + transition.getSecond() + ") = " + value);
                        })
                    );
                    break;
                case "5":
                    if (!fa.deterministic) {
                        System.out.println("This finite automata is not deterministic");
                        break;
                    }
                    String seq = scanner.nextLine();
                    System.out.println("Sequence is " + (fa.verifySequence(seq) ? "" : "not ") + "accepted");
                    break;
                default:
                    System.out.println("Upsi, try again");
                    printMenu();
                    break;
            }

            input = scanner.nextLine();
        }
    }

    public static void printMenu() {
        String str = "1. The states\n" +
                     "2. The alphabet\n" +
                     "3. The final state\n" +
                     "4. The transitions\n" +
                     "5. Verify sequence\n" +
                     "0. Exit\n" +
                     "\n>";
        System.out.println(str);
    }

    public static String ListToSetString(String list) {
        return list
                .replace(",", ", ")
                .replace('[', '{')
                .replace(']', '}');
    }
}
