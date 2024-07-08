package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  KingMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
        this.pieceColor = pieceColor;
        this.board = board;
        this.position = position;
    }
    public Collection<ChessMove> calcMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = position.getRow();
        int currentCol = position.getColumn();

        if (currentRow <= 7) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
            }
        }

        if (currentRow >= 2) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
            }
        }

        if (currentCol <= 7) {
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol + 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
            }
        }
        if (currentCol >= 1) {
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol - 1);
            if (validMove(newPosition, board)) {
                moves.add(new ChessMove(position, newPosition, null));
            }
        }
        return moves;
    }

    private boolean validMove (ChessPosition newPosition, ChessBoard board) {
        if (isInBounds(newPosition)) {
            if (board.getPiece(newPosition) == null) {
                return true;
            }
            return board.getPiece(newPosition).getTeamColor() != pieceColor;
        }
        return false;
    }
    private boolean isInBounds (ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        return row <= 8 && col <= 8 && row >= 1 && col >= 1;
    }
}
