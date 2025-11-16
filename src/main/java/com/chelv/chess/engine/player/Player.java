package com.chelv.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.board.Board;
import com.chelv.chess.engine.board.Move;
import com.chelv.chess.engine.pieces.King;
import com.chelv.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    

    private final boolean isInCheck;
    protected final Collection<Move> legalMoves;

    

    Player(Board board,
            final Collection<Move> legaMoves,
            final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legaMoves;
        this.isInCheck  = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty(); 

    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }
    public King getPlayerKing() {
        return playerKing;
    }

    private static Collection<Move> calculateAttacksOnTile(Integer piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(Move move: moves){
            if(piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    private King establishKing(){
        for(final Piece piece : getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }

        throw new RuntimeException("Shouldnt reach here! Not a valid state");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    // work to do 
    public boolean isCheck(){
        return this.isInCheck;
    }

    // work to do 
    public boolean isCheckMate(){
        return isCheck() && !hasEscapeMoves();
    }
    
    public boolean isStaleMate(){
        return !this.isInCheck && !hasEscapeMoves();
    }

    // work to do
    public boolean isCastled(){
        return false;
    }

    public MoveTransition makeMove(final Move move){
        if(!isMoveLegal(move)){
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final Board transitionBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile
                                            ( transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition(), 
                                              transitionBoard.getCurrentPlayer().getLegalMoves()
                                            );
        
        
        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    /*
     * We are going to create an imaginary board
     * with all the legalMoves we have like studing the board
     * and evaluating if we are able to make our king move
     * when he is inCheck. If so and we succeed then our king
     * is noLonger under attack
     */
    private boolean hasEscapeMoves() {
        for(final Move move: this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }


    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();
}
