package com.chelv.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.board.Board;
import com.chelv.chess.engine.board.BoardUtils;
import com.chelv.chess.engine.board.Move;
import com.chelv.chess.engine.board.Tile;
import com.chelv.chess.engine.board.Move.AttackMove;
import com.chelv.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17 };

    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

                if (isColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if (!this.pieceAlliance.equals(pieceAlliance)) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    @Override
    public boolean isColumnExclusion(final int currentPosition, final int candidateOffset) {
        return isKnightColumnExclusion(currentPosition, candidateOffset);
    }

    public static boolean isKnightColumnExclusion(final int currentPosition, final int candidateOffset) {
    return (BoardUtils.FIRST_COLUMN[currentPosition] && 
               // These conditions belong together
               (candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15)) ||
           (BoardUtils.SECOND_COLUMN[currentPosition] && 
               (candidateOffset == -10 || candidateOffset == 6)) ||
           (BoardUtils.SEVENTH_COLUMN[currentPosition] && 
               (candidateOffset == -6 || candidateOffset == 10)) ||
           (BoardUtils.EIGHTH_COLUMN[currentPosition] &&
               (candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17));
}

}
