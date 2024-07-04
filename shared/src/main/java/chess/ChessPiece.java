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

    public boolean validMove (ChessPosition myPosition, ChessBoard board) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (row >= 1 && col >= 1 && row <= 8 && col <= 8) {
            if (board.getPiece(myPosition) == null || board.getPiece(myPosition).getTeamColor() != pieceColor) {
                return true;
            }
        }
        return false;
    }

    public ChessMove addMove (ChessPosition currentPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        return new ChessMove(currentPosition, endPosition, promotionPiece);
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        //Create empty collection to store moves
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //Move Up
        if (currentRow != 1) {
            //Forward left
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (currentCol != 1 && validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
            }
            //Forward
            newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
            }
            //Forward Right
            newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (currentCol != 8 && validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
            }
        }

        //Move Down
        if (currentRow != 8) {
            //Back left
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (currentCol != 1 && validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
            }
            //Back
            newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
            }
            //Back Right
            newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (currentCol != 8 && validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
            }
        }

        //Left and Right
        ChessPosition newPosition = new ChessPosition(currentRow, currentCol + 1);
        if (currentCol != 8 && validMove(newPosition, board)) {
            moves.add(addMove(myPosition, newPosition, null));
        }
        newPosition = new ChessPosition(currentRow, currentCol - 1);
        if (currentCol != 1 && validMove(newPosition, board)) {
            moves.add(addMove(myPosition, newPosition, null));
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

        int firstRow = beginRow;
        int firstCol = beginCol;

        //Up and Right
        while (firstRow <= 8 && firstCol <= 8) {
            ChessPosition newPosition = new ChessPosition(firstRow + 1, firstCol + 1);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
                if(board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else{
                break;
            }

            firstRow++;
            firstCol++;
        }

        firstRow = beginRow;
        firstCol = beginCol;
        //Up and Left
        while (firstRow <= 8 && firstCol >= 1) {
            ChessPosition newPosition = new ChessPosition(firstRow + 1, firstCol - 1);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
                if(board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else{
                break;
            }

            firstRow++;
            firstCol--;
        }

        firstRow = beginRow;
        firstCol = beginCol;
        //Back and Right
        while (firstRow >= 1 && firstCol <=8) {
            ChessPosition newPosition = new ChessPosition(firstRow - 1, firstCol + 1);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
                if(board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else{
                break;
            }

            firstRow--;
            firstCol++;
        }

        firstRow = beginRow;
        firstCol = beginCol;
        //Back and Left
        while (firstRow >= 1 && firstCol >= 1) {
            ChessPosition newPosition = new ChessPosition(firstRow - 1, firstCol - 1);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
                if(board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else{
                break;
            }

            firstRow--;
            firstCol--;
        }

        return moves;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        return moves;
    }

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //Move right
        for (int i = currentCol; i <= 8; i++) {
            ChessPosition newPosition = new ChessPosition(currentRow, i + 1);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor){
                    break;
                }
            }
            else {
                break;
            }
        }

        //Move Left
        for (int i = currentCol; i >= 1; i--) {
            ChessPosition newPosition = new ChessPosition(currentRow, i - 1);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else {
                break;
            }
        }

        //Move up
        for (int i = currentRow; i <= 8; i++) {
            ChessPosition newPosition = new ChessPosition(i + 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
                if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() != pieceColor) {
                    break;
                }
            }
            else {
                break;
            }
        }

        //Move down
        for (int i = currentRow; i >= 1; i--) {
            ChessPosition newPosition = new ChessPosition(i - 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(addMove(myPosition, newPosition, null));
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

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        return moves;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return false;
        }
        if (getClass() != obj.getClass()) {
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
