package org.cis1200.chess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class ChessBoard extends JPanel {
    private Chess chess;
    private JLabel status;
    private List<Piece> whites;
    private List<Piece> black;
    private Piece chosen;
    private List<int[]> chosenPoss;
    private boolean turn;
    private boolean gameOver;
    private String winner;
    private boolean check;

    public final int BOARD_WIDTH = 400;
    public final int BOARD_HEIGHT = 400;

    public ChessBoard(JLabel statusInit) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setFocusable(true);

        chess = new Chess(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        whites = chess.getWhitePieces();
        black = chess.getBlackPieces();
        gameOver = false;
        winner = "TBD";
        check = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (chosen == null) {
                    Point p = e.getPoint();

                    // updates the model given the coordinates of the mouseclick
                    chosen = chess.choosePiece(p.y / 50, p.x / 50);

                    String chosenColor = chosen.getTeam();

                    if (chosenColor.equals("White") && turn
                            || chosenColor.equals("Black") && !turn) {

                        chosenPoss = chosen.possGetter();

                        int choseRow = -1;
                        int choseCol = -1;
                        int cSize = chosenPoss.size();

                        for (int i = 0; i < cSize; i++) {
                            choseRow = chosenPoss.get(i)[0];
                            choseCol = chosenPoss.get(i)[1];
                            if (chosen.getTeam().equals("White")) {
                                for (int j = 0; j < whites.size(); j++) {
                                    if (whites.get(j).getRow() == choseRow
                                            && whites.get(j).getCol() == choseCol) {
                                        chosenPoss.remove(i);
                                        i--;
                                        cSize--;
                                    }
                                }
                            } else {
                                for (int j = 0; j < black.size(); j++) {
                                    if (black.get(j).getRow() == choseRow
                                            && black.get(j).getCol() == choseCol) {
                                        chosenPoss.remove(i);
                                        i--;
                                        cSize--;
                                    }
                                }
                            }
                        }

                        if (chosen.getPieceType().equals("Pawn")) {

                            choseCol = chosen.getCol();
                            int i = 1;

                            while (i <= chosenPoss.size()) {
                                if (chosen.getTeam().equals("White")) {
                                    choseRow = chosen.getRow() - i;
                                } else {
                                    choseRow = chosen.getRow() + i;
                                }
                                for (int j = 0; j < whites.size(); j++) {
                                    if (whites.get(j).getRow() == choseRow
                                            && whites.get(j).getCol() == choseCol) {
                                        if (i == 1) {
                                            chosenPoss.clear();
                                        } else {
                                            chosenPoss.remove(j);
                                        }
                                    }
                                }
                                for (int j = 0; j < black.size(); j++) {
                                    if (black.get(j).getRow() == choseRow
                                            && black.get(j).getCol() == choseCol) {
                                        if (i == 1) {
                                            chosenPoss.clear();
                                        } else {
                                            chosenPoss.remove(j);
                                        }
                                    }
                                }
                                i++;
                            }
                            int choseCol2 = -1;
                            int[] holder = new int[2];
                            if (chosen.getTeam().equals("White")) {
                                choseRow = chosen.getRow() - 1;
                                choseCol = chosen.getCol() + 1;
                                choseCol2 = chosen.getCol() - 1;
                                for (int j = 0; j < black.size(); j++) {
                                    if (black.get(j).getRow() == choseRow
                                            && black.get(j).getCol() == choseCol) {
                                        holder[0] = choseRow;
                                        holder[1] = choseCol;
                                        chosenPoss.add(holder.clone());
                                    }
                                    if (black.get(j).getRow() == choseRow
                                            && black.get(j).getCol() == choseCol2) {
                                        holder[0] = choseRow;
                                        holder[1] = choseCol2;
                                        chosenPoss.add(holder.clone());
                                    }
                                }
                            } else {
                                choseRow = chosen.getRow() + 1;
                                choseCol = chosen.getCol() + 1;
                                choseCol2 = chosen.getCol() - 1;
                                for (int j = 0; j < whites.size(); j++) {
                                    if (whites.get(j).getRow() == choseRow
                                            && whites.get(j).getCol() == choseCol) {
                                        holder[0] = choseRow;
                                        holder[1] = choseCol;
                                        chosenPoss.add(holder.clone());
                                    }
                                    if (whites.get(j).getRow() == choseRow
                                            && whites.get(j).getCol() == choseCol2) {
                                        holder[0] = choseRow;
                                        holder[1] = choseCol2;
                                        chosenPoss.add(holder.clone());
                                    }
                                }
                            }

                        }

                        if (chosen.getPieceType().equals("Rook")) {
                            chosenPoss = fixRook(chosen);
                        }

                        if (chosen.getPieceType().equals("Bishop")) {
                            chosenPoss = fixBishop(chosen);
                        }
                        if (chosen.getPieceType().equals("Queen")) {
                            chosenPoss = fixQueen(chosen);
                        }
                        if (chosen.getPieceType().equals("King")) {
                            chosenPoss = fixKing(chosen);
                        }

                        chosen.possSetter(chosenPoss);

                        doomsday();
                        repaint(); // repaints the game board
                    } else {
                        chosen = null;
                    }
                } else {
                    Point p = e.getPoint();
                    chosen.move(p.y / 50, p.x / 50, chosenPoss);
                    int rowMoved = p.y / 50;
                    int colMoved = p.x / 50;
                    if (chosen.isValid(rowMoved, colMoved, chosenPoss)) {
                        if (chosen.getTeam().equals("Black")) {
                            for (int j = 0; j < whites.size(); j++) {
                                if (whites.get(j).getRow() == rowMoved
                                        && whites.get(j).getCol() == colMoved) {
                                    whites.remove(j);
                                    j = whites.size();
                                }
                            }
                        } else {
                            for (int j = 0; j < black.size(); j++) {
                                if (black.get(j).getRow() == rowMoved
                                        && black.get(j).getCol() == colMoved) {
                                    black.remove(j);
                                    j = black.size();
                                }
                            }
                        }
                        turn = !turn;
                        if (turn && whiteCheck()) {
                            status.setText("White's in Check'");
                        } else if (turn && !whiteCheck()) {
                            status.setText("White's Turn");
                        } else if (!turn && blackCheck()) {
                            status.setText("Black's in Check");
                        } else {
                            status.setText("Black's Turn");
                        }
                    }
                    repaint();
                    chosen = null;

                }

            }
        });
    }

    public boolean blackLose() {
        boolean output = false;
        for (int j = 0; j < black.size(); j++) {
            if (black.get(j).getPieceType().equals("King")) {
                output = (black.get(j).possGetter().size() == 0 && blackCheck());
            }
        }
        return output;
    }

    public boolean whiteLose() {
        boolean output = false;
        for (int j = 0; j < whites.size(); j++) {
            if (whites.get(j).getPieceType().equals("King")) {
                output = (whites.get(j).possGetter().size() == 0 && whiteCheck());
            }
        }
        return output;
    }

    public void doomsday() {
        if (blackLose() && blackCheck()) {
            this.winner = "White";
            this.gameOver = true;
        }
        if (whiteLose() && whiteCheck()) {
            this.winner = "Black";
            this.gameOver = true;
        }
    }

    public List<int[]> fixKing(Piece r) {
        List<int[]> totalPoss = new LinkedList<>();
        List<int[]> kingPoss = r.possGetter();
        List<int[]> output = new LinkedList<>();
        int rowPoss = 10;
        int colPoss = 10;
        boolean clean = true;
        int[] holder = new int[2];
        if (r.getTeam().equals("White")) {
            for (int i = 0; i < black.size(); i++) {
                totalPoss.addAll(black.get(i).possGetter());
            }
            for (int i = 0; i < kingPoss.size(); i++) {
                rowPoss = kingPoss.get(i)[0];
                colPoss = kingPoss.get(i)[1];
                for (int j = 0; j < totalPoss.size(); j++) {
                    if (totalPoss.get(j)[0] == rowPoss && totalPoss.get(j)[1] == colPoss) {
                        clean = false;
                    }
                }
                if (clean) {
                    holder[0] = rowPoss;
                    holder[1] = colPoss;
                    output.add(holder.clone());
                }
                clean = true;
            }

        } else {
            for (int i = 0; i < whites.size(); i++) {
                totalPoss.addAll(whites.get(i).possGetter());
            }
            for (int i = 0; i < kingPoss.size(); i++) {
                rowPoss = kingPoss.get(i)[0];
                colPoss = kingPoss.get(i)[1];
                for (int j = 0; j < totalPoss.size(); j++) {
                    if (totalPoss.get(j)[0] == rowPoss && totalPoss.get(j)[1] == colPoss) {
                        clean = false;
                    }
                }
                if (clean) {
                    holder[0] = rowPoss;
                    holder[1] = colPoss;
                    output.add(holder.clone());
                }
                clean = true;
            }
        }
        return output;
    }

    public List<int[]> fixer(Piece p) {
        if (p.getPieceType().equals("Rook")) {
            return fixRook(p);
        }
        if (p.getPieceType().equals("Bishop")) {
            return fixBishop(p);
        }
        if (p.getPieceType().equals("Queen")) {
            return fixQueen(p);
        }
        return p.possGetter();
    }

    public boolean whiteCheck() {
        List<int[]> totalPoss = new LinkedList<>();
        for (int i = 0; i < black.size(); i++) {
            fixer(black.get(i));
            totalPoss.addAll(fixer(black.get(i)));
        }
        for (int j = 0; j < whites.size(); j++) {
            if (whites.get(j).getPieceType().equals("King")) {
                for (int i = 0; i < totalPoss.size(); i++) {
                    if (totalPoss.get(i)[0] == whites.get(j).getRow()
                            && totalPoss.get(i)[1] == whites.get(j).getCol()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean blackCheck() {
        List<int[]> totalPoss = new LinkedList<>();
        for (int i = 0; i < whites.size(); i++) {
            totalPoss.addAll(whites.get(i).possGetter());
        }
        for (int j = 0; j < black.size(); j++) {
            if (black.get(j).getPieceType().equals("King")) {
                for (int i = 0; i < totalPoss.size(); i++) {
                    if (totalPoss.get(i)[0] == black.get(j).getRow()
                            && totalPoss.get(i)[1] == black.get(j).getCol()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<int[]> fixRook(Piece r) {
        int rookRow = r.getRow();
        int rookCol = r.getCol();
        int rowPoss = 10;
        int colPoss = 10;
        boolean isClean = true;
        List<int[]> poss = r.possGetter();
        List<int[]> output = new LinkedList<>();
        int[] holder = new int[2];
        int size = poss.size();

        for (int j = 0; j < size; j++) {
            rowPoss = poss.get(j)[0];
            colPoss = poss.get(j)[1];
            if (rookRow == rowPoss) {
                if (colPoss > rookCol) {
                    for (int i = 1; colPoss - i > rookCol; i++) {
                        if (isOccupied(rookRow, colPoss - i)) {
                            isClean = false;
                        }
                    }
                }
                if (colPoss < rookCol) {
                    for (int i = 1; colPoss + i < rookCol; i++) {
                        if (isOccupied(rookRow, colPoss + i)) {
                            isClean = false;
                        }
                    }
                }
            }
            if (rookCol == colPoss) {
                if (rowPoss > rookRow) {
                    for (int i = 1; rowPoss - i > rookRow; i++) {
                        if (isOccupied(rowPoss - i, colPoss)) {
                            isClean = false;
                        }
                    }
                }
                if (rowPoss < rookRow) {
                    for (int i = 1; rowPoss + i < rookRow; i++) {
                        if (isOccupied(rowPoss + i, rookCol)) {
                            isClean = false;
                        }
                    }
                }
            }
            if (isClean) {
                holder[0] = rowPoss;
                holder[1] = colPoss;
                output.add(holder.clone());
            }
            isClean = true;
        }
        r.possSetter(output);
        return output;
    }

    public List<int[]> fixQueen(Piece r) {
        List<int[]> output = new LinkedList<>();
        List<int[]> output2 = new LinkedList<>();
        int[] holder = new int[2];
        int rowPoss = 10;
        int colPoss = 10;
        boolean clean = true;
        Piece testRook = new Piece("Rook", r.getRow(), r.getCol(), r.getBool());
        Piece testBishop = new Piece("Bishop", r.getRow(), r.getCol(), r.getBool());
        output.addAll(fixRook(testRook));
        output.addAll(fixBishop(testBishop));

        for (int j = 0; j < output.size(); j++) {
            rowPoss = output.get(j)[0];
            colPoss = output.get(j)[1];
            if (r.getTeam().equals("White")) {
                for (int i = 0; i < whites.size(); i++) {
                    if (whites.get(i).getRow() == rowPoss && whites.get(i).getCol() == colPoss) {
                        clean = false;
                    }
                }
            } else {
                for (int i = 0; i < black.size(); i++) {
                    if (black.get(i).getRow() == rowPoss && black.get(i).getCol() == colPoss) {
                        clean = false;
                    }
                }
            }
            if (clean) {
                holder[0] = rowPoss;
                holder[1] = colPoss;
                output2.add(holder.clone());
            }
            clean = true;
        }
        return output2;
    }

    public List<int[]> fixBishop(Piece r) {
        int bishopRow = r.getRow();
        int bishopCol = r.getCol();
        int rowPoss = 10;
        int colPoss = 10;
        boolean isClean = true;
        List<int[]> poss = r.possGetter();
        List<int[]> output = new LinkedList<>();
        int[] holder = new int[2];
        int size = poss.size();
        for (int j = 0; j < size; j++) {
            rowPoss = poss.get(j)[0];
            colPoss = poss.get(j)[1];
            if (rowPoss < bishopRow && colPoss < bishopCol) {
                for (int i = 1; rowPoss + i < bishopRow; i++) {
                    if (isOccupied(rowPoss + i, colPoss + i)) {
                        isClean = false;
                    }
                }
            }
            if (rowPoss < bishopRow && colPoss > bishopCol) {
                for (int i = 1; rowPoss + i < bishopRow; i++) {
                    if (isOccupied(rowPoss + i, colPoss - i)) {
                        isClean = false;
                    }
                }
            }
            if (rowPoss > bishopRow && colPoss < bishopCol) {
                for (int i = 1; rowPoss - i > bishopRow; i++) {
                    if (isOccupied(rowPoss - i, colPoss + i)) {
                        isClean = false;
                    }
                }
            }
            if (rowPoss > bishopRow && colPoss > bishopCol) {
                for (int i = 1; rowPoss - i > bishopRow; i++) {
                    if (isOccupied(rowPoss - i, colPoss - i)) {
                        isClean = false;
                    }
                }
            }
            if (isClean) {
                holder[0] = rowPoss;
                holder[1] = colPoss;
                output.add(holder.clone());
            }
            isClean = true;
        }
        return output;
    }

    public void checkIfOver(Piece r) {
    }

    public boolean isOccupied(int row, int col) {
        for (int i = 0; i < whites.size(); i++) {
            if (whites.get(i).getRow() == row && whites.get(i).getCol() == col) {
                return true;
            }
        }

        for (int i = 0; i < black.size(); i++) {
            if (black.get(i).getRow() == row && black.get(i).getCol() == col) {
                return true;
            }
        }

        return false;
    }

    public void reset() {
        chess.reset();
        status.setText("White's Turn");
        turn = true;
        repaint();
        gameOver = false;

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void gameOver() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!gameOver) {
            // Draws board grid
            g.drawLine(0, 0, 400, 0);
            g.drawLine(400, 400, 400, 0);
            g.drawLine(0, 0, 0, 400);
            g.drawLine(400, 400, 0, 400);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 50, 50, 50);
            g.fillRect(0, 150, 50, 50);
            g.fillRect(0, 250, 50, 50);
            g.fillRect(0, 350, 50, 50);
            g.fillRect(50, 0, 50, 50);
            g.fillRect(50, 100, 50, 50);
            g.fillRect(50, 200, 50, 50);
            g.fillRect(50, 300, 50, 50);
            g.fillRect(100, 50, 50, 50);
            g.fillRect(100, 150, 50, 50);
            g.fillRect(100, 250, 50, 50);
            g.fillRect(100, 350, 50, 50);
            g.fillRect(150, 0, 50, 50);
            g.fillRect(150, 100, 50, 50);
            g.fillRect(150, 200, 50, 50);
            g.fillRect(150, 300, 50, 50);
            g.fillRect(200, 50, 50, 50);
            g.fillRect(200, 150, 50, 50);
            g.fillRect(200, 250, 50, 50);
            g.fillRect(200, 350, 50, 50);
            g.fillRect(250, 0, 50, 50);
            g.fillRect(250, 100, 50, 50);
            g.fillRect(250, 200, 50, 50);
            g.fillRect(250, 300, 50, 50);
            g.fillRect(300, 50, 50, 50);
            g.fillRect(300, 150, 50, 50);
            g.fillRect(300, 250, 50, 50);
            g.fillRect(300, 350, 50, 50);
            g.fillRect(350, 0, 50, 50);
            g.fillRect(350, 100, 50, 50);
            g.fillRect(350, 200, 50, 50);
            g.fillRect(350, 300, 50, 50);
            if (chosen != null) {
                g.setColor(Color.RED);
                for (int i = 0; i < chosen.possGetter().size(); i++) {
                    g.fillOval(
                            chosen.possGetter().get(i)[1] * 50, chosen.possGetter().get(i)[0] * 50,
                            30, 30
                    );
                }
            }
            for (int i = 0; i < whites.size(); i++) {
                whites.get(i).drawPiece(g);
            }
            for (int i = 0; i < black.size(); i++) {
                black.get(i).drawPiece(g);
            }
        } else {
            if (winner.equals("White")) {
                g.setColor(Color.BLACK);
                g.drawString("WHITE WINS", 200, 200);
            } else if (winner.equals("Black")) {
                g.setColor(Color.BLACK);
                g.drawString("BLACK WINS", 200, 200);
            } else {
                g.setColor(Color.DARK_GRAY);
                g.drawString("TIE", 200, 200);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}