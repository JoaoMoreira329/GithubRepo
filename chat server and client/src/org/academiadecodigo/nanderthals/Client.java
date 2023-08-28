package org.academiadecodigo.nanderthals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String hostName = "localhost";
        int portNumber = 8080;

        try (
                Socket clientSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            System.out.println("Connected to the server. Type 'exit' to quit.");

            while (true) {
                String userInput = input.nextLine();
                if (userInput.equalsIgnoreCase("exit")) {
                    break; // Exit the loop if user input is 'exit'
                }
                out.println(userInput); // Send user input to the server
            }

            System.out.println("Closing the client.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage()); // Handle IO exceptions
        }
    }
}




