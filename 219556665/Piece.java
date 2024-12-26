package org.cis1200.chess;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Piece {
    private String pieceType;
    private int row;
    private int col;
    private boolean isWhite;
    private boolean isAlive;
    public String imgFile;
    private BufferedImage img;
    private List<int[]> possiblePlaces;

    public Piece(String pieceType, int row, int col, boolean isWhite) {
        this.row = row;
        this.col = col;
        this.pieceType = pieceType;
        this.isWhite = isWhite;
        this.isAlive = true;
        if (isWhite) {
            if (pieceType.equals("Pawn")) {
                imgFile = "files/whitePawn.png";
            }
            if (pieceType.equals("Knight")) {
                imgFile = "files/whiteKnight.png";
            }
            if (pieceType.equals("Bishop")) {
                imgFile = "files/whiteBishop.png";
            }
            if (pieceType.equals("Rook")) {
                imgFile = "files/whiteRook.png";
            }
            if (pieceType.equals("Queen")) {
                imgFile = "files/whiteQueen.png";
            }
            if (pieceType.equals("King")) {
                imgFile = "files/whiteKing.png";
            }
        } else {
            if (pieceType.equals("Pawn")) {
                imgFile = "files/blackPawn.png";
            }
            if (pieceType.equals("Knight")) {
                imgFile = "files/blackKnight.png";
            }
            if (pieceType.equals("Bishop")) {
                imgFile = "files/blackBishop.png";
            }
            if (pieceType.equals("Rook")) {
                imgFile = "files/blackRook.png";
            }
            if (pieceType.equals("Queen")) {
                imgFile = "files/blackQueen.png";
            }
            if (pieceType.equals("King")) {
                imgFile = "files/blackKing.png";
            }
        }

        try {
            if (img == null) {
                img = ImageIO.read(new File(imgFile));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        this.possiblePlaces = getPossiblePlaces();
    }

    public int getRow() {
        return row;
    }

    public String getTeam() {
        if (isWhite) {
            return "White";
        } else {
            return "Black";
        }
    }

    public int getCol() {
        return col;
    }

    public String getPieceType() {
        return pieceType;
    }

    public int[] getPlaceOnBoard() {
        int[] place = { row, col };
        return place;
    }

    public void teamCheck(int[] holder) {
        if (!isWhite) {
            holder[0] = 7 - 0;
        }
    }

    public List<int[]> getPossiblePlaces() {
        List<int[]> output = new LinkedList<>();

        if (getPieceType().equals("Pawn")) {
            if (isWhite) {
                int i = 1;
                int[] holder = new int[2];
                while (i < 3 && row - i >= 0) {
                    holder[0] = row - i;
                    holder[1] = col;
                    output.add(holder.clone());
                    i++;
                    if (row != 6) {
                        i++;
                    }
                }
            } else {
                int i = 1;
                int[] holder = new int[2];
                while (i < 3 && row + i <= 7) {
                    holder[0] = row + i;
                    holder[1] = col;
                    output.add(holder.clone());
                    i++;
                    if (row != 1) {
                        i++;
                    }
                }
            }
        }

        if (getPieceType().equals("Knight")) {
            int[] holder = new int[2];
            holder[0] = row + 2;
            holder[1] = col + 1;
            if (holder[0] <= 7 && holder[1] <= 7) {
                output.add(holder.clone());
            }

            holder[0] = row + 2;
            holder[1] = col - 1;
            if (holder[0] <= 7 && holder[1] >= 0) {
                output.add(holder.clone());
            }

            holder[0] = row - 2;
            holder[1] = col + 1;
            if (holder[0] >= 0 && holder[1] <= 7) {
                output.add(holder.clone());
            }

            holder[0] = row - 2;
            holder[1] = col - 1;
            if (holder[0] >= 0 && holder[1] >= 0) {
                output.add(holder.clone());
            }

            holder[0] = row + 1;
            holder[1] = col + 2;
            if (holder[0] <= 7 && holder[1] <= 7) {
                output.add(holder.clone());
            }

            holder[0] = row + 1;
            holder[1] = col - 2;
            if (holder[0] <= 7 && holder[1] >= 0) {
                output.add(holder.clone());
            }

            holder[0] = row - 1;
            holder[1] = col + 2;
            if (holder[0] >= 0 && holder[1] <= 7) {
                output.add(holder.clone());
            }

            holder[0] = row - 1;
            holder[1] = col - 2;
            if (holder[0] >= 0 && holder[1] >= 0) {
                output.add(holder.clone());
            }
        }

        if (getPieceType().equals("Bishop")) {
            int[] holder = new int[2];
            int i = 1;
            while (row + i <= 7 && col + i <= 7) {
                holder[0] = row + i;
                holder[1] = col + i;
                output.add(holder.clone());
                i++;
            }
            i = 1;
            while (row + i <= 7 && col - i >= 0) {
                holder[0] = row + i;
                holder[1] = col - i;
                output.add(holder.clone());
                i++;
            }
            i = 1;
            while (row - i >= 0 && col + i <= 7) {
                holder[0] = row - i;
                holder[1] = col + i;
                output.add(holder.clone());
                i++;
            }
            i = 1;
            while (row - i >= 0 && col - i >= 0) {
                holder[0] = row - i;
                holder[1] = col - i;
                output.add(holder.clone());
                i++;
            }
        }

        if (getPieceType().equals("Rook")) {
            int[] holder = new int[2];
            for (int i = 1; row + i <= 7; i++) {
                holder[0] = row + i;
                holder[1] = col;
                output.add(holder.clone());
            }
            for (int i = 1; row - i >= 0; i++) {
                holder[0] = row - i;
                holder[1] = col;
                output.add(holder.clone());
            }

            for (int i = 1; col + i <= 7; i++) {
                holder[0] = row;
                holder[1] = col + i;
                output.add(holder.clone());
            }

            for (int i = 1; col - i >= 0; i++) {
                holder[0] = row;
                holder[1] = col - i;
                output.add(holder.clone());
            }
        }

        if (getPieceType().equals("Queen")) {
            int[] holder = new int[2];
            int i = 1;
            while (row + i <= 7 && col + i <= 7) {
                holder[0] = row + i;
                holder[1] = col + i;
                output.add(holder.clone());
                i++;
            }
            i = 1;
            while (row + i <= 7 && col - i >= 0) {
                holder[0] = row + i;
                holder[1] = col - i;
                output.add(holder.clone());
                i++;
            }
            i = 1;
            while (row - i >= 0 && col + i <= 7) {
                holder[0] = row - i;
                holder[1] = col + i;
                output.add(holder.clone());
                i++;
            }
            i = 1;
            while (row - i >= 0 && col - i >= 0) {
                holder[0] = row - i;
                holder[1] = col - i;
                output.add(holder.clone());
                i++;
            }
            for (int j = 1; row + j <= 7; j++) {
                holder[0] = row + j;
                holder[1] = col;
                output.add(holder.clone());
            }
            for (int j = 1; row - j >= 0; j++) {
                holder[0] = row - j;
                holder[1] = col;
                output.add(holder.clone());
            }

            for (int j = 1; col + j <= 7; j++) {
                holder[0] = row;
                holder[1] = col + j;
                output.add(holder.clone());
            }

            for (int j = 1; col - j >= 0; j++) {
                holder[0] = row;
                holder[1] = col - j;
                output.add(holder.clone());
            }
        }

        if (getPieceType().equals("King")) {
            int[] holder = new int[2];
            holder[0] = row + 1;
            holder[1] = col + 1;
            if (holder[0] <= 7 && holder[1] <= 7) {
                output.add(holder.clone());
            }

            holder[0] = row + 1;
            holder[1] = col - 1;
            if (holder[0] <= 7 && holder[1] >= 0) {
                output.add(holder.clone());
            }

            holder[0] = row + 1;
            holder[1] = col;
            if (holder[0] <= 7) {
                output.add(holder.clone());
            }

            holder[0] = row;
            holder[1] = col + 1;
            if (holder[1] <= 7) {
                output.add(holder.clone());
            }

            holder[0] = row;
            holder[1] = col - 1;
            if (holder[1] >= 0) {
                output.add(holder.clone());
            }

            holder[0] = row - 1;
            holder[1] = col - 1;
            if (holder[0] >= 0 && holder[1] >= 0) {
                output.add(holder.clone());
            }

            holder[0] = row - 1;
            holder[1] = col;
            if (holder[0] >= 0) {
                output.add(holder.clone());
            }

            holder[0] = row - 1;
            holder[1] = col + 1;
            if (holder[0] >= 0 && holder[1] <= 7) {
                output.add(holder.clone());
            }
        }

        return output;
    }

    public List<int[]> possGetter() {
        return possiblePlaces;
    }

    public void possSetter(List<int[]> x) {
        this.possiblePlaces = x;
    }

    public void setRow(int x) {
        row = x;
    }

    public void setCol(int y) {
        col = y;
    }

    public boolean getBool() {
        return isWhite;
    }

    public void move(int y, int x, List<int[]> possibles) {
        for (int i = 0; i < possibles.size(); i++) {
            if (possibles.get(i)[0] == y && possibles.get(i)[1] == x) {
                setRow(y);
                setCol(x);
            }
        }
        this.possiblePlaces = getPossiblePlaces();
    }

    public void drawPiece(Graphics g) {
        g.drawImage(img, getCol() * 50, getRow() * 50, 30, 30, null);
    }

    public boolean isValid(int y, int x, List<int[]> possibles) {
        for (int i = 0; i < possibles.size(); i++) {
            if (possibles.get(i)[0] == y && possibles.get(i)[1] == x) {
                return true;
            }
        }
        return false;
    }

}