package org.academiadecodigo.nanderthals;

import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UDPserver {

    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        byte[] sendBuffer = new byte[1024];
        byte[] recvBuffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket(SERVER_PORT)) {
            while (true) {
                //create a DatagramPacket to receive data from client
                DatagramPacket receivePacket = new DatagramPacket(recvBuffer, recvBuffer.length);
                socket.receive(receivePacket);

                //extract the message from the received packet
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(message);

                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();

                //Convert message to uppercase
                String uppercase = message.toUpperCase();
                sendBuffer = uppercase.getBytes();

                //create a DatagramPacket to send the response back to the client
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
