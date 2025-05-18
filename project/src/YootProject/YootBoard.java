package YootProject;

import java.awt.*;
import java.util.List;
import java.util.Arrays;

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

        panelPan = new JPanel();
        panelPan.setLayout(null);
        panelPan.setBackground(new Color(255, 255, 255));

        switch(this.boardShape){
            case 4:
                line = new JLabel(new ImageIcon("project/img/line.png"));
                panButton = new JButton[3][21];
                int xpos = buttonSizeX * 7;
                int ypos = buttonSizeY * 7;
                double buttonInterval = buttonSizeX * 1.25;

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
                break;
            case 5:
                line = new JLabel(new ImageIcon("project/img/fiveline.png"));
                line.setBounds(30,30,350,350);
                line.setVisible(true);

                panButton = new JButton[6][36];

                buttonSizeX = 30;
                buttonSizeY = buttonSizeX;

                /*0,1,2,3,5 = bigcircle*/
                /*4 = startcircle*/
                /*else = circle*/
                List<Point> pointsPenta = Arrays.asList(
                        new Point(380, 159),  // 0 시계방향 기준 2시 꼭짓점
                        new Point(205, 32),   // 1 12시 방향 꼭짓점
                        new Point(30, 158),   // 2 10시 방향 꼭짓점
                        new Point(99, 365),   // 3 8시 방향 꼭짓점
                        new Point(310, 364),  // 4 출발 지점
                        new Point(204, 215),  // 5 중심점
                        new Point(345, 133),  // 6 0-1 외곽선 반시계 방향
                        new Point(309, 108),  // 7
                        new Point(274, 83),   // 8
                        new Point(240, 57),   // 9
                        new Point(169, 57),   // 10 1-2
                        new Point(135, 82),   // 11
                        new Point(100, 108),  // 12
                        new Point(65, 133),   // 13
                        new Point(44, 200),   // 14 2-3
                        new Point(57, 241),   // 15
                        new Point(71, 282),   // 16
                        new Point(84, 323),   // 17
                        new Point(141, 364),  // 18 3-4
                        new Point(184, 364),  // 19
                        new Point(225, 364),  // 20
                        new Point(267, 364),  // 21
                        new Point(323, 323),  // 22 4-0
                        new Point(338, 282),  // 23
                        new Point(352, 241),  // 24
                        new Point(365, 200),  // 25
                        new Point(321, 178),  // 26 0-5를 잇는 중심선
                        new Point(262, 196),  // 27
                        new Point(205, 92),   // 28 1-5
                        new Point(204, 153),  // 29
                        new Point(88, 177),   // 30 2-5
                        new Point(147, 196),  // 31
                        new Point(133, 315),  // 32 3-5
                        new Point(169, 264),  // 33
                        new Point(274, 315),  // 34 4-5
                        new Point(240, 264)   // 35
                );

                for(int i = 0; i<36; i++){
                    Point x = pointsPenta.get(i);
                    ImageIcon icon;

                    if(i<6){
                        icon = new ImageIcon("project/img/bigcircle.jpg");
                        if(i==0){

                            panButton[1][0] = new JButton(icon);
                            panButton[1][0].setBounds(x.x - buttonSizeX/2, x.y - buttonSizeY/2, buttonSizeX, buttonSizeY);
                            panButton[1][0].setBorderPainted(false);
                            panButton[1][0].setContentAreaFilled(false);
                            panelPan.add(panButton[1][0]);
                        }
                        else if(i==1){

                            panButton[2][0] = new JButton(icon);
                            panButton[2][0].setBounds(x.x - buttonSizeX/2, x.y - buttonSizeY/2, buttonSizeX, buttonSizeY);
                            panButton[2][0].setBorderPainted(false);
                            panButton[2][0].setContentAreaFilled(false);
                            panelPan.add(panButton[2][0]);
                        }
                        else if(i==2){

                            panButton[3][0] = new JButton(icon);
                            panButton[3][0].setBounds(x.x - buttonSizeX/2, x.y - buttonSizeY/2, buttonSizeX, buttonSizeY);
                            panButton[3][0].setBorderPainted(false);
                            panButton[3][0].setContentAreaFilled(false);
                            panelPan.add(panButton[3][0]);
                        }
                        else if(i==3){

                            panButton[0][20] = new JButton(icon);
                            panButton[0][20].setBounds(x.x - buttonSizeX/2, x.y - buttonSizeY/2, buttonSizeX, buttonSizeY);
                            panButton[0][20].setBorderPainted(false);
                            panButton[0][20].setContentAreaFilled(false);
                            panelPan.add(panButton[0][20]);
                        }
                        else if(i==4){

                            panButton[0][25] = new JButton(icon);
                            panButton[0][25].setBounds(x.x - buttonSizeX/2, x.y - buttonSizeY/2, buttonSizeX, buttonSizeY);
                            panButton[0][25].setBorderPainted(false);
                            panButton[0][25].setContentAreaFilled(false);
                            panelPan.add(panButton[0][25]);
                        }
                        else{

                            panButton[5][0] = new JButton(icon);
                            panButton[5][0].setBounds(x.x - buttonSizeX/2, x.y - buttonSizeY/2, buttonSizeX, buttonSizeY);
                            panButton[5][0].setBorderPainted(false);
                            panButton[5][0].setContentAreaFilled(false);
                            panelPan.add(panButton[5][0]);
                        }

                    }

                    else{
                        icon = new ImageIcon("project/img/circle.jpg");
                        for(int k=0;k<6;k++){
                            if(k==0){

                                if(i<26&&i>21) {

                                    panButton[0][i-21] = new JButton(icon);
                                    panButton[0][i-21].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[0][i-21].setBorderPainted(false);
                                    panButton[0][i-21].setContentAreaFilled(false);
                                    panelPan.add(panButton[0][i-21]);
                                }
                                else if(i<10&&i>5){

                                    panButton[0][i] = new JButton(icon);
                                    panButton[0][i].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[0][i].setBorderPainted(false);
                                    panButton[0][i].setContentAreaFilled(false);
                                    panelPan.add(panButton[0][i]);
                                }
                                else if(i>9&&i<14){

                                    panButton[0][i+1] = new JButton(icon);
                                    panButton[0][i+1].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[0][i+1].setBorderPainted(false);
                                    panButton[0][i+1].setContentAreaFilled(false);
                                    panelPan.add(panButton[0][i+1]);
                                }
                                else if(i>13&&i<18){


                                    panButton[0][i+2] = new JButton(icon);
                                    panButton[0][i+2].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[0][i+2].setBorderPainted(false);
                                    panButton[0][i+2].setContentAreaFilled(false);
                                    panelPan.add(panButton[0][i+2]);
                                }
                                else if(i>17&&i<22){

                                    panButton[0][i+3] = new JButton(icon);
                                    panButton[0][i+3].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[0][i+3].setBorderPainted(false);
                                    panButton[0][i+3].setContentAreaFilled(false);
                                    panelPan.add(panButton[0][i+3]);
                                }
                                else{
                                    continue;
                                }
                            }
                            else if(k==1){
                                if(i==26||i==27){

                                    panButton[k][i-25] = new JButton(icon);
                                    panButton[k][i-25].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[k][i-25].setBorderPainted(false);
                                    panButton[k][i-25].setContentAreaFilled(false);
                                    panelPan.add(panButton[k][i-25]);
                                }
                            }
                            else if(k==2){
                                if(i==28||i==29){

                                    panButton[k][i-27] = new JButton(icon);
                                    panButton[k][i-27].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[k][i-27].setBorderPainted(false);
                                    panButton[k][i-27].setContentAreaFilled(false);
                                    panelPan.add(panButton[k][i-27]);
                                }
                            }
                            else if(k==3){
                                if(i==30||i==31){

                                    panButton[k][i-29] = new JButton(icon);
                                    panButton[k][i-29].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[k][i-29].setBorderPainted(false);
                                    panButton[k][i-29].setContentAreaFilled(false);
                                    panelPan.add(panButton[k][i-29]);
                                }
                            }
                            else if(k==4){
                                if(i==33){

                                    panButton[k][i-32] = new JButton(icon);
                                    panButton[k][i-32].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[k][i-32].setBorderPainted(false);
                                    panButton[k][i-32].setContentAreaFilled(false);
                                    panelPan.add(panButton[k][i-32]);
                                }
                                if(i==32){

                                    panButton[k][i-30] = new JButton(icon);
                                    panButton[k][i-30].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[k][i-30].setBorderPainted(false);
                                    panButton[k][i-30].setContentAreaFilled(false);
                                    panelPan.add(panButton[k][i-30]);
                                }
                            }
                            else {
                                if(i==35){

                                    panButton[k][i-34] = new JButton(icon);
                                    panButton[k][i-34].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[k][i-34].setBorderPainted(false);
                                    panButton[k][i-34].setContentAreaFilled(false);
                                    panelPan.add(panButton[k][i-34]);
                                }
                                if(i==34){

                                    panButton[k][i-32] = new JButton(icon);
                                    panButton[k][i-32].setBounds(x.x - buttonSizeX / 2, x.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                                    panButton[k][i-32].setBorderPainted(false);
                                    panButton[k][i-32].setContentAreaFilled(false);
                                    panelPan.add(panButton[k][i-32]);
                                }

                            }
                        }

                    }


                }
                panelPan.add(line);
                break;

            case 6:
                line = new JLabel(new ImageIcon("project/img/sixline.png"));
                line.setBounds(15, 00, 400, 400);
                line.setVisible(true);

                panButton = new JButton[7][43]; // routeIndex 0: 외곽, 1~6: 대각선 및 중심 경로

                buttonSizeX = 30;
                buttonSizeY = buttonSizeX;

                List<Point> pointsHexa = Arrays.asList(
                        // 외곽 경로 (route 0)
                        new Point(420, 200), new Point(400, 165), new Point(380, 131), new Point(360, 96), new Point(340, 61),
                        new Point(320, 27), new Point(280, 27), new Point(240, 27), new Point(200, 27), new Point(160, 27),
                        new Point(120, 27), new Point(100, 61), new Point(80, 96), new Point(60, 131), new Point(40, 165),
                        new Point(20, 200), new Point(40, 235), new Point(60, 269), new Point(80, 304), new Point(100, 339),
                        new Point(120, 373), new Point(160, 373), new Point(200, 373), new Point(240, 373), new Point(280, 373),
                        new Point(320, 373), new Point(340, 339), new Point(360, 304), new Point(380, 269), new Point(400, 235),

                        // 내부 대각선 경로 및 중심 (1~6번 라인)
                        new Point(320, 27), new Point(287, 84), new Point(253, 143),         // route 1
                        new Point(120, 27), new Point(153, 84), new Point(187, 143),         // route 2
                        new Point(20, 200), new Point(87, 200), new Point(153, 200),         // route 3
                        new Point(120, 373), new Point(153, 316), new Point(187, 257), new Point(220, 200),       // route 4
                        new Point(320, 373), new Point(287, 316), new Point(253, 257),       // route 5
                        new Point(420, 200), new Point(353, 200), new Point(287, 200)       // route 6
                );

                // 외곽 route 0: 0~29까지 총 30칸
                for (int i = 0; i < 30; i++) {
                    Point pt = pointsHexa.get(i);
                    ImageIcon icon;
                    if(i==0){
                        icon = new ImageIcon("project/img/startcircle.jpg");
                    }
                    else if(i == 5 || i == 10 || i == 20 || i == 25){
                        icon = new ImageIcon("project/img/bigcircle.jpg");
                    }
                    else icon = new ImageIcon("project/img/circle.jpg");

                    panButton[0][i] = new JButton(icon);
                    panButton[0][i].setBounds(pt.x - buttonSizeX / 2, pt.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                    panButton[0][i].setBorderPainted(false);
                    panButton[0][i].setContentAreaFilled(false);
                    panelPan.add(panButton[0][i]);
                }

                // route 1~6 내부 라인 구성
                int[][] routeOffsets = {
                        {30, 3}, // route 1
                        {33, 3}, // route 2
                        {36, 3}, // route 3
                        {39, 4}, // route 4
                        {43, 3}, // route 5
                        {46, 3}  // route 6
                };

                for (int r = 1; r <= 6; r++) {
                    int offset = routeOffsets[r - 1][0];
                    int len = routeOffsets[r - 1][1];
                    for (int i = 0; i < len; i++) {
                        Point pt = pointsHexa.get(offset + i);

                        ImageIcon icon = (i == len - 1) ? new ImageIcon("project/img/bigcircle.jpg") : new ImageIcon("project/img/circle.jpg");

                        panButton[r][i] = new JButton(icon);
                        panButton[r][i].setBounds(pt.x - buttonSizeX / 2, pt.y - buttonSizeY / 2, buttonSizeX, buttonSizeY);
                        panButton[r][i].setBorderPainted(false);
                        panButton[r][i].setContentAreaFilled(false);
                        panelPan.add(panButton[r][i]);
                    }
                }


                panelPan.add(line);
                break;
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
    public void updateBoard(List<Player> players) {
        // 1. Reset all buttons (remove piece icons)
        for (int r = 0; r < panButton.length; r++) {
            for (int p = 0; p < panButton[r].length; p++) {
                if (panButton[r][p] != null) {
                    panButton[r][p].setIcon(null); // Or set to empty circle
                }
            }
        }
        // 2. For each piece of each player, draw it on the correct button
        for (Player player : players) {
            for (Piece piece : player.getPieces()) {
                int route = piece.getRouteIndex();
                int pos = piece.getPositionIndex();
                if (route >= 0 && route < panButton.length
                        && pos >= 0 && pos < panButton[route].length
                        && panButton[route][pos] != null) {
                    // Set player icon, or use color/image per player
                    panButton[route][pos].setIcon(new ImageIcon("project/img/piece_player" + player.getId() + ".png"));
                }
            }
        }
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