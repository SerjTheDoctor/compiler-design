public class Main {
    public static void main(String[] args) {
        SymbolTable st = new SymbolTable();

        int xPos = st.insert("x");

        assert st.search("x") == xPos;
        assert st.search("abc") == -1;
    }
}
