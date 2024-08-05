package dataaccess.memory;

import dataaccess.GameDao;
import model.GameData;

import java.util.HashMap;

public class MemGameDao implements GameDao {
    private final HashMap<Integer, GameData> games = new HashMap<>();
}
