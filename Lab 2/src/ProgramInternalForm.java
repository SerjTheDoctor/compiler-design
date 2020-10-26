import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    private List<Pair<String, Pair<Integer, Integer>>> form;

    public ProgramInternalForm() {
        form = new ArrayList<>();
    }

    public void add(String token, Pair<Integer, Integer> position) {
        Pair<String, Pair<Integer, Integer>> element = new Pair<>(token, position);
        form.add(element);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n-- ProgramInternalForm --\n");

        for (Pair<String, Pair<Integer, Integer>> element : form) {
            str.append(element.getFirst()).append(": ").append(element.getSecond()).append('\n');
        }

        str.append("--------------\n");
        return str.toString();
    }
}
