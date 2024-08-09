package responses;

import model.GameData;

public record CreateGameResponse(GameData gameData, String message) {
}
