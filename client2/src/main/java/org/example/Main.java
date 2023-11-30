package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 3800;
        DataOutputStream out;
        DataInputStream in;
        Socket socket;
        String line;
        Thread thread;
        try {
            socket = new Socket(host, port);
            Scanner input = new Scanner(System.in);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            Runnable readLine = new ReadLine(in);
            thread = new Thread(readLine);
            thread.start();
            while (true){
                line = input.nextLine();
                out.writeUTF(line);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

class ReadLine implements Runnable{
    DataInputStream in;
    public ReadLine( DataInputStream in){
        this.in = in;
    }
    @Override
    public void run() {
        try {
            while (true){
                System.out.println(in.readUTF());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}