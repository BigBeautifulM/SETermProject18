package YootProject;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class YootBoard extends JFrame {
    private JPanel panelPan;
    JButton[][] panButton;

    private int windowSizeX = 1000;
    private int windowSizeY = 700;
    private int buttonSizeX = windowSizeX / 20;
    private int buttonSizeY = buttonSizeX;
    JButton throwButton, newPiece;
    JButton[] testButton = new JButton[7];
    JButton nowPlayer;
    JButton panClick;
    JButton[] playerInfobtn;
    JLabel yotResult;
    JLabel boardMessage;
    JLabel[] playerInfo;

    JLabel line;

    public YootBoard() {

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

            if (i == 5 || i == 10 || i == 15) {
                panButton[0][i] = new JButton(new ImageIcon("project/img/bigcircle.jpg"));
            } else if (i == 20) {
                panButton[0][i] = new JButton(new ImageIcon("project/img/bigcircle.jpg"));
            } else {
                panButton[0][i] = new JButton(new ImageIcon("project/img/circle.jpg"));
            }
            panButton[0][i] = createBoardBtn(panButton[0][i], xpos, ypos, buttonSizeX, buttonSizeY, false);
            panelPan.add(panButton[0][i]);
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
}
