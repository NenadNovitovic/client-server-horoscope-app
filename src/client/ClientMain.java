package client;

import controllers.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/clientmainwindow.fxml"));
        Parent root = loader.load();
        Scene scene=new Scene(root, 620,520);

        ClientController controller = loader.getController();
        controller.connectToServer();
        controller.setScene(scene);

        primaryStage.setTitle("Horoscope App");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e->controller.closeConnection());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
