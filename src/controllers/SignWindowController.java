package controllers;

import client.ClientConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sign.Sign;

import java.io.IOException;

public class SignWindowController {
    private ClientConnection connection;
    private Sign sign;
    private Scene scene;

    @FXML private Label lblSignName;
    @FXML private Label lblShownInfo;
    @FXML private ImageView imgViewSign;
    @FXML private TextArea taSignText;

    public void initData(Sign sign, ClientConnection conn,Scene s){
        connection=conn;
        scene=s;
        this.sign=sign;
        lblSignName.setText(sign.getName());
        imgViewSign.setImage(new Image("/images/"+sign.getName().toLowerCase()+".png"));
        taSignText.setText(sign.getBasicDescription());
    }

    public void backToFirstView(ActionEvent event)throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/clientmainwindow.fxml"));
        Parent noviRoot = loader.load();

        ClientController controller = loader.getController();
        controller.setConnection(connection);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void showBasicDescription(){
        taSignText.setText(sign.getBasicDescription());
        lblShownInfo.setText("Opšte informacije o znaku:");
    }
    public void showDailyHoroscope(){
        taSignText.setText(sign.getDailyHoroscope());
        lblShownInfo.setText("Dnevni horoskop:");
    }
    public void showFamousPeople(){
        taSignText.setText(sign.getFamousPeopleInSameSign());
        lblShownInfo.setText("Poznate ličnosti:");
    }

}
