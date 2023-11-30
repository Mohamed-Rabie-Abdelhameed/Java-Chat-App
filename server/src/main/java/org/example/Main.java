package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        int port = 3800;
        DataInputStream in1;
        DataOutputStream out1;
        DataInputStream in2;
        DataOutputStream out2;
        ServerSocket server;
        Socket socket1;
        Socket socket2;
        Thread thread;
        try {
            server = new ServerSocket(port);
            socket1 = server.accept();
            socket2 = server.accept();
            System.out.println("connected");
            in1 = new DataInputStream(socket1.getInputStream());
            out1 = new DataOutputStream(socket1.getOutputStream());
            in2 = new DataInputStream(socket2.getInputStream());
            out2 = new DataOutputStream(socket2.getOutputStream());
            Runnable readLine1 = new ReadLine(in1, out2);
            Runnable readLine2 = new ReadLine(in2, out1);
            thread = new Thread(readLine1);
            thread.start();
            thread = new Thread(readLine2);
            thread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


class ReadLine implements Runnable{
    DataInputStream in;
    DataOutputStream out;
    public ReadLine( DataInputStream in, DataOutputStream out){
        this.in = in;
        this.out = out;
    }
    @Override
    public void run() {
        try {
            while (true){
                out.writeUTF(in.readUTF());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}