package controllers;

import database.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import server.Server;
import sign.Sign;

import java.sql.SQLException;

public class ServerController {
    DataBaseConnection conn;
    Server server;
    Sign sign;
    @FXML private Button btnSaveToDataBase;
    @FXML private ComboBox comboBoxSign;
    @FXML private TextArea  textAreaBasicDesc;
    @FXML private TextArea  textAreaFamousPeople;
    @FXML private TextArea  textAreaDailyHoroscope;
    @FXML private TextArea textAreaLog;
    @FXML private Label lblSign;


    @FXML private void initialize(){
        server=new Server();
        server.start();
        comboBoxSign.getItems().addAll("Ovan","Bik","Blizanci","Rak","Lav",
                "Devica","Vaga","Škorpija","Strelac","Jarac","Vodolija","Ribe"
        );
        btnSaveToDataBase.setDisable(true);
    }

    public void closeServer(){
        server.stopServer();
    }

    public void readFromDataBase(){
        if (comboBoxSign.getValue()!=null){
            if(conn==null)
                conn=new DataBaseConnection();
            try {
                if(conn!=null){
                    sign=conn.getSign(comboBoxSign.getValue().toString());
                    lblSign.setText(sign.getName());
                    fillTextAreas();
                    btnSaveToDataBase.setDisable(false);
                    textAreaLog.appendText("Pročitao iz baze znak: "+sign.getName()+"\n");
                }
            } catch (SQLException  | NullPointerException e ) {
                textAreaLog.appendText("Problem sa povezivanjem sa bazom podataka.\n");
                conn=null;
                lblSign.setText("");
                clearTextAreas();
                btnSaveToDataBase.setDisable(true);
            }
        }else{
            textAreaLog.appendText("Morate izabrati znak.\n");
        }
    }
    public void fillTextAreas(){
        textAreaBasicDesc.setText(sign.getBasicDescription());
        textAreaFamousPeople.setText(sign.getFamousPeopleInSameSign());
        textAreaDailyHoroscope.setText(sign.getDailyHoroscope());
    }
    public void clearTextAreas(){
        textAreaBasicDesc.clear();
        textAreaFamousPeople.clear();
        textAreaDailyHoroscope.clear();
    }

    public void saveSignToDataBase(){
        sign.setBasicDescription(textAreaBasicDesc.getText());
        sign.setFamousPeopleInSameSign(textAreaFamousPeople.getText());
        sign.setDailyHoroscope(textAreaDailyHoroscope.getText());
        if(checkLenght()){
            if(conn==null)
                conn=new DataBaseConnection();
            try{
                conn.updateSign(sign);
                clearTextAreas();
                btnSaveToDataBase.setDisable(true);
                textAreaLog.appendText("Baza je ažurirana. Promenjen znak: "+sign.getName()+"\n");
                lblSign.setText("");
            }catch (NullPointerException | SQLException e){
                textAreaLog.appendText("Niste konektovani na bazu.\n");
                conn=null;
            }
        }
    }

    private boolean checkLenght(){
        if(sign.getBasicDescription().length()>5000){
            textAreaLog.appendText("Opšti opis znaka mora da ima manje od 5000 karaktera.\n");
            return false;
        }
        if(sign.getDailyHoroscope().length()>3000){
            textAreaLog.appendText("Opšti opis znaka mora da ima manje od 3000 karaktera.\n");
            return false;
        }
        if(sign.getFamousPeopleInSameSign().length()>3000){
            textAreaLog.appendText("Opšti opis znaka mora da ima manje od 3000 karaktera.\n");
            return false;
        }
        return true;
    }

}
