import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTable {
    private List<LinkedList<String>> items;
    private int size;

    public HashTable(int size) {
        this.size = size;
        this.items = new ArrayList<>(size);
        for(int i = 0; i < this.size; ++i) {
            this.items.add(new LinkedList<>());
        }
    }

    public Pair<Integer, Integer> add(String x) {
        Pair<Integer, Integer> pos = this.index(x);

        if (pos.getFirst() > -1) {
            return pos;
        }

        int posInHash = hash(x);

        LinkedList<String> list = items.get(posInHash);
        list.add(x);

        int posInLinkedList = list.size()-1;

        return new Pair<>(posInHash, posInLinkedList);
    }

    public Pair<Integer, Integer> index(String x) {
        for (int i = 0; i < this.size; ++i) {
            int posInLinkedList = this.items.get(i).indexOf(x);
            if (posInLinkedList != -1) {
                return new Pair<>(i, posInLinkedList);
            }
        }
        return new Pair<>(-1, -1);
    }

    private int hash(String x) {
        int sum = 0;
        for (int i = 0; i < x.length(); ++i) {
            sum += x.charAt(i);
        }
        return sum % size;
    }

    public List<LinkedList<String>> getItems() {
        return items;
    }
}
