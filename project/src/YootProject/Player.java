package YootProject;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    private int id;
    private ArrayList<Piece> pieces;
    private int point;
    private int pieceNum; // 아직 윷판에 안나오고 대기중인 말 수

    public Player(int id, int pieceNum) {
        this.id = id;
        this.pieces = new ArrayList<Piece>();
        this.point = 0;
        this.pieceNum = pieceNum;
    }

    public ArrayList<Piece> getPieces() {
        return this.pieces;
    }

    public int getId() {
        return this.id;
    }

    public int getPoint() {
        return this.point;
    }

    public int getPieceNum() {
        return this.pieceNum;
    }

    public int getPieceUpdaNum(int x, int y) {
        int a = 0;
        if (x == 0 && y == 0) {
            x = 0;
            y = 20;
        } else if (x == 0 && y == -1) {
            x = 0;
            y = 19;
        } else if (x == 1 && y == 3) {
            x = 2;
            y = 3;
        } else if (y > 20) {
            return 0;
        }
        for (Piece piece : pieces) {
            if (piece.getX() == x && piece.getY() == y) {
                a = piece.getPoint();
                break;
            }
        }
        return a;
    }

    public int createPiece() {
        if (pieceNum > 0) {
            pieces.add(new Piece()); // 판 위에 말 올림
            pieceNum--;
            return 1; // 말 생성 성공
        }
        return 0; // 말 생성 실패
    }

    public String playerPiece() {
        return "<남은 말:" + pieceNum + " 포인트:" + point + ">";
    }

    public int move(int x, int y, int active) {
        if (pieces.size() != 0) {
            for (Piece piece : pieces) {
                if (piece.getX() == x && piece.getY() == y) {
                    piece.setPos(active);
                }
            }
            return checkUpda();
        }
        return 0;
    }

    public int checkCatch(int positionx, int positiony) {
        Iterator<Piece> it = pieces.iterator();
        while (it.hasNext()) {
            Piece piece = it.next();
            if (piece.getX() == positionx && piece.getY() == positiony) {
                pieceNum += piece.getPoint();
                it.remove();  // 안전하게 삭제
                return 1;
            }
        }
        return 0;
    }

    public int checkEnable(int posX, int posY) {
        int i = 0;
        for (Piece piece : pieces) {
            if (piece.getX() == posX && piece.getY() == posY) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int checkPiecein() {
        for (Iterator<Piece> it = pieces.iterator(); it.hasNext();) {
            Piece piece = it.next();
            if (piece.getY() > 20) {
                point += piece.getPoint();
                it.remove();
                return 1;
            }
        }
        return 0;
    }

    public int checkUpda() {
        for (int p = 0; p < pieces.size(); p++) {
            for (int q = p + 1; q < pieces.size(); q++) {
                if (pieces.get(p).getX() == pieces.get(q).getX() &&
                        pieces.get(p).getY() == pieces.get(q).getY()) {

                    // ✅ 누적 방식으로 point 합산
                    pieces.get(p).setPoint(pieces.get(p).getPoint() + pieces.get(q).getPoint());
                    pieces.remove(q);
                    return 1;
                }
            }
        }
        return 0;
    }
}