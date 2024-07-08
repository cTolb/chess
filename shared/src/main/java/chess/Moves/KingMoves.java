package chess.Moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

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
     * This function will return an ArrayList of possible moves a king can make at a given
     * position on the board
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> kingMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int currentRow = getPosition().getRow();
        int currentCol = getPosition().getColumn();

        if (currentRow <= 7) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
            }
            newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
            }
        }

        if (currentRow >= 2) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
            }
            newPosition = new ChessPosition(currentRow - 1, currentCol + 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
            }
        }

        if (currentCol <= 7) {
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol + 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
            }
        }
        if (currentCol >= 1) {
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol - 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
            }
        }
        return moves;
    }

    private boolean validMove (ChessPosition newPosition) {
        if (isInBounds(newPosition)) {
            if (getBoard().getPiece(newPosition) == null) {
                return true;
            }
            return getBoard().getPiece(newPosition).getTeamColor() != getPieceColor();
        }
        return false;
    }
    private boolean isInBounds (ChessPosition myPosition) {
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        return row <= 8 && col <= 8 && row >= 1 && col >= 1;
    }
}
