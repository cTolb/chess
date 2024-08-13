package client;

import java.awt.desktop.QuitEvent;
import java.util.Objects;
import java.util.Scanner;

public class Repl {
    private ChessClient client;

    public Repl(String serverURL) {
        client = new ChessClient();
    }

    public void run() {
        try {
            System.out.println("Welcome to the chess server! Please enter a command; ");
            client.options();

            Scanner scanner = new Scanner(System.in);
            State result = null;
            while (!Objects.equals(client.getState(), State.QUIT)) {
                String line = scanner.nextLine();

                try {
                    result = client.eval(line);
                    System.out.println(result);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            System.out.println("Thank you for playing!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
