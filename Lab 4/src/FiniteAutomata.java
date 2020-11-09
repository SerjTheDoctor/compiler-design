import java.io.*;
import java.util.*;

public class FiniteAutomata {
    public List<String> allStates;
    public List<String> alphabet;
    public String q0;
    public List<String> finalStates;
    public Map<Pair<String, String>, List<String>> transitions;
    public Boolean deterministic = true;

    public void readFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            allStates = Arrays.asList(br.readLine().split(", "));
            alphabet = Arrays.asList(br.readLine().split(", "));
            q0 = br.readLine().strip();
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

    public boolean verifySequence(String seq) throws NonDeterministicException {
        if (!deterministic) {
            throw new NonDeterministicException();
        }

        System.out.println("Sequence: " + seq);
        String currentState = q0;
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
}
