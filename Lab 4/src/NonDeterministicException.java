public class NonDeterministicException extends Exception {
    public NonDeterministicException() {
        super("This finite automata is not deterministic");
    }
}
