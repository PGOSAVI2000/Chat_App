import java.net.*;
import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.*;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Server {

    ServerSocket server;
    Socket socket;
    BufferedReader br; // read for data
    PrintWriter out; // write for data

    // Constructor
    public Server() {
        try {
            server = new ServerSocket(3003);
            System.out.println("sever is ready to accept connection");
            System.out.println("waiting...");
            socket = server.accept();

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading(); // Function Calling
            startWriting(); // FUnction Calling

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void startReading() {
        // thread - read karke deta rahega
        Runnable r1 = () -> // Multithreading
        {

            System.out.println("reader started..");
            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        break;
                    }

                    System.out.println("Client : " + msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        // thread - data user lega and send karega client tak
        Runnable r2 = () -> // Multithreading
        {
            System.out.println("writer started..");
            while (true) {
                try {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                } catch (Exception e) {
                    e.printStackTrace(); // console pe technical information ko trace karte waqt ale Vale Exception ke
                                         // liye
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is server..going to start server");
        new Server();
    }

}
