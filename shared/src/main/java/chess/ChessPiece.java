package chess;

import chess.Moves.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //get piece type
        PieceType pieceType = getPieceType();

        //call move function depending on piece type, else return null
        if (pieceType == PieceType.KING) {
            KingMoves moves = new KingMoves(board, myPosition, getTeamColor());
            return moves.kingMoves();
        }
        if (pieceType ==PieceType.QUEEN) {
            QueenMoves moves = new QueenMoves(board, myPosition, getTeamColor());
            return moves.queenMoves();
        }
        if (pieceType == PieceType.BISHOP) {
            BishopMoves moves = new BishopMoves(board, myPosition, getTeamColor());
            return moves.bishopMoves();
        }
        if (pieceType == PieceType.KNIGHT) {
            KnightMoves moves = new KnightMoves(board, myPosition, getTeamColor());
            return moves.knightMoves();
        }
        if (pieceType == PieceType.ROOK) {
            RookMoves moves = new RookMoves(board, myPosition, getTeamColor());
            return moves.rookMoves();
        }
        if (pieceType == PieceType.PAWN) {
            PawnMoves moves = new PawnMoves(board, myPosition, getTeamColor());
            return moves.pawnMoves();
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, pieceColor);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || getClass() != obj.getClass()) {
            return false;
        }
        ChessPiece other = (ChessPiece) obj;
        return getTeamColor() == other.getTeamColor() && getPieceType() == other.getPieceType();
    }

    @Override
    public String toString() {
        return "Piece{" + "Color = " + pieceColor + " Type = " + type + "}";
    }
}
