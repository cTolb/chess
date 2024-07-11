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
     * This function will return possible moves for a pawn at a given position on the board.
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> calcPawnMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getColumn();
        ChessPiece.PieceType [] promos = {ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.QUEEN};

        //White Moves
        ChessPosition whiteRegularMove = new ChessPosition(currentRow + 1, currentCol);
        ChessPosition whiteCaptureRight = new ChessPosition(currentRow + 1, currentCol + 1);
        ChessPosition whiteCaptureLeft = new ChessPosition(currentRow + 1, currentCol - 1);

        //BlackMoves
        ChessPosition blackRegularMove = new ChessPosition(currentRow - 1, currentCol);
        ChessPosition blackCaptureRight = new ChessPosition(currentRow - 1, currentCol + 1);
        ChessPosition blackCaptureLeft = new ChessPosition(currentRow - 1, currentCol - 1);

        //White initial moves
        if (getPieceColor() == ChessGame.TeamColor.WHITE && currentRow == 2) {
            ChessPosition whiteMoveTwo = new ChessPosition(currentRow + 2, currentCol);
            if (Moves.validPawnMove(whiteRegularMove, getBoard())) {
                moves.add(new ChessMove(getPosition(), whiteRegularMove, null));
                if (Moves.validPawnMove(whiteMoveTwo, getBoard())) {
                    moves.add(new ChessMove(getPosition(), whiteMoveTwo, null));
                }
            }
        }

        //Black initial moves
        if (getPieceColor() == ChessGame.TeamColor.BLACK && currentRow == 7) {
            ChessPosition blackMoveTwo = new ChessPosition(currentRow - 2, currentCol);
            if (Moves.validPawnMove(blackRegularMove, getBoard())) {
                moves.add(new ChessMove(getPosition(), blackRegularMove, null));
                if (Moves.validPawnMove(blackMoveTwo, getBoard())) {
                    moves.add(new ChessMove(getPosition(), blackMoveTwo, null));
                }
            }
        }

        //All other white moves
        if (getPieceColor() == ChessGame.TeamColor.WHITE) {
            //Promo
            if (Moves.validPawnMove(whiteRegularMove, getBoard()) && whiteRegularMove.getRow() == 8){
                moves.addAll(makePromotion(promos, getPosition(), whiteRegularMove, moves));
            }
            else { //Regular Move
                if (Moves.validPawnMove(whiteRegularMove, getBoard())) {
                    moves.add(new ChessMove(getPosition(), whiteRegularMove, null));
                }
            }

            //Capture right
            //Promo
            if (Moves.validPawnCap(whiteCaptureRight, getBoard(), getPieceColor()) && whiteCaptureRight.getRow() == 8) {
                moves.addAll(makePromotion(promos, getPosition(), whiteCaptureRight, moves));
            }
            else { //Regular capture
                if (Moves.validPawnCap(whiteCaptureRight, getBoard(), getPieceColor())) {
                    moves.add(new ChessMove(getPosition(), whiteCaptureRight, null));
                }
            }

            //Capture left
            //Promo
            if (Moves.validPawnCap(whiteCaptureLeft, getBoard(), getPieceColor()) && whiteCaptureLeft.getRow() == 8) {
                moves.addAll(makePromotion(promos, getPosition(), whiteCaptureLeft, moves));
            }
            else { //Regular capture
                if (Moves.validPawnCap(whiteCaptureLeft, getBoard(), getPieceColor())) {
                    moves.add(new ChessMove(getPosition(), whiteCaptureLeft, null));
                }
            }
        }

        //Black moves
        if (getPieceColor() == ChessGame.TeamColor.BLACK) {
            //Promo
            if (Moves.validPawnMove(blackRegularMove, getBoard()) && blackRegularMove.getRow() == 1) {
                moves.addAll(makePromotion(promos, getPosition(), blackRegularMove, moves));
            }
            else { //Regular move
                if (Moves.validPawnMove(blackRegularMove, getBoard())) {
                    moves.add(new ChessMove(getPosition(), blackRegularMove, null));
                }
            }

            //Capture right
            //Promo
            if (Moves.validPawnCap(blackCaptureRight, getBoard(), getPieceColor()) && blackCaptureRight.getRow() == 1) {
                moves.addAll(makePromotion(promos, getPosition(), blackCaptureRight, moves));
            }
            else { //Regular capture
                if (Moves.validPawnCap(blackCaptureRight, getBoard(), getPieceColor())) {
                    moves.add(new ChessMove(getPosition(), blackCaptureRight, null));
                }
            }

            //Capture left
            //Promo
            if (Moves.validPawnCap(blackCaptureLeft, getBoard(), getPieceColor()) && blackCaptureLeft.getRow() == 1) {
                moves.addAll(makePromotion(promos, getPosition(), blackCaptureLeft, moves));
            }
            else { //Regular capture
                if (Moves.validPawnCap(blackCaptureLeft, getBoard(), getPieceColor())) {
                    moves.add(new ChessMove(getPosition(), blackRegularMove, null));
                }
            }
        }
        return moves;
    }

    private Collection<ChessMove> makePromotion (ChessPiece.PieceType[] promos, ChessPosition oldPosition,
                                                 ChessPosition newPosition, Collection<ChessMove> moves) {
        for (ChessPiece.PieceType promo : promos) {
            moves.add(new ChessMove(oldPosition, newPosition, promo));
        }
        return moves;
    }
}
