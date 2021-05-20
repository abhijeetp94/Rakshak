package mainPack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static ServerSocket serverSocket;



    public static void main(String[] args) {
        try{
            serverSocket = new ServerSocket(8800);

        } catch (IOException ie){
            ie.printStackTrace();
        }
        while (true) {
            try {

                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client Connected");
                Thread t = new Thread(new ClientHandler(socket));
                t.start();

        } catch(IOException ie){
            ie.printStackTrace();
        }
    }
    }
}
