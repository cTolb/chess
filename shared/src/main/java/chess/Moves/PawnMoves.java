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
     * This function will return possible moves for a pawn at a given position on the board.
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> pawnMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getColumn();

        if (getPieceColor() == ChessGame.TeamColor.WHITE && currentRow == 2) {
            ChessPosition newPosition1 = new ChessPosition(currentRow + 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow + 2, currentCol);
            if (validMove(newPosition1)) {
                moves.add(new ChessMove(getPosition(), newPosition1, null));
                if (validMove(newPosition2)) {
                    moves.add(new ChessMove(getPosition(), newPosition2, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.BLACK && currentRow == 7) {
            ChessPosition newPosition1 = new ChessPosition(currentRow - 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow - 2, currentCol);
            if (validMove(newPosition1)) {
                moves.add(new ChessMove(getPosition(), newPosition1, null));
                if (validMove(newPosition2)) {
                    moves.add(new ChessMove(getPosition(), newPosition2, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.WHITE) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (validMove(newPosition) && newPosition.getRow() == 8){
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validMove(newPosition)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.BLACK) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (validMove(newPosition) && newPosition.getRow() == 1) {
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validMove(newPosition)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.WHITE) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (validCap(newPosition) && newPosition.getRow() == 8) {
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validCap(newPosition)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (validCap(newPosition) && newPosition.getRow() == 8) {
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validCap(newPosition)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.BLACK) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (validCap(newPosition) && newPosition.getRow() == 1) {
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validCap(newPosition)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (validCap(newPosition) && newPosition.getRow() == 1) {
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.KNIGHT));
                moves.add(new ChessMove(getPosition(), newPosition, ChessPiece.PieceType.QUEEN));
            }
            else {
                if (validCap(newPosition)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        return moves;
    }

    /**
     * This function determines if a given move is valid.
     * @param newPosition Proposed new position
     * @return boolean if the move is valid
     */
    private boolean validMove (ChessPosition newPosition) {
        if (isInBounds(newPosition)) {
            return getBoard().getPiece(newPosition) == null;
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
            if(getBoard().getPiece(newPosition) != null){
                if (getBoard().getPiece(newPosition).getTeamColor() != getPieceColor()) {
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
