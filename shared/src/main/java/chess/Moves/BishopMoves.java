package chess.Moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  BishopMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
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

    /**
     * This function returns an ArrayList of possibles moves for a bishop piece at a given location
     * on the board.
     * @return Collection of possible moves
     */
    public Collection<ChessMove> calcBishopMoves() {
        Collection<ChessMove> moves = new ArrayList<>();

        Moves.calculateContinuousMove(moves, 1, 1, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, 1, -1, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, -1, 1, getPosition(), getBoard(), getPieceColor());
        Moves.calculateContinuousMove(moves, -1, -1, getPosition(), getBoard(), getPieceColor());

        return moves;
    }


}
