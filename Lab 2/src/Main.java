import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Analyzer scanner = new Analyzer("token.in");

//        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nFilename: ");
            String filename = "p3.txt"; //input.nextLine();

            try {
                scanner.scan(filename, "pif.out", "st.out");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
