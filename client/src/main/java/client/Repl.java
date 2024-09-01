package client;

import java.util.Objects;
import java.util.Scanner;

import static ui.EscapeSequences.SET_TEXT_COLOR_WHITE;

public class Repl {
    private final ChessClient client;

    public Repl(String serverURL) {
        client = new ChessClient(serverURL);
    }

    public void run() {
        try {
            System.out.println(STR."\{SET_TEXT_COLOR_WHITE}Welcome to the chess server! Please enter a command; ");
            System.out.print(client.helpMenu());

            Scanner scanner = new Scanner(System.in);
            State result = null;
            while (!Objects.equals(client.getState(), State.QUIT)) {
                String line = scanner.nextLine();

                try {
                    result = client.eval(line);
                    //System.out.println(result);
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
