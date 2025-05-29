package YootProjectjavafx;

import java.awt.*;
import java.util.List;
import java.util.Arrays;

import YootProject.Piece;
import YootProject.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javax.swing.*;
import java.io.IOException;

public class YootBoardfx extends TitledPane {
    private YootBoardfxController controller;
    private Stage stage;

    public YootBoardfx(int playerNum, int pieceNum, int boardNum){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/YootProjectjavafx/YootBoard.fxml"));
            Parent root = loader.load();
            controller = loader.getController();
            controller.initBoard(playerNum,pieceNum,boardNum);
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YootBoardfxController getController(){
        return controller;
    }

    public Stage getStage(){
        return stage;
    }
}
