package chess;

import chess.Moves.*;

import java.util.ArrayList;
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
            KingMoves moves = new KingMoves(board, myPosition, pieceColor);
            return moves.kingMoves();
        }
        if (pieceType ==PieceType.QUEEN) {
            return queenMoves(board, myPosition);
        }
        if (pieceType == PieceType.BISHOP) {
            BishopMoves moves = new BishopMoves(board, myPosition, pieceColor);
            return moves.bishopMoves();
        }
        if (pieceType == PieceType.KNIGHT) {
            KnightMoves moves = new KnightMoves(board, myPosition, pieceColor);
            return moves.knightMoves();
        }
        if (pieceType == PieceType.ROOK) {
            RookMoves moves = new RookMoves(board, myPosition, pieceColor);
            return moves.rookMoves();
        }
        if (pieceType == PieceType.PAWN) {
            PawnMoves moves = new PawnMoves(board, myPosition, pieceColor);
            return moves.pawnMoves();
        }
        return null;
    }


    /**
     * This function will return possible moves for a queen at a given position on the board. It
     * uses the rookMoves and bishopMoves functions.
     * @param board current game board
     * @param myPosition current piece position
     * @return ArrayList of possible moves
     */
    private Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        moves.addAll(rookMoves(board, myPosition));
        moves.addAll(bishopMoves(board, myPosition));

        return moves;
    }

    /**
     * This function returns an ArrayList of possibles moves for a bishop piece at a given location
     * on the board.
     * @param board current game board
     * @param myPosition current piece position
     * @return ArrayList of possible moves
     */
    private Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int beginRow = myPosition.getRow();
        int beginCol = myPosition.getColumn();

        for (int startRow = beginRow, startCol = beginCol; startRow <= 8 && startCol <= 8; startRow++, startCol++) {
            ChessPosition newPosition = new ChessPosition(startRow + 1, startCol + 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else {
                break;
            }
        }

        for (int startRow = beginRow, startCol = beginCol;startRow <= 8 && startCol >= 1; startRow++, startCol--) {
            ChessPosition newPosition = new ChessPosition(startRow + 1, startCol - 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else {
                break;
            }
        }

        for (int startRow = beginRow, startCol = beginCol ;startRow >= 1 && startCol <= 8; startRow--, startCol++) {
            ChessPosition newPosition = new ChessPosition(startRow - 1, startCol + 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else {
                break;
            }
        }

        for (int startRow = beginRow, startCol = beginCol;startRow >= 1 && startCol >= 1; startRow--, startCol--) {
            ChessPosition newPosition = new ChessPosition(startRow - 1, startCol - 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else {
                break;
            }
        }
        return moves;
    }

    /**
     * This function will return possible moves for a rook at a given position on the board.
     * @param board current game board
     * @param myPosition current piece position
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        for (int i = currentCol; i <= 8; i++) {
            ChessPosition newPosition = new ChessPosition(currentRow, i + 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
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
                moves.add(new ChessMove(myPosition, newPosition, null));
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
                moves.add(new ChessMove(myPosition, newPosition, null));
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
                moves.add(new ChessMove(myPosition, newPosition, null));
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

    /**
     * This function determines if a given move is valid.
     * @param newPosition Proposed new position
     * @param board current game board
     * @return boolean if the move is valid
     */
    private boolean validMove (ChessPosition newPosition, ChessBoard board) {
        if (type == PieceType.PAWN) {
            if (isInBounds(newPosition)) {
                return board.getPiece(newPosition) == null;
            }
        }
        else {
            if (isInBounds(newPosition)) {
                if (board.getPiece(newPosition) == null) {
                    return true;
                }
                return board.getPiece(newPosition).getTeamColor() != pieceColor;
            }
        }
        return false;
    }

    /**
     * This function determines if the given move of a pawn is a valid capture move.
     * @param newPosition proposed new position
     * @param board current game board
     * @return boolean if the move is valid capture
     */
    private boolean validCap(ChessPosition newPosition, ChessBoard board) {
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

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || getClass() != obj.getClass()) {
            return false;
        }
        ChessPiece other = (ChessPiece) obj;
        return pieceColor == other.pieceColor && type == other.type;
    }

    @Override
    public String toString() {
        return "Piece{" + "Color = " + pieceColor + " Type = " + type + "}";
    }
}
