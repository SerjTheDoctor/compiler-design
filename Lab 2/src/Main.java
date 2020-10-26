import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner("token.in");

        try {
            scanner.scan("p3.txt", "pif.out", "st.out");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
