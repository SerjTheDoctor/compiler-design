import scanner.Analyzer;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Analyzer scanner = new Analyzer("token.in");

        Scanner input = new Scanner(System.in);

        try {
            while (true) {
                System.out.println("\nFilename: ");
                String filename = input.nextLine();
                scanner.scan(filename, "pif.out", "st.out");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
