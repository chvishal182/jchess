package com.chelv.chess.engine.board;

public final class BoardUtils {

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private static boolean[] initColumn(int columNumber) {
        final boolean[] column = new boolean[64];

        do {
            column[columNumber] = true;
            columNumber += NUM_TILES_PER_ROW;
        } while (columNumber < NUM_TILES);

        return column;
    }

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }

}
