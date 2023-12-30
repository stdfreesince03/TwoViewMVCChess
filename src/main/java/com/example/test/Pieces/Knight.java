package com.example.test.Pieces;

import com.example.test.GameUtils.ColorUtil;
import com.example.test.GameUtils.Location;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    public Knight(int rowPos, int colPos, ColorUtil color, int keyNum) {
        super(rowPos, colPos, color, keyNum);
        if (color.equals(ColorUtil.WHITE)) {
            super.pieceType = PieceType.KNIGHTW;
        } else {
            super.pieceType = PieceType.KNIGHTB;
        }
        super.setPieceKey(super.pieceType.getKey(keyNum));
    }

    @Override
    public boolean isValidPath(int row, int col) {
        int r0 = super.getPieceRowPos();
        int c0 = super.getPieceColPos();
        return (Math.abs(row-r0) == 1 && Math.abs(col-c0) ==2) || (Math.abs(row-r0) == 2 && Math.abs(col -c0) == 1 );
    }

    @Override
    public List<Location> getAllPath(int row, int col) {
         return new ArrayList<Location>(List.of(new Location(row+1,col+2),
                 new Location(row+1,col-2) ,
                 new Location(row+2,col-1),new Location(row+2,col+1),
                 new Location(row-2,col-1),new Location(row-2,col+1),
                 new Location(row-1,col-2),new Location(row-1,col+2)));
    }
}
