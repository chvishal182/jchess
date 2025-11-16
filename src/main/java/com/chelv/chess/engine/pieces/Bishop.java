package com.chelv.chess.engine.pieces;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.board.BoardUtils;

public class Bishop extends SlidingPiece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -7, 7, 9 };

    public Bishop(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.BISHOP, piecePosition, pieceAlliance);
    }

    @Override
    public int[] getCandidateMoveOffsets() {
        return CANDIDATE_MOVE_VECTOR_COORDINATES;
    }

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }

    @Override
    public boolean isColumnExclusion(final int currentPosition, final int candidateOffset) {
        return isBishopColumnExclusion(currentPosition, candidateOffset);
    }

    public static boolean isBishopColumnExclusion(final int currentPosition, final int candidateOffset) {
        return (BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7)) ||
               (BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7));
    }

}
