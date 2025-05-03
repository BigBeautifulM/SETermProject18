package YootProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayGame implements ActionListener{



    PlayGame(int people, int mal, int boardShape)
    {
        System.out.printf("%d, %d , %d", people, mal, boardShape);
        YootBoard board = new YootBoard();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        }
    }

