package com.chelv.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chelv.chess.engine.Alliance;
import com.chelv.chess.engine.pieces.Bishop;
import com.chelv.chess.engine.pieces.King;
import com.chelv.chess.engine.pieces.Knight;
import com.chelv.chess.engine.pieces.Pawn;
import com.chelv.chess.engine.pieces.Piece;
import com.chelv.chess.engine.pieces.Queen;
import com.chelv.chess.engine.pieces.Rook;
import com.chelv.chess.engine.player.BlackPlayer;
import com.chelv.chess.engine.player.Player;
import com.chelv.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;

public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces, blackPieces;
    private final Player whitePlayer, blackPlayer;

    private Board(Builder builder){
        this.gameBoard   = createGameBoard(builder);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);

        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces),
                               blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        blackPlayer = new BlackPlayer(this, blackStandardLegalMoves, whiteStandardLegalMoves);
        
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));

            if((i+1) % BoardUtils.NUM_TILES_PER_ROW == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Collection<Piece> getBlackPieces(){
        return this.blackPieces;
    } 

    public Collection<Piece> getWhitePieces(){
        return this.whitePieces;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
       final List<Move> legalMoves = new ArrayList<>();
       for(final Piece piece : pieces){
            legalMoves.addAll(piece.calculateLegalMoves(this));
       }
       return ImmutableList.copyOf(legalMoves);
    }

    private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        final List<Piece> activPieces = new ArrayList<>();
        for(final Tile tile : gameBoard){
            if(tile.isTileOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getPieceAlliance() == alliance){
                    activPieces.add(piece);
                }
            }
        }
        return activPieces;
    }

    public Tile getTile(final int tileCoordinate) {
        return gameBoard.get(tileCoordinate);
    }

    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard(){
        final Builder builder = new Builder();

        builder.setPiece(new Rook(0, Alliance.BLACK))
               .setPiece(new Knight(1, Alliance.BLACK))
               .setPiece(new Bishop(2, Alliance.BLACK))
               .setPiece(new Queen(3, Alliance.BLACK))
               .setPiece(new King(4, Alliance.BLACK))
               .setPiece(new Bishop(5,Alliance.BLACK))
               .setPiece(new Knight(6, Alliance.BLACK))
               .setPiece(new Rook(7,Alliance.BLACK))
               .setPiece(new Pawn(8, Alliance.BLACK))
               .setPiece(new Pawn(9, Alliance.BLACK))
               .setPiece(new Pawn(10, Alliance.BLACK))
               .setPiece(new Pawn(11, Alliance.BLACK))
               .setPiece(new Pawn(12, Alliance.BLACK))
               .setPiece(new Pawn(13, Alliance.BLACK))
               .setPiece(new Pawn(14, Alliance.BLACK))
               .setPiece(new Pawn(15, Alliance.BLACK));
        
        builder.setPiece(new Pawn(48, Alliance.WHITE))       
               .setPiece(new Pawn(49, Alliance.WHITE))
               .setPiece(new Pawn(50, Alliance.WHITE))
               .setPiece(new Pawn(51, Alliance.WHITE))
               .setPiece(new Pawn(52, Alliance.WHITE))
               .setPiece(new Pawn(53, Alliance.WHITE))
               .setPiece(new Pawn(54, Alliance.WHITE))
               .setPiece(new Pawn(55, Alliance.WHITE))
               .setPiece(new Rook(56,Alliance.WHITE))
               .setPiece(new Knight(57, Alliance.WHITE))
               .setPiece(new Bishop(58,Alliance.WHITE))
               .setPiece(new Queen(59, Alliance.WHITE))
               .setPiece(new King(60, Alliance.WHITE))
               .setPiece(new Bishop(61, Alliance.WHITE))
               .setPiece(new Knight(62, Alliance.WHITE))
               .setPiece(new Rook(63, Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);
        return builder.build();
    }

    public static class Builder{
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder(){
            this.boardConfig = new HashMap<>(); 
        }

        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return new Board(this);
        }
    }

}
