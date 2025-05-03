package YootProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

public class BoardAdapter implements ActionListener{

    PlayConfig playConfig;
    public BoardAdapter(PlayConfig playConfig) {
        this.playConfig = playConfig;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JRadioButton btn = (JRadioButton)e.getSource();
        int boardShape = Integer.parseInt(btn.getText());
        playConfig.setBoardShape(boardShape);
        System.out.println(playConfig.getBoardShape());
    }

}