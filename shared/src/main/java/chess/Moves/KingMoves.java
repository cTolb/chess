package chess.Moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  KingMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
        this.board = board;
        this.position = position;
    }

    public ChessGame.TeamColor getPieceColor() {
        return pieceColor;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public ChessPosition getPosition() {
        return position;
    }

    /**
     * This function will return an ArrayList of possible moves a king can make at a given
     * position on the board
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> kingMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getColumn();

        ChessPosition newPosition;
        newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
        if (Moves.validMove(newPosition, board, pieceColor)) {
            moves.add(new ChessMove(getPosition(), newPosition, null));
        }
        newPosition = new ChessPosition(currentRow + 1, currentCol);
        if (Moves.validMove(newPosition, board, pieceColor)) {
            moves.add(new ChessMove(getPosition(), newPosition, null));
        }
        newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
        if (Moves.validMove(newPosition, board, pieceColor)) {
            moves.add(new ChessMove(getPosition(), newPosition, null));
        }
        newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
        if (Moves.validMove(newPosition, board, pieceColor)) {
            moves.add(new ChessMove(getPosition(), newPosition, null));
        }
        newPosition = new ChessPosition(currentRow - 1, currentCol);
        if (Moves.validMove(newPosition, board, pieceColor)) {
            moves.add(new ChessMove(getPosition(), newPosition, null));
        }
        newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
        if (Moves.validMove(newPosition, board, pieceColor)) {
            moves.add(new ChessMove(getPosition(), newPosition, null));
        }
        newPosition = new ChessPosition(currentRow, currentCol + 1);
        if (Moves.validMove(newPosition, board, pieceColor)) {
            moves.add(new ChessMove(getPosition(), newPosition, null));
        }
        newPosition = new ChessPosition(currentRow, currentCol - 1);
        if (Moves.validMove(newPosition, board, pieceColor)) {
            moves.add(new ChessMove(getPosition(), newPosition, null));
        }
        return moves;
    }

}
