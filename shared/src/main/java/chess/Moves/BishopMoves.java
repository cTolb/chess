package chess.Moves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMoves {
    private final ChessGame.TeamColor pieceColor;
    private final ChessBoard board;
    private final ChessPosition position;
    public  BishopMoves (ChessBoard board, ChessPosition position, ChessGame.TeamColor pieceColor) {
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
     * This function returns an ArrayList of possibles moves for a bishop piece at a given location
     * on the board.
     * @return ArrayList of possible moves
     */
    public Collection<ChessMove> bishopMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int beginRow = getPosition().getRow();
        int beginCol = getPosition().getColumn();

        for (int startRow = beginRow, startCol = beginCol; startRow <= 8 && startCol <= 8; startRow++, startCol++) {
            ChessPosition newPosition = new ChessPosition(startRow + 1, startCol + 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
                if (getBoard().getPiece(newPosition) != null && getBoard().getPiece(newPosition).getTeamColor() != getPieceColor()) {
                    break;
                }
            }
            else {
                break;
            }
        }

        for (int startRow = beginRow, startCol = beginCol;startRow <= 8 && startCol >= 1; startRow++, startCol--) {
            ChessPosition newPosition = new ChessPosition(startRow + 1, startCol - 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
                if (getBoard().getPiece(newPosition) != null && getBoard().getPiece(newPosition).getTeamColor() != getPieceColor()) {
                    break;
                }
            }
            else {
                break;
            }
        }

        for (int startRow = beginRow, startCol = beginCol ;startRow >= 1 && startCol <= 8; startRow--, startCol++) {
            ChessPosition newPosition = new ChessPosition(startRow - 1, startCol + 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
                if (getBoard().getPiece(newPosition) != null && getBoard().getPiece(newPosition).getTeamColor() != getPieceColor()) {
                    break;
                }
            }
            else {
                break;
            }
        }

        for (int startRow = beginRow, startCol = beginCol;startRow >= 1 && startCol >= 1; startRow--, startCol--) {
            ChessPosition newPosition = new ChessPosition(startRow - 1, startCol - 1);
            if (validMove(newPosition)) {
                moves.add(new ChessMove(getPosition(), newPosition, null));
                if (getBoard().getPiece(newPosition) != null && getBoard().getPiece(newPosition).getTeamColor() != getPieceColor()) {
                    break;
                }
            }
            else {
                break;
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
    private boolean isInBounds (ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return row <= 8 && col <= 8 && row >= 1 && col >= 1;
    }
}
