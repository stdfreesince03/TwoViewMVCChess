package com.example.test.GameUtils;

public class Location {
    private int row;
    private int col;
    public Location(int row ,int col ){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol(){
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
