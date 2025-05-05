package YootProject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Player {
    private final int id;
    private final List<Piece> pieces;
    private int score;  // 말이 도착했을 때 얻는 점수
    private int standbyPieces;  // 아직 판 위에 올라가지 않은 말 수

    public Player(int id, int pieceNum) {
        this.id = id;
        this.pieces = new ArrayList<>();
        this.score = 0;
        this.standbyPieces = pieceNum;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public int getStandbyPieceCount() {
        return standbyPieces;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    // 말 생성
    public boolean createPiece() {
        if (standbyPieces > 0) {
            pieces.add(new Piece());
            standbyPieces--;
            return true;
        }
        return false;
    }

    // 말이 있는 위치에 업혀있는 말 수 확인
    public int getStackCountAt(int x, int y) {
        for (Piece piece : pieces) {
            if (piece.isAt(x, y)) {
                return piece.getPoint(); // 업힌 말 수
            }
        }
        return 0;
    }

    // 말 이동
    public boolean movePieceAt(int x, int y, int move) {
        for (Piece piece : pieces) {
            if (piece.isAt(x, y)) {
                piece.move(move); // 내부에서 좌표 이동
                return true;
            }
        }
        return false;
    }

    // 말이 판에서 나왔는지 확인하고 점수 처리
    public boolean checkAndHandleArrival() {
        Iterator<Piece> it = pieces.iterator();
        while (it.hasNext()) {
            Piece piece = it.next();
            if (piece.hasArrived()) {
                score += piece.getPoint();
                it.remove();
                return true;
            }
        }
        return false;
    }

    // 해당 위치에 내 말이 있는지 확인
    public int findPieceIndexAt(int x, int y) {
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).isAt(x, y)) {
                return i;
            }
        }
        return -1;
    }

    // 업기: 같은 위치에 있는 내 말들을 병합
    public boolean checkAndMergeStack() {
        for (int i = 0; i < pieces.size(); i++) {
            for (int j = i + 1; j < pieces.size(); j++) {
                Piece a = pieces.get(i);
                Piece b = pieces.get(j);
                if (a.isAtSamePosition(b)) {
                    a.addPoint(b.getPoint());
                    pieces.remove(j);
                    return true;
                }
            }
        }
        return false;
    }

    // 상대 말 잡기
    public boolean captureOpponentPiece(int x, int y) {
        Iterator<Piece> it = pieces.iterator();
        while (it.hasNext()) {
            Piece piece = it.next();
            if (piece.isAt(x, y)) {
                standbyPieces += piece.getPoint();
                it.remove();
                return true;
            }
        }
        return false;
    }

    public String getStatusText() {
        return "<남은 말: " + standbyPieces + " 점수: " + score + ">";
    }
}