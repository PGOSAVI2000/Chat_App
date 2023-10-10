import java.net.*;
import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.*;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Client {

    Socket socket;
    BufferedReader br; // read for data
    PrintWriter out; // write for data

    public Client() {
        try {
            System.out.println("Sending request to server");
            socket = new Socket("192.168.123.8", 3003);
            System.out.println("connection done.");

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
                        System.out.println("Server terminated the chat");
                        break;
                    }

                    System.out.println("Server : " + msg);

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
                    e.printStackTrace();
                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is client");

        new Client();
    }

}
