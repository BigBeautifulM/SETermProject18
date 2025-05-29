package YootProjectjavafx;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import YootProject.Yoot;
import YootProject.Player;
import YootProject.Piece;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;


public class YootBoardfxController {
    private YootBoardfx board;
    private Button pieceBtn;
    private PlayGamefx game;
    private List<Button> pieceButtons = new ArrayList<>();
    private int i=0;

    @FXML
    private AnchorPane boardPane;

    @FXML
    private Label playerInfo1;
    @FXML
    private Label playerInfo2;
    @FXML
    private Label playerInfo3;
    @FXML
    private Label playerInfo4;
    @FXML
    private Label turnLabel;

    @FXML
    private FlowPane yootStackPanel;

    @FXML
    private Label randomResult;
    @FXML
    private Button randomButton;

    @FXML
    private Button yootType0;
    @FXML
    private Button yootType1;
    @FXML
    private Button yootType2;
    @FXML
    private Button yootType3;
    @FXML
    private Button yootType4;
    @FXML
    private Button yootType5;
    private Button[] yootType;

    @FXML
    private Button moveOption0;
    @FXML
    private Button moveOption1;
    private Button[] moveOption;


    private Label line = new Label();
    private Button[][] panButton;

    private int windowSizeX = 1000;
    private int windowSizeY = 700;
    private int buttonSizeX = windowSizeX / 20;
    private int buttonSizeY = buttonSizeX;

    private Label[] labels;

    public YootBoardfxController() {}
    public void updateYootStack(List<Integer> extraTurnList, EventHandler<ActionEvent> listner) {//윷 스택 업데이트
        FlowPane panel = this.getYootStackPanel();
        panel.getChildren().clear();

        for (int value : extraTurnList) {
            String text = Yoot.getResultString(value);
            Button btn = new Button(text);

            btn.setPrefWidth(60);
            btn.setPrefHeight(40);
            btn.setOnAction(listner);
            btn.setId("STACK_" + value);

            panel.getChildren().add(btn);
        }
    }

    public void setBoard(YootBoardfx board) {
        this.board = board;
    }

    public void initBoard(int playerNum, int pieceNum, int boardNum){
        Image image;
        switch(boardNum){
            case 4:
                image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/line.png"));
                line.setGraphic(new ImageView(image));
                line.setLayoutX(52);
                line.setLayoutY(50);
                boardPane.getChildren().add(line);

                panButton = new Button[3][21];
                int xpos = buttonSizeX * 7;
                int ypos = buttonSizeY * 7;
                double buttonInterval = buttonSizeX * 1.25;

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

                    if (i == 15) {
                        image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));
                        panButton[0][i] = new Button();
                        panButton[0][i].setGraphic(new ImageView(image));
                    } else if (i == 5) {
                        image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));
                        panButton[1][0] = new Button();
                        panButton[1][0].setGraphic(new ImageView(image));
                    }
                    else if(i==10){
                        image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));
                        panButton[2][0] = new Button();
                        panButton[2][0].setGraphic(new ImageView(image));
                    }else if(i == 20){
                        image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/startcircle.jpg"));
                        panButton[0][i] = new Button();
                        panButton[0][i].setGraphic(new ImageView(image));
                    }
                    else {
                        image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/circle.jpg"));
                        panButton[0][i] = new Button();
                        panButton[0][i].setGraphic(new ImageView(image));
                    }

                    if(i<5) {
                        panButton[0][i] = createBoardBtn(panButton[0][i], xpos, ypos, buttonSizeX, buttonSizeY, false);
                        boardPane.getChildren().add(panButton[0][i]);
                    }
                    else if(i==5){
                        panButton[1][0] = createBoardBtn(panButton[1][0], xpos, ypos, buttonSizeX, buttonSizeY, false);
                        boardPane.getChildren().add(panButton[1][0]);
                    }
                    else if(i<10){
                        panButton[0][i] = createBoardBtn(panButton[0][i], xpos, ypos, buttonSizeX, buttonSizeY, false);
                        boardPane.getChildren().add(panButton[0][i]);
                    }
                    else if(i==10){
                        panButton[2][0] = createBoardBtn(panButton[2][0], xpos, ypos, buttonSizeX, buttonSizeY, false);
                        boardPane.getChildren().add(panButton[2][0]);
                    }
                    else{
                        panButton[0][i] = createBoardBtn(panButton[0][i], xpos, ypos, buttonSizeX, buttonSizeY, false);
                        boardPane.getChildren().add(panButton[0][i]);
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
                            continue;
                        } else {
                            image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/circle.jpg"));
                            panButton[1][p] = new Button();
                            panButton[1][p].setGraphic(new ImageView(image));
                            panButton[1][p] = createBoardBtn(panButton[1][p], xpos, ypos, buttonSizeX, buttonSizeY, false);
                            xpos -= buttonSizeX;
                            ypos += buttonSizeY;
                            boardPane.getChildren().add(panButton[1][p]);
                        }
                    }
                }

                xpos = buttonSizeX - 10;
                ypos = buttonSizeY - 10;
                for (p = 0; p < 6; p++) {
                    if (p == 0) {
                        xpos += buttonSizeX;
                        ypos += buttonSizeY;
                        continue;
                    } else {
                        if (p == 3) {
                            image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));
                            panButton[2][p] = new Button();
                            panButton[2][p].setGraphic(new ImageView(image));

                        } else {
                            image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/circle.jpg"));
                            panButton[2][p] = new Button();
                            panButton[2][p].setGraphic(new ImageView(image));
                        }
                        panButton[2][p] = createBoardBtn(panButton[2][p], xpos, ypos, buttonSizeX, buttonSizeY, false);
                        xpos += buttonSizeX;
                        ypos += buttonSizeY;
                        boardPane.getChildren().add(panButton[2][p]);
                    }
                }
                break;

            case 5:
                image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/fiveline.png"));
                line.setGraphic(new ImageView(image));
                line.setLayoutX(10);
                line.setLayoutY(5);
                line.setPrefWidth(350);
                line.setPrefHeight(350);
                boardPane.getChildren().add(line);

                panButton = new Button[6][36];

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
                    Image icon;

                    if(i<6){
                        if(i==0){
                            icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));

                            panButton[1][0] = new Button();
                            panButton[1][0].setGraphic(new ImageView(icon));
                            panButton[1][0].setLayoutX(x.x - buttonSizeX);
                            panButton[1][0].setLayoutY(x.y - buttonSizeY);
                            panButton[1][0].setPrefWidth(buttonSizeX);
                            panButton[1][0].setPrefHeight(buttonSizeY);
                            panButton[1][0].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                            boardPane.getChildren().add(panButton[1][0]);
                        }
                        else if(i==1){
                            icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));

                            panButton[2][0] = new Button();
                            panButton[2][0].setGraphic(new ImageView(icon));
                            panButton[2][0].setLayoutX(x.x - buttonSizeX);
                            panButton[2][0].setLayoutY(x.y - buttonSizeY);
                            panButton[2][0].setPrefWidth(buttonSizeX);
                            panButton[2][0].setPrefHeight(buttonSizeY);
                            panButton[2][0].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                            boardPane.getChildren().add(panButton[2][0]);
                        }
                        else if(i==2){
                            icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));

                            panButton[3][0] = new Button();
                            panButton[3][0].setGraphic(new ImageView(icon));
                            panButton[3][0].setLayoutX(x.x - buttonSizeX);
                            panButton[3][0].setLayoutY(x.y - buttonSizeY);
                            panButton[3][0].setPrefWidth(buttonSizeX);
                            panButton[3][0].setPrefHeight(buttonSizeY);
                            panButton[3][0].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                            boardPane.getChildren().add(panButton[3][0]);
                        }
                        else if(i==3){
                            icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));

                            panButton[0][20] = new Button();
                            panButton[0][20].setGraphic(new ImageView(icon));
                            panButton[0][20].setLayoutX(x.x - buttonSizeX);
                            panButton[0][20].setLayoutY(x.y - buttonSizeY);
                            panButton[0][20].setPrefWidth(buttonSizeX);
                            panButton[0][20].setPrefHeight(buttonSizeY);
                            panButton[0][20].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                            boardPane.getChildren().add(panButton[0][20]);
                        }
                        else if(i==4){
                            icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/startcircle.jpg"));

                            panButton[0][25] = new Button();
                            panButton[0][25].setGraphic(new ImageView(icon));
                            panButton[0][25].setLayoutX(x.x - buttonSizeX);
                            panButton[0][25].setLayoutY(x.y - buttonSizeY);
                            panButton[0][25].setPrefWidth(buttonSizeX);
                            panButton[0][25].setPrefHeight(buttonSizeY);
                            panButton[0][25].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                            boardPane.getChildren().add(panButton[0][25]);
                        }
                        else{
                            icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));

                            panButton[5][0] = new Button();
                            panButton[5][0].setGraphic(new ImageView(icon));
                            panButton[5][0].setLayoutX(x.x - buttonSizeX);
                            panButton[5][0].setLayoutY(x.y - buttonSizeY);
                            panButton[5][0].setPrefWidth(buttonSizeX);
                            panButton[5][0].setPrefHeight(buttonSizeY);
                            panButton[5][0].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                            boardPane.getChildren().add(panButton[5][0]);
                        }

                    }

                    else{
                        icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/circle.jpg"));
                        for(int k=0;k<6;k++){
                            if(k==0){

                                if(i<26&&i>21) {
                                    panButton[0][i-21] = new Button();
                                    panButton[0][i-21].setGraphic(new ImageView(icon));
                                    panButton[0][i-21].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[0][i-21].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[0][i-21].setPrefWidth(buttonSizeX);
                                    panButton[0][i-21].setPrefHeight(buttonSizeY);
                                    panButton[0][i-21].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[0][i-21]);
                                }
                                else if(i<10&&i>5){
                                    panButton[0][i] = new Button();
                                    panButton[0][i].setGraphic(new ImageView(icon));
                                    panButton[0][i].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[0][i].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[0][i].setPrefWidth(buttonSizeX);
                                    panButton[0][i].setPrefHeight(buttonSizeY);
                                    panButton[0][i].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[0][i]);
                                }
                                else if(i>9&&i<14){
                                    panButton[0][i+1] = new Button();
                                    panButton[0][i+1].setGraphic(new ImageView(icon));
                                    panButton[0][i+1].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[0][i+1].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[0][i+1].setPrefWidth(buttonSizeX);
                                    panButton[0][i+1].setPrefHeight(buttonSizeY);
                                    panButton[0][i+1].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[0][i+1]);
                                }
                                else if(i>13&&i<18){
                                    panButton[0][i+2] = new Button();
                                    panButton[0][i+2].setGraphic(new ImageView(icon));
                                    panButton[0][i+2].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[0][i+2].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[0][i+2].setPrefWidth(buttonSizeX);
                                    panButton[0][i+2].setPrefHeight(buttonSizeY);
                                    panButton[0][i+2].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[0][i+2]);
                                }
                                else if(i>17&&i<22){
                                    panButton[0][i+3] = new Button();
                                    panButton[0][i+3].setGraphic(new ImageView(icon));
                                    panButton[0][i+3].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[0][i+3].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[0][i+3].setPrefWidth(buttonSizeX);
                                    panButton[0][i+3].setPrefHeight(buttonSizeY);
                                    panButton[0][i+3].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[0][i+3]);
                                }
                                else{
                                    continue;
                                }
                            }
                            else if(k==1){
                                if(i==26||i==27){
                                    panButton[k][i-25] = new Button();
                                    panButton[k][i-25].setGraphic(new ImageView(icon));
                                    panButton[k][i-25].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[k][i-25].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[k][i-25].setPrefWidth(buttonSizeX);
                                    panButton[k][i-25].setPrefHeight(buttonSizeY);
                                    panButton[k][i-25].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[k][i-25]);
                                }
                            }
                            else if(k==2){
                                if(i==28||i==29){
                                    panButton[k][i-27] = new Button();
                                    panButton[k][i-27].setGraphic(new ImageView(icon));
                                    panButton[k][i-27].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[k][i-27].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[k][i-27].setPrefWidth(buttonSizeX);
                                    panButton[k][i-27].setPrefHeight(buttonSizeY);
                                    panButton[k][i-27].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[k][i-27]);
                                }
                            }
                            else if(k==3){
                                if(i==30||i==31){
                                    panButton[k][i-29] = new Button();
                                    panButton[k][i-29].setGraphic(new ImageView(icon));
                                    panButton[k][i-29].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[k][i-29].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[k][i-29].setPrefWidth(buttonSizeX);
                                    panButton[k][i-29].setPrefHeight(buttonSizeY);
                                    panButton[k][i-29].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[k][i-29]);
                                }
                            }
                            else if(k==4){
                                if(i==33){
                                    panButton[k][i-32] = new Button();
                                    panButton[k][i-32].setGraphic(new ImageView(icon));
                                    panButton[k][i-32].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[k][i-32].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[k][i-32].setPrefWidth(buttonSizeX);
                                    panButton[k][i-32].setPrefHeight(buttonSizeY);
                                    panButton[k][i-32].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[k][i-32]);
                                }
                                if(i==32){
                                    panButton[k][i-30] = new Button();
                                    panButton[k][i-30].setGraphic(new ImageView(icon));
                                    panButton[k][i-30].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[k][i-30].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[k][i-30].setPrefWidth(buttonSizeX);
                                    panButton[k][i-30].setPrefHeight(buttonSizeY);
                                    panButton[k][i-30].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[k][i-30]);
                                }
                            }
                            else {
                                if(i==35){
                                    panButton[k][i-34] = new Button();
                                    panButton[k][i-34].setGraphic(new ImageView(icon));
                                    panButton[k][i-34].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[k][i-34].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[k][i-34].setPrefWidth(buttonSizeX);
                                    panButton[k][i-34].setPrefHeight(buttonSizeY);
                                    panButton[k][i-34].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[k][i-34]);
                                }
                                if(i==34){
                                    panButton[k][i-32] = new Button();
                                    panButton[k][i-32].setGraphic(new ImageView(icon));
                                    panButton[k][i-32].setLayoutX(x.x - buttonSizeX/2);
                                    panButton[k][i-32].setLayoutY(x.y - buttonSizeY/2);
                                    panButton[k][i-32].setPrefWidth(buttonSizeX);
                                    panButton[k][i-32].setPrefHeight(buttonSizeY);
                                    panButton[k][i-32].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                                    boardPane.getChildren().add(panButton[k][i-32]);
                                }

                            }
                        }

                    }


                }
                break;

            case 6:
                image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/sixline.png"));
                line.setGraphic(new ImageView(image));
                line.setLayoutX(15);
                line.setLayoutY(00);
                line.setPrefWidth(400);
                line.setPrefHeight(400);
                line.setVisible(true);
                boardPane.getChildren().add(line);

                panButton = new Button[7][43];// routeIndex 0: 외곽, 1~6: 대각선 및 중심 경로

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
                        new Point(420, 200),
                        // 내부 대각선 경로 및 중심 (1~6번 라인)
                        new Point(320, 27), new Point(287, 84), new Point(253, 143), new Point(220, 200),        // route 1
                        new Point(120, 27), new Point(153, 84), new Point(187, 143), new Point(220, 200),        // route 2
                        new Point(20, 200), new Point(87, 200), new Point(153, 200), new Point(220, 200),        // route 3
                        new Point(120, 373), new Point(153, 316), new Point(187, 257), new Point(220, 200),       // route 4
                        new Point(220, 200), new Point(253, 257), new Point(287, 316), new Point(320, 373),       // route 5
                        new Point(220, 200), new Point(287, 200), new Point(353, 200), new Point(420, 200)       // route 6
                );

                // 외곽 route 0: 0~30까지 총 31칸
                for (int i = 0; i < 31; i++) {
                    Point pt = pointsHexa.get(i);
                    Image icon;
                    panButton[0][i] = new Button();
                    if(i==30 || i == 0){
                        icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/startcircle.jpg"));
                        panButton[0][i].setLayoutX(pt.x - buttonSizeX);
                        panButton[0][i].setLayoutY(pt.y - buttonSizeY);
                    }
                    else if(i == 5 || i == 10 || i==15 || i == 20 || i == 25){
                        icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));
                        panButton[0][i].setLayoutX(pt.x - buttonSizeX);
                        panButton[0][i].setLayoutY(pt.y - buttonSizeY);
                    }
                    else {
                        icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/circle.jpg"));
                        panButton[0][i].setLayoutX(pt.x - buttonSizeX/2);
                        panButton[0][i].setLayoutY(pt.y - buttonSizeY/2);
                    }

                    panButton[0][i].setGraphic(new ImageView(icon));
                    panButton[0][i].setPrefWidth(buttonSizeX);
                    panButton[0][i].setPrefHeight(buttonSizeY);
                    panButton[0][i].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                    boardPane.getChildren().add(panButton[0][i]);
                }

                // route 1~6 내부 라인 구성
                int[][] routeOffsets = {
                        {31, 4}, // route 1
                        {35, 4}, // route 2
                        {39, 4}, // route 3
                        {43, 4}, // route 4
                        {47, 4}, // route 5
                        {51, 4}  // route 6
                };

                for (int r = 1; r <= 6; r++) {
                    int offset = routeOffsets[r - 1][0];
                    int len = routeOffsets[r - 1][1];
                    for (int i = 0; i < len; i++) {
                        Point pt = pointsHexa.get(offset + i);
                        Image icon;
                        panButton[r][i] = new Button();
                        if(i==3&&r == 6){
                            icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/startcircle.jpg"));
                            panButton[r][i].setLayoutX(pt.x - buttonSizeX);
                            panButton[r][i].setLayoutY(pt.y - buttonSizeY);
                        }else if(i == 0 || i == 3){
                            icon = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg"));
                            panButton[r][i].setLayoutX(pt.x - buttonSizeX);
                            panButton[r][i].setLayoutY(pt.y - buttonSizeY);
                        }
                        else{
                            icon = (i == len - 1) ? new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/bigcircle.jpg")) : new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/circle.jpg"));
                            panButton[r][i].setLayoutX(pt.x - buttonSizeX / 2);
                            panButton[r][i].setLayoutY(pt.y - buttonSizeY / 2);
                        }

                        panButton[r][i].setGraphic(new ImageView(icon));
                        panButton[r][i].setPrefWidth(buttonSizeX);
                        panButton[r][i].setPrefHeight(buttonSizeY);
                        panButton[r][i].setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                        boardPane.getChildren().add(panButton[r][i]);
                    }
                }
                break;
        }

        turnLabel.setText("현재 차례: P1");

        labels = new Label[]{playerInfo1,playerInfo2,playerInfo3,playerInfo4};
        for(int i = 0; i<4; i++){
            labels[i].setText("");
            if(i<playerNum){
                labels[i].setText("P"+(i+1)+": 남은 말 " + pieceNum + "개");
            }
        }
       yootType = new Button[]{yootType0,yootType1,yootType2,yootType3,yootType4,yootType5};
       moveOption = new Button[]{moveOption0,moveOption1};
    }

    Button createBoardBtn(Button btn, int x, int y, int width, int height, boolean visibleStyle){
        btn.setLayoutX(x);
        btn.setLayoutY(y);

        btn.setPrefWidth(width);
        btn.setPrefHeight(height);

        if(!visibleStyle){
            btn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        }else{
            btn.setStyle("");
        }

        return btn;
    }

    public FlowPane getYootStackPanel(){
        return yootStackPanel;
    }

    public void updateBoard(List<Player> players) {
        // 1. Reset all buttons (remove piece icons)
        for (int r = 0; r < panButton.length; r++) {
            for (int p = 0; p < panButton[r].length; p++) {
                if (panButton[r][p] != null) {
                    panButton[r][p].setGraphic(null); // Or set to empty circle
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
                    Image image = new Image(getClass().getResourceAsStream("/YootProjectjavafx/img/piece_player" + player.getId() + ".png"));
                    panButton[route][pos].setGraphic(new ImageView(image));
                }
            }
        }
    }

    public void showWinner(String winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over");
        alert.setHeaderText(null);
        alert.setContentText(winner);
        alert.showAndWait();
    }

    public void showThrowResult(String result) {
        if (randomResult != null) {
            randomResult.setText("결과: " + result);
        } else {
            System.out.println("⚠ randomResult가 null입니다!");
        }
    }

    public Button getNewPieceButton() {
        return moveOption[0]; // "내보내기" 버튼
    }
    public Button getSelectOnBoardButton() {
        return moveOption[1]; // moveOption[1] = "판에서 선택" 버튼
    }
    public Button[][] getPanButton() {
        return panButton;
    }
    public AnchorPane getPanelPan() {
        return boardPane;
    }

    public Button getRandomButton() {
        return randomButton;
    }


    public Button[] getYootType() {
        return yootType;
    }

    public Label[] getPlayerInfoLabels() {
        return labels;
    }
    public Label getTurnLabel() {
        return turnLabel;
    }

    public void updateBoard(List<Player> players, EventHandler listener) {
        String[] pieceImages = {
                "/YootProjectjavafx/img/yellow1.jpg", "/YootProjectjavafx/img/blue1.jpg", "/YootProjectjavafx/img/green1.jpg", "/YootProjectjavafx/img/red1.jpg",
                "/YootProjectjavafx/img/yellow2.jpg", "/YootProjectjavafx/img/blue2.jpg", "/YootProjectjavafx/img/green2.jpg", "/YootProjectjavafx/img/red2.jpg",
                "/YootProjectjavafx/img/yellow3.jpg", "/YootProjectjavafx/img/blue3.jpg", "/YootProjectjavafx/img/green3.jpg", "/YootProjectjavafx/img/red3.jpg",
                "/YootProjectjavafx/img/yellow4.jpg", "/YootProjectjavafx/img/blue4.jpg", "/YootProjectjavafx/img/green4.jpg", "/YootProjectjavafx/img/red4.jpg",
                "/YootProjectjavafx/img/yellow5.jpg", "/YootProjectjavafx/img/blue5.jpg", "/YootProjectjavafx/img/green5.jpg", "/YootProjectjavafx/img/red5.jpg",
                "/YootProjectjavafx/img/bigyellow1.jpg", "/YootProjectjavafx/img/bigblue1.jpg", "/YootProjectjavafx/img/biggreen1.jpg", "/YootProjectjavafx/img/bigred1.jpg",
                "/YootProjectjavafx/img/bigyellow2.jpg", "/YootProjectjavafx/img/bigblue2.jpg", "/YootProjectjavafx/img/biggreen2.jpg", "/YootProjectjavafx/img/bigred2.jpg",
                "/YootProjectjavafx/img/bigyellow3.jpg", "/YootProjectjavafx/img/bigblue3.jpg", "/YootProjectjavafx/img/biggreen3.jpg", "/YootProjectjavafx/img/bigred3.jpg",
                "/YootProjectjavafx/img/bigyellow4.jpg", "/YootProjectjavafx/img/bigblue4.jpg", "/YootProjectjavafx/img/biggreen4.jpg", "/YootProjectjavafx/img/bigred4.jpg",
                "/YootProjectjavafx/img/bigyellow5.jpg", "/YootProjectjavafx/img/bigblue5.jpg", "/YootProjectjavafx/img/biggreen5.jpg", "/YootProjectjavafx/img/bigred5.jpg"

        };

        Button[][] panButton = this.getPanButton();
        AnchorPane panel = this.getPanelPan();

        if (panButton == null || panel == null) return;

        // 1. 기존 말 JButton 제거 (이름이 "piece_"로 시작하는 것만)
        ObservableList<Node> children = panel.getChildren();
        children.removeIf(node->
                node instanceof Button &&
                node.getUserData() != null &&
                        ((String) node.getUserData()).startsWith("piece_"));


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

                Button btn = panButton[route][pos];
                if (btn == null) continue;

                int imageIndex = isBigCirclePosition(route, pos)
                        ? 20 + (count - 1) * 4 + (playerId - 1)
                        : (count - 1) * 4 + (playerId - 1);
                String imagePath = pieceImages[imageIndex];

                pieceBtn = new Button();
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                pieceBtn.setGraphic(new ImageView(image));
                pieceBtn.setLayoutX(btn.getLayoutX());
                pieceBtn.setLayoutY(btn.getLayoutY());
                pieceBtn.setPrefWidth(btn.getPrefWidth());
                pieceBtn.setPrefHeight(btn.getPrefHeight());
                pieceBtn.setOnAction(listener);
                pieceBtn.setId("MOVE_" + route + "_" + pos);
                pieceBtn.setUserData("piece_" + playerId);
                pieceBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                children.add(pieceBtn);

                System.out.println("말 추가됨: Player " + playerId + " → MOVE_" + route + "_" + pos + ", count=" + count);
            }
        }
        Label[] playerLabels = this.getPlayerInfoLabels();

        for (Player p : players) {
            int pid = p.getId(); // 1-based
            String status = "P" + pid + " : 남은 말 " + p.getStandbyPieceCount() + "개, 점수 " + p.getScore();
            playerLabels[pid - 1].setText(status);
        }

    }
    public void updateTurn(int turn){
        Label turnLabel = this.getTurnLabel();
        if (turnLabel != null) {

            turnLabel.setText("현재 차례: P" + turn);

        }

    }
    private boolean isBigCirclePosition(int route, int pos) {
        return (route == 0 && (pos == 5 || pos == 10 || pos == 15 || pos == 20)) || (route == 2 && pos == 3);
    }

    public Button getpieceBtn(){
        return pieceBtn;
    }
}
