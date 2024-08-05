package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class MemGameDao implements GameDao {
    private final HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }
}
