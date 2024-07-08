package chess.Moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class RookMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  RookMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
        this.board = board;
        this.position = position;
    }

    /**
     * This function will return possible moves for a rook at a given position on the board.
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> rookMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        for (int i = currentCol; i <= 8; i++) {
            ChessPosition newPosition = new ChessPosition(currentRow, i + 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
                if (board.getPiece(newPosition) != null) {
                    if(board.getPiece(newPosition).getTeamColor() != pieceColor) {
                        break;
                    }
                }
            }
            else {
                break;
            }
        }

        for (int i = currentCol; i >= 1; i--) {
            ChessPosition newPosition = new ChessPosition(currentRow, i - 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
                if (board.getPiece(newPosition) != null) {
                    if(board.getPiece(newPosition).getTeamColor() != pieceColor) {
                        break;
                    }
                }
            }
            else {
                break;
            }
        }

        for (int i = currentRow; i <= 8; i++) {
            ChessPosition newPosition = new ChessPosition(i + 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
                if (board.getPiece(newPosition) != null) {
                    if(board.getPiece(newPosition).getTeamColor() != pieceColor) {
                        break;
                    }
                }
            }
            else {
                break;
            }
        }

        for (int i = currentRow; i >= 1; i--) {
            ChessPosition newPosition = new ChessPosition(i - 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
                if (board.getPiece(newPosition) != null) {
                    if(board.getPiece(newPosition).getTeamColor() != pieceColor) {
                        break;
                    }
                }
            }
            else {
                break;
            }
        }
        return moves;
    }

    private boolean validMove (ChessPosition newPosition, ChessBoard board) {
        if (isInBounds(newPosition)) {
            if (board.getPiece(newPosition) == null) {
                return true;
            }
            return board.getPiece(newPosition).getTeamColor() != pieceColor;
        }
        return false;
    }
    private boolean isInBounds (ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return row <= 8 && col <= 8 && row >= 1 && col >= 1;
    }
}
