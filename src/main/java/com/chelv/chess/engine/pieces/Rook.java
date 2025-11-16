package com.chelv.chess.engine.pieces;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.board.BoardUtils;

public class Rook extends SlidingPiece {
    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -8, -1, 1, 8 };

    public Rook(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.ROOK, piecePosition, pieceAlliance);
    }

    @Override
    public String toString(){
        return PieceType.ROOK.toString();
    }

    @Override
    public int[] getCandidateMoveOffsets() {
        return CANDIDATE_MOVE_VECTOR_COORDINATES;
    }

    @Override
    public boolean isColumnExclusion(final int currentPosition, final int candidateOffset) {
        return isRookColumnExclusion(currentPosition, candidateOffset);
    }

    public static boolean isRookColumnExclusion(final int currentPosition, final int candidateOffset) {
        return (BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1)) ||
                (BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1));
    }
}