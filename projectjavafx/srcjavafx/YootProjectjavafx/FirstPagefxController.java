package YootProjectjavafx;

import YootProject.PlayConfig;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class FirstPagefxController {
    @FXML
    private ToggleGroup playerNum;
    @FXML
    private  ToggleGroup pieceNum;
    @FXML
    private ToggleGroup boardNum;
    @FXML

    private PlayConfig playconfig;

    public void initialize(){
        playconfig = new PlayConfig();
    }

    @FXML
    private void handleStart(ActionEvent event){
         RadioButton selectedPlayer = (RadioButton) this.playerNum.getSelectedToggle();
         RadioButton selectedPiece = (RadioButton) this.pieceNum.getSelectedToggle();
         RadioButton selectedBoard = (RadioButton) this.boardNum.getSelectedToggle();

         this.playconfig.setPlayerNum(Integer.parseInt((String)selectedPlayer.getUserData()));
         this.playconfig.setPieceNum(Integer.parseInt((String)selectedPiece.getUserData()));
         this.playconfig.setBoardShape(Integer.parseInt((String)selectedBoard.getUserData()));

         new PlayGamefx(this.playconfig);

         FirstPagefx.closeFirstPagefx();
    }
}
