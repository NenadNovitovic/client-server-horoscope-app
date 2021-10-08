package client;

import controllers.ClientController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import messagebox.MessageBox;
import sign.Sign;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private boolean isConnected;
    private ClientController clientController;
    private Label lblConnectionStatus;
    private Button btnShowSign;

    public ClientConnection(ClientController clientController,Label lbl, Button button){
        isConnected=false;
        lblConnectionStatus=lbl;
        btnShowSign=button;
        this.clientController=clientController;
    }

    public void connectToServer(){
        try{
            if(!isConnected){
                InetAddress addr= InetAddress.getByName("127.0.0.1");
                socket=new Socket(addr,9000);
                out=new ObjectOutputStream(socket.getOutputStream());
                in=new ObjectInputStream(socket.getInputStream());
                isConnected=true;
                clientController.setIsConnected(true);
                lblConnectionStatus.setText("Konektovani ste!");
                lblConnectionStatus.setStyle("-fx-text-fill: green;");
                btnShowSign.setDisable(false);
            }else {
                MessageBox.show("Obaveštenje.","Već ste konektovani!");
            }
        }catch(Exception e){
            MessageBox.show("Obaveštenje.","Nije moguće povezati se sa serverom.");
        }
    }

    public void closeConnection(){
        try {
            if(isConnected){
                out.writeObject("exit");
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Nije moguće zatvoriti konekciju");
        }
    }

    public Sign getSignFromServer(String date)throws IOException{
        try{
            out.writeObject("SIGN~"+date);
            return (Sign)in.readObject();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
