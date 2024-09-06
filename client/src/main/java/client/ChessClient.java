package client;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import requests.JoinGameRequest;
import requests.LoginRequest;
import responses.*;
import static ui.EscapeSequences.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChessClient {
    private final ServerFacade facade;
    private final String serverURL = null;
    private State state = State.PRELOGIN;
    private HashMap<Integer, Integer> gameInfo = new HashMap<>();
    private String playerName;
    private String playerAuthToken;

    ChessClient(String serverURL) {
        facade = new ServerFacade(serverURL);
    }

    private void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return this.state;
    }

    private void setPlayerName(String username) {
        this.playerName = username;
    }

    private String getPlayerName() {
        return this.playerName;
    }

    private void setPlayerAuthToken(String authToken) {
        this.playerAuthToken = authToken;
    }

    private String getPlayerAuthToken() {
        return this.playerAuthToken;
    }

    public State eval(String input) throws Exception {
        try {
            String[] tokens = input.split(" ");
            String[] params = null;
            if (tokens.length > 1) {
                params = new String[tokens.length - 1];
                params = Arrays.copyOfRange(tokens, 1, tokens.length);
            }
            var command = tokens[0].toLowerCase();

            State currentUI = this.state;
            currentUI = switch (command) {
                case "quit" -> quit();
                case "login" -> login(params);
                case "register" -> register(params);
                case "list" -> list();
                case "join" -> join(params);
                case "create" -> create(params);
                case "observe" -> observe(params);
                case "logout" -> logout();
                default -> help();
            };
            setState(currentUI);
            return currentUI;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private State quit() throws Exception {
        if (getState() != State.PRELOGIN) {
            throw new Exception(SET_TEXT_COLOR_RED+ "You must be logged out to quit." + RESET_TEXT_COLOR);
        }
        setState(State.QUIT);
        return getState();
    }

    private State help() throws Exception{
        try {
            System.out.println(SET_TEXT_COLOR_WHITE + "These are the current options: ");
            System.out.print(helpMenu() + RESET_TEXT_COLOR);
            return getState();
        } catch (Exception e) {
            throw new Exception(SET_TEXT_COLOR_RED + e.getMessage() + RESET_TEXT_COLOR);
        }
    }

    private State register(String... params) throws Exception {
        if (state != State.PRELOGIN) {
            throw new Exception(SET_TEXT_COLOR_RED + "You must be logged out inorder to register a user." +
                    RESET_TEXT_COLOR);
        }

        UserData userData;
        if (params == null || params.length != 3) {
            throw new Exception(SET_TEXT_COLOR_RED + "An incorrect number of information was given.\n" +
                    "You must provide a username, password, and email." + RESET_TEXT_COLOR);
        } else {
            userData = new UserData(params[0], params[1], params[2]);
        }

        RegisterResponse response = null;
        try {
            response = facade.register(userData);
        } catch (Exception e) {
            if (e.getMessage().equals("400")) {
                throw new Exception(SET_TEXT_COLOR_RED + "Error: bad request.");
            }
            else if (e.getMessage().equals("403")) {
                throw new Exception(SET_TEXT_COLOR_RED + "Error: username is already taken." + RESET_TEXT_COLOR);
            }
            else if (e.getMessage().equals("500")) {
                throw new Exception(SET_TEXT_COLOR_RED + "Error: something went wrong, please try again." + RESET_TEXT_COLOR);
            }
            else {
                throw new Exception(SET_TEXT_COLOR_RED + "Error: something went wrong, please try again." + RESET_TEXT_COLOR);
            }
        }

        if (response.authToken().length() > 1) {
            System.out.println(SET_TEXT_COLOR_GREEN + "Welcome to the chess server " + response.username() + "!" +
                    " Please type a command:");
            setPlayerName(response.username());
            setPlayerAuthToken(response.authToken());
            setState(State.POSTLOGIN);
        } else {
            setState(State.PRELOGIN);
            throw new Exception(SET_TEXT_COLOR_RED + "Unsuccessful registration: " + response.message());
        }

        System.out.print(helpMenu() + RESET_TEXT_COLOR);

        return getState();
    }

    private State login(String... params) throws Exception {
        if(this.state != State.PRELOGIN) {
            throw new Exception(SET_TEXT_COLOR_RED + "Already logged in.");
        }

        LoginRequest userData;
        if(params == null || params.length != 2) {
            throw new Exception(SET_TEXT_COLOR_RED + "The wrong number of parameters were given.\n" +
                    "You must provide a username and password." + RESET_TEXT_COLOR);
        } else {
            userData = new LoginRequest(params[0], params[1]);
        }

        LoginResponse response = null;
        try {
            response = facade.login(userData);
        } catch (Exception ex){
            throw new Exception(SET_TEXT_COLOR_RED + "Username or password is incorrect." + RESET_TEXT_COLOR);
        }
        
        if(response.authToken().length() > 1) {
            System.out.println(SET_TEXT_COLOR_GREEN + "Welcome back " + response.username() + "! Pick a command:" +
                    RESET_TEXT_COLOR);
            setPlayerName(response.username());
            setPlayerAuthToken(response.authToken());
            setState(State.POSTLOGIN);
        } else {
            setState(State.PRELOGIN);
            throw new Exception(SET_TEXT_COLOR_RED + "Unsuccessful login: " + response.message() + RESET_TEXT_COLOR);
        }
        
        System.out.print(helpMenu() + RESET_TEXT_COLOR);

        return getState();
    }

    private State list() throws Exception{
        State currentState = getState();
        if (currentState != State.POSTLOGIN) {
            throw new Exception(SET_TEXT_COLOR_RED + "You must be logged in to see games." + RESET_TEXT_COLOR);
        }

        try {
            ListGameResponse response = facade.listGame(getPlayerAuthToken());
            ArrayList<GameData> gameDataList = new ArrayList<>(response.games());

            if (gameDataList.isEmpty()) {
                throw new Exception(SET_TEXT_COLOR_RED + "There are no games created. Please create a game to play." +
                        RESET_TEXT_COLOR);
            } else {
                System.out.println(SET_TEXT_COLOR_BLUE + "ALL GAMES:");
                for (int i = 0; i < gameDataList.size(); i++) {
                    int gameNumber = i + 1;
                    System.out.println(SET_TEXT_COLOR_WHITE + "GAME NUMBER: " + gameNumber);
                    System.out.println("GAME NAME: " + gameDataList.get(i).gameName());
                    System.out.print("WHITE USERNAME: " + gameDataList.get(i).whiteUsername());
                    System.out.println(" BLACK USERNAME: " + gameDataList.get(i).blackUsername() + "\n");

                    gameInfo.put(gameNumber, gameDataList.get(i).gameID());
                }
            }
        } catch (Exception e) {
            throw new Exception(SET_TEXT_COLOR_RED + e.getMessage());
        }

        return getState();
    }

    private State join(String... params) throws Exception {

        State currentState = getState();
        if (currentState != State.POSTLOGIN) {
            throw new Exception(SET_TEXT_COLOR_RED + "User must be logged in to join a game." + RESET_TEXT_COLOR);
        }

        if (params == null || params.length != 2) {
            throw new Exception(SET_TEXT_COLOR_RED + "The wrong number of parameters were given.\n" +
                    "You must provide a game number and team color." + RESET_TEXT_COLOR);
        }

        int gameNumber = 0;
        try {
            gameNumber = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            throw new Exception(SET_TEXT_COLOR_RED + "The first input after \"join\" must be a number." + RESET_TEXT_COLOR);
        }

        if (!gameInfo.containsKey(gameNumber)) {
            throw new Exception(SET_TEXT_COLOR_RED + "Game number not found. Please list the games and try again." +
                    RESET_TEXT_COLOR);
        }

        int gameID = gameInfo.get(gameNumber);
        String requestedColor = params[1].toUpperCase();
        ChessGame.TeamColor color = null;

        if (requestedColor.equals("BLACK")) {
            color = ChessGame.TeamColor.BLACK;
        }
        else if (requestedColor.equals("WHITE")) {
            color = ChessGame.TeamColor.WHITE;
        }
        else {
            throw new Exception(SET_TEXT_COLOR_RED + "Team color not valid. Please try again." + RESET_TEXT_COLOR);
        }

        JoinGameRequest request = new JoinGameRequest(color, gameID);

        try {
            JoinGameResponse response = facade.joinGame(getPlayerAuthToken(), request);

            if (response.message() == null) {
                System.out.println(SET_TEXT_COLOR_GREEN + "You have joined the game!");
            }
        } catch (Exception e) {
            throw new Exception(SET_TEXT_COLOR_RED + e.getMessage());
        }

        return getState();
    }

    private State create(String... params) throws Exception {
        State currentState = getState();
        if (currentState != State.POSTLOGIN) {
            throw new Exception(SET_TEXT_COLOR_RED + "User must be logged in to create a game." + RESET_TEXT_COLOR);
        }

        if (params == null || params.length != 1) {
            throw new Exception(SET_TEXT_COLOR_RED + "The wrong number of parameters were provided." + RESET_TEXT_COLOR);
        }

        try {
            GameData newGame = new GameData(0, null, null, params[0], new ChessGame());
            CreateGameResponse response = facade.createGame(getPlayerAuthToken(), newGame);

            if (response.message() == null) {
                System.out.println(SET_TEXT_COLOR_GREEN + params[0] + " has been created.\n" + RESET_TEXT_COLOR);
                System.out.print(helpMenu());
            }

        } catch (Exception e) {
            throw new Exception(SET_TEXT_COLOR_RED + "Something went wrong. Please try again." + RESET_TEXT_COLOR);
        }
        return getState();
    }

    private State observe(String... params) throws Exception {
        printBoard();
        return getState();
    }

    private void printBoard() {
        printWhite();
        printBlack();
    }

    private void printWhite() {
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print("    a  b  c  d  e  f  g  h    ");
        System.out.println(RESET_BG_COLOR);
        for (int i = 8; i >= 1; i--) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            System.out.print(" " + i +" ");
            for (int j = 1; j <= 8; j++) {
                if ((j + i) % 2 == 0) {
                    System.out.print(SET_BG_COLOR_BLACK);
                }
                else {
                    System.out.print(SET_BG_COLOR_WHITE);
                }

                if (i == 1 || i == 2) {
                    System.out.print(SET_TEXT_COLOR_RED);
                    if (i == 1) {
                        if (j == 1 || j == 8) {
                            System.out.print(" R ");
                        }
                        if (j == 2 || j == 7) {
                            System.out.print(" N ");
                        }
                        if (j == 3 || j == 6) {
                            System.out.print( " B ");
                        }
                        if (j == 4) {
                            System.out.print(" Q ");
                        }
                        if (j == 5) {
                            System.out.print(" K ");
                        }
                    }

                    if (i == 2) {
                        System.out.print(" P ");
                    }
                }

                else if (i == 7 || i == 8) {
                    System.out.print(SET_TEXT_COLOR_BLUE);
                    if (i == 8) {
                        if (j == 1 || j == 8) {
                            System.out.print(" R ");
                        }
                        if (j == 2 || j == 7) {
                            System.out.print(" N ");
                        }
                        if (j == 3 || j == 6) {
                            System.out.print( " B ");
                        }
                        if (j == 4) {
                            System.out.print(" Q ");
                        }
                        if (j == 5) {
                            System.out.print(" K ");
                        }
                    }

                    if (i == 7) {
                        System.out.print(" P ");
                    }
                }
                else {
                    System.out.print("   ");
                }
            }
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.print(" " + i + " ");
            System.out.println(RESET_BG_COLOR);
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print("    a  b  c  d  e  f  g  h    ");
        System.out.println(RESET_BG_COLOR);
        System.out.println();

    }

    private void printBlack() {
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print("    h  g  f  e  d  c  b  a    ");
        System.out.println(RESET_BG_COLOR);
        for (int i = 1; i <= 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            System.out.print(" " + i +" ");
            for (int j = 1; j <= 8; j++) {
                if ((j + i) % 2 != 0) {
                    System.out.print(SET_BG_COLOR_BLACK);
                }
                else {
                    System.out.print(SET_BG_COLOR_WHITE);
                }

                if (i == 1 || i == 2) {
                    System.out.print(SET_TEXT_COLOR_RED);
                    if (i == 1) {
                        if (j == 1 || j == 8) {
                            System.out.print(" R ");
                        }
                        if (j == 2 || j == 7) {
                            System.out.print(" N ");
                        }
                        if (j == 3 || j == 6) {
                            System.out.print( " B ");
                        }
                        if (j == 5) {
                            System.out.print(" Q ");
                        }
                        if (j == 4) {
                            System.out.print(" K ");
                        }
                    }

                    if (i == 2) {
                        System.out.print(" P ");
                    }
                }

                else if (i == 7 || i == 8) {
                    System.out.print(SET_TEXT_COLOR_BLUE);
                    if (i == 8) {
                        if (j == 1 || j == 8) {
                            System.out.print(" R ");
                        }
                        if (j == 2 || j == 7) {
                            System.out.print(" N ");
                        }
                        if (j == 3 || j == 6) {
                            System.out.print( " B ");
                        }
                        if (j == 5) {
                            System.out.print(" Q ");
                        }
                        if (j == 4) {
                            System.out.print(" K ");
                        }
                    }

                    if (i == 7) {
                        System.out.print(" P ");
                    }
                }
                else {
                    System.out.print("   ");
                }
            }
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.print(" " + i + " ");
            System.out.println(RESET_BG_COLOR);
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print("    h  g  f  e  d  c  b  a    ");
        System.out.println(RESET_BG_COLOR);
        System.out.println();
    }

    private State logout() throws Exception {
        if (this.state != State.POSTLOGIN) {
            throw new Exception(SET_TEXT_COLOR_RED + "User is not logged in." + RESET_TEXT_COLOR);
        }

        try {
            facade.logout(getPlayerAuthToken());
            setState(State.PRELOGIN);
            System.out.println(SET_TEXT_COLOR_GREEN + "You have been logged out." + RESET_TEXT_COLOR);
            System.out.print(helpMenu());
        } catch (Exception e) {
            throw new Exception(SET_TEXT_COLOR_RED + "Error logging out. Please try again." + RESET_TEXT_COLOR);
        }
        return getState();
    }

    public String helpMenu() throws Exception {
        String currentMenu = null;
        if (this.state == State.PRELOGIN) {
            currentMenu = """
                    - register <USERNAME> <PASSWORD> <EMAIL> - to create a new account
                    - login <USERNAME> <PASSWORD> - to log in to your account
                    - help - for other information
                    - quit - to exit
                    """;
        }
        else if (this.state == State.POSTLOGIN) {
            currentMenu = """
                    - join <GAME NUMBER> <BLACK or WHITE> - to join a game as the color selected
                    - create <GAME NAME> - to create a new game
                    - observe <GAME NUMBER> - to observe a game
                    - list - see all games
                    - help - for other information
                    - logout - to sign out of your account
                    """;
        }
        else if (this.state == State.GAMEPLAY) {
            currentMenu = "Gameplay is not yet implemented.";
        }
        else {
            throw new Exception("State is not valid.");
        }
        return SET_TEXT_COLOR_WHITE + currentMenu;
    }
}
