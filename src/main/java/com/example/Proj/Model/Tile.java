package com.example.Proj.Model;

import com.example.Proj.Pieces.Piece;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;

public class Tile {
    private Piece piece ;
    private LocAt.Location location ;
    private ColorUtil tileColor;

    public Tile(int row, int col, ColorUtil tileColor) {
        this.location = LocAt.at(row,col);
        this.piece = null;
        this.tileColor = tileColor;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    //check if a piece on a tile has a dest in it's path
    public boolean inPiecePath(Tile dest,GameBoard gameBoard){
        if(this.piece != null){
            return this.piece.possibleMovesContains(this.getLocation().row(), this.getLocation().col()
                    , dest.getLocation().row(),dest.getLocation().col(),gameBoard);
        }
        return false;

    }

    public boolean hasPiece(){
        return this.piece != null;
    }
    public Piece getPiece() {
        return this.piece;
    }

    public LocAt.Location getLocation() {
        return this.location;
    }

    public ColorUtil getTileColor() {
        return tileColor;
    }
}
