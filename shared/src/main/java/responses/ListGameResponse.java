package responses;

import model.GameData;

import java.util.Collection;

public record ListGameResponse(Collection<GameData> games, String message) {
}
