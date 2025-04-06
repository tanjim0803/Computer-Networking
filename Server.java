/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop.groupchat;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author Tanjim Ahmed
 */


public class Server {
    static Vector<ClientHandler> clientList = new Vector<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started on port 1234...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket);

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            ClientHandler newClient = new ClientHandler(clientSocket, dis, dos);
            Thread t = new Thread(newClient);
            clientList.add(newClient);
            t.start();
        }
    }
}
