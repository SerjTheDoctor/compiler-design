package fa;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import ds.Pair;
import program.Language;

public class FiniteAutomata {
    public List<String> allStates;
    public List<String> alphabet;
    public String startState;
    public List<String> finalStates;
    public Map<Pair<String, String>, List<String>> transitions;
    public Boolean deterministic = true;

    public void readFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            allStates = Arrays.asList(br.readLine().split(", "));
            alphabet = Arrays.asList(br.readLine().split(", "));
            startState = br.readLine().strip();
            finalStates = Arrays.asList(br.readLine().split(", "));

            String line;
            transitions = new HashMap<>();
            while((line = br.readLine()) != null) {
                String[] literals = line.split(",|=");
                String fromState = literals[0].strip();
                String symbol = literals[1].strip();
                String toState = literals[2].strip();

                Pair<String, String> transition = new Pair<>(fromState, symbol);
                if (transitions.containsKey(transition)) {
                    List<String> transitionStates = transitions.get(transition);
                    transitionStates.add(toState);
                    deterministic = false;
                } else {
                    List<String> transitionStates = new ArrayList<>();
                    transitionStates.add(toState);
                    transitions.put(transition, transitionStates);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean verifySequence(String seq) {
        System.out.println("Sequence: " + seq);
        String currentState = startState;
        for (int i = 0; i < seq.length(); i++) {
            String symbol = seq.substring(i, i+1);
            System.out.println("Checking δ(" + currentState + ", " + symbol + ")");

            Pair<String, String> currentTransition = new Pair<>(currentState, symbol);
            if (transitions.containsKey(currentTransition)) {
                currentState = transitions.get(currentTransition).get(0);
            } else {
                return false;
            }
        }

        return finalStates.contains(currentState);
    }

    private void addTransition(String fromState, String symbol, String toState) {
        transitions.put(new Pair<>(fromState, symbol), Collections.singletonList(toState));
    }

    public void initIdentifierFA() {
        allStates = Arrays.asList("q0", "q1");

        alphabet = new ArrayList<>();
        alphabet.addAll(Language.lowercaseLetters);
        alphabet.addAll(Language.uppercaseLetters);
        alphabet.addAll(Language.digits.stream().map(Object::toString).collect(Collectors.toList()));
        alphabet.add("_");

        startState = "q0";
        finalStates = Collections.singletonList("q1");

        /* () illustrates "final state"
            q0, { a, b, ..., z, A, B, ..., Z, _ } -> (q1)
            q1, { a, b, ..., z, A, B, ..., Z, _, 0, 1, ..., 9 } -> (q1)
         */
        transitions = new HashMap<>();
        Language.lowercaseLetters.forEach(letter -> {
            transitions.put(new Pair<>("q0", letter), Collections.singletonList("q1"));
            transitions.put(new Pair<>("q1", letter), Collections.singletonList("q1"));
        });
        Language.uppercaseLetters.forEach(letter -> {
            transitions.put(new Pair<>("q0", letter), Collections.singletonList("q1"));
            transitions.put(new Pair<>("q1", letter), Collections.singletonList("q1"));
        });
        Language.digits.forEach(digit ->
                transitions.put(new Pair<>("q1", digit.toString()), Collections.singletonList("q1"))
        );
        transitions.put(new Pair<>("q0", "_"), Collections.singletonList("q1"));
        transitions.put(new Pair<>("q1", "_"), Collections.singletonList("q1"));

    }

    public void initIntegerConstantFA() {
        allStates = Arrays.asList("q0", "q1", "q2", "q3");

        alphabet = new ArrayList<>();
        alphabet.addAll(Language.digits.stream().map(Object::toString).collect(Collectors.toList()));
        alphabet.addAll(Arrays.asList("+", "-"));

        startState = "q0";
        finalStates = Arrays.asList("q1", "q3");

        /*
            q0, 0 -> (q1)
            q0, { +, - } -> q2
            q0, { 1, ..., 9 } -> (q3)
            q2, { 1, ..., 9 } -> (q3)
            q3, { 0, ..., 9 } -> (q3)
         */
        transitions = new HashMap<>();
        addTransition("q0", "0", "q1");
        addTransition("q0", "+", "q2");
        addTransition("q0", "-", "q2");
        Language.digits.stream().filter(d -> d != 0).forEach(digit -> {
            addTransition("q0", digit.toString(), "q1");
            addTransition("q2", digit.toString(), "q3");
        });
        Language.digits.forEach(digit ->
            addTransition("q3", digit.toString(), "q3")
        );
    }
}
