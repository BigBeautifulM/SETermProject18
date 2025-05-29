package YootProjectjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EndPagefxController {
    private EndPagefx page;

    @FXML
    private AnchorPane endPage;

    public EndPagefxController(){}

    public void setBoard(EndPagefx endpage){
        this.page = endpage;
    }

    @FXML
    private void handleRestart(ActionEvent e){
        Stage stage = (Stage) endPage.getScene().getWindow();
        stage.close();
        FirstPagefx.showFirstPage();
    }

    @FXML
    private void handleExit(ActionEvent e){
        System.exit(0);
    }
}
