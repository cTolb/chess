package chess;

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
            return kingMoves(board, myPosition);
        }
        if (pieceType ==PieceType.QUEEN) {
            return queenMoves(board, myPosition);
        }
        if (pieceType == PieceType.BISHOP) {
            return bishopMoves(board, myPosition);
        }
        if (pieceType == PieceType.KNIGHT) {
            return knightMoves(board, myPosition);
        }
        if (pieceType == PieceType.ROOK) {
            return rookMoves(board, myPosition);
        }
        if (pieceType == PieceType.PAWN) {
            return pawnMoves(board, myPosition);
        }
        return null;
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        if (currentRow != 1) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (currentCol != 1 && validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (currentCol != 8 && validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        if (currentRow != 8) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (currentCol != 1 && validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (currentCol != 8 && validMove(newPosition, board)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }

        ChessPosition newPosition = new ChessPosition(currentRow, currentCol + 1);
        if (currentCol != 8 && validMove(newPosition, board)) {
            moves.add(new ChessMove(myPosition, newPosition, null));
        }
        newPosition = new ChessPosition(currentRow, currentCol - 1);
        if (currentCol != 1 && validMove(newPosition, board)) {
            moves.add(new ChessMove(myPosition, newPosition, null));
        }
        return moves;
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        moves.addAll(rookMoves(board, myPosition));
        moves.addAll(bishopMoves(board, myPosition));

        return moves;
    }

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
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

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //Down
        if (currentRow >= 1 && currentRow <= 6) {
            //Down and left
            if(currentCol >= 2) {
                ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol - 1);
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

            //Down and right
            if (currentCol <= 7) {
                ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol + 1);
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        //Up
        if(currentRow <= 8 && currentRow >= 3) {
            //Up and left
            if (currentCol >= 2) {
                ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol - 1);
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

            //Up and right
            if (currentCol <= 7) {
                ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol + 1);
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        //Right
        if (currentCol <= 6 && currentCol >= 1) {
            //Right and up
            if (currentRow >= 2) {
                ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol + 2);
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

            //Right and Down
            if (currentRow <= 7) {
                ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 2);
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        //Left
        if (currentCol <= 8 && currentCol >= 3) {
            //Left and up
            if (currentRow >= 2) {
                ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 2);
                if(validMove(newPosition, board)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }

            //Left and down
            if (currentRow <= 7) {
                ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 2);
                if (validMove(newPosition, board)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        return moves;
    }

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

        //Left
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

        //Up
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

        //Down
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

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //White first moves
        if (currentRow == 2 && pieceColor == ChessGame.TeamColor.WHITE) {
            ChessPosition newPosition1 = new ChessPosition(currentRow + 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow + 2, currentCol);
            if (pawnValidMove(newPosition1, board)) {
                moves.add(new ChessMove(myPosition, newPosition1, null));
                if (pawnValidMove(newPosition2, board)) {
                    moves.add(new ChessMove(myPosition, newPosition2, null));
                }
            }
        }

        //Black first moves
        if (currentRow == 7 && pieceColor == ChessGame.TeamColor.BLACK) {
            ChessPosition newPosition1 = new ChessPosition(currentRow - 1, currentCol);
            ChessPosition newPosition2 = new ChessPosition(currentRow - 2, currentCol);
            if (pawnValidMove(newPosition1, board)) {
                moves.add(new ChessMove(myPosition, newPosition1, null));
                if (pawnValidMove(newPosition2, board)) {
                    moves.add(new ChessMove(myPosition, newPosition2, null));
                }
            }
        }

        //White regular move and promo
        if (ChessGame.TeamColor.WHITE == pieceColor) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (pawnValidMove(newPosition, board)) {
                if (newPosition.getRow() == 8) {
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                }
                else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        //Black regular move and promo
        if (ChessGame.TeamColor.BLACK == pieceColor) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (pawnValidMove(newPosition, board)) {
                if (newPosition.getRow() == 1) {
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                }
                else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        //White Capture
        if (ChessGame.TeamColor.WHITE == pieceColor) {
            //Right
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (pawnCapture(newPosition, board)) {
                if (newPosition.getRow() == 8) {
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                }
                else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
            //Left
            newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (pawnCapture(newPosition, board)) {
                if (newPosition.getRow() == 8) {
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                }
                else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        //Black Capture
        if (ChessGame.TeamColor.BLACK == pieceColor) {
            //Right
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (pawnCapture(newPosition, board)) {
                if (newPosition.getRow() == 1) {
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                }
                else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
            //Left
            newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (pawnCapture(newPosition, board)) {
                if (newPosition.getRow() == 1) {
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
                }
                else {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        return moves;
    }

    public boolean validMove (ChessPosition myPosition, ChessBoard board) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row >= 1 && col >= 1 && row <= 8 && col <= 8) {
            if (board.getPiece(myPosition) == null) {
                return true;
            }
            return board.getPiece(myPosition).getTeamColor() != pieceColor;
        }
        return false;
    }

    public boolean pawnValidMove(ChessPosition myPosition, ChessBoard board) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row >= 1 && col >= 1 && row <= 8 && col <= 8) {
            return board.getPiece(myPosition) == null;
        }
        return false;
    }

    public boolean pawnCapture(ChessPosition myPosition, ChessBoard board) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row >= 1 && col >= 1 && row <= 8 && col <= 8) {
            if (board.getPiece(myPosition) != null) {
                return board.getPiece(myPosition).getTeamColor() != pieceColor;
            }
        }
        return false;
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
