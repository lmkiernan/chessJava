package org.cis1200.chess;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class Chess {

    private int whiteNumPoints;
    private int blackNumPoints;
    private boolean white;
    private boolean gameOver;
    private List<Piece> whitePieces = new LinkedList<>();
    private List<Piece> blackPieces = new LinkedList<>();

    public Chess() {
        reset();
    }

    public void reset() {
        white = true;
        gameOver = false;
        whiteStart(whitePieces);
        blackStart(blackPieces);
    }

    public void whiteStart(List<Piece> wp) {
        wp.clear();
        Piece wpawn1 = new Piece("Pawn", 6, 0, true);
        Piece wpawn2 = new Piece("Pawn", 6, 1, true);
        Piece wpawn3 = new Piece("Pawn", 6, 2, true);
        Piece wpawn4 = new Piece("Pawn", 6, 3, true);
        Piece wpawn5 = new Piece("Pawn", 6, 4, true);
        Piece wpawn6 = new Piece("Pawn", 6, 5, true);
        Piece wpawn7 = new Piece("Pawn", 6, 6, true);
        Piece wpawn8 = new Piece("Pawn", 6, 7, true);
        Piece wrook1 = new Piece("Rook", 7, 0, true);
        Piece wrook2 = new Piece("Rook", 7, 7, true);
        Piece wknight1 = new Piece("Knight", 7, 1, true);
        Piece wknight2 = new Piece("Knight", 7, 6, true);
        Piece wbishop1 = new Piece("Bishop", 7, 2, true);
        Piece wbishop2 = new Piece("Bishop", 7, 5, true);
        Piece wking = new Piece("King", 7, 4, true);
        Piece wqueen = new Piece("Queen", 7, 3, true);
        wp.add(wpawn1);
        wp.add(wpawn2);
        wp.add(wpawn3);
        wp.add(wpawn4);
        wp.add(wpawn5);
        wp.add(wpawn6);
        wp.add(wpawn7);
        wp.add(wpawn8);
        wp.add(wrook1);
        wp.add(wrook2);
        wp.add(wknight1);
        wp.add(wknight2);
        wp.add(wbishop1);
        wp.add(wbishop2);
        wp.add(wking);
        wp.add(wqueen);
    }

    public void blackStart(List<Piece> bp) {
        bp.clear();
        Piece bpawn1 = new Piece("Pawn", 1, 0, false);
        Piece bpawn2 = new Piece("Pawn", 1, 1, false);
        Piece bpawn3 = new Piece("Pawn", 1, 2, false);
        Piece bpawn4 = new Piece("Pawn", 1, 3, false);
        Piece bpawn5 = new Piece("Pawn", 1, 4, false);
        Piece bpawn6 = new Piece("Pawn", 1, 5, false);
        Piece bpawn7 = new Piece("Pawn", 1, 6, false);
        Piece bpawn8 = new Piece("Pawn", 1, 7, false);
        Piece brook1 = new Piece("Rook", 0, 0, false);
        Piece brook2 = new Piece("Rook", 0, 7, false);
        Piece bknight1 = new Piece("Knight", 0, 1, false);
        Piece bknight2 = new Piece("Knight", 0, 6, false);
        Piece bbishop1 = new Piece("Bishop", 0, 2, false);
        Piece bbishop2 = new Piece("Bishop", 0, 5, false);
        Piece bking = new Piece("King", 0, 4, false);
        Piece bqueen = new Piece("Queen", 0, 3, false);
        bp.add(bpawn1);
        bp.add(bpawn2);
        bp.add(bpawn3);
        bp.add(bpawn4);
        bp.add(bpawn5);
        bp.add(bpawn6);
        bp.add(bpawn7);
        bp.add(bpawn8);
        bp.add(brook1);
        bp.add(brook2);
        bp.add(bknight1);
        bp.add(bknight2);
        bp.add(bbishop1);
        bp.add(bbishop2);
        bp.add(bking);
        bp.add(bqueen);
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public boolean isOccupied(int row, int col) {

        for (int i = 0; i < whitePieces.size(); i++) {
            if (whitePieces.get(i).getRow() == row && whitePieces.get(i).getCol() == col) {
                return true;
            }
        }

        for (int i = 0; i < blackPieces.size(); i++) {
            if (blackPieces.get(i).getRow() == row && blackPieces.get(i).getCol() == col) {
                return true;
            }
        }

        return false;
    }

    public Piece choosePiece(int row, int col) {
        for (int i = 0; i < whitePieces.size(); i++) {
            if (whitePieces.get(i).getRow() == row && whitePieces.get(i).getCol() == col) {
                return whitePieces.get(i);
            }
        }
        for (int i = 0; i < blackPieces.size(); i++) {
            if (blackPieces.get(i).getRow() == row && blackPieces.get(i).getCol() == col) {
                return blackPieces.get(i);
            }
        }
        return null;
    }

    public List<int[]> reducedPossibilities(List<int[]> array) {
        int x = -1;
        int y = -1;
        for (int i = 0; i < array.size(); i++) {
            x = array.get(i)[0];
            y = array.get(i)[1];
            if (isOccupied(x, y)) {
                array.remove(i);
            }
        }
        return array;
    }
}