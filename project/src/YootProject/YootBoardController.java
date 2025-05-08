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
                "project/img/red5.jpg"
        };
        System.out.println(playerId);
        String imagePath = pieceImages[playerId-1];
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

            int route = piece.getRouteIndex();
            int pos = piece.getPositionIndex();

            if (route >= panButton.length || pos >= panButton[route].length) continue;

            JButton btn = panButton[route][pos];
            if (btn == null) continue;

            // 말 이미지 라벨 생성
            JLabel label = new JLabel(new ImageIcon(imagePath));
            label.setName("piece_" + count++);
            label.setBounds(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight());
            panel.add(label);
            panel.setComponentZOrder(label, 0);
        }

        panel.revalidate();
        panel.repaint();
    }
}



