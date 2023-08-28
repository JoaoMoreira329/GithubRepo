package org.academiadecodigo.nanderthals;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// The Server class responsible for managing client connections
public class Server {

    private ServerSocket serverSocket;
    public static ArrayList<ServerWorker> serverWorkers = new ArrayList<>();
    private BufferedWriter bufferedWriter;
    private HashMap<String, Writer> map = new HashMap<>();

    // Start the server and listen for client connections
    public void serverStart() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080); // Create server socket
        ExecutorService cachepool = Executors.newCachedThreadPool(); // Thread pool for clients

        // Accept client connections and create ServerWorker instances to handle them
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            System.out.println("Connected");
            ServerWorker serverWorker = new ServerWorker(socket);
            serverWorkers.add(serverWorker); // Store worker instances
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            cachepool.submit(serverWorker); // Submit worker to thread pool
        }
    }

    public void sendMessage(String messageToSend) throws IOException {
        for (ServerWorker serverWorker : serverWorkers) {
            try {
                serverWorker.send(messageToSend);
            } catch (IOException e) {

            }
        }
    }

    public void updateUsername(String oldUsername, String newUsername) {
        map.put(newUsername, map.remove(oldUsername));
    }

    public void sendPrivateMessage(String sender, String recipient, String message) throws IOException {
        Writer writer = map.get(recipient);
        if (writer != null) {
            writer.write(sender + " whispered to you: " + message + "\n"); // Append \n for newline
            writer.flush();
        }
    }

    // Inner class representing a worker that handles a client connection
    private class ServerWorker implements Runnable {


        public Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private String name;
        private Server server;


        // Constructor to initialize worker when a client connects
        public ServerWorker(Socket socket) {
            // Initialize properties and handle initial communication with client
            try {
                this.socket = socket;
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.name = bufferedReader.readLine(); // Read client's name

                sendMessage(name + " logged in."); // Send a login message to all clients
                map.put(name, bufferedWriter); // Store the client's writer

            } catch (IOException e) {
                // Handle errors or disconnections
                closeAll(socket, bufferedReader, bufferedWriter);
            }
        }

        @Override
        public void run() {

            String messageClient;

            while (!socket.isClosed()) {
                try {
                    messageClient = bufferedReader.readLine();
                    messageClient = messageClient.trim(); // Trim leading and trailing spaces
                    if (messageClient.equals("/help")) { // Check for exact match
                        sendHelpMessage();
                    } else if (messageClient.startsWith("/list")) {
                        StringBuilder userList = new StringBuilder("Connected users:\n");
                        for (String username : map.keySet()) {
                            userList.append(username).append("\n");
                        }
                        send(userList.toString());
                    } else if (messageClient.startsWith("/username ")) {
                        String newUsername = messageClient.substring(10);
                        updateUsername(name, newUsername);
                        sendMessage(name + " changed their username to " + newUsername);
                        name = newUsername;
                    } else if (messageClient.startsWith("/whisper ")) {
                        String[] parts = messageClient.split(" ", 3);
                        if (parts.length == 3) {
                            String recipient = parts[1];
                            String privateMessage = parts[2];
                            sendPrivateMessage(name, recipient, privateMessage);
                        }
                    } else {
                        sendMessage(messageClient);
                    }
                } catch (IOException e) {
                    closeAll(socket, bufferedReader, bufferedWriter);
                }
            }
        }

        // Method for sending messages to the client
        public void send(String messageToSend) throws IOException {
            try {
                    synchronized (serverWorkers) {

                        bufferedWriter.write(messageToSend);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
            } catch (IOException e) {
                closeAll(socket, bufferedReader, bufferedWriter);

            }

        }

        public void removeServerWorker() {
            serverWorkers.remove(this);
            System.out.println("ServerWorker Removed");
        }

        // Close resources and remove worker from list
        public void closeAll(Socket socket, BufferedReader buffReader, BufferedWriter buffWriter) {

            removeServerWorker();
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

        // Send a help message to the client for /help command
        private void sendHelpMessage() throws IOException {
            String helpMessage = "Available commands:\n" +
                    "/help - Display this help message\n" +
                    "/list - List all connected users\n";
            send(helpMessage);
        }

    }
}
