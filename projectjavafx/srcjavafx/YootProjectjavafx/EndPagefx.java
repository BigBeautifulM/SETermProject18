package YootProjectjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EndPagefx {

    private EndPagefxController controller;
    public EndPagefx(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/YootProjectjavafx/EndPage.fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            controller.setBoard(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
