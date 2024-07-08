package chess.Moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPosition;

public class QueenMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  QueenMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
        this.board = board;
        this.position = position;
    }
}
