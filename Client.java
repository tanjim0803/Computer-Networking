/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.groupchat;
import java.io.*;
import java.net.*;
/**
 *
 * @author Tanjim Ahmed
 */

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connected to server!");

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // Thread for receiving messages
        Thread readThread = new Thread(() -> {
            try {
                while (true) {
                    String msg = dis.readUTF();
                    System.out.println(msg);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        });

        // Thread for sending messages
        Thread writeThread = new Thread(() -> {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    String msgToSend = br.readLine();
                    dos.writeUTF(msgToSend);
                }
            } catch (IOException e) {
                System.out.println("Error writing to server.");
            }
        });

        readThread.start();
        writeThread.start();
    }
}

