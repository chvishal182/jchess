package com.chelv.chess.engine.pieces;

import com.chelv.chess.engine.Alliance;

public class Queen extends SlidingPiece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };


    public Queen(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.QUEEN, piecePosition, pieceAlliance);
    }

    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }

    @Override
    public int[] getCandidateMoveOffsets() {
        return CANDIDATE_MOVE_VECTOR_COORDINATES;
    }

     @Override
    public boolean isColumnExclusion(final int currentPosition, final int candidateOffset) {
        return Rook.isRookColumnExclusion(currentPosition, candidateOffset) ||
               Bishop.isBishopColumnExclusion(currentPosition, candidateOffset);
    }

}
