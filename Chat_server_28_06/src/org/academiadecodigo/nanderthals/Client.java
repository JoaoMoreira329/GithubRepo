package org.academiadecodigo.nanderthals;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

// The Client class responsible for connecting to the server
public class Client {

    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientName;

    public static void main(String[] args) throws IOException {

        Scanner nameInput = new Scanner(System.in);
        System.out.println("Put your name ");
        String name = nameInput.nextLine();

        String hostName = "localhost"; // Server hostname
        int portNumber = 8080; // Server port number

        // Establish a connection to the server
        Socket clientSocket = new Socket(hostName, portNumber);
        Client client = new Client(clientSocket, name); // Create a client instance

        client.readMessage(); // Start reading messages from the server
        client.sendMessage(); // Start sending messages to the server


    }

    public Client(Socket clientSocket, String input) throws IOException {
        this.clientSocket = clientSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.clientName = input;
    }

    // Send messages to the server
    public void sendMessage() throws IOException {
        // Send the client's name to the server
        bufferedWriter.write(clientName);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        Scanner inp = new Scanner(System.in);

        // Continuously read user input and send messages to the server
        while (clientSocket.isConnected()) {
            String message = inp.nextLine();

            // Handle special commands or send regular messages
            if (message.equals("/help")) {
                helpCommand();
            } else if (message.equals("/list")) {
                listCommand();
            } else if (message.startsWith("/username")) {
                changeUsernameCommand();
            } else if (message.startsWith("/whisper")) {
                whisperCommand();
            } else {
                // Send regular messages
                bufferedWriter.write(clientName + " sent: " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
    }

    // Read messages from the server
    public void readMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mesgToReceive;
                while (clientSocket.isConnected()) {
                    try {
                        // Read and display messages from the server
                        mesgToReceive = bufferedReader.readLine();
                        System.out.println(mesgToReceive);
                    } catch (IOException e) {
                        // Handle disconnection or errors
                        closeAll(clientSocket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    // Close resources when needed
    public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter) {

        try {
            if (buffReader != null) {
                buffReader.close();
            }
            if (buffWriter != null) {
                buffWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

    }

    public void whisperCommand() throws IOException {
        Scanner inp = new Scanner(System.in);
        System.out.println("Enter recipient's username: ");
        String recipient = inp.nextLine();
        System.out.println("Enter your message: ");
        String message = inp.nextLine();
        bufferedWriter.write("/whisper " + recipient + " " + message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public void changeUsernameCommand() throws IOException {
        Scanner inp = new Scanner(System.in);
        System.out.println("Enter your new username: ");
        String newUsername = inp.nextLine();
        bufferedWriter.write("/username " + newUsername);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        clientName = newUsername;
    }

    public void helpCommand() throws IOException {
        bufferedWriter.write("/help");
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public void listCommand() throws IOException {
        bufferedWriter.write("/list");
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

}
