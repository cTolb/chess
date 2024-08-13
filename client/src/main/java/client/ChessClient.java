package client;

import model.GameData;
import model.UserData;
import requests.LoginRequest;
import responses.LoginResponse;

import java.util.ArrayList;

public class ChessClient {
    private final ServerFacade facade;
    private final String serverURL = null;
    private State state = State.PRELOGIN;
    private ArrayList<GameData> gameDataList;
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
                //case "register" -> this.register(params);
                /*case "logout" -> this.logout();
                case "list" -> this.list();
                case "join" -> this.join(params);
                case "create" -> this.create(params);
                case "observe" -> this.observe(params);
                case "redraw" -> this.redraw();
                case "leave" -> this.leave();
                case "highlight" -> this.hightlight();*/
                default -> throw new IllegalStateException("Unexpected value: " + command);
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

    /*public State register(String... params) throws Exception {
        if (this.state != State.PRELOGIN) {
            throw new Exception("User is already logged in");
        }

        UserData userData;
        if (params.length != 3) {
            
        }
    }*/

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
                    - join <GAMEID> <BLACK|WHITE> - to join a game as the color selected
                    - create <GAMENAME> - to create a new game
                    - list - to see all created games
                    - help - for other information
                    - logout - to sigh out of your account
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
