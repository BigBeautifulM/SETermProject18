package YootProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.image.BufferedImage;

public class YootBoardController {
    private YootBoard board;

    public YootBoardController(YootBoard board) {
        this.board = board;
    }
    public void updateYootStack(List<Integer> extraTurnList, ActionListener listener) {//윷 스택 업데이트
        JPanel panel = board.getYootStackPanel();
        panel.removeAll();

        for (int value : extraTurnList) {
            String text = Yoot.getResultString(value);
            JButton btn = new JButton(text);
            btn.setPreferredSize(new Dimension(60, 40));
            btn.setActionCommand("STACK_" + value);
            btn.addActionListener(listener);
            panel.add(btn);
        }

        panel.revalidate();
        panel.repaint();
    }

    public void updateBoard(Player player) {
        int playerId = player.getId();
        String[] pieceImages = {
                "project/img/yellow1.jpg",
                "project/img/blue1.jpg",
                "project/img/green1.jpg",
                "project/img/red1.jpg",
                "project/img/yellow2.jpg",
                "project/img/blue2.jpg",
                "project/img/green2.jpg",
                "project/img/red2.jpg",
                "project/img/yellow3.jpg",
                "project/img/blue3.jpg",
                "project/img/green3.jpg",
                "project/img/red3.jpg",
                "project/img/yellow4.jpg",
                "project/img/blue4.jpg",
                "project/img/green4.jpg",
                "project/img/red4.jpg",
                "project/img/yellow5.jpg",
                "project/img/blue5.jpg",
                "project/img/green5.jpg",
                "project/img/red5.jpg",
                "project/img/bigyellow1.jpg",
                "project/img/bigblue1.jpg",
                "project/img/biggreen1.jpg",
                "project/img/bigred1.jpg",
                "project/img/bigyellow2.jpg",
                "project/img/bigblue2.jpg",
                "project/img/biggreen2.jpg",
                "project/img/bigred2.jpg",
                "project/img/bigyellow3.jpg",
                "project/img/bigblue3.jpg",
                "project/img/biggreen3.jpg",
                "project/img/bigred3.jpg",
                "project/img/bigyellow4.jpg",
                "project/img/bigblue4.jpg",
                "project/img/biggreen4.jpg",
                "project/img/bigred4.jpg",
                "project/img/bigyellow5.jpg",
                "project/img/bigblue5.jpg",
                "project/img/biggreen5.jpg",
                "project/img/bigred5.jpg"
        };
        System.out.println(playerId);
        String imagePath;


        JButton[][] panButton = board.getPanButton();
        JPanel panel = board.getPanelPan(); // YootBoard에 getPanelPan() 정의 필요

        if (panButton == null || panel == null) return;

        // 1. 기존 말 JLabel 제거 (이름이 "piece_"로 시작하는 것만)
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel && c.getName() != null && c.getName().startsWith("piece_")) {
                panel.remove(c);
            }
        }

        // 2. 말 위치에 말 이미지 JLabel 추가
        int count = 0;
        for (Piece piece : player.getPieces()) {
            if (piece.getPositionIndex() < 0 || piece.getRouteIndex() < 0) continue;
System.out.println("1단계 통과");
            int route = piece.getRouteIndex();
            int pos = piece.getPositionIndex();

            if (route >= panButton.length || pos >= panButton[route].length) continue;
            System.out.println("2단계 통과");
            JButton btn = panButton[route][pos];
            if (btn == null) continue;
            System.out.println("3단계 통과");
            if (isBigCirclePosition(route, pos)) {
                imagePath = pieceImages[playerId+20];
                System.out.println("큰이미지 생성");// 특별 이미지 경로
            }
            else{
                imagePath = pieceImages[playerId-1];

            }
            // 말 이미지 라벨 생성
            JLabel label = new JLabel(new ImageIcon(imagePath));
            label.setName("piece_" + count++);
            label.setBounds(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight());
            panel.add(label);
            panel.setComponentZOrder(label, 0);
            System.out.println(route+ pos);

        }

        panel.revalidate();
        panel.repaint();
    }
    private boolean isBigCirclePosition(int route, int pos) {
        return (route == 0 && (pos == 5 || pos == 10 || pos == 15 || pos == 20)) || (route == 2 && pos == 3);
    }
}



