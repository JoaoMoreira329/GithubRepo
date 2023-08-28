package org.academiadecodigo.nanderthals;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class UDPclient {

    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) throws IOException {
        try (Scanner input = new Scanner(System.in);
             DatagramSocket socket = new DatagramSocket()) {

            //prompt user for input
            System.out.println("What do you want to send? ");
            String inp = input.nextLine();
            System.out.println("My message: " + inp);

            //specify serevr's address and port
            InetAddress inetAddress = InetAddress.getByName("localhost");
            byte[] sendBuffer = inp.getBytes();
            DatagramPacket dgSend = new DatagramPacket(sendBuffer, sendBuffer.length, inetAddress, SERVER_PORT);
            socket.send(dgSend);

            //create a DatagramPacket to send data to the server
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            try {
                //receive response from server
                socket.receive(receivePacket);
                String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(response);
            } catch (SocketTimeoutException ste) {
                System.err.println("Error: Timeout Occurred, packet assumed lost");
            } finally {
                socket.close();
            }
        }
    }
}
