package com.example.test.Pieces;

import com.example.test.GameUtils.ColorUtil;
import com.example.test.GameUtils.GameBoard;
import com.example.test.GameUtils.Location;
import com.example.test.GameUtils.Tile;

import java.util.ArrayList;
import java.util.List;

public class Rook {
//    public Queen(int rowPos, int colPos, ColorUtil color, int keyNum){
//        super(rowPos, colPos);
//        super.setPieceType(color.equals(ColorUtil.WHITE) ? PieceType.QUEENW: PieceType.QUEENB );
//        super.setKey(keyNum);
//    }
//
//    @Override
//    public boolean isValidPath(int row, int col, GameBoard gb) {
//        Tile[][] tiles = gb.getTiles();
//        if(tiles[row][col] == null && tiles[row][col].samePieceColor(tiles[super.getPieceRow()][super.getPieceCol()]) ){
//            return false;
//        }
//        if(super.getPieceCol() == col && super.getPieceRow() != row){
//            int start = super.getPieceRow();
//            int index = row > start ? 1 : -1;
//            start+= index;
//            while(start != row){
//                if(tiles[start][col].hasPiece()){
//                    System.out.println(start + " " + col);
//                    return false;
//                }
//                start+= index;
//
//            }
//            return true;
//
//        } else if(super.getPieceCol() != col && super.getPieceRow() == row){
//
//            int start = super.getPieceCol();
//            int index = col > start ? 1 : -1;
//            start += index;
//
//            while(start != col){
//                if(tiles[row][start].hasPiece()){
//                    return false;
//                }
//                start += index;
//            }
//            return true;
//        }else if( Math.abs(super.getPieceCol() - col )  == Math.abs(super.getPieceRow() - row)) {
//            System.out.println("three");
//            int startX = super.getPieceCol();
//            int startY = super.getPieceRow();
//            int xAxisDec = (col - startX< 0 )  ? -1 : 1 ;
//            int yAxisDec =(row-startY < 0) ? -1 : 1 ;
//
//            startX += xAxisDec;
//            startY += yAxisDec;
//            while(col != startX || row != startY){
//                if(tiles[startY][startX].hasPiece()){
//                    return false;
//                }
//                startX += xAxisDec;
//                startY += yAxisDec;
//
//            }
//
//            return true;
//        }
//        return true;
//    }
//
//    @Override
//    public List<Location> getAllPath(int row, int col) {
//        return new ArrayList<>(List.of(new Location(7,7)));
//    }
}
