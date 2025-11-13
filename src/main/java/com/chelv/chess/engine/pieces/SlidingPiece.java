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
import com.chelv.chess.engine.board.Tile;
import com.google.common.collect.ImmutableList;

public abstract class SlidingPiece extends Piece {

    SlidingPiece(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset : getCandidateMoveOffsets()) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(
                                    new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }


    public abstract int[] getCandidateMoveOffsets();

    public abstract boolean isColumnExclusion(int currentPosition, int candidateOffset);
}