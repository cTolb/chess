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
     * @return Collection of possible moves
     */
    public Collection<ChessMove> bishopMoves() {
        Collection<ChessMove> moves = new ArrayList<>();
        int beginRow = getPosition().getRow();
        int beginCol = getPosition().getColumn();

        for (int startRow = beginRow, startCol = beginCol; startRow <= 8 && startCol <= 8; startRow++, startCol++) {
            ChessPosition newPosition = new ChessPosition(startRow + 1, startCol + 1);
            if (Moves.validMove(newPosition, board, pieceColor)) {
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
            if (Moves.validMove(newPosition, board, pieceColor)) {
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
            if (Moves.validMove(newPosition, board, pieceColor)) {
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
            if (Moves.validMove(newPosition, board, pieceColor)) {
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
}
