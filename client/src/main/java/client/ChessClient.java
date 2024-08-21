package client;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import requests.LoginRequest;
import responses.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ChessClient {
    private final ServerFacade facade;
    private final String serverURL = null;
    private State state = State.PRELOGIN;
    private ArrayList<GameData> gameDataList;
    private HashMap<Integer, String> gameInfo = new HashMap<>();
    private String playerName;
    private String playerAuthToken;

    ChessClient() {
        facade = new ServerFacade("http://localhost:8080");
    }

    public State eval(String input) throws Exception {
        try {
            String[] tokens = input.split(" ");
            String[] params = null;
            if (tokens.length > 1) {
                params = new String[tokens.length - 1];
                System.arraycopy(tokens, 1, params, 0, params.length);
            }
            var command = tokens[0].toLowerCase();

            State currentUI = this.state;
            currentUI = switch (command) {
                case "help" -> this.help();
                //case "quit" -> this.quit();
                case "login" -> this.login(params);
                case "register" -> this.register(params);
                case "logout" -> this.logout();
                case "list" -> this.list();
                case "join" -> this.join(params);
                case "create" -> this.create(params);
                //case "observe" -> this.observe(params);
                default -> this.help();
            };
            setState(currentUI);
            return currentUI;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public State help() throws Exception {
        System.out.println("These are the current options: ");
        this.options();
        return getState();
    }

    public State register(String... params) throws Exception {
        if (this.state != State.PRELOGIN) {
            throw new Exception("User is already logged in");
        }

        UserData userData;
        if (params.length != 3) {
            throw new Exception("The wrong number of parameters were given");
        } else {
            userData = new UserData(params[0], params[1], params[2]);
        }

        RegisterResponse response = null;
        try {
            response = facade.register(userData);
        } catch (Exception e) {
            if (e.getMessage().equals("400")){
                throw new Exception("Error: bad request");
            }
            else if (e.getMessage().equals("403")) {
                throw new Exception("Error: username already taken");
            }
            else if (e.getMessage().equals("500")){
                throw new Exception("Error: unexpected error please try again.");
            }
        }

        State newState = null;

        if (response.authToken().length() > 1) {
            System.out.println("Welcome to the chess server " + response.username() + "! Pick a command:");
            String username = response.username();
            String authToken = response.authToken();
            setPlayerName(username);
            setPlayerAuthToken(authToken);
            newState = State.POSTLOGIN;
        } else {
            newState = State.PRELOGIN;
            System.out.println("Unsuccessful registration: " + response.message());
        }

        setState(newState);
        this.options();

        return newState;
    }

    public State login(String... params) throws Exception {
        if(this.state != State.PRELOGIN) {
            throw new Exception("Already logged in");
        }

        LoginRequest userData;
        if(params.length < 2) {
            throw new Exception("Missing parameters");
        } else {
            userData = new LoginRequest(params[0], params[1]);
        }

        LoginResponse response = null;
        try {
            response = facade.login(userData);
        } catch (Exception ex){
            throw new Exception("Username or password is incorrect");
        }

        State newState = null;
        if(response.authToken().length() > 1) {
            System.out.println("Welcome back " + response.username() + "! Pick a command:");
            setPlayerName(response.username());
            setPlayerAuthToken(response.authToken());
            newState = State.POSTLOGIN;
        } else {
            newState = State.PRELOGIN;
            System.out.println("Unsuccessful login: " + response.message());
        }

        setState(newState);
        this.options();

        return newState;
    }

    public State logout() throws Exception {
        if (this.state != State.POSTLOGIN) {
            throw new Exception("User is not logged in");
        }

        State newState = getState();
        try {
            facade.logout(getPlayerAuthToken());
            newState = State.PRELOGIN;
            setState(newState);
            System.out.println("You have been logged out");
            this.options();

        } catch (Exception ex) {
            throw new Exception("Error logging out, please try again");
        }
        return newState;
    }

    public State list() throws Exception{
        State currentState = getState();
        if (currentState != State.POSTLOGIN) {
            throw new Exception("You must be logged in to see games");
        }

        try {
            ListGameResponse response = facade.listGame(getPlayerAuthToken());
            gameDataList = new ArrayList<>(response.games());
            System.out.print("");
            System.out.println("ALL GAMES:");
            for (int i = 0; i < gameDataList.size(); i++){
                int gameNumber = i + 1;
                System.out.println("GAME NUMBER: " + gameNumber);
                System.out.println("GAME NAME: " + gameDataList.get(i).gameName());
                System.out.print("WHITE USERNAME: " + gameDataList.get(i).whiteUsername() + ", ");
                System.out.println("BLACK USERNAME: " + gameDataList.get(i).blackUsername() + "\n");

                gameInfo.put(gameNumber, gameDataList.get(i).gameName());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return getState();
    }

    public State join(String... params) throws Exception {
        State currentState = getState();
        if (currentState != State.POSTLOGIN) {
            throw new Exception("User must be logged in to join a game");
        }
        return getState();
    }

    public State create(String... params) throws Exception {
        State currentState = getState();
        if (currentState != State.POSTLOGIN) {
            throw new Exception("User must be logged in to create a game");
        }

        if (params.length != 1) {
            throw new Exception("The wrong number of parameters were provided");
        }

        try {
            GameData newGame = new GameData(0, null, null, params[0], new ChessGame());
            CreateGameResponse response = facade.createGame(getPlayerAuthToken(), newGame);

            if (response.message() == null) {
                System.out.println(params[0] + " has been created.");
                this.options();
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return getState();
    }
    public void options() throws Exception {
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
                    - list - see all games
                    - help - for other information
                    - logout - to sign out of your account
                    """;
        }
        else if (this.state == State.GAMEPLAY) {
            currentMenu = "not yet implemented";
        }
        else {
            throw new Exception("state is not valid");
        }
        System.out.println(currentMenu);
    }

    public void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return this.state;
    }

    public void setPlayerName(String username) {
        this.playerName = username;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerAuthToken(String authToken) {
        this.playerAuthToken = authToken;
    }

    public String getPlayerAuthToken() {
        return this.playerAuthToken;
    }
}
