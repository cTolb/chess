package responses;

import model.AuthData;

public record LoginResponse(String username, String authToken, String message) {
}
