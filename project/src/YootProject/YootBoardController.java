    package YootProject;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionListener;
    import java.util.List;
    import java.util.ArrayList;


    public class YootBoardController {
        private YootBoard board;
        private JButton pieceBtn;
        private PlayGame game;
        private List<JButton> pieceButtons = new ArrayList<>();
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

        public void updateBoard(List<Player> players, ActionListener listener) {
            String[] pieceImages = {
                    "project/img/yellow1.jpg", "project/img/blue1.jpg", "project/img/green1.jpg", "project/img/red1.jpg",
                    "project/img/yellow2.jpg", "project/img/blue2.jpg", "project/img/green2.jpg", "project/img/red2.jpg",
                    "project/img/yellow3.jpg", "project/img/blue3.jpg", "project/img/green3.jpg", "project/img/red3.jpg",
                    "project/img/yellow4.jpg", "project/img/blue4.jpg", "project/img/green4.jpg", "project/img/red4.jpg",
                    "project/img/yellow5.jpg", "project/img/blue5.jpg", "project/img/green5.jpg", "project/img/red5.jpg",
                    "project/img/bigyellow1.jpg", "project/img/bigblue1.jpg", "project/img/biggreen1.jpg", "project/img/bigred1.jpg",
                    "project/img/bigyellow2.jpg", "project/img/bigblue2.jpg", "project/img/biggreen2.jpg", "project/img/bigred2.jpg",
                    "project/img/bigyellow3.jpg", "project/img/bigblue3.jpg", "project/img/biggreen3.jpg", "project/img/bigred3.jpg",
                    "project/img/bigyellow4.jpg", "project/img/bigblue4.jpg", "project/img/biggreen4.jpg", "project/img/bigred4.jpg",
                    "project/img/bigyellow5.jpg", "project/img/bigblue5.jpg", "project/img/biggreen5.jpg", "project/img/bigred5.jpg"
            };

            JButton[][] panButton = board.getPanButton();
            JPanel panel = board.getPanelPan();

            if (panButton == null || panel == null) return;

            // 1. 기존 말 JButton 제거 (이름이 "piece_"로 시작하는 것만)
            for (Component c : panel.getComponents()) {
                if (c instanceof JButton && c.getName() != null && c.getName().startsWith("piece_")) {
                    panel.remove(c);
                }
            }

            // 2. 모든 플레이어의 말 그리기
            for (Player player : players) {
                int playerId = player.getId();

                for (Piece piece : player.getPieces()) {
                    if (piece.getPositionIndex() < 0 || piece.getRouteIndex() < 0) continue;

                    int route = piece.getRouteIndex();
                    int pos = piece.getPositionIndex();
                    if (route >= panButton.length || pos >= panButton[route].length) continue;

                    int count = player.getStackCountAt(route, pos); // 스택 수
                    count = Math.max(1, Math.min(count, 5)); // 안정성

                    JButton btn = panButton[route][pos];
                    if (btn == null) continue;

                    int imageIndex = isBigCirclePosition(route, pos)
                            ? 20 + (count - 1) * 4 + (playerId - 1)
                            : (count - 1) * 4 + (playerId - 1);
                    String imagePath = pieceImages[imageIndex];

                    JButton pieceBtn = new JButton(new ImageIcon(imagePath));
                    pieceBtn.setBounds(btn.getX(), btn.getY(), btn.getWidth(), btn.getHeight());
                    pieceBtn.setActionCommand("MOVE_" + route + "_" + pos);
                    pieceBtn.setBorderPainted(false);
                    pieceBtn.setContentAreaFilled(false);
                    pieceBtn.setName("piece_" + playerId);
                    pieceBtn.addActionListener(listener);
                    panel.add(pieceBtn);
                    panel.setComponentZOrder(pieceBtn, 0);

                    System.out.println("말 추가됨: Player " + playerId + " → MOVE_" + route + "_" + pos + ", count=" + count);
                }
            }
            JLabel[] playerLabels = board.getPlayerInfoLabels();

            for (Player p : players) {
                int pid = p.getId(); // 1-based
                String status = "P" + pid + " : 남은 말 " + p.getStandbyPieceCount() + "개, 점수 " + p.getScore();
                playerLabels[pid - 1].setText(status);
            }
            panel.revalidate();
            panel.repaint();

        }
        private boolean isBigCirclePosition(int route, int pos) {
            return (route == 0 && (pos == 5 || pos == 10 || pos == 15 || pos == 20)) || (route == 2 && pos == 3);
        }

        public JButton getpieceBtn(){
            return pieceBtn;
        }

    }



