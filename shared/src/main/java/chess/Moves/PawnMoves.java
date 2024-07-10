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
        ChessPiece.PieceType [] promos = {ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.QUEEN};



        //White initial moves
        if (getPieceColor() == ChessGame.TeamColor.WHITE && currentRow == 2) {
            ChessPosition newPosition1 = new ChessPosition(currentRow + 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow + 2, currentCol);
            if (Moves.validPawnMove(newPosition1, getBoard())) {
                moves.add(new ChessMove(getPosition(), newPosition1, null));
                if (Moves.validPawnMove(newPosition2, getBoard())) {
                    moves.add(new ChessMove(getPosition(), newPosition2, null));
                }
            }
        }

        //Black initial moves
        if (getPieceColor() == ChessGame.TeamColor.BLACK && currentRow == 7) {
            ChessPosition newPosition1 = new ChessPosition(currentRow - 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow - 2, currentCol);
            if (Moves.validPawnMove(newPosition1, getBoard())) {
                moves.add(new ChessMove(getPosition(), newPosition1, null));
                if (Moves.validPawnMove(newPosition2, getBoard())) {
                    moves.add(new ChessMove(getPosition(), newPosition2, null));
                }
            }
        }

        //All other white moves
        if (getPieceColor() == ChessGame.TeamColor.WHITE) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            //Promo
            if (Moves.validPawnMove(newPosition, getBoard()) && newPosition.getRow() == 8){
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            //Regular Move
            else {
                if (Moves.validPawnMove(newPosition, getBoard())) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
            //Capture right
            newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            //Promo
            if (Moves.validPawnCap(newPosition, getBoard(), getPieceColor()) && newPosition.getRow() == 8) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            //Regular capture
            else {
                if (Moves.validPawnCap(newPosition, getBoard(), getPieceColor())) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
            //Capture left
            newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            //Promo
            if (Moves.validPawnCap(newPosition, getBoard(), getPieceColor()) && newPosition.getRow() == 8) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            //Regular capture
            else {
                if (Moves.validPawnCap(newPosition, getBoard(), getPieceColor())) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }

        //Black moves
        if (getPieceColor() == ChessGame.TeamColor.BLACK) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol);
            //Promo
            if (Moves.validPawnMove(newPosition, getBoard()) && newPosition.getRow() == 1) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            //Regular move
            else {
                if (Moves.validPawnMove(newPosition, getBoard())) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
            //Capture right
            newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            //Promo
            if (Moves.validPawnCap(newPosition, getBoard(), getPieceColor()) && newPosition.getRow() == 1) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            //Regular capture
            else {
                if (Moves.validPawnCap(newPosition, getBoard(), getPieceColor())) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
            //Capture left
            newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            //Promo
            if (Moves.validPawnCap(newPosition, getBoard(), getPieceColor()) && newPosition.getRow() == 1) {
                for (ChessPiece.PieceType promo : promos) {
                    moves.add(new ChessMove(getPosition(), newPosition, promo));
                }
            }
            //Regular capture
            else {
                if (Moves.validPawnCap(newPosition, getBoard(), getPieceColor())) {
                    moves.add(new ChessMove(getPosition(), newPosition, null));
                }
            }
        }
        return moves;
    }
}
