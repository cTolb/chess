package chess.Moves;

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

    public Collection<ChessMove> queenMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        RookMoves rookMoves = new RookMoves(getBoard(), getPosition(), getPieceColor());
        BishopMoves bishopMoves = new BishopMoves(getBoard(), getPosition(), getPieceColor());

        moves.addAll(rookMoves.rookMoves());
        moves.addAll(bishopMoves.bishopMoves());

        return moves;
    }
}
