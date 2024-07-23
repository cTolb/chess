package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board = new ChessBoard();
    private TeamColor teamTurn;

    public ChessGame() {
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /*private ChessBoard copyBoard() {
        ChessBoard newBoard = new ChessBoard();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                newBoard.addPiece();
            }
        }
        return newBoard;
    }*/

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition){
        ChessPiece piece = board.getPiece(startPosition);
        Collection<ChessMove> potentialMoves = piece.pieceMoves(board, startPosition);
        Collection<ChessMove> moves = new ArrayList<>();
        ChessBoard workingBoard = board.copyBoard();

        if (piece == null) {
            return null;
        }

        for (ChessMove move : potentialMoves) {
            //Move piece to board to check if is in check
            movePiece(move);
            if (!isInCheck(piece.getTeamColor())) {
                moves.add(move);
            }
            //Set board back to actual board
            resetBoard(workingBoard);
        }
        return moves;

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece workingPiece = board.getPiece(move.getStartPosition());
        if (workingPiece == null) {
            throw new InvalidMoveException();
        }


    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king = findPiecePosition(teamColor, getBoard(), ChessPiece.PieceType.KING);
        Collection<ChessPosition> otherTeam = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece piece = board.getPiece(new ChessPosition(i ,j));
                if (piece != null) {
                    if (piece.getTeamColor() == opponentColor(teamColor)) {
                        otherTeam.add(new ChessPosition(i, j));
                    }
                }
            }
        }

        for (ChessPosition loc : otherTeam) {
            ChessPiece checkPiece = board.getPiece(loc);
            if (checkPiece != null && checkPiece.getTeamColor() == opponentColor(teamColor)) {
                Collection<ChessMove> moves = checkPiece.pieceMoves(board, loc);
                for (ChessMove move : moves) {
                    if (move.getEndPosition().equals(king)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void movePiece(ChessMove move) {
        if (move.getPromotionPiece() != null) {
            board.addPiece(move.getEndPosition(), new ChessPiece(board.getPiece(move.getStartPosition()).getTeamColor(), move.getPromotionPiece()));
        }
        else {
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        }
        board.addPiece(move.getStartPosition(), null);
    }

    private void resetBoard(ChessBoard workingBoard) {
        board = workingBoard.copyBoard();
    }

    public ChessPosition findPiecePosition(TeamColor teamColor, ChessBoard board, ChessPiece.PieceType typeToFind) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(row, col));

                if (checkPiece != null && checkPiece.getTeamColor() == teamColor && checkPiece.getPieceType() == typeToFind) {
                    return new ChessPosition(row, col);
                }
            }
        }
        return null;
    }

    private TeamColor opponentColor (TeamColor teamColor) {
        return switch (teamColor) {
            case WHITE -> TeamColor.BLACK;
            case BLACK -> TeamColor.WHITE;
        };
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this || getClass() != obj.getClass()) {
            return false;
        }
        ChessGame other = (ChessGame) obj;
        return getTeamTurn() == other.getTeamTurn() && getBoard() == other.getBoard();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
