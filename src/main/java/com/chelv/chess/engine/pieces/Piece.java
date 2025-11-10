package com.chelv.chess.engine.pieces;

import java.util.Collection;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.board.Board;
import com.chelv.chess.engine.board.Move;

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;

    Piece(int piecePosition, final Alliance pieceAlliance){
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
    }

    //calculate all the legal moves
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }
}
