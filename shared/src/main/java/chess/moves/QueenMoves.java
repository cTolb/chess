package chess.moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  QueenMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
        this.board = board;
        this.position = position;
    }

    private ChessGame.TeamColor getPieceColor() {
        return pieceColor;
    }

    private ChessBoard getBoard() {
        return board;
    }

    private ChessPosition getPosition() {
        return position;
    }

    public Collection<ChessMove> calcQueenMoves() {
        Collection<ChessMove> moves = new ArrayList<>();

        //Diagonal
        Moves.calculateContinuousMove(moves, 1, 1, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, 1, -1, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, -1, 1, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, -1, -1, getPosition(), getBoard(), getPieceColor());

        //Straight line
        Moves.calculateContinuousMove(moves, 1, 0, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, -1, 0, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, 0, 1, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, 0, -1, getPosition(), getBoard(), getPieceColor());

        return moves;
    }
}
