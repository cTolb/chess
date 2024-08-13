package responses;

import model.AuthData;

public record RegisterResponse(String authToken, String username, String message) {
}
