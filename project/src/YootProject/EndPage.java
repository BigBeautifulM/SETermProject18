package YootProject;

import java.awt.*;
import javax.swing.*;

public class EndPage extends JFrame {
    private JPanel panelPan;

    private int windowSizeX = 515;
    private int windowSizeY = 170;

    private int buttonSizeX = 235;
    private int buttonSizeY = 110;

    public EndPage(){
        panelPan = new JPanel();
        panelPan.setBounds(0,0,windowSizeX,windowSizeY);
        this.setContentPane(panelPan);
        this.setSize(windowSizeX,windowSizeY);
        this.setVisible(true);

        panelPan.setLayout(null);

        JButton restartButton = new JButton("재시작");
        restartButton.setBounds(10,10,buttonSizeX,buttonSizeY);
        restartButton.setOpaque(true);
        restartButton.setBackground(Color.LIGHT_GRAY);
        restartButton.setForeground(Color.BLACK);
        restartButton.addActionListener(e->{
            this.dispose();
            new FirstPage().setVisible(true);
        });
        panelPan.add(restartButton);

        JButton exitButton = new JButton("종료");
        exitButton.setBounds(255,10,buttonSizeX,buttonSizeY);
        exitButton.setOpaque(true);
        exitButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setForeground(Color.BLACK);
        exitButton.addActionListener(e->System.exit(0));
        panelPan.add(exitButton);
    }

    /* 테스트용 메인 함수
    public static void main(String[] args){
        new EndPage();
    }
     */
}
