package com.chelv.chess.engine.pieces;

import java.util.Collection;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.board.Board;
import com.chelv.chess.engine.board.Move;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;

    Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance){
        this.pieceType     = pieceType; 
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
    
    public PieceType getPieceType() {
        return pieceType;
    }
    public enum PieceType{
        PAWN("P"){

            @Override
            public boolean isKing() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'isKing'");
            }

        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        };

        private String pieceName;

        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }

        public abstract boolean isKing();
    }

}
