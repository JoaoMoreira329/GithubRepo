package org.academiadecodigo.nanderthals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int portNumber = 8080;

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept(); // Wait for a client to connect
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println("Client connected.");

            String message;
            while ((message = in.readLine()) != null) { // Read messages from the client
                System.out.println("Received: " + message); // Print received message
            }

            System.out.println("Client disconnected.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage()); // Handle IO exceptions
        }
    }
}
