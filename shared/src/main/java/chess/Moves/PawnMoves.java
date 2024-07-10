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
        ChessPiece.PieceType [] promos = {ChessPiece.PieceType.ROOK, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.QUEEN};

        if (getPieceColor() == ChessGame.TeamColor.WHITE && currentRow == 2) {
            ChessPosition newPosition1 = new ChessPosition(currentRow + 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow + 2, currentCol);
            if (Moves.validPawnMove(newPosition1, board)) {
                moves.add(new ChessMove(getPosition(), newPosition1, null));
                if (Moves.validPawnMove(newPosition2, board)) {
                    moves.add(new ChessMove(getPosition(), newPosition2, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.BLACK && currentRow == 7) {
            ChessPosition newPosition1 = new ChessPosition(currentRow - 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow - 2, currentCol);
            if (Moves.validPawnMove(newPosition1, board)) {
                moves.add(new ChessMove(getPosition(), newPosition1, null));
                if (Moves.validPawnMove(newPosition2, board)) {
                    moves.add(new ChessMove(getPosition(), newPosition2, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.WHITE) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (Moves.validPawnMove(newPosition, board) && newPosition.getRow() == 8){
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            else {
                if (Moves.validPawnMove(newPosition, board)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.BLACK) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (Moves.validPawnMove(newPosition, board) && newPosition.getRow() == 1) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            else {
                if (Moves.validPawnMove(newPosition, board)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.WHITE) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (Moves.validPawnCap(newPosition, board, pieceColor) && newPosition.getRow() == 8) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            else {
                if (Moves.validPawnCap(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (Moves.validPawnCap(newPosition, board, pieceColor) && newPosition.getRow() == 8) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            else {
                if (Moves.validPawnCap(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        if (getPieceColor() == ChessGame.TeamColor.BLACK) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (Moves.validPawnCap(newPosition, board, pieceColor) && newPosition.getRow() == 1) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            else {
                if (Moves.validPawnCap(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (Moves.validPawnCap(newPosition, board, pieceColor) && newPosition.getRow() == 1) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            else {
                if (Moves.validPawnCap(newPosition, board, pieceColor)) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        return moves;
    }
}
