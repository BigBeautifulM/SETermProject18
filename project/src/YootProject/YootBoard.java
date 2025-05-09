package YootProject;

import java.awt.*;


import javax.swing.*;

public class YootBoard extends JFrame {
    private JPanel panelPan;
    JButton[][] panButton;

    private int playerNum;
    private int pieceNum;
    private int boardShape;
    private JLabel turnLabel;
    private int windowSizeX = 1000;
    private int windowSizeY = 700;
    private int buttonSizeX = windowSizeX / 20;
    private int buttonSizeY = buttonSizeX;
    private JPanel yootStackPanel;
    private JButton randomButton = new JButton("랜덤");
    private JLabel randomResult = new JLabel("결과: ");
    private JButton[] yootType = new JButton[6];
    private JLabel[] playerInfo;
    private JButton[] moveOption = new JButton[2];
    JLabel line;

    public YootBoard(int playerNum, int pieceNum, int boardShape) {


        this.playerNum = playerNum;
        this.pieceNum = pieceNum;
        this.boardShape = boardShape;

        Toolkit tools = Toolkit.getDefaultToolkit();
        Image mouseimg = tools.getImage("project/img/cursor.png");

        Cursor newcursor = tools.createCustomCursor(mouseimg, new Point(0, 0), "LOL");
        setCursor(newcursor);

        line = new JLabel(new ImageIcon("project/img/line.png"));

        panelPan = new JPanel();
        panButton = new JButton[3][21];
        int xpos = buttonSizeX * 7;
        int ypos = buttonSizeY * 7;
        double buttonInterval = buttonSizeX * 1.25;
        panelPan.setLayout(null);
        panelPan.setBackground(new Color(255, 255, 255));

        line.setSize(330, 332);
        line.setLocation(52, 50);

        for (int i = 1; i < 21; i++) {
            if (i < 6) {
                ypos -= buttonInterval;
            } else if (i < 11) {
                xpos -= buttonInterval;
            } else if (i < 16) {
                ypos += buttonInterval;
            } else {
                xpos += buttonInterval;
            }

            if (i == 20 || i == 15) {
                panButton[0][i] = new JButton(new ImageIcon("project/img/bigcircle.jpg"));
            } else if (i == 5) {
                panButton[1][0] = new JButton(new ImageIcon("project/img/bigcircle.jpg"));
            }
            else if(i==10){
                panButton[2][0] = new JButton(new ImageIcon("project/img/bigcircle.jpg"));
            }else {
                panButton[0][i] = new JButton(new ImageIcon("project/img/circle.jpg"));
            }

                if(i<5) {
                    panButton[0][i] = createBoardBtn(panButton[0][i], xpos, ypos, buttonSizeX, buttonSizeY, false);
                    panelPan.add(panButton[0][i]);
                }
                else if(i==5){
                    panButton[1][0] = createBoardBtn(panButton[1][0], xpos, ypos, buttonSizeX, buttonSizeY, false);
                    panelPan.add(panButton[1][0]);
                }
                else if(i<10){
                    panButton[0][i] = createBoardBtn(panButton[0][i], xpos, ypos, buttonSizeX, buttonSizeY, false);
                    panelPan.add(panButton[0][i]);
                }
                else if(i==10){
                    panButton[2][0] = createBoardBtn(panButton[2][0], xpos, ypos, buttonSizeX, buttonSizeY, false);
                    panelPan.add(panButton[2][0]);
                }
                else{
                    panButton[0][i] = createBoardBtn(panButton[0][i], xpos, ypos, buttonSizeX, buttonSizeY, false);
                    panelPan.add(panButton[0][i]);
                }


        }

        ypos = buttonSizeY - 10;
        xpos = buttonSizeX * 7 - 10;
        int p;
        for (p = 0; p < 6; p++) {
            if (p == 3) {
                xpos -= buttonSizeX;
                ypos += buttonSizeY;
            } else {
                if (p == 0) {
                    xpos -= buttonSizeX;
                    ypos += buttonSizeY;
                } else {
                    panButton[1][p] = new JButton(new ImageIcon("project/img/circle.jpg"));
                    panButton[1][p] = createBoardBtn(panButton[1][p], xpos, ypos, buttonSizeX, buttonSizeY, false);
                    xpos -= buttonSizeX;
                    ypos += buttonSizeY;
                    panelPan.add(panButton[1][p]);
                    panelPan.add(line);
                }
            }
        }

        xpos = buttonSizeX - 10;
        ypos = buttonSizeY - 10;
        for (p = 0; p < 6; p++) {
            if (p == 0) {
                xpos += buttonSizeX;
                ypos += buttonSizeY;
            } else {
                if (p == 3) {
                    panButton[2][p] = new JButton(new ImageIcon("project/img/bigcircle.jpg"));

                } else {
                    panButton[2][p] = new JButton(new ImageIcon("project/img/circle.jpg"));
                }
                panButton[2][p] = createBoardBtn(panButton[2][p], xpos, ypos, buttonSizeX, buttonSizeY, false);
                xpos += buttonSizeX;
                ypos += buttonSizeY;
                panelPan.add(panButton[2][p]);
                panelPan.add(line);
            }
        }

        this.add(panelPan);

        //현재 차례 정보 표시 - Dull
        turnLabel = new JLabel("현재 차례: P1");
        turnLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        turnLabel.setBounds(500,20,100,30);
        turnLabel.setOpaque(true);
        turnLabel.setBackground(Color.LIGHT_GRAY);
        turnLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelPan.add(turnLabel);

        //각 플레이어 남은 말 정보 표시 - Dull
        JPanel statusBox = new JPanel();
        statusBox.setLayout(null);
        statusBox.setBounds(500, 70, 400, 300);
        statusBox.setBackground(Color.GRAY);
        statusBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelPan.add(statusBox);

        this.playerInfo = new JLabel[this.playerNum];
        int boxHeight = 40;
        for (int i = 0; i<this.playerNum; i++){
            playerInfo[i] = new JLabel("P" + (i+1) + " : 남은 말 " + this.pieceNum + "개");
            playerInfo[i].setOpaque(true);
            playerInfo[i].setBackground(Color.LIGHT_GRAY);
            playerInfo[i].setForeground(Color.BLACK);
            playerInfo[i].setFont(new Font("Dialog", Font.PLAIN, 14));
            playerInfo[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            playerInfo[i].setBounds(50, i*(boxHeight+20) + 40, 300, boxHeight);
            playerInfo[i].setHorizontalAlignment(SwingConstants.CENTER);
            statusBox.add(playerInfo[i]);
        }

        // 레이아웃 정렬을 위한 그리드 레이아웃 설정
        JPanel lowGrid = new JPanel();
        lowGrid.setLayout(new GridLayout(2,2,10,10));
        lowGrid.setBounds(50,400,900,200);
        panelPan.add(lowGrid);

        //윷 버튼 레이아웃
        JPanel yootTypeFlow = new JPanel();
        yootTypeFlow.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        lowGrid.add(yootTypeFlow);

       //디버그 윷 선택
        for (int i = 0;  i<6; i++){
            yootType[i] = new JButton();
            yootType[i].setOpaque(true);
            yootType[i].setBackground(Color.LIGHT_GRAY);
            yootType[i].setForeground(Color.BLACK);
        }
        yootType[0].setText("백도");
        yootType[1].setText("도");
        yootType[2].setText("개");
        yootType[3].setText("걸");
        yootType[4].setText("윷");
        yootType[5].setText("모");
        for (int i = 0; i<6; i++){
            yootType[i].setPreferredSize(new Dimension(60,60));
            yootTypeFlow.add(yootType[i]);
        }

        //이동 옵션 레이아웃
        JPanel moveOptionFlow = new JPanel();
        moveOptionFlow.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        lowGrid.add(moveOptionFlow);


        for (int i = 0; i<2; i++){
            moveOption[i] = new JButton();
            moveOption[i].setOpaque(true);
            moveOption[i].setBackground(Color.LIGHT_GRAY);
            moveOption[i].setForeground(Color.BLACK);
        }
        moveOption[0].setText("내보내기");



        moveOption[1].setText("판에서 선택");
        for (int i = 0; i<2; i++){
            moveOption[i].setPreferredSize(new Dimension(160,80));
            moveOptionFlow.add(moveOption[i]);
        }

        //랜덤 버튼 및 결과 레이아웃
        JPanel randomPanel = new JPanel();
        randomPanel.setLayout(null);
        lowGrid.add(randomPanel);


        randomButton.setOpaque(true);
        randomButton.setBackground(Color.LIGHT_GRAY);
        randomButton.setForeground(Color.BLACK);
        randomButton.setBounds(10,10,80,80);
        randomPanel.add(randomButton);


        randomResult.setHorizontalAlignment(SwingConstants.CENTER);
        randomResult.setOpaque(true);
        randomResult.setBackground(Color.LIGHT_GRAY);
        randomResult.setForeground(Color.BLACK);
        randomResult.setBounds(100,10,340,80);
        randomPanel.add(randomResult);

        //윷스택 라벨
        JPanel yootStackwrapper = new JPanel();
        yootStackwrapper.setLayout(null);
        yootStackwrapper.setBounds(0, 0, 450, 100); // 적절히 수정 가능
        lowGrid.add(yootStackwrapper);

        yootStackPanel = new JPanel();
        yootStackPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 20));
        yootStackPanel.setBounds(10, 10, 430, 80);
        yootStackPanel.setBackground(Color.LIGHT_GRAY);
        yootStackwrapper.add(yootStackPanel);

        this.add(panelPan);
        this.setTitle("Yoot Board");
        this.setSize(windowSizeX, windowSizeY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    JButton createBoardBtn(JButton btn, int x, int y, int width, int depth, boolean tf) {
        btn.setSize(width, depth);
        btn.setBorderPainted(tf);
        btn.setContentAreaFilled(tf);
        btn.setLocation(x, y);
        return btn;
    }
    public JPanel getYootStackPanel() {
        return yootStackPanel;
    }
    public void updateBoard(Player player){

    }
    public void showWinner(String winner){

    }
    public void showThrowResult(String result) {
        if (randomResult != null) {
            randomResult.setText("결과: " + result);
            randomResult.repaint();
            randomResult.revalidate();
        } else {
            System.out.println("⚠ randomResult가 null입니다!");
        }
    }
    public JButton getNewPieceButton() {
        return moveOption[0]; // "내보내기" 버튼
    }
    public JButton getSelectOnBoardButton() {
        return moveOption[1]; // moveOption[1] = "판에서 선택" 버튼
    }
    public JButton[][] getPanButton() {
        return panButton;
    }
    public JPanel getPanelPan() {
        return panelPan;
    }

    public JButton getRandomButton() {
        return randomButton;
    }


    public JButton[] getYootType() {
        return yootType;
    }

    public JLabel[] getPlayerInfoLabels() {
        return playerInfo;
    }
    public JLabel getTurnLabel() {
        return turnLabel;
    }
}

