public class SymbolTable {
    private final int SIZE = 10;
    private HashTable hashTable;

    public SymbolTable() {
        this.hashTable = new HashTable(SIZE);
    }

    public int insert(String x) {
        return this.hashTable.add(x);
    }

    public int search(String x) {
        return this.hashTable.index(x);
    }
}
