package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {
        return startPosition;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {
        return endPosition;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += startPosition.getColumn() * 31;
        hash += startPosition.getRow() * 31;
        hash += endPosition.getColumn() * 31;
        hash += endPosition.getRow() * 31;
        hash += Objects.hash(promotionPiece);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || getClass() != obj.getClass()) {
            return false;
        }
        ChessMove other = (ChessMove) obj;
        return Objects.deepEquals(startPosition, other.startPosition) &&
                Objects.equals(endPosition, other.endPosition) &&
                promotionPiece == other.promotionPiece;
    }

    @Override
    public String toString() {
        return "Chess Move: Start Position = " + startPosition + ", End Position = " + endPosition + ", Promotion Piece = " + promotionPiece + ".";
    }
}
