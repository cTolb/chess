package dataaccess.memory;

import dataaccess.AuthDao;

import java.util.ArrayList;
import java.util.Collection;

public class MemAuthDao implements AuthDao {
    private final Collection<Integer> authTokens = new ArrayList<>();
    public void createAuth(){

    }
}
