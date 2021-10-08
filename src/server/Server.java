package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    public Server(){ }
    @Override public void run(){
        System.out.println("Pokrenut server...");
        try{
            ServerSocket ss=new ServerSocket(9000);
            while(true){
                System.out.println("Ceka novog klijenta...");
                Socket s=ss.accept();
                new ServerThread(s).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopServer(){
        ServerThread.stopThreads();
        System.exit(1);
    }
}
