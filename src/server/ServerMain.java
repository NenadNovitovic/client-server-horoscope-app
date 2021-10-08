package server;

import controllers.ClientController;
import controllers.ServerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/server.fxml"));
        Parent root = loader.load();

        ServerController controller = loader.getController();

        primaryStage.setTitle("Horoscope App Server");
        primaryStage.setOnCloseRequest(e->controller.closeServer());
        primaryStage.setScene(new Scene(root, 920, 615));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
