package server;

import dataaccess.DataAccess;
import handlers.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        DataAccess data = new DataAccess();

        //Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegisterHandler(data));
        Spark.delete("/db", new ClearHandler(data));


        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}
