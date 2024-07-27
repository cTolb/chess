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

    private void changeTeamTurn(TeamColor color) {
        if (color == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        }
        if (color == TeamColor.BLACK) {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    private TeamColor getOpponentColor (TeamColor teamColor) {
        return switch (teamColor) {
            case WHITE -> TeamColor.BLACK;
            case BLACK -> TeamColor.WHITE;
        };
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

    private void resetBoard(ChessBoard resetBoard) {
        board = resetBoard.copyBoard();
    }

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
        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessBoard currentBoard = board.copyBoard();

        //If piece at startPosition is null return null
        if (piece == null) {
            return null;
        }

        for (int i = 0; i < potentialMoves.size(); i++) {
            //Move piece to board to check if is in check
            ChessMove possibleMove = (ChessMove) potentialMoves.toArray()[i];
            movePiece(possibleMove);
            if (!isInCheck(piece.getTeamColor())) {
                validMoves.add(possibleMove);
            }
            //Set board back to actual board
            resetBoard(currentBoard);
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece movingPiece = board.getPiece(move.getStartPosition());

        //Is there a piece at the starting point or piece the right color?
        if ((movingPiece == null) || (movingPiece.getTeamColor() != getTeamTurn())) {
            throw new InvalidMoveException();
        }
        //Is the move included in the possible moves?
        Collection<ChessMove> possibleMoves = validMoves(move.getStartPosition());
        if(!possibleMoves.contains(move)){
            throw new InvalidMoveException();
        }
        //Make Move on Board
        movePiece(move);
        //Change the Team color after turn
        changeTeamTurn(movingPiece.getTeamColor());
    }

    private void movePiece(ChessMove move) {
        TeamColor color = board.getPiece(move.getStartPosition()).getTeamColor();
        ChessPiece startingPiece = board.getPiece(move.getStartPosition());

        //Move piece to new position depending on piece type
        if (startingPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
            //Move piece depending on promotion piece
            if (move.getPromotionPiece() == ChessPiece.PieceType.ROOK) {
                board.addPiece(move.getEndPosition(), new ChessPiece(color, ChessPiece.PieceType.ROOK));
            }
            if (move.getPromotionPiece() == ChessPiece.PieceType.KNIGHT) {
                board.addPiece(move.getEndPosition(), new ChessPiece(color, ChessPiece.PieceType.KNIGHT));
            }
            if (move.getPromotionPiece() == ChessPiece.PieceType.BISHOP) {
                board.addPiece(move.getEndPosition(), new ChessPiece(color, ChessPiece.PieceType.BISHOP));
            }
            if (move.getPromotionPiece() == ChessPiece.PieceType.QUEEN) {
                board.addPiece(move.getEndPosition(), new ChessPiece(color, ChessPiece.PieceType.QUEEN));
            }
            if (move.getPromotionPiece() == null) {
                board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
            }
        }
        //Non pawn move
        else {
            board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        }

        //Remove piece from starting position
        board.addPiece(move.getStartPosition(), null);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king = findPiecePosition(teamColor, getBoard(), ChessPiece.PieceType.KING);
        TeamColor opponentTeamColor = getOpponentColor(teamColor);
        Collection<ChessPosition> otherTeam = findTeamPositions(opponentTeamColor, new ArrayList<>());


        for (int i = 0; i < otherTeam.size(); i++) {
            ChessPosition checkPosition = (ChessPosition) otherTeam.toArray()[i];
            ChessPiece checkPiece = getBoard().getPiece(checkPosition);
            if (checkPiece != null) {
                Collection<ChessMove> moves = checkPiece.pieceMoves(getBoard(), checkPosition);
                for (int j = 0; j < moves.size(); j++) {
                    ChessMove checkMove = (ChessMove) moves.toArray()[j];
                    if (checkMove.getEndPosition().equals(king)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //King must be in check to be in checkmate
        if (!isInCheck(teamColor)) {
            return false;
        }
        //Check to see if any other moves can be made
        return isMovePossible(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return false;
        }

        return isMovePossible(teamColor);
    }

    private boolean isMovePossible(TeamColor teamColor) {
        Collection<ChessPosition> currentTeamPositions = findTeamPositions(teamColor, new ArrayList<>());
        for (int i = 0; i < currentTeamPositions.size(); i++) {
            ChessPosition checkPosition = (ChessPosition) currentTeamPositions.toArray()[i];
            Collection<ChessMove> possibleMoves = validMoves(checkPosition);
            if (possibleMoves.size() > 0) {
                return false;
            }
        }
        return true;
    }

    private Collection<ChessPosition> findTeamPositions(TeamColor color, Collection<ChessPosition> team) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece piece = board.getPiece(new ChessPosition(i ,j));
                if (piece != null && piece.getTeamColor() == color) {
                    team.add(new ChessPosition(i, j));
                }
            }
        }
        return team;
    }

    public ChessPosition findPiecePosition(TeamColor teamColor, ChessBoard board, ChessPiece.PieceType typeToFind) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(i, j));
                //Check to make sure piece is what we are looking for
                if (checkPiece != null && checkPiece.getTeamColor() == teamColor && checkPiece.getPieceType() == typeToFind) {
                    return new ChessPosition(i, j);
                }
            }
        }
        return null;
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
