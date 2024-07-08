package chess.Moves;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  PawnMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
        this.board = board;
        this.position = position;
    }

    /**
     * This function will return possible moves for a pawn at a given position on the board.
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> pawnMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE && currentRow == 2) {
            ChessPosition newPosition1 = new ChessPosition(currentRow + 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow + 2, currentCol);
            if (validMove(newPosition1, board)) {
                moves.add(new ChessMove(position, newPosition1, null));
                if (validMove(newPosition2, board)) {
                    moves.add(new ChessMove(position, newPosition2, null));
                }
            }
        }

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK && currentRow == 7) {
            ChessPosition newPosition1 = new ChessPosition(currentRow - 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow - 2, currentCol);
            if (validMove(newPosition1, board)) {
                moves.add(new ChessMove(position, newPosition1, null));
                if (validMove(newPosition2, board)) {
                    moves.add(new ChessMove(position, newPosition2, null));
                }
            }
        }

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (validMove(newPosition, board) && newPosition.getRow() == 8){
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(position, newPosition, null));
                }
            }
        }

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (validMove(newPosition, board) && newPosition.getRow() == 1) {
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(position, newPosition, null));
                }
            }
        }

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.WHITE) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (validCap(newPosition) && newPosition.getRow() == 8) {
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validCap(newPosition)) {
                    moves.add(new ChessMove(position, newPosition, null));
                }
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (validCap(newPosition) && newPosition.getRow() == 8) {
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validCap(newPosition)) {
                    moves.add(new ChessMove(position, newPosition, null));
                }
            }
        }

        if (board.getPiece(position).getTeamColor() == ChessGame.TeamColor.BLACK) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (validCap(newPosition) && newPosition.getRow() == 1) {
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validCap(newPosition)) {
                    moves.add(new ChessMove(position, newPosition, null));
                }
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (validCap(newPosition) && newPosition.getRow() == 1) {
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validCap(newPosition)) {
                    moves.add(new ChessMove(position, newPosition, null));
                }
            }
        }

        return moves;
    }

    /**
     * This function determines if a given move is valid.
     * @param newPosition Proposed new position
     * @param board current game board
     * @return boolean if the move is valid
     */
    private boolean validMove (ChessPosition newPosition, ChessBoard board) {
        if (isInBounds(newPosition)) {
            return board.getPiece(newPosition) == null;
        }
        return false;
    }

    /**
     * This function determines if the given move of a pawn is a valid capture move.
     * @param newPosition proposed new position
     * @return boolean if the move is valid capture
     */
    private boolean validCap(ChessPosition newPosition) {
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
     * Returns if a position is in bounds.
     * @param myPosition current position on board
     * @return boolean if position is in bounds
     */
    private boolean isInBounds (ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        return row <= 8 && col <= 8 && row >= 1 && col >= 1;
    }
}
