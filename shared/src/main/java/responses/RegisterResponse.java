package responses;

import model.AuthData;

public record RegisterResponse(AuthData authData, String message) {
}
