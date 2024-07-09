package chess.Moves;

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
     * This function will return possible moves for a knight at a given position on the board.
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> knightMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getColumn();

        if (currentRow >= 1 && currentRow <= 6) {
            if(currentCol >= 2) {
                ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol - 1);
                if (Moves.validMove(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }

            if (currentCol <= 7) {
                ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol + 1);
                if (Moves.validMove(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if(currentRow <= 8 && currentRow >= 3) {
            if (currentCol >= 2) {
                ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol - 1);
                if (Moves.validMove(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }

            if (currentCol <= 7) {
                ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol + 1);
                if (Moves.validMove(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if (currentCol <= 6 && currentCol >= 1) {
            if (currentRow >= 2) {
                ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol + 2);
                if (Moves.validMove(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }

            if (currentRow <= 7) {
                ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 2);
                if (Moves.validMove(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if (currentCol <= 8 && currentCol >= 3) {
            if (currentRow >= 2) {
                ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 2);
                if(Moves.validMove(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }

            if (currentRow <= 7) {
                ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 2);
                if (Moves.validMove(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        return moves;
    }
}
