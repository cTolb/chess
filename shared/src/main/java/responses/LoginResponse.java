package responses;

import model.AuthData;

public record LoginResponse(AuthData authData, String message) {
}
