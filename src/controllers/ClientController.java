package controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import client.ClientConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import messagebox.MessageBox;
import sign.Sign;

import java.io.IOException;

public class ClientController {
    ClientConnection connection;
    private boolean isConnected;
    private Scene scene;
    @FXML private ComboBox cbDay;
    @FXML private ComboBox cbMonth;
    @FXML private Label lblConnectionStatus;
    @FXML private Button btnShowSign;

    @FXML private void initialize(){
        cbMonth.getItems().addAll(
                "Januar","Februar","Mart","April","Maj","Jun",
                "Jul","Avgust","Septembar","Oktobar","Novembar","Decembar");
        cbMonth.setOnAction(e-> fillCbDay());
        isConnected=false;
        btnShowSign.setDisable(true);
    }

    private void fillCbDay(){
        {
            cbDay.getItems().clear();
            if(cbMonth.getValue().equals("Februar")){
                for(int i=1;i<29;i++){
                    cbDay.getItems().add(i);
                }
            }else if(cbMonth.getValue().equals("Januar")||cbMonth.getValue().equals("Mart")||
                    cbMonth.getValue().equals("Maj")||cbMonth.getValue().equals("Jul")||
                    cbMonth.getValue().equals("Avgust")||cbMonth.getValue().equals("Oktobar")||
                    cbMonth.getValue().equals("Decembar")){
                for(int i=1;i<32;i++){
                    cbDay.getItems().add(i);
                }
            }else if(cbMonth.getValue().equals("April")||cbMonth.getValue().equals("Jun")||
                    cbMonth.getValue().equals("Septembar")||cbMonth.getValue().equals("Novembar")){
                for(int i=1;i<31;i++){
                    cbDay.getItems().add(i);
                }
            }
        }
    }

    public void connectToServer(){
        if (connection==null){
            connection=new ClientConnection(this,lblConnectionStatus,btnShowSign);
        }
        if(!isConnected){
            connection.connectToServer();
        }else{
            MessageBox.show("Obaveštenje!","Već ste konektovani!");
        }
    }

    public void setIsConnected(boolean connection){
        isConnected=connection;
    }

    public void setConnection(ClientConnection conn){
        connection=conn;
    }

    public void setScene(Scene s){
        scene=s;
    }

    public void closeConnection(){
        if(connection!=null)
            connection.closeConnection();
    }

    public void showSign(ActionEvent event){
        if(cbMonth.getValue()!=null && cbDay.getValue()!=null){
            try{
                Sign sign=connection.getSignFromServer(cbMonth.getValue()+"-"+cbDay.getValue());
                if(!sign.getName().equals("Nepoznat znak")){
                    FXMLLoader loader=new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxml/clientsignwindow.fxml"));
                    Parent noviRoot = loader.load();

                    Scene noviScene = new Scene(noviRoot);
                    SignWindowController controller = loader.getController();
                    controller.initData(sign,connection,scene);

                    Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    window.setScene(noviScene);
                    window.show();
                }else{
                    MessageBox.show("Obaveštenje!","Nije pokrenuta baza podataka na serveru.");
                }
            }catch (IOException e){
                MessageBox.show("Obaveštenje!","Nemoguće konektovati se na server.");
                Platform.runLater(()->{
                    btnShowSign.setDisable(true);
                    lblConnectionStatus.setText("Niste konektovani na server.");
                    lblConnectionStatus.setStyle("-fx-text-fill: red;");
                });
                isConnected=false;
                connection=null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            MessageBox.show("Upozorenje!","Morate izabrati dan i mesec.");
            }
        }
}
