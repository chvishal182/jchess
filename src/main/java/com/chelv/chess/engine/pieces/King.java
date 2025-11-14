package com.chelv.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.board.Board;
import com.chelv.chess.engine.board.BoardUtils;
import com.chelv.chess.engine.board.Move;
import com.chelv.chess.engine.board.Move.AttackMove;
import com.chelv.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import com.chelv.chess.engine.board.Tile;

public class King extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};
    public King(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if(isColumnExclusion(this.piecePosition, currentCandidateOffset)){
                continue;
            }
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }else{
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance   = pieceAtDestination.getPieceAlliance();
                    if(this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString(){
        return PieceType.KING.toString();
    }

    @Override
    public boolean isColumnExclusion(final int currentPosition, final int candidateOffset) {
        return isKingColumnExclusion(currentPosition, candidateOffset);
    }

    public static boolean isKingColumnExclusion(final int currentPosition, final int candidateOffset) {
        return (BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||candidateOffset == 7)) ||
               (BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == 1  ||candidateOffset == -7));
    }

}
