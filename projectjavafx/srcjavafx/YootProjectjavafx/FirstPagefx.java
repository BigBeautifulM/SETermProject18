package YootProjectjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.application.Application;

public class FirstPagefx extends Application {
    private static Stage firstpagefx;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/YootProjectjavafx/FirstPage.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            firstpagefx = primaryStage;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }

    public static void closeFirstPagefx(){
        if(firstpagefx != null){
            firstpagefx.close();
        }
    }

    public static void showFirstPage(){
        try{
            FXMLLoader loader = new FXMLLoader(FirstPagefx.class.getResource("/YootProjectjavafx/FirstPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            firstpagefx = stage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
