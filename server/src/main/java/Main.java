import chess.*;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        int port = 8080;
        Spark.port(port);

        Spark.staticFiles.location("web");

        createRoutes();

        Spark.awaitInitialization();
    }

    private static void createRoutes(){
        Spark.get("/hello", (req, res) -> "Hello Chess!");
    }
}