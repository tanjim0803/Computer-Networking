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

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private boolean isLoggedIn;

    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
        this.isLoggedIn = true;
    }

    @Override
    public void run() {
        String received;
        try {
            while (true) {
                received = dis.readUTF();
                if (received.equalsIgnoreCase("exit")) {
                    this.isLoggedIn = false;
                    this.socket.close();
                    break;
                }

                // broadcast to all clients
                for (ClientHandler ch : Server.clientList) {
                    if (ch.isLoggedIn) {
                        ch.dos.writeUTF("Client " + socket.getPort() + ": " + received);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + socket);
        } finally {
            try {
                dis.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

