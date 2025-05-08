package YootProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import javax.swing.ImageIcon;

public class FirstPage extends JFrame {
    private static final int MAX_PLAYER = 3;
    private static final int MAX_PIECE = 4;
    private static final int MAX_BOARD = 3;

    private JRadioButton[] playerNumbtn = new JRadioButton[MAX_PLAYER];
    private JRadioButton[] pieceNumbtn = new JRadioButton[MAX_PIECE];
    private JRadioButton[] boardNumbtn = new JRadioButton[MAX_BOARD];

    private ButtonGroup playerNumcheck = new ButtonGroup();
    private ButtonGroup pieceNumcheck = new ButtonGroup();
    private ButtonGroup boardNumcheck = new ButtonGroup();

    private JButton start = new JButton("게임 시작");

    private PlayConfig playConfig = new PlayConfig();
    private PlayerAdapter playerAdapter = new PlayerAdapter(playConfig);
    private PieceAdapter pieceAdapter = new PieceAdapter(playConfig);
    private BoardAdapter boardAdapter = new BoardAdapter(playConfig); // 새로 추가 가정

    public FirstPage() {
        this.setTitle("게임 설정");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        optionsPanel.add(createOptionPanel("사용자 수", playerNumbtn, playerNumcheck, 2, playerAdapter));
        optionsPanel.add(createOptionPanel("말 개수", pieceNumbtn, pieceNumcheck, 2, pieceAdapter));
        optionsPanel.add(createOptionPanel("게임 판", boardNumbtn, boardNumcheck, 4, boardAdapter));

        JPanel startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;  // 수직 가운데 정렬
        start.setPreferredSize(new Dimension(100, 80));
        startPanel.add(start, gbc);


        mainPanel.add(optionsPanel, BorderLayout.CENTER);
        mainPanel.add(startPanel, BorderLayout.EAST);
        this.add(mainPanel);

        // 기본 선택값
        playerNumbtn[0].setSelected(true);
        pieceNumbtn[0].setSelected(true);
        boardNumbtn[0].setSelected(true);

        start.addActionListener(e -> {
            System.out.printf("Player: %d, Piece: %d, Board: %d%n",
                    playConfig.getPlayerNum(),
                    playConfig.getPieceNum(),
                    playConfig.getBoardShape());

            new PlayGame(playConfig);
            dispose();
        });

        this.setVisible(true);
    }

    private JPanel createOptionPanel(String title, JRadioButton[] btns, ButtonGroup group, int startNum, ActionListener listener) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));

        for (int i = 0; i < btns.length; i++) {
            btns[i] = new JRadioButton(Integer.toString(i + startNum));
            group.add(btns[i]);
            btns[i].addActionListener(listener);
            panel.add(btns[i]);
        }
        return panel;
    }

    public static void main(String[] args) {
        new FirstPage();

    }
}
