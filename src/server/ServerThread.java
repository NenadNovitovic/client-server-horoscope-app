package server;

import database.DataBaseConnection;
import javafx.scene.control.TextArea;
import sign.Sign;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

public class ServerThread extends Thread {
    DataBaseConnection dbConnection;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static boolean running;

    public ServerThread(Socket s){
        this.socket=s;
        try{
            out=new ObjectOutputStream(s.getOutputStream());
            in=new ObjectInputStream(s.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        running=true;
    }

    public static void stopThreads(){
        running=false;
    }

    //Glavna funkcija za odredjivanje toka kommunikacije servera sa kliejntom
    @Override
    public void run() {
        System.out.println("Dobio novog klijenta!");
        while (running) {
            try {
                String request = (String)in.readObject();
                if (request.equals("exit")){
                    break;
                }else{
                    String[] requestString=request.split("~");
                    String requestType=requestString[0];
                    if(requestType.equals("SIGN")){
                        getSignFunction(requestString[1]);
                    }else{
                        out.writeObject("Nepoznata poruka u komunikaciji.");
                    }
                    }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            out.writeObject("exit");
            in.close();
            out.close();
            socket.close();
            System.out.println("Klijent se odjavio.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSignFunction(String message) throws IOException{
        String response="";
        response=findSign(message);
        if(dbConnection==null)
            dbConnection=new DataBaseConnection();
        Sign sign= new Sign();
        try {
            sign = dbConnection.getSign(response);
            out.writeObject(sign);
        }catch (SQLException e) {
            System.out.println("Problem u SQL.");
            dbConnection=null;
            sign.setName("Nepoznat znak");
            out.writeObject(sign);
        }catch (NullPointerException e){
            System.out.println("Problem u niti:Nemoguće pročitati iz baze.");
            dbConnection=null;
            sign.setName("Nepoznat znak");
            out.writeObject(sign);
        }
    }

    public String findSign(String message){
        String[] messageStringSplit=message.split("-");
        String month=messageStringSplit[0];
        int day=Integer.parseInt(messageStringSplit[1]);
        String sign="Greska.";
        if (month.equals("Decembar")){
            if (day < 22)
                sign = "Strelac";
            else
                sign ="Jarac";
        }
        else if (month.equals( "Januar")){
            if (day < 20)
                sign = "Jarac";
            else
                sign = "Vodolija";
        }
        else if (month.equals( "Februar")){
            if (day < 19)
                sign = "Vodolija";
            else
                sign = "Ribe";
        }
        else if(month.equals( "Mart")){
            if (day < 21)
                sign = "Ribe";
            else
                sign = "Ovan";
        }
        else if (month.equals( "April")){
            if (day < 20)
                sign = "Ovan";
            else
                sign = "Bik";
        }
        else if (month.equals( "Maj")){
            if (day < 21)
                sign = "Bik";
            else
                sign = "Blizanci";
        }
        else if( month.equals( "Jun")){
            if (day < 21)
                sign = "Blizanci";
            else
                sign = "Rak";
        }
        else if (month.equals( "Jul")){
            if (day < 23)
                sign = "Rak";
            else
                sign = "Lav";
        }
        else if( month.equals( "Avgust")){
            if (day < 23)
                sign = "Lav";
            else
                sign = "Devica";
        }
        else if (month.equals( "Septembar")){
            if (day < 23)
                sign = "Devica";
            else
                sign = "Vaga";
        }
        else if (month.equals( "Oktobar")){
            if (day < 23)
                sign = "Vaga";
            else
                sign = "Škorpija";
        }
        else if (month.equals( "Novembar")){
            if (day < 22)
                sign = "Škorpija";
            else
                sign = "Strelac";
        }
        return sign;
    }

}


