package chess.moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  KnightMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
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
     * This function will return possible moves for a knight at a given position on the board.
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> calcKnightMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        ChessPosition[] possibleMoves = {new ChessPosition(currentRow + 2, currentCol - 1),
                new ChessPosition(currentRow + 2, currentCol + 1),
                new ChessPosition(currentRow - 2, currentCol - 1),
                new ChessPosition(currentRow - 2, currentCol + 1),
                new ChessPosition(currentRow - 1, currentCol + 2),
                new ChessPosition(currentRow + 1, currentCol + 2),
                new ChessPosition(currentRow - 1, currentCol - 2),
                new ChessPosition(currentRow + 1, currentCol - 2)};

        for (ChessPosition move : possibleMoves) {
            if (Moves.validMove(move, getBoard(), getPieceColor())) {
                moves.add(new ChessMove(getPosition(), move, null));
            }
        }

        return moves;
    }
}
