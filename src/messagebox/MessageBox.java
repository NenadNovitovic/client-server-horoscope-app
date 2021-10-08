package messagebox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageBox {
    public static void show(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMinHeight(125);
        window.setResizable(false);

        Label lblMessage = new Label();
        lblMessage.setText(message);

        Button btnClose = new Button("U redu");
        btnClose.setOnAction(e->window.close());
        btnClose.setStyle("-fx-background-color: #d05ce3; -fx-background-radius: 5px;");


        VBox vBox = new VBox();
        vBox.getChildren().addAll(lblMessage,btnClose);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        Scene scene=new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }
}
