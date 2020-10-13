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

    public int add(String x) {
        int pos = this.index(x);
        if (pos > -1) {
            return pos;
        }

        pos = hash(x);

        LinkedList<String> list = items.get(pos);
        list.add(x);

        return pos;
    }

    public int index(String x) {
        for (int i = 0; i < this.size; ++i) {
            if (this.items.get(i).contains(x)) {
                return i;
            }
        }
        return -1;
    }

    private int hash(String x) {
        int sum = 0;
        for (int i = 0; i < x.length(); ++i) {
            sum += x.charAt(i);
        }
        return sum % size;
    }
}
