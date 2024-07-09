package chess.Moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class Moves {
    public static boolean validMove(ChessPosition newPosition, ChessBoard board, ChessGame.TeamColor pieceColor) {
        if (isInBounds(newPosition)) {
            if (board.getPiece(newPosition) == null) {
                return true;
            }
            return board.getPiece(newPosition).getTeamColor() != pieceColor;
        }
        return false;
    }

    /**
     * This function determines if a given move is valid.
     * @param newPosition Proposed new position
     * @param board current game board
     * @return boolean if the move is valid
     */
    public static boolean validPawnMove (ChessPosition newPosition, ChessBoard board) {
        if (isInBounds(newPosition)) {
            return board.getPiece(newPosition) == null;
        }
        return false;
    }

    /**
     * This function determines if the given move of a pawn is a valid capture move.
     * @param newPosition proposed new position
     * @param board current chess board
     * @param pieceColor current piece color
     * @return boolean if the move is valid capture
     */
    public static boolean validPawnCap(ChessPosition newPosition, ChessBoard board, ChessGame.TeamColor pieceColor) {
        if (isInBounds(newPosition)) {
            if(board.getPiece(newPosition) != null){
                if (board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if the position is in bounds, or on the board
     * @param position proposed position
     * @return boolean if position is in bounds
     */
    public static boolean isInBounds(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return row <= 8 && col <= 8 && row >= 1 && col >= 1;
    }
}
