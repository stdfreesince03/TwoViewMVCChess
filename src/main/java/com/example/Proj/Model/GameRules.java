package com.example.Proj.Model;

import com.example.Proj.Pieces.*;
import com.example.Proj.Util.ColorUtil;
import com.example.Proj.Util.LocAt;
import com.example.Proj.Util.LocAt.*;
import jdk.swing.interop.SwingInterOpUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GameRules {
    private static GameBoard gameBoard;

    public GameRules(GameBoard gb) {
        gameBoard = gb;
    }

    public static boolean validMove(Tile src,Tile tgt){
       return  src.inPiecePath(tgt,gameBoard)  && !kingEndangered(src.getLocation(),tgt.getLocation(),gameBoard);
    }

    //used for all concerning check : checkmate , open position , king path that is not possible / guarded
    public static boolean kingEndangered(Location curr, Location next, GameBoard gameBoard) {
        Piece srcPiece = gameBoard.getTile(curr.row(), curr.col()).getPiece();
        Piece destPiece = gameBoard.getTile(next.row(), next.col()).getPiece();

        if(destPiece instanceof King){
            return false;
        }

        // Perform the move
        gameBoard.getTile(curr.row(), curr.col()).setPiece(null);
        gameBoard.getTile(next.row(), next.col()).setPiece(srcPiece);

        boolean isSafe = !isKingInCheck(srcPiece.getColor());

        // Revert the move
        gameBoard.getTile(curr.row(), curr.col()).setPiece(srcPiece);
        gameBoard.getTile(next.row(), next.col()).setPiece(destPiece);


        return !isSafe;
    }

    public static boolean stalemate(Move move){
        King king = move.getPiece().getColor() == ColorUtil.BLACK ? gameBoard.getWhiteKing() : gameBoard.getBlackKing();
        return  kingHasNoMove(move) && !pieceCanBlock(move, true);
    }

    public static ColorUtil checkMate(Move move){
        ColorUtil color = move.getPiece().getColor();
        System.out.println(isKingInCheckAfterMove(move));
        System.out.println(kingHasNoMove(move));
        if(!isKingInCheckAfterMove(move)){
            return null;
        }
        if(!kingHasNoMove(move)) {
            return null;
        }
        if(pieceCanBlock(move,false)) {
            return null;
        }
        return color;
    }

    //Because a check is certain (code above after isKingInCheckAfterMove), check if a king has an escape or not
    private static boolean kingHasNoMove(Move move){
        King king = move.getPiece().getColor() == ColorUtil.BLACK ? gameBoard.getWhiteKing() : gameBoard.getBlackKing();
        Location kingLoc = gameBoard.getPieceLocation(king);
        List<Location> check = king.getPossibleMoves(kingLoc.row(),kingLoc.col(),gameBoard);
        return check.isEmpty();
    }

    private static boolean isKingInCheckAfterMove(Move move){
        Location to= move.getTo();
        Piece p = move.getPiece();
        ColorUtil color = p.getColor();
        Location kingLoc = gameBoard.getPieceLocation((color == ColorUtil.BLACK) ?
                gameBoard.getWhiteKing() : gameBoard.getBlackKing());
        return p.possibleMovesContains(to.row(),to.col(),kingLoc.row(),kingLoc.col(),gameBoard);
    }

    //used to check stalemate and checkmate as well, stalemate=true check stalemate otherwise check if checkmate can be blocked/negated
    private static boolean pieceCanBlock(Move move, boolean stalemate){
        Piece p = move.getPiece();
        Location to = move.getTo();
        ColorUtil color = p.getColor();
        Location kingLocation = (color == ColorUtil.BLACK) ? gameBoard.getPieceLocation(gameBoard.getWhiteKing()) :
                gameBoard.getPieceLocation(gameBoard.getBlackKing());
        if(stalemate){
            for(int i = 0;i<8;i++){
                for(int j = 0;j<8;j++){
                    Tile t = gameBoard.getTile(i,j);
                    if(t.hasPiece() && t.getPiece().getColor() != p.getColor()){
                        if(!t.getPiece().getPossibleMoves(i, j, gameBoard).isEmpty()) return true;
                    }
                }
            }

        }else{
            if(kingLocation != null){
                for(int i = 0;i<8;i++){
                    for(int j = 0;j<8;j++){
                        Tile t = gameBoard.getTile(i,j);
                        if(t.hasPiece() && t.getPiece().getColor() != p.getColor()){
                            List<Location> tMoves = t.getPiece().getPossibleMoves(i,j,gameBoard);
                            for(Location m : tMoves){
                                if(!kingEndangered(LocAt.at(i,j),m,gameBoard)){
                                    return true;
                                }
                            }
                        }

                    }
                }
            }
        }
        return false;
    }

    public static boolean isKingInCheck(ColorUtil color) {
        // Get the king of the specified color
        King king = (color==ColorUtil.BLACK) ? gameBoard.getBlackKing() : gameBoard.getWhiteKing();;
        Location loc = gameBoard.getPieceLocation(king);

        int row = loc.row();
        int col = loc.col();

        // Check straight threats
        if (checkStraightThreats(king, row, col))return true;

        // Check diagonal threats
        if (checkDiagonalThreats(king, row, col)) return true;

        // Check knight threats
        if (checkKnightThreats(row, col,color)) return true;

        // Check pawn attacks
        if (checkPawnAttacks( row, col,color))return true;

        // Check king threats
        if (checkKingThreats(king,row, col,color )) return true;

        return false;
    }

    //below until end are threats check and helper functions

    private static boolean checkStraightThreats(Piece king, int row, int col) {
        List<Location> straightThreats = MovementHelper.straight(row, col, king,gameBoard);
        for (Location loc : straightThreats) {
            Piece piece = gameBoard.getTile(loc.row(), loc.col()).getPiece();
            if (piece != null && piece.getColor() != king.getColor() &&
                    (piece instanceof Queen || piece instanceof Rook)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonalThreats(Piece king, int row, int col) {
        List<Location> diagonalThreats = MovementHelper.diagonal(row, col, king, gameBoard);
        for (Location loc : diagonalThreats) {
            Piece piece = gameBoard.getTile(loc.row(), loc.col()).getPiece();
            if (piece != null && piece.getColor() != king.getColor() &&
                    (piece instanceof Queen || piece instanceof  Bishop )) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkKnightThreats(int row, int col,ColorUtil color) {
        List<Location> knightMoves = MovementHelper.knightSpecial(row, col);
        for (Location loc : knightMoves) {
            Piece piece = gameBoard.getTile(loc.row(),loc.col()).getPiece();
            if (piece != null && color != piece.getColor() &&
                    piece instanceof  Knight) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkPawnAttacks( int row, int col,ColorUtil color) {
        List<Location> pawnAttacks = MovementHelper.pawnAttacks(row, col,color);
        for (Location loc : pawnAttacks) {
            Piece piece = gameBoard.getTile(loc.row(),loc.col()).getPiece();
            if (piece != null && piece.getColor() != color && piece instanceof Pawn) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkKingThreats(King king , int row, int col,ColorUtil color) {
        List<Location> kingMoves = MovementHelper.kingSpecial(king ,row,col,gameBoard);
        for (Location loc : kingMoves) {
            Piece piece = gameBoard.getTile(loc.row(),loc.col()).getPiece();
            if (piece != null && color != piece.getColor()
                    && piece instanceof  King) {
                return true;
            }
        }
        return false;
    }


  public static boolean validEnemy(Tile  src, Tile tgt){
        if(src == tgt){
            return false;
        }
       return tgt.hasPiece() &&
               tgt.getPiece().getColor() != src.getPiece().getColor() &&
               src.inPiecePath(tgt,gameBoard);
   }

   public static boolean validEmpty(Tile src, Tile tgt){
        if(src == tgt){
            return false;
        }
        return !tgt.hasPiece() && src.inPiecePath(tgt,gameBoard);
   }

}
