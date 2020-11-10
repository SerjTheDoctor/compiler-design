package program;

import ds.HashTable;
import ds.Pair;

public class SymbolTable {
    private final int SIZE = 10;
    private HashTable hashTable;

    public SymbolTable() {
        this.hashTable = new HashTable(SIZE);
    }

    public Pair<Integer, Integer> insert(String x) {
        return this.hashTable.add(x);
    }

    public Pair<Integer, Integer> search(String x) {
        return this.hashTable.index(x);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("-- program.SymbolTable --\n");

        for (int i = 0; i < hashTable.getItems().size(); i++) {
            str.append(i).append(": ").append(hashTable.getItems().get(i)).append('\n');
        }

        str.append("------------\n");
        return str.toString();
    }
}
