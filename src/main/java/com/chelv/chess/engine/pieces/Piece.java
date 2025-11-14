package com.chelv.chess.engine.pieces;

import java.util.Collection;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.board.Board;
import com.chelv.chess.engine.board.Move;

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;

    Piece(int piecePosition, final Alliance pieceAlliance){
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        //!!TODO more work here
        this.isFirstMove   = false;  
    }

    //calculate all the legal moves
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public boolean isFirstMove(){
        return isFirstMove;
    }
    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public abstract boolean isColumnExclusion(int currentPosition, int candidateOffset);

    public Integer getPiecePosition() {
        return piecePosition;
    }

    public enum PieceType{
        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

        private String pieceName;

        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }
    }
}
